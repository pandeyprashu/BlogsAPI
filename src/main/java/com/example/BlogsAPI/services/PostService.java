package com.example.BlogsAPI.services;

import com.example.BlogsAPI.entities.Post;
import com.example.BlogsAPI.payloads.PostDto;
import com.example.BlogsAPI.payloads.PostResponse;

import java.util.List;

public interface PostService {

        PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

        PostDto updatePost(PostDto postDto,Integer postId);


        void deletePost(Integer postId);

        PostResponse getAllPosts(Integer pageSize, Integer pageNumber);


        PostDto getPostById(Integer postId);

        List<PostDto> getPostByCategory(Integer categoryId);

        List<PostDto> getPostByUser(Integer userId);

        List<PostDto> searchPosts(String keyword);
}
