package com.uob.capstone3.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountType;
import com.uob.capstone3.Repositories.AccountRepository;
import com.uob.capstone3.Repositories.AccountTypeRepository;
@Controller
@RequestMapping("/Teller")
public class AccountController {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository ar;

    @Autowired
    AccountTypeRepository accountTypeRepository;
   
    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model model) {
        List<Account> accounts = ar.findAll();
        model.addAttribute("accounts", accounts);
        return "viewAccounts";
    }

    @GetMapping("/deactivateAccount/{accountID}")
    public String deactivateAccount(Model model, @PathVariable int accountID) {
        Optional<Account> accountOptional = ar.findById(accountID);
        if (accountOptional.isPresent()) {
            Account acct = accountOptional.get();
            acct.setAccountIsActive(0);
            ar.save(acct);
        }
        return "redirect:/Teller/viewAccounts";
    }

    @GetMapping("/activateAccount/{accountID}")
    public String activateAccount(Model model, @PathVariable int accountID) {
        Optional<Account> accountOptional = ar.findById(accountID);
        if (accountOptional.isPresent()) {
            Account acct = accountOptional.get();
            acct.setAccountIsActive(1);
            ar.save(acct);
        }
        return "redirect:/Teller/viewAccounts";
    }

    @RequestMapping("/createAccount")
    public String showCreateAccount(Model model) {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        // List<Account> account = ar.findAll();
        model.addAttribute("accountTypes", accountTypes);
        model.addAttribute("account", new Account());
        return "createAccount";
    }

    @PostMapping("/doCreateAccount")
    public String doCreateAccount(@ModelAttribute("account") Account account, Model m) {
        List<Account> accounts = ar.findByAccountTypeAndCustomerNRIC(account.getAccountType(),
                account.getCustomerNRIC());
        if (accounts.size() > 0) {
            m.addAttribute("errorMessage", "An account with the same account type and NRIC already exists.");
            List<AccountType> accountTypes = accountTypeRepository.findAll();
            m.addAttribute("accountTypes", accountTypes);
            return "createAccount";
        } else {
            ar.save(account);
            return "redirect:/Teller/viewAccounts";
        }
    }

    
}
