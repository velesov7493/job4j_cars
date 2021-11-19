package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tz_users")
public class User {

    @Id
    @SequenceGenerator(
            name = "usersIdSeq",
            sequenceName = "tz_users_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    private int id;
    private String name;
    private String login;
    private String email;
    private String password;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Post> posts;

    public User() {
        posts = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPost(Post value) {
        value.setAuthor(this);
        posts.add(value);
    }

    public void deletePost(Post value) {
        posts.remove(value);
    }

    public Set<Post> getPosts() {
        return new HashSet<>(posts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return
                id == user.id
                && Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, email, password);
    }
}
