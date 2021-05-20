
DROP TABLE IF EXISTS `city_house_deal_stat`;

CREATE TABLE `city_house_deal_stat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `deal_year` int(11) DEFAULT NULL COMMENT '成交年',
  `deal_month` tinyint(4) DEFAULT NULL COMMENT '成交月',
  `city_name` varchar(32) NOT NULL COMMENT '城市',
  `city_code` varchar(32) NOT NULL DEFAULT 'zz' COMMENT '城市码',
  `county_name` varchar(32) DEFAULT NULL COMMENT '区县',
  `county_code` varchar(32) NOT NULL COMMENT '区县码',
  `deal_count` int(11) DEFAULT NULL,
  `deal_area` decimal(16,2) DEFAULT NULL,
  `deal_avg_price` decimal(16,2) DEFAULT NULL,
  `second_count` int(11) DEFAULT NULL,
  `second_area` decimal(16,2) DEFAULT NULL,
  `second_avg_price` decimal(16,2) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Table structure for table `community` */

DROP TABLE IF EXISTS `community`;

CREATE TABLE `community` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '小区名称',
  `ninety_days_deal_num` int(11) DEFAULT NULL COMMENT '90天成交数',
  `deal_num` int(11) DEFAULT NULL COMMENT '成交数,链家只有90成交数，这个是自己统计到的总成交数',
  `on_rent_num` int(11) DEFAULT NULL COMMENT '在租数',
  `on_sale_num` int(11) DEFAULT NULL COMMENT '在售数',
  `completion_year` int(10) DEFAULT NULL COMMENT '建成年份',
  `reference_price` decimal(14,2) DEFAULT NULL COMMENT '参考价',
  `lianjia_id` varchar(32) DEFAULT NULL COMMENT '链家小区id',
  `page_no` int(10) DEFAULT NULL COMMENT '页码',
  `building_type` varchar(256) DEFAULT NULL COMMENT '建造类型',
  `property_fee` varchar(64) DEFAULT NULL COMMENT '物业费',
  `property_company` varchar(128) DEFAULT NULL COMMENT '物业公司',
  `develop_company` varchar(128) DEFAULT NULL COMMENT '开发商',
  `building_num` int(11) DEFAULT NULL COMMENT '楼栋总数',
  `house_num` int(11) DEFAULT NULL COMMENT '房屋总数',
  `city_name` varchar(32) DEFAULT NULL COMMENT '城市名',
  `city_code` varchar(32) NOT NULL COMMENT '城市码',
  `county_name` varchar(32) DEFAULT NULL COMMENT '区县名',
  `county_code` varchar(32) NOT NULL COMMENT '区县码',
  `street_name` varchar(64) DEFAULT NULL COMMENT '街区名',
  `street_code` varchar(64) NOT NULL COMMENT '街区码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_day` tinyint(2) DEFAULT NULL COMMENT '更新日',
  `update_hour` tinyint(2) DEFAULT NULL COMMENT '更新时',
  `is_watched` tinyint(1) DEFAULT '0' COMMENT '是否被关注',
  `longitude` decimal(14,11) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(14,11) DEFAULT NULL COMMENT '纬度',
  `address` varchar(256) DEFAULT NULL COMMENT '地址',
  `longitude2` decimal(14,11) DEFAULT NULL,
  `latitude2` decimal(14,11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lianjia_id` (`lianjia_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19021 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Table structure for table `community_history_deal` */

DROP TABLE IF EXISTS `community_history_deal`;

CREATE TABLE `community_history_deal` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `community_id` int(11) DEFAULT NULL,
  `lianjia_community_id` varchar(32) DEFAULT NULL,
  `lianjia_house_id` varchar(32) DEFAULT NULL COMMENT '链家房源编号',
  `model` varchar(32) DEFAULT NULL COMMENT '房屋格局',
  `area` decimal(14,2) DEFAULT NULL COMMENT '房屋面积',
  `face_to` varchar(32) DEFAULT NULL COMMENT '朝向',
  `fitment_situation` varchar(32) DEFAULT NULL COMMENT '装修情况',
  `floor_type` varchar(32) DEFAULT NULL COMMENT '楼层情况',
  `total_floor_num` int(11) DEFAULT NULL COMMENT '总楼层数',
  `completion_year` int(11) DEFAULT NULL COMMENT '建成年份',
  `building_type` varchar(32) DEFAULT NULL COMMENT '建筑类型',
  `deal_unit_price` decimal(14,2) DEFAULT NULL COMMENT '成交均价',
  `five_years` varchar(32) DEFAULT NULL COMMENT '房屋满五年',
  `list_date` date DEFAULT NULL COMMENT '挂牌日期',
  `list_price` decimal(14,2) DEFAULT NULL COMMENT '挂牌价',
  `deal_price` decimal(14,2) DEFAULT NULL COMMENT '成交价',
  `deal_date` date DEFAULT NULL COMMENT '成交日期',
  `deal_month` tinyint(4) DEFAULT NULL COMMENT '成交月',
  `deal_year` int(11) DEFAULT NULL COMMENT '成交年',
  `deal_cycle` int(11) DEFAULT NULL COMMENT '成交周期（天）',
  `city_code` varchar(32) NOT NULL COMMENT '城市码',
  `county_code` varchar(32) NOT NULL COMMENT '区县码',
  `street_code` varchar(64) NOT NULL COMMENT '街区码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lianjia_house_num` (`lianjia_house_id`),
  KEY `city_code` (`city_code`,`county_code`,`street_code`)
) ENGINE=InnoDB AUTO_INCREMENT=555504 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Table structure for table `county_init_record` */

DROP TABLE IF EXISTS `county_init_record`;

CREATE TABLE `county_init_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `county_id` int(11) NOT NULL,
  `county_code` varchar(64) NOT NULL,
  `county_name` varchar(64) NOT NULL,
  `city_id` int(11) NOT NULL,
  `city_name` varchar(32) NOT NULL,
  `city_code` varchar(32) NOT NULL,
  `province_id` int(11) NOT NULL,
  `province_name` varchar(32) NOT NULL,
  `phase` tinyint(2) NOT NULL COMMENT '初始化阶段',
  `is_pressing` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否在处理中',
  `community_last_update_time` datetime DEFAULT NULL COMMENT '小区最后更新时间',
  `deal_history_last_update_time` datetime DEFAULT NULL COMMENT '成交记录最后更新时间',
  `community_num` int(11) DEFAULT NULL COMMENT '小区数',
  `deal_num` int(11) DEFAULT NULL COMMENT '成交记录数',
  `house_num` int(11) DEFAULT NULL COMMENT '房源数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Table structure for table `house` */

DROP TABLE IF EXISTS `house`;

CREATE TABLE `house` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `community_id` int(11) DEFAULT NULL,
  `lianjia_community_id` varchar(32) DEFAULT NULL,
  `lianjia_house_id` varchar(32) DEFAULT NULL COMMENT '链家房源编号',
  `model` varchar(32) DEFAULT NULL COMMENT '房屋格局',
  `area` decimal(14,2) DEFAULT NULL COMMENT '房屋面积',
  `face_to` varchar(32) DEFAULT NULL COMMENT '朝向',
  `fitment_situation` varchar(32) DEFAULT NULL COMMENT '装修情况',
  `floor_type` varchar(32) DEFAULT NULL COMMENT '楼层情况',
  `total_floor_num` int(11) DEFAULT NULL COMMENT '总楼层数',
  `completion_year` int(11) DEFAULT NULL COMMENT '建成年份',
  `building_type` varchar(32) DEFAULT NULL COMMENT '建筑类型',
  `villa_tag` varchar(32) DEFAULT NULL COMMENT '别墅标志',
  `follower_num` int(11) DEFAULT '0' COMMENT '关注人数',
  `five_years` varchar(32) DEFAULT NULL COMMENT '房屋满五年',
  `list_price` decimal(14,2) DEFAULT NULL COMMENT '挂牌价',
  `unit_price` decimal(14,2) DEFAULT NULL COMMENT '单价',
  `city_code` varchar(32) NOT NULL COMMENT '城市码',
  `county_code` varchar(32) NOT NULL COMMENT '区县码',
  `street_code` varchar(32) NOT NULL COMMENT '街区码',
  `good_tag` varchar(32) DEFAULT NULL COMMENT '必看好房',
  `look_tag` varchar(32) DEFAULT NULL COMMENT '随时看房',
  `inner_area` decimal(14,2) DEFAULT NULL COMMENT '套内面积',
  `elevator_ratio` varchar(32) DEFAULT NULL COMMENT '梯户比例',
  `heating_method` varchar(32) DEFAULT NULL COMMENT '供暖方式',
  `structure` varchar(32) DEFAULT NULL COMMENT '结构：平层、跃层、错层、复式',
  `list_date` date DEFAULT NULL COMMENT '挂牌日期',
  `last_deal_date` date DEFAULT NULL COMMENT '上次交易日期',
  `trade_type` varchar(32) DEFAULT NULL COMMENT '交易权属',
  `house_use` varchar(32) DEFAULT NULL COMMENT '房屋用途；别墅、普通住宅',
  `property_type` varchar(32) DEFAULT NULL COMMENT '产权所属',
  `property_permit` varchar(32) DEFAULT NULL COMMENT '产权证信息',
  `guaranty_message` varchar(128) DEFAULT NULL COMMENT '抵押信息',
  `is_deal` tinyint(1) NOT NULL DEFAULT '0' COMMENT '成交标志',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `lianjia_house_num` (`lianjia_house_id`)
) ENGINE=InnoDB AUTO_INCREMENT=118095 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Table structure for table `watch_community` */

DROP TABLE IF EXISTS `watch_community`;

CREATE TABLE `watch_community` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `community_id` int(11) DEFAULT NULL,
  `lianjia_community_id` varchar(32) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `city_code` varchar(32) NOT NULL,
  `county_code` varchar(32) NOT NULL,
  `street_code` varchar(64) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


