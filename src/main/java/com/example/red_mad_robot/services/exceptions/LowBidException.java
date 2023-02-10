package com.example.red_mad_robot.services.exceptions;

public class LowBidException extends RuntimeException{
    public LowBidException(){
        super("слишком низкая ставка");
    }
}
