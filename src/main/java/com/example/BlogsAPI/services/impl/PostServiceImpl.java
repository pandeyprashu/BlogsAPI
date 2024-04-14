package com.example.BlogsAPI.services.impl;

import com.example.BlogsAPI.entities.Category;
import com.example.BlogsAPI.entities.Post;
import com.example.BlogsAPI.entities.User;
import com.example.BlogsAPI.exceptions.ResourceNotFoundException;
import com.example.BlogsAPI.payloads.PostDto;
import com.example.BlogsAPI.payloads.PostResponse;
import com.example.BlogsAPI.repositories.CategoryRepository;
import com.example.BlogsAPI.repositories.PostRepository;
import com.example.BlogsAPI.repositories.UserRepo;
import com.example.BlogsAPI.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepository categoryRepository;

    // Create a new post
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        // Retrieve the user by userId or throw a ResourceNotFoundException if not found
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

        // Retrieve the category by categoryId or throw a ResourceNotFoundException if not found
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        // Map the PostDto to a Post entity
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        // Save the new post
        Post newPost = this.postRepository.save(post);

        // Map the saved post entity back to a PostDto and return
        return this.modelMapper.map(newPost, PostDto.class);
    }

    // Update an existing post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        // Retrieve the post by postId or throw a ResourceNotFoundException if not found
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        // Update post details with the provided PostDto
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        // Save the updated post
        Post updatedPost = this.postRepository.save(post);

        // Map the updated post entity back to a PostDto and return
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    // Delete a post by postId
    @Override
    public void deletePost(Integer postId) {
        // Retrieve the post by postId or throw a ResourceNotFoundException if not found
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

        // Delete the post
        this.postRepository.delete(post);
    }

    // Retrieve all posts
    @Override
    public PostResponse getAllPosts(Integer pageSize, Integer pageNumber) {


// Creating a Pageable object to specify pagination parameters
        Pageable page = PageRequest.of(pageNumber, pageSize);

// Fetching a page of posts from the repository based on the specified pagination parameters
        Page<Post> postPage = this.postRepository.findAll(page);

// Extracting the list of posts from the fetched page
        List<Post> postList = postPage.getContent();

// Creating a response object to hold paginated post data
        PostResponse postResponse = new PostResponse();

// Mapping each post entity to a PostDto using ModelMapper and collecting into a list
        List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();

// Setting the content of the response object to the list of mapped PostDto objects
        postResponse.setContent(postDtoList);

// Setting pagination-related attributes in the response object
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        // Map each post entity to a PostDto and collect into a list
        return postResponse;
    }

    // Retrieve a post by postId
    @Override
    public PostDto getPostById(Integer postId) {
        // Retrieve the post by postId or throw a ResourceNotFoundException if not found
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        // Map the post entity to a PostDto and return
        return this.modelMapper.map(post, PostDto.class);
    }

    // Retrieve posts by categoryId
    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        // Retrieve the category by categoryId or throw a ResourceNotFoundException if not found
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

        // Retrieve posts by category
        List<Post> postList = this.postRepository.findByCategory(cat);

        // Map each post entity to a PostDto and collect into a list
        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    // Retrieve posts by userId
    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        // Retrieve the user by userId or throw a ResourceNotFoundException if not found
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        // Retrieve posts by user
        List<Post> postList = this.postRepository.findByUser(user);

        // Map each post entity to a PostDto and collect into a list
        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    // Search for posts by keyword (not implemented yet)
    @Override
    public List<Post> searchPosts(String keyword) {
        return null; // Placeholder, not implemented yet
    }
}
