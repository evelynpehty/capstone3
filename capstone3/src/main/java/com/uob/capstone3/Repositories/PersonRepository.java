package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uob.capstone3.Entities.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByUsername(String username);
}
