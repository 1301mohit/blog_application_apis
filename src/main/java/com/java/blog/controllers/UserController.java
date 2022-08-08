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
		ApiResponse apiResponse = userService.createUser(userDto);
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Long userId) {
		ApiResponse apiResponse = userService.updateUser(userDto, userId);
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {
		ApiResponse apiResponse = this.userService.deleteUser(userId);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> getAllUsers() {
		ApiResponse apiResponse = this.userService.getAllUsers();
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponse> getSingleUserById(@PathVariable Long userId) {
		ApiResponse apiResponse = this.userService.getUserById(userId);
		return ResponseEntity.ok(apiResponse);
	}
	
}
