create table publisher(
	pid int(4) auto_increment primary key not null,
	uid int(4) not null,
	p_name varchar (50),
	p_summary text,
	p_contact varchar(75),
	p_join_date date,
	p_good_count int(4) default 0,
	p_isActivate int(1) default 0,
	p_isRestricted int(1) default 0,
	p_isAdmin int(4) default -19
);