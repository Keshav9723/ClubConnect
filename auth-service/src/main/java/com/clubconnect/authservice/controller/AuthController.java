package com.clubconnect.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubconnect.authservice.dto.AuthDTO;
import com.clubconnect.authservice.dto.ReturnDTO;
import com.clubconnect.authservice.dto.SignupDTO;
import com.clubconnect.authservice.service.AuthService;

    
@RequestMapping("/auth")
@RestController
public class AuthController 
{
    AuthService _AuthService;

    @Autowired
    AuthController(AuthService authService)
    {
        this._AuthService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<ReturnDTO> SignUp(@RequestBody SignupDTO cred) 
    {
        ReturnDTO response = new ReturnDTO();
        boolean isSuccess = _AuthService.SignUp(cred, response);
        if(isSuccess)
        {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> Authenticate(@RequestBody AuthDTO cred) 
    {
        boolean isAuthenticated = _AuthService.Authenticate(cred);
        if (isAuthenticated) {
            return ResponseEntity.ok("Authentication successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

}