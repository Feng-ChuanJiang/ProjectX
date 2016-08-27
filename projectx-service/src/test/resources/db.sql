CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `photos` varchar(80) DEFAULT NULL,
  `mobile_phone` varchar(24) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `constellation` varchar(8) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `lndustry` varchar(48) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `labels` varchar(48) DEFAULT NULL,
  `longitude` decimal(24,17) DEFAULT NULL,
  `latitude` decimal(24,17) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `Interact_id` bigint(20) DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(320) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(80) DEFAULT NULL,
  `company_logo` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12351 DEFAULT CHARSET=utf8;
CREATE TABLE `education` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `university` varchar(32) DEFAULT NULL,
  `degree` varchar(32) DEFAULT NULL,
  `majorX` varchar(32) DEFAULT NULL,
  `majorX_for_short` varchar(8) DEFAULT NULL,
  `majorY` varchar(32) DEFAULT NULL,
  `majorY_for_short` varchar(8) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(48) DEFAULT NULL,
  `file_name` varchar(48) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2380 DEFAULT CHARSET=utf8;
CREATE TABLE `interact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(480) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `privacy_permission` int(11) DEFAULT NULL,
  `tag` int(11) DEFAULT NULL,
  `longitude` decimal(24,17) DEFAULT NULL,
  `latitude` decimal(24,17) DEFAULT NULL,
  `address_describe` varchar(240) DEFAULT NULL,
  `picture` varchar(320) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
CREATE TABLE `interact_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Interact_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `tag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
CREATE TABLE `s` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `U` double DEFAULT NULL,
  `V` double DEFAULT NULL,
  `M` double DEFAULT NULL,
  `N` double DEFAULT NULL,
  `A` double DEFAULT NULL,
  `B` double DEFAULT NULL,
  `C` double DEFAULT NULL,
  `D` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
CREATE TABLE `working_experience` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `company_for_short` varchar(8) DEFAULT NULL,
  `department` varchar(32) DEFAULT NULL,
  `department_for_short` varchar(8) DEFAULT NULL,
  `title` varchar(56) DEFAULT NULL,
  `title_for_short` varchar(8) DEFAULT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
