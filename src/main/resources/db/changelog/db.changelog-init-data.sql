--liquibase formatted sql

--changeset bulo4kasmyasom:1
INSERT INTO public.users (id, username, password, email, role, image, last_activity, created_at, updated_at)
VALUES (DEFAULT, 'admin', '$2a$10$Ac3TtVhpGSRfk5fLJiDjF.mCJSzwiWMhnNl1pS7VKwiXJJYemnyui', 'admin@localhost.ru', 'ADMIN',
        DEFAULT, current_timestamp,
        current_timestamp, null);

--changeset bulo4kasmyasom:2
INSERT INTO public.sections (id, title, description, created_at, updated_at)
VALUES (DEFAULT, 'Web-разработка', 'Описание раздела веб-разработки', current_timestamp, null),
       (DEFAULT, 'Операционные системы', 'Описание раздела операционные системы', current_timestamp, null);

--changeset bulo4kasmyasom:3
INSERT INTO public.categories (id, section_id, title, description, created_at, updated_at)
VALUES (DEFAULT, 1, 'Java', 'Описание категории Java', current_timestamp, null),
       (DEFAULT, 1, 'PHP', 'Описание категории PHP', current_timestamp, null),
       (DEFAULT, 2, 'Windows', 'Описание категории Windows', current_timestamp, null),
       (DEFAULT, 2, 'GNU/Linux', 'Описание категории GNU/Linux', current_timestamp, null);

--changeset bulo4kasmyasom:4
INSERT INTO public.sub_categories (id, category_id, title, description, created_at, updated_at)
VALUES (DEFAULT, 1, 'Core', 'Описание подкатегории Core', current_timestamp, null),
       (DEFAULT, 1, 'Spring', 'Описание подкатегории Spring', current_timestamp, null),
       (DEFAULT, 2, 'php 8', 'Описание подкатегории php 8', current_timestamp, null),
       (DEFAULT, 2, 'php 7', 'Описание подкатегории php 7', current_timestamp, null),
       (DEFAULT, 3, 'windows 11', 'Описание подкатегории windows 11', current_timestamp, null),
       (DEFAULT, 3, 'windows 10', 'Описание подкатегории windows 10', current_timestamp, null),
       (DEFAULT, 3, 'windows 95', 'Описание подкатегории windows 95', current_timestamp, null),
       (DEFAULT, 4, 'Arch Linux', 'Описание подкатегории Arch Linux', current_timestamp, null);

--changeset bulo4kasmyasom:5
INSERT INTO public.topics (id, category_id, sub_category_id, title, author_id, created_at, updated_at)
VALUES (DEFAULT, 1, null, 'Тема 1 в категории Java', 1, current_timestamp, null),
       (DEFAULT, 1, null, 'Тема 2 категории Java', 1, current_timestamp, null),
       (DEFAULT, 1, null, 'Тема 3 категории Java', 1, current_timestamp, null),
       (DEFAULT, 1, null, 'Тема 4 категории Java', 1, current_timestamp, null),
       (DEFAULT, 1, 1, 'Тема 1 категории Java и подкатегории Core', 1, current_timestamp, null),
       (DEFAULT, 1, 1, 'Тема 2 категории Java и подкатегории Core', 1, current_timestamp, null),
       (DEFAULT, 1, 2, 'Тема 1 категории Java и подкатегории Spring', 1, current_timestamp, null),
       (DEFAULT, 1, 2, 'Тема 2 категории Java и подкатегории Spring', 1, current_timestamp, null),
       (DEFAULT, 1, 3, 'Тема 1 категории PHP и подкатегории php 8', 1, current_timestamp, null),
       (DEFAULT, 1, 4, 'Тема 1 категории PHP и подкатегории php 7', 1, current_timestamp, null),
       (DEFAULT, 1, 5, 'Тема 1 категории Windows и подкатегории windows 11', 1, current_timestamp, null),
       (DEFAULT, 1, 6, 'Тема 1 категории Windows и подкатегории windows 10', 1, current_timestamp, null),
       (DEFAULT, 1, 7, 'Тема 1 категории Windows и подкатегории windows 95', 1, current_timestamp, null);

--changeset bulo4kasmyasom:6
INSERT INTO public.topic_messages (id, topic_id, text, author_id, created_at, updated_at)
VALUES (DEFAULT, 1, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 1, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 1, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 2, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 2, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 2, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 2, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 3, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 3, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 3, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 4, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 5, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 5, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 6, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 7, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null),
       (DEFAULT, 7, 'Текст сообщения  ' || trunc(random() * 10000), 1, current_timestamp, null);