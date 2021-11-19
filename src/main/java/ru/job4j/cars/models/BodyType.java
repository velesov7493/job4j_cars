package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tz_body_types")
public class BodyType {

    @Id
    @SequenceGenerator(
            name = "carBodyTypesIdSeq",
            sequenceName = "tz_body_types_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carBodyTypesIdSeq")
    private int id;
    private String name;
    @OneToMany(mappedBy = "bodyType", cascade = CascadeType.ALL)
    private Set<Post> posts;

    public BodyType() {
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

    public void addPost(Post value) {
        value.setBodyType(this);
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
        BodyType bodyType = (BodyType) o;
        return
                id == bodyType.id
                && Objects.equals(name, bodyType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
