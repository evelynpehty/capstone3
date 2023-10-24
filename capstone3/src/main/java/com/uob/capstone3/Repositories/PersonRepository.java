package com.uob.capstone3.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import com.uob.capstone3.Entities.Person;
import jakarta.transaction.Transactional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByUsername(String username);

    List<Person> findByRoleIgnoreCase(String role);

    @Modifying
    @Transactional
    Long deleteByUserID(Integer userID);
}
