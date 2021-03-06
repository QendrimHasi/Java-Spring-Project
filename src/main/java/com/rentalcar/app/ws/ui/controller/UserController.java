package com.rentalcar.app.ws.ui.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.ErrorManager;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentalcar.app.ws.excaption.UserServiceException;
import com.rentalcar.app.ws.service.UserService;
import com.rentalcar.app.ws.shared.dto.UserDto;
import com.rentalcar.app.ws.ui.model.request.UserDetailRequestModel;
import com.rentalcar.app.ws.ui.model.response.ErrorMessages;
import com.rentalcar.app.ws.ui.model.response.OperationStatusModel;
import com.rentalcar.app.ws.ui.model.response.RequestOperationName;
import com.rentalcar.app.ws.ui.model.response.RequestOpirationStatus;
import com.rentalcar.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users")   //http://localhost:8080/users
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();
		
		if (userDetails.getFirstname().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@GetMapping(path="/{id}" , produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id){
		UserRest returnValue = new UserRest();
		UserDto userDto= userService.getUserByUserId(id);
		
		BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue;
	}
	
	
	@PutMapping(path = "/{id}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailRequestModel userDetails){
		UserRest returnValue = new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updateUser = userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updateUser, returnValue);
		
		return returnValue;
	}
	
	
	
	@DeleteMapping(path = "/{id}",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id){
		
		OperationStatusModel returnvalue = new OperationStatusModel();
		returnvalue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deteUser(id);
		
		returnvalue.setOperationResult(RequestOpirationStatus.SUCCESS.name());
		return returnvalue;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page
			,@RequestParam(value = "limit", defaultValue = "25") int limit ){
		
		List<UserRest> returnvalue = new ArrayList<>();
		
		if(page>0) page = page - 1;
		
		List<UserDto> users = userService.getUsers(page,limit);
		
		for(UserDto userDto: users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnvalue.add(userModel);
		}
		return returnvalue;
	}

}
