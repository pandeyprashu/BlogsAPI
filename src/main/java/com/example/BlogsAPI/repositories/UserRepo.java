package com.example.BlogsAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BlogsAPI.entities.User;

public interface UserRepo extends JpaRepository<User,Integer> {

}
