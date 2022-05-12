package javaGuides.duc.Entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "timetable")
public class TimeTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private Instant time;
	@Column
	private String detail;
	@Column
	private Instant time_create;
	@Column
	private Instant time_update;
	@Column
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Instant getTime_create() {
		return time_create;
	}
	public void setTime_create(Instant time_create) {
		this.time_create = time_create;
	}
	public Instant getTime_update() {
		return time_update;
	}
	public void setTime_update(Instant time_update) {
		this.time_update = time_update;
	}
	@ManyToMany(mappedBy = "tables")
	private Set<Student> students;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
}
