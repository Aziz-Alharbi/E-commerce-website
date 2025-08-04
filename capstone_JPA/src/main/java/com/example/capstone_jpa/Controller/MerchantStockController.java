package com.example.capstone_jpa.Controller;

import com.example.capstone_jpa.Api.ApiResponse;
import com.example.capstone_jpa.Model.MerchantStock;
import com.example.capstone_jpa.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(merchantStockService.getAllMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable Integer id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock deletion failed!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Integer id,
                                         @Valid @RequestBody MerchantStock merchantStock,
                                         Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock update failed!"));
    }

    @PutMapping("/addStock")
    public ResponseEntity<?> addStock(@RequestParam Integer merchantId,
                                      @RequestParam Integer productId,
                                      @RequestParam int stock) {
        boolean isUpdated = merchantStockService.addMerch(merchantId, productId, stock);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock update failed!"));
    }
}
