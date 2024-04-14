package com.example.BlogsAPI.services;

import com.example.BlogsAPI.payloads.CategoryDto;
import com.example.BlogsAPI.payloads.CategoryResponse;

import java.util.List;

public interface CategoryService  {
    //Create
    public CategoryDto createCategory(CategoryDto categoryDto);


    //Update
     CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    //Delete
     void deleteCategory(Integer categoryId);
    //Get
     CategoryDto getCategory(Integer categoryId);

    //GetAll
     CategoryResponse getCategories(Integer pageSize,Integer pageNumber);
}
