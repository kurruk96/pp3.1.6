package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String allUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("userAddNewUser", new User());
        return "admin";
    }

    @PostMapping("new")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesList[]") String[] roles) {
            user.setRoles(roleService.makeSetRolesFromArray(roles));
            userService.save(user);
            return "redirect:/admin";
    }

    @PatchMapping("{id}/edit")
    public String update(@ModelAttribute("user")
                             User user,
                         @PathVariable("id") Long id,
                         @RequestParam(value = "rolesListEdit[]", required = false) String[] roles) {
            user.setRoles(roleService.makeSetRolesFromArray(roles));
            userService.update(id, user);
            return "redirect:/admin";
    }

    @DeleteMapping("{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
