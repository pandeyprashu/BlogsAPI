package com.example.BlogsAPI.controllers;

import com.example.BlogsAPI.entities.Comment;
import com.example.BlogsAPI.payloads.APIResponse;
import com.example.BlogsAPI.payloads.CommentDto;
import com.example.BlogsAPI.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment/")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @GetMapping("/")
    private ResponseEntity<List<CommentDto>> getComments(){
        List<CommentDto> commentList=this.commentService.getComments();

        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
    @PostMapping("/add/{postId}/comments")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,
                                                 @PathVariable Integer postId){
        CommentDto createdComment=this.commentService.addComment(commentDto,postId);

        return new ResponseEntity<>(createdComment,HttpStatus.OK);

    }

    @DeleteMapping("/delete/{commentId}")
    public  ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);

        return new ResponseEntity<>(new APIResponse("User Deleted Successfully",true),HttpStatus.OK);

    }

}
