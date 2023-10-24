package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountType;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCustomerNRIC(String customerNRIC);
    
    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType AND a.customerNRIC = :customerNRIC")
    List<Account> findByAccountTypeAndCustomerNRIC(@Param("accountType") AccountType accountType, @Param("customerNRIC") String customerNRIC);
    }
