package ru.job4j.cars.models;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private String model;
    private String description;
    @Column(name = "is_sold", insertable = false)
    private short sold;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(insertable = false)
    private Date created;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author")
    private User author;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carbrand")
    private Brand carBrand;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bodytype")
    private BodyType bodyType;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Photo photo;

    public Post() {
        price = new BigDecimal(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public float getPrice() {
        return price.floatValue();
    }

    public void setPrice(float value) {
        price = new BigDecimal(value);
    }

    public boolean isNew() {
        long diff = System.currentTimeMillis() - created.getTime();
        int daysDiff = (int) (diff / (1000 * 60 * 60 * 24));
        return daysDiff <= 1;
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
        return id == post.id;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}