create table shop_db.products
(
    id   bigint auto_increment
        primary key,
    name varchar(36) not null,
    post_date datetime not null,
    cost double      not null,
    constraint id
        unique (id)
);
