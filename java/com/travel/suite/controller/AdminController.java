package com.travel.suite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel.suite.model.City;
import com.travel.suite.model.Destination;
import com.travel.suite.model.User;
import com.travel.suite.repository.UserRepository;
import com.travel.suite.service.CityService;
import com.travel.suite.service.DestinationService;
import com.travel.suite.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
@Autowired
    private UserRepository userRepository;

    @Autowired
    private DestinationService destinationService;
  @Autowired
private CityService cityService;
@Autowired
private UserService userService; 
@GetMapping("/manage-users")
public String manageUsers(Model model, HttpSession session) {
    if (!isAdmin(session)) {
        return "redirect:/home";
    }

    List<User> users = userService.getAllUsers();
    model.addAttribute("users", users);
    return "admin/manage-users"; // View file: manage-users.html
}

@PostMapping("/users/delete/{id}")
public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session) {
    if (!isAdmin(session)) {
        return "redirect:/home";
    }
    
    boolean deleted = userService.deleteUserByIdIfRoleUser(id);
    
    if (deleted) {
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
    } else {
        redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user. User not found or has admin role.");
    }
    
    return "redirect:/admin/manage-users";
}
    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("loggedInRole");
        return role != null && role.equalsIgnoreCase("ADMIN");
    }

   
    @GetMapping("/home")
    public String adminHome(HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/home";
        }
        return "admin"; 
    }
    @GetMapping("/manage-destinations")
    public String manageDestinations(Model model) {
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations); 
        return "/admin/manage-destinations"; 
    }

    @PostMapping("/add-destination")
    public String addDestination(@ModelAttribute("destination") Destination destination, RedirectAttributes redirectAttributes) {
       
        String cityName = destination.getCity().getName();
        City city = cityService.findByName(cityName);
    
        if (city == null) {
            city = new City();
            city.setName(cityName);
            city = cityService.saveCity(city); 
        }
        if (cityName == null || cityName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "City name is required.");
            return "redirect:/admin/add-destination";
        }
        if (cityName == null || cityName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "City name is required.");
            return "redirect:/admin/add-destination";
        }
                
    
        destination.setCity(city); 
        destinationService.saveDestination(destination);
    
        redirectAttributes.addFlashAttribute("message", "Destination added successfully!");
        return "redirect:/admin/manage-destinations";
    }
    @GetMapping("/add-destination")
public String showAddDestinationForm(Model model, HttpSession session) {
    if (!isAdmin(session)) {
        return "redirect:/home";
    }
    model.addAttribute("destination", new Destination());
    model.addAttribute("cities", cityService.findAll()); 
    return "admin/add-destination";
}

    @GetMapping("/edit-destination/{id}")
    public String showEditDestinationForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/home";
        }

        Destination destination = destinationService.getDestinationById(id);
        if (destination != null) {
            model.addAttribute("destination", destination);
            return "edit_destination"; 
        } else {
            return "redirect:/admin/manage-destinations";
        }
    }
    @PostMapping("/update-destination/{id}")
    public String updateDestination(@PathVariable Long id, @ModelAttribute Destination destination, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/home";
        }
        destination.setId(id); 
        destinationService.saveDestination(destination);
        return "redirect:/admin/manage-destinations";
    }
    @GetMapping("/delete-destination/{id}")
    public String deleteDestination(@PathVariable Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/home";
        }
    
        System.out.println("Attempting to delete destination with ID: " + id); // Log for debugging
    
        destinationService.deleteDestinationById(id);
        return "redirect:/admin/manage-destinations";
    }
}