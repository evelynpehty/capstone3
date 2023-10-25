package com.uob.capstone3.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Your logic to determine the target URL based on the user's roles
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
            return "/tellers";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Teller"))) {
            return "/viewAccounts";
        } else {
            return "/login";
        }
    }
}
