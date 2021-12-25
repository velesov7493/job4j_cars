package ru.job4j.cars.repositories.rules;

import java.util.List;

public interface Store<K, V> {

    List<V> findAll();

    V findById(K id);

    boolean save(V value);

    boolean deleteById(K id);
}
