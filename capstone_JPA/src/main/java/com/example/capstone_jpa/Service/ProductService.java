package com.example.capstone_jpa.Service;

import com.example.capstone_jpa.Model.Product;
import com.example.capstone_jpa.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addProduct(Product product) {
        if (!categoryService.existsById(product.getCategoryID())) {
            throw new RuntimeException("Category not found");
        }
        productRepository.save(product);
    }

    public boolean deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return false;
        productRepository.delete(product);
        return true;
    }

    public boolean updateProduct(Integer id, Product updatedProduct) {
        if (!categoryService.existsById(updatedProduct.getCategoryID())) {
            throw new RuntimeException("Category not found");
        }

        Product oldProduct = productRepository.findById(id).orElse(null);
        if (oldProduct == null) return false;

        oldProduct.setName(updatedProduct.getName());
        oldProduct.setPrice(updatedProduct.getPrice());
        oldProduct.setCategory(updatedProduct.getCategory());
        oldProduct.setCategoryID(updatedProduct.getCategoryID());
        oldProduct.setDiscountPercentage(updatedProduct.getDiscountPercentage());
        oldProduct.setDeliveryFee(updatedProduct.getDeliveryFee());

        productRepository.save(oldProduct);
        return true;
    }

    public boolean setProductDiscount(Integer id, double discount) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return false;

        product.setDiscountPercentage(discount);
        productRepository.save(product);
        return true;
    }

    public boolean existsById(Integer id) {
        return productRepository.existsById(id);
    }
}
