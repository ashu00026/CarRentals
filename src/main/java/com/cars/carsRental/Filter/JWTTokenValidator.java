package com.cars.carsRental.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cars.carsRental.Constants.SecurityConstants;
import com.cars.carsRental.Entity.ErrorResponseObject;
import com.cars.carsRental.Exceptions.BadLoginRequestException;
import com.cars.carsRental.Exceptions.SignatureTamperedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTTokenValidator extends OncePerRequestFilter {
    private final ObjectMapper objectMapper=new ObjectMapper();
    private String getCookieValue(HttpServletRequest req, String cookieName) {
        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt=request.getHeader(SecurityConstants.JWT_HEADER).substring(7);
//        String jwt=getCookieValue(request,"JWT-TOKEN");
        if(jwt!=null){
            try{
                System.out.println(jwt);
                SecretKey key= Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                System.out.println("validating jwt token!!");
                DecodedJWT decodedJwt= JWT.decode(jwt);
//                System.out.println(decodedJwt.getExpiresAt().before(new Date()));
                if(decodedJwt.getExpiresAt().before(new Date())){
                    throw new BadLoginRequestException("TokenExpired!");
                }
                Claims claims= Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username=String.valueOf(claims.get("username"));
                String authorities=(String)claims.get("authorities");
                Authentication auth=new UsernamePasswordAuthenticationToken(username,null,
                            AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (SignatureException | MalformedJwtException | JWTDecodeException e) {
                throw new SignatureTamperedException("JWT Signature Tampered");
            }
        }
        filterChain.doFilter(request,response);
    }
//    public String convertObjectToJson(Object object) throws JsonProcessingException {
//        if (object == null) {
//            return null;
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.writeValueAsString(object);
//    }
//    private void handleException(HttpServletResponse response, BadLoginRequestException ex) throws IOException {
//        ErrorResponseObject error=new ErrorResponseObject();
//        error.setTimeStamp(System.currentTimeMillis());
//        error.setMessage(ex.getMessage());
//        error.setStatus(HttpStatus.BAD_REQUEST.value());
//        // Convert exception to JSON.
////        String jsonResponse = objectMapper.writeValueAsString(error);
//
//        // Set response headers.
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//
//        // Set HTTP status.
//        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//
//        // Write JSON response to the output stream.
//        response.getWriter().write(convertObjectToJson(error));
////        response.getWriter().flush();
//    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getHeader(SecurityConstants.JWT_HEADER).startsWith(SecurityConstants.JWT_Token_Type);
//        return (getCookieValue(request,"JWT-TOKEN")==null);

    }
}
