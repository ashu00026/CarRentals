package com.cars.carsRental.ControllerAdvice;

import com.cars.carsRental.Exceptions.BadLoginRequestException;
import com.cars.carsRental.Exceptions.BadRequestException;
import com.cars.carsRental.Exceptions.CarNotFoundException;
import com.cars.carsRental.Entity.ErrorResponseObject;
import com.cars.carsRental.Exceptions.UnAuthorisedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class RentalExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseObject>BadCredentials(BadLoginRequestException exc){
        ErrorResponseObject err=new ErrorResponseObject();
        err.setMessage(exc.getMessage());
        err.setTimeStamp(System.currentTimeMillis());
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseObject>carNotFound(CarNotFoundException exc){
        ErrorResponseObject error=new ErrorResponseObject();
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    ResponseEntity<ErrorResponseObject>BadRequest(BadRequestException exc){
        ErrorResponseObject error=new ErrorResponseObject();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setTimeStamp(System.currentTimeMillis());
        error.setMessage(exc.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    ResponseEntity<ErrorResponseObject>UnAuthorisedUser(UnAuthorisedException exc){
        ErrorResponseObject error=new ErrorResponseObject();
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler
    ResponseEntity<ErrorResponseObject>WrongArgument(MethodArgumentTypeMismatchException exc){
        ErrorResponseObject error=new ErrorResponseObject();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
//        System.out.println(exc);
        error.setMessage("Please Provide Valid Input!");
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    ResponseEntity<ErrorResponseObject>AllExceptions(Exception exe){
        ErrorResponseObject error=new ErrorResponseObject();
        error.setTimeStamp(System.currentTimeMillis());
        error.setMessage(exe.getMessage());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
