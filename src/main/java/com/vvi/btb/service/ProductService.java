package com.vvi.btb.service;

import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.request.CategoryRequest;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;

import java.util.List;

public interface ProductService {

    ProductResponse saveProduct(ProductRequest productRequest) throws ProductException, CategoryException;
    ProductResponse updateProduct(Long id, ProductRequest productRequest) throws ProductException, CategoryException;
    boolean deleteProduct(Long id) throws ProductException;
    List<ProductResponse> getAllProducts();
    ProductResponse getProductByName(String categoryName) throws ProductException;
    ProductResponse getProductDetail(Long id);
}
