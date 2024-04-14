package com.example.BlogsAPI.repositories;

import com.example.BlogsAPI.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
