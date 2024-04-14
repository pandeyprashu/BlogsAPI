package com.example.BlogsAPI.controllers;

import com.example.BlogsAPI.payloads.APIResponse;
import com.example.BlogsAPI.payloads.PostDto;
import com.example.BlogsAPI.payloads.PostResponse;
import com.example.BlogsAPI.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts/")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId) {
        // Delegate post creation to the service
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

        // Return the created post in the response with appropriate HTTP status
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts/")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer userId) {

        List<PostDto> postDtoList = this.postService.getPostByUser(userId);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts/")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Integer categoryId) {

        List<PostDto> postDtoList = this.postService.getPostByCategory(categoryId);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/posts/")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue = "0",required = false)Integer pageNumber,
                                                          @RequestParam(value = "pageSize",defaultValue = "1",required = false)Integer pageSize){

        PostResponse postResponse=this.postService.getAllPosts(pageSize, pageNumber);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return ResponseEntity.ok(postDto);


    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId)
    {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new APIResponse("User Deleted Successfully",false),HttpStatus.OK);
    }

    @PutMapping("post/{postId}/")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId,
                                              @RequestBody PostDto postDto){
        PostDto updatedPost=this.postService.updatePost(postDto,postId);
        return  ResponseEntity.ok(updatedPost);
    }


    //Search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword")String keyword){

        return ResponseEntity.ok(this.postService.searchPosts(keyword));

    }

}
