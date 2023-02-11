package com.vvi.btb.controller;


import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.service.abs.ProductService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {
        Optional<ProductResponse> product = productService.getProductByName(productRequest.getProductName());
        if(product.isPresent()){
            return response.response(HttpStatus.CONFLICT, ProductImplConstant.PRODUCT_ALREADY_EXISTS,
                    product.get());
        }
        ProductResponse productResponse = productService.saveProduct(productRequest);
        return response.response(OK, ProductImplConstant.PRODUCT_ADDED_SUCCESSFULLY,productResponse);
    }

    @PutMapping("/updateProduct/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> updateProduct(@PathVariable("productId") Long productId,
                                                         @RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {

        Optional<ProductResponse> productDetail = productService.getProductDetail(productId);
        if (productDetail.isPresent()) {
            return response.response(OK, ProductImplConstant.PRODUCT_ALREADY_EXISTS, productDetail.get());
        }
        ProductResponse productResponse = productService.updateProduct(productId,productRequest);
        if (productResponse == null) {
            return  response.response(OK, ProductImplConstant.PRODUCT_UPDATE_ERROR_MESSAGE,productResponse);
        }
        return response.response(OK, ProductImplConstant.PRODUCT_UPDATED_SUCCESSFULLY,productResponse);
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable("productId") Long productId) throws ProductException {
        productService.deleteProduct(productId);
        return response.response(OK, ProductImplConstant.PRODUCT_DELETED_SUCCESSFULLY,null);
    }

    @GetMapping("/productDetail/{productId}")
    public ResponseEntity<HttpResponse> getProductDetail(@PathVariable ("productId") Long productId) throws ProductException{
        Optional<ProductResponse> productDetail = productService.getProductDetail(productId);
        if(productDetail.isPresent()){
           return response.response(OK, ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productDetail);
        }
        return  response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND,productDetail.get());
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<HttpResponse> getAllProducts(){
        return response.response(OK,ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productService.getAllProducts());
    }

    @GetMapping("/productRating/{productId}")
    public ResponseEntity<HttpResponse> getProductRatings(@PathVariable ("productId") Long productId) throws ProductException {
        Optional<ProductResponse> productDetail = productService.getProductDetail(productId);
        if(productDetail.isPresent()){
            return response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND, productDetail.get());
        }
        return response.response(OK,ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productService.getProductRatings(productDetail));
    }

}
