package com.vvi.btb.controller;

import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.mapper.user.UserRegisterEntityMapper;
import com.vvi.btb.domain.mapper.user.UserResponseMapper;
import com.vvi.btb.domain.request.user.UserLoginRequest;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.request.user.UserRegisterRequest;
import com.vvi.btb.domain.response.OTPResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.service.abs.OTPService;
import com.vvi.btb.service.abs.UserService;
import com.vvi.btb.service.impl.JwtService;
import com.vvi.btb.util.Response;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import static com.vvi.btb.constant.UserImplConstant.*;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
@Slf4j
public record UserResource(Response response, UserService userService,
         OTPService otpService, UserRegisterEntityMapper userRegisterEntityMapper,
         JwtService jwtService, AuthenticationManager authenticationManager,
         UserResponseMapper userResponseMapper
) {

    @PostMapping("/generateOTP")
    public ResponseEntity<HttpResponse> generateOTP(@RequestBody @Valid UserOTPRequest userOTPRequest,
                                                            BindingResult errors) throws UserException {
        if(errors.hasErrors()){

        }
        if(userService.findUserByContactNumber(Long.valueOf(userOTPRequest.getNumber())).isPresent()){
            Optional<OTPResponse> otpResponse = otpService.sendOTP(Long.parseLong(userOTPRequest.getNumber()));
            if(otpResponse.isPresent()){
                OTPResponse res = otpResponse.get();
                if(res.getStatus().equals(FAILED)){
                    return response.response(BAD_GATEWAY, USER_NOT_OTP_GENERATED,null);
                }
                return response.response(OK, USER_OTP_GENERATED,USER_OTP_GENERATED);
                }
        } else {
            UserResponse userResponse = userService.save(userOTPRequest);
            if(otpService.sendOTP(Long.parseLong(userOTPRequest.getNumber())).isPresent()){
               return response.response(OK, USER_OTP_GENERATED, null);
            }
        }
        return response.response(BAD_GATEWAY, USER_NOT_OTP_GENERATED,null);
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<HttpResponse> verifyOTP(@RequestBody UserOTPRequest userOTPRequest)
    {
        Optional<UserResponse> user = userService.findUserByContactNumber(Long.valueOf(userOTPRequest.getNumber()));
        if(!user.isPresent()){
            return response.response(BAD_GATEWAY, USER_OTP_NOT_VERIFIED, USER_GENERATE_OTP);
        }
        if(otpService.verifyOTP(userOTPRequest)){
            return response.response(OK, USER_LOGGED_SUCCESS, user.get());
        }
        return response.response(BAD_GATEWAY, USER_OTP_NOT_VERIFIED,null);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) throws UserException {
        Optional<UserResponse> user = userService.findUserByName(userRegisterEntityMapper.generateUsername(userRegisterRequest));
        if(user.isPresent()){
                return response.response(CONFLICT, USER_ALREADY_EXISTS, user.get());
        }
        UserResponse userResponse = userService.save(userRegisterRequest);
        return response.response(OK, ADMIN_REGISTERED_SUCCESS, userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody UserLoginRequest userLoginRequest) throws UserException {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(),
                        userLoginRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            Optional<UserResponse> user = userService.findUserByName(userLoginRequest.getUserName());
            return response.response(OK, ADMIN_LOGGED_SUCCESS, user.get());
        }
        return response.response(BAD_GATEWAY, ADMIN_NOT_LOGGED_SUCCESS,null);
    }
}
