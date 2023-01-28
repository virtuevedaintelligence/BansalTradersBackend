package com.vvi.btb.service.impl;

import com.vvi.btb.domain.mapper.UserMapper;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.repository.UserRepository;
import com.vvi.btb.service.UserService;
import org.springframework.stereotype.Service;
import static com.vvi.btb.constant.UserImplConstant.USER_NOT_FOUND;

@Service
public record UserServiceImpl(UserRepository userRepository, UserMapper userMapper) implements UserService {

    @Override
    public UserResponse findUserById(Long id) {
        return userRepository.findUserById(id).map(userMapper).orElseThrow(USER_NOT_FOUND);
    }
}
