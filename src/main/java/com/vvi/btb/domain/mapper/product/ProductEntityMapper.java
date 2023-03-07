package com.vvi.btb.domain.mapper.product;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.dao.CategoryDao;
import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.ProductInformation;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.exception.domain.CategoryException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.vvi.btb.constant.ProductImplConstant.ON;

@Component
public record ProductEntityMapper(CategoryDao categoryDao) implements Function<ProductRequest, Product>{

    @SneakyThrows
    @Override
    public Product apply(ProductRequest productRequest) {
        Product product = Product
                .builder()
                .productName(productRequest.getProductName())
                .productImageUrl(productRequest.getProductImageUrl())
                .productDescription(productRequest.getProductDescription())
                .weight(productRequest.getWeight())
                .isFeatured(setFeatured(productRequest))
                .isActive(setActive(productRequest))
                .categoryName(productRequest.getCategoryName())
                .category(setCategory(productRequest))
                .build();
        product.setProductInformation(buildProductInformation(productRequest,product));
        return product;
    }

    public Category setCategory(ProductRequest productRequest) throws CategoryException {
        Optional<Category> category = categoryDao.findByCategoryName(productRequest.getCategoryName());
        if(category.isPresent()){
            return category.get();
        }else{
            throw new CategoryException(CategoryImplConstant.CATEGORY_NOT_FOUND, CategoryImplConstant.PLEASE_CONTACT_ADMIN);
        }
    }
    private List<ProductInformation> buildProductInformation(ProductRequest productRequest, Product product){
        return List.of(ProductInformation.builder()
                .quantity(productRequest.getQuantity())
                .productPrice(productRequest.getProductPrice())
                .weight(productRequest.getWeight())
                .product(product)
                .build());
    }
    public boolean setFeatured(ProductRequest productRequest) {
        return productRequest.getFeatured().equals(ON);
    }

    public boolean setActive(ProductRequest productRequest){
        return productRequest.getIsactive().equals(ON);
    }
}
