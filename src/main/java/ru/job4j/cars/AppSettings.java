package ru.job4j.cars;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Properties;

public class AppSettings {

    private static final class Holder {

        private static final Properties SETTINGS = loadProperties();
        private static final Gson GSON = getGson();

        private static Properties loadProperties() {
            Properties s = new Properties();
            try (
                InputStream in =
                    AppSettings.class
                        .getClassLoader()
                        .getResourceAsStream("webapp.properties")
            ) {
                if (in != null) {
                    s.load(in);
                }
            } catch (Throwable ex) {
                LOG.error(
                    "Критическая ошибка - невозможно прочитать свойства приложения: ", ex
                );
                LOG.info("Выключаюсь...");
                System.exit(2);
            }
            return s;
        }

        private static Gson getGson() {
            return
                    new GsonBuilder()
                    .setDateFormat("dd.MM.yyyy")
                    .create();
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(AppSettings.class.getName());

    public static Gson getGson() {
        return Holder.GSON;
    }

    public static Properties getProperties() {
        return Holder.SETTINGS;
    }
}