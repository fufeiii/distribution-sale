--liquibase formatted sql
--changeset fufeii:1
/*
    建库参考！！！！
    CREATE DATABASE `ds` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
    USE `ds`;
 */
-- 初始化表结构
create table ds_account
(
    id                   bigint   not null comment '主键' primary key,
    member_id            bigint   not null comment '会员主键',
    money_total_history  int      not null comment '历史总金额',
    money_total          int      not null comment '总金额',
    money_available      int      not null comment '可用金额',
    money_frozen         int      not null comment '冻结金额',
    points_total_history int      not null comment '历史总积分',
    points_total         int      not null comment '总积分',
    points_available     int      not null comment '可用积分',
    points_frozen        int      not null comment '冻结积分',
    version              int      not null comment '乐观锁',
    update_date_time     datetime not null comment '更新时间',
    create_date_time     datetime not null comment '创建时间',
    constraint uq_MemberId unique (member_id)
) comment '会员账户';

create table ds_account_change_record
(
    id                     bigint       not null comment '主键' primary key,
    member_id              bigint       not null comment '会员主键',
    account_id             bigint       not null comment '账户主键',
    account_type           tinyint      not null comment '账户类型',
    change_type            tinyint      not null comment '变动类型',
    change_biz_number      varchar(32)  not null comment '变动业务编号',
    before_available_count int          not null comment '变动前可用总数',
    after_available_count  int          not null comment '变动后可用总数',
    change_count           int          not null comment '变动数',
    memo                   varchar(128) not null comment '变动描述',
    version                int          not null comment '乐观锁',
    update_date_time       datetime     not null comment '更新时间',
    create_date_time       datetime     not null comment '创建时间',
    constraint uq_ChangeBizNumber unique (change_biz_number)
) comment '账户变动记录';

create table ds_allot_profit_config
(
    id                   bigint      not null comment '主键' primary key,
    platform_username    varchar(32) not null comment '平台标识',
    platform_nickname    varchar(16) not null comment '平台名称',
    account_type         tinyint     not null comment '账户类型',
    profit_type          tinyint     not null comment '分润类型',
    calculate_mode       tinyint     not null comment '计算方式',
    profit_level         tinyint     not null comment '分润等级',
    profit_ratio         int         not null comment '分润比例',
    member_identity_type tinyint     not null comment '会员类型',
    member_rank_type     tinyint     not null comment '会员段位',
    state                tinyint     not null comment '状态',
    version              int         not null comment '乐观锁',
    update_date_time     datetime    not null comment '更新时间',
    create_date_time     datetime    not null comment '创建时间',
    constraint uq_PS_AT_PT_PL_MIT_MRT unique (platform_username, account_type, profit_type, profit_level,
                                              member_identity_type, member_rank_type)
) comment '分润配置';

create table ds_allot_profit_event
(
    id                bigint       not null comment '主键' primary key,
    platform_username varchar(32)  not null comment '平台标识',
    platform_nickname varchar(16)  not null comment '平台名称',
    profit_type       tinyint      not null comment '分润类型',
    trigger_member_id bigint       not null comment '事件触发会员主键',
    event_number      varchar(32)  not null comment '事件编号',
    event_amount      int          not null comment '事件金额',
    notify_state      int          not null comment '通知状态',
    memo              varchar(255) not null comment '备注',
    version           int          not null comment '乐观锁',
    create_date_time  datetime     not null comment '创建时间',
    update_date_time  datetime     not null comment '更新时间',
    constraint uq_PlatformUsername_EventNumber unique (platform_username, event_number)
) comment '分润事件';

create table ds_member
(
    id                bigint       not null comment '主键' primary key,
    platform_username varchar(32)  not null comment '平台标识',
    platform_nickname varchar(16)  not null comment '平台名称',
    username          varchar(32)  not null comment '会员标识',
    nickname          varchar(16)  not null comment '会员昵称',
    avatar            varchar(255) not null comment '会员头像',
    first_inviter_id  bigint       not null comment '第一级邀请人主键',
    second_inviter_id bigint       not null comment '第二级邀请人主键',
    third_inviter_id  bigint       not null comment '第三级邀请人主键',
    identity_type     tinyint      not null comment '身份类型',
    rank_type         tinyint      not null comment '段位类型',
    state             tinyint      not null comment '状态',
    version           int          not null comment '乐观锁',
    update_date_time  datetime     not null comment '更新时间',
    create_date_time  datetime     not null comment '创建时间',
    constraint uq_PlatformUsername_Username unique (platform_username, username)
) comment '会员信息';

create table ds_member_rank_config
(
    id                bigint      not null comment '主键' primary key,
    platform_username varchar(32) not null comment '平台标识',
    platform_nickname varchar(16) not null comment '平台名称',
    member_rank_type  tinyint     not null comment '会员段位类型',
    begin_points      int         not null comment '开始积分',
    end_points        int         not null comment '结束积分',
    state             tinyint     not null comment '状态',
    version           int         not null comment '乐观锁',
    update_date_time  datetime    not null comment '更新时间',
    create_date_time  datetime    not null comment '创建时间',
    constraint uq_PlatformUsername_MemberRankType unique (platform_username, member_rank_type)
) comment '会员段位配置';

create table ds_platform
(
    id               bigint       not null comment '主键' primary key,
    username         varchar(32)  not null comment '平台标识',
    nickname         varchar(16)  not null comment '平台名称',
    sk               char(32)     not null comment '签名密钥',
    notify_url       varchar(128) not null comment '回调地址',
    state            tinyint      not null comment '状态',
    version          int          not null comment '乐观锁',
    update_date_time datetime     not null comment '更新时间',
    create_date_time datetime     not null comment '创建时间',
    constraint uq_username unique (username)
) comment '平台信息';

create table ds_profit_income_record
(
    id               bigint       not null comment '主键' primary key,
    profit_event_id  bigint       not null comment '事件主键',
    account_type     tinyint      not null comment '账户类型',
    impact_member_id bigint       not null comment '获利会员主键',
    income_count     int          not null comment '获利数',
    memo             varchar(128) not null comment '备注',
    version          int          not null comment '乐观锁',
    update_date_time datetime     not null comment '更新时间',
    create_date_time datetime     not null comment '创建时间'
) comment '利润收益记录';
create index idx_ProfitEventId on ds_profit_income_record (profit_event_id);

create table ds_system_user
(
    id                bigint       not null comment '主键' primary key,
    platform_username varchar(32)  not null comment '平台标识',
    platform_nickname varchar(16)  not null comment '平台名称',
    username          varchar(16)  not null comment '登录名',
    nickname          varchar(16)  not null comment '用户名',
    avatar            varchar(255) not null comment '头像',
    password          char(32)     not null comment '密码',
    slat              char(8)      not null comment '密码盐',
    state             tinyint      not null comment '状态',
    version           int          not null comment '乐观锁',
    update_date_time  datetime     not null comment '更新时间',
    create_date_time  datetime     not null comment '创建时间',
    constraint uq_username unique (username)
) comment '系统用户';


-- 初始化数据
INSERT INTO ds_system_user(`id`, `platform_username`, `platform_nickname`, `username`, `nickname`, `avatar`, `password`,
                           `slat`, `state`, `version`, `update_date_time`, `create_date_time`)
VALUES (1, '', '0', 'admin', '超级管理员', '', '96864683d80a0d1768b3c8b90f818191', 'abcdefgh', 1, 1, now(), now());



