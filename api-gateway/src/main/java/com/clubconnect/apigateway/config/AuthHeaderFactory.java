package com.clubconnect.apigateway.config;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthHeaderFactory {

    @Value("${clubservice.auth.username}")
    String _clubUsername;
    @Value("${clubservice.auth.password}")
    String _clubPassword;

    @Value("${eventservice.auth.username}")
    String _eventUsername;
    @Value("${eventservice.auth.password}")
    String _eventPassword;

    @Value("${memberservice.auth.username}")
    String _memberUsername;
    @Value("${memberservice.auth.password}")
    String _memberPassword;

    @Value("${registrationservice.auth.username}")
    String _registrationUsername;
    @Value("${registrationservice.auth.password}")
    String _registrationPassword;

    @Value("${apigateway.shared.secret}")
    String _SharedSecret;
    
    String BuildAuthHeader(String serviceName)
    {
        String username = "";
        String password = "";

        if("clubservice".equals(serviceName))
        {
            username = _clubUsername; 
            password = _clubPassword;
        }
        else if("eventservice".equals(serviceName))
        {
            username = _eventUsername; 
            password = _eventPassword;            
        }
        else if("memberservice".equals(serviceName))
        {
            username = _memberUsername; 
            password = _memberPassword;
        }
        else if("registrationservice".equals(serviceName))
        {
            username = _registrationUsername; 
            password = _registrationPassword;
        }

        String auth = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    String getSharedSecret()
    {
        return _SharedSecret;
    }
}