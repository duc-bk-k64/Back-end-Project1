package javaGuides.duc.DTO;

import java.time.Instant;

public class MenuDTO {
	private String name;
	private String comment;
	private Instant date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Instant getDate() {
		return date;
	}
	public void setDate(Instant date) {
		this.date = date;
	}

}
