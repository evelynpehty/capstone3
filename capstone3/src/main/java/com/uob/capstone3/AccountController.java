package com.uob.capstone3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountType;
import com.uob.capstone3.Repositories.AccountRepository;
import com.uob.capstone3.Repositories.AccountTypeRepository;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountTypeRepository accountTypeRepository;
    

    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model M){
        return "viewAccounts";
    }

    @RequestMapping("/createAccount")
    public String showCreateAccount(Model model) {
        List<AccountType> accountTypes = accountTypeRepository.findAll(); // Get account types from your service/repository
        model.addAttribute("accountTypes", accountTypes);
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public String doCreateAccount(@RequestParam String customerNRIC, @RequestParam String customerName, @RequestParam int accountTypeID) {
        Account account = new Account();
        AccountType accountType = accountTypeRepository.findById(accountTypeID).orElseThrow(() -> new IllegalArgumentException("Invalid account type ID"));
        account.setAccountType(accountType);
        account.setCustomerNRIC(customerNRIC);
        account.setCustomerName(customerName);
    
        accountRepository.save(account);
        return "redirect:/viewAccounts";
    }
}
