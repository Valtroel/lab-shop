create table shop_db.users
(
    id        bigint auto_increment
        primary key,
    password  varchar(128) not null,
    user_name varchar(36)  not null,
    email     varchar(36)  not null,
    role      varchar(5)      not null
);