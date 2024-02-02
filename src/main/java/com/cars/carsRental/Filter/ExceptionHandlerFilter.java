package com.cars.carsRental.Filter;

import com.cars.carsRental.Entity.ErrorResponseObject;
import com.cars.carsRental.Exceptions.BadLoginRequestException;
import com.cars.carsRental.Exceptions.SignatureTamperedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

//package com.cars.carsRental.Filter;
//
//import com.cars.carsRental.Exceptions.BadLoginRequestException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BadLoginRequestException e) {

            // custom error response class used across my project
            System.out.println("Bad login request");
            ErrorResponseObject errorResponse = new ErrorResponseObject();
            errorResponse.setTimeStamp(System.currentTimeMillis());
            errorResponse.setMessage(e.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        } catch(SignatureTamperedException e){
            ErrorResponseObject errorResponse = new ErrorResponseObject();
            errorResponse.setTimeStamp(System.currentTimeMillis());
            errorResponse.setMessage(e.getMessage());
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}