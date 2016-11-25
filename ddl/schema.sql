
CREATE TABLE directory(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(100),
    FOREIGN KEY (parent_id) REFERENCES dir(id) ON DELETE CASCADE
)


CREATE TABLE coordinate(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(100),
    directory BIGINT,
    lat FLOAT(10,6) NOT NULL,
    lon FLOAT(10,6) NOT NULL,
    INDEX (name),
    FOREIGN KEY (directory) REFERENCES directory(id) ON DELETE CASCADE
);

--insert into directory(name, description) values ('Test1', 'TestDesc2');
--insert into coordinate(name, description, directory, lon, lat) values ('Test1', 'TestDesc1', null, 111.111, 222.222);
--insert into coordinate(name, description, directory, lon, lat) values ('Test2', 'TestDesc2', null, 333.333, 444.444);

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
