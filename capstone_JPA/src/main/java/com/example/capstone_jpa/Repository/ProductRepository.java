
package com.example.capstone_jpa.Repository;

import com.example.capstone_jpa.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
