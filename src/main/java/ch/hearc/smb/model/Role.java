package ch.hearc.smb.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<CustomUser> customUsers;


    public Role(String name) {
        this.name = name;
        this.customUsers = new HashSet<>();
    }

    public Role() {
        this("Role_name");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CustomUser> getCustomUsers() {
        return customUsers;
    }

    public void setCustomUsers(Set<CustomUser> customUsers) {
        this.customUsers = customUsers;
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof Role) ) { return false; }
        return Objects.equals(this.name, ((Role) o).name);
    }

}