package com.vvi.btb.domain.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserOTPRequest {

    @NotNull
    @Pattern(regexp = "^\\d{10}$")
    private String number;
//    @Pattern(regexp = "^\\d{6}$")
    private String otp;
}
