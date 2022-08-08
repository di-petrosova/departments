DROP table tempdep.media

DROP database tempdep

CREATE DATABASE tempdep

CREATE table tempdep.employees

ALTER TABLE employees MODIFY column tempphoto longblob

select * from tempdep.media where idEmp=4967

select * from tempdep.employees

create table employees

INSERT employees (id, firstname, lastname, dateBirth, photo, age, email, createdDate, modifiedDate, experience, tempphoto, departmentId)
 VALUES ('10', 'New', 'del', '1999-08-25', "fdf", (SELECT TIMESTAMPDIFF(YEAR, '1999-08-25', CURDATE()) AS age), 'new10@gmail.com', now(), now(), false, "dddd", '951');

INSERT departments(pk, id, name, adress)
VALUES ('4', '04', 'dep4', '61066');

UPDATE employees SET id='1', firstName = 'Dian', lastName = 'Petrosova', dateBirth = '1980-05-05', email = 'diana@gmail.com', photo = 'home/diana/Pictures/di.jpg', age = (SELECT TIMESTAMPDIFF(YEAR, '1983-05-05', CURDATE()) WHERE id='1'), createdDate = '2022-06-21', modifiedDate = now(), experience = true, departmentId = '7352'
WHERE id='1'

SELECT TIMESTAMPDIFF(YEAR, dateBirth, CURDATE()) AS age FROM employees


CREATE TABLE employees
(
    id INT,
    firstName VARCHAR(20),
    lastName VARCHAR(20),
    dateBirth date,
    age INT,
    email VARCHAR(30),
    photo BLOB,
    createdDate datetime,
    modifiedDate datetime,
    experience BOOLEAN,
    departmentId   INT
);