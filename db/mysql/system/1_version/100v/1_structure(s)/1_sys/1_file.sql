CREATE TABLE `sys_file` (
  `id` varchar(36) NOT NULL,
  `name` varchar(200) NOT NULL COMMENT '',
  `path` varchar(200) NOT NULL COMMENT '',
  `type` varchar(200) NOT NULL COMMENT '',
  `target_key` varchar(100) NOT NULL COMMENT '',
  {{ TrackColumn }},
  PRIMARY KEY (`id`)
) COMMENT='文件表';