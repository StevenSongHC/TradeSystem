create table good(
	gid int(6) auto_increment primary key not null,
	pid int(4) not null,
	g_title varchar(125) not null,
	g_pic varchar(100) default 'images/good/nopic.jpg',
	g_price int(10) not null,
	g_desc text,
	g_buyer_count int(6) default 0,
	g_add_time timestamp,
	g_isAgreed int(1) default 0,
	g_isAvailable int(1) default 1,
	g_isDeleted int(1) default 0
);