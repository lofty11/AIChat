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
    request_method INTEGER NOT NULL,
    creator_id  bigint DEFAULT NULL,
    creator_name varchar(128) DEFAULT NULL,
    modifier_id  bigint DEFAULT NULL,
    modifier_name  varchar(128) DEFAULT NULL,
    gmt_create  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gmt_modified  datetime DEFAULT NULL
);
alter table application_service character set utf8;

/* required列 0表示否 1表示是*/
DROP TABLE IF EXISTS extension_input;
CREATE TABLE extension_input
(
    input_id bigint AUTO_INCREMENT PRIMARY KEY,
    field_name VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    type INTEGER NOT NULL,
    enumeration_range VARCHAR(255),
    required INTEGER NOT NULL,
    description VARCHAR(255),
    application_id bigint,
    creator_id  bigint DEFAULT NULL,
    creator_name varchar(128) DEFAULT NULL,
    modifier_id  bigint DEFAULT NULL,
    modifier_name  varchar(128) DEFAULT NULL,
    gmt_create  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gmt_modified  datetime DEFAULT NULL,
    constraint fk_application_id  foreign key (application_id) references application_service (application_id) on update cascade on delete cascade
);

alter table extension_input character set utf8;

DROP TABLE IF EXISTS extension_output;
CREATE TABLE extension_output
(
    out_id bigint AUTO_INCREMENT PRIMARY KEY,
    field_name VARCHAR(255) NOT NULL,
    field VARCHAR(255) NOT NULL,
    type INTEGER NOT NULL,
    enumeration_range VARCHAR(255),
    required INTEGER NOT NULL,
    description VARCHAR(255),
    application_id bigint,
    creator_id  bigint DEFAULT NULL,
    creator_name varchar(128) DEFAULT NULL,
    modifier_id  bigint DEFAULT NULL,
    modifier_name  varchar(128) DEFAULT NULL,
    gmt_create  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gmt_modified  datetime DEFAULT NULL,
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
    value INTEGER NOT NULL,
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
    type INTEGER NOT NULL,
    enumeration_range VARCHAR(255),
    necessary INTEGER NOT NULL,
    description VARCHAR(255),
    deleted INTEGER NOT NULL,
    plug_id bigint,
    creator_id  bigint DEFAULT NULL,
    creator_name varchar(128) DEFAULT NULL,
    modifier_id  bigint DEFAULT NULL,
    modifier_name  varchar(128) DEFAULT NULL,
    gmt_create  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    gmt_modified  datetime DEFAULT NULL,
    constraint fk_plug_id2  foreign key (plug_id) references plug (id) on update cascade on delete cascade
);
alter table userpara character set utf8;

DROP TABLE IF EXISTS functions;
CREATE TABLE functions
(
    id bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ename VARCHAR(255) NOT NULL,
    type INTEGER NOT NULL,
    api INTEGER NOT NULL,
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

DROP TABLE IF EXISTS func_type;
CREATE TABLE func_type
(
    id  bigint AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL
);
alter table func_type character set utf8;

DROP TABLE IF EXISTS `chat`;
CREATE TABLE chat (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      user_id BIGINT NOT NULL,
                      chat_name VARCHAR(255) DEFAULT NULL,
                      creator_id  bigint DEFAULT NULL,
                      creator_name varchar(128) DEFAULT NULL,
                      modifier_id  bigint DEFAULT NULL,
                      modifier_name  varchar(128) DEFAULT NULL,
                      gmt_create  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      gmt_modified  datetime DEFAULT NULL
);
alter table chat character set utf8;

DROP TABLE IF EXISTS `message`;
CREATE TABLE message (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         chat_id BIGINT NOT NULL,
                         content MEDIUMTEXT,
                         type TINYINT,
                         creator_id  bigint DEFAULT NULL,
                         creator_name varchar(128) DEFAULT NULL,
                         modifier_id  bigint DEFAULT NULL,
                         modifier_name  varchar(128) DEFAULT NULL,
                         gmt_create  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         gmt_modified  datetime DEFAULT NULL
);
alter table message  character set utf8;

INSERT INTO `plug` VALUES (NULL, 'plug_test', 'web_search', 'http请求', 1, 1, 0);
INSERT INTO `plug` VALUES (NULL, 'plug_test2', 'b', 'http', 1, 1, 0);
INSERT INTO `plugpara` VALUES (NULL,'aaa', 1, 0,1);
INSERT INTO `plugpara` VALUES (NULL,'bbb', 1, 0,1);
INSERT INTO `userpara` VALUES (NULL, 'search', 'b', 1, '多个值由逗号分开', 1, 'e', 0, 1, NULL, NULL, NULL, NULL, NOW(), NULL);
INSERT INTO `userpara` VALUES (NULL, 'search1', 'b', 2, NULL, 1, 'e', 0, 1, NULL, NULL, NULL, NULL, NOW(), NULL);

INSERT INTO user VALUES (NULL,0, 'admin', '123456', '10000');
INSERT INTO user VALUES (NULL,1,'zmr','100000','181000000');


INSERT INTO application_service VALUES (NULL, '天气预报查询1', 'weather1', 'https://weatherquery.api.bdymkt.com/weather/query/by-area', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO application_service VALUES (NULL, '天气预报查询2', 'weather2', 'https://getweather.api.bdymkt.com/lundear/weather1d', 1, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO application_service VALUES (NULL, '天气预报查询3', 'weather3', 'https://jisuweather.api.bdymkt.com/weather/query', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO application_service VALUES (NULL, '汇率查询', 'exchange rate', 'https://jmwhpjbzlb.api.bdymkt.com/exchange-rate/query-ten-bank', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO application_service VALUES (NULL, '快递查询1', 'express1', 'https://jumkjibn.api.bdymkt.com/express/query/v1', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO application_service VALUES (NULL, '快递查询2', 'express2', 'https://jmexpressv2.api.bdymkt.com/express/query-v2', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO application_service VALUES (NULL, '快递查询3', 'express3', 'https://qgkdwlcx.api.bdymkt.com/express3/query', 1, 1, 'admin', NULL, NULL, NOW(), NULL);


INSERT INTO extension_input VALUES (NULL, '地名', 'area', 1, '地名不能带有“市”、“县”，比如查杭州，area传入“杭州”，而不是传入“杭州市”', 1, NULL, 1, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '地名id', 'areaId', 1, NULL, 0, NULL, 1, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '地区名称', 'areaCn', 1, '地名不能带有“市”、“县”，比如查杭州，area传入“杭州”，而不是传入“杭州市”', 1, 'areaCode、areaCn、ip、lng&lat四选一', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '地区编码', 'areaCode', 1, NULL, 0, 'areaCode、areaCn、ip、lng&lat四选一', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, 'ip地址', 'ip', 1, NULL, 0, 'areaCode、areaCn、ip、lng&lat四选一', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '纬度', 'lat', 1, NULL, 0, 'areaCode、areaCn、ip、lng&lat四选一', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '经度', 'lng', 1, NULL, 0, 'areaCode、areaCn、ip、lng&lat四选一', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '是否需要3小时段天气预', 'need3hour', 1, NULL, 0, '1:需要，0:不需要', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '是否需要生活指数数据', 'needIndex', 1, NULL, 0, '1:需要，0:不需要', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '是否需要24小时天气指数', 'needObserve', 1, NULL, 0, '1:需要，0:不需要', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '是否需要天气预警', 'needalarm', 1, NULL, 0, '1:需要，0:不需要', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '是否需要1小时段天气预报', 'need1hour', 1, NULL, 0, '1:需要，0:不需要', 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '城市', 'city', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '城市id', 'cityid', 1, NULL, 0, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '城市天气代号', 'citycode', 1, NULL, 0, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '经纬度', 'location', 1, NULL, 0, '纬度在前经度在后用,分割如：39.983424,116.322987', 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, 'ip', 'ip', 1, NULL, 0, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '银行编码', 'bankCode', 1, '工商银行：ICBC，中国银行：BOC，农业银行：ABCHINA，交通银行：BANKCOMM，建设银行：CCB，招商银行：CMBCHINA，光大银行：CEBBANK，浦发银行：SPDB，兴业银行：CIB，中信银行：ECITIC', 1, NULL, 4, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '快递单号', 'number', 1, NULL, 1, NULL, 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '手机号', 'mobile', 1, NULL, 0, '查顺丰快递时要输入寄件人或收件人手机号', 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '快递代码', 'expressCode', 1, NULL, 1, '自动识别请传auto', 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '快递公司编号', 'expressCode', 1, NULL, 0, '例如圆通:YTO。注意：快递公司编号不传时，系统会自动识别快递公司编号，但响应时间会比传递快递编号略长', 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '运单编号', 'number', 1, NULL, 1, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '手机号', 'mobile', 1, NULL, 0, '顺丰速运/丰网速运需要传入收/寄件人手机号或后四位手机号', 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '快递代号', 'expressCode', 1, NULL, 0, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '快递编号', 'number', 1, NULL, 1, '顺丰速运/丰网速运需要传入收/寄件人手机号或后四位手机号', 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_input VALUES (NULL, '手机号', 'mobile', 1, NULL, 0, '查顺丰时要输入寄件人或收件人手机号, 11位完整手机号或手机号后4位', 7, 1, 'admin', NULL, NULL, NOW(), NULL);


INSERT INTO extension_output VALUES (NULL, '地名', 'area', 1, NULL, 1, NULL, 1, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '当前实时天气', 'now', 3, NULL, 1, NULL, 1, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '未来7天天气', 'dayWeathers', 2, NULL, 0, '按日期从早到晚排序', 1, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '白天天气', 'day', 3, NULL, 1, NULL, 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '夜间天气', 'night', 3, NULL, 1, NULL, 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '城市信息', 'cityInfo', 3, NULL, 1, NULL, 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '当前天气', 'now', 3, NULL, 1, NULL, 2, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '城市', 'city', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '日期', 'date', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '天气', 'weather', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '当前温度', 'temp', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '最高温度', 'temphigh', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '最低温度', 'templow', 1, NULL, 1, NULL, 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '指标', 'index', 2, NULL, 0, '空调指数和运动指数', 3, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '货币数量', 'listSize', 4, NULL, 1, NULL, 4, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '汇率集合', 'list', 2, NULL, 1, NULL, 4, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '银行编码', 'name', 1, NULL, 0, NULL, 4, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '日期', 'day', 1, NULL, 0, NULL, 4, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '时间', 'time', 1, NULL, 0, NULL, 4, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递公司', 'expressName', 1, NULL, 1, NULL, 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递单号', 'number', 1, NULL, 1, NULL, 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递公司', 'expressName', 1, NULL, 1, NULL, 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递派送状态', 'status', 4, '1-13', 1, 'status字段说明：1在途中，2派送中，3已签收，4派送失败，5揽收，6退回，7转单，8疑难，9退签，10待清关，11清关中，12已清关，13清关异常', 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递配送轨迹', 'logisticsList', 2, NULL, 0, NULL, 5, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递公司名称', 'expressCompanyName', 1, NULL, 1, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递单号', 'taskNo', 1, NULL, 1, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '物流状态', 'logisticsStatusDesc', 1, NULL, 1, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '运单号物流流转当前最新变更时间', 'theLastTime', 1, NULL, 1, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '运单号物流流转当前最新描述', 'theLastMessage', 1, NULL, 1, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '从揽收到送达所耗时间', 'takeTime', 1, NULL, 0, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '物流明细', 'logisticsTraceDetails', 2, NULL, 0, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递员', 'courier', 1, NULL, 0, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递员联系方式', 'courierPhone', 1, NULL, 0, NULL, 6, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递公司名称', 'logisticsCompanyName', 1, NULL, 1, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '快递单号名称', 'mailNo', 1, NULL, 1, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '运单号物流流转当前最新变更时间', 'theLastTime', 1, NULL, 1, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '运单号物流流转当前最新描述', 'theLastMessage', 1, NULL, 1, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '运单号当前物流状态文字描述', 'logisticsStatusDesc', 1, NULL, 1, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '从揽收到送达所耗时间', 'takeTime', 1, NULL, 0, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);
INSERT INTO extension_output VALUES (NULL, '物流明细', 'logisticsTraceDetailList', 2, NULL, 0, NULL, 7, 1, 'admin', NULL, NULL, NOW(), NULL);


INSERT INTO `functions` VALUES (NULL, '获取实时天气1', 'get_current_weather1', 1, 1, '天气预报查询', 0);
INSERT INTO `functions` VALUES (NULL, '获取实时天气2', 'get_current_weather2', 1, 2, '天气预报查询', 0);
INSERT INTO `functions` VALUES (NULL, '获取实时天气3', 'get_current_weather3', 1, 3, '天气预报查询', 0);
INSERT INTO `functions` VALUES (NULL, '汇率查询', 'get_exchange_rate', 1, 4, '十大银行汇率查询', 0);
INSERT INTO `functions` VALUES (NULL, '快递查询1', 'get_express_status1', 1, 5, '快递状态查询', 0);
INSERT INTO `functions` VALUES (NULL, '快递查询2', 'get_express_status2', 1, 6, '快递状态查询', 0);
INSERT INTO `functions` VALUES (NULL, '快递查询3', 'get_express_status3', 1, 7, '快递状态查询', 0);


INSERT INTO type_union VALUES(NULL, 'string');
INSERT INTO type_union VALUES(NULL, 'array');
INSERT INTO type_union VALUES(NULL, 'json');
INSERT INTO type_union VALUES(NULL, 'int');
INSERT INTO type_union VALUES(NULL, 'bool');
INSERT INTO type_union VALUES(NULL, 'float');

INSERT INTO func_type VALUES(NULL, 'http请求');
INSERT INTO func_type VALUES(NULL, 'http请求2');
INSERT INTO func_type VALUES(NULL, 'http请求3');