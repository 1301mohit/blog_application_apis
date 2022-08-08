package com.java.blog.services;

import java.util.List;

import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.UserDto;

public interface IUserService {
	
	ApiResponse createUser(UserDto userDto);
	
	ApiResponse updateUser(UserDto userDto, Long userId);
	
	ApiResponse getUserById(Long userId);
	
	ApiResponse getAllUsers();
	
	ApiResponse deleteUser(Long userId);

}
