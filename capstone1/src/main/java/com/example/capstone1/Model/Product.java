package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {


    //1- Create Product Class:
    //• id (must not be empty).
    //• name (must not be empty, have to be more than 3 length long).
    //• price (must not be empty, must be positive number).
    //• categoryID (must not be empty).


    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = 3)
    private String name;

    @NotNull
    @Positive
    private double price;

    @NotEmpty
    private String categoryID;

    @NotEmpty
    private String category;

    @NotNull
    private double discountPercentage; // e.g., 0.15 for 15%

    @NotNull
    private double deliveryFee; // default delivery fee, e.g., 20.0

}
