package javaGuides.duc.DTO;

import lombok.Data;

@Data
public class forgotPWdto {
	private String email;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
