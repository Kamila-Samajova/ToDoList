package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Progress;
import cz.muni.fi.pv168.project.ui.i18n.I18N;
import cz.muni.fi.pv168.project.ui.renderer.ProgressRenderer;
import cz.muni.fi.pv168.project.ui.resources.AppColor;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Abstract class for representing a dialog window.
 *
 * @author Kamila Šamajová
 * @author Patrik Mažári
 */
public abstract class EntityDialog {

    protected static final cz.muni.fi.pv168.project.ui.i18n.I18N I18Nentity = new I18N(EntityDialog.class);
    protected final String title;
    protected final JPanel panel = new JPanel(new GridBagLayout());
    protected final JTextField nameField = new JTextField();
    private int nextComponentRow = 0;

    protected EntityDialog(String title) {
        this.title = title;
    }

    protected void add(String labelText, JComponent component) {
        add(labelText + "    ", component, false);
    }

    protected void add(String labelText, JComponent component, boolean required) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = nextComponentRow++;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        var label = new JLabel(labelText);
        label.setLabelFor(component);
        panel.add(label, constraints);

        constraints.gridx++;
        if (required) {
            var requiredLabel = new JLabel("  *  ");
            requiredLabel.setForeground(AppColor.VALIDATION.getColor());
            panel.add(requiredLabel, constraints);
        }

        constraints.gridx++;
        constraints.weightx = 1.0;
        panel.add(component, constraints);
    }

    protected JComboBox<Progress> createProgressComboBox(ComboBoxModel<Progress> progress) {
        var progressComboBox = new JComboBox<>(progress);
        progressComboBox.setRenderer(new ProgressRenderer());
        return progressComboBox;
    }

    protected JComboBox<Category> createCategoryComboBox(ComboBoxModel<Category> categories) {
        return new JComboBox<>(categories);
    }

    public void show(Component parentComponent) {
        var option = showDialog(parentComponent);

        while (nameField.getText().isBlank() && option == JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(parentComponent, "Name cannot be empty");
            option = showDialog(parentComponent);
        }
    }

    protected int showDialog(Component parentComponent) {
        String[] options = {I18Nentity.getString("add_button"),
                I18Nentity.getString("cancel_button")};

        return JOptionPane.showOptionDialog(parentComponent, panel, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    protected int showEditDialog(Component parentComponent) {
        String[] options = {I18Nentity.getString("edit_button"),
                I18Nentity.getString("remove_button"),
                I18Nentity.getString("cancel_button")};
        String[] ok_option = {I18Nentity.getString("edit_button"),
                I18Nentity.getString("remove_button")};
        return JOptionPane.showOptionDialog(parentComponent, panel, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }
}
