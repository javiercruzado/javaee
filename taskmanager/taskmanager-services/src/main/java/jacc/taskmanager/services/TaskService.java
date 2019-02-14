package jacc.taskmanager.services;

import java.util.List;

import jacc.taskmanager.entities.Task;
import taskutils.TaskStatus;

public interface TaskService {

	String getGreeting();

	void createTask(Task entity);

	Task updateTask(Task task);

	Task findTask(int id);

	void deleteTask(int id);
	
	List<Task> getTasks();
	
	List<Task> getTasks(TaskStatus taskCriteria);

}
