package com.example.BlogsAPI.controllers;

import com.example.BlogsAPI.payloads.APIResponse;
import com.example.BlogsAPI.payloads.CategoryDto;
import com.example.BlogsAPI.payloads.CategoryResponse;
import com.example.BlogsAPI.repositories.CategoryRepository;
import com.example.BlogsAPI.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto createcategoryDto=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createcategoryDto, HttpStatus.CREATED);
    }


    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto,@PathVariable("categoryId")Integer categoryId){
        CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable("categoryId")Integer cid){
        this.deleteCategory(cid);
        return new ResponseEntity<APIResponse>(new APIResponse("User Deleted",false),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = "1",required = false)Integer pageSize
    ){
        return ResponseEntity.ok(this.categoryService.getCategories(pageSize,pageNumber));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer cid){
        CategoryDto categoryDto=this.categoryService.getCategory(cid);
        return ResponseEntity.ok(categoryDto);
    }
}
