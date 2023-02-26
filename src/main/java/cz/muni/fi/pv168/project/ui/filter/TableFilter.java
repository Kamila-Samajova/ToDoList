package cz.muni.fi.pv168.project.ui.filter;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Progress;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;

import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing a table filter.
 *
 * @author Patrik Mažári
 * @author Radomír Dedek
 */
public final class TableFilter extends RowFilter<TaskTableModel, Integer> {

    private final TableRowSorter<TaskTableModel> rowSorter;
    private List<Progress> progresses = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private LocalDate fromDate = LocalDate.MIN;
    private LocalDate toDate = LocalDate.MAX;

    public TableFilter(TableRowSorter<TaskTableModel> rowSorter) {
        this.rowSorter = rowSorter;
    }

    public void filterDueDate(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate != null ? fromDate.minusDays(1) : LocalDate.MIN;
        this.toDate = toDate != null ? toDate.plusDays(1) : LocalDate.MAX;
        rowSorter.setRowFilter(this);
    }

    public void filterProgress(List<Progress> progresses) {
        this.progresses = progresses;
        rowSorter.setRowFilter(this);
    }

    public void filterCategory(List<Category> categories) {
        this.categories = categories;
        rowSorter.setRowFilter(this);
    }

    @Override
    public boolean include(Entry<? extends TaskTableModel, ? extends Integer> entry) {
        TaskTableModel tableModel = entry.getModel();
        Task task = tableModel.getEntity(entry.getIdentifier());
        LocalDate taskDate = task.getDueTime();
        return (progresses.isEmpty() || progresses.contains(task.getProgress())) &&
                (categories.isEmpty() || categories.contains(task.getCategory())) &&
                (taskDate == null || (taskDate.isBefore(TableFilter.this.toDate) &&
                        taskDate.isAfter(TableFilter.this.fromDate)));
    }
}
