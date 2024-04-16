package com.example.BlogsAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Entity
@Table(name="comments")
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

}
