package com.uob.capstone3.Controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uob.capstone3.Entities.Account;
import com.uob.capstone3.Entities.AccountTransaction;
import com.uob.capstone3.Entities.AccountType;
import com.uob.capstone3.Repositories.AccountRepository;
import com.uob.capstone3.Repositories.AccountTransactionRepository;
import com.uob.capstone3.Repositories.AccountTypeRepository;

@Controller
@RequestMapping("/Teller")
public class AccountController {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository ar;

    @Autowired
    AccountTransactionRepository atr;

    @Autowired
    AccountTypeRepository accountTypeRepository;
   
    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model model) {
        List<Account> accounts = ar.findAll();
        model.addAttribute("accounts", accounts);
        model.addAttribute("page", "viewAccounts");
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
        model.addAttribute("page", "createAccount");
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
            m.addAttribute("page", "createAccount");
            return "createAccount";
        } else {
            ar.save(account);
            return "redirect:/Teller/viewAccounts";
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
        @RequestParam(value = "description", required = false) String description,
        RedirectAttributes redirectAttributes
    ) {
        //TODO: process POST request
        String message = null, messageType = null;
        Account account = ar.findById(id).get();

        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccountID(account);
        transaction.setTransactionType(type.substring(0, 1).toUpperCase() + type.substring(1));
        transaction.setTransactionDescription(description);
        transaction.setTransactionAmount(amount);
        transaction.setTransactionDate(LocalDate.now());

        switch (type) {
            case "deposit":
                account.setAccountBalance(account.getAccountBalance() + amount);
                message = "Your deposit was successful.";
                messageType = "success";
                break;

            case "withdraw":
                if (amount > account.getAccountBalance()) {
                    redirectAttributes.addFlashAttribute("message", "Your withdrawal failed. You do not have sufficient fund.");
                    redirectAttributes.addFlashAttribute("messageType", "error");
                    return "redirect:/Teller/accountdashboard/" + id;
                } else {
                    account.setAccountBalance(account.getAccountBalance() - amount);
                    message = "Your withdrawal was successful.";
                    messageType = "success";
                }
                break;

            case "transfer":
                Optional<Account> optionalPayee = ar.findById(payeeId);
                if (optionalPayee.isEmpty()) {
                    redirectAttributes.addFlashAttribute("message", "Your fund transfer failed. The payee does not exist.");
                    redirectAttributes.addFlashAttribute("messageType", "error");
                    return "redirect:/Teller/accountdashboard/" + id;
                } else {
                    Account payee = optionalPayee.get();
                    if (payee.getAccountIsActive() == 0) {
                        redirectAttributes.addFlashAttribute("message", "Your fund transfer failed. The payee's account is inactive.");
                        redirectAttributes.addFlashAttribute("messageType", "error");
                        return "redirect:/Teller/accountdashboard/" + id;
                    } else {
                        if (amount > account.getAccountBalance()) {
                            redirectAttributes.addFlashAttribute("message", "Your fund transfer failed. You do not have sufficient fund.");
                            redirectAttributes.addFlashAttribute("messageType", "error");
                            return "redirect:/Teller/accountdashboard/" + id;
                        } else {
                            account.setAccountBalance(account.getAccountBalance() - amount);
                            payee.setAccountBalance(payee.getAccountBalance() + amount);
                            transaction.setTransactionPartyAccountID(payee);

                            message = "Your fund transfer was successful.";
                            messageType = "success";
                            ar.save(payee);
                        }
                    }
                }
                break;
        
            default:
                break;
        }

        ar.save(account);
        atr.save(transaction);

        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("messageType", messageType);
        return "redirect:/Teller/accountdashboard/" + id;
    }

    @GetMapping(value="/toggleActive/{id}")
    public String toggleActive(@PathVariable(value = "id", required = true) int id, RedirectAttributes redirectAttributes) {
        Account account = ar.findById(id).get();
        account.setAccountIsActive(1 - account.getAccountIsActive());
        ar.save(account);

        redirectAttributes.addFlashAttribute("message", "Your account has been " + (account.getAccountIsActive() == 1 ? "activated" : "deactivated") + " successfully.");
        return "redirect:/Teller/accountdashboard/" + id;
    }
}
