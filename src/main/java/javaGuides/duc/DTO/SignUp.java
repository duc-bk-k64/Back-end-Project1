package javaGuides.duc.DTO;

import java.util.Set;

import lombok.Data;

@Data
public class SignUp {
	private String username;
	private String email;
	private String password;
	private String code; //studenCode or teacherCode
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private Set<String> role;

	public Set<String> getRole() {
		return role;
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

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
