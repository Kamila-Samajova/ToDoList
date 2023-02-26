package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.Category;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class for accessing the database table CATEGORY.
 *
 * @author Radomír Dedek
 */
public class CategoryDao implements DataAccessObject<Category>{

    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
        if (!tableExists("APP", "CATEGORY")) {
            createTable();
        }
    }

    @Override
    public void create(Category category) throws DataAccessException {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO CATEGORY (NAME) VALUES (?)",
                     RETURN_GENERATED_KEYS)) {
            st.setString(1, category.getName());
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.getMetaData().getColumnCount() != 1) {
                    throw new DataAccessException("Failed to fetch generated key: " +
                            "compound key returned for category: " + category);
                }
                if (rs.next()) {
                    category.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Failed to fetch generated key: " +
                            "no key returned for category: " + category);
                }
                if (rs.next()) {
                    throw new DataAccessException("Failed to fetch generated key: " +
                            "multiple keys returned for category: " + category);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store category " + category, ex);
        }
    }

    @Override
    public List<Category> findAll() throws DataAccessException {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, NAME FROM CATEGORY")) {

            List<Category> categories = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    var category = new Category(rs.getLong("ID"), rs.getString("NAME"));
                    categories.add(category);
                }
            }
            return categories;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all employees", ex);
        }
    }

    public Category findById(Long id) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, NAME FROM CATEGORY WHERE ID = ?")) {
            st.setLong(1, id);
            try (var rs = st.executeQuery()) {
                if (rs.next()) {
                    var category = new Category(rs.getLong("ID"), rs.getString("NAME"));
                    if (rs.next()) {
                        throw new DataAccessException("Multiple categories with id " + id + " found");
                    }
                    return category;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load category with id " + id, ex);
        }
    }

    @Override
    public void update(Category category) throws DataAccessException {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Category has null ID");
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE CATEGORY SET NAME = ? WHERE ID = ?")) {
            st.setString(1, category.getName());
            st.setLong(2, category.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException("Failed to update non-existing category: " + category);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to update category " + category, ex);
        }
    }

    @Override
    public void delete(Category category) throws DataAccessException {
        if (category.getId() == null) {
            throw new IllegalArgumentException("Category has null ID: " + category);
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("DELETE FROM CATEGORY WHERE ID = ?")) {
            st.setLong(1, category.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException("Failed to delete non-existing category: " + category);
            }
        } catch (DerbySQLIntegrityConstraintViolationException ex) {
            throw new DataAccessException("Failed to delete category. Category " + category + " is used in Task.");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to delete category " + category, ex);
        }
    }

    private boolean tableExists(String schema, String table) {
        try (var connection = dataSource.getConnection();
             var resultSet = connection.getMetaData().getTables(null, schema, table, null)) {
            return resultSet.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to get information about the existance of "
                    + table + " in schema " + schema, ex);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            if (!tableExists("APP", "CATEGORY")) {
                statement.executeUpdate("CREATE TABLE APP.CATEGORY (" +
                        "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                        "NAME VARCHAR(100) NOT NULL)");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create CATEGORY table", ex);
        }
    }
}
