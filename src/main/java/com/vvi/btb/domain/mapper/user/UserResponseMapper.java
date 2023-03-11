package com.vvi.btb.domain.mapper.user;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.service.impl.JwtService;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public record UserResponseMapper(JwtService jwtService) implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getEmail(),// Profile Image
                generateToken(user.getUserName()));
    }

    private String generateToken(String userName){
       return jwtService.generateToken(userName);
    }

}
