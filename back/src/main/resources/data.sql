DROP DATABASE IF EXISTS tripdb;

CREATE DATABASE tripdb;
use tripdb;

CREATE TABLE `member`
(
    `member_id`         varchar(12)                                          NOT NULL,
    `password`          varchar(200)                                         NOT NULL,
    `nickname`          varchar(10)                                          NOT NULL unique,
    `email`             varchar(50)                                          NOT NULL unique,
    `image_url`         varchar(200)                                         NULL,
    `role`              char(1),
    `oauth_server_type` ENUM ('GENERAL','GOOGLE', 'KAKAO') default 'GENERAL' NOT NULL,
    PRIMARY KEY (`member_id`)
);

CREATE TABLE `oauth_member`
(
    `oauth_id`          int                               NOT NULL AUTO_INCREMENT,
    `email`             varchar(50)                       NOT NULL,
    `oauth_server_type` ENUM ('GENERAL','GOOGLE','KAKAO') NOT NULL,
    PRIMARY KEY (`oauth_id`)
);

CREATE TABLE `sido`
(
    `sido_id` int          NOT NULL AUTO_INCREMENT,
    `name`    varchar(100) NOT NULL,
    `imageUrl` varchar(200)   NULL,
    PRIMARY KEY (`sido_id`)
);

CREATE TABLE `attraction_info`
(
    `attraction_info_id` int             NOT NULL AUTO_INCREMENT,
    `sido_id`            int             NOT NULL,
    `name`               varchar(100)    NULL,
    `addr`               varchar(100)    NULL,
    `url`                varchar(200)    NULL,
    `latitude`           decimal(20, 17) NOT NULL,
    `longitude`          decimal(20, 17) NOT NULL,
    PRIMARY KEY (`attraction_info_id`),
    FOREIGN KEY (`sido_id`) REFERENCES `sido` (`sido_id`)
);

CREATE TABLE `attraction_description`
(
    `attraction_description_id` int not null,
    `overview` varchar(10000) not null,
    FOREIGN KEY (`attraction_description_id`) REFERENCES `attraction_info` (`attraction_info_id`)
);

CREATE TABLE `review`
(
    `review_id`          int           NOT NULL AUTO_INCREMENT,
    `attraction_info_id` int           NOT NULL,
    `member_id`          varchar(12)   NOT NULL,
    `title`              varchar(150)  NOT NULL,
    `content`            varchar(1500) NOT NULL,
    `hits`               int           not null default 0,
    `image_url`          varchar(200)  NULL,
    `create_date`        timestamp              DEFAULT CURRENT_TIMESTAMP,
    `update_date`        timestamp              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`review_id`),
    FOREIGN KEY (`attraction_info_id`) REFERENCES `attraction_info` (`attraction_info_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE
);

CREATE TABLE `comment`
(
    `comment_id`  int          NOT NULL AUTO_INCREMENT,
    `member_id`   varchar(12)  NOT NULL,
    `review_id`   int          NOT NULL,
    `content`     varchar(300) NOT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`comment_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`) ON DELETE CASCADE,
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE
);

CREATE TABLE `member_board_log`
(
    `member_board_log_id` int         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `member_id`           varchar(12) NOT NULL,
    `attraction_info_id`  int         NOT NULL,
    `create_date`         timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`attraction_info_id`) REFERENCES `attraction_info` (`attraction_info_id`)
);

CREATE TABLE `member_board_map`
(
    `member_board_map_id` int         NOT NULL AUTO_INCREMENT,
    `member_id`           varchar(12) NOT NULL,
    `sido_id`             int         NOT NULL,
    `now_location`        int     DEFAULT 0,
    `island_cnt`          int     DEFAULT 0,
    PRIMARY KEY (`member_board_map_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
    FOREIGN KEY (`sido_id`) REFERENCES `sido` (`sido_id`)
);

CREATE TABLE `board`
(
    `board_id`            int NOT NULL AUTO_INCREMENT,
    `member_board_map_id` int NOT NULL,
    `attraction_info_id`  int NOT NULL,
    `location`            int NOT NULL,
    PRIMARY KEY (`board_id`),
    FOREIGN KEY (`member_board_map_id`) REFERENCES `member_board_map` (`member_board_map_id`),
    FOREIGN KEY (`attraction_info_id`) REFERENCES `attraction_info` (`attraction_info_id`)
);

CREATE TABLE `member_board_like`
(
    `member_board_like_id` int         NOT NULL AUTO_INCREMENT,
    `review_id`            int         NOT NULL,
    `member_id`            varchar(12) NOT NULL,
    PRIMARY KEY (`member_board_like_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`) ON DELETE CASCADE,
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE
);

CREATE TABLE `score`
(
    `score_id`  int         NOT NULL AUTO_INCREMENT,
    `sido_id`   int         NOT NULL,
    `member_id` varchar(12) NOT NULL,
    `score`     int         NULL,
    PRIMARY KEY (`score_id`),
    FOREIGN KEY (`sido_id`) REFERENCES `sido` (`sido_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON DELETE CASCADE
);

CREATE TABLE `restaurant`
(
    `restaurant_id` int             NOT NULL AUTO_INCREMENT,
    `name`          varchar(100)    NULL,
    `content`       varchar(1000)   NULL,
    `tel`           varchar(30)     NULL,
    `latitude`      decimal(20, 17) NULL,
    `longitude`     decimal(20, 17) NULL,
    PRIMARY KEY (`restaurant_id`)
);

CREATE TABLE `mail`
(
    `email`           varchar(50) NOT NULL,
    `key`             varchar(10) NOT NULL,
    `expiration_date` timestamp DEFAULT (CURRENT_TIMESTAMP + INTERVAL 5 minute ),
    PRIMARY KEY (`email`)
);

# LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\sido_data.csv'
#     INTO TABLE `tripdb`.`sido`
#     FIELDS TERMINATED BY ','
#     ENCLOSED BY '"'
#     LINES TERMINATED BY '\n'
#     (`sido_id`, `name`);

INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (1, '서울', '|bf21e15f-e233-431a-b650-ce5dba9e34b6.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (2, '인천', '|5e34adc7-84eb-4d0c-939e-a169bceaf457.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (3, '대전', '|b2c558c9-c654-4e51-a246-e02e4ff91bb6.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (4, '대구', '|fe71911d-042a-4922-8cdd-ecf85d7102e0.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (5, '광주', '|e268df1e-8f5a-4a36-a4a0-a7bea97c8a2c.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (6, '부산', '|793c7b82-1c1f-4f3a-bef0-a7a7e6c7b598.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (7, '울산', '|7a92e9cf-e59f-4a19-99b8-d9ed643bd033.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (8, '세종특별자치시', '|052febb5-080a-4e0b-a1ad-3ed305410534.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (31, '경기도', '|fbeb2efb-3842-4a8e-b385-25298a6527bd.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (32, '강원도', '|98e47d86-972c-40b2-9931-7a35fe0ac1b3.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (33, '충청북도', '|ab99a7f6-410e-4a56-a642-561000b14a78.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (34, '충청남도', '|0f81d92f-96dc-4ee4-b281-728368de9d28.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (35, '경상북도', '|9790cdb6-ed6a-4061-9e47-cf87e54f7121.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (36, '경상남도', '|c6ad405e-4c6a-4071-b92b-77ed51de410c.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (37, '전라북도', '|fe9c52f8-767f-461c-80a0-260851e070e2.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (38, '전라남도', '|e780fe94-0904-4258-a527-333148f014e4.jpg');
INSERT INTO tripdb.sido (sido_id, name, imageUrl) VALUES (39, '제주도', '|46467e4a-b74f-4f8b-925e-cb8a454ae559.jpg');

LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\attraction_info_data.csv'
    INTO TABLE `tripdb`.`attraction_info`
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    (`attraction_info_id`, `sido_id`, `name`, `addr`, `url`, `latitude`, `longitude`);

LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\attraction_description_data.csv'
    INTO TABLE `tripdb`.`attraction_description`
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    (`attraction_description_id`, `overview`);