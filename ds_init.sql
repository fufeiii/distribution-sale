/*
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Schema         : distribution_sale
 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ds_account
-- ----------------------------
DROP TABLE IF EXISTS `ds_account`;
CREATE TABLE `ds_account`
(
    `id`                     bigint(20)   NOT NULL COMMENT '主键',
    `member_id`              bigint(20)   NOT NULL COMMENT '会员主键',
    `money_total`            bigint(20)   NOT NULL COMMENT '总金额',
    `money_available`        bigint(20)   NOT NULL COMMENT '可用金额',
    `money_frozen`           bigint(20)   NOT NULL COMMENT '冻结金额',
    `integral_total_history` int(11)      NOT NULL COMMENT '历史总积分',
    `integral_total`         int(11)      NOT NULL COMMENT '总积分',
    `integral_available`     int(11)      NOT NULL COMMENT '可用积分',
    `integral_frozen`        int(11)      NOT NULL COMMENT '冻结积分',
    `state`                  tinyint(1)   NOT NULL COMMENT '账户状态',
    `memo`                   varchar(128) NULL DEFAULT NULL COMMENT '备注',
    `version`                int(11)      NOT NULL COMMENT '乐观锁',
    `update_time`            datetime(0)  NOT NULL COMMENT '更新时间',
    `create_time`            datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_merber_id` (`member_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '会员账户'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_account_record
-- ----------------------------
DROP TABLE IF EXISTS `ds_account_record`;
CREATE TABLE `ds_account_record`
(
    `id`                  bigint(20)   NOT NULL COMMENT '主键',
    `member_id`           bigint(20)   NOT NULL COMMENT '用户主键',
    `account_id`          bigint(20)   NOT NULL COMMENT '账户主键',
    `account_type`        tinyint(1)   NOT NULL COMMENT '账户类型',
    `before_change_total` bigint(20)   NOT NULL COMMENT '变动前总数',
    `after_change_total`  bigint(20)   NOT NULL COMMENT '变动后总数',
    `change_amount`       bigint(20)   NOT NULL COMMENT '变动数',
    `change_type`         int(11)      NOT NULL COMMENT '变动类型',
    `change_record_id`    bigint(20)   NOT NULL COMMENT '变动记录主键',
    `memo`                varchar(128) NULL DEFAULT NULL COMMENT '备注',
    `create_time`         datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '账户记录'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_member
-- ----------------------------
DROP TABLE IF EXISTS `ds_member`;
CREATE TABLE `ds_member`
(
    `id`            bigint(20)   NOT NULL COMMENT '主键',
    `username`      varchar(16)  NOT NULL COMMENT '用户标识',
    `nickname`      varchar(16)  NOT NULL COMMENT '用户昵称',
    `avatar`        varchar(128) NOT NULL COMMENT '用户头像',
    `fir_parent`    varchar(16)  NULL DEFAULT NULL COMMENT '第一层级父级标识',
    `sec_parent`    varchar(16)  NULL DEFAULT NULL COMMENT '第二层级父级标识',
    `thr_parent`    varchar(16)  NULL DEFAULT NULL COMMENT '第三层级父级标识',
    `parent_path`   varchar(64)  NULL DEFAULT NULL COMMENT '父级全路径',
    `identity_type` tinyint(1)   NOT NULL COMMENT '身份类型',
    `rank_type`     tinyint(1)   NOT NULL COMMENT '用户段位',
    `state`         tinyint(1)   NOT NULL COMMENT '用户状态',
    `memo`          varchar(128) NULL DEFAULT NULL COMMENT '备注',
    `version`       int(11)      NOT NULL COMMENT '乐观锁',
    `update_time`   datetime(0)  NOT NULL COMMENT '更新时间',
    `create_time`   datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_member_username` (`username`) USING BTREE,
    INDEX `idx_parent_username` (`fir_parent`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '会员信息'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_profit_event
-- ----------------------------
DROP TABLE IF EXISTS `ds_profit_event`;
CREATE TABLE `ds_profit_event`
(
    `id`                      bigint(20)   NOT NULL COMMENT '主键',
    `profit_type`             tinyint(1)   NOT NULL COMMENT '分润类型',
    `trigger_member_username` varchar(16)  NOT NULL COMMENT '事件触发人',
    `event_number`            varchar(32)  NULL DEFAULT NULL COMMENT '事件编号，交易分润时为商户订单编号',
    `event_price`             bigint(20)   NULL DEFAULT NULL COMMENT '事件金额，目前仅交易分润记录订单金额',
    `memo`                    varchar(255) NULL DEFAULT NULL COMMENT '备注',
    `create_time`             datetime(0)  NOT NULL COMMENT '创建事件',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '分润事件'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_profit_param
-- ----------------------------
DROP TABLE IF EXISTS `ds_profit_param`;
CREATE TABLE `ds_profit_param`
(
    `id`             bigint(20)  NOT NULL COMMENT '主键',
    `account_type`   tinyint(1)  NOT NULL COMMENT '账户类型',
    `profit_type`    tinyint(1)  NOT NULL COMMENT '分润类型',
    `calculate_mode` tinyint(1)  NOT NULL COMMENT '计算方式',
    `profit_level`   tinyint(1)  NOT NULL COMMENT '分润等级',
    `profit_ratio`   int(11)     NOT NULL COMMENT '分润比例',
    `member_type`    tinyint(1)  NOT NULL COMMENT '用户类型',
    `member_rank`    tinyint(1)  NOT NULL COMMENT '用户段位',
    `state`          tinyint(1)  NOT NULL COMMENT '状态',
    `version`        int(11)     NOT NULL COMMENT '乐观锁',
    `update_time`    datetime(0) NOT NULL COMMENT '更新时间',
    `create_time`    datetime(0) NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '分润参数'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_profit_record
-- ----------------------------
DROP TABLE IF EXISTS `ds_profit_record`;
CREATE TABLE `ds_profit_record`
(
    `id`               bigint(20)   NOT NULL COMMENT '主键',
    `event_id`         bigint(20)   NOT NULL COMMENT '事件主键',
    `account_type`     tinyint(1)   NOT NULL COMMENT '账户类型',
    `impact_member_id` bigint(20)   NOT NULL COMMENT '获利会员主键',
    `income_amount`    bigint(20)   NOT NULL COMMENT '获利数',
    `memo`             varchar(128) NULL DEFAULT NULL COMMENT '备注',
    `create_time`      datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_event_id` (`event_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '分润记录'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_rank_param
-- ----------------------------
DROP TABLE IF EXISTS `ds_rank_param`;
CREATE TABLE `ds_rank_param`
(
    `id`             bigint(20)  NOT NULL COMMENT '主键',
    `member_rank`    tinyint(1)  NOT NULL COMMENT '用户段位',
    `begin_integral` int(11)     NOT NULL COMMENT '开始积分',
    `end_integral`   int(11)     NOT NULL COMMENT '结束积分',
    `state`          tinyint(1)  NOT NULL COMMENT '状态',
    `version`        int(11)     NOT NULL COMMENT '乐观锁',
    `update_time`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '段位配置'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ds_withdraw_apply
-- ----------------------------
DROP TABLE IF EXISTS `ds_withdraw_apply`;
CREATE TABLE `ds_withdraw_apply`
(
    `id`              bigint(20)   NOT NULL COMMENT '主键',
    `approval_time`   datetime(0)  NULL DEFAULT NULL COMMENT '审批时间',
    `member_id`       bigint(20)   NOT NULL COMMENT '用户主键',
    `withdraw_number` varchar(32)  NOT NULL COMMENT '提现单号',
    `withdraw_amount` bigint(20)   NOT NULL COMMENT '提现金额',
    `fee_amount`      bigint(20)   NOT NULL COMMENT '手续费',
    `state`           tinyint(1)   NOT NULL COMMENT '状态',
    `memo`            varchar(128) NULL DEFAULT NULL COMMENT '备注',
    `version`         int(11)      NOT NULL COMMENT '乐观锁',
    `update_time`     datetime(0)  NOT NULL COMMENT '更新时间',
    `create_time`     datetime(0)  NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '提现申请'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
