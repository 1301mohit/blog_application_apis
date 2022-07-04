package com.java.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.blog.dtos.UserDto;
import com.java.blog.entities.User;
import com.java.blog.exceptions.ResourceNotFoundException;
import com.java.blog.repositories.UserRepository;
import com.java.blog.services.IUserService;

@Service
public class UserServiceImplimentation implements IUserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepository.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
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
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId)
				                       .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		System.out.println(user);
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = this.userRepository.findAll();
		return userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepository.delete(user);
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
