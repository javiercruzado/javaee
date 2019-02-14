package jacc.taskmanager.jsf.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import jacc.taskmanager.entities.Task;
import jacc.taskmanager.services.TaskService;
import taskutils.TaskStatus;

@Named
@SessionScoped
public class TaskController implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private TaskService taskService;

	TaskStatus pageSelectionCriteria = TaskStatus.UNCOMPLETED;
	private String greeting;
	private Task taskModel;
	private Task selectedTaskModel;
	private List<Task> taskListModel;

	@PostConstruct
	private void init() {
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
		clearTask();
		taskListModel = taskService.getTasks(pageSelectionCriteria);
		/*
		 * if (!taskListModel.isEmpty()) { setSelectedTaskModel(taskListModel.get(0)); }
		 */
	}

	public void getTasksByStatus(TabChangeEvent event) {
		String statusId = (String) event.getTab().getAttributes().get("statusId");
		switch (statusId) {
		case "0":
			pageSelectionCriteria = TaskStatus.UNCOMPLETED;
			break;
		case "1":
			pageSelectionCriteria = TaskStatus.COMPLETED;
			break;
		}
		getTasks();
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

	public void saveAsCompleted(int id) {
		if (id != 0) {
			taskModel.setCompleted(true);
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

	public void editTask(int id) {
		Optional<Task> optionalTask = getTask(id);
		setTaskModel(optionalTask.orElse(new Task()));
	}

	public void onRowSelect(SelectEvent event) {
		Task task = (Task) event.getObject();
		setTaskModel(task);
		//FacesMessage msg = new FacesMessage("Task Selected", task.getTitle());
		//FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String formatDateTime(LocalDateTime dateTime) {
		if (dateTime == null)
			return "";
		return dateTime.toLocalDate().toString();
	}

}
