package com.vvi.btb.service;

import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.RatingException;

public interface RatingService {
    RatingResponse postRating(RatingRequest ratingRequest, ProductResponse product, UserResponse user) throws RatingException;
    RatingResponse updateRating(Long ratingId, RatingRequest ratingRequest, ProductResponse product, UserResponse user) throws RatingException;

    boolean deleteRating(Long reviewId) throws RatingException;
}
