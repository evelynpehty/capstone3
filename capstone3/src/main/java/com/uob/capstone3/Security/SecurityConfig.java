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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthFailureHandler();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        String[] staticresources = {
                "/css/**",
                "/images/**",
                "/fonts/**",
                "/scripts/**",
        };
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/tellers").hasAuthority("Admin")
                        .requestMatchers("/createAccount", "/viewAccounts").hasAuthority("Teller")
                        .requestMatchers("/logout", "/login").permitAll()
                        .requestMatchers(staticresources).permitAll()
                        .requestMatchers("/", "/login", "/index", "/admin", "/createAccount", "/viewAccounts",
                                "/tellers")
                        .authenticated())
                .formLogin(
                        fl -> fl
                                .loginPage("/login").failureHandler(customAuthenticationFailureHandler())
                                .defaultSuccessUrl("/viewAccounts")
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
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}