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
    `expiration_date` timestamp DEFAULT (CURRENT_TIMESTAMP + INTERVAL 30 second ),
    PRIMARY KEY (`email`)
);

LOAD DATA INFILE 'C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\sido_data.csv'
    INTO TABLE `tripdb`.`sido`
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    (`sido_id`, `name`);

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
