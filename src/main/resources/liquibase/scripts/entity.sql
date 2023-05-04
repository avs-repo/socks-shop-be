-- liquibase formatted sql

-- changeSet andrew:1
CREATE TABLE socks
(
    id         SERIAL NOT NULL PRIMARY KEY,
    color      TEXT,
    cottonPart TEXT,
    quantity   INTEGER
);