package com.manjunath.responce;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data	
public class SignupRequest {

	private String email;

	private String fullname;

	private String otp;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	

}
