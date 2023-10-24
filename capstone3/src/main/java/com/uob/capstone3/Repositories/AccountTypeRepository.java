package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uob.capstone3.Entities.AccountType;


public interface AccountTypeRepository extends JpaRepository<AccountType, Integer>{
    AccountType findByAccountTypeName(String accountTypeName);
}
