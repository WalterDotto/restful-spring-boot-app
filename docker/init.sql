GRANT ALL PRIVILEGES ON DATABASE springtest TO root;

create table cancel_reasons
(
	reason_id serial 
		constraint reasons_pk
			primary key,
	description varchar(40) not null
);


create table orders
(
	order_id serial
		constraint orders_pk
			primary key,
	created_date timestamp not null,
	cancel_reason int,
	constraint order_cancel_reason_fk
		foreign key (cancel_reason) references cancel_reasons (reason_id)
);

create table order_items
(
	order_id int,
	order_item_id serial,
	product_id int not null,
	constraint order_items_pk
		primary key (order_item_id),
	constraint order_items_order_id_fk
		foreign key (order_id) references orders (order_id)
);

INSERT INTO cancel_reasons(description) VALUES('Could not contact client');
INSERT INTO cancel_reasons(description) VALUES('No stock');
INSERT INTO cancel_reasons(description) VALUES('Purchase failure');

INSERT INTO orders(created_date) VALUES(current_timestamp);
INSERT INTO orders(created_date) VALUES(current_timestamp);
INSERT INTO orders(created_date) VALUES(current_timestamp);
INSERT INTO orders(created_date) VALUES(current_timestamp);
INSERT INTO orders(created_date) VALUES(current_timestamp);
INSERT INTO orders(created_date) VALUES(current_timestamp);
INSERT INTO orders(created_date, cancel_reason) VALUES(current_timestamp, 1);
INSERT INTO orders(created_date, cancel_reason) VALUES(current_timestamp, 3);

INSERT INTO order_items(order_id, product_id) VALUES(1, 25);
INSERT INTO order_items(order_id, product_id) VALUES(1, 20);
INSERT INTO order_items(order_id, product_id) VALUES(2, 20);
INSERT INTO order_items(order_id, product_id) VALUES(2, 21);




