package com.vvi.btb.repository;

import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.constant.ReviewImplConstant;
import com.vvi.btb.dao.RatingRepo;
import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.exception.domain.RatingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public record RatingRepository(RatingRepo ratingRepo) {
    public boolean deleteById(Long id) throws RatingException {
        boolean isDelete;
        try{
            ratingRepo.deleteById(id);
            isDelete =  true;
        }
        catch (Exception ex){
            isDelete = false;
            throw new RatingException(ex.getMessage(), ReviewImplConstant.REVIEW_DELETE_ERROR_MESSAGE);
        }
        return isDelete;
    }

    public Review save(Review review) throws RatingException {
        try{
         return ratingRepo.save(review);
        }
        catch (Exception ex){
            throw new RatingException(ex.getMessage(), ReviewImplConstant.REVIEW_ADD_ERROR_MESSAGE);
        }
    }

    public Review findReviewById(Long ratingId) throws RatingException {
        try{
            return ratingRepo.findById(ratingId).orElse(null);
        }
        catch (Exception ex){
            throw new RatingException(ex.getMessage(), ReviewImplConstant.REVIEW_ADD_ERROR_MESSAGE);
        }
    }
}
