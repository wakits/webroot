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

CREATE TABLE IF NOT EXISTS userw (
    id bigint PRIMARY KEY,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    email character varying(255) NOT NULL UNIQUE,
    username character varying(255) NOT NULL UNIQUE,
    password character varying(255) NOT NULL,
    token character varying(255) NULL UNIQUE,
    role_id bigint REFERENCES roles(id)
);

TRUNCATE TABLE userw CASCADE;
TRUNCATE TABLE roles CASCADE;

INSERT INTO roles (id, label) VALUES(1, 'USER');

INSERT INTO roles
    (id, label)
SELECT 1, 'USER'
WHERE NOT EXISTS (
    SELECT id FROM roles WHERE id BETWEEN 0 AND 2
);

INSERT INTO userw(id, email, firstname,lastname, username, password, role_id)
VALUES(1001, 'random1001@email.com', 'random1001','name1001','random-name1001', 'password1001', 1);

INSERT INTO userw(id, email, firstname,lastname, username, password, role_id)
VALUES(1002, 'random1002@email.com','random1002','name1002', 'random-name1002', 'password1002', 1);

INSERT INTO userw(id, email, firstname,lastname, username, password, role_id)
VALUES(1003, 'random1003@email.com', 'random1003','name1003','random-name1003', 'password1003', 1);
