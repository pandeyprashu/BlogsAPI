package com.example.BlogsAPI.services;

import java.util.List;

import com.example.BlogsAPI.payloads.UserDto;
import com.example.BlogsAPI.payloads.UserResponse;

public interface UserService {
   
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    UserResponse getAllUsers(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);
    void deleteUser(Integer userId);

}
