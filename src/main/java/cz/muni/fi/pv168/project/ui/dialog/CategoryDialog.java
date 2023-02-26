package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.data.CategoryDao;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.i18n.I18N;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Class for representing a category dialog.
 *
 * @author Janka Marschalková
 * @author Kamila Šamajová
 * @author Patrik Mažári
 */
public class CategoryDialog extends EntityDialog {

    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(CategoryDialog.class);
    private final CategoryDao categoryDao;

    public CategoryDialog(CategoryDao categoryDao) {
        super(I18N.getString("title"));
        this.categoryDao = categoryDao;
        addFields();
    }

    public void addFields() {
        add(I18N.getString("category_name") + ':', super.nameField, true);
    }

    @Override
    public void show(Component parentComponent) {
        var option = showDialog(parentComponent);

        while (nameField.getText().isBlank() && option == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(parentComponent, I18N.getString("failed_validation"));
            option = showDialog(parentComponent);
        }

        if (option == JOptionPane.OK_OPTION) {
            Category category = new Category(nameField.getText());
            categoryDao.create(category);
        }
    }
}
