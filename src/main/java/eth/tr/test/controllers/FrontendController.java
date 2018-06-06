package eth.tr.test.controllers;

import eth.tr.test.exceptions.EmptyNameException;
import eth.tr.test.messages.Message;
import eth.tr.test.model.Role;
import eth.tr.test.model.User;
import eth.tr.test.repository.RoleRepository;
import eth.tr.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

@Controller
@EnableWebMvc
@RequestMapping("/")
public class FrontendController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @PostMapping("/addRoleToUser")
    public String addRoleToUser(@RequestParam String roleName,
                                @RequestParam Long userId,
                                Model model) {
        try {
            User user = userRepository.findById(userId).get();
            user.addRole(roleRepository.findByName(roleName));
            userRepository.save(user);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", new Message("400", "such role is already assigned to the user"));
            return "error";
        }

        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @PostMapping("/getUsersByName")
    public String getUsersByName(@RequestParam(required = false) String name,
                                 Model model) {
        if (name == null || name.isEmpty()) {
            model.addAttribute("users", userRepository.findAll());
        } else {
            model.addAttribute("users", userRepository.findByName(name));
        }
        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @PostMapping("/getUsersByRole")
    public String getUsersByRole(@RequestParam(required = false) String name,
                                 Model model) {
        if (name == null || name.isEmpty()) {
            model.addAttribute("users", userRepository.findAll());
        } else {
            model.addAttribute("users", userRepository.findByRole(
                    roleRepository.findByName(name).getId()));
        }
        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @PostMapping("/getUserById")
    public String getUserById(@RequestParam(required = false) Long id,
                              Model model) {
        if (id == null) {
            model.addAttribute("users", userRepository.findAll());
        } else {
            ArrayList<User> users = new ArrayList<>();
            User user = userRepository.findById(id).get();
            users.add(user);
            model.addAttribute("users", users);
        }
        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam String name,
                          Model model) throws EmptyNameException {
        if (name == null || name.isEmpty()) {
            model.addAttribute("errorMessage", new Message("400", "Name should be filled"));
            return "error";
        }
        User newUser = new User();
        newUser.setName(name);
        userRepository.save(newUser);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @PostMapping("/addRole")
    public String addRole(@RequestParam String name,
                          Model model) throws EmptyNameException {
        if (roleRepository.countByName(name) != 0) {
            model.addAttribute("errorMessage", new Message("400", "Such role already exists"));
            return "error";
        }
        Role newRole = new Role();
        newRole.setName(name);
        roleRepository.save(newRole);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

}
