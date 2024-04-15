package com.example.BlogsAPI.repositories;

import com.example.BlogsAPI.entities.Category;
import com.example.BlogsAPI.entities.Post;
import com.example.BlogsAPI.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    //For Searching implementation
    List<Post> findByTitleContaining(String title);
}
