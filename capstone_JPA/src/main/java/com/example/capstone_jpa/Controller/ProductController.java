package com.example.capstone_jpa.Controller;

import com.example.capstone_jpa.Api.ApiResponse;
import com.example.capstone_jpa.Model.Product;
import com.example.capstone_jpa.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors){
        if(errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("Product has been added successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Product has been deleted successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product failed to be deleted!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @Valid @RequestBody Product product, Errors errors){
        if(errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = productService.updateProduct(id, product);
        if (isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Product has been updated successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product failed to be updated!"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id){
        Product input = productService.getProduct(id);
        if(input == null) {
            return ResponseEntity.status(400).body(new ApiResponse("Product has not been found!"));
        }
        return ResponseEntity.status(200).body(input);
    }
}
