package com.vvi.btb.controller;


import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.constant.ReviewImplConstant;
import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.request.RatingRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.service.ProductService;
import com.vvi.btb.service.RatingService;
import com.vvi.btb.service.UserService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/ratings/")
@Slf4j
public class RatingResource {

    private ProductService productService;
    private UserService userService;
    private Response response;
    private RatingService ratingService;

    public RatingResource(ProductService productService, UserService userService,
                          Response response, RatingService ratingService) {
        this.productService = productService;
        this.userService = userService;
        this.response = response;
        this.ratingService = ratingService;
    }

    @PostMapping("/postReview")
    public ResponseEntity<HttpResponse> postReview(@RequestBody RatingRequest ratingRequest) {

        ProductResponse product = productService.getProductDetail(ratingRequest.getProductId());

        if(product == null){
            return response.response(HttpStatus.NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND, product);
        }
        UserResponse user = null;
        if(ratingRequest.getUserId() != null) {
          user  = userService.findUserById(ratingRequest.getUserId());
        }

        RatingResponse ratingResponse = ratingService.postRating(ratingRequest, product, user);
        return response.response(OK, ReviewImplConstant.REVIEW_ADDED_SUCCESSFULLY, ratingResponse);
    }
}
