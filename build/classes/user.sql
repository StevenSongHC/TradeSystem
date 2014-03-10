create table user(
	uid int(4) auto_increment primary key not null,
	u_name varchar(30) not null,
	u_account varchar(30) not null,
	u_password varchar(30) not null,
	u_portrait varchar(50) default 'images/default.png',
	u_summary text,
	u_isSuspend int(1) default 0,
	u_isDelete int(1) default 0,
	u_isRestricted int(1) default 0,
	u_isPublisher int(1) default 0
);