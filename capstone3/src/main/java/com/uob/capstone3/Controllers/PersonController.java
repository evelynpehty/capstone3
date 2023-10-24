package com.uob.capstone3.Controllers;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uob.capstone3.Entities.Person;
import com.uob.capstone3.Repositories.PersonRepository;

@Controller
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/tellers")
    public String listTellers(Model model) {
        model.addAttribute("tellers", personRepository.findByRoleIgnoreCase("Teller"));
        return "admin";
    }

    @PostMapping("/addTeller")
    public String addTeller(
            @RequestParam("username") String username,
            @RequestParam("first_name") String first_name,
            @RequestParam("last_name") String last_name,
            @RequestParam("password") String password) {
        Person teller = new Person();
        teller.setFirstName(first_name);
        teller.setLastName(last_name);
        teller.setPassword(password);
        teller.setRole("Teller");
        teller.setUsername(username);
        teller.setCreationDate(LocalDate.now());
        personRepository.save(teller);
        return "redirect:/tellers";
    }

    @PostMapping("/deleteTeller")
    public String deleteTeller(@RequestParam Integer userID) {
        personRepository.deleteByUserID(userID);
        return "redirect:/tellers";
    }

}
