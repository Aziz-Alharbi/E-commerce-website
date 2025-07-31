package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    //6- Create endpoint for getting and adding and deleting updating a Product.


    private final CategoryService categoryService;
    private final ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public Product getProduct(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product) {
        if (!categoryService.existsById(product.getCategoryID())) {
            throw new RuntimeException("Category not found");
        }
        products.add(product);
    }

    public boolean deleteProduct(String id) {
        return products.removeIf(p -> p.getId().equals(id));
    }

    public boolean updateProduct(String id, Product product) {
        if (!categoryService.existsById(product.getCategoryID())) {
            throw new RuntimeException("Category not found");
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                return true;
            }
        }
        return false;
    }

    public boolean setProductDiscount(String productId, double discount) {
        for (Product p : products) {
            if (p.getId().equals(productId)) {
                p.setDiscountPercentage(discount);
                return true;
            }
        }
        return false;
    }

    public boolean existsById(String id) {
        return products.stream().anyMatch(p -> p.getId().equals(id));
    }



}
