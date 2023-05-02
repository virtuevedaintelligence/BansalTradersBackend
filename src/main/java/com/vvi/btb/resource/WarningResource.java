package com.vvi.btb.resource;

import com.vvi.btb.constant.GlobalConstant;
import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.domain.request.user.UserOTPRequest;
import com.vvi.btb.domain.response.OTPResponse;
import com.vvi.btb.domain.response.UserResponse;
import com.vvi.btb.exception.domain.UserException;
import com.vvi.btb.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = GlobalConstant.LOCAL_ENV)
@RestController
@RequestMapping("/v1/bansaltraders")
@Slf4j
public record WarningResource(Response response) {

    @GetMapping("/warning")
    public ResponseEntity<HttpResponse> warning(){
        return response.response(OK, "OK",null);
    }

}
