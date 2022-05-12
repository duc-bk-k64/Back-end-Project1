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
@Table(name = "activity")
public class Activity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String detail;
	@Column
	private Instant time;
	@Column
	private String name;
	@Column
	private Instant time_create;
	@Column
	private Instant time_update;
	@Column String code;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@ManyToMany(mappedBy="activities")
	private Set<User> users;
	

}
