package com.vvi.btb.controller;


import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductResource {
    private final ProductService productService;
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {
        log.info(productRequest.toString());
        ProductResponse product = productService.getProductByName(productRequest.getProductName());
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.CONFLICT);
        }
        ProductResponse productResponse = productService.saveProduct(productRequest);
        return new ResponseEntity<>(productResponse, OK);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Long productId,
                                                         @RequestBody ProductRequest productRequest) throws ProductException, CategoryException {

        ProductResponse product = productService.getProductDetail(productId);
        if (product == null) {
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
        ProductResponse productResponse = productService.updateProduct(productId,productRequest);
        if (productResponse == null) {
            return new ResponseEntity<>(productResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productResponse, OK);
    }
    @PatchMapping("/patchProduct/{productId}")
    public ResponseEntity<ProductResponse> updatePatchProduct(@PathVariable("productId") Long productId,
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
        ProductResponse productResponse = productService.updateProduct(productId, createProductRequest(product));
        if (productResponse == null) {
            return new ResponseEntity<>(productResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productResponse, OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable("productId") Long productId) throws ProductException {
        productService.deleteProduct(productId);
        return response(OK, ProductImplConstant.PRODUCT_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/productDetail/{productId}")
    public ResponseEntity<ProductResponse> getProductDetail(@PathVariable ("productId") Long productId) throws ProductException{
        ProductResponse productDetail = productService.getProductDetail(productId);
        if(productDetail == null){
            return new ResponseEntity<>(productDetail, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDetail, OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProductDetails(){
        return new ResponseEntity<>(productService.getAllProducts(), OK);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

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
}
