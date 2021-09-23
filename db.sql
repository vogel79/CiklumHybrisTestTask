/* Delete the tables if they already exist */
drop table if exists orders;
drop table if exists products;
drop table if exists order_items;

/* Create the schema for our tables */
create table orders (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, user_id int, status varchar(20), created_at varchar(20));
create table products (id int NOT NULL AUTO_INCREMENT PRIMARY KEY, name varchar(20), price int,
    status enum('out_of_stock', 'in_stock','running_low'), created_at date);
create table order_items (order_id int, product_id int, quantity int);
