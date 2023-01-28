package com.vvi.btb.service.impl;

import com.vvi.btb.constant.CategoryImplConstant;
import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.domain.entity.Category;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.dao.CategoryDao;
import com.vvi.btb.dao.ProductDao;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.vvi.btb.constant.ProductImplConstant.ON;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryDao categoryDao;
    public ProductServiceImpl(ProductRepository productRepository, CategoryDao categoryDao) {
        this.productRepository = productRepository;
        this.categoryDao = categoryDao;
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
            throw new ProductException(ex.getMessage(), ProductImplConstant.PRODUCT_DELETED_SUCCESSFULLY);
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
            productResponse.setProductId(product.get().getId());
            productResponse.setProductName(product.get().getProductName());
            productResponse.setProductDescription(product.get().getProductDescription());
            productResponse.setProductPrice(product.get().getProductPrice());
            productResponse.setWeight(product.get().getWeight());
            productResponse.setQuantity(product.get().getQuantity());
            productResponse.setFeatured(product.get().isFeatured());
            productResponse.setProductImageUrl(product.get().getProductImageUrl());
            productResponse.setCategoryName(product.get().getCategory().getCategoryName());
            productResponse.setActive(product.get().isActive());
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

    private ProductResponse getProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(savedProduct.getId());
        productResponse.setProductName(savedProduct.getProductName());
        productResponse.setProductImageUrl(savedProduct.getProductImageUrl());
        productResponse.setProductDescription(savedProduct.getProductDescription());
        productResponse.setWeight(savedProduct.getWeight());
        productResponse.setQuantity(savedProduct.getQuantity());
        productResponse.setProductPrice(savedProduct.getProductPrice());
        productResponse.setFeatured(savedProduct.isFeatured());
        productResponse.setActive(savedProduct.isActive());
        productResponse.setAvgStarRating(savedProduct.getAverageRating());
        productResponse.setCategoryName(savedProduct.getCategory().getCategoryName());
        return productResponse;
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
