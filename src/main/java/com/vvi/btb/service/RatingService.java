package com.vvi.btb.service;

import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;

public interface RatingService {
    RatingResponse postRating(RatingRequest ratingRequest, ProductResponse product, UserResponse user);
    boolean deleteRating(Long id);
    RatingResponse updateRating(RatingRequest ratingRequest, ProductResponse product, UserResponse user);
}
