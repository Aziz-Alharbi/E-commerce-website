package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(categoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully!"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category deletion failed!"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdated = categoryService.updateCategory(id, category);
        if (isUpdated) {
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully!"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category update failed!"));
    }
}
