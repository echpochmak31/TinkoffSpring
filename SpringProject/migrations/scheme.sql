--liquibase formatted sql

--changeset gleb:1
CREATE SCHEMA links;

CREATE TABLE links.link (
    link_id BIGSERIAL PRIMARY KEY,
    url VARCHAR NOT NULL
);

CREATE TABLE links.chat (
    chat_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL
);

CREATE TABLE links.link_chat (
    link_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    PRIMARY KEY (link_id, chat_id),
    FOREIGN KEY (link_id)
        REFERENCES links.link (link_id),
    FOREIGN KEY (chat_id)
        REFERENCES links.chat (chat_id)
);
