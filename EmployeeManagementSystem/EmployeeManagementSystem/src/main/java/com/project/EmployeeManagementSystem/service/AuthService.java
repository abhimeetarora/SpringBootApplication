package com.project.EmployeeManagementSystem.service;

import com.project.EmployeeManagementSystem.dto.LoginDTO;
import com.project.EmployeeManagementSystem.model.User;
import com.project.EmployeeManagementSystem.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public UserRepo userRepo;
    public BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthenticationManager authenticationManager;
   public JwtService jwtService;
    public AuthService(UserRepo userRepo,BCryptPasswordEncoder bCryptPasswordEncoder,AuthenticationManager authenticationManager,JwtService jwtService)
    {
        this.userRepo=userRepo;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }
    public  User createUser(User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public String login(LoginDTO dto)
    {
        Authentication authentication=authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(dto.username(),dto.password()));
        return  jwtService.generateToken(dto.username());
    }
}
