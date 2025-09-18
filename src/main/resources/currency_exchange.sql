DROP TABLE IF EXISTS currency;

CREATE TABLE IF NOT EXISTS currency (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code TEXT UNIQUE,
    full_name TEXT,
    sign TEXT
);

INSERT INTO currency(code, full_name, sign)
VALUES ('RUB', 'Russian Ruble', '₽'),
       ('EUR', 'Euro', '€'),
       ('USD', 'US Dollar', '$');