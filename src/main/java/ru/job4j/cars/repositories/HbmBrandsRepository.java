package ru.job4j.cars.repositories;

import org.hibernate.Session;
import ru.job4j.cars.HibernateUtils;
import ru.job4j.cars.models.Brand;
import ru.job4j.cars.repositories.rules.Store;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HbmBrandsRepository implements Store<Integer, Brand> {

    private static final HbmBrandsRepository INSTANCE = new HbmBrandsRepository();

    private HbmBrandsRepository() { }

    public static Store<Integer, Brand> getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Brand> findAll() {
        Function<Session, List<Brand>> f = (s) ->
            s.createQuery("FROM Brand b ORDER BY b.id", Brand.class)
            .getResultList();
        return HibernateUtils.select(f);
    }

    @Override
    public Brand findById(Integer id) {
        Function<Session, Brand> f = (s) -> {
            String hql = "FROM Brand b WHERE b.id = :fId";
            return
                    s.createQuery(hql, Brand.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public boolean save(Brand value) {
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
            String hql = "DELETE FROM Brand b WHERE b.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", id)
            .executeUpdate();
        };
        return HibernateUtils.execute(task);
    }
}
