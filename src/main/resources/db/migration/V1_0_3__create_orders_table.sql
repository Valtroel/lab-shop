create table shop_db.orders
(
    id         bigint auto_increment
        primary key,
    user_id    bigint   not null,
    product_id bigint   not null,
    order_date datetime not null,
    total_cost double   not null,
    constraint orders_product_id___fk
        foreign key (product_id) references shop_db.products (id),
    constraint orders_user_id___fk
        foreign key (user_id) references shop_db.users (id)
);

