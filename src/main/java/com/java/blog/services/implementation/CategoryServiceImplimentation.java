package com.java.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.blog.dtos.CategoryDto;
import com.java.blog.entities.Category;
import com.java.blog.exceptions.ResourceNotFoundException;
import com.java.blog.repositories.CategoryRepository;
import com.java.blog.services.ICategoryService;

@Service
public class CategoryServiceImplimentation implements ICategoryService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category createdCategory = categoryRepository.save(category);
		return this.categoryToCategoryDto(createdCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		return this.categoryToCategoryDto(category);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		this.categoryRepository.delete(category);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
								.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
		return this.categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categoriesList = this.categoryRepository.findAll();
		return categoriesList.stream().map(category -> this.categoryToCategoryDto(category)).collect(Collectors.toList());
	}
	
	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		return this.modelMapper.map(categoryDto, Category.class);
	}
	
	public CategoryDto categoryToCategoryDto(Category category) {
		return this.modelMapper.map(category, CategoryDto.class);
	}
}
