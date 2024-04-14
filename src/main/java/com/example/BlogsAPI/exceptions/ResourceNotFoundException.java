package com.example.BlogsAPI.exceptions;

// Custom Exception Is created here
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String UserId;
    long fieldValue;

    public ResourceNotFoundException(String resourceName, String userId, long fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName,userId,fieldValue));
        this.resourceName = resourceName;
        UserId = userId;
        this.fieldValue = fieldValue;
    }
}
