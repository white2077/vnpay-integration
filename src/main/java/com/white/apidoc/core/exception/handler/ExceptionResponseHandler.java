package com.white.apidoc.core.exception.handler;


import com.white.apidoc.core.exception.code.ErrorCode;
import com.white.apidoc.core.exception.custom.CustomException;
import com.white.apidoc.core.response.ResponseObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<?> handle(CustomException ex, WebRequest request) {
        return new ResponseObject<>(ex.getHttpStatusCode(), ex.getMessage(), ex.getDetails());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseObject<?> handleConstraintViolationException(ConstraintViolationException ex ) {
        Map<String, Object> details = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation ->
                        violation.getPropertyPath().toString(), ConstraintViolation::getMessage));
        return new ResponseObject<>(HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_PARAMETER.getCode() +
                ErrorCode.INVALID_PARAMETER.getMessage(), details);
    }
}
