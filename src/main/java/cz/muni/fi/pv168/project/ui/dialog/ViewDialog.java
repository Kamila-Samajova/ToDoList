package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.ui.i18n.I18N;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Class for representing a view dialog.
 *
 * @author Kamila Šamajová
 */
public class ViewDialog extends EntityDialog {

    private static final cz.muni.fi.pv168.project.ui.i18n.I18N I18N = new I18N(EditDialog.class);
    private final Task task;

    public ViewDialog(Task task) {
        super(I18N.getString("title"));
        this.task = task;
        addFields();
    }

    private void addFields() {
        add(I18Nentity.getString("task_name"), new JLabel(task.getTaskName()));
        add(I18Nentity.getString("task_desc"), new JLabel(task.getDescription()));
        add(I18Nentity.getString("progress"), new JLabel(task.getProgress().toString()));
        add(I18Nentity.getString("category"), new JLabel(task.getCategory().toString()));
        add(I18Nentity.getString("due_date"), new JLabel(task.getDueTime() == null ?
                "" : task.getDueTime().toString()));
        add(I18Nentity.getString("est_time"), new JLabel(task.getEstimatedTime()));
        add(I18Nentity.getString("location"), new JLabel(task.getLocation()));
    }

    public void show(JButton parentButton) {
        showDialog(parentButton);
    }

    @Override
    protected int showDialog(Component parentComponent) {
        String[] options = {I18N.getString("cancel_button")};

        return JOptionPane.showOptionDialog(parentComponent, panel, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
