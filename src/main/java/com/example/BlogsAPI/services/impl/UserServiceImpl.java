package com.example.BlogsAPI.services.impl;

import java.util.List;
import com.example.BlogsAPI.entities.User;
import com.example.BlogsAPI.exceptions.ResourceNotFoundException;
import com.example.BlogsAPI.payloads.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.BlogsAPI.payloads.UserDto;
import com.example.BlogsAPI.repositories.UserRepo;
import com.example.BlogsAPI.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        this.repository.save(user);
        return this.usertoDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.repository.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        
        User updatedUser=this.repository.save(user);
        
        return this.usertoDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.repository.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
        
         return this.usertoDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageSize,Integer pageNumber,String sortBy,String sortDir) {

        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();

        }else{
            sort=Sort.by(sortBy).descending();
        }

        Pageable page= PageRequest.of(pageNumber,pageSize, sort);


        Page<User> userPage=this.repository.findAll(page);

        List<User> userList=userPage.getContent();

        UserResponse userResponse=new UserResponse();

        List<UserDto> userDtoList=userList.stream().map(post->this.modelMapper.map(post,UserDto.class)).toList();

        userResponse.setContent(userDtoList);

        userResponse.setPageNumber(userPage.getNumber());
        userResponse.setPageSize(userPage.getSize());
        userResponse.setTotalElements(userPage.getTotalElements());
        userResponse.setTotalPages(userPage.getTotalPages());
        userResponse.setLastPage(userPage.isLast());

        return userResponse;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.repository.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
        this.repository.delete(user);

    }

    private User dtoToUser(UserDto userDto){


//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        return this.modelMapper.map(userDto,User.class);
    }

    private UserDto usertoDto(User user){

//        UserDto dto=new UserDto();
//
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setPassword(user.getPassword());
//        dto.setAbout(user.getAbout());



        return this.modelMapper.map(user,UserDto.class);
    }
    
}
