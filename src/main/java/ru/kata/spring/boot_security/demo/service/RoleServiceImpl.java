package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> makeSetRolesFromArray(String[] roles) {
        Set<Role> ourRoles = new HashSet<>();
        for(String role : roles) {
            ourRoles.add(roleRepository.findByName(role));
        }
        return ourRoles;
    }

    @Override
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }


}
