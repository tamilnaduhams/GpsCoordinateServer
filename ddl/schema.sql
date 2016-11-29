
CREATE TABLE directory(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL DEFAULT -1,
    name VARCHAR(100) NOT NULL,
    INDEX (parent_id),
    UNIQUE INDEX (parent_id, name),
    FOREIGN KEY (parent_id) REFERENCES directory(id) ON DELETE CASCADE
);

insert into directory (id, parent_id, name) values (-1, -1, 'root');

CREATE TABLE coordinate(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    directory_id BIGINT NOT NULL DEFAULT -1,
    lat FLOAT(10,6) NOT NULL,
    lon FLOAT(10,6) NOT NULL,
    INDEX (directory_id),
    UNIQUE INDEX (name, directory_id),
    FOREIGN KEY (directory_id) REFERENCES directory(id) ON DELETE CASCADE
);

CREATE TABLE user(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(256) NOT NULL,
    authorities VARCHAR(100),
    UNIQUE INDEX (username)
)

CREATE TABLE crypto(
	secret VARCHAR(64) NOT NULL
)
