package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {

    private ErrorMessage errorMessage;

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorMessage> customerExceptionHandler(CustomerException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        if(ex instanceof ResourceNotFoundException)
            httpStatus = HttpStatus.NOT_FOUND;
        if(ex instanceof ResourceDuplicateException)
            httpStatus = HttpStatus.CONFLICT;

        errorMessage = new ErrorMessage(
                httpStatus.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}
