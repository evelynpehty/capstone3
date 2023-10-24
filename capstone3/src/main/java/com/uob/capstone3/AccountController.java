package com.uob.capstone3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping(value="/transact/{type}")
    public String transact(@PathVariable(value = "type", required = true) String type, Model model) {
        model.addAttribute("type", type);
        return "transact";
    }
}
