package com.vvi.btb.repository;

import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.dao.ProductDao;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.exception.domain.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.vvi.btb.constant.CommonImplConstant.PLEASE_CONTACT_ADMIN;

@Component
@Slf4j
public record ProductRepository(ProductDao productDao){

    public Product getProductDetail(Long id) {
        Optional<Product> productDetail = productDao.findById(id);
        if(productDetail.isPresent()){
            return productDetail.get();
        }else{
            log.info(ProductImplConstant.PRODUCT_NOT_FOUND);
        }
        return null;
    }

    public Product save(Product productToSave) {
        return productDao.save(productToSave);
    }

    public Optional<Product> findById(Long productId) {
        return productDao.findById(productId);
    }

    public boolean deleteById(Long id) {
        productDao.deleteById(id);
        return true;
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public boolean saveAll(List<Product> products) throws ProductException {
        try{
            productDao.saveAllAndFlush(products);
        }
        catch (Exception ex){
            throw new ProductException(ex.getMessage(), PLEASE_CONTACT_ADMIN);
        }
        return true;
    }

    public Optional<Product> findByProductName(String productName) {
        return productDao.findByProductName(productName);
    }
    public Optional<Product> findByProductNameAndWeight(String productName, int weight) {
        return productDao.findByProductNameAndWeight(productName,weight);
    }
}
