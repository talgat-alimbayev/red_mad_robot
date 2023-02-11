package com.example.red_mad_robot.controllers;

import com.example.red_mad_robot.services.exceptions.AdNotFoundException;
import com.example.red_mad_robot.services.exceptions.ArchivedAdException;
import com.example.red_mad_robot.services.exceptions.LowBidException;
import com.example.red_mad_robot.services.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(AdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String adNotFoundHandler(AdNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(ArchivedAdException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String archivedAdHandler(ArchivedAdException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(LowBidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String lowBidExceptionHandler(LowBidException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundExceptionHandler(UserNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }
}
