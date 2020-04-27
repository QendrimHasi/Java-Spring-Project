package com.rentalcar.app.ws.excaption;


import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.rentalcar.app.ws.ui.model.response.ErrorMesage;
import com.rentalcar.app.ws.ui.model.response.ErrorMessages;

@ControllerAdvice
public class AppExcaptionHandler {
	
	@ExceptionHandler(value = {UserServiceException.class})
	public ResponseEntity<Object> handlerUserServiceException(UserServiceException ex , WebRequest request){
		ErrorMesage errorMesage = new ErrorMesage( new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMesage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handlerOtherException(Exception ex , WebRequest request){
		ErrorMesage errorMesage = new ErrorMesage( new Date(), ex.getMessage());
		return new ResponseEntity<>(errorMesage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
