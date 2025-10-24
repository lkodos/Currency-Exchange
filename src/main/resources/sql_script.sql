PRAGMA foreign_keys = on;

DROP TABLE IF EXISTS currency;
DROP TABLE IF EXISTS exchange_rates;

CREATE TABLE IF NOT EXISTS currency
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    code TEXT UNIQUE NOT NULL,
    name TEXT        NOT NULL,
    sign TEXT
);

CREATE TABLE IF NOT EXISTS exchange_rates
(
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    base_currency_id INTEGER NOT NULL REFERENCES currency (id),
    target_currency_id INTEGER NOT NULL REFERENCES currency (id),
    rate NUMERIC,
    UNIQUE (base_currency_id, target_currency_id)
);

INSERT INTO currency(code, name, sign)
VALUES ('RUB', 'Russian Ruble', '₽'),
       ('EUR', 'Euro', '€'),
       ('USD', 'US Dollar', '$');

INSERT INTO exchange_rates(base_currency_id, target_currency_id, rate)
VALUES (1, 2, 2),
       (1, 3, 0.5),
       (2, 3, 1.4);

SELECT e.id AS id,
       e.base_currency_id AS base_id,
       c.code AS base_code,
       c.name AS base_name,
       c.sign AS base_sign,
       e.target_currency_id AS target_id,
       c2.code AS target_code,
       c2.name AS target_name,
       c2.sign AS target_sign,
       e.rate AS rate
FROM exchange_rates e
JOIN currency c ON c.id = e.base_currency_id
JOIN currency c2 ON c2.id = e.target_currency_id;