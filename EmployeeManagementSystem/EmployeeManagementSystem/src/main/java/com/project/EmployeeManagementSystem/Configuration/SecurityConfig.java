package com.project.EmployeeManagementSystem.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
    {
        return  httpSecurity.csrf(csrf->csrf.disable()).authorizeHttpRequests(auth->auth.anyRequest().permitAll()).build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }
}
