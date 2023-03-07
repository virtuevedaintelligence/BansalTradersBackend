package com.vvi.btb.domain.response;

import java.util.List;

public record ProductResponse(
     long productId,
     String productName,
     String productImageUrl,
     String productDescription,
     boolean isFeatured,
     boolean isActive,
     String categoryName,
     String categoryType,
     String avgStarRating,
     List<ProductInformationResponse> productInformation,
     List<RatingResponse> ratingResponse
    ){
}
