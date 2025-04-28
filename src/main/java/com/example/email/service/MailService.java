package com.example.email.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.email.model.Mail;
import com.example.email.repository.MailRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MailService {
    @Autowired
    private MailRepository mailRepository;
    
    public void sendMail(Mail mail) {
        mail.setMessageDate(LocalDateTime.now());
        mail.setTrash(null);
        mailRepository.save(mail);
    }
    
    public List<Mail> getInboxMails(String userEmail) {
        return mailRepository.findByReceiverAndTrashIsNullOrderByMessageDateDesc(userEmail);
    }
    
    public List<Mail> getSentMails(String userEmail) {
        return mailRepository.findBySenderOrderByMessageDateDesc(userEmail);
    }
    
    public List<Mail> getTrashMails(String userEmail) {
        return mailRepository.findByReceiverAndTrashIsNotNullOrderByMessageDateDesc(userEmail);
    }
    
    public void moveToTrash(Integer mailId) {
        Optional<Mail> mailOpt = mailRepository.findById(mailId);
        if (mailOpt.isPresent()) {
            Mail mail = mailOpt.get();
            mail.setTrash("YES");
            mailRepository.save(mail);
        }
    }
    
    public void restoreMail(Integer mailId) {
        Optional<Mail> mailOpt = mailRepository.findById(mailId);
        if (mailOpt.isPresent()) {
            Mail mail = mailOpt.get();
            mail.setTrash(null);
            mailRepository.save(mail);
        }
    }
    
    public void deleteMail(Integer mailId) {
        mailRepository.deleteById(mailId);
    }
    
    public void emptyTrash(String userEmail) {
        List<Mail> trashMails = getTrashMails(userEmail);
        mailRepository.deleteAll(trashMails);
    }
}