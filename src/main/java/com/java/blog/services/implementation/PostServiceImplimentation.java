package com.java.blog.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.java.blog.dtos.ApiResponse;
import com.java.blog.dtos.PostDto;
import com.java.blog.dtos.PostResponse;
import com.java.blog.entities.Category;
import com.java.blog.entities.Post;
import com.java.blog.entities.User;
import com.java.blog.exceptions.ResourceNotFoundException;
import com.java.blog.repositories.CategoryRepository;
import com.java.blog.repositories.PostRepository;
import com.java.blog.repositories.UserRepository;
import com.java.blog.services.IPostService;

@Service
@PropertySource("classpath:/messages/api_response_messages.properties")
public class PostServiceImplimentation implements IPostService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private Environment environment;

	@Override
	public ApiResponse createPost(PostDto postDto, Long category_Id, Long user_Id) {
		Post post = this.modelMapper.map(postDto, Post.class);
		User user = this.userRepository.findById(user_Id).orElseThrow(() -> new ResourceNotFoundException("User", "user id", user_Id));
		Category category = this.categoryRepository.findById(user_Id).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", category_Id));
		post.setUser(user);
		post.setCategory(category);
		post.setImageName("default.png");
		Post createdPost = this.postRepository.save(post);
		PostDto createdPostDto = this.modelMapper.map(createdPost, PostDto.class);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("post.creation.successful"), createdPostDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse updatePost(PostDto postDto, Long postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		Post updatedPost = this.postRepository.save(post);
		PostDto updatedPostDto = this.modelMapper.map(updatedPost, PostDto.class);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty("post.updation.successful"), updatedPostDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse deletePost(Long postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepository.delete(post);
		ApiResponse apiResponse = new ApiResponse(environment.getProperty(environment.getProperty("post.deletion.successful")), null, true);
		return apiResponse;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepository.findAll(page);
		List<Post> postList = pagePost.getContent();
		PostResponse postResponse = new PostResponse();
		List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastpage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public ApiResponse getPostbyId(Long postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		ApiResponse apiResponse = new ApiResponse("Post details of post id : "+postId, postDto, true);
		return apiResponse;
	}

	@Override
	public ApiResponse getPostByUser(Long userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		List<Post> postList = this.postRepository.findByUser(user);
		List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse("List of post of particular user", postDtoList, true);
		return apiResponse;
	}

	@Override
	public ApiResponse getPostByCategory(Long categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> postList = this.postRepository.findByCategory(category);
		List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse("List of post of particular category", postDtoList, true);
		return apiResponse;
	}

	@Override
	public ApiResponse searchPostByTitle(String keyword) {
		List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse("Searched post", postDtos, true);
		return apiResponse;
	}

	@Override
	public ApiResponse searchPostByContent(String keyword) {
		List<Post> posts = this.postRepository.searchByContent("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		ApiResponse apiResponse = new ApiResponse("Searched post", postDtos, true);
		return apiResponse;
	}

}
