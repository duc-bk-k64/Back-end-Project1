package javaGuides.duc.DTO;

import java.time.Instant;
public class FormDTO {
	private String name;
	private String status;
	private Instant time;
	private String code;
	public FormDTO(String name,String status,Instant time,String code) {
		this.name=name;
		this.status=status;
		this.time=time;
		this.code=code;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
