drop table account;
drop table post;
drop table post_content;

create type role as enum ('simpleuser', 'admin');
create type status as enum ('suggested', 'accepted');

create table account (
    id bigserial primary key,
    username varchar(35),
    password varchar(30),
    email varchar(45),
    role role
);

create table post (
    id bigserial primary key,
    author_id bigint,
    foreign key (author_id) references account(id),
    title varchar(70),
    status status
);

create table post_content (
    id bigserial primary key,
    text text,
    picture text,
    post_id bigint,
    foreign key (post_id) references post(id)
);

create table comment (
    id bigserial primary key,
    post_id bigint,
    foreign key (post_id) references post(id),
    author_id bigint,
    foreign key (author_id) references account(id),
    text text
);

create table subscription (
    id bigserial primary key,
    account_id bigint,
    foreign key (account_id) references account(id),
    subscriber_id bigint,
    foreign key (subscriber_id) references account(id)
)
