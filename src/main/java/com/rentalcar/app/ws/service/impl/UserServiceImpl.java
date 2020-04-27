package com.rentalcar.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rentalcar.app.ws.excaption.UserServiceException;
import com.rentalcar.app.ws.io.entity.UserEntity;
import com.rentalcar.app.ws.io.repositories.UserRepository;
import com.rentalcar.app.ws.service.UserService;
import com.rentalcar.app.ws.shared.Utils;
import com.rentalcar.app.ws.shared.dto.UserDto;
import com.rentalcar.app.ws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils utils;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {
		UserEntity storedUserDetail = userRepository.findByEmail(user.getEmail());
		if (storedUserDetail != null)
			throw new RuntimeException("Record alredy exist");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId(publicUserId);
		userEntity.setEncryptesPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserEntity storedDetail = userRepository.save(userEntity);

		UserDto returnevalue = new UserDto();
		BeanUtils.copyProperties(storedDetail, returnevalue);
		return returnevalue;
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto returnvalue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnvalue);
		return returnvalue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptesPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String id) {
		UserDto returnvalue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(id);

		BeanUtils.copyProperties(userEntity, returnvalue);

		return returnvalue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		
		UserDto returnvalue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstname(user.getFirstname());
		userEntity.setLastname(user.getLastname());
		
		UserEntity updatesUserDetail= userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatesUserDetail, returnvalue);

		
		return returnvalue;
	}

	@Override
	public void deteUser(String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnvalue = new ArrayList<>();
		Pageable pageable = PageRequest.of(page, limit);
		
		Page<UserEntity> usersPage = userRepository.findAll(pageable);
		List<UserEntity> users = usersPage.getContent();
		
		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnvalue.add(userDto);
		}
		return returnvalue;
	}

}
