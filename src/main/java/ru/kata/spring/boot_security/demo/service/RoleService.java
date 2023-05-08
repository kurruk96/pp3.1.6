package ru.kata.spring.boot_security.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

public interface RoleService {
    Set<Role> makeSetRolesFromArray(String[] roles);
}
