package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.filter.TableFilter;
import cz.muni.fi.pv168.project.ui.i18n.I18N;
import cz.muni.fi.pv168.project.ui.model.LocalDateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Class for representing a dDue Date filter dialog.
 * @author Radomír Dedek
 *
 */
public class DateFilterDialog extends EntityDialog {

    private static final I18N I18N = new I18N(DateFilterDialog.class);
    private final LocalDateModel fromDateModel = new LocalDateModel();
    private final LocalDateModel toDateModel = new LocalDateModel();
    private final TableFilter tableFilter;

    public DateFilterDialog(String title, TableFilter tableFilter) {
        super(title);
        this.tableFilter = tableFilter;
        add(I18N.getString("from"), new JDatePicker(fromDateModel));
        add(I18N.getString("to"), new JDatePicker(toDateModel));
    }

    @Override
    public void show(Component parentComponent) {
        var option = showDialog(parentComponent);

        if (option == JOptionPane.OK_OPTION) {
            tableFilter.filterDueDate(fromDateModel.getValue(), toDateModel.getValue());
        }
    }

    @Override
    protected int showDialog(Component parentComponent) {
        String[] options = {I18N.getString("filter"), I18N.getString("cancel")};

        return JOptionPane.showOptionDialog(parentComponent, panel, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

}
