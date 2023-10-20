package com.uob.capstone3;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uob.capstone3.Entities.Person;
import com.uob.capstone3.Repositories.PersonRepository;

@Controller
public class AccountController {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/")
    public String showMain(Model model, Principal principal) {
        List<Person> usersList = (List<Person>) personRepository.findAll();
        System.out.println(principal.getName());
        model.addAttribute("usersList", usersList);
        model.addAttribute("username", principal.getName());
        model.addAttribute("principal", principal);
        return "index";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

}
