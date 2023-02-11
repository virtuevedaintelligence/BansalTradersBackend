package com.vvi.btb.service.abs;

import com.vvi.btb.domain.entity.User;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.response.OTPResponse;

import java.util.Optional;

public interface OTPService {
    Optional<OTPResponse> sendOTP(long number);
    boolean verifyOTP(UserOTPRequest userOTPRequest);

}
