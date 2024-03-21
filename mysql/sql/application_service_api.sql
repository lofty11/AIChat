/* 创建数据库 */
SET NAMES 'utf8';
create database aichat character set utf8;
# CREATE DATABASE aichat;
use aichat;

alter database aichat character set utf8;
/* request_method列 1表示GET 2表示POST 3表示PUT 4表示DELETE*/
DROP TABLE IF EXISTS application_service;
CREATE TABLE application_service
(
    application_id bigint AUTO_INCREMENT PRIMARY KEY,
    api_name VARCHAR(255) NOT NULL,
    api_code VARCHAR(255) NOT NULL,
    request_url VARCHAR(255) NOT NULL,
    request_method INTEGER NOT NULL
);
alter table application_service character set utf8;

/* required列 0表示否 1表示是*/
DROP TABLE IF EXISTS extension_input;
CREATE TABLE extension_input
(
    input_id bigint AUTO_INCREMENT PRIMARY KEY,
    field_name VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    enumeration_range VARCHAR(255),
    required INTEGER NOT NULL,
    description VARCHAR(255),
    application_id bigint,
    constraint fk_application_id  foreign key (application_id) references application_service (application_id) on update cascade on delete cascade
);

alter table extension_input character set utf8;

DROP TABLE IF EXISTS extension_output;
CREATE TABLE extension_output
(
    out_id bigint AUTO_INCREMENT PRIMARY KEY,
    field_name VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    enumeration_range VARCHAR(255),
    required INTEGER NOT NULL,
    description VARCHAR(255),
    application_id bigint,
    constraint fk_application_id2  foreign key (application_id) references application_service (application_id) on update cascade on delete cascade
);
alter table extension_output character set utf8;


INSERT INTO application_service VALUES (NULL, '服务api', 'Serapi', 'https://modao.cc/', 1);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数1', 'q', 'string', NULL, 1, '定义', 1);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数2', 'q', 'string', NULL, 1, '定义', 1);
INSERT INTO extension_output VALUES (NULL, '搜索结果1', 'q', 'json', NULL, 0, NULL, 1);
INSERT INTO extension_output VALUES (NULL, '搜索结果2', 'q', 'json', NULL, 0, NULL, 1);