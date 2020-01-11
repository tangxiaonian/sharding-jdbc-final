# sharding-jdbc-final
sharding-jdbc实现读写分离+分表分库

192.168.108.127 主
192.168.108.128 从

192.168.108.129 主
192.168.108.130 从

数据表:

CREATE TABLE `goods_0` (
  `goods_id` bigint(20) NOT NULL,
  `goods_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `goods_type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

主要配置在 config 目录下面。
