package com.java.blog.services;

import java.util.List;

import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.CategoryDto;

public interface ICategoryService {

	ApiResponse createCategory(CategoryDto categoryDto);
	
	ApiResponse updateCategory(CategoryDto categoryDto, Long categoryId);
	
	ApiResponse deleteCategory(Long categoryId);
	
	ApiResponse getCategory(Long categoryId);
	
	ApiResponse getCategories();
	
}
