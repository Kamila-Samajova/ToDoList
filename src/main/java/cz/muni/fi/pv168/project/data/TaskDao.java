package cz.muni.fi.pv168.project.data;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Progress;
import cz.muni.fi.pv168.project.model.Task;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Class for accessing the database table TASK.
 *
 * @author Radomír Dedek
 */
public class TaskDao implements DataAccessObject<Task>{

    private final DataSource dataSource;
    private final Function<Long, Category> categoryResolver;

    public TaskDao(DataSource dataSource, Function<Long, Category> categoryResolver) {
        this.dataSource = dataSource;
        this.categoryResolver = categoryResolver;
        if (!tableExists("APP", "TASK")) {
            createTable();
        }
    }

    @Override
    public void create(Task task) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO TASK (NAME, DESCRIPTION, PROGRESS, CATEGORY_ID, DUE_DATE, ESTIMATED_TIME, " +
                             "LOCATION) VALUES (?, ?, ?, ?, ?, ?, ?)", RETURN_GENERATED_KEYS)) {
            setRequest(task, st);
            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.getMetaData().getColumnCount() != 1) {
                    throw new DataAccessException("Failed to fetch generated key: " +
                            "compound key returned for task: " + task);
                }
                if (rs.next()) {
                    task.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Failed to fetch generated key: " +
                            "no key returned for task: " + task);
                }
                if (rs.next()) {
                    throw new DataAccessException("Failed to fetch generated key: " +
                            "multiple keys returned for task: " + task);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store task " + task, ex);
        }
    }

    @Override
    public List<Task> findAll() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, NAME, DESCRIPTION, " +
                     "PROGRESS, CATEGORY_ID, DUE_DATE, ESTIMATED_TIME, LOCATION FROM TASK")) {
            List<Task> tasks = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    var task = new Task(
                            rs.getString("NAME"),
                            Progress.valueOf(rs.getString("PROGRESS")),
                            categoryResolver.apply(rs.getLong("CATEGORY_ID")));
                    task.setId(rs.getLong("ID"));
                    task.setDescription(rs.getString("DESCRIPTION"));
                    task.setDueTime(rs.getDate("DUE_DATE") == null ?
                            null : rs.getDate("DUE_DATE").toLocalDate());
                    task.setEstimatedTime(rs.getString("ESTIMATED_TIME"));
                    task.setLocation(rs.getString("LOCATION"));
                    tasks.add(task);
                }
            }
            return tasks;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all tasks", ex);
        }
    }

    @Override
    public void update(Task task) throws DataAccessException {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task has null ID");
        }
        try (var connection = dataSource.getConnection();
            var st = connection.prepareStatement(
                "UPDATE TASK SET NAME = ?, DESCRIPTION = ?, PROGRESS = ?, CATEGORY_ID = ?, DUE_DATE = ?, " +
                        "ESTIMATED_TIME= ?, LOCATION = ? WHERE ID = ?")) {
            setRequest(task, st);
            st.setLong(8, task.getId());
            int rowsUpdated = st.executeUpdate();
        if (rowsUpdated == 0) {
            throw new DataAccessException("Failed to update non-existing task: " + task);
            }

        } catch (SQLException ex) {
            throw new DataAccessException("Failed to update task " + task, ex);
        }
    }

    private void setRequest(Task task, PreparedStatement st) throws SQLException {
        st.setString(1, task.getTaskName());
        st.setString(2, task.getDescription());
        st.setString(3, task.getProgress().name());
        st.setLong(4, task.getCategory().getId());
        st.setDate(5, task.getDueTime() == null ? null : Date.valueOf(task.getDueTime()));
        st.setString(6, task.getEstimatedTime());
        st.setString(7, task.getLocation());
    }

    @Override
    public void delete(Task task) throws DataAccessException {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task has null ID: " + task);
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("DELETE FROM TASK WHERE ID = ?")) {
            st.setLong(1, task.getId());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException("Failed to delete non-existing task: " + task);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to delete task " + task, ex);
        }
    }

    private boolean tableExists(String schema, String table) {
        try (var connection = dataSource.getConnection();
             var resultSet = connection.getMetaData().getTables(null, schema, table, null)) {
            return resultSet.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to get information about the existance of "
                    + table + " in schema " + schema);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            if (!tableExists("APP", "TASK")) {
                statement.executeUpdate("CREATE TABLE APP.TASK (" +
                        "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                        "NAME VARCHAR(100) NOT NULL," +
                        "DESCRIPTION VARCHAR(200)," +
                        "PROGRESS VARCHAR(20) NOT NULL CONSTRAINT PROGRESS_CHECK CHECK " +
                        "(PROGRESS IN ('PLANNED','IN_PROGRESS','FINISHED'))," +
                        "CATEGORY_ID BIGINT REFERENCES CATEGORY(ID)," +
                        "DUE_DATE DATE," +
                        "ESTIMATED_TIME VARCHAR(100)," +
                        "LOCATION VARCHAR(100)" +
                        ")");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create TASK table", ex);
        }
    }
}
