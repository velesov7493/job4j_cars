package ru.job4j.cars.repositories.rules;

import ru.job4j.cars.models.Photo;

import java.io.InputStream;
import java.io.OutputStream;

public interface PhotoStore extends Store<Integer, Photo> {

    boolean writeImageToStream(Photo value, OutputStream stream);

    void saveImageFromStream(Photo value, InputStream stream);
}
