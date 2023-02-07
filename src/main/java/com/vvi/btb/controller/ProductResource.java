package com.vvi.btb.controller;


import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.service.ProductService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductResource {
    private final ProductService productService;
    private final Response response;
    public ProductResource(ProductService productService, Response response) {
        this.productService = productService;
        this.response = response;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {
        log.info(productRequest.toString());
        ProductResponse product = productService.getProductByName(productRequest.getProductName());
        if(product != null){
            return response.response(HttpStatus.CONFLICT, ProductImplConstant.PRODUCT_ALREADY_EXISTS, product);
        }
        ProductResponse productResponse = productService.saveProduct(productRequest);
        return response.response(OK, ProductImplConstant.PRODUCT_ADDED_SUCCESSFULLY,productResponse);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<HttpResponse> updateProduct(@PathVariable("productId") Long productId,
                                                         @RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {

        ProductResponse product = productService.getProductDetail(productId);
        if (product == null) {
            return  response.response(OK, ProductImplConstant.PRODUCT_ALREADY_EXISTS,product);
        }
        ProductResponse productResponse = productService.updateProduct(productId,productRequest);
        if (productResponse == null) {
            return  response.response(OK, ProductImplConstant.PRODUCT_UPDATE_ERROR_MESSAGE,productResponse);
        }
        return response.response(OK, ProductImplConstant.PRODUCT_UPDATED_SUCCESSFULLY,productResponse);
    }
    /*
    @PatchMapping("/patchProduct/{productId}")
    public ResponseEntity<HttpResponse> updatePatchProduct(@PathVariable("productId") Long productId,
                                                             @RequestBody Map<String,Object> fields) throws ProductException, CategoryException {
        ProductResponse product = productService.getProductDetail(productId);
        if (product == null) {
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
        fields.forEach((key,value) -> {
            Field field = ReflectionUtils.findField(ProductResponse.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field,product,value);
        });
//        ProductResponse productResponse = productService.updateProduct(productId, createProductRequest(product));
        ProductResponse productResponse= null;
        if (productResponse == null) {
            return new ResponseEntity<>(productResponse, HttpStatus.NOT_FOUND);
        }
        return response.response(OK, ProductImplConstant.PRODUCT_ADDED_SUCCESSFULLY,productResponse);
    }
    */


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable("productId") Long productId) throws ProductException {
        productService.deleteProduct(productId);
        return response.response(OK, ProductImplConstant.PRODUCT_DELETED_SUCCESSFULLY,null);
    }

    @GetMapping("/productDetail/{productId}")
    public ResponseEntity<HttpResponse> getProductDetail(@PathVariable ("productId") Long productId) throws ProductException{
        ProductResponse productDetail = productService.getProductDetail(productId);
        if(productDetail == null){
            return  response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND,productDetail);
        }
        return  response.response(OK, ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY,productDetail);
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getProductDetails(){
        return response.response(OK,ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productService.getAllProducts());
    }

    /*
    private ProductRequest createProductRequest(ProductResponse productResponse){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductDescription(productResponse.getProductDescription());
        productRequest.setProductName(productResponse.getProductName());
        productRequest.setProductPrice(productResponse.getProductPrice());
        productRequest.setQuantity(productResponse.getQuantity());
        productRequest.setFeatured(productResponse.isFeatured() ? "true" : "false");
        productRequest.setIsactive(productResponse.isActive() ? "true" : "false");
        productRequest.setWeight(productResponse.getWeight());
        productRequest.setCategoryName(productResponse.getCategoryName());
        productRequest.setProductImageUrl(productResponse.getProductImageUrl());
        return productRequest;
    }
    */

    @GetMapping("/productRating/{productId}")
    public ResponseEntity<HttpResponse> getProductRatings(@PathVariable ("productId") Long productId) throws ProductException {
        ProductResponse productDetail = productService.getProductDetail(productId);
        if(productDetail == null){
            return response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND,productDetail);
        }
        return response.response(OK,ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productService.getProductRatings(productDetail));
    }

}
