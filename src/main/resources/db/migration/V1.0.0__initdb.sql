
--------------------------
-- information about users
--------------------------

CREATE SEQUENCE HIBERNATE_SEQUENCE;

CREATE TABLE user(
    login VARCHAR(64) NOT NULL PRIMARY KEY,
    salt VARCHAR(8) NOT NULL,
    password VARCHAR(40) NOT NULL
);

CREATE TABLE user_profile(
    login VARCHAR(64) NOT NULL PRIMARY KEY,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);


