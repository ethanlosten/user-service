package eth.tr.test.controllers;

import eth.tr.test.exceptions.EmptyNameException;
import eth.tr.test.exceptions.RoleExistsException;
import eth.tr.test.messages.Message;
import eth.tr.test.model.User;
import eth.tr.test.repository.RoleRepository;
import eth.tr.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("allUsers")
    public List<User> listAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("getUsersByName")
    public List<User> getUserByName(@RequestParam String name) {
        return (List<User>) userRepository.findByName(name);
    }

    @GetMapping("getUsersById")
    public User getUserById(@RequestParam Long id) {
        return userRepository.findById(id).get();
    }

    @GetMapping("getUsersByRole")
    public List<User> getUsersByRole(@RequestParam String role) {
        return (List<User>) userRepository.findByRole(
                roleRepository.findByName(role).getId());
    }

    @PostMapping("newUser")
    public Message addNewUser(@RequestParam String name) {
        try {
            User user = new User();
            user.setName(name);
            userRepository.save(user);
            return new Message("success", String.format("User %s was added", name));
        } catch (EmptyNameException ex) {
            return new Message("error", ex.getMessage());
        }
    }

    @PostMapping("assignUserToRoles")
    public Message assingUserToRole(@RequestParam Long userId,
                                    @RequestParam List<String> roleNames) {
        try {
            User user = userRepository.findById(userId).get();
            for (String role : roleNames) {
                user.addRole(roleRepository.findByName(role));
            }
            userRepository.save(user);
            return new Message("success", "User was assigned to the role");
        } catch (NullPointerException ex) {
            return new Message("error", ex.getMessage());
        }
    }
}
