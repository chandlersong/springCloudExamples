drop table if exists user;
CREATE TABLE user
(
    id              INT          NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    favorite_number int          NOT NULL,
    favorite_color  VARCHAR(100),
    PRIMARY KEY (id)
);

drop table if exists test_avroTopic;
CREATE TABLE test_avroTopic
(
    id              INT          NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    favorite_number int          NOT NULL,
    favorite_color  VARCHAR(100),
    PRIMARY KEY (id)
);

