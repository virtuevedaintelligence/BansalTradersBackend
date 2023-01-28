package com.vvi.btb.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductRating {
    private String productName;
    private long totalReviews;
    private List<RatingDetail> ratingDetail;
}
