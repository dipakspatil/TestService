package com.demo.example.TestService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.example.TestService.dto.Post;

public interface PostRepository extends JpaRepository<Post, String>{

}
