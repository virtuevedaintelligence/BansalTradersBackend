package com.vvi.btb.service.impl;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.mapper.user.UserOTPEntityMapper;
import com.vvi.btb.domain.mapper.user.UserRegisterEntityMapper;
import com.vvi.btb.domain.mapper.user.UserResponseMapper;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.request.user.UserRegisterRequest;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.repository.UserRepository;
import com.vvi.btb.service.abs.UserService;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static com.vvi.btb.constant.UserImplConstant.BY;
import static com.vvi.btb.constant.UserImplConstant.USER_NOT_FOUND;

@Service
public record UserServiceImpl(UserRepository userRepository,
                              UserResponseMapper userResponseMapper,
                              UserOTPEntityMapper userOTPEntityMapper,
                              UserRegisterEntityMapper userRegisterEntityMapper
                              ) implements UserService {

    @Override
    public UserResponse findUserById(Long id) throws UserException {
        return userRepository.findUserById(id).map(userResponseMapper)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND, BY + id));
    }

    @Override
    public Optional<UserResponse> findUserByContactNumber(Long contactNumber) {
        return Optional.ofNullable(userRepository
                .findUserByContactNumber(contactNumber).map(userResponseMapper)
                .orElse(null));
    }

    @Override
    public UserResponse save(Object userRequest) throws UserException {
        User user = null;
        if(userRequest instanceof UserOTPRequest userOTPRequest){
            user = userRepository.save(userOTPEntityMapper.apply(userOTPRequest));
        }else if (userRequest instanceof UserRegisterRequest userRegisterRequest){
            user = userRepository.save(userRegisterEntityMapper.apply(userRegisterRequest));
        }
        return userResponseMapper.apply(user);
    }

    @Override
    public Optional<UserResponse> findUserByName(String userName) throws UserException {
        return Optional.ofNullable(userRepository
                .findUserByUserName(userName).map(userResponseMapper)
                .orElse(null));
    }
}
