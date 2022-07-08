package com.java.blog.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private Long categoryId;
	
	@NotEmpty(message = "Category title must not be empty")
	@Size(min = 3, message = "Minimum size of category title is 3")
	private String categoryTitle;
	
	@NotEmpty(message = "Category description must not be empty")
	@Size(min = 5, message = "Minimum size of category description is 5")
	private String categoryDescription;
	
}
