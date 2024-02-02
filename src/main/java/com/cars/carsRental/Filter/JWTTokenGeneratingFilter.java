package com.cars.carsRental.Filter;

import com.cars.carsRental.Constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JWTTokenGeneratingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println(request.getHeaders("Authorization"));
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
        SecretKey key= Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt= Jwts.builder().setIssuer("CarsRental").setSubject("JWT Token")
                        .claim("username",authentication.getName())
                        .claim("authorities",populateAuthorities(authentication.getAuthorities()))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime()+30000000))
                        .signWith(key).compact();
//                response.setHeader(SecurityConstants.JWT_HEADER,jwt);
                Cookie jwtCookie= new Cookie("JWT-TOKEN",jwt);
                jwtCookie.setMaxAge(60*60*24);//setExpiration till 1 day,
                response.addCookie(jwtCookie);
//                response.addCookie();
        }
        filterChain.doFilter(request,response);

    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        return request.getServletPath().equals("/");
        return false;
    }


    public String populateAuthorities(Collection<? extends GrantedAuthority> collection){
        Set<String> authoritiesSet=new HashSet<>();
        for(GrantedAuthority x: collection){
            authoritiesSet.add(x.getAuthority());
        }
        return String.join(",",authoritiesSet);
    }


}
