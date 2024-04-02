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



/*Plug数据库中的表*/

DROP TABLE IF EXISTS plug;
CREATE TABLE plug
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    available INTEGER NOT NULL,
    open INTEGER NOT NULL,
    deleted INTEGER NOT NULL
);
alter table plug character set utf8;

DROP TABLE IF EXISTS plugpara;
CREATE TABLE plugpara
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    deleted INTEGER NOT NULL,
    plug_id bigint,
    constraint fk_plug_id1  foreign key (plug_id) references plug (id) on update cascade on delete cascade
);
alter table plugpara character set utf8;

DROP TABLE IF EXISTS userpara;
CREATE TABLE userpara
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    necessary INTEGER NOT NULL,
    description VARCHAR(255) NOT NULL,
    deleted INTEGER NOT NULL,
    plug_id bigint,
    constraint fk_plug_id2  foreign key (plug_id) references plug (id) on update cascade on delete cascade
);
alter table userpara character set utf8;

DROP TABLE IF EXISTS functions;
CREATE TABLE functions
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ename VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    api VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    deleted INTEGER NOT NULL
);
alter table functions character set utf8;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      user_level INT NOT NULL,
                      user_name VARCHAR(255) NOT NULL,
                      user_password VARCHAR(255) NOT NULL,
                      user_tel VARCHAR(20)
);
alter table user character set utf8;

DROP TABLE IF EXISTS type_union;
CREATE TABLE type_union
(
    id  bigint AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);
alter table type_union character set utf8;


INSERT INTO `functions` VALUES (NULL, 'search', 'web_search', 'http', 'api', '联网搜索工具1', 0);
INSERT INTO `functions` VALUES (NULL, 'search1', 'web_search', 'http', 'api', '联网搜索工具2', 0);
INSERT INTO `functions` VALUES (NULL, 'search2', 'web_search', 'http', 'api', '联网搜索工具3', 0);
INSERT INTO `plug` VALUES (NULL, 'plug_test', 'web_search', 'http请求', 1, 1, 0);
INSERT INTO `plug` VALUES (NULL, 'plug_test2', 'b', 'http', 1, 1, 1);
INSERT INTO `plugpara` VALUES (NULL,'aaa', 1, 0,1);
INSERT INTO `plugpara` VALUES (NULL,'bbb', 1, 0,1);
INSERT INTO `userpara` VALUES (NULL, 'search', 'b', 'c', 1, 'e', 0, 1);
INSERT INTO `userpara` VALUES (NULL, 'search1', 'b', 'c', 1, 'e', 0, 1);

INSERT INTO user VALUES (NULL,0, 'admin', '123456', '10000');
INSERT INTO user VALUES (NULL,1,'zmr','100000','181000000');


INSERT INTO application_service VALUES (NULL, '服务api', 'Serapi', 'https://service/', 1);
INSERT INTO application_service VALUES (NULL, '测试api', 'test_api', 'https://test.com/', 2);
INSERT INTO application_service VALUES (NULL, '毕设api', 'design', 'https://design/', 3);
INSERT INTO application_service VALUES (NULL, '墨刀api', 'modao', 'https://modao.cc/', 4);

INSERT INTO extension_input VALUES (NULL, '搜索查询参数1', 'search1', 'string', '仅限小写字母', 1, '定义', 1);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数2', 'search2', 'array', NULL, 1, '定义', 1);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数3', 'search3', 'json', NULL, 1, '定义', 1);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数4', 'search4', 'integer', NULL, 1, '定义', 1);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数1', 'search1', 'string', NULL, 1, '定义', 2);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数2', 'search2', 'array', NULL, 1, '定义', 2);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数3', 'search3', 'json', NULL, 1, '定义', 2);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数4', 'search4', 'integer', NULL, 1, '定义', 2);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数1', 'search1', 'string', NULL, 1, '定义', 3);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数2', 'search2', 'array', NULL, 1, '定义', 3);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数3', 'search3', 'json', NULL, 1, '定义', 3);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数4', 'search4', 'integer', NULL, 1, '定义', 3);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数1', 'search1', 'string', NULL, 1, '定义', 4);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数2', 'search2', 'array', NULL, 1, '定义', 4);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数3', 'search3', 'json', NULL, 1, '定义', 4);
INSERT INTO extension_input VALUES (NULL, '搜索查询参数4', 'search4', 'integer', NULL, 1, '定义', 4);

INSERT INTO extension_output VALUES (NULL, '搜索结果1', 'search1', 'string', NULL, 0, NULL, 1);
INSERT INTO extension_output VALUES (NULL, '搜索结果2', 'search2', 'json', NULL, 0, NULL, 1);
INSERT INTO extension_output VALUES (NULL, '搜索结果3', 'search3', 'array', NULL, 0, NULL, 1);
INSERT INTO extension_output VALUES (NULL, '搜索结果4', 'search4', 'integer', NULL, 0, NULL, 1);
INSERT INTO extension_output VALUES (NULL, '搜索结果1', 'search1', 'string', NULL, 0, NULL, 2);
INSERT INTO extension_output VALUES (NULL, '搜索结果2', 'search2', 'json', NULL, 0, NULL, 2);
INSERT INTO extension_output VALUES (NULL, '搜索结果3', 'search3', 'array', NULL, 0, NULL, 2);
INSERT INTO extension_output VALUES (NULL, '搜索结果4', 'search4', 'integer', NULL, 0, NULL, 2);
INSERT INTO extension_output VALUES (NULL, '搜索结果1', 'search1', 'string', NULL, 0, NULL, 3);
INSERT INTO extension_output VALUES (NULL, '搜索结果2', 'search2', 'json', NULL, 0, NULL, 3);
INSERT INTO extension_output VALUES (NULL, '搜索结果3', 'search3', 'array', NULL, 0, NULL, 3);
INSERT INTO extension_output VALUES (NULL, '搜索结果4', 'search4', 'integer', NULL, 0, NULL, 3);
INSERT INTO extension_output VALUES (NULL, '搜索结果1', 'search1', 'string', NULL, 0, NULL, 4);
INSERT INTO extension_output VALUES (NULL, '搜索结果2', 'search2', 'json', NULL, 0, NULL, 4);
INSERT INTO extension_output VALUES (NULL, '搜索结果3', 'search3', 'array', NULL, 0, NULL, 4);
INSERT INTO extension_output VALUES (NULL, '搜索结果4', 'search4', 'integer', NULL, 0, NULL, 4);

INSERT INTO type_union VALUES(NULL, 'string');
INSERT INTO type_union VALUES(NULL, 'array');
INSERT INTO type_union VALUES(NULL, 'json');
INSERT INTO type_union VALUES(NULL, 'integer');