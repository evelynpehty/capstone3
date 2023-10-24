package com.uob.capstone3.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountTransaction;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Integer> {
    List <AccountTransaction> findAllByTransactionPartyAccountID(Account transactionPartyAccountID);
    List <AccountTransaction> findAllByAccountID(Account accountID);
}
