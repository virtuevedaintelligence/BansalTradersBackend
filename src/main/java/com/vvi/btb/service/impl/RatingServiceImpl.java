package com.vvi.btb.service.impl;

import com.vvi.btb.constant.CommonImplConstant;
import com.vvi.btb.constant.ReviewImplConstant;
import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.mapper.RatingMapper;
import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.RatingException;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.repository.RatingRepository;
import com.vvi.btb.repository.UserRepository;
import com.vvi.btb.service.abs.RatingService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public record RatingServiceImpl(ProductRepository productRepository,
                                UserRepository userRepository,
                                RatingRepository ratingRepository,
                                RatingMapper ratingMapper) implements RatingService {

    @Override
    public RatingResponse postRating(RatingRequest ratingRequest,
                                     Optional<ProductResponse> product, UserResponse user) throws RatingException {
        Review reviewCreate = new Review();
        Review review = ratingRepository.save(buildReview(reviewCreate,ratingRequest));
        return ratingMapper.apply(review);
    }

    @Override
    public RatingResponse updateRating(Long ratingId, RatingRequest ratingRequest, UserResponse user)
            throws RatingException {
        Review review = ratingRepository.findReviewById(ratingId);
        if(review == null){
            throw new RatingException(ReviewImplConstant.REVIEW_NOT_FOUND, CommonImplConstant.PLEASE_CONTACT_ADMIN);
        }
        Review reviewUpdated = ratingRepository.save(buildReview(review,ratingRequest));
        return ratingMapper.apply(reviewUpdated);
    }

    private Review buildReview(Review review, RatingRequest ratingRequest) {
        review.setReviewBy(ratingRequest.getReviewBy());
        review.setReviewDate(new Date());
        review.setReviewDescription(ratingRequest.getReviewDescription());
        review.setStarRating(ratingRequest.getStarRating());
        review.setLocation(ratingRequest.getLocation());
        review.setProduct(productRepository.getProductDetail(ratingRequest.getProductId()));
        Optional<User> user = Optional.empty();
        if(ratingRequest.getUserId() != null) {
            user = userRepository.findUserById(ratingRequest.getUserId());
        }
        if(user.isPresent()){
            review.setUser(user.get());
        }
        return review;
    }

    @Override
    public boolean deleteRating(Long id) throws RatingException {
        return ratingRepository.deleteById(id);
    }
}
