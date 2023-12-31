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

create table if not exists question (
    `id` bigint auto_increment comment 'id' primary key,
    `title` varchar(512) not null comment 'title',
    `content` text not null comment 'content',
    `tags` varchar(1024) null comment 'tag list (json)',
    `answer` text not null comment 'question answer',
    `judgeCase` text not null comment 'judge case (json)',
    `userId` bigint not null comment 'user id',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment 'creation time',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    `idDelete` tinyint default 0 not null comment 'is delete',
    index idx_userId (userId)
) comment 'question';

create table if not exists submitted_question (
    `id` bigint auto_increment comment 'id' primary key,
    `language` varchar(128) not null comment 'programming language',
    `code` text not null comment 'user code',
    `judgeInfo` text null comment 'judge info',
    `status` tinyint default 0 not null comment 'result status: 0-wait, 1-in progress, 2-success, 3-failure',
    `questionId` bigint not null comment 'question id',
    `userId` bigint not null comment 'user id',
    `createTime` datetime default CURRENT_TIMESTAMP not null comment 'creation time',
    `updateTime` datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    `idDelete` tinyint default 0 not null comment 'is delete',
    index idx_questionId (questionId),
    index idx_userId (userId)
) comment 'submitted question';

