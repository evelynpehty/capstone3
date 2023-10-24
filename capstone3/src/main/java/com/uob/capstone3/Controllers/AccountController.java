package com.uob.capstone3.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountTransaction;
import com.uob.capstone3.Entities.AccountType;
import com.uob.capstone3.Repositories.AccountRepository;
import com.uob.capstone3.Repositories.AccountTransactionRepository;
import com.uob.capstone3.Repositories.AccountTypeRepository;

@Controller
public class AccountController {
    @Autowired
    private AccountRepository ar;

    @Autowired
    AccountTransactionRepository atr;

    @Autowired
    AccountTypeRepository accountTypeRepository;

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

    @RequestMapping("/createAccount")
    public String showCreateAccount(Model model) {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        List<Account> account = ar.findAll();
        model.addAttribute("accountTypes", accountTypes);
        model.addAttribute("account", account);
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public String doCreateAccount(@RequestParam String customerNRIC, @RequestParam String customerName,
            @RequestParam int accountTypeID, Model m) {
        Account account = new Account();
        List<Account> existingAccounts = ar.findByCustomerNRIC(customerNRIC);
        AccountType accountType = accountTypeRepository.findById(accountTypeID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account type ID"));
        if (existingAccounts.size() == 0) {
            account.setAccountType(accountType);
            account.setCustomerNRIC(customerNRIC);
            account.setCustomerName(customerName);

            ar.save(account);
            String alert = "Account created!";
            m.addAttribute("alert", alert);
            return "redirect:/viewAccounts";
        } else {
            for (Account accountRef : existingAccounts) {
                if (accountRef.getAccountType().getAccountTypeName()
                        .equalsIgnoreCase(accountType.getAccountTypeName())) {
                    String alert = "Account already exists!";
                    m.addAttribute("alert", alert);
                    return "redirect:/createAccount";
                }
            }
            account.setAccountType(accountType);
            account.setCustomerNRIC(customerNRIC);
            if (!existingAccounts.get(0).getCustomerName().equalsIgnoreCase(customerName)) {
                account.setCustomerName(existingAccounts.get(0).getCustomerName());
                String alert = "Account created using previously registered customer name!";
                m.addAttribute("alert", alert);
            } else {
                account.setCustomerName(customerName);
                String alert = "Account created!";
                m.addAttribute("alert", alert);
            }

            ar.save(account);
            return "redirect:/viewAccounts";
        }
    }

    @GetMapping(value="/accountdashboard/{id}")
    public String showAccountDashboard(@PathVariable(value = "id") int id, Model m) {
        Account account = ar.findById(id).get();
        List<AccountTransaction> transactions = atr.findAllByAccountIDOrTransactionPartyAccountIDOrderByTransactionIDDesc(account, account);
        m.addAttribute("account", account);
        m.addAttribute("transactions", transactions);
        return "accountdashboard";
    }

    @GetMapping(value="/transact/{type}/{id}")
    public String transact(@PathVariable(value = "type", required = true) String type, @PathVariable(value = "id", required = true) int id, Model model) {
        model.addAttribute("account", ar.findById(id).get());
        model.addAttribute("type", type);
        return "transact";
    }

    @PostMapping(value="/transact/{type}/{id}")
    public String transacting(
        @PathVariable(value = "type", required = true) String type,
        @PathVariable(value = "id", required = true) int id,
        @RequestParam(value = "payeeId", required = false) Integer payeeId,
        @RequestParam(value = "amount", required = true) double amount,
        @RequestParam(value = "description", required = false) String description
    ) {
        //TODO: process POST request
        switch (type) {
            case "deposit":
                
                break;
            case "withdraw":
                break;
            case "transfer":
                break;
        
            default:
                break;
        }
        return "";
    }
}
