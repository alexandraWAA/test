-- liquibase formatted sql

-- changeset homychok:1

CREATE TABLE airline
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(50) NOT NULL
);