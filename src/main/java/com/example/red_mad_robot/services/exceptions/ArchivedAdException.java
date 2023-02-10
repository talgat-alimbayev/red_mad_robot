package com.example.red_mad_robot.services.exceptions;

public class ArchivedAdException extends RuntimeException{
    public ArchivedAdException(Long id){
        super("объявление " + id.toString() + " находится в архиве");
    }
}
