--liquibase formatted sql

--changeset gleb:1
CREATE SCHEMA links;

CREATE TABLE link (
    link_id BIGINT PRIMARY KEY,
    url VARCHAR NOT NULL
);

CREATE TABLE chat (
    chat_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL
);

CREATE TABLE link_chat (
    link_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    PRIMARY KEY (link_id, chat_id),
    FOREIGN KEY (link_id)
        REFERENCES Link (link_id),
    FOREIGN KEY (chat_id)
        REFERENCES chat (chat_id)
);
