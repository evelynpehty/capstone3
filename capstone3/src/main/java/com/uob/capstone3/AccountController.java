package com.uob.capstone3;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Repositories.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository ar;

    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model model){
        List<Account> accounts = ar.findAll();
        model.addAttribute("accounts", accounts);
        return "viewAccounts";
    }
    
    @GetMapping("/deactivateAccount/{accountID}")
    public String deactivateAccount(Model model, @PathVariable int accountID){
        Optional<Account> accountOptional = ar.findById(accountID);
      
          
        if (accountOptional.isPresent()) {
            Account acct = accountOptional.get();
            acct.setAccountIsActive(0);
            ar.save(acct);
        }

        return "redirect:/viewAccounts";
    }

    @GetMapping("/activateAccount/{accountID}")
    public String activateAccount(Model model, @PathVariable int accountID){
        Optional<Account> accountOptional = ar.findById(accountID);
      
          
        if (accountOptional.isPresent()) {
            Account acct = accountOptional.get();
            acct.setAccountIsActive(1);
            ar.save(acct);
        }

        return "redirect:/viewAccounts";
    }
}
