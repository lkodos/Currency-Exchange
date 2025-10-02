DROP TABLE IF EXISTS currency;

CREATE TABLE IF NOT EXISTS currency (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code VARCHAR(3) UNIQUE NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    sign VARCHAR(10)
);

INSERT INTO currency(code, full_name, sign)
VALUES ('RUB', 'Russian Ruble', '₽'),
       ('EUR', 'Euro', '€'),
       ('USD', 'US Dollar', '$');