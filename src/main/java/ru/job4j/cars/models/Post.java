package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tr_posts")
public class Post {

    @Id
    @SequenceGenerator(
            name = "postsIdSeq",
            sequenceName = "tr_posts_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postsIdSeq")
    private int id;
    private String description;
    @Column(name = "is_sold")
    private short sold;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author")
    private User author;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carbrand")
    private Brand carBrand;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bodytype")
    private BodyType bodyType;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_photo")
    private Photo photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSold() {
        return sold == 1;
    }

    public void setSold(boolean value) {
        sold = value ? (short) 1 : 0;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Brand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(Brand carBrand) {
        this.carBrand = carBrand;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return
                id == post.id
                && sold == post.sold
                && Objects.equals(description, post.description)
                && Objects.equals(created, post.created)
                && Objects.equals(author, post.author)
                && Objects.equals(carBrand, post.carBrand)
                && Objects.equals(bodyType, post.bodyType)
                && Objects.equals(photo, post.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, sold, created, author, carBrand, bodyType, photo);
    }
}