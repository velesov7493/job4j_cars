INSERT INTO tz_body_types (name) VALUES
('Седан'), ('Хетчбэк'), ('Лифтбек'), ('Фастбек'), ('Хардтоп'),
('Лимузин'), ('Универсал'), ('Шутинг брэк'), ('Хардтоп-универсал'), ('Купе'),
('Фастбэк-купе'), ('Хардтоп-купе'), ('Клаб-купе'), ('Бизнес-купе'), ('Кабриолет'),
('Фаэтон'), ('Родстер'), ('Ландоле'), ('Таун-кар'), ('Кабрио-коуч'), ('Тарга');

INSERT INTO tz_brands (name) VALUES
('ВАЗ'), ('ГАЗ'), ('УАЗ'), ('Ford'), ('Ferrari'), ('Audi'), ('Hyndai'),
('Lexus'), ('Mazda'), ('Honda'), ('Aston martin'), ('BMW'), ('Mercedes'), ('Peugeot');

INSERT INTO tz_users (login, name, email, password, phone) VALUES
('sysdba', 'Власов Александр Сергеевич', 'velesov7493@gmail.com', 'AB4154A7C451F56E9B7FF1537758DDD0C619F8BE', '+79611681681');

INSERT INTO tr_posts (id_author, id_carbrand, id_bodytype, model, price, description) VALUES
(1, 1, 1, 'ВАЗ-2107', 40000.00, 'ВАЗ-2107 2010 года выпуска на ходу в отличном состоянии');

INSERT INTO tr_photos (id, mimeType) VALUES
(1, 'image/jpeg');