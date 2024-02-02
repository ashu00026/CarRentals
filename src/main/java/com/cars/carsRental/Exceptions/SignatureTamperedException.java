package com.cars.carsRental.Exceptions;

public class SignatureTamperedException extends RuntimeException{
    public SignatureTamperedException(String msg){
        super(msg);
    }
    public SignatureTamperedException(String msg,Throwable cause){
        super(msg,cause);
    }
    public SignatureTamperedException(Throwable cause){
        super(cause);
    }
}
