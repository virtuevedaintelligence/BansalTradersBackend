package com.vvi.btb.util;

import com.vvi.btb.domain.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Response {
    public ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message, Object response) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message, response), httpStatus);
    }
}
