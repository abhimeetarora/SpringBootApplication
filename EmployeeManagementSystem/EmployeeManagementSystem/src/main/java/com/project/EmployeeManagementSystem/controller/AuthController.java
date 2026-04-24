package com.project.EmployeeManagementSystem.controller;

import com.project.EmployeeManagementSystem.dto.LoginDTO;
import com.project.EmployeeManagementSystem.model.User;
import com.project.EmployeeManagementSystem.service.AuthService;
import com.project.EmployeeManagementSystem.service.JwtService;
import io.jsonwebtoken.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService,JwtService jwtService) {
        this.authService = authService;
    }
        @PostMapping()
        public User createUser (@RequestBody User user)
        {
            return authService.createUser(user);
        }

        @PostMapping("/login")
        public String login (@RequestBody LoginDTO dto)
        {
            return authService.login(dto);
        }
        @GetMapping()
        public User getUser(Long id)
        {
            return authService.getUser(id);
        }
    }
