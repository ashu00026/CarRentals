package com.cars.carsRental.Exceptions;


public class BadLoginRequestException extends RuntimeException{
    public BadLoginRequestException(String msg){
        super(msg);
    }
    public BadLoginRequestException(String msg, Throwable clause){
        super(msg,clause);
    }
    public BadLoginRequestException(Throwable clause){
        super(clause);
    }

}
