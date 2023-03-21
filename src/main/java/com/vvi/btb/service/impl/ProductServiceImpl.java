package com.vvi.btb.service.impl;

import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.dao.ProductInformationDao;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.ProductInformation;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.mapper.product.ProductEntityMapper;
import com.vvi.btb.domain.mapper.product.ProductFav;
import com.vvi.btb.domain.mapper.product.ProductResponseMapper;
import com.vvi.btb.domain.mapper.product.ProductResponseMapperFavorite;
import com.vvi.btb.domain.request.product.ProductRequest;
import com.vvi.btb.domain.request.product.ProductRequests;
import com.vvi.btb.domain.response.ProductRating;
import com.vvi.btb.domain.response.ProductResponse;
import com.vvi.btb.domain.response.RatingDetail;
import com.vvi.btb.domain.response.RatingResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.repository.ProductRepository;
import com.vvi.btb.repository.UserRepository;
import com.vvi.btb.service.abs.ProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.vvi.btb.constant.ProductImplConstant.PRODUCT_NOT_FOUND;
import static com.vvi.btb.constant.ProductImplConstant.PRODUCT_NOT_MARKED_FAVORITE;

@Service
@Slf4j
public record ProductServiceImpl(ProductRepository productRepository,
                                 ProductInformationDao productInformationDao,
                                 ProductResponseMapper productResponseMapper,
                                 ProductEntityMapper productEntityMapper,
                                 ProductResponseMapperFavorite productResponseMapperFavorite,
                                 UserRepository userRepository) implements ProductService {


    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) throws ProductException, CategoryException {
        Optional<Product> product = productRepository.findByProductName(productRequest.getProductName());
        if(product.isPresent()){
            return productResponseMapper.apply(addProductInformation(productRequest, product));
        }else{
            return getProductResponse(productRepository.save(productEntityMapper.apply(productRequest)));
        }
    }

    @Override
    public boolean saveProducts(ProductRequests productRequests) throws ProductException, CategoryException, IOException {
        List<Product> products = productRequests.getProductRequests()
                .stream()
                .map(productEntityMapper::apply)
                .collect(Collectors.toList());
        return productRepository.saveAll(products);
    }

    @Override
    public ProductResponse updateProduct(Product product, ProductRequest productRequest)
            throws ProductException, CategoryException {
        try{
            if(productRequest.getWeight() != product.getWeight()){
                return productResponseMapper.apply(addProductInformation(productRequest, Optional.ofNullable(product)));
            }else {
                return getProductResponse(productRepository.save(extractedProduct(productRequest, product)));
            }
        }
        catch (Exception ex){
            throw new ProductException(ex.getMessage(),ProductImplConstant.PRODUCT_UPDATE_ERROR_MESSAGE);
        }
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
    public List<ProductResponse> getAllProducts(Long userId) {
        List<ProductResponse> productResponses = new ArrayList<>();
        productRepository.findAll().forEach(product -> productResponses.add(getProductResponseGetAll(product, userId)));
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
    public Optional<ProductResponse> getProductByNameAndWeight(String productName, int weight) throws ProductException {
        Optional<Product> product = productRepository.findByProductNameAndWeight(productName,weight);
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
    public Optional<ProductResponse> getProductDetailToUpdate(String productName, int weight) {
        Optional<Product> productDetail = productRepository.findByProductNameAndWeight(productName,weight);
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

    @Override
    public boolean favoriteProduct(Optional<ProductResponse> productDetail, Optional<User> userOptional)
            throws ProductException, UserException {

        boolean favorite = false;
        try {
            Product product = productRepository.findById(productDetail.get().productId())
                    .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND, PRODUCT_NOT_MARKED_FAVORITE));
            Set<Product> products = new HashSet<>();
            products.add(product);
            User user = userOptional.get();
            Set<User> users = new HashSet<>();
            users.add(user);
            user.setProducts(products);
            product.setUsers(users);
            userRepository.save(user);
            favorite = true;
        } catch (Exception | ProductException ex) {
            throw new ProductException(PRODUCT_NOT_FOUND, PRODUCT_NOT_MARKED_FAVORITE);
        }
        return favorite;
    }

    private ProductResponse getProductResponse(Product product) {
        return productResponseMapper.apply(product);
    }

    private ProductResponse getProductResponseGetAll(Product product, Long userId) {
        return productResponseMapperFavorite.apply(new ProductFav(product,userId));
    }


    @SneakyThrows
    private Product extractedProduct(ProductRequest productRequest, Product product) {
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductImageUrl(productRequest.getProductImageUrl());
        product.setActive(productEntityMapper.setActive(productRequest));
        product.setFeatured(productEntityMapper.setFeatured(productRequest));
        product.setCategory(productEntityMapper.setCategory(productRequest));
        product.setCategoryName(productRequest.getCategoryName());
        product.setProductInformation(updateProductInformation(productRequest,product));
        return product;
    }

    private Product addProductInformation(ProductRequest productRequest, Optional<Product> product) {
        Map<Integer, List<ProductInformation>> products = product.get().getProductInformation()
                .stream()
                .collect(Collectors.groupingBy(ProductInformation::getWeight));
        if(products.containsKey(productRequest.getWeight())){
            ProductInformation productInformation = setProductInformation(productRequest,
                    Objects.requireNonNull(products.get(productRequest.getWeight()).stream().findAny().orElse(null)));
            products.put(productRequest.getWeight(), List.of(productInformation));
            productInformationDao.save(products.get(productRequest.getWeight()).get(0));
        }else{
            ProductInformation productInformation = new ProductInformation();
            setProductInformation(productRequest, productInformation);
            productInformation.setProduct(product.get());
            products.put(productRequest.getWeight(),List.of(productInformation));
            productInformationDao.save(productInformation);
        }
        product.get().setProductInformation(products.get(productRequest.getWeight()));
        return productRepository.save(product.get());
    }

    private ProductInformation setProductInformation(ProductRequest productRequest, ProductInformation information) {
        information.setWeight(productRequest.getWeight());
        information.setProductPrice(productRequest.getProductPrice());
        information.setProductPriceWithOutDiscount(productRequest.getProductPriceWithoutDiscount());
        information.setQuantity(productRequest.getQuantity());
        return information;
    }

    private List<ProductInformation> updateProductInformation(ProductRequest productRequest, Product product) {
        List<ProductInformation> productInformationList = product.getProductInformation();
        for (ProductInformation productInformation : productInformationList) {
            if(productInformation.getWeight()==productRequest.getWeight()){
                productInformation.setQuantity(productRequest.getQuantity());
                productInformation.setProductPrice(productRequest.getProductPrice());
                productInformation.setWeight(productRequest.getWeight());
                productInformation.setProductPriceWithOutDiscount(productRequest.getProductPriceWithoutDiscount());
            }
            productInformationDao.save(productInformation);
        }
        return productInformationList;
    }
}
