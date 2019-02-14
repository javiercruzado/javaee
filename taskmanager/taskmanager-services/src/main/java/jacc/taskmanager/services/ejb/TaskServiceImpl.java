package jacc.taskmanager.services.ejb;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

//	@Inject  
//    private Logger logger;

	private static final long serialVersionUID = 1L;
	@PersistenceContext(name = "TaskManagerUnit")
	EntityManager entityManager;

	public String getGreeting() {
		return "Hi, it is " + LocalDate.now().toString();
	}

	@Override
	public void createTask(Task entity) {
		entity.setCreatedOn(LocalDateTime.now());
		entityManager.persist(entity);
	}

	public Task updateTask(Task task) {
		task.setUpdateDate(LocalDateTime.now());
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
