package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.data.DataAccessObject;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Progress;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.ui.i18n.I18N;

import java.time.LocalDate;
import java.util.List;

/**
 * Class representing a task in the table.
 *
 * @author Radomír Dedek
 */
public class TaskTableModel extends EditableEntityTableModel<Task> {

    private static final I18N I18N = new I18N(TaskTableModel.class);

    private static final List<Column<Task, ?>> COLUMNS = List.of(
            Column.editable(I18N.getString("progress"), Progress.class, Task::getProgress, Task::setProgress),
            Column.readOnly(I18N.getString("task"), String.class, Task::getTaskName),
            Column.readOnly(I18N.getString("description"), String.class, Task::getDescription),
            Column.readOnly(I18N.getString("category"), Category.class, Task::getCategory),
            Column.readOnly(I18N.getString("due_date"), LocalDate.class, Task::getDueTime),
            Column.readOnly(I18N.getString("estimated_time"), String.class, Task::getEstimatedTime),
            Column.readOnly(I18N.getString("location"), String.class, Task::getLocation)
    );

    public TaskTableModel(DataAccessObject<Task> taskDao) {
        super(COLUMNS, taskDao);
    }


}
