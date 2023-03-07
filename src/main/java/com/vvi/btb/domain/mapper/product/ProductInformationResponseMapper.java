package com.vvi.btb.domain.mapper.product;

import com.vvi.btb.domain.entity.ProductInformation;
import com.vvi.btb.domain.response.ProductInformationResponse;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public record ProductInformationResponseMapper() implements Function<ProductInformation, ProductInformationResponse> {
    @Override
    public ProductInformationResponse apply(ProductInformation productInformation) {
        return new ProductInformationResponse(
                productInformation.getProductPrice(),
                productInformation.getQuantity(),
                productInformation.getWeight()
        );
    }
}
