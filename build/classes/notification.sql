create table notification(
	uid int(4) auto_increment primary key not null,
	new_message int(3) default 0 comment '有其他用户发给自己的新消息提醒',
	order_give int(3) default 0 comment '有新订单(publisher)',
	order_cancel int(3) default 0 comment '有新的订单取消申请(publisher)',
	good_suspended int(2) default 0 comment '商品被admin冻结(publisher)',
	good_deleted int(2) default 0 comment '商品被admin删除(publisher)',
	new_publisher_apply int(4) default 0 comment '有新用户申请成为publisher(admin)'
);