drop table if exists superhero;

create table superhero(
hero_id int generated always as identity primary key,
hero_name varchar(50) not null,
hero_origin varchar(50)
);

drop table if exists assistant;

create table assistant(
assist_id int generated always as identity primary key,
assist_name varchar(50) not null
);

drop table if exists superhero_power;

create table power(
power_id int generated always as identity primary key,
power_name varchar(50) not null,
power_description text
);