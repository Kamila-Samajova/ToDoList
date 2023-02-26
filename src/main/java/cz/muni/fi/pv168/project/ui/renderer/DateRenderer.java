package cz.muni.fi.pv168.project.ui.renderer;

import cz.muni.fi.pv168.project.ui.resources.Icons;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer for date. Marks today´s tasks.
 *
 * @author Kamila Šamajová
 */
public final class DateRenderer implements ListCellRenderer<LocalDate>, TableCellRenderer {

    private final DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    private final DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends LocalDate> list, LocalDate value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        var label = (JLabel) listCellRenderer.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        updateLabel(label, value);
        return label;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        var label = (JLabel) tableCellRenderer.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
        updateLabel(label, (LocalDate) value);
        return label;
    }

    private void updateLabel(JLabel label, LocalDate localDate) {
        if (localDate != null && localDate.equals(LocalDate.now())) {
            label.setForeground(new Color(215,0,0));
            label.setIcon(Icons.TODAY_ICON.createIcon());
        }
        else {
            label.setForeground(Color.BLACK);
            label.setIcon(null);
        }
    }
}
