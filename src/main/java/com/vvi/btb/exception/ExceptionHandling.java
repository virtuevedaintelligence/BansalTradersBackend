package com.vvi.btb.exception;

import com.vvi.btb.domain.HttpResponse;
import com.vvi.btb.exception.domain.CategoryException;
import com.vvi.btb.exception.domain.ProductException;
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
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<HttpResponse> categoryGenericException(CategoryException exception) {
        return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage(), exception.getExplain());
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<HttpResponse> productGenericException(ProductException exception) {
        return createHttpResponse(HttpStatus.EXPECTATION_FAILED, exception.getMessage(), exception.getExplain());
    }
    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message, String explain) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase() + " " + explain, message), httpStatus);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
//    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}
