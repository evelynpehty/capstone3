package com.uob.capstone3.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uob.capstone3.Entities.Person;
import com.uob.capstone3.Repositories.PersonRepository;

@Service
public class PersonDetailService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);

        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Convert roles to lowercase for case-insensitive comparison
        String lowercaseRole = person.getRole().toLowerCase();

        return User.builder()
                .username(person.getUsername())
                .password(person.getPassword())
                .roles(lowercaseRole) // Use lowercase role name for case-insensitive comparison
                .build();
    }
}
