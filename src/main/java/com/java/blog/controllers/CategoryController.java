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
import com.java.blog.dtos.CategoryDto;
import com.java.blog.services.ICategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		ApiResponse apiResponse = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {
		ApiResponse apiResponse = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
		ApiResponse apiResponse = this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> getCategory(@PathVariable Long categoryId) {
		ApiResponse apiResponse = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse> getCategories() {
		ApiResponse apiResponse = this.categoryService.getCategories();
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
