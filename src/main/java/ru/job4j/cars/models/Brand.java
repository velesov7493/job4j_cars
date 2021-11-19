package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tz_brands")
public class Brand {

    @Id
    @SequenceGenerator(
            name = "carBrandsIdSeq",
            sequenceName = "tz_brands_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carBrandsIdSeq")
    private int id;
    private String name;
    @OneToMany(mappedBy = "carBrand", cascade = CascadeType.ALL)
    private Set<Post> posts;

    public Brand() {
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
        value.setCarBrand(this);
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
        Brand brand = (Brand) o;
        return
                id == brand.id
                && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
