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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.NamedQueries;

@Entity
@Access(FIELD)
@NamedQueries({

		@NamedQuery(name = "Task.FindAll", query = "select t from Task t order by t.completed, t.dueDate, t.startDate"),
		@NamedQuery(name = "Task.FindAllCompleted", query = "select t from Task t where t.completed=1 order by t.completed, t.dueDate, t.startDate"),
		@NamedQuery(name = "Task.FindAllNotCompleted", query = "select t from Task t where t.completed=0 order by t.completed, t.dueDate, t.startDate") })
public class Task {

	@Id
	@GeneratedValue(strategy = AUTO)
	private Integer id;

	public Integer getId() {
		return id;
	}

	@NotNull
	@Size(min = 5, message = "The min lenght for this field is 5")
	private String title;
	private String description;

	@NotNull
	private LocalDate startDate;

	@NotNull
	private LocalDate dueDate;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private boolean completed;

	@Transient
	private int delayedDays;
	@Transient
	private int spentDays;

	@ManyToMany
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

	public LocalDateTime getCreatedOn() {
		return createDate;
	}

	public void setCreatedOn(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
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

	/*
	 * public int getElapsedDays() { Period period = Period.between(dueDate,
	 * LocalDate.now()); return period.getDays(); }
	 */

	public int getDelayedDays() {
		Period period = Period.between(dueDate, LocalDate.now());
		if (!completed && period.getDays() > 0)
			return period.getDays();
		else
			return 0;
	}

	public int getSpentDays() {
		LocalDate dateOfCompletion = updateDate == null ? LocalDate.now() : updateDate.toLocalDate();
		Period period = Period.between(dateOfCompletion, createDate.toLocalDate());
		return period.getDays();
	}

}
