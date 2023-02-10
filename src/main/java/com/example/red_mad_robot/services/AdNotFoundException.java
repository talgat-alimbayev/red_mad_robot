package com.example.red_mad_robot.services;

public class AdNotFoundException extends RuntimeException{
    public AdNotFoundException(Long id){
        super("объявление " + id.toString() + " не найдено");
    }
}
