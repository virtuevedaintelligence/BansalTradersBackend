package com.vvi.btb.domain.response;

public record UserResponse(
         String firstName,
         String lastName,
         String username,
         String email,
         long contactNumber,
         String profileImageUrl,
         String password
) {
}
