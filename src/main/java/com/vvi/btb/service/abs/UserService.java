package com.vvi.btb.service.abs;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.UserException;

import java.util.Optional;

public interface UserService {

    UserResponse findUserById(Long id) throws UserException;

    Optional<UserResponse> findUserByContactNumber(Long contactNumber);

    UserResponse save(Object userRequest) throws UserException;
    Optional<UserResponse> findUserByName(String userName) throws UserException;
}
