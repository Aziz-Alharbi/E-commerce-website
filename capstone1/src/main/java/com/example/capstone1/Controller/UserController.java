package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User deletion failed!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User update failed!"));
    }



    @PostMapping("/buy")
    public ResponseEntity buyProduct( @RequestParam String userId,@RequestParam String productId,@RequestParam String merchantId) {
        ApiResponse response = userService.buyProduct(userId, productId, merchantId);
        if (!response.getResponse().equals("Purchase successful")) {
            return ResponseEntity.status(400).body(new ApiResponse( "Purchase failed"));
        }
        return ResponseEntity.status(200).body(new ApiResponse( "Purchase successful"));
    }


    @PostMapping("/refund")
    public ResponseEntity<?> refundProduct( @RequestParam String userId, @RequestParam String productId, @RequestParam String merchantId) {
        ApiResponse response = userService.refundProduct(userId, productId, merchantId);
        if (!response.getResponse().equals("Refund successful")) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/check-discount")
    public ResponseEntity<?> checkDiscount( String productId) {
        ApiResponse response = userService.checkProductDiscount(productId);
        return ResponseEntity.status(200).body(response);
    }


    @PostMapping("/replace")
    public ResponseEntity<?> replaceProduct( @RequestParam String userId,
                                             @RequestParam String oldProductId,
                                             @RequestParam String merchantId,
                                             @RequestParam String newProductId) {
        ApiResponse response = userService.replaceProduct(userId, oldProductId, merchantId, newProductId);
        if (!response.getResponse().equals("Replace successful")) {
            return ResponseEntity.status(400).body(response);
        }
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/approve-request")
    public ResponseEntity<?> approveRequest(@RequestParam String userId, @RequestParam boolean approve) {
        ApiResponse response = userService.processApproval(userId, approve);
        return ResponseEntity.status(200).body(response);
    }



}
