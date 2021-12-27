package ru.job4j.cars.repositories;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.HibernateUtils;
import ru.job4j.cars.models.Post;
import ru.job4j.cars.repositories.rules.PostStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.function.Function;

public class HbmPostsRepository implements PostStore {

    private static final class Holder {
        private static final HbmPostsRepository INSTANCE = new HbmPostsRepository();
    }

    private static final Logger LOG = LoggerFactory.getLogger(HbmPostsRepository.class);

    private HbmPostsRepository() { }

    public static PostStore getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Post findById(Integer id) {
        Function<Session, Post> f = (s) -> {
            String hql =
                    "SELECT p FROM Post p "
                    + "JOIN FETCH p.photo "
                    + "JOIN FETCH p.carBrand "
                    + "JOIN FETCH p.bodyType "
                    + "JOIN FETCH p.author "
                    + "WHERE p.id = :fId";
            return
                    s.createQuery(hql, Post.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public List<Post> findAll() {
        Function<Session, List<Post>> f = (s) -> {
            String hql =
                    "FROM Post p "
                    + "JOIN FETCH p.author "
                    + "JOIN FETCH p.bodyType "
                    + "JOIN FETCH p.carBrand ";
            return
                    s.createQuery(hql, Post.class)
                    .getResultList();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public List<Post> findAllOfLastDay() {
        Function<Session, List<Post>> f = (s) -> {
            String hql =
                    "FROM Post p "
                    + "JOIN FETCH p.author "
                    + "JOIN FETCH p.bodyType "
                    + "JOIN FETCH p.carBrand "
                    + "WHERE (day(current_timestamp()) - day(p.created)) <= 1";
            return
                    s.createQuery(hql, Post.class)
                    .getResultList();
        };
        return HibernateUtils.select(f);
    }

    @Override
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

    @Override
    public List<Post> findAllByDimensions(
        Boolean isSold, Integer brandId,
        Integer bodyTypeId, Integer authorId
    ) {
        Function<Session, List<Post>> f = (s) -> {
            Map<String, Integer> params = new HashMap<>();
            if (isSold != null) {
                params.put("p.sold", isSold ? 1 : 0);
            }
            if (brandId != null) {
                params.put("p.carBrand.id", brandId);
            }
            if (bodyTypeId != null) {
                params.put("p.bodyType.id", bodyTypeId);
            }
            if (authorId != null) {
                params.put("p.author.id", authorId);
            }
            String cond = "";
            StringJoiner j = new StringJoiner(" AND ");
            if (params.size() > 0) {
                for (Map.Entry<String, Integer> entry : params.entrySet()) {
                    j.add(entry.getKey() + "=" + entry.getValue());
                }
                cond = "WHERE " + j.toString();
            }
            String hql =
                "SELECT p FROM Post p "
                + "JOIN FETCH p.carBrand "
                + "JOIN FETCH p.bodyType "
                + "JOIN FETCH p.author "
                + cond;
            return
                s.createQuery(hql, Post.class)
                .getResultList();
        };
        return HibernateUtils.select(f);
    }

    @Override
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

    @Override
    public boolean deleteById(Integer id) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM Post p WHERE p.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", id)
            .executeUpdate();
        };
        return HibernateUtils.execute(task);
    }
}