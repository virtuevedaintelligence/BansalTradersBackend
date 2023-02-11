package com.vvi.btb.service.abs;

import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.RatingException;

import java.util.Optional;

public interface RatingService {
    RatingResponse postRating(RatingRequest ratingRequest, Optional<ProductResponse> product, UserResponse user) throws RatingException;
    RatingResponse updateRating(Long ratingId, RatingRequest ratingRequest, UserResponse user) throws RatingException;

    boolean deleteRating(Long reviewId) throws RatingException;
}
