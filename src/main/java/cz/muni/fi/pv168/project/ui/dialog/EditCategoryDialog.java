package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.data.CategoryDao;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.i18n.I18N;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.Objects;

/**
 * Class for representing a category dialog.
 *
 * @author Janka Marschalková
 * @author Kamila Šamajová
 * @author Patrik Mažári
 */
public class EditCategoryDialog extends EntityDialog {

    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(CategoryDialog.class);
    private final CategoryDao categoryDao;
    private final JComboBox<Category> categoryComboBox;
    private final int tableRowCount;

    public EditCategoryDialog(CategoryDao categoryDao, JComboBox<Category> categoryComboBox, int tableRowCount) {
        super(I18N.getString("edit_delete_title"));
        this.categoryDao = categoryDao;
        this.categoryComboBox = categoryComboBox;
        this.tableRowCount = tableRowCount;
        addFields();
    }

    public void addFields() {
        add(I18N.getString("new_category_name") + " " + 
                categoryComboBox.getItemAt(categoryComboBox.getSelectedIndex()).getName() + ": ", 
                super.nameField, true);
    }

    @Override
    public void show(Component parentComponent) {
        Category origin_category = categoryComboBox.getItemAt(categoryComboBox.getSelectedIndex());
        var option = showEditDialog(parentComponent);

        var finished_editing = false;
        while (!finished_editing) {
            if (option == JOptionPane.OK_OPTION) {
                if (nameField.getText().isBlank()) {
                    JOptionPane.showMessageDialog(parentComponent, I18N.getString("failed_validation"));
                    option = showEditDialog(parentComponent);
                } else {
                    var already_exists = categoryDao.findAll().stream()
                            .anyMatch(e -> Objects.equals(e.getName(), nameField.getText()));
                    if (already_exists) {
                        JOptionPane.showMessageDialog(parentComponent, I18N.getString("failed_edit_category"));
                        option = showEditDialog(parentComponent);
                    } else {
                        origin_category.setName(nameField.getText());
                        categoryDao.update(origin_category);
                        finished_editing = true;
                    }
                }
            } else if (option == 1) {
                if (tableRowCount == 0) {
                    categoryDao.delete(origin_category);
                    finished_editing = true;
                } else {
                    JOptionPane.showMessageDialog(parentComponent, I18N.getString("failed_delete_category"));
                    option = showEditDialog(parentComponent);
                }
            } else {
                finished_editing = true;
            }
        }
    }
}
