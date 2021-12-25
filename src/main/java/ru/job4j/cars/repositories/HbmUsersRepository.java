package ru.job4j.cars.repositories;

import org.hibernate.Session;
import ru.job4j.cars.HibernateUtils;
import ru.job4j.cars.Security;
import ru.job4j.cars.models.User;
import ru.job4j.cars.repositories.rules.UserStore;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HbmUsersRepository implements UserStore {

    private static final HbmUsersRepository INSTANCE = new HbmUsersRepository();

    private HbmUsersRepository() { }

    public static UserStore getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        Function<Session, List<User>> f = (s) ->
            s.createQuery("FROM User", User.class)
            .getResultList();
        return HibernateUtils.select(f);
    }

    @Override
    public User findById(Integer id) {
        Function<Session, User> f = (s) -> {
            String hql = "FROM User u WHERE u.id = :fId";
            return
                    s.createQuery(hql, User.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Function<Session, User> f = (s) -> {
            String hql = "FROM User u WHERE u.login = :fLogin AND u.password = :fPass";
            return
                    s.createQuery(hql, User.class)
                    .setParameter("fLogin", login)
                    .setParameter("fPass", Security.getSHA1(password))
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public boolean save(User value) {
        Consumer<Session> task = (s) -> {
            value.setPassword(Security.getSHA1(value.getPassword()));
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
            String hql = "DELETE FROM User u WHERE u.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", id)
            .executeUpdate();
        };
        return HibernateUtils.execute(task);
    }
}
