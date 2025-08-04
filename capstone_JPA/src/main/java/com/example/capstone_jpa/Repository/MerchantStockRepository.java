package com.example.capstone_jpa.Repository;

import com.example.capstone_jpa.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantStockRepository extends JpaRepository<MerchantStock, Integer> {
}
