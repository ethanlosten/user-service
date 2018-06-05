package eth.tr.test.controllers;

import eth.tr.test.exceptions.EmptyNameException;
import eth.tr.test.exceptions.RoleExistsException;
import eth.tr.test.messages.Message;
import eth.tr.test.model.Role;
import eth.tr.test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("newRole")
    public Object addNewRole(@RequestParam String name) {
        try {
            if (roleRepository.countByName(name) != 0) {
                throw new RoleExistsException(String.format("Role %s already exists", name));
            }
            Role role = new Role();
            role.setName(name);
            roleRepository.save(role);
            return role;
        } catch (EmptyNameException ex) {
            return new Message("error", ex.getMessage());
        } catch (RoleExistsException ex) {
            return new Message("error", ex.getMessage());
        }
    }

}
