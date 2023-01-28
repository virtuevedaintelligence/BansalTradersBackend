package com.vvi.btb.dao;

import com.vvi.btb.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepo extends JpaRepository<Review, Long> {
}
