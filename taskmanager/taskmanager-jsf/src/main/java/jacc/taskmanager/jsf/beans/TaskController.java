package jacc.taskmanager.jsf.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

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

	private TimelineModel timeLineModel;
	private Date startDate;

	@PostConstruct
	private void init() {
		getTasks();
		fillTimeLineModel();
		greeting = "Welcome, here you check your pending tasks: (" + taskListModel.size() + " pending)";
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

	public TimelineModel getTimeLineModel() {
		fillTimeLineModel();
		return timeLineModel;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date minDate) {
		this.startDate = minDate;
	}

	private LocalDate minLocalDate;

	private void fillTimeLineModel() {
		timeLineModel = new TimelineModel();
		taskListModel.forEach(x -> {
			Calendar cal = Calendar.getInstance();
			cal.setTime(java.sql.Date.valueOf(x.getDueDate()));
			timeLineModel.add(new TimelineEvent(x.getTitle(), cal.getTime()));
		});
		if (!taskListModel.isEmpty()) {
			minLocalDate = taskListModel.get(0).getDueDate();
			taskListModel.forEach(x -> {
				if (x.getDueDate().isBefore(minLocalDate))
					minLocalDate = x.getDueDate();
			});
		}
		startDate = java.sql.Date.valueOf(minLocalDate.minusDays(1));
	}

	public void setTimeLineModel(TimelineModel timeLineModel) {
		this.timeLineModel = timeLineModel;
	}

	// data

	public void getTasks() {
		clearTask();
		taskListModel = taskService.getTasks(pageSelectionCriteria);
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
		default:
			pageSelectionCriteria = TaskStatus.UNCOMPLETED;
		}
		getTasks();
	}

	public String saveTask(int id) {
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
		return "/index.xhtml?faces-redirect=true";
	}

	public String saveAsCompleted(int id) {
		if (id != 0) {
			taskModel.setCompleted(true);
			taskModel.setCompletedDate(LocalDate.now());
			taskService.updateTask(taskModel);
		}
		getTasks();
		return "/index.xhtml?faces-redirect=true";
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
	}

	public String formatDateTime(LocalDateTime dateTime) {
		if (dateTime == null)
			return "";
		return dateTime.toLocalDate().toString();
	}

	public String formatDate(LocalDate date) {
		if (date == null)
			return "";
		return date.toString();
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

}
