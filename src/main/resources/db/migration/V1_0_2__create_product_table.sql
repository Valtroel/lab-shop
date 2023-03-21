create table shop_db.products
(
    id          bigint auto_increment
        primary key,
    name        varchar(36) not null,
    post_date   datetime    not null,
    cost        double      not null,
    category_id bigint      not null,
    info varchar(128)       not null,
    constraint id
        unique (id),
    constraint products_product_category_id_fk
        foreign key (category_id) references shop_db.product_category (id)
);

