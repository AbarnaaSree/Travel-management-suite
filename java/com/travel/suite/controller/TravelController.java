package com.travel.suite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.suite.model.Destination;
import com.travel.suite.model.User;
import com.travel.suite.repository.DestinationRepository;
import com.travel.suite.service.DestinationService;
import com.travel.suite.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class TravelController {

    @Autowired
    private UserService userService;

    @Autowired
    private DestinationService destinationService;
    @GetMapping("/")
    public String showWelcomePage() {
        return "welcome";  
    }
    
    @Autowired
    private DestinationRepository destinationRepository;
    @GetMapping("/login")
    public String loginPage() {
        System.out.println("Custom login page is being accessed");
        return "login";  
    }
    @GetMapping("/start")
    public String redirectToLogin() {
        return "redirect:/login";  
    }
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              Model model,
                              HttpSession session) {
        User user = userService.findByUsername(username);
    
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInRole", user.getRole());
            session.setAttribute("loggedInUser", user);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/home";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Invalid credentials. Please try again.");
            return "login";  
        }
    }
    @GetMapping("/home")
public String home(Model model) {
    List<String> countries = destinationRepository.findAllCountries();
    model.addAttribute("countries", countries);
    return "index";  
}

    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        String role = (String) session.getAttribute("loggedInRole");

        if ("ADMIN".equalsIgnoreCase(role)) {
            return "admin";  
        } else {
            return "redirect:/home";  
        }
    }
    
    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "signup";  
    }
@RequestMapping("/logout")
public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
    session.invalidate();
    return "redirect:/login";
}

    @PostMapping("/signup")
    public String handleSignup(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               Model model) {
        User existingUser = userService.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "Username is already taken.");
            return "signup";  
        }

        User newUser = new User(username, password, "USER", email);
        userService.saveUser(newUser);

        model.addAttribute("message", "Signup successful! Please login.");
        return "login";  
    }
    @GetMapping("/result")
    public String showAllCountries(Model model) {
        List<String> countries = destinationRepository.findAllCountries();
        model.addAttribute("countries", countries);
        return "result";  
    }
    @PostMapping("/search")
    public String searchDestinations(@RequestParam String country,
                                     @RequestParam double cost,
                                     @RequestParam int days,
                                     Model model) {
        try {
            if (country.isEmpty() || cost <= 0 || days <= 0) {
                model.addAttribute("error", "Please provide valid inputs.");
                return "index";
            }

            String formattedCountry = country.substring(0, 1).toUpperCase() + country.substring(1).toLowerCase();
            List<Destination> destinations = destinationService.findDestinations(formattedCountry, cost, days);

            model.addAttribute("country", formattedCountry);
            model.addAttribute("destinations", destinations);

            return "result";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Unexpected error: " + e.getMessage());
            return "index";
        }
    }
    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("error", "An error occurred. Please try again.");
        return "index";
    }
}
