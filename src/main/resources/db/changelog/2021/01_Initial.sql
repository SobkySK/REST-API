--liquibase formatted sql
--changeset sobol:1
CREATE TABLE person(
    id              BIGINT                     NOT NULL AUTO_INCREMENT,
    date_created    TIMESTAMP                  NOT NULL                                                                         ,
    date_modified   TIMESTAMP                  DEFAULT NULL                                                                     ,
    name            VARCHAR(20)                NOT NULL                                                                         ,
    last_name       VARCHAR(255)               NOT NULL                                                                         ,
    email           VARCHAR(255)               NOT NULL,
    CONSTRAINT pk___person___id                PRIMARY KEY(id),
    CONSTRAINT u___person___email              UNIQUE(email)
);

CREATE TABLE bank_card(
    id              BIGINT                     NOT NULL AUTO_INCREMENT,
    date_created    TIMESTAMP                  NOT NULL                                                                         ,
    date_modified   TIMESTAMP                  DEFAULT NULL                                                                     ,
    person_id       BIGINT                     NOT NULL                                                                         ,
    card_number     VARCHAR(16)                NOT NULL                                                                         ,
    expiration      DATE                       NOT NULL                                                                         ,
    CONSTRAINT pk___bank_card___id             PRIMARY KEY(id),
    CONSTRAINT fk___bank_card___person_id      FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT u___bank_card___card_number     UNIQUE(card_number)
);