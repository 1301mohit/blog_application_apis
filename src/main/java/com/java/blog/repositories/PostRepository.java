package com.java.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.blog.entities.Category;
import com.java.blog.entities.Post;
import com.java.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByContent(@Param("key") String content);
}
