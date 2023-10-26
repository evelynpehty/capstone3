package com.uob.capstone3.Security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

     @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        // Your logic to determine the target URL based on the user's roles
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
            return "/Admin/viewTellers";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Teller"))) {
            return "/Teller/viewAccounts";
        } else {
            return "/login";
        }
    }
}
