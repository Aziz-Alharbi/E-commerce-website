package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    //• id (must not be empty).
    //• productId (must not be empty).
    //• merchantId (must not be empty).
    //• stock (must not be empty, have to be more than 10 at start).

    @NotEmpty
    private String id;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String merchantId;

    @NotNull
    @Min(10)
    private int stock;
}
