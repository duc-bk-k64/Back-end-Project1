package javaGuides.duc.DTO;

import java.time.Instant;

public class DetailDTO {
	private String comment;
	private String photo;
	private Instant time_in;
	private Instant time_out;
	private String code;

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

	public Instant getTime_in() {
		return time_in;
	}

	public void setTime_in(Instant time_in) {
		this.time_in = time_in;
	}

	public Instant getTime_out() {
		return time_out;
	}

	public void setTime_out(Instant time_out) {
		this.time_out = time_out;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
