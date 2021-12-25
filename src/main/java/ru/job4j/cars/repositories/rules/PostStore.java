package ru.job4j.cars.repositories.rules;

import ru.job4j.cars.models.Post;

import java.util.List;

public interface PostStore extends Store<Integer, Post> {

    List<Post> findAllOfLastDay();

    List<Post> findAllWithPhoto();

    List<Post> findAllByDimensions(
        Boolean isSold, Integer brandId, Integer bodyTypeId, Integer authorId
    );
}
