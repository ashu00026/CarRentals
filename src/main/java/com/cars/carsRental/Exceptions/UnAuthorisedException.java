package com.cars.carsRental.Exceptions;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String msg){
        super(msg);
    }
    public UnAuthorisedException(String msg,Throwable cause){
        super(msg,cause);
    }
    public UnAuthorisedException(Throwable cause){
        super(cause);
    }
}
