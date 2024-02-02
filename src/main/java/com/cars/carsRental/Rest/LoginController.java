package com.cars.carsRental.Rest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/login")
    public ResponseEntity<String> getLoginData(){
        String loggedIn="You are logged in Successfully!!";
        return new ResponseEntity<>(loggedIn, HttpStatus.OK);
    }

    @GetMapping("/login/cars")
    public void redirectLogin(HttpServletResponse response) throws IOException {
        Authentication curentUser= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(curentUser.getName());
        System.out.println(curentUser.getAuthorities());
        System.out.println(curentUser.getCredentials());
        response.sendRedirect("/api/cars");
    }



}
