package com.example.capstone_jpa.Controller;

import com.example.capstone_jpa.Api.ApiResponse;
import com.example.capstone_jpa.Model.User;
import com.example.capstone_jpa.Service.UserService;
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
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User deletion failed!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id,
                                        @Valid @RequestBody User user,
                                        Errors errors) {
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
    public ResponseEntity<?> buyProduct(@RequestParam Integer userId,
                                        @RequestParam Integer productId,
                                        @RequestParam Integer merchantId) {
        ApiResponse response = userService.buyProduct(userId, productId, merchantId);
        return response.getResponse().equals("Purchase successful")
                ? ResponseEntity.status(200).body(response)
                : ResponseEntity.status(400).body(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundProduct(@RequestParam Integer userId,
                                           @RequestParam Integer productId,
                                           @RequestParam Integer merchantId) {
        ApiResponse response = userService.refundProduct(userId, productId, merchantId);
        return response.getResponse().contains("awaiting admin")
                ? ResponseEntity.status(200).body(response)
                : ResponseEntity.status(400).body(response);
    }

    @GetMapping("/check-discount")
    public ResponseEntity<?> checkDiscount(@RequestParam Integer productId) {
        return ResponseEntity.status(200).body(userService.checkProductDiscount(productId));
    }

    @PostMapping("/replace")
    public ResponseEntity<?> replaceProduct(@RequestParam Integer userId,
                                            @RequestParam Integer oldProductId,
                                            @RequestParam Integer merchantId,
                                            @RequestParam Integer newProductId) {
        ApiResponse response = userService.replaceProduct(userId, oldProductId, merchantId, newProductId);
        return response.getResponse().contains("awaiting admin")
                ? ResponseEntity.status(200).body(response)
                : ResponseEntity.status(400).body(response);
    }

    @PutMapping("/approve-request")
    public ResponseEntity<?> approveRequest(@RequestParam Integer userId,
                                            @RequestParam boolean approve) {
        return ResponseEntity.status(200).body(userService.processApproval(userId, approve));
    }
}
