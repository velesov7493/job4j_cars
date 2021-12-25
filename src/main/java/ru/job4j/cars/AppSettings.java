package ru.job4j.cars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Properties;

public class AppSettings {

    private static final Logger LOG = LoggerFactory.getLogger(AppSettings.class.getName());
    private static SoftReference<Properties> settings;
    private static Gson gson;

    public static synchronized Properties loadProperties() {
        Properties s = settings == null ? null : settings.get();
        if (s == null) {
            s = new Properties();
            try (
                    InputStream in =
                         AppSettings.class
                         .getClassLoader()
                         .getResourceAsStream("webapp.properties")
            ) {
                if (in != null) {
                    s.load(in);
                    settings = new SoftReference<>(s);
                }
            } catch (Throwable ex) {
                LOG.error(
                    "Критическая ошибка - невозможно прочитать свойства приложения: ", ex
                );
                LOG.info("Выключаюсь...");
                System.exit(2);
            }
        }
        return s;
    }

    public static synchronized Gson getGson() {
        if (gson == null) {
            gson =
                    new GsonBuilder()
                    .setDateFormat("dd.MM.yyyy")
                    .create();
        }
        return gson;
    }
}