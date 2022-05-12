package javaGuides.duc.Entity;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="detail")
public class Detail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String comment;
	@Column
	private String photo;
	@Column
	private Instant timeIn;
	@Column
	private Instant timeOut;
	@Column
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@OneToOne(mappedBy = "detail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Student student;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Instant getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(Instant timeIn) {
		this.timeIn = timeIn;
	}
	public Instant getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Instant timeOut) {
		this.timeOut = timeOut;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getDetail() {
		if(this.student!=null) {
		String detail="Code:"+this.student.getStudentCode()+" Comment:"+this.comment+"  Time in:"+this.timeIn+" Time out:"+this.timeOut+" Photo url:"+this.photo;
		return detail;
		}
		return "Detail has no student";
		
	}
}
