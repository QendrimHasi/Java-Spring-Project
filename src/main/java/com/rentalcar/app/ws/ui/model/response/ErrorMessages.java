package com.rentalcar.app.ws.ui.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing required field. Please check dokumentation for required fields"),
	RECORD_ALREDY_EXISTS("Record alredy exists"),
	INERNAL_SERVER_ERROR("Internal server Error"),
	NO_RECORD_FOUND("Record with provided it nis not found"),
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("Could not update Record"),
	COULD_NOT_DELETE_RECORD("Could not delete Record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be found");
	
	private String errorMessage;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	

}
