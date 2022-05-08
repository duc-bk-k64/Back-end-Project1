package javaGuides.duc.DTO;

import java.time.Instant;

public class TimetableDTO {
	private Instant time;
	private String detail;

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
}
