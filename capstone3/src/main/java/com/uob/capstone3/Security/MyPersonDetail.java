package com.uob.capstone3.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.uob.capstone3.Entities.Person;

import java.util.Collection;
import java.util.Collections;

public class MyPersonDetail implements UserDetails {

    private final Person person;

    public MyPersonDetail(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String Role = person.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(Role));
    }

    @Override
    public boolean isAccountNonExpired() {
        // Implement your expiration logic here, if applicable
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Implement your account locking logic here, if applicable
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Implement your credentials expiration logic here, if applicable
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Implement your logic to enable or disable user accounts
        return true;
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }
}
