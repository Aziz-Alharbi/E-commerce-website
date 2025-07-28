package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    //2- Create Category Class: • id (must not be
    //empty).
    //• name (must not be empty, have to be more than 3 length long).

    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = 3)
    private String name;
}
