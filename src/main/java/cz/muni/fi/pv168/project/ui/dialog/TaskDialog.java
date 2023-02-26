package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.data.CategoryDao;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Progress;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.ui.i18n.I18N;
import cz.muni.fi.pv168.project.ui.model.CategoryModel;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.LocalDateModel;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.sql.DataSource;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.time.LocalDate;

/**
 * Class for representing a task dialog.
 *
 * @author Janka Marschalková
 * @author Kamila Šamajová
 * @author Patrik Mažári
 */
public class TaskDialog extends EntityDialog {

    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(TaskDialog.class);
    private final JTextField description = new JTextField();
    private final ComboBoxModel<Progress> progress = new DefaultComboBoxModel<>(Progress.values());
    private final ComboBoxModel<Category> categories;
    private final DateModel<LocalDate> dueTime = new LocalDateModel();
    private final JTextField estimatedTime = new JTextField();
    private final JTextField location = new JTextField();
    private final TaskTableModel taskTableModel;

    public TaskDialog(DataSource dataSource, TaskTableModel taskTableModel) {
        super(I18N.getString("title"));
        this.categories = new ComboBoxModelAdapter<>(new CategoryModel(new CategoryDao(dataSource)));
        this.taskTableModel = taskTableModel;
        addFields();
    }

    private void addFields() {
        add(I18Nentity.getString("task_name"), super.nameField, true);
        add(I18Nentity.getString("task_desc"), description);
        add(I18Nentity.getString("progress"), createProgressComboBox(progress), true);
        add(I18Nentity.getString("category"), createCategoryComboBox(categories), true);
        add(I18Nentity.getString("due_date"), new JDatePicker(dueTime));
        add(I18Nentity.getString("est_time"), estimatedTime);
        add(I18Nentity.getString("location"), location);
    }

    public void show(JButton parentButton) {
        var option = showDialog(parentButton);

        while ((nameField.getText().isBlank() || categories.getSelectedItem() == null) &&
                option == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(parentButton, I18N.getString("failed_validation"));
            option = showDialog(parentButton);
        }

        if (option == JOptionPane.OK_OPTION) {
            Task task = new Task(nameField.getText(), (Progress) progress.getSelectedItem(),
                    (Category) categories.getSelectedItem());
            task.setDescription(description.getText() == null ? "" : description.getText());
            task.setDueTime(dueTime.getValue() == null ? null : dueTime.getValue());
            task.setEstimatedTime(estimatedTime.getText() == null ? "" : estimatedTime.getText());
            task.setLocation(location.getText() == null ? "" : location.getText());
            taskTableModel.addRow(task);
            taskTableModel.fireTableDataChanged();
        }
    }
}
