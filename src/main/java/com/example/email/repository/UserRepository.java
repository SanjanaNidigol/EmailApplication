package com.example.email.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.email.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> { 
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}
