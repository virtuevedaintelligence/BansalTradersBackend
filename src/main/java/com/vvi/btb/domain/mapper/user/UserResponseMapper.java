package com.vvi.btb.domain.mapper.user;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.response.UserResponse;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public record UserResponseMapper() implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getEmail()); // Profile Image
    }
}
