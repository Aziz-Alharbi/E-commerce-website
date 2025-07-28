package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    //• id (must not be empty).
    //• username (must not be empty, have to be more than 5 length long).
    //• password (must not be empty, have to be more than 6 length long, must have
    //characters and digits).
    //• email (must not be empty, must be valid email).
    //• role (must not be empty, have to be in ( “Admin”,”Customer”)).
    //• balance (must not be empty, have to be positive).


    @NotEmpty
    private String id;

    @NotEmpty
    @Size(min = 5)
    private String username;

    @NotEmpty
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Must contain letters and digits")
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(Admin|Customer)$", message = "Must contain letters and digits")
    private String role;

    @NotNull
    @Positive
    private double balance;


    @NotNull
    private boolean canRefund = false;

   // @NotNull(message = "Last product ID is required for refund")
    private String lastProductId;

    // @NotNull(message = "Last merchant ID is required for refund")
    private String lastMerchantId;


    private boolean refundRequested = false;
    private boolean replaceRequested = false;
    private boolean requestApproved = false;
    private String requestType; // "refund" or "replace"
    private String newProductId; // only if replace

}
