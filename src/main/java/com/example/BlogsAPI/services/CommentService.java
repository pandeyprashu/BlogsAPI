package com.example.BlogsAPI.services;

import com.example.BlogsAPI.entities.Comment;
import com.example.BlogsAPI.payloads.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentService{


    public CommentDto addComment(CommentDto commentDto,Integer postId);
    public List<CommentDto> getComments();

    public void deleteComment(Integer commentId);

    public CommentDto updateComment();



}
