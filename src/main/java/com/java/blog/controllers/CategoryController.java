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
import com.java.blog.entities.Category;
import com.java.blog.services.ICategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		ApiResponse apiResponse = new ApiResponse("Category created successfully", createdCategoryDto, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		ApiResponse apiResponse = new ApiResponse("Category updated successfully", updatedCategoryDto, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
		this.categoryService.deleteCategory(categoryId);
		ApiResponse apiResponse = new ApiResponse("Category deleted successfully", null, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> getCategory(@PathVariable Long categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
		ApiResponse apiResponse = new ApiResponse("Category details", categoryDto, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse> getCategories() {
		List<CategoryDto> categoryList = this.categoryService.getCategories();
		ApiResponse apiResponse = new ApiResponse("Category details", categoryList, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
