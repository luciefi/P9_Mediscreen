CREATE DATABASE IF NOT EXISTS patient_service;
USE patient_service;

--
-- TABLE: patient
-- 
--  

CREATE TABLE patient (
  id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  family varchar(64) NOT NULL,
  given varchar(64) NOT NULL,
  dob date NOT NULL,
  sex varchar(1) NOT NULL,
  address varchar(255) NOT NULL,
  phone varchar(20) NOT NULL
);

--
-- Create springuser
-- 
-- 

create user 'springuser'@'%' identified by 'ThePassword';
GRANT CREATE, ALTER, DROP, INSERT, UPDATE, DELETE, SELECT, REFERENCES, RELOAD on *.* TO 'springuser_webapp'@'%' WITH GRANT OPTION;
