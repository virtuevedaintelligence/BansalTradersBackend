package com.vvi.btb.domain.mapper.user;

import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.user.UserRegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public record UserRegisterEntityMapper(PasswordEncoder passwordEncoder) implements Function<UserRegisterRequest, User> {
    @Override
    public User apply(UserRegisterRequest userRegisterRequest) {
        return User
                .builder()
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .userName(generateUsername(userRegisterRequest))
                .contactNumber(userRegisterRequest.getContactNumber())
                .email(userRegisterRequest.getEmail())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .roles(UserImplConstant.ADMIN)
                .build();
    }

    public String generateUsername(UserRegisterRequest userRegisterRequest) {
        return userRegisterRequest.getFirstName().substring(0, 3) +
               userRegisterRequest.getLastName().substring(0, 3);
    }
}
