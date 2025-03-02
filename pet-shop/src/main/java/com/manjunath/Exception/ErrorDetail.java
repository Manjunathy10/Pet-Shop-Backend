package com.manjunath.Exception;

import java.time.LocalDateTime;

public class ErrorDetail {

	private String error;
	private String detail;
	private LocalDateTime timeStamp;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public ErrorDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErrorDetail(String error, String detail, LocalDateTime timeStamp) {
		super();
		this.error = error;
		this.detail = detail;
		this.timeStamp = timeStamp;
	}
	
	
	
}
