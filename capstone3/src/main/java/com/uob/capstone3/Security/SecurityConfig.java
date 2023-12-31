package com.uob.capstone3.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Admin/**").hasAuthority("Admin")
                        .requestMatchers("/Teller/**").hasAuthority("Teller")
                        .requestMatchers("/logout", "/login", "/images/**").permitAll()
                        .requestMatchers( "/login", "/Teller/**", "/Admin/**")
                        .authenticated())
                .formLogin(
                        fl -> fl
                                .loginPage("/login").failureHandler(customAuthenticationFailureHandler())
                                .successHandler(customAuthenticationSuccessHandler())
                                .permitAll())
                .logout((logout) -> logout.logoutSuccessUrl("/login"))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new PersonDetailService();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
