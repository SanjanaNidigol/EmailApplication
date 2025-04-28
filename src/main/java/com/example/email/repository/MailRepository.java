package com.example.email.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.email.model.Mail;
import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, Integer> {
    List<Mail> findByReceiverAndTrashOrderByMessageDateDesc(String receiver, String trash);
    List<Mail> findBySenderOrderByMessageDateDesc(String sender);
    List<Mail> findByReceiverAndTrashIsNullOrderByMessageDateDesc(String receiver);
    List<Mail> findByReceiverAndTrashIsNotNullOrderByMessageDateDesc(String receiver);
}
