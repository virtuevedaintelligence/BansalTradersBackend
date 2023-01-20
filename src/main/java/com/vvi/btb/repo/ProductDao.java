package com.vvi.btb.repo;

import com.vvi.btb.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDao extends JpaRepository<Product,Long> {
    Optional<Product> findByProductName(String productName);
}
