package jacc.taskmanager.entities;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.NamedQueries;

@Entity
@Table(schema = "taskmanager")
@Access(FIELD)
@NamedQueries({

		@NamedQuery(name = "Task.FindAll", query = "select t from Task t order by t.completed, t.dueDate, t.startDate"),
		@NamedQuery(name = "Task.FindAllCompleted", query = "select t from Task t where t.completed=true order by t.completed, t.dueDate, t.startDate"),
		@NamedQuery(name = "Task.FindAllNotCompleted", query = "select t from Task t where t.completed=false order by t.completed, t.dueDate, t.startDate") })
public class Task {

	@Id
	@GeneratedValue(strategy = AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}

	@NotNull(message = "mandatory")
	@Size(min = 5, message = "The min lenght for this field is 5")
	private String title;
	private String description;

	@NotNull(message = "mandatory")
	private LocalDate startDate;

	@NotNull(message = "mandatory")
	private LocalDate dueDate;
	private LocalDate completedDate;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private boolean completed;

	@Transient
	private int delayedDays;
	@Transient
	private int spentDays;

	@ManyToMany
	@JoinTable(schema = "taskmanager")
	private List<Tag> tags;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String descrption) {
		this.description = descrption;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(LocalDate completedDate) {
		this.completedDate = completedDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public int getDelayedDays() {
		Period period;
		if (completed) {
			period = Period.between(dueDate, completedDate);
			return period.getDays() > 0 ? period.getDays() : 0;
		} else {
			period = Period.between(dueDate, LocalDate.now());
			return period.getDays() > 0 ? period.getDays() : 0;
		}
	}

	public int getSpentDays() {
		Period period;

		if (completed) {
			period = Period.between(startDate, completedDate);
			return period.getDays() > 0 ? period.getDays() : 0;
		} else {
			period = Period.between(startDate, LocalDate.now());
			return period.getDays() > 0 ? period.getDays() : 0;
		}
	}

}
