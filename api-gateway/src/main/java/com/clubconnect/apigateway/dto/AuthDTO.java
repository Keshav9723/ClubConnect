package com.clubconnect.apigateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthDTO {

    @JsonProperty("email")
    String _Email;

    @JsonProperty("password")
    String _Password;

    public AuthDTO() 
    {

    }

    public AuthDTO(String email, String password) 
    {
        _Email = email;
        _Password = password;
    }

    public String get_Email() 
    {
        return _Email;
    }

    public void set_Email(String email) 
    {
        _Email = email;
    }

    public String get_Password() 
    {
        return _Password;
    }

    public void set_Password(String password)
    {
        _Password = password;
    }

    
}