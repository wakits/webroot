CREATE SEQUENCE IF NOT EXISTS hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS roles (
    id bigint PRIMARY KEY,
    label character varying(255)
);

CREATE TABLE IF NOT EXISTS users (
    id bigint PRIMARY KEY,
    email character varying(255) NOT NULL UNIQUE,
    name character varying(255) NOT NULL UNIQUE,
    password character varying(255) NOT NULL,
    role_id bigint REFERENCES roles(id)
);

INSERT INTO roles (id, label) VALUES(1, 'USER');

