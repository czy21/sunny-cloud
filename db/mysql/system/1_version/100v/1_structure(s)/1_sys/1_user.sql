CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` text NULL COMMENT '密码',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名字',
  `phone` varchar(100) NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  {{ TrackColumn }},
  PRIMARY KEY (`id`)
) COMMENT='用户表';