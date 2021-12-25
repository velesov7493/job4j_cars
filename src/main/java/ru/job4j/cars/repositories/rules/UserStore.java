package ru.job4j.cars.repositories.rules;

import ru.job4j.cars.models.User;

public interface UserStore extends Store<Integer, User> {

    User findByLoginAndPassword(String login, String password);
}
