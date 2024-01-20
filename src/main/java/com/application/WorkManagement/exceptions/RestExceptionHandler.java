package com.application.WorkManagement.exceptions;

import com.application.WorkManagement.dto.responses.ExceptionResponse;
import com.application.WorkManagement.exceptions.custom.CustomDuplicateException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception
    ){
        Map<String, String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage
                        )
                );
        return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errors);
    }

    @ExceptionHandler(CustomDuplicateException.class)
    public ResponseEntity<ExceptionResponse> customDuplicateException(CustomDuplicateException exception){
        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new ExceptionResponse(exception.getMessage())
                );
    }

}