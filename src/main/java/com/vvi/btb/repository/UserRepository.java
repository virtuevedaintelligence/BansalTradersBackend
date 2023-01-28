package com.vvi.btb.repository;

import com.vvi.btb.constant.ProductImplConstant;
import com.vvi.btb.dao.UserDao;
import com.vvi.btb.domain.entity.Product;
import com.vvi.btb.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public record UserRepository (UserDao userDao) {

    public Optional<User> findUserById(Long id){
        Optional<User> productDetail = userDao.findById(id);
        if(productDetail.isPresent()){
            return productDetail;
        }else{
            log.info(ProductImplConstant.PRODUCT_NOT_FOUND);
        }
        return null;
    }
}
