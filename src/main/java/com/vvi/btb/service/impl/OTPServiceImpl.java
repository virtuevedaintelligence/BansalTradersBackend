package com.vvi.btb.service.impl;

import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.response.OTPResponse;
import com.vvi.btb.service.abs.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class OTPServiceImpl implements OTPService {
    private static Map<Long, OTPResponse> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<OTPResponse> sendOTP(long number) {
        log.info("in send OTP");
        OTPResponse success = new OTPResponse();
        int count = 1;
        if(cache.containsKey(number)) {
             success = cache.get(number);
             count = count+cache.get(number).getCount();
             if(count <= 3){
                 success = getOtpResponse(success, count);
                 cache.put(number, success);
             }else if(LocalDateTime.now().isAfter(cache.get(number).getTime().plusMinutes(3))){
                 success = getOtpResponse(success, 1);
             }
        }else{
            success = getOtpResponse(success, count);
            cache.put(number, success);
        }
        return Optional.ofNullable(success);
    }

    private OTPResponse getOtpResponse(OTPResponse response, int count) {
        // nexon code
        response.setOtp(generateOTP());
        response.setStatus("SUCCESS");
        response.setCount(count);
        response.setTime(LocalDateTime.now());
        return response;
    }

    @Override
    public boolean verifyOTP(UserOTPRequest userOTPRequest) {
        boolean validated = false;
        if(cache.containsKey(userOTPRequest.getNumber())){
             validated = userOTPRequest.getOtp() == Long.parseLong(cache.get(userOTPRequest.getNumber()).getOtp());
             cache.remove(userOTPRequest.getNumber());
        }
        return validated;
    }

    private String generateOTP(){
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
