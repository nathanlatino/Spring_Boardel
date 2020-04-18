package ch.hearc.smb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;


@Entity
@Table(name = "user")
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    public CustomUser() {
        this.comments = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.roles = new HashSet<>();
        this.username = "";
        this.email = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRole(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.roles.add(role);
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean equals(Object customUser) {
        if (customUser == this) { return true; }
        if (!(customUser instanceof CustomUser)) { return false; }
        return Objects.equals(this.id, ((CustomUser) customUser).id);
    }

    @Override
    public int hashCode() {
        return username.hashCode() * email.hashCode();
    }
}