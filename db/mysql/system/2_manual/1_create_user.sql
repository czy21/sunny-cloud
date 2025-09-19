drop user if exists 'admin'@'localhost';
drop user if exists 'admin'@'%';
CREATE USER IF NOT EXISTS 'admin'@'localhost' IDENTIFIED BY '<password>';
GRANT ALL ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;

CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY '<password>';
GRANT ALL ON *.* TO 'admin'@'%' WITH GRANT OPTION;

-- 创建xx管理员账号
CREATE USER IF NOT EXISTS 'xxadmin'@'%' IDENTIFIED BY '<pasword>';
--- 分配xx管理员账号权限
GRANT all ON `xx\_%`.* TO 'xxadmin'@'%';
-- 创建xx开发账号
CREATE USER IF NOT EXISTS 'xxdev'@'%' IDENTIFIED BY '<pasword>';
--- 分配xx开发账号权限
GRANT Create Temporary Tables, Create View,Delete,Event, Execute, Grant Option, Index, Insert, Lock Tables, References, SELECT,Show View, Trigger, Update ON `xx\_%`.* TO 'xxdev'@'%';
--- 创建nacos账号
CREATE USER IF NOT EXISTS 'nacos'@'%' IDENTIFIED BY '<pasword>';
GRANT all on `nacos`.* TO 'nacos'@'%';
--- 创建xxl_job账号
CREATE USER IF NOT EXISTS 'xxl_job'@'%' IDENTIFIED BY '<pasword>';
GRANT all on `xxl_job`.* TO 'xxl_job'@'%';