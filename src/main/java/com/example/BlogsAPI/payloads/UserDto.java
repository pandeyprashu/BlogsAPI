package com.example.BlogsAPI.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {


    private int id;

    @NotEmpty
    @Size(min = 4,message = "Name should be more than 4 characters")
    private String name;

    @Email(message = "Not Valid")
    @NotEmpty
    private String email;

    @NotEmpty(message = "Cannot be empty")
    private String password;

    @NotEmpty
    private String about;
}
