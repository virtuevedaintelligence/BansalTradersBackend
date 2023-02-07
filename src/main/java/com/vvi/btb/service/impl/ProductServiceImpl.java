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
import com.vvi.btb.dao.CategoryDao;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.service.ProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;
    private final ProductEntityMapper productEntityMapper;
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductResponseMapper productResponseMapper,
                              ProductEntityMapper productEntityMapper) {
        this.productRepository = productRepository;
        this.productResponseMapper = productResponseMapper;
        this.productEntityMapper = productEntityMapper;
    }

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
    public ProductResponse getProductByName(String productName) throws ProductException {
        Optional<Product> product = productRepository.findByProductName(productName);
        ProductResponse productResponse = null;
        if(product.isPresent()){
            return productResponseMapper.apply(product.get());
        }
        else {
            log.info(ProductImplConstant.PRODUCT_NOT_FOUND);
        }
        return productResponse;
    }

    @Override
    public ProductResponse getProductDetail(Long id) {
        Optional<Product> productDetail = productRepository.findById(id);
        if(productDetail.isPresent()){
          return getProductResponse(productDetail.get());
        }else{
            log.info(ProductImplConstant.PRODUCT_NOT_FOUND);
        }
        return null;
    }

    @Override
    public ProductRating getProductRatings(ProductResponse productResponse) {
       String productName = productResponse.productName();
       long totalReviews = productResponse.ratingResponse().size();
       List<RatingDetail> ratingDetails = productResponse.ratingResponse()
                .stream().collect(Collectors.groupingBy(RatingResponse::starRating, Collectors.counting()))
                .entrySet()
                .stream()
                .map(product -> new RatingDetail(product.getKey(), product.getValue()))
                .toList();
       return new ProductRating(productName,totalReviews,ratingDetails);
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
