create table if not exists products(
    id bigserial primary key,
    product_mark varchar(125) not null,
    product_model varchar(125) not null,
    price decimal(12, 2) not null check(price > 0),
    amount int not null,
    reserved int default 0,
    product_image varchar(255)
);
--drop table products;

insert into products(product_mark, product_model, price, amount)
values('Материнская плата MSI PRO', 'H610M-E DDR4', 7990.00, 10),
('Материнская плата ASROCK H670M PRO', 'RS', 11140.00, 5),
('Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 12),
('Материнская плата GIGABYTE', 'B760M DS3H DDR4', 12600.00, 8);