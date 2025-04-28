package com.example.email.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.email.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ForgotPasswordController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }
    
    @PostMapping("/verify-account")
    public String verifyAccount(@RequestParam String email, 
                              @RequestParam String phone,
                            //   @RequestParam LocalDate dob,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        Optional<com.example.email.model.User> userOpt = userService.findByEmailAndPhone(email, phone);
        
        if (userOpt.isPresent()) {
            session.setAttribute("resetEmail", email);
            return "redirect:/reset-password";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials. Please try again.");
            return "redirect:/forgot-password";
        }
    }
    
    @GetMapping("/reset-password")
    public String resetPasswordPage(HttpSession session, Model model) {
        String resetEmail = (String) session.getAttribute("resetEmail");
        
        if (resetEmail == null) {
            return "redirect:/forgot-password";
        }
        
        return "reset-password";
    }
    
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String password,
                               @RequestParam String confirmPassword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        String resetEmail = (String) session.getAttribute("resetEmail");
        
        if (resetEmail == null) {
            return "redirect:/forgot-password";
        }
        
        if (password.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 6 characters long.");
            return "redirect:/reset-password";
        }
        
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/reset-password";
        }
        
        boolean updated = userService.updatePassword(resetEmail, password);
        
        if (updated) {
            session.removeAttribute("resetEmail");
            redirectAttributes.addFlashAttribute("success", "Password reset successfully. Please login with your new password.");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to reset password. Please try again.");
            return "redirect:/reset-password";
        }
    }
}
