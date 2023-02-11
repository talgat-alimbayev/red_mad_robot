package com.example.red_mad_robot.services.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("пользователь " + id.toString() + " не найден");
    }
    public UserNotFoundException(String email){
        super("пользователь с имейлом " + email + " не найден");
    }
}
