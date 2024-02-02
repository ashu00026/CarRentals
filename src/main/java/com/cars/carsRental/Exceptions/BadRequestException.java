package com.cars.carsRental.Exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super(msg);
    }
    public BadRequestException(String msg,Throwable cause){
        super(msg,cause);
    }
    public BadRequestException(Throwable cause){
        super(cause);
    }
}
