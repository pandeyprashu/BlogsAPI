package com.example.BlogsAPI.payloads;

import com.example.BlogsAPI.entities.Category;
import com.example.BlogsAPI.entities.Comment;
import com.example.BlogsAPI.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private String title;

    private String content;

    private String imageName;

    private UserDto user;

    private Date addedDate;

    private CategoryDto category;

    private HashSet<Comment> commentSet=new HashSet<>();


}
