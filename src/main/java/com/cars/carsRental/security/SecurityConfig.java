package com.cars.carsRental.security;

import com.cars.carsRental.Filter.ExceptionHandlerFilter;
import com.cars.carsRental.Filter.JWTTokenValidator;
import com.cars.carsRental.Interceptors.JwtValidatiorInterceptor;
import com.cars.carsRental.Filter.CsrfCookieFilter;
import com.cars.carsRental.Filter.JWTTokenGeneratingFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

//    @Autowired
//    public JwtValidatiorInterceptor jwtValidatiorInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry interceptorRegistry){
//        interceptorRegistry.addInterceptor(jwtValidatiorInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/login");
//    }


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT user_name,password,enabled from users where user_name=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT user_name,authority from roles where user_name=?");
        return jdbcUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        CsrfTokenRequestAttributeHandler requestHandler=new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers(HttpMethod.GET,"/api/login").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/login/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/cars").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/api/cars/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/api/cars").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT,"/api/cars").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE,"/api/cars/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
        );
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .cors(corsCustomizer->corsCustomizer.configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config=new CorsConfiguration();
                                config.setAllowedOrigins(Collections.singletonList("*"));
                                config.setAllowedMethods(Collections.singletonList("*"));
                                config.setAllowCredentials(true);
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                config.setExposedHeaders(Arrays.asList("Authorization"));
                                config.setMaxAge(3600L);
                                return config;
                            }
                        })).csrf((csrf)->csrf
                                            .csrfTokenRequestHandler(requestHandler).ignoringRequestMatchers("api/cars","api/cars/**")
                                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
        http.addFilterAfter(new JWTTokenGeneratingFilter(),BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidator(),BasicAuthenticationFilter.class)
                .addFilterAfter(new ExceptionHandlerFilter(), LogoutFilter.class)
                .addFilterAfter(new CsrfCookieFilter(),BasicAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());

//        http.csrf(csrf->csrf.disable());

        return http.build();

    }



}
