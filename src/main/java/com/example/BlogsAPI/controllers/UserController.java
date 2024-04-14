package com.example.BlogsAPI.controllers;

import com.example.BlogsAPI.payloads.APIResponse;
import com.example.BlogsAPI.payloads.UserDto;
import com.example.BlogsAPI.payloads.UserResponse;
import com.example.BlogsAPI.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto,
    @PathVariable("userId") Integer userId){
           UserDto updateduser= this.userService.updateUser(userDto,userId);
           return ResponseEntity.ok(updateduser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable("userId") Integer uid){
        this.deleteUser(uid);
        //We can create API response class for returning the value
       // return new ResponseEntity<>(Map.of("message","User Deleted successfully"),HttpStatus.OK);
        return new ResponseEntity<APIResponse>(new APIResponse("User Deleted",false),HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<UserResponse> getAllUsers(@RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = "1",required = false)Integer pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = "id",required = false)String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir){


        UserResponse userResponse=this.userService.getAllUsers(pageSize,pageNumber,sortBy,sortDir);
        return new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer uid){
        UserDto user=this.userService.getUserById(uid);
        return ResponseEntity.ok(user);
    }

}
