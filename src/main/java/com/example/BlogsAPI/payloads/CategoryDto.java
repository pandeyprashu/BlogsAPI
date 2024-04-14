package com.example.BlogsAPI.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private int id;

    @NotEmpty
    private String title;


    @NotEmpty
    private String description;
}
