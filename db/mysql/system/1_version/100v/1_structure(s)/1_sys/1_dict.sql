CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `code` varchar(100) NOT NULL COMMENT '编码',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `desc` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `value_type`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '值类型;0-Int,1-String',
  {{ TrackColumn }},
  PRIMARY KEY (`id`),
  KEY idx_name (name),
  UNIQUE KEY uniq_idx_code(code)
) COMMENT='字典';

CREATE TABLE `sys_dict_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name`  varchar(100) NOT NULL COMMENT '名称',
  `value` varchar(100) NOT NULL COMMENT '值',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `dict_id` bigint(20) NOT NULL COMMENT '字典主键',
  {{ TrackColumn }},
  PRIMARY KEY (`id`),
  KEY idx_name (name),
  KEY idx_dict_id (dict_id),
  UNIQUE KEY uniq_idx_dict_id_value(dict_id,value)
) COMMENT='字典值';