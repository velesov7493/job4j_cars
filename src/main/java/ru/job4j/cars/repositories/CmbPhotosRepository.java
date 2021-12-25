package ru.job4j.cars.repositories;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cars.AppSettings;
import ru.job4j.cars.HibernateUtils;
import ru.job4j.cars.models.Photo;
import ru.job4j.cars.repositories.rules.PhotoStore;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class CmbPhotosRepository implements PhotoStore {

    private static final Logger LOG = LoggerFactory.getLogger(CmbPhotosRepository.class);
    private static final CmbPhotosRepository INSTANCE = new CmbPhotosRepository();

    private String imagesDir;

    private CmbPhotosRepository() {
        Properties cfg = AppSettings.loadProperties();
        imagesDir = cfg.getProperty("dir.images");
        File folder = new File(imagesDir);
        boolean result = folder.exists();
        if (!result) {
            result = folder.mkdir();
        }
        if (!result) {
            LOG.error("Критическая ошибка инициализации хранилища картинок!");
            LOG.info("Выключаюсь...");
            System.exit(2);
        }
    }

    public static PhotoStore getInstance() {
        return INSTANCE;
    }

    private String fileNameById(int imageId) {
        StringBuilder number = new StringBuilder(String.valueOf(imageId));
        int nc = 9 - number.length();
        for (int i = 0; i < nc; i++) {
            number.insert(0, "0");
        }
        return imagesDir + "/img" + number.toString() + ".res";
    }

    @Override
    public boolean writeImageToStream(Photo value, OutputStream stream) {
        boolean result = false;
        if (value.getId() == 0) {
            throw new IllegalStateException("Отсутствуют метаданные изображения!");
        }
        File imgFile = new File(fileNameById(value.getId()));
        if (!imgFile.exists()) {
            return false;
        }
        try (
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(imgFile));
            BufferedOutputStream out = new BufferedOutputStream(stream)
        ) {
            out.write(in.readAllBytes());
            result = true;
        } catch (Throwable ex) {
            LOG.error("Ошибка загрузки фото в поток: ", ex);
        }
        return result;
    }

    @Override
    public void saveImageFromStream(Photo value, InputStream stream) {
        if (!save(value)) {
            LOG.warn("Метаданные изображения не сохранены. Отмена сохранения файла!");
            return;
        }
        try (
            BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(new File(fileNameById(value.getId())))
            );
            BufferedInputStream bufferedStream = new BufferedInputStream(stream)
        ) {
            out.write(bufferedStream.readAllBytes());
        } catch (Throwable ex) {
            LOG.error("Ошибка при записи загруженного изображения: ", ex);
        }
    }

    @Override
    public List<Photo> findAll() {
        Function<Session, List<Photo>> f = (s) ->
            s.createQuery("FROM Photo", Photo.class)
            .getResultList();
        return HibernateUtils.select(f);
    }

    @Override
    public Photo findById(Integer id) {
        Function<Session, Photo> f = (s) -> {
            String hql = "FROM Photo ph WHERE ph.id = :fId";
            return
                    s.createQuery(hql, Photo.class)
                    .setParameter("fId", id)
                    .getSingleResult();
        };
        return HibernateUtils.select(f);
    }

    @Override
    public boolean save(Photo value) {
        Consumer<Session> task = (s) -> s.saveOrUpdate(value);
        return HibernateUtils.execute(task);
    }

    @Override
    public boolean deleteById(Integer id) {
        Consumer<Session> task = (s) -> {
            String hql = "DELETE FROM Photo ph WHERE ph.id = :fId";
            s.createQuery(hql)
            .setParameter("fId", id)
            .executeUpdate();
        };
        File nFile = new File(fileNameById(id));
        return HibernateUtils.execute(task) && nFile.delete();
    }
}