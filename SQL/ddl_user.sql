CREATE DATABASE IF NOT EXISTS users;
USE users;

--
-- TABLE: user
-- 
--  

CREATE TABLE user (
  login varchar(64) NOT NULL PRIMARY KEY,
  password varchar(64) NOT NULL,
  creationDate date NOT NULL,
  role varchar(20) NOT NULL
);

INSERT INTO user (login, password, creationDate, role)
VALUES ('admin', '$2a$10$JvRkO7Wq.yG.tTqMenNQs.ff3Zs4IpKOTOIcMpz.cH4Z7CQX5H9va' , '2023-09-02', 'ROLE_ADMIN');


--
-- Create springuser
-- 
-- 

create user 'springuser_webapp'@'%' identified by 'ThePassword';
GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'springuser_webapp'@'%' WITH GRANT OPTION;
