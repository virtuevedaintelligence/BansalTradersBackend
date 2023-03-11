package com.vvi.btb.resource;


import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.ProductRequest;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.repository.UserRepository;
import com.vvi.btb.service.abs.ProductService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/products")
@Slf4j
public class ProductResource {
    private final ProductService productService;
    private final Response response;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public ProductResource(ProductService productService, Response response,
                           ProductRepository productRepository, UserRepository userRepository) {
        this.productService = productService;
        this.response = response;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/createProduct")
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {
        Optional<ProductResponse> product = productService.getProductByNameAndWeight(productRequest.getProductName(),
                                                            productRequest.getWeight());
        if(product.isPresent()){
            return response.response(HttpStatus.CONFLICT,
                    ProductImplConstant.PRODUCT_ALREADY_EXISTS_WITH_NAME_WEIGHT,
                    product.get());
        }
        ProductResponse productResponse = productService.saveProduct(productRequest);
        return response.response(OK, ProductImplConstant.PRODUCT_ADDED_SUCCESSFULLY,productResponse);
    }

    @PutMapping("/updateProduct/{productId}")
  //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HttpResponse> updateProduct(@PathVariable("productId") Long productId,
                                                         @RequestBody ProductRequest productRequest)
            throws ProductException, CategoryException {

        Optional<Product> productDetail = productRepository.findByProductNameAndWeight(productRequest.getProductName(),
                productRequest.getWeight());
        if (!productDetail.isPresent()) {
            Optional<Product> product = productRepository.findById(productId);
            if(!product.isPresent()){
                return response.response(OK, ProductImplConstant.PRODUCT_NOT_FOUND_FOR_UPDATE, productDetail.get());
            }else{
                return updateProductWithDetails(productRequest, product);
            }
        }
        return updateProductWithDetails(productRequest, productDetail);
    }

    private ResponseEntity<HttpResponse> updateProductWithDetails(ProductRequest productRequest, Optional<Product> product) throws ProductException, CategoryException {
        ProductResponse productResponse = productService.updateProduct(product.get(), productRequest);
        if (productResponse == null) {
            return response.response(OK, ProductImplConstant.PRODUCT_UPDATE_ERROR_MESSAGE, productResponse);
        }
        return response.response(OK, ProductImplConstant.PRODUCT_UPDATED_SUCCESSFULLY, productResponse);
    }

    @DeleteMapping("/delete/{productId}")
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
        return response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND,productDetail.get());
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<HttpResponse> getAllProducts(@RequestParam(name = "userId" , required = false) Long userId){
        return response.response(OK,ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productService.getAllProducts(userId));
    }

    @GetMapping("/productRating/{productId}")
  //  @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> getProductRatings(@PathVariable ("productId") Long productId) throws ProductException {
        Optional<ProductResponse> productDetail = productService.getProductDetail(productId);
        if(productDetail.isPresent()){
            return response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND, productDetail.get());
        }
        return response.response(OK,ProductImplConstant.PRODUCT_FETCHED_SUCESSFULLY, productService.getProductRatings(productDetail));
    }

    @GetMapping("/favorite/{productId}/{userId}")
    //  @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<HttpResponse> favoriteProduct(@PathVariable ("productId") Long productId,
                                                        @PathVariable ("userId") Long userId)
            throws ProductException, UserException {
        Optional<ProductResponse> productDetail = productService.getProductDetail(productId);
        if(productDetail.isEmpty()){
            return response.response(NOT_FOUND, ProductImplConstant.PRODUCT_NOT_FOUND, productDetail);
        }
        Optional<User> user = userRepository.findUserById(userId);
        if(user.isEmpty()){
            return response.response(NOT_FOUND, UserImplConstant.USER_MUST_BE_LOGGED_IN, user);
        }
        if(productService.favoriteProduct(productDetail, user)){
          return response.response(OK, ProductImplConstant.PRODUCT_MARKED_FAVORITE,
                    productService.favoriteProduct(productDetail, user));
        }
       return response.response(BAD_GATEWAY, ProductImplConstant.PRODUCT_NOT_MARKED_FAVORITE, productDetail.get());
    }
}
