package com.zizser.zizserservice.exception;

import com.zizser.zizserservice.exception.type.BadUrlAddressException;
import com.zizser.zizserservice.exception.type.WebsiteMetaDataIdNotFoundException;
import com.zizser.zizserservice.model.ErrorHttpResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    @ExceptionHandler(BadUrlAddressException.class)
    public ResponseEntity<ErrorHttpResponse> badUrlAddressException(BadUrlAddressException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(WebsiteMetaDataIdNotFoundException.class)
    public ResponseEntity<ErrorHttpResponse> websiteMetaDateNotFoundException(WebsiteMetaDataIdNotFoundException exception) {
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }


    private ResponseEntity<ErrorHttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        ErrorHttpResponse httpResponse = new ErrorHttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message);

        return new ResponseEntity<>(httpResponse, httpStatus);
    }
}
