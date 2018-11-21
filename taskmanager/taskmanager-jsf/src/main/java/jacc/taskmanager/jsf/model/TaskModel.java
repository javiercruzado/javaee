package jacc.taskmanager.jsf.model;

import java.time.LocalDate;
import java.util.List;

public class TaskModel {

    private int taskId;
    private String title;
    private String description;
    private String startDate;
    private LocalDate dueDate;
    private String assignedTo;
    private List <String> tags;

    public TaskModel() {
    }

    public TaskModel(int id, String title) {
        this.taskId = id;
        this.title = title;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public List <String> getTags() {
        return tags;
    }

    public void setTags(List <String> tags) {
        this.tags = tags;
    }

}
