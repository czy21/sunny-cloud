CREATE TABLE `ent_sys_company` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  {{ TrackColumn }},
  PRIMARY KEY (`id`)
) COMMENT='公司表';