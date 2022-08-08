package com.java.blog.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.blog.configurations.PaginationConstants;
import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.PostDto;
import com.java.blog.dtos.PostResponse;
import com.java.blog.services.IPostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private IPostService postService;
	
	@PostMapping("/category/{categoryId}/user/{userId}/post")
	public ResponseEntity<ApiResponse> createPost(
			@RequestBody PostDto postDto, 
			@PathVariable Long categoryId, 
			@PathVariable Long userId ) {
		ApiResponse apiResponse = this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> updatePost(
			@RequestBody PostDto postDto,
			@PathVariable Long postId) {
		ApiResponse apiResponse = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
		ApiResponse apiResponse = this.postService.deletePost(postId);
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = PaginationConstants.PAGENUMBER_DEFAULT_VALUE, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = PaginationConstants.PAGESIZE_DEFAULT_VALUE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = PaginationConstants.SORTBY_DEFAULT_VALUE, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = PaginationConstants.SORTDIRECTION_DEFAULT_VALUE, required = false) String sortDirection) {
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
		return ResponseEntity.ok(allPost);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> getPostById(@PathVariable Long postId) {
		ApiResponse apiResponse = this.postService.getPostbyId(postId);
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<ApiResponse> getPostByUser(@PathVariable Long userId) {
		ApiResponse apiResponse = this.postService.getPostByUser(userId);
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<ApiResponse> getPostByCategory(@PathVariable Long categoryId) {
		ApiResponse apiResponse = this.postService.getPostByCategory(categoryId);
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/post/search/title/{keyword}")
	public ResponseEntity<ApiResponse> searchPostByTitle(@PathVariable String keyword) {
		ApiResponse apiResponse = this.postService.searchPostByTitle(keyword);
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/post/search/content/{keyword}")
	public ResponseEntity<ApiResponse> searchPostByContent(@PathVariable String keyword) {
		ApiResponse apiResponse = this.postService.searchPostByContent(keyword);
		return ResponseEntity.ok(apiResponse);
	}
	
}
