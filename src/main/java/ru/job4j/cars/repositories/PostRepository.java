package ru.job4j.cars.repositories;

import org.hibernate.Session;
import ru.job4j.cars.HibernateUtils;
import ru.job4j.cars.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class PostRepository {

    private static final PostRepository INSTANCE = new PostRepository();

    private PostRepository() { }

    public static PostRepository getInstance() {
        return INSTANCE;
    }

    public List<Post> findAll() {
        Function<Session, List<Post>> f = (s) -> {
            String hql =
                    "FROM Post p "
                    + "JOIN FETCH p.author, p.bodyType, p.carBrand, p.photo";
            return
                    s.createQuery(hql, Post.class)
                    .getResultList();
        };
        return HibernateUtils.select(f);
    }

    public List<Post> findAllOfLastDay() {
        Function<Session, List<Post>> f = (s) -> {
            String hql =
                    "FROM Post p "
                    + "JOIN FETCH p.author, p.bodyType, p.carBrand "
                    + "WHERE (day(current_timestamp()) - day(p.created)) <= 1";
            return
                    s.createQuery(hql, Post.class)
                    .getResultList();
        };
        return HibernateUtils.select(f);
    }

    public List<Post> findAllWithPhoto() {
        Function<Session, List<Post>> f = (s) -> {
            String hql =
                    "SELECT p FROM Post p "
                    + "FETCH ALL PROPERTIES "
                    + "WHERE p.photo IS NOT NULL";
            return
                    s.createQuery(hql, Post.class)
                    .getResultList();
        };
        return HibernateUtils.select(f);
    }

    public List<Post> findByBrand(int brandId) {
        Function<Session, List<Post>> f = (s) -> {
            String hql =
                    "SELECT p FROM Post p "
                    + "JOIN FETCH p.carBrand, p.bodyType, p.author "
                    + "WHERE p.carBrand.id = :fId";
            return
                    s.createQuery(hql, Post.class)
                    .setParameter("fId", brandId)
                    .getResultList();
        };
        return HibernateUtils.select(f);
    }

    public Post getById(int postId) {
        Function<Session, Post> f = (s) -> {
            String hql =
                    "SELECT p FROM Post p "
                    + "JOIN FETCH p.photo, p.carBrand, p.bodyType, p.author "
                    + "WHERE p.id = :fId";
            return
                    s.createQuery(hql, Post.class)
                    .setParameter("fId", postId)
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    public boolean save(Post value) {
        Consumer<Session> task = (s) -> {
            if (value.getId() == 0) {
                s.save(value);
            } else {
                s.update(value);
            }
        };
        return HibernateUtils.execute(task);
    }

    public boolean deleteById(int postId) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM Post p WHERE p.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", postId)
            .executeUpdate();
        };
        return HibernateUtils.execute(task);
    }
}
