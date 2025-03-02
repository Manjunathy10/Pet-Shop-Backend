package com.manjunath.responce;

import lombok.Data;

@Data
public class ApiResponce {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
