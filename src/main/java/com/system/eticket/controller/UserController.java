package com.system.eticket.controller;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.web.bind.annotation.*;

import com.system.eticket.auth.AuthRequest;
import com.system.eticket.model.User;
import com.system.eticket.service.JwtService;
import com.system.eticket.service.UserService;

  
@RestController
@RequestMapping("/auth") 
public class UserController { 
  
    @Autowired
    private UserService service; 
  
    @Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager; 
  
    @PostMapping("/addNewUser") 
    public String addNewUser(@RequestBody User userInfo) { 
        return service.addUser(userInfo); 
    } 
  
    @PostMapping("/login") 
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getUsername()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
  
} 
