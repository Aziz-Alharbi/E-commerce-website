package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final ProductService productService;


    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(merchantService.getAllMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant deletion failed!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant update failed!"));
    }



    @PutMapping("/setDiscount")
    public ResponseEntity setDiscount( String productId,  double discount) {
        boolean ok = productService.setProductDiscount(productId, discount);
        if (ok) return ResponseEntity.status(200).body(new ApiResponse("Discount set successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Invalid product ID"));
    }

}
