package com.vvi.btb.exception;

import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.exception.category.CategoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<HttpResponse> categoryGenericException(CategoryException exception) {
        return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage());
    }
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}
