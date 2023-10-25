package com.uob.capstone3.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Modifying
    @Transactional
    @Query("update Person p set p.username=:username, p.firstName=:firstName, p.lastName=:lastName, p.password=:password where p.userID=:userID")
    void updateUser(String username,String firstName, String lastName, String password, int userID);
}
