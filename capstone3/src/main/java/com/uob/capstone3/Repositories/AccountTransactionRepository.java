package com.uob.capstone3.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uob.capstone3.Entities.AccountTransaction;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Integer> {

}
