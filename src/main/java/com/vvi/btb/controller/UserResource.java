package com.vvi.btb.controller;

import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserResource {

    private final Response response;

    public UserResource(Response response) {
        this.response = response;
    }

    @GetMapping("/generateOTP/{number}")
    public ResponseEntity<HttpResponse> createProduct(@PathVariable("number") String number)
    {
        if(number.equalsIgnoreCase("999999999")){
            return response.response(HttpStatus.OK, UserImplConstant.USER_OTP_GENERATED, null);
        }
        return response.response(BAD_GATEWAY, UserImplConstant.USER_NOT_OTP_GENERATED,null);
    }
}
