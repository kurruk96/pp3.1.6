package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();
    boolean findUsernameInBD(String username);
    User findUserByUsername(String username);
    User findById(Long id);
    void save(User user);
    void deleteById(Long id);
    void update(Long id, User user);
}
