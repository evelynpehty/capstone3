package com.uob.capstone3.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uob.capstone3.Entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByUsername(String username);

    List<Person> findByRole(String role);
}
