package com.project.EmployeeManagementSystem.controller;

import com.project.EmployeeManagementSystem.model.User;
import com.project.EmployeeManagementSystem.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    public AuthController(AuthService authService)
    {
        this.authService=authService;
    }
    @PostMapping()
    public User createUser(@RequestBody User user)
    {
        return authService.createUser(user);
    }
}
