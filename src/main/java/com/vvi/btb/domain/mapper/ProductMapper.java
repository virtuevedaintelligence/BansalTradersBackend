package com.vvi.btb.domain.mapper;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public record ProductMapper(RatingMapper ratingMapper) implements Function<Product, ProductResponse> {

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
                product.getAverageRating(),
                product.getReviews().stream().map(ratingMapper).collect(Collectors.toList())
        );
    }
}
