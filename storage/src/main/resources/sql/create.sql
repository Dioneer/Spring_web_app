create table if not exists products(
    id bigserial primary key,
    product_mark varchar(125) not null,
    product_model varchar(125) not null,
    price decimal(12, 2) not null check(price > 0),
    amount int not null,
    reserved int,
    product_image varchar(255)
);
create table if not exists admin_user(
    id bigserial primary key,
    role varchar(32) not null,
    admin_login varchar(125) not null,
    password varchar(128) default '{noop}123'
);
--drop table products;

insert into products(product_mark, product_model, price, amount,reserved,product_image)
values('Материнская плата MSI PRO', 'H610M-E DDR4', 7990.00, 10,1,'01.jpg'),
('Материнская плата ASROCK H670M PRO', 'RS123', 11140.00, 5,2,'02.jpg'),
('Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 12,3,'03.jpg'),
('Материнская плата GIGABYTE', 'B760M DS3H DDR4', 12600.00, 8,0,'04.jpg'),
('Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 12,0,'05.webp'),
('Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 12,0,'06.webp');

insert into admin_user(role, admin_login) values ('ADMIN', 'petrov@mail.ru'),('ADMIN', 'ivanov@mail.ru'),
('USER', 'sidorov@mail.ru'), ('USER', 'panin@mail.ru')