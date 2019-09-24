package jacc.taskmanager.services.ejb;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jacc.taskmanager.entities.Task;
import jacc.taskmanager.services.TaskService;
import taskutils.TaskStatus;

@Stateless
public class TaskServiceImpl implements TaskService, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "TaskManagerUnit")
	private EntityManager entityManager;

	public String getGreeting() {
		return "Welcome, date: " + LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
	}

	@Override
	public void createTask(Task entity) {
		entity.setCreatedDate(LocalDateTime.now());
		entityManager.persist(entity);
	}

	public Task updateTask(Task task) {
		task.setUpdatedDate(LocalDateTime.now());
		return entityManager.merge(task);
	}

	public Task findTask(int id) {
		return entityManager.find(Task.class, id);
	}

	public void deleteTask(int id) {
		Task task = this.findTask(id);
		entityManager.remove(task);
	}

	public List<Task> getTasks() {
		TypedQuery<Task> query = entityManager.createNamedQuery("Task.FindAll", Task.class);
		return query.getResultList();
	}

	@Override
	public List<Task> getTasks(TaskStatus taskCriteria) {

		if (taskCriteria.equals(TaskStatus.COMPLETED)) {
			TypedQuery<Task> query = entityManager.createNamedQuery("Task.FindAllCompleted", Task.class);
			return query.getResultList();
		} else if (taskCriteria.equals(TaskStatus.UNCOMPLETED)) {
			TypedQuery<Task> query = entityManager.createNamedQuery("Task.FindAllNotCompleted", Task.class);
			return query.getResultList();
		} else {
			return new ArrayList<>();
		}
	}

}
