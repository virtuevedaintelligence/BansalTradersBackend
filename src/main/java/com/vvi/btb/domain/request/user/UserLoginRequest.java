package com.vvi.btb.domain.request.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
