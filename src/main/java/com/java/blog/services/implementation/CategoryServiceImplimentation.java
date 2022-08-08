package com.java.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.CategoryDto;
import com.java.blog.entities.Category;
import com.java.blog.exceptions.ResourceNotFoundException;
import com.java.blog.repositories.CategoryRepository;
import com.java.blog.services.ICategoryService;

@Service
@PropertySource("classpath:/messages/api_response_messages.properties")
public class CategoryServiceImplimentation implements ICategoryService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	Environment environment;

	@Override
	public ApiResponse createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category createdCategory = categoryRepository.save(category);
		CategoryDto categoryDtoResponse = this.categoryToCategoryDto(createdCategory);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("category.creation.successful"), categoryDtoResponse, true);
		return apiResponse;
	}

	@Override
	public ApiResponse updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		CategoryDto CategoryDtoResponse = this.categoryToCategoryDto(category);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("category.updation.successful"), CategoryDtoResponse, true);
		return apiResponse;
	}

	@Override
	public ApiResponse deleteCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepository.delete(category);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("category.deletion.successful"), null, true);
		return apiResponse;
	}

	@Override
	public ApiResponse getCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		CategoryDto categoryDto = this.categoryToCategoryDto(category);
		ApiResponse apiResponse = new ApiResponse("Category detail of id : "+categoryId, categoryDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse getCategories() {
		List<Category> categoriesList = this.categoryRepository.findAll();
		List<CategoryDto> categoryList = categoriesList.stream().map(category -> this.categoryToCategoryDto(category)).collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("category.list"), categoryList, true);
		return apiResponse;
	}
	
	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}
	
	public CategoryDto categoryToCategoryDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}
}
