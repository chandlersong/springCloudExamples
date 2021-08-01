CREATE TABLE user
(
    id              INT          NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100) NOT NULL,
    favorite_number int          NOT NULL,
    favorite_color  VARCHAR(100),
    PRIMARY KEY (id)
);
