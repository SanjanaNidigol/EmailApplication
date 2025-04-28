package com.example.email.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.email.model.Mail;
import com.example.email.model.User;
import com.example.email.service.MailService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MailController {
    @Autowired
    private MailService mailService;
    
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        List<Mail> inboxMails = mailService.getInboxMails(loggedInUser.getEmail());
        model.addAttribute("mails", inboxMails);
        model.addAttribute("activeTab", "inbox");
        
        return "home";
    }
    
    @GetMapping("/compose")
    public String composePage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        model.addAttribute("mail", new Mail());
        model.addAttribute("activeTab", "compose");
        
        return "compose";
    }
    
    @PostMapping("/send")
    public String sendMail(@ModelAttribute Mail mail, HttpSession session, 
                         RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        mail.setSender(loggedInUser.getEmail());
        mailService.sendMail(mail);
        
        redirectAttributes.addFlashAttribute("success", "Mail sent successfully");
        return "redirect:/home";
    }
    
    @GetMapping("/sent")
    public String sentPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        List<Mail> sentMails = mailService.getSentMails(loggedInUser.getEmail());
        model.addAttribute("mails", sentMails);
        model.addAttribute("activeTab", "sent");
        
        return "sent";
    }
    
    @GetMapping("/trash")
    public String trashPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        List<Mail> trashMails = mailService.getTrashMails(loggedInUser.getEmail());
        model.addAttribute("mails", trashMails);
        model.addAttribute("activeTab", "trash");
        
        return "trash";
    }
    
    @GetMapping("/trash/{id}")
    public String moveToTrash(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        mailService.moveToTrash(id);
        return "redirect:/home";
    }
    
    @GetMapping("/restore/{id}")
    public String restoreMail(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        mailService.restoreMail(id);
        return "redirect:/trash";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteMail(@PathVariable Integer id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        mailService.deleteMail(id);
        return "redirect:/trash";
    }
    
    @GetMapping("/empty-trash")
    public String emptyTrash(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        
        if (loggedInUser == null) {
            return "redirect:/";
        }
        
        mailService.emptyTrash(loggedInUser.getEmail());
        return "redirect:/trash";
    }
}
