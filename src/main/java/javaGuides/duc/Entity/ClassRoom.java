package javaGuides.duc.Entity;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "classroom")
public class ClassRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private Instant time_create;
	@Column
	private Instant time_update;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Student> getStudents() {
		return students;
	}
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	public Set<teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(Set<teacher> teachers) {
		this.teachers = teachers;
	}
	@Column
	private String name;
	@OneToMany(mappedBy = "classRoom")
	private Set<Student> students;
	@OneToMany(mappedBy = "classRoom")
	private Set<teacher> teachers;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "menu_classroom", joinColumns = {
			@JoinColumn(name = "classroom_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false) })
	private Set<Menu> menus;
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	
}
