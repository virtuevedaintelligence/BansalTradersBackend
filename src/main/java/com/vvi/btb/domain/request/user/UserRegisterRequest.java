package com.vvi.btb.domain.request.user;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private long contactNumber;
    private String password;
}
