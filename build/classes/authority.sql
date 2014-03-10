create table authority(
	uid int(4) auto_increment primary key not null comment '映射到user对象,游客无人权 >:)',
	auth_give_order int(1) default 1 comment '下订单的权限,可通过其达到限制账号的目的(ANY)',
	auth_publish_good int(1) default 0 comment '发布商品的权限(publisher,admin)',
	auth_edit_good int(1) default 0 comment '编辑已发布商品的权限(publisher,admin)',
	auth_suspend_good int(1) default 0 comment '暂停商品出售的权限(publisher,admin)',
	auth_delete_good int(1) default 0 comment '删除商品的权限(publisher,admin)',
	auth_edit_any_user int(1) default 0 comment '编辑任意用户的权限(admin)',
	auth_suspend_any_user int(1) default 0 comment '暂停任意账户的权限(admin)',
	auth_delete_any_user int(1) default 0 comment '删除任意账户的权限(admin)',
	auth_agree_publisher int(1) default 0 comment '授权普通user升级为publisher的权限(admin)',
	auth_change_auth int(1) default 0 comment '更改任意用户权限细则的权限(super_admin)'
);