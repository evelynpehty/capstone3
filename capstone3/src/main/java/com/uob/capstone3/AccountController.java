package com.uob.capstone3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.uob.capstone3.Entities.AccountTransaction;
import com.uob.capstone3.Repositories.AccountTransactionRepository;


@Controller
public class AccountController {
    @Autowired
    AccountTransactionRepository atr;
    
    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model M){
        return "viewAccounts";
    }

    @GetMapping(value="/accountdashboard")
    public String showAccountDashboard(Model m) {
        m.addAttribute("transactions", atr.findAll());
        return "accountdashboard";
    }
    
}
