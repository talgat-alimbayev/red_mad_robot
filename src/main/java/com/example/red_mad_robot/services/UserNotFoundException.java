package com.example.red_mad_robot.services;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("пользователь " + id.toString() + " не найден");
    }
}
