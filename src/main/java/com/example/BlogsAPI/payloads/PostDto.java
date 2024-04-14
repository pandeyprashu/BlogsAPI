package com.example.BlogsAPI.payloads;

import com.example.BlogsAPI.entities.Category;
import com.example.BlogsAPI.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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


}
