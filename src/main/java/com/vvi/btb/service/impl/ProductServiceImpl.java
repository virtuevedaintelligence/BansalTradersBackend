package com.vvi.btb.service.impl;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.mapper.ProductMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.vvi.btb.constant.ProductImplConstant.ON;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryDao categoryDao;
    private final ProductMapper productMapper;
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryDao categoryDao,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryDao = categoryDao;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) throws ProductException, CategoryException {
        Product productToSave = buildProduct(productRequest);
        Product savedProduct = productRepository.save(productToSave);
        ProductResponse productResponse = getProductResponse(savedProduct);
        return productResponse;
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
        productRepository.findAll().stream()
                .forEach(product -> {
                    productResponses.add(getProductResponse(product));
                });
        return productResponses;
    }

    @Override
    public ProductResponse getProductByName(String productName) throws ProductException {
        Optional<Product> product = productRepository.findByProductName(productName);
        ProductResponse productResponse = null;
        if(product.isPresent()){
            return productMapper.apply(product.get());
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
        return productMapper.apply(product);
    }

    private Product buildProduct(ProductRequest productRequest) throws CategoryException {
        Product product = new Product();
        return extractedProduct(productRequest,product);
    }
    private Product extractedProduct(ProductRequest productRequest, Product prod) throws CategoryException {
        prod.setProductName(productRequest.getProductName());
        prod.setProductPrice(productRequest.getProductPrice());
        prod.setProductImageUrl(productRequest.getProductImageUrl());
        prod.setWeight(productRequest.getWeight());
        prod.setProductDescription(productRequest.getProductDescription());
        prod.setQuantity(productRequest.getQuantity());
        setFeaturedAndActive(productRequest, prod);
        Optional<Category> category = categoryDao.findByCategoryName(productRequest.getCategoryName());
        if(category.isPresent()){
            prod.setCategory(category.get());
        }else{
            throw new CategoryException(CategoryImplConstant.CATEGORY_NOT_FOUND, CategoryImplConstant.PLEASE_CONTACT_ADMIN);
        }
        return prod;
    }

    private void setFeaturedAndActive(ProductRequest productRequest, Product prod) {
        if(productRequest.getIsactive().equals(ON)){
            prod.setActive(true);
        }else{
            prod.setActive(false);
        }
        if(productRequest.getFeatured().equals(ON)){
            prod.setFeatured(true);
        }else{
            prod.setFeatured(false);
        }
    }
}
