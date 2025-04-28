package com.example.email.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.email.model.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
    
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        model.addAttribute("user", loggedInUser);
        model.addAttribute("activeTab", "profile");
        
        return "profile";  
    }
}
