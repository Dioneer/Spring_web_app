create table if not exists users(
    id bigserial primary key,
    username varchar(64) not null unique,
    birthday_date date,
    firstname varchar(64) not null,
    lastname varchar(64),
    role varchar(32) not null,
    password varchar(128) default '{noop}123',
    user_image varchar(255)
);
--drop table users;

insert into users(birthday_date, firstname, lastname, role, username, user_image) values
 ('1990-01-10', 'Ivan', 'Ivanov', 'SILVER', 'ivanov@gmail.com', 'san1.jpg'),
('1995-02-11', 'Petr', 'Petrov', 'GOLD', 'petrov@gmail.com','Screenshot_1.jpg'),
('1997-03-12', 'Nina', 'Sidorov', 'GOLD', 'sidorov@gmail.com','Screenshot_6.jpg'),
('1998-04-13', 'Roma', 'Gogin', 'BRILLIANT', 'gogin@gmail.com','Screenshot_12.jpg');