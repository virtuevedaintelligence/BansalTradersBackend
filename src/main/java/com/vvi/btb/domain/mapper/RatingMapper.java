package com.vvi.btb.domain.mapper;

import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public record RatingMapper() implements Function<Review, RatingResponse> {
    @Override
    public RatingResponse apply(Review review) {
        return new RatingResponse(
                review.getId(),
                review.getReviewBy(),
                review.getStarRating(),
                review.getReviewDescription(),
                review.getReviewDate(),
                review.getLocation());
    }
}
