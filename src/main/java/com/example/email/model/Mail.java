package com.example.email.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mail123")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String sender;
    
    private String receiver;
    
    private String subject;
    
    @Column(length = 2500)
    private String message;
    
    private String trash;
    
    @Column(name = "MESSAGEDATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime messageDate;
    
    // getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getSender() {
        return sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public String getReceiver() {
        return receiver;
    }
    
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTrash() {
        return trash;
    }
    
    public void setTrash(String trash) {
        this.trash = trash;
    }
    
    public LocalDateTime getMessageDate() {
        return messageDate;
    }
    
    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }
}
