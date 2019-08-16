package jacc.taskmanager.entities;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

/**
 * Entity implementation class for Entity: Tag
 *
 */
@Entity
@Table(schema = "taskmanager")
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = AUTO)
	private short tagId;
	@Column(unique = true)
	private String name;
	private LocalDate createDate;

	public Tag() {
		super();
	}

	public short getTagId() {
		return this.tagId;
	}

	public void setTagId(short tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

}
