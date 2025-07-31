package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getAllCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public boolean deleteCategory(String id) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                categories.remove(c);
                return true;
            }
        }
        return false;
    }

    public boolean updateCategory(String id, Category category) {
        for (Category c : categories) {
            if (c.getId().equals(id)) {
                int index = categories.indexOf(c);
                categories.set(index, category);
                return true;
            }
        }
        return false;
    }

    public boolean existsById(String id) {
        return categories.stream().anyMatch(c -> c.getId().equals(id));
    }
}
