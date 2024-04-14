package com.example.BlogsAPI.services.impl;

import com.example.BlogsAPI.entities.Category;
import com.example.BlogsAPI.exceptions.ResourceNotFoundException;
import com.example.BlogsAPI.payloads.CategoryDto;
import com.example.BlogsAPI.payloads.CategoryResponse;
import com.example.BlogsAPI.repositories.CategoryRepository;
import com.example.BlogsAPI.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.modelMapper.map(categoryDto, Category.class);
        Category addCategory=this.categoryRepository.save(category);
        return this.modelMapper.map(addCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat=this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        cat.setCategoryTitle(categoryDto.getTitle());
        cat.setCategoryDescription(categoryDto.getDescription());
        cat.setCategoryId(categoryDto.getId());

        Category updatedCategory=this.categoryRepository.save(cat);
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        this.categoryRepository.delete(category);


    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        return this.modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryResponse getCategories(Integer pageSize,Integer pageNumber) {

        Pageable page= PageRequest.of(pageNumber,pageSize);

        Page<Category> categoryPage=this.categoryRepository.findAll(page);

        List<Category> categoryList=categoryPage.getContent();


        List<CategoryDto> categoryDtoList=categoryList.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class)).toList();


        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDtoList);

        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;




    }
}
