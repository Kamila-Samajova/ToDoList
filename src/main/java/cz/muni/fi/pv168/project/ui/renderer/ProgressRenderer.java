package cz.muni.fi.pv168.project.ui.renderer;

import cz.muni.fi.pv168.project.model.Progress;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.util.Map;

/**
 * Base renderer for Progress. Has 3 states - Planned, In-progress and Finished.
 *
 * @author Radomír Dedek
 */
public final class ProgressRenderer implements ListCellRenderer<Progress>, TableCellRenderer {

    private static final Map<Progress, Icon> PROGRESS_ICONS = Map.of(
            Progress.PLANNED, Icons.PLANNED_ICON.createIcon(),
            Progress.IN_PROGRESS, Icons.IN_PROGRESS_ICON.createIcon(),
            Progress.FINISHED, Icons.FINISHED_ICON.createIcon()
    );

    private final DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
    private final DefaultListCellRenderer listCellRenderer = new DefaultListCellRenderer();

    @Override
    public Component getListCellRendererComponent(JList<? extends Progress> list, Progress value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
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
        updateLabel(label, (Progress) value);
        return label;
    }

    private void updateLabel(JLabel label, Progress progress) {
        if (progress != null) {
            label.setIcon(PROGRESS_ICONS.get(progress));
        }
    }
}
