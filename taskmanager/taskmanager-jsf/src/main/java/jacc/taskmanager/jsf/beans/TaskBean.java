package jacc.taskmanager.jsf.beans;

import jacc.taskmanager.jsf.model.Task;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@SessionScoped
public class TaskBean implements Serializable {

    private static int ids;

    private Task task;
    private List <Task> tasks;
    private int taskCount;

    public TaskBean() {
    }

    @PostConstruct
    private void init() {
        tasks = new ArrayList <>();
        Task firstTask = new Task(0, "first task");
        tasks.add(firstTask);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }


    public List <Task> getTasks() {
        return tasks;
    }

    public void setTasks(List <Task> tasks) {
        this.tasks = tasks;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void editTask(int id) {
        Optional <Task> optionalTask = getTask(id);
        task = optionalTask.orElse(new Task(0, ""));
    }

    public void clearTask() {
        task = new Task(0, "");
    }

    public void saveTask(int id) {
        if (id == 0) {

        }
    }


    public void deleteTask(int id) {
    }

    //Helper
    private Optional <Task> getTask(int id) {
        return tasks.stream().filter(x -> x.getTaskId() == id).findFirst();
    }
}
