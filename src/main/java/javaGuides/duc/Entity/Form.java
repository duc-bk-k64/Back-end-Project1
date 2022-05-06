package javaGuides.duc.Entity;

import java.time.Instant;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "form")
public class Form {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String name;
	@Column
	private String status;
	@Column
	private Instant time;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id",referencedColumnName = "id")
	private Student student;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Instant getTime() {
		return time;
	}
	public void setTime(Instant time) {
		this.time = time;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getDetail() {
		if(this.student!=null) {
			return "Student code:"+this.student.getStudentCode()+" Name:"+this.name+" Status:"+this.status+" Time:"+this.time;
		}
		else return "Form "+this.id+" has no student";
	}
}
