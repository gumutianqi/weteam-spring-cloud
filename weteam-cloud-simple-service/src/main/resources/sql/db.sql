CREATE TABLE `user` (
	  `id` bigint(11) NOT NULL AUTO_INCREMENT,
	  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
	  `age` int(4) DEFAULT NULL COMMENT '用户年龄',
	  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称',
	  `role_id` bigint(11) DEFAULT NULL COMMENT '用户角色',
	  `date` datetime NULL DEFAULT NULL,
	  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;