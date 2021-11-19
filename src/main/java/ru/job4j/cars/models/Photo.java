package ru.job4j.cars.models;

import javax.persistence.*;
import java.util.Objects;

public class Photo {

    @Id
    @SequenceGenerator(
            name = "photosIdSeq",
            sequenceName = "tz_photos_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photosIdSeq")
    private int id;
    private String mimeType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        return
                id == photo.id
                && Objects.equals(mimeType, photo.mimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mimeType);
    }
}
