package ru.job4j.cars.repositories;

import org.hibernate.Session;
import ru.job4j.cars.HibernateUtils;
import ru.job4j.cars.models.BodyType;
import ru.job4j.cars.repositories.rules.Store;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HbmBodyTypesRepository implements Store<Integer, BodyType> {

    private static final class Holder {
        private static final HbmBodyTypesRepository INSTANCE = new HbmBodyTypesRepository();
    }

    private HbmBodyTypesRepository() { }

    public static Store<Integer, BodyType> getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public List<BodyType> findAll() {
        Function<Session, List<BodyType>> f = (s) ->
            s.createQuery("FROM BodyType bt ORDER BY bt.id", BodyType.class)
            .getResultList();
        return HibernateUtils.select(f);
    }

    @Override
    public BodyType findById(Integer id) {
        Function<Session, BodyType> f = (s) -> {
            String hql = "FROM BodyType bt WHERE bt.id = :fId";
            return
                    s.createQuery(hql, BodyType.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public boolean save(BodyType value) {
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
            String hql = "DELETE FROM BodyType bt WHERE bt.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", id)
            .executeUpdate();
        };
        return HibernateUtils.execute(task);
    }
}
