package com.vvi.btb.controller;

import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.constant.ReviewImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.RatingException;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.service.abs.ProductService;
import com.vvi.btb.service.abs.RatingService;
import com.vvi.btb.service.abs.UserService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ratings/")
@Slf4j
public class RatingResource {
    private final ProductService productService;
    private final UserService userService;
    private final Response response;
    private final RatingService ratingService;

    public RatingResource(ProductService productService,
                          UserService userService,
                          Response response,
                          RatingService ratingService) {
        this.productService = productService;
        this.userService = userService;
        this.response = response;
        this.ratingService = ratingService;
    }

    @PostMapping("/postReview")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> postReview(@RequestBody RatingRequest ratingRequest) throws RatingException, UserException {

        Optional<ProductResponse> product = productService.getProductDetail(ratingRequest.getProductId());
        if(!product.isPresent()){
            return response.response(HttpStatus.NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND, product);
        }
        UserResponse user = null;
        if(ratingRequest.getUserId() != null) {
          user  = userService.findUserById(ratingRequest.getUserId());
        }

        RatingResponse ratingResponse = ratingService.postRating(ratingRequest, product, user);
        return response.response(OK, ReviewImplConstant.REVIEW_ADDED_SUCCESSFULLY, ratingResponse);
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> deleteReview(@PathVariable("reviewId") Long reviewId)
            throws RatingException {
        ratingService.deleteRating(reviewId);
        return response.response(OK, ReviewImplConstant.REVIEW_DELETED_SUCCESSFULLY,null);
    }
    @PutMapping("/updateReview/{reviewId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> updateReview(@PathVariable("reviewId") Long reviewId,
                                                     @RequestBody RatingRequest ratingRequest) throws RatingException, UserException {

        Optional<ProductResponse> product = productService.getProductDetail(ratingRequest.getProductId());

        if(!product.isPresent()){
            return response.response(HttpStatus.NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND, product);
        }
        UserResponse user = null;
        if(ratingRequest.getUserId() != null) {
            user  = userService.findUserById(ratingRequest.getUserId());
        }

        RatingResponse ratingResponse = ratingService.updateRating(reviewId, ratingRequest, user);
        return response.response(OK, ReviewImplConstant.REVIEW_ADDED_SUCCESSFULLY, ratingResponse);
    }
}
