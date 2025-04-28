 package com.example.email.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.email.model.User;
import com.example.email.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String loginProcess(@RequestParam String email, @RequestParam String password, 
                              HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.authenticateUser(email, password);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("loggedInUser", user);
            return "redirect:/home";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/";
        }
    }
    
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signupProcess(@Valid @ModelAttribute("user") User user, 
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if (user.getDate() != null) {
            LocalDate today = LocalDate.now();
            if (user.getDate().isAfter(today)) {
                result.addError(new FieldError("user", "date", "Birth date cannot be in the future"));
            }
            // minimum age requirement 10 years
            LocalDate minAgeDate = today.minus(10, ChronoUnit.YEARS);
            if (user.getDate().isAfter(minAgeDate)) {
                result.addError(new FieldError("user", "date", "Minimum age requirement is 10 years"));
            }
        }
        
        if (result.hasErrors()) {
            return "signup";
        }
        
        boolean registered = userService.registerUser(user);
        
        if (registered) {
            redirectAttributes.addFlashAttribute("success", "Account created successfully. Please login.");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Email already exists");
            return "redirect:/signup";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}