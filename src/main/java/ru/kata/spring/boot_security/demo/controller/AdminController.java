package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("users/{id}")
    public String adminPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "show_user_by_id";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("users/new")
    public String addNewUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @RequestParam(defaultValue = "ROLE_USER", value = "role") String[] roles) {
        if (!userService.findByName(user.getUsername())) {
            bindingResult.addError(new ObjectError("usernameNotUnique", "Username is already taken"));
        }
        if (bindingResult.hasErrors()) {
            return "new";
        }
        else {
            user.setRoles(roleService.makeSetRolesFromArray(roles));
            userService.save(user);
            return "redirect:/admin/users";
        }
    }

    @GetMapping("users/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PatchMapping("users/{id}/edit")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") Long id,
                         @RequestParam(defaultValue = "ROLE_USER", value = "role") String[] roles) {
        if (!userService.findByName(user.getUsername())) {
            bindingResult.addError(new ObjectError("usernameNotUnique", "Username is already taken"));
        }
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            user.setRoles(roleService.makeSetRolesFromArray(roles));
            userService.update(id, user);
            return "redirect:/admin/users";
        }
    }

    @DeleteMapping("users/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
