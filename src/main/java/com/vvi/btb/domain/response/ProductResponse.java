package com.vvi.btb.domain.response;

import java.util.List;

public record ProductResponse(
     long productId,
     String productName,
     String productImageUrl,
     String productDescription,
     int productPrice,
     int quantity, // 10 packets 20 packets
     int weight, // 250gm 500gm
     boolean isFeatured,
     boolean isActive,
     String categoryName,
     String categoryType,
     String avgStarRating,
     List<RatingResponse> ratingResponse
    ){
}
