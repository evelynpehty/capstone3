package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uob.capstone3.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
