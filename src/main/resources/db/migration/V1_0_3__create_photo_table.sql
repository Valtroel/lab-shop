create table shop_db.photos
(
    id         bigint auto_increment primary key,
    product_id bigint       not null,
    path       varchar(128) not null,
    constraint product_id_fk
        foreign key (product_id) references shop_db.products (id)
);

