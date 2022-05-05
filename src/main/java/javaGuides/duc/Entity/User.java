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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String tokenForgotPassword;
	@Column
	private Instant timeCreation;

	public Instant getTimeCreation() {
		return timeCreation;
	}

	public void setTimeCreation(Instant timeCreation) {
		this.timeCreation = timeCreation;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false) })
	private Set<Role> roles;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Student student;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private teacher teacher;

	public long getId() {
		return id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(teacher teacher) {
		this.teacher = teacher;
	}


	public String getUsername() {
		return username;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getTokenForgotPassword() {
		return tokenForgotPassword;
	}

	public void setTokenForgotPassword(String tokenForgotPassword) {
		this.tokenForgotPassword = tokenForgotPassword;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getDetail(User user) {
		String detailString = "Username:" + user.getUsername() + " " + "Email:"
				+ user.getEmail();
		return detailString;
	}

}
