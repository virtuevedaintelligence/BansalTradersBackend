package com.vvi.btb.constant;

import java.util.function.Supplier;

public class UserImplConstant {
    public static Supplier USER_NOT_FOUND = () -> UserImplConstant.USER_NOT_FOUND;
    public static String USER_OTP_GENERATED = "OTP Generated Successfully";
    public static String USER_NOT_OTP_GENERATED = "OTP Generation Failed";
}
