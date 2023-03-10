package com.vvi.btb.domain.mapper.product;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.ProductInformation;
import com.vvi.btb.domain.entity.Review;
import com.vvi.btb.domain.entity.User;
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
public record ProductResponseMapperFavorite(RatingMapper ratingMapper,
                                            ProductInformationResponseMapper productInformationResponseMapper)
        implements Function<ProductFav, ProductResponse> {

    @Override
    public ProductResponse apply(ProductFav productFav) {

        return new ProductResponse(
                productFav.getProduct().getId(),
                productFav.getProduct().getProductName(),
                productFav.getProduct().getProductImageUrl(),
                productFav.getProduct().getProductDescription(),
                productFav.getProduct().isFeatured(),
                productFav.getProduct().isActive(),
                favorite(productFav.product, productFav.userId),
                productFav.getProduct().getCategory().getCategoryName(),
                productFav.getProduct().getCategory().getCategoryType(),
                productFav.getProduct().getAverageRating(),
                of(productFav.getProduct().getProductInformation()),
                of(productFav.getProduct().getReviews())
        );
    }

    public List<RatingResponse> of(Optional<List<Review>> reviews) {
        if (reviews.isPresent()) {
            return reviews.map(re -> re.stream().map(ratingMapper).collect(Collectors.toList())).orElse(null);
        }
        return null;
    }

    public List<ProductInformationResponse> of(List<ProductInformation> productInformation) {
        if (!productInformation.isEmpty()) {
            return productInformation.stream().map(productInformationResponseMapper).collect(Collectors.toList());
        }
        return null;
    }

    private boolean favorite(Product product, Long userId) {
        boolean favorite = false;
        if (userId != null) {
            for (User user : product.getUsers()) {
                for (Product userProduct : user.getProducts()) {
                    if (product.getId().equals(userProduct.getId())) {
                        return true;
                    }
                }
            }
        }
        return favorite;
    }

}
