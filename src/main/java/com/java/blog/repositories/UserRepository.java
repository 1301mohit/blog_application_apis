package com.java.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
