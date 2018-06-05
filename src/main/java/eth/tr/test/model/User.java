package eth.tr.test.model;

import eth.tr.test.exceptions.EmptyNameException;
import eth.tr.test.exceptions.RoleExistsException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "roles_has_users",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id")}
    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) throws NullPointerException {
        if (role == null) {
            throw new NullPointerException("Role is null");
        }
        roles.add(role);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) throws EmptyNameException {
        if (name.isEmpty()) {
            throw new EmptyNameException("User's name is empty");
        }
        this.name = name;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
