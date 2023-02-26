package cz.muni.fi.pv168.project.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class for representing a task.
 *
 * @author Kamila Šamajová
 */
public class Task {

    private Long id;
    private String taskName;
    private String description = "";
    private Progress progress;
    private Category category;
    private LocalDate dueTime;
    private String estimatedTime = "";
    private String location = "";

    public Task(String name, Progress progress, Category category) {
        this.taskName = name;
        this.progress = progress;
        this.category = category;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDate dueTime) {
        this.dueTime = dueTime;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.taskName + " as " + this.progress + " in category " + this.category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return taskName.equals(task.taskName) &&
                Objects.equals(description, task.description) &&
                progress == task.progress && category.equals(task.category) &&
                Objects.equals(dueTime, task.dueTime) && Objects.equals(estimatedTime, task.estimatedTime) &&
                Objects.equals(location, task.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, progress, category, dueTime, estimatedTime, location);
    }
}
