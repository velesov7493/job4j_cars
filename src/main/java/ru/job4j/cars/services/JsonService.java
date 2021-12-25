package ru.job4j.cars.services;

import com.google.gson.Gson;
import ru.job4j.cars.AppSettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JsonService {

    private static final JsonService INSTANCE = new JsonService();

    private final Gson gson;

    private JsonService() {
        gson = AppSettings.getGson();
    }

    public static JsonService getInstance() {
        return INSTANCE;
    }

    public <T> void writeToStream(T value, OutputStream out) throws IOException {
        String json = gson.toJson(value);
        out.write(json.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }

    public <T> T readFromStream(InputStream in, Class<T> clazz) throws IOException {
        String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        return gson.fromJson(json, clazz);
    }
}
