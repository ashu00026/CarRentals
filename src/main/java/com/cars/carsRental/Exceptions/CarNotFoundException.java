package com.cars.carsRental.Exceptions;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(String msg){
        super(msg);
    }
    public CarNotFoundException(String msg ,Throwable cause){
        super(msg,cause);
    }
    public CarNotFoundException(Throwable cause){
        super(cause);
    }
}
