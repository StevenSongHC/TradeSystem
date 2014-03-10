create table message(
	mid int(8) auto_increment primary key not null,
	sender_uid int(4),
	receiver_uid int(4) not null,
	word text,
	time timestamp,
	notice_type int(2),
	isRead int(1) default 0
);