package com.vvi.btb.service.impl;

import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.mapper.RatingMapper;
import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.dao.RatingRepo;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.repository.UserRepository;
import com.vvi.btb.service.RatingService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public record RatingServiceImpl(ProductRepository productRepository,
                                UserRepository userRepository,
                                RatingRepo ratingRepo,
                                RatingMapper ratingMapper) implements RatingService {

    @Override
    public RatingResponse postRating(RatingRequest ratingRequest, ProductResponse product, UserResponse user) {
        Review review = ratingRepo.save(buildReview(ratingRequest));
        return ratingMapper.apply(review);
    }

    private Review buildReview(RatingRequest ratingRequest) {
        Review review = new Review();
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
    public boolean deleteRating(Long id) {
        return productRepository.deleteById(id);
    }

    @Override
    public RatingResponse updateRating(RatingRequest ratingRequest, ProductResponse product, UserResponse user) {
        return null;
    }
}
