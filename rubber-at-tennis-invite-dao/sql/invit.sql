CREATE TABLE t_invite_info (
  Fid int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  Finvite_code varchar(50) NOT NULL DEFAULT '' COMMENT '邀请code',
  Fuid int(11) unsigned NOT NULL default 0 COMMENT '发起人id',
  Finvite_title varchar(50) NOT NULL DEFAULT '' COMMENT '邀请标题',
  Finvite_home_img varchar(50) NOT NULL DEFAULT '' COMMENT '邀请人主图',
  Finvite_img varchar(50) NOT NULL DEFAULT '' COMMENT '邀请相关图片',
  Finvite_number int(11) NOT NULL DEFAULT '' COMMENT '邀请人数',
  Fstart_time datetime DEFAULT NULL COMMENT '开始时间',
  Fend_time datetime DEFAULT NULL COMMENT '结束时间',
  Fcourt_code varchar(50) NOT NULL DEFAULT '' COMMENT '关联的球场code',
  Fcourt_province varchar(50) NOT NULL DEFAULT '' COMMENT '场地所在省',
  Fcourt_city varchar(50) NOT NULL DEFAULT '' COMMENT '球场所在市',
  Fcourt_district varchar(50) NOT NULL DEFAULT '' COMMENT '球场所在区',
  Fcourt_address varchar(255) NOT NULL DEFAULT '' COMMENT '球场详细地址',
  Fcourt_latitude varchar(255) NOT NULL DEFAULT '' COMMENT '球场场地所在纬度',
  Fcourt_longitude varchar(255) NOT NULL DEFAULT '' COMMENT '球场场地所在经度',
  Fremark varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  Fversion int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  Fstatus int(3) NOT NULL DEFAULT '10' COMMENT '状态 10表示初始化 20表示已发布，30表示已完成',
  Fcreate_time datetime DEFAULT NULL COMMENT '创建时间',
  Fupdate_time datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (Fid),
  UNIQUE KEY unq_invite_code (Finvite_code) USING BTREE,
  KEY Fidx_uid_invite (Fuid,Finvite_code) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀约详情表';


CREATE TABLE t_invite_user (
  Fid int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
  Finvite_code varchar(50) NOT NULL DEFAULT '' COMMENT '邀请code',
  Fjoin_uid int(11) NOT NULL default 0 COMMENT '参与人id',
  Fdata_version int(11) NOT NULL default 0  COMMENT '关联的数据版本',
  Fremark varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
  Fversion int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  Fstatus int(3) NOT NULL DEFAULT '10' COMMENT '状态 10表示报名成功 20表示退出',
  Fcreate_time datetime DEFAULT NULL COMMENT '创建时间',
  Fupdate_time datetime DEFAULT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (Fid),
  UNIQUE KEY unq_invite_uid (Finvite_code,Fjoin_uid) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邀约人详情表';
