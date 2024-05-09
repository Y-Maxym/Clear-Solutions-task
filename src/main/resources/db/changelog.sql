--liquibase formatted sql

--changeset maxym:init_schema

create table if not exists users
(
    id           bigserial primary key,
    email        varchar(255) not null,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    birth_date   timestamp    not null,
    address      varchar(1024),
    phone_number varchar(50),
    created_at   timestamp    not null,
    updated_at   timestamp    not null
);