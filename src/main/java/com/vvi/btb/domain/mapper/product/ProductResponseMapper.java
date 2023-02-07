package com.vvi.btb.domain.mapper.product;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.mapper.RatingMapper;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public record ProductResponseMapper(RatingMapper ratingMapper) implements Function<Product, ProductResponse> {

    @Override
    public ProductResponse apply(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductImageUrl(),
                product.getProductDescription(),
                product.getProductPrice(),
                product.getQuantity(),
                product.getWeight(),
                product.isFeatured(),
                product.isActive(),
                product.getCategory().getCategoryName(),
                product.getCategory().getCategoryType(),
                product.getAverageRating(),
                of(product.getReviews())
        );
    }

    public List<RatingResponse> of(Optional<List<Review>> reviews){
        if(reviews.isPresent()){
            return reviews.map(re -> re.stream().map(ratingMapper).collect(Collectors.toList())).orElse(null);
        }
        return null;
    }
}
