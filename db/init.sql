DROP TABLE IF EXISTS tr_posts;
DROP TABLE IF EXISTS tz_brands;
DROP TABLE IF EXISTS tz_photos;
DROP TABLE IF EXISTS tz_users;
DROP TABLE IF EXISTS tz_bodytypes;

CREATE TABLE tz_brands (
    id SERIAL PRIMARY KEY,
    name VARCHAR(120) UNIQUE NOT NULL
);

CREATE TABLE tz_body_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) UNIQUE NOT NULL
);

CREATE TABLE tz_photos (
    id SERIAL PRIMARY KEY,
    mimeType VARCHAR(40)
);

CREATE TABLE tz_users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    login VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(60) NOT NULL UNIQUE,
    pass VARCHAR(40) NOT NULL,
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE tr_posts (
    id SERIAL PRIMARY KEY,
    is_sold SMALLINT DEFAULT 0,
    id_author INTEGER NOT NULL REFERENCES tz_users (id) ON DELETE CASCADE,
    id_carbrand INTEGER NOT NULL REFERENCES tz_brands (id) ON DELETE CASCADE,
    id_bodytype INTEGER NOT NULL REFERENCES tz_body_types (id) ON DELETE CASCADE,
    id_photo INTEGER REFERENCES tz_photos (id) ON DELETE SET NULL,
    price NUMERIC(10,2) NOT NULL,
    created TIMESTAMP DEFAULT current_timestamp,
    description TEXT NOT NULL
);