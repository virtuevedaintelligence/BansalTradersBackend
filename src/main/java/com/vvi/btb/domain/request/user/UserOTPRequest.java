package com.vvi.btb.domain.request.user;

import lombok.Data;

@Data
public class UserOTPRequest {
    private long number;
    private long otp;
}
