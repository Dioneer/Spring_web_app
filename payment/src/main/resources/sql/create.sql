create table if not exists payments(
    id bigserial primary key,
    cart_number bigint not null unique,
    balance decimal(12, 2)
);

insert into payments (cart_number, balance)
values (11111, 100000),
       (22222, 500000),
       (33333, 1000000);