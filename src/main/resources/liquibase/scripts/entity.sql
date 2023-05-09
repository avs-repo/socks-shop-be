-- liquibase formatted sql

-- changeSet andrew:1
CREATE TABLE socks
(
    id          SERIAL NOT NULL PRIMARY KEY,
    color       VARCHAR(255) NOT NULL,
    cotton_part SMALLINT NOT NULL,
    quantity    INTEGER NOT NULL
);

-- changeSet andrew:2
ALTER TABLE socks ALTER COLUMN id TYPE BIGINT;