package com.example.BlogsAPI.repositories;

import com.example.BlogsAPI.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
