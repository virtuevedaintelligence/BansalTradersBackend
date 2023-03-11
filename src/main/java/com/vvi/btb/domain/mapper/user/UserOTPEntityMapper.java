package com.vvi.btb.domain.mapper.user;

import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public record UserOTPEntityMapper(PasswordEncoder passwordEncoder) implements Function<UserOTPRequest, User> {
    @Override
    public User apply(UserOTPRequest userOTPRequest) {
        return User
                .builder()
                .firstName(String.valueOf(userOTPRequest.getNumber()))
                .lastName(String.valueOf(userOTPRequest.getNumber()))
                .userName(String.valueOf(userOTPRequest.getNumber()))
                .password(passwordEncoder.encode(String.valueOf(userOTPRequest.getNumber())))
                .contactNumber(Long.parseLong(userOTPRequest.getNumber()))
                .roles(UserImplConstant.USER)
                .build();
    }
}
