drop table if exists users;
drop table if exists login_tokens;

create table users(
  id serial primary key,
  email varchar(255),
  password varchar(128)
);

create table login_tokens (
    email varchar(255) not null primary key,
    token varchar(255) not null,
    logged_in TIMESTAMP not null
);