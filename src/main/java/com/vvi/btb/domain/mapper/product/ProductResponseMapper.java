package com.vvi.btb.domain.mapper.product;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.ProductInformation;
import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.mapper.RatingMapper;
import com.vvi.btb.domain.response.ProductInformationResponse;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public record ProductResponseMapper(RatingMapper ratingMapper,
                                    ProductInformationResponseMapper productInformationResponseMapper)
        implements Function<Product, ProductResponse> {

    @Override
    public ProductResponse apply(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getProductImageUrl(),
                product.getProductDescription(),
                product.isFeatured(),
                product.isActive(),
                product.getCategory().getCategoryName(),
                product.getCategory().getCategoryType(),
                product.getAverageRating(),
                of(product.getProductInformation()),
                of(product.getReviews())
        );
    }

    public List<RatingResponse> of(Optional<List<Review>> reviews){
        if(reviews.isPresent()){
            return reviews.map(re -> re.stream().map(ratingMapper).collect(Collectors.toList())).orElse(null);
        }
        return null;
    }
    public List<ProductInformationResponse> of(List<ProductInformation> productInformation){
        if(!productInformation.isEmpty()){
            return productInformation.stream().map(productInformationResponseMapper).collect(Collectors.toList());
        }
        return null;
    }
}
