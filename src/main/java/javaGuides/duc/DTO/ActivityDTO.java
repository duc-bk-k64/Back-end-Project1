package javaGuides.duc.DTO;

import java.time.Instant;

public class ActivityDTO {
	private String detail;
	private String name;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	private Instant time;

	public ActivityDTO(String detail, String name, Instant time) {
		this.detail = detail;
		this.name = name;
		this.time = time;
	}
}
