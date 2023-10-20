package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uob.capstone3.Entities.AccountType;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
}
