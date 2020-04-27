package com.rentalcar.app.ws.ui.model.response;

import java.util.Date;

public class ErrorMesage {

	private Date timestamp;
	private String Message;
	
	public ErrorMesage() {}	

	public ErrorMesage(Date timestamp, String message) {
		this.timestamp = timestamp;
		Message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
