-- create database
create database if not exists blog;

use blog;

-- user table
create table if not exists user (
    `id`           bigint                                 auto_increment comment 'id' primary key,
    `userAccount`  varchar(256)                           not null comment 'account',
    `userPassword` varchar(512)                           not null comment 'password',
    `userName`     varchar(256)                           null comment 'user name',
    `userAvatar`   varchar(1024)                          null comment 'user avatar',
    `userRole`     varchar(256) default 'user'            not null comment 'user role: user/admin',
    `createTime`   datetime     default CURRENT_TIMESTAMP not null comment 'create time',
    `updateTime`   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    `isDelete`     tinyint      default 0                 not null comment 'is delete',
    index idx_userAccount (`userAccount`)
) comment 'user' collate = utf8mb4_unicode_ci;

create table if not exists interview_question (
    `id` bigint auto_increment comment 'id' primary key,
    `language` varchar(256) not null comment 'language',
    `topic` varchar(256) not null comment 'topic group',
    `question` varchar(1024) not null comment 'interview question',
    `answer` text not null comment 'interview answer',
    `userId` bigint not null comment 'user id',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment 'creation time',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    `isDelete` tinyint default 0 not null comment 'is delete',
    index idx_userId(userId)
) comment 'interview question';

