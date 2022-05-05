package javaGuides.duc.DTO;

import java.time.Instant;

public class informationDTO {

	private String code;
	private String name;
	private String adress;
	private String phoneNumber;
	private String image;
	private Instant dateofBirth;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Instant getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Instant dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
}
