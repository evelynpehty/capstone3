package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uob.capstone3.Entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
