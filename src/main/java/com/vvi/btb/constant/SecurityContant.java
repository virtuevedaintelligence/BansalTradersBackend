package com.vvi.btb.constant;

public class SecurityContant {

    public static final String[] PUBLIC_URLS = {
                                                "/v1/products/getAllProducts",
                                                "/v1/products/productDetail/**",
                                                "/v1/categories/getAllCategories",
                                                "/v1/users/generateOTP",
                                                "/v1/users/verifyOTP",
                                                "/v1/users/register",
                                                "/v1/users/login"
                                                };
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
}
