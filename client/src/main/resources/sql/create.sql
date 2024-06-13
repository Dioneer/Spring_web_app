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

create table if not exists buy_products(
    id bigserial primary key,
    product_id bigint not null,
    user_id bigint not null references users(id),
    product_mark varchar(125) not null,
    product_model varchar(125) not null,
    price decimal(12, 2) not null check(price > 0),
    amount int
);
--drop table buy_products;

create table if not exists reserved_products(
    id bigserial primary key,
    product_id bigint not null,
    user_id bigint not null references users(id),
    product_mark varchar(125) not null,
    product_model varchar(125) not null,
    price decimal(12, 2) not null check(price > 0),
    amount int
);
--drop table reserved_products;

insert into users(birthday_date, firstname, lastname, role, username, user_image) values
 ('1990-01-10', 'Ivan', 'Ivanov', 'SILVER', 'ivanov@gmail.com', '01.jpg'),
('1995-02-11', 'Petr', 'Petrov', 'GOLD', 'petrov@gmail.com','02.jpg'),
('1997-03-12', 'Nina', 'Sidorov', 'GOLD', 'sidorov@gmail.com','03.jpg'),
('1998-04-13', 'Roma', 'Gogin', 'BRILLIANT', 'gogin@gmail.com','04.jpg');

insert into buy_products(product_id, user_id, product_mark, product_model, price, amount) values
 (1,1, 'Материнская плата MSI PRO', 'H610M-E DDR4', 7990.00, 1),
(2,2,'Материнская плата ASROCK H670M PRO', 'RS', 11140.00, 2),
(3,3,'Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 3);

insert into reserved_products(product_id, user_id, product_mark, product_model, price, amount) values
 (1,1, 'Материнская плата MSI PRO', 'H610M-E DDR4', 7990.00, 1),
(2,2,'Материнская плата ASROCK H670M PRO', 'RS', 11140.00, 2),
(3,3,'Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 3);