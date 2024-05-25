create table if not exists payments(
    id bigserial primary key,
    client_number bigint not null,
    storage_balance decimal(12, 2) default 0,
    cart_balance decimal(12, 2),
    user_id bigint
);

insert into payments (client_number, cart_balance, user_id)
values (11111, 100000, 1),
       (22222, 500000, 2),
       (33333, 1000000, 3);