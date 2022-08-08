package com.java.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.UserDto;
import com.java.blog.entities.User;
import com.java.blog.exceptions.ResourceNotFoundException;
import com.java.blog.repositories.UserRepository;
import com.java.blog.services.IUserService;

@Service
@PropertySource("classpath:/messages/api_response_messages.properties")
public class UserServiceImplimentation implements IUserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Environment environment;
	
	@Override
	public ApiResponse createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		UserDto userDtoResponse = this.userToDto(savedUser);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("user.creation.successful"), userDtoResponse, true);
		return apiResponse;
	}

	@Override
	public ApiResponse updateUser(UserDto userDto, Long userId) {
		User user = this.userRepository.findById(userId)
									   .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		if(!userDto.getName().isEmpty())
			user.setName(userDto.getName());
		if(!userDto.getEmail().isEmpty())
			user.setEmail(userDto.getEmail());
		if(!userDto.getPassword().isEmpty())
			user.setPassword(userDto.getPassword());
		if(!userDto.getAbout().isEmpty())
			user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepository.save(user);
		UserDto updatedUserDto = this.userToDto(updatedUser);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("user.updation.successful"), updatedUserDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse getUserById(Long userId) {
		User user = this.userRepository.findById(userId)
				                       .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		UserDto userDto = this.userToDto(user);
		ApiResponse apiResponse = new ApiResponse("User detail of id : "+ userId, userDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse getAllUsers() {
		List<User> userList = this.userRepository.findAll();
		List<UserDto> userListDto = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("user.list"), userListDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse deleteUser(Long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepository.delete(user);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("user.deletion.successful"), null, true);
		return apiResponse;
	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
