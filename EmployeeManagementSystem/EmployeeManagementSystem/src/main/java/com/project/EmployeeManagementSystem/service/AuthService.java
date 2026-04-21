package com.project.EmployeeManagementSystem.service;

import com.project.EmployeeManagementSystem.model.User;
import com.project.EmployeeManagementSystem.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public UserRepo userRepo;
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepo userRepo,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepo=userRepo;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    public  User createUser(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
