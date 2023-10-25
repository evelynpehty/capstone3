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
import com.uob.capstone3.Entities.Person;
import com.uob.capstone3.Repositories.AccountRepository;
import com.uob.capstone3.Repositories.AccountTypeRepository;
import com.uob.capstone3.Repositories.PersonRepository;

@Controller
public class AccountController {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository ar;

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/")
    public String showMain(Model model, Principal principal) {
        List<Person> usersList = (List<Person>) personRepository.findAll();
        System.out.println(principal.getName());
        model.addAttribute("usersList", usersList);
        model.addAttribute("username", principal.getName());
        model.addAttribute("principal", principal);

        return "redirect:/viewAccounts";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

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
        return "redirect:/viewAccounts";
    }

    @GetMapping("/activateAccount/{accountID}")
    public String activateAccount(Model model, @PathVariable int accountID) {
        Optional<Account> accountOptional = ar.findById(accountID);
        if (accountOptional.isPresent()) {
            Account acct = accountOptional.get();
            acct.setAccountIsActive(1);
            ar.save(acct);
        }
        return "redirect:/viewAccounts";
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
            return "redirect:/viewAccounts";
        }
    }

    // @PostMapping("/createAccount")
    // public String doCreateAccount(@RequestParam String customerNRIC,
    // @RequestParam String customerName,
    // @RequestParam int accountTypeID, Model m) {
    // Account account = new Account();
    // List<Account> existingAccounts = ar.findByCustomerNRIC(customerNRIC);
    // AccountType accountType = accountTypeRepository.findById(accountTypeID)
    // .orElseThrow(() -> new IllegalArgumentException("Invalid account type ID"));
    // if (existingAccounts.size() == 0) {
    // account.setAccountType(accountType);
    // account.setCustomerNRIC(customerNRIC);
    // account.setCustomerName(customerName);

    // ar.save(account);
    // String alert = "Account created!";
    // m.addAttribute("alert", alert);
    // return "redirect:/viewAccounts";
    // } else {
    // for (Account accountRef : existingAccounts) {
    // if (accountRef.getAccountType().getAccountTypeName()
    // .equalsIgnoreCase(accountType.getAccountTypeName())) {
    // String alert = "Account already exists!";
    // m.addAttribute("alert", alert);
    // return "redirect:/createAccount";
    // }
    // }
    // account.setAccountType(accountType);
    // account.setCustomerNRIC(customerNRIC);
    // if
    // (!existingAccounts.get(0).getCustomerName().equalsIgnoreCase(customerName)) {
    // account.setCustomerName(existingAccounts.get(0).getCustomerName());
    // String alert = "Account created using previously registered customer name!";
    // m.addAttribute("alert", alert);
    // } else {
    // account.setCustomerName(customerName);
    // String alert = "Account created!";
    // m.addAttribute("alert", alert);
    // }

    // ar.save(account);
    // return "redirect:/viewAccounts";
    // }
    // }
}
