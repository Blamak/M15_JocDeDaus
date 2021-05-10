package com.ITAcademy.M15_JocDeDaus.Response;

public class Message {
	
	private String message = "";
	private Object object;
	private String error = "";
	
	public Message() {
		
	}
	
	public Message(String message, Object object, String error) {
		this.message = message;
		this.object = object;
		this.error = error;
	}

	
	// getter & setters:
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
