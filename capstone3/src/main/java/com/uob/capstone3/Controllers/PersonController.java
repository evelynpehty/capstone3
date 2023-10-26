package com.uob.capstone3.Controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.uob.capstone3.Entities.Person;
import com.uob.capstone3.Repositories.PersonRepository;

@Controller
@RequestMapping("/Admin")
public class PersonController {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/viewTellers")
    public String listTellers(Model model) {
        model.addAttribute("tellers", personRepository.findByRoleIgnoreCase("Teller"));
        model.addAttribute("page", "viewTellers");
        return "admin";
    }

    @GetMapping("/addTeller")
    public String showAddTeller(Model model) {
        model.addAttribute("tellers", personRepository.findByRoleIgnoreCase("Teller"));
        model.addAttribute("page", "addTeller");
        return "addTeller";
    }

    @PostMapping("/doAddTeller")
    public String addTeller(
            @RequestParam("username") String username,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("password") String password,
            Model model) {
        try {
            Person teller = new Person();
            teller.setFirstName(firstName);
            teller.setLastName(lastName);
            teller.setPassword(passwordEncoder.encode(password));
            teller.setRole("Teller");
            teller.setUsername(username);
            teller.setCreationDate(LocalDate.now());
            personRepository.save(teller);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("tellers", personRepository.findByRoleIgnoreCase("Teller"));
            model.addAttribute("duplicateUsernameError",
                    "Username is already in use. Please choose a different username.");
            model.addAttribute("page", "addTeller");
            return "addTeller";
        }
        return "redirect:/Admin/viewTellers";
    }

    @PostMapping("/deleteTeller")
    public String deleteTeller(@RequestParam Integer userID) {
        personRepository.deleteByUserID(userID);
        return "redirect:/Admin/viewTellers";
    }

    @PostMapping("/editTeller")
    public String editTeller(
            @RequestParam("editUserID") int userID,
            @RequestParam("editUsername") String username,
            @RequestParam("editFirstName") String firstName,
            @RequestParam("editLastName") String lastName,
            @RequestParam("editPassword") String password,
            Model model) {
        try {
            personRepository.updateUser(username, firstName, lastName, passwordEncoder.encode(password), userID);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("tellers", personRepository.findByRoleIgnoreCase("Teller"));
            model.addAttribute("duplicateUsernameError",
                    "Username is already in use. Please choose a different username.");
            model.addAttribute("page", "viewTellers");
            return "admin";
        }
        return "redirect:/Admin/viewTellers";
    }
}
