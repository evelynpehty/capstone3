package com.uob.capstone3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountTransaction;
import com.uob.capstone3.Repositories.AccountRepository;
import com.uob.capstone3.Repositories.AccountTransactionRepository;


@Controller
public class AccountController {
    @Autowired
    AccountTransactionRepository atr;

    @Autowired
    AccountRepository ar;
    
    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model M){
        return "viewAccounts";
    }

    @GetMapping(value="/accountdashboard")
    public String showAccountDashboard(Model m) {
        Account account = ar.findById(2).get();
        List<AccountTransaction> transactionList = atr.findAllByTransactionPartyAccountID(account);
        transactionList.addAll(atr.findAllByAccountID(account));
        m.addAttribute("transactions", transactionList);
        return "accountdashboard";
    }
    
}
