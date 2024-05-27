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

insert into products(product_mark, product_model, price, amount, product_image)
values('Материнская плата MSI PRO', 'H610M-E DDR4', 7990.00, 10,'light_bulb_light_dark_226180_1200x1600.jpg'),
('Материнская плата ASROCK H670M PRO', 'RS', 11140.00, 5, 'Screenshot_1.jpg'),
('Материнская плата MSI MPG', 'B550 GAMING PLUS', 14840.00, 12, 'Screenshot_6.jpg'),
('Материнская плата GIGABYTE', 'B760M DS3H DDR4', 12600.00, 8, 'Screenshot_7.jpg');