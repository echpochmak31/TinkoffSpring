--liquibase formatted sql

--changeset gleb:1
CREATE SCHEMA links;

CREATE TABLE links.chat (
    chat_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL
);

CREATE TABLE links.link (
    chat_id BIGINT NOT NULL,
    url VARCHAR NOT NULL,
    PRIMARY KEY (chat_id, url),
    FOREIGN KEY (chat_id)
        REFERENCES links.chat (chat_id)
);

--changeset gleb:2
ALTER TABLE links.chat
DROP COLUMN user_id;