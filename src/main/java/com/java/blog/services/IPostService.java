package com.java.blog.services;

import java.util.List;

import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.PostDto;
import com.java.blog.dtos.PostResponse;
import com.java.blog.entities.Category;
import com.java.blog.entities.Post;
import com.java.blog.entities.User;

public interface IPostService {

	ApiResponse createPost(PostDto postDto, Long category_Id, Long user_Id);
	
	ApiResponse updatePost(PostDto postDto, Long postId);
	
	ApiResponse deletePost(Long postId);
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
	
	ApiResponse getPostbyId(Long postId);
	
	ApiResponse getPostByUser(Long userId);
	
	ApiResponse getPostByCategory(Long categoryId);
	
	ApiResponse searchPostByTitle(String keyword);
	
	ApiResponse searchPostByContent(String keyword);

}
