insert into power (power_name, power_description) values ('Webb', 'Superhero shots webbs to disable there enemies');
insert into power (power_name, power_description) values ('Super strength', 'Superhero gets superstrong and can lift any object');
insert into power (power_name, power_description) values ('Rich', 'Superhero gets enormously wealthy');
insert into power (power_name, power_description) values ('Fire ballz', 'Superhero gets the ability to shot firballs');
insert into power (power_name, power_description) values ('Lazer beams', 'Superhero can shoot lazerbeams');

insert into superhero_power (superhero_id, power_id) values (3, 1);
insert into superhero_power (superhero_id, power_id) values (3, 2);
insert into superhero_power (superhero_id, power_id) values (2, 3);
insert into superhero_power (superhero_id, power_id) values (1, 5);
insert into superhero_power (superhero_id, power_id) values (1, 2);



select * from power;
select * from  superhero_power;