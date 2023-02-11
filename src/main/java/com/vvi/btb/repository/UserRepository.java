package com.vvi.btb.repository;

import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.dao.UserDao;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.exception.domain.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public record UserRepository (UserDao userDao) {

    public Optional<User> findUserById(Long id){
        Optional<User> userDetail = userDao.findById(id);
        if(userDetail.isPresent()){
            return userDetail;
        }else{
            log.info(String.valueOf(UserImplConstant.USER_NOT_FOUND));
        }
        return null;
    }
    public Optional<User> findUserByContactNumber(Long contactNumber){
        Optional<User> user = userDao.findByContactNumber(contactNumber);
        if(user.isPresent()){
            return user;
        }else{
            log.info(String.valueOf(UserImplConstant.USER_NOT_FOUND));
        }
        return Optional.empty();
    }

    public Optional<User> findUserByUserName(String userName){
        Optional<User> user = userDao.findUserByUserName(userName);
        if(user.isPresent()){
            return user;
        }else{
            log.info(String.valueOf(UserImplConstant.USER_NOT_FOUND));
        }
        return null;
    }

    public User save(User user) throws UserException {
        try{
           return userDao.save(user);
        }
        catch (Exception ex){
            throw new UserException(UserImplConstant.USER_CREATION_EXCEPTION,ex.getMessage());
        }
    }
}
