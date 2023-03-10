package com.vvi.btb.service.abs;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductRating;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.exception.domain.UserException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponse saveProduct(ProductRequest productRequest) throws ProductException, CategoryException;
    ProductResponse updateProduct(Product product, ProductRequest productRequest) throws ProductException, CategoryException;
    boolean deleteProduct(Long id) throws ProductException;
    List<ProductResponse> getAllProducts(Long userId);
    Optional<ProductResponse> getProductByName(String productName) throws ProductException;
    Optional<ProductResponse> getProductByNameAndWeight(String productName, int weight) throws ProductException;
    Optional<ProductResponse> getProductDetail(Long id);
    Optional<ProductResponse> getProductDetailToUpdate(String productName, int weight);
    ProductRating getProductRatings(Optional<ProductResponse> productResponse);
    boolean favoriteProduct(Optional<ProductResponse> productDetail, Optional<User> user) throws ProductException, UserException;
}
