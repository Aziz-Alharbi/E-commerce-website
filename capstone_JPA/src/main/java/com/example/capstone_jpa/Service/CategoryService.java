package com.example.capstone_jpa.Service;

import com.example.capstone_jpa.Model.Category;
import com.example.capstone_jpa.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public boolean deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) return false;
        categoryRepository.deleteById(id);
        return true;
    }

    public boolean updateCategory(Integer id, Category updatedCategory) {
        Category oldCategory = categoryRepository.findById(id).orElse(null);
        if (oldCategory == null) return false;

        oldCategory.setName(updatedCategory.getName());
        categoryRepository.save(oldCategory);
        return true;
    }

    public boolean existsById(Integer id) {
        return categoryRepository.existsById(id);
    }
}
