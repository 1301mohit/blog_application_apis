package com.java.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
