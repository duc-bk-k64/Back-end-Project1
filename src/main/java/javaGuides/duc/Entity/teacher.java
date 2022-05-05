package javaGuides.duc.Entity;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.scheduling.SchedulingAwareRunnable;

import net.bytebuddy.asm.Advice.This;
@Entity
@Table(name="teacher")
public class teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String teacherCode;
	@Column
	private String name;
	@Column
	private String phoneNumber;
	@Column
	private String adress;
	@Column(length = 256)
	private String image;
	@Column
	private Instant dateOfBirth;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	private User user;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTeacherCode() {
		return teacherCode;
	}
	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Instant getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDetail() {
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append(this.getName()+" "+this.getAdress()+" "+this.getPhoneNumber());
		return stringBuilder.toString();
	}
	
}
