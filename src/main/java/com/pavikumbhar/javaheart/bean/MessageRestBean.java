package com.pavikumbhar.javaheart.bean;

public class MessageRestBean {

	private String message;
	private String errorCode;
	
	public MessageRestBean() {
		
	}
	
	public MessageRestBean(String message) {
		this.message = message;
	}

	public MessageRestBean(String message, String errorCode) {
		this(message);
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
