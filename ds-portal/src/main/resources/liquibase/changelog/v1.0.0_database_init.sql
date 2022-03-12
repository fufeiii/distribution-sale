--liquibase formatted sql
--changeset fufeii:v1.0.0_database_init.sql_1
/*
    建库参考！！！！
    CREATE DATABASE `ds` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;
    USE `ds`;
 */

create table ds_account
(
    id                     bigint   not null comment '主键' primary key,
    member_id              bigint   not null comment '会员主键',
    money_total            bigint   not null comment '总金额',
    money_available        bigint   not null comment '可用金额',
    money_frozen           bigint   not null comment '冻结金额',
    integral_total_history int      not null comment '历史总积分',
    integral_total         int      not null comment '总积分',
    integral_available     int      not null comment '可用积分',
    integral_frozen        int      not null comment '冻结积分',
    state                  tinyint(1) not null comment '账户状态',
    memo                   varchar(255) null comment '备注',
    version                int      not null comment '乐观锁',
    update_date_time       datetime not null comment '更新时间',
    create_date_time       datetime not null comment '创建时间',
    constraint uk_member_id unique (member_id)
) comment '会员账户';

create table ds_account_record
(
    id                  bigint   not null comment '主键' primary key,
    member_id           bigint   not null comment '用户主键',
    account_id          bigint   not null comment '账户主键',
    account_type        tinyint(1) not null comment '账户类型',
    before_change_total bigint   not null comment '变动前总数',
    after_change_total  bigint   not null comment '变动后总数',
    change_amount       bigint   not null comment '变动数',
    change_type         tinyint  not null comment '变动类型',
    profit_record_id    bigint   not null comment '变动记录主键',
    memo                varchar(255) null comment '备注',
    create_date_time    datetime not null comment '创建时间'
) comment '账户记录';

create table ds_member
(
    id               bigint       not null comment '主键' primary key,
    username         varchar(16)  not null comment '用户标识',
    nickname         varchar(16)  not null comment '用户昵称',
    avatar           varchar(255) not null comment '用户头像',
    fir_parent       varchar(16)  not null comment '第一层级父级标识',
    sec_parent       varchar(16)  not null comment '第二层级父级标识',
    thr_parent       varchar(16)  not null comment '第三层级父级标识',
    parent_path      varchar(64)  not null comment '父级全路径',
    identity_type    tinyint(1) not null comment '身份类型',
    rank_type        tinyint(1) not null comment '段位类型',
    state            tinyint(1) not null comment '用户状态',
    memo             varchar(255) null comment '备注',
    version          int          not null comment '乐观锁',
    update_date_time datetime     not null comment '更新时间',
    create_date_time datetime     not null comment '创建时间',
    constraint uk_username unique (username)
) comment '会员信息';
create index idx_parent_username on ds_member (fir_parent);

create table ds_profit_event
(
    id                      bigint      not null comment '主键' primary key,
    profit_type             tinyint(1) not null comment '分润类型',
    trigger_member_username varchar(16) not null comment '事件触发人',
    event_number            varchar(32) not null comment '事件编号，交易分润时为商户订单编号',
    event_price             bigint      not null comment '事件金额，目前仅交易分润记录订单金额',
    memo                    varchar(255) null comment '备注',
    create_date_time        datetime    not null comment '创建事件'
) comment '分润事件';

create table ds_profit_param
(
    id                   bigint   not null comment '主键' primary key,
    account_type         tinyint(1) not null comment '账户类型',
    profit_type          tinyint(1) not null comment '分润类型',
    calculate_mode       tinyint(1) not null comment '计算方式',
    profit_level         tinyint(1) not null comment '分润等级',
    profit_ratio         int      not null comment '分润比例',
    member_identity_type tinyint(1) not null comment '用户类型',
    member_rank_type     tinyint(1) not null comment '用户段位',
    state                tinyint(1) not null comment '状态',
    version              int      not null comment '乐观锁',
    update_date_time     datetime not null comment '更新时间',
    create_date_time     datetime not null comment '创建时间'
) comment '分润参数';

create table ds_profit_record
(
    id               bigint   not null comment '主键' primary key,
    profit_event_id  bigint   not null comment '事件主键',
    account_type     tinyint(1) not null comment '账户类型',
    impact_member_id bigint   not null comment '获利会员主键',
    income_amount    bigint   not null comment '获利数',
    memo             varchar(128) null comment '备注',
    create_date_time datetime not null comment '创建时间'
) comment '分润记录';
create index fk_profit_event_id on ds_profit_record (profit_event_id);

create table ds_rank_param
(
    id               bigint   not null comment '主键' primary key,
    member_rank_type tinyint(1) not null comment '用户段位类型',
    begin_integral   int      not null comment '开始积分',
    end_integral     int      not null comment '结束积分',
    state            tinyint(1) not null comment '状态',
    version          int      not null comment '乐观锁',
    update_date_time datetime not null comment '更新时间',
    create_date_time datetime not null comment '创建时间'
) comment '段位配置';

create table ds_withdraw_apply
(
    id               bigint      not null comment '主键' primary key,
    member_id        bigint      not null comment '用户主键',
    withdraw_number  varchar(32) not null comment '提现单号',
    withdraw_amount  bigint      not null comment '提现金额',
    fee_amount       bigint      not null comment '手续费',
    approval_time    datetime null comment '审批时间',
    state            tinyint(1) not null comment '状态',
    memo             varchar(255) null comment '备注',
    version          int         not null comment '乐观锁',
    update_date_time datetime    not null comment '更新时间',
    create_date_time datetime    not null comment '创建时间'
) comment '提现申请';

