package com.uob.capstone3.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uob.capstone3.Entities.Account;
import org.springframework.stereotype.Repository;
import com.uob.capstone3.Entities.AccountTransaction;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Integer> {
    List<AccountTransaction> findAllByAccountIDOrTransactionPartyAccountIDOrderByTransactionIDDesc(Account account, Account transactionParty);
}
