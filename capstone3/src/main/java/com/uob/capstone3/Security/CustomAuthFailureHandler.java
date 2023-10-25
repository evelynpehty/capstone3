package com.uob.capstone3.Security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid credentials. Please try again.";

        if (exception.getMessage().equalsIgnoreCase("UserNotFound")) {
            errorMessage = "User not found.";
        } else if (exception.getMessage().equalsIgnoreCase("BadCredentials")) {
            errorMessage = "Incorrect password.";
        }

        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/login?error");
    }
}
