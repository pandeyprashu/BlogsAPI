package com.example.BlogsAPI.services.impl;

import com.example.BlogsAPI.entities.Comment;
import com.example.BlogsAPI.entities.Post;
import com.example.BlogsAPI.exceptions.ResourceNotFoundException;
import com.example.BlogsAPI.payloads.CommentDto;
import com.example.BlogsAPI.payloads.PostDto;
import com.example.BlogsAPI.repositories.CommentRepository;
import com.example.BlogsAPI.repositories.PostRepository;
import com.example.BlogsAPI.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto addComment(CommentDto commentDto, Integer postId) {

        Post post=this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("PostId","Id",postId));
        Comment comment=this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        Comment savedComment=this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public List<CommentDto> getComments() {
        List<Comment> commentList=this.commentRepository.findAll();
        return commentList.stream()
                .map(comment -> this.modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("CommentId","Id",commentId));

        this.commentRepository.delete(comment);

    }

    @Override
    public CommentDto updateComment() {
        return null;
    }
}
