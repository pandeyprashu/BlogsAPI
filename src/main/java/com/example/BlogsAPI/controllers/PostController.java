package com.example.BlogsAPI.controllers;

import com.example.BlogsAPI.payloads.APIResponse;
import com.example.BlogsAPI.payloads.PostDto;
import com.example.BlogsAPI.payloads.PostResponse;
import com.example.BlogsAPI.services.FileService;
import com.example.BlogsAPI.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

   @Value("${project.image}")
    private String path;

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


    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image")MultipartFile file,
                                                     @PathVariable Integer postId) throws IOException {
       String fileName= this.fileService.uploadImage(path,file);
       PostDto postDto=this.postService.getPostById(postId);
       postDto.setImageName(fileName);
      PostDto updatedPost= this.postService.updatePost(postDto,postId);
      return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

    @GetMapping(value = "/post/images/{fileName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveFile(@PathVariable("fileName")String fileName, HttpServletResponse response) throws IOException {

        InputStream resource=this.fileService.getResource(path,fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());



    }


}
