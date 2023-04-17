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

--changeset gleb:3

DROP TABLE links.link;

CREATE TABLE links.link (
    link_id BIGSERIAL PRIMARY KEY,
    url VARCHAR NOT NULL UNIQUE
);

CREATE TABLE links.link_chat (
     link_id BIGINT NOT NULL,
     chat_id BIGINT NOT NULL,
     PRIMARY KEY(link_id, chat_id),
     FOREIGN KEY (link_id)
         REFERENCES links.link (link_id),
     FOREIGN KEY (chat_id)
         REFERENCES links.chat (chat_id)
);

--changeset gleb:4

ALTER TABLE links.link
ADD COLUMN last_update timestamptz,
ADD COLUMN last_check timestamptz NOT NULL DEFAULT NOW();

--changeset gleb:5

ALTER TABLE links.link
ALTER COLUMN last_update
SET DEFAULT make_timestamptz(1970, 01, 01, 0, 0, 0)
