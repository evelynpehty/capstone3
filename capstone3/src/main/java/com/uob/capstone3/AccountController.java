package com.uob.capstone3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AccountController {
    
    @GetMapping("/viewAccounts")
    public String showViewAccounts(Model M){
        return "viewAccounts";
    }

    @GetMapping(value="/accountdashboard")
    public String showAccountDashboard() {
        return "accountdashboard";
    }
    
}
