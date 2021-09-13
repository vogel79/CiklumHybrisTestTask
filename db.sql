/* Delete the tables if they already exist */
drop table if exists orders;
drop table if exists products;
drop table if exists order_items;

/* Create the schema for our tables */
create table orders (id int, user_id int, status varchar(20), created_at varchar(20));
create table products (id int, name varchar(20), price int, status enum('out_of_stock', 'in_stock','running_low'), created_at date);
create table order_items (order_id int, product_id int, quantity int);

/* Populate the tables with our data */
insert into orders values(101, 101, 'order1', 'January');
insert into orders values(102, 101, 'order2', 'February');
insert into orders values(103, 102, 'order3', 'March');

insert into products values(101, 'lemon', 5, 'out_of_stock', '2001-09-01');
insert into products values(102, 'banana', 6, 'in_stock', '2001-09-03');
insert into products values(103, 'orange', 7, 'running_low', '2001-09-03');

insert into order_items values(101, 101, 100);
insert into order_items values(101, 102, 200);
insert into order_items values(102, 101, 300);
insert into order_items values(103, 103, 300);
insert into order_items values(103, 101, 500);