create table product_category
(
    id   bigint auto_increment,
    name varchar(36) not null,
    constraint product_category_pk
        primary key (id)
);