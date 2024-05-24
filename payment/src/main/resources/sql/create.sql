create table if not exists payments(
    id bigserial primary key,
    client_number bigint not null unique,
    storage_balance decimal(12, 2) default 0,
    cart_balance decimal(12, 2)
);

insert into payments (client_number, cart_balance)
values (11111, 100000),
       (22222, 500000),
       (33333, 1000000);