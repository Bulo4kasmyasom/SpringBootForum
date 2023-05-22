--liquibase formatted sql

--changeset bulo4kasmyasom:0
create schema if not exists public;

--changeset bulo4kasmyasom:0.1
set schema 'public';

--changeset bulo4kasmyasom:1
create table public.roles
(
    value varchar(20) unique not null
);

--changeset bulo4kasmyasom:2
create table public.users
(
    id            bigserial primary key,
    username      varchar(20) unique not null,
    password      varchar(256)       not null,
    email         varchar(50) unique not null,
    role          varchar(20)        not null references roles (value),
    image         varchar(128)       not null default 'default.png',
    last_activity timestamp          not null,
    created_at    timestamp          not null,
    updated_at    timestamp
);

--changeset bulo4kasmyasom:3
create table public.sections
(
    id          bigserial primary key,
    title       varchar(128) not null,
    description varchar(512) not null,
    created_at  timestamp    not null,
    updated_at  timestamp
);

--changeset bulo4kasmyasom:4
create table public.categories
(
    id          bigserial primary key,
    section_id  bigint references sections (id) on update cascade on delete cascade,
    title       varchar(128) not null,
    description varchar(512) not null,
    topic_count bigint       not null default 0,
    created_at  timestamp    not null,
    updated_at  timestamp
);

--changeset bulo4kasmyasom:5
create table public.sub_categories
(
    id          bigserial primary key,
    category_id bigint       not null references categories (id) on update cascade on delete cascade,
    title       varchar(128) not null,
    description varchar(512) not null,
    topic_count bigint       not null default 0,
    created_at  timestamp    not null,
    updated_at  timestamp
);

--changeset bulo4kasmyasom:6
create table public.topics
(
    id              bigserial primary key,
    category_id     bigint       not null references categories (id) on update cascade on delete cascade,
    sub_category_id bigint references sub_categories (id) on update cascade on delete cascade,
    title           varchar(200) not null,
    author_id       bigint       not null references users (id) on update cascade,
    message_count   bigint       not null default 0,
    created_at      timestamp    not null,
    updated_at      timestamp
);

--changeset bulo4kasmyasom:7
create table public.topic_messages
(
    id         bigserial primary key,
    topic_id   bigint        not null references topics (id) on delete cascade on update cascade,
    text       varchar(4000) not null,
    author_id  bigint        not null references users (id) on update cascade,
    created_at timestamp     not null,
    updated_at timestamp
);


--changeset bulo4kasmyasom:8
INSERT INTO public.roles (value)
VALUES ('ADMIN'),
       ('MODERATOR'),
       ('USER'),
       ('GUEST');