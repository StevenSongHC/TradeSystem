create table orders(
	oid int(10) auto_increment primary key not null,
	gid int(6) not null,
	o_sellerid int(4),
	o_buyerid int(4),
	o_add_time timestamp,
);