package com.vvi.btb.service.impl;

import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.mapper.product.ProductEntityMapper;
import com.vvi.btb.domain.mapper.product.ProductResponseMapper;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductRating;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingDetail;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.service.abs.ProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public record ProductServiceImpl(ProductRepository productRepository,
                                ProductResponseMapper productResponseMapper,
                                ProductEntityMapper productEntityMapper) implements ProductService {


    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) throws ProductException, CategoryException {
        return getProductResponse(productRepository.save(productEntityMapper.apply(productRequest)));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) throws ProductException, CategoryException {
        try{
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
           Product prod = product.get();
           return getProductResponse(productRepository.save(extractedProduct(productRequest, prod)));
        }
        }catch (Exception ex){
            throw new ProductException(ex.getMessage(),ProductImplConstant.PRODUCT_UPDATE_ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) throws ProductException {
        try{
            productRepository.deleteById(id);
        }
        catch (Exception ex){
            throw new ProductException(ex.getMessage(), ProductImplConstant.PRODUCT_DELETE_ERROR_MESSAGE);
        }
        return true;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        productRepository.findAll().forEach(product -> productResponses.add(getProductResponse(product)));
        return productResponses;
    }

    @Override
    public Optional<ProductResponse> getProductByName(String productName) throws ProductException {
        Optional<Product> product = productRepository.findByProductName(productName);
        if(product.isPresent()){
            return Optional.ofNullable(productResponseMapper.apply(product.get()));
        }
        else {
            log.info(ProductImplConstant.PRODUCT_NOT_FOUND);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductResponse> getProductDetail(Long id) {
        Optional<Product> productDetail = productRepository.findById(id);
        if(productDetail.isPresent()){
          return Optional.ofNullable(getProductResponse(productDetail.get()));
        }else{
            log.info(ProductImplConstant.PRODUCT_NOT_FOUND);
        }
        return Optional.empty();
    }

    @Override
    public ProductRating getProductRatings(Optional<ProductResponse> productResponse) {
       if(productResponse.isPresent()){
           String productName = productResponse.get().productName();
           long totalReviews = productResponse.get().ratingResponse().size();
           List<RatingDetail> ratingDetails = productResponse.get().ratingResponse()
                   .stream().collect(Collectors.groupingBy(RatingResponse::starRating, Collectors.counting()))
                   .entrySet()
                   .stream()
                   .map(product -> new RatingDetail(product.getKey(), product.getValue()))
                   .toList();
           return new ProductRating(productName,totalReviews,ratingDetails);
       }
       return null;
    }

    private ProductResponse getProductResponse(Product product) {
        return productResponseMapper.apply(product);
    }


    @SneakyThrows
    private Product extractedProduct(ProductRequest productRequest, Product prod) {
        prod.setProductName(productRequest.getProductName());
        prod.setProductDescription(productRequest.getProductDescription());
        prod.setProductImageUrl(productRequest.getProductImageUrl());
        prod.setWeight(productRequest.getWeight());
        prod.setProductPrice(productRequest.getProductPrice());
        prod.setActive(productEntityMapper.setActive(productRequest));
        prod.setFeatured(productEntityMapper.setFeatured(productRequest));
        prod.setCategory(productEntityMapper.setCategory(productRequest));
        prod.setCategoryName(productRequest.getCategoryName());
        return prod;
    }
}
