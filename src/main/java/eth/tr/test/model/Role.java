package eth.tr.test.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import eth.tr.test.exceptions.EmptyNameException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) throws EmptyNameException {
        if (name.isEmpty()) {
            throw new EmptyNameException("The name for role is empty");
        }
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
