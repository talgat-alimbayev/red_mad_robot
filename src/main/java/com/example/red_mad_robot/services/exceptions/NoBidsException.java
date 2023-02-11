package com.example.red_mad_robot.services.exceptions;

public class NoBidsException extends RuntimeException{
    public NoBidsException(Long id){
        super("нет ставок по объявлению " + id.toString());
    }
}
