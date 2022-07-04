package com.java.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.UserDto;
import com.java.blog.services.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		ApiResponse apiResponse = new ApiResponse("User created successfully", createdUser, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
		UserDto updatedUser = userService.updateUser(userDto, userId);
		ApiResponse apiResponse = new ApiResponse("User updated successfully", updatedUser, true);
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		ApiResponse apiResponse = new ApiResponse("User deleted Successfully", null, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> getAllUsers() {
		List<UserDto> userList = this.userService.getAllUsers();
		ApiResponse apiResponse = new ApiResponse("List of users", userList, true);
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getSingleUserById(@PathVariable Integer userId) {
		UserDto user = this.userService.getUserById(userId);
		ApiResponse apiResponse = new ApiResponse("User detail of id : "+ userId, user, true);
		return ResponseEntity.ok(apiResponse);
	}
	
}
