--liquibase formatted sql

--changeset bulo4kasmyasom:insert_default_users
INSERT INTO public.users (id, username, password, email, role, image, last_activity, created_at, updated_at)
VALUES (DEFAULT, 'admin', '$2a$10$Ac3TtVhpGSRfk5fLJiDjF.mCJSzwiWMhnNl1pS7VKwiXJJYemnyui', 'admin@localhost.ru', 'ADMIN',
        DEFAULT, current_timestamp,
        current_timestamp, null),
       (DEFAULT, 'moderator', '$2a$10$PyKe4AtehOB6y5P78J32E.9yiKwEBPZbpVPPnvE12qdGQmwA0i7ZS', 'moderator@localhost.ru', 'MODERATOR',
        DEFAULT, current_timestamp,
        current_timestamp, null),
       (DEFAULT, 'user', '$2a$10$PsTXpgvshtOit/qKgLCKX.RYFlzSAe5dvhfhlvdLX.eCJOp3HthGq', 'user@localhost.ru', 'USER',
        DEFAULT, current_timestamp,
        current_timestamp, null);

--changeset bulo4kasmyasom:insert_default_sections
INSERT INTO public.sections (id, title, description, created_at, updated_at)
VALUES (DEFAULT, 'Web-разработка', 'Описание раздела веб-разработки', current_timestamp, null),
       (DEFAULT, 'Операционные системы', 'Описание раздела операционные системы', current_timestamp, null);

--changeset bulo4kasmyasom:insert_default_categories
INSERT INTO public.categories (id, section_id, title, description, created_at, updated_at)
VALUES (DEFAULT, 1, 'Java', 'Описание категории Java', current_timestamp, null),
       (DEFAULT, 1, 'PHP', 'Описание категории PHP', current_timestamp, null),
       (DEFAULT, 2, 'GNU/Linux', 'Описание категории GNU/Linux', current_timestamp, null);

--changeset bulo4kasmyasom:insert_default_sub_categories
INSERT INTO public.sub_categories (id, category_id, title, description, created_at, updated_at)
VALUES (DEFAULT, 1, 'Core', 'Описание подкатегории Core', current_timestamp, null),
       (DEFAULT, 1, 'Spring', 'Описание подкатегории Spring', current_timestamp, null),
       (DEFAULT, 2, 'php 8', 'Описание подкатегории php 8', current_timestamp, null),
       (DEFAULT, 3, 'Arch Linux', 'Описание подкатегории Arch Linux', current_timestamp, null);