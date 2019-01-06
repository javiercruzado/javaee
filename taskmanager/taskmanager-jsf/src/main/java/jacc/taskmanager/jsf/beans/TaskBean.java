package jacc.taskmanager.jsf.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import jacc.taskmanager.entities.Task;
import jacc.taskmanager.services.TaskService;

@Named
@SessionScoped
public class TaskBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private TaskService taskService;
	private String greeting;
	private Task taskModel;
	private Task selectedTaskModel;
	private List<Task> taskListModel;

	@PostConstruct
	private void init() {
		setTaskModel(new Task());
		greeting = taskService.getGreeting();
		getTasks();
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public Task getTaskModel() {
		return taskModel;
	}

	public void setTaskModel(Task taskModel) {
		this.taskModel = taskModel;
	}

	public Task getSelectedTaskModel() {
		return selectedTaskModel;
	}

	public void setSelectedTaskModel(Task selectedTaskModel) {
		this.selectedTaskModel = selectedTaskModel;
	}

	public List<Task> getTaskListModel() {
		return taskListModel;
	}

	public void setTaskListModel(List<Task> taskListModel) {
		this.taskListModel = taskListModel;
	}

	// data

	public void getTasks() {
		taskListModel = taskService.getTasks();
		if (taskListModel.size() > 0) {
			setSelectedTaskModel(taskListModel.get(0));
		}
	}

	public void saveTask(int id) {
		if (id == 0) {
			Task task = new Task();
			task.setTitle(taskModel.getTitle());
			task.setDescription(taskModel.getDescription());
			task.setStartDate(taskModel.getStartDate());
			task.setDueDate(taskModel.getDueDate());
			task.setCompleted(taskModel.isCompleted());
			taskService.createTask(task);
		} else {
			taskService.updateTask(taskModel);
		}
		getTasks();
	}

	public void deleteTask(int id) {
		taskService.deleteTask(id);
		getTasks();
	}

	public void clearTask() {
		setTaskModel(new Task());
	}

	// Helpers
	private Optional<Task> getTask(int id) {
		return taskListModel.stream().filter(x -> x.getId() == id).findFirst();
	}

	public int getTaskCount() {
		return taskListModel.size();
	}

	public void editTask(int id) {
		Optional<Task> optionalTask = getTask(id);
		setTaskModel(optionalTask.orElse(new Task()));
	}

	public void onRowSelect(SelectEvent event) {
		Task task = (Task) event.getObject();
		setTaskModel(task);
		FacesMessage msg = new FacesMessage("Task Selected", task.getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
