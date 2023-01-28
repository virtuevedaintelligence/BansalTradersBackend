package com.vvi.btb.domain.mapper;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getContactNumber(),
                user.getEmail());
    }
}
