package com.cars.carsRental.Interceptors;

import com.cars.carsRental.Constants.SecurityConstants;
import com.cars.carsRental.Exceptions.BadLoginRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtValidatiorInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(JwtValidatiorInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return true;
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER).substring(7);
//        String jwt=null;
        if (jwt != null) {
            try {
//                System.out.println(jwt);
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                logger.info("Request received for URL: " + request.getServletPath());
                System.out.println("validating jwt token!!"+request.getRequestURL().toString());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
                return true;
            } catch (Exception e) {
//                System.out.println(e);
                throw new BadLoginRequestException("Invalid Credentialssss!");
            }
        }
        return true;
    }
}