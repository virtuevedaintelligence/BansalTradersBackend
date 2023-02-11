package com.vvi.btb.controller;

import com.vvi.btb.constant.UserImplConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.mapper.user.UserRegisterEntityMapper;
import com.vvi.btb.domain.request.user.UserLoginRequest;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.request.user.UserRegisterRequest;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.service.abs.OTPService;
import com.vvi.btb.service.abs.UserService;
import com.vvi.btb.service.impl.JwtService;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.vvi.btb.constant.UserImplConstant.*;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserResource {

    private final Response response;
    private final UserService userService;
    private final OTPService otpService;
    private final UserRegisterEntityMapper userRegisterEntityMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResource(Response response, UserService userService,
                        OTPService otpService,
                        UserRegisterEntityMapper userRegisterEntityMapper,
                        JwtService jwtService,
                        AuthenticationManager authenticationManager) {
        this.response = response;
        this.userService = userService;
        this.otpService = otpService;
        this.userRegisterEntityMapper = userRegisterEntityMapper;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/generateOTP")
    public ResponseEntity<HttpResponse> generateOTP(@RequestBody UserOTPRequest userOTPRequest) throws UserException {
        if(userService.findUserByContactNumber(userOTPRequest.getNumber()).isPresent()){
                if(otpService.sendOTP(userOTPRequest.getNumber()).isPresent()){
                 return response.response(OK, USER_OTP_GENERATED,USER_OTP_GENERATED);
                }
        } else {
            UserResponse userResponse = userService.save(userOTPRequest);
            if(otpService.sendOTP(userOTPRequest.getNumber()).isPresent()){
               return response.response(OK, USER_OTP_GENERATED, null);
            }
        }
        return response.response(BAD_GATEWAY, UserImplConstant.USER_NOT_OTP_GENERATED,null);
    }
    @PostMapping("/verifyOTP")
    public ResponseEntity<HttpResponse> verifyOTP(@RequestBody UserOTPRequest userOTPRequest)
    {
        if(otpService.verifyOTP(userOTPRequest)){
            return response.response(OK, USER_OTP_VERIFIED,null);
        }
        return response.response(BAD_GATEWAY, UserImplConstant.USER_OTP_NOT_VERIFIED,null);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) throws UserException {
        Optional<UserResponse> user = userService.findUserByName(userRegisterEntityMapper.generateUsername(userRegisterRequest));
        if(user.isPresent()){
                return response.response(CONFLICT, USER_ALREADY_EXISTS, user.get());
        }
        UserResponse userResponse = userService.save(userRegisterRequest);
        return response.response(OK, USER_REGISTERED_SUCCESSFULLY, userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody UserLoginRequest userLoginRequest)
    {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getUserName(),
                        userLoginRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            String token = jwtService.generateToken(userLoginRequest.getUserName());
            return response.response(OK, String.valueOf(UserImplConstant.USER_LOGGED_SUCCESS), token);
        }
        return response.response(BAD_GATEWAY, String.valueOf(UserImplConstant.USER_NOT_OTP_GENERATED),null);
    }
}
