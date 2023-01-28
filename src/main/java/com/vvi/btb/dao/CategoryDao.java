package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}