package com.java.blog.services;

import java.util.List;

import com.java.blog.dtos.CategoryDto;

public interface ICategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
	
	void deleteCategory(Long categoryId);
	
	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> getCategories();
	
}
