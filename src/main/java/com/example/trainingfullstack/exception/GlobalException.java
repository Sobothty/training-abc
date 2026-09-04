package com.example.trainingfullstack.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class GlobalException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorException> handleAppException(
        AppException exception
    ){
        ErrorException responseException = new ErrorException(
                exception.getStatusCode().value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(exception.getStatusCode()).body(responseException);
}
}
