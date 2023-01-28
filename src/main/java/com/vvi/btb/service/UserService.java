package com.vvi.btb.service;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.response.UserResponse;

public interface UserService {

    UserResponse findUserById(Long id);

}
