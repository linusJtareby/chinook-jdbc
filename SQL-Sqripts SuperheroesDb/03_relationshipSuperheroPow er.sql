drop table if exists superhero_power;

create table superhero_power(
superhero_id int not null references superhero,
power_id int not null references power,
primary key (superhero_id, power_id)
);