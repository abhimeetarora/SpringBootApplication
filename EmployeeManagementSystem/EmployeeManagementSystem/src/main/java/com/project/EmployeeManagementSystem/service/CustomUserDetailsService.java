package com.project.EmployeeManagementSystem.service;

import com.project.EmployeeManagementSystem.model.User;
import com.project.EmployeeManagementSystem.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo)
    {
        this.userRepo=userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user is not present"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole())));

    }
}
