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
import java.awt.Component;
import java.time.LocalDate;
import javax.sql.DataSource;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

/**
 * Class for representing an edit dialog.
 *
 * @author Janka Marschalková
 * @author Kamila Šamajová
 */
public class EditDialog extends EntityDialog {

    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(EditDialog.class);
    private final Task task;
    private final JTextField description = new JTextField();
    private final ComboBoxModel<Progress> progress = new DefaultComboBoxModel<>(Progress.values());
    private final ComboBoxModel<Category> categories;
    private final DateModel<LocalDate> dueTime = new LocalDateModel();
    private final JTextField estimatedTime = new JTextField();
    private final JTextField location = new JTextField();
    private final TaskTableModel taskTableModel;

    public EditDialog(Task task, DataSource dataSource, TaskTableModel taskTableModel) {
        super(I18N.getString("title"));
        this.task = task;
        this.categories = new ComboBoxModelAdapter<>(new CategoryModel(new CategoryDao(dataSource)));
        this.taskTableModel = taskTableModel;
        setValues();
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

    private void setValues() {
        nameField.setText(task.getTaskName());
        description.setText(task.getDescription());
        progress.setSelectedItem(task.getProgress());
        categories.setSelectedItem(task.getCategory());
        dueTime.setValue(task.getDueTime());
        estimatedTime.setText(task.getEstimatedTime());
        location.setText(task.getLocation());
    }

    public void show(JButton parentButton) {
        var option = showDialog(parentButton);

        while (nameField.getText().isBlank() && option == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(parentButton, I18N.getString("failed_validation"));
            option = showDialog(parentButton);
        }

        if (option == JOptionPane.OK_OPTION) {
            task.setTaskName(nameField.getText());
            task.setDescription(description.getText() == null ? "" : description.getText());
            task.setProgress((Progress) progress.getSelectedItem());
            task.setCategory((Category) categories.getSelectedItem());
            task.setDueTime(dueTime.getValue() == null ? null : dueTime.getValue());
            task.setEstimatedTime(estimatedTime.getText() == null ? "" : estimatedTime.getText());
            task.setLocation(location.getText() == null ? "" : location.getText());
            taskTableModel.updateRow(task);
            taskTableModel.fireTableDataChanged();
        }
    }

    @Override
    protected int showDialog(Component parentComponent) {
        String[] options = {I18N.getString("edit_button"), I18N.getString("cancel_button")};

        return JOptionPane.showOptionDialog(parentComponent, panel, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
