DROP DATABASE IF EXISTS tripdb;

CREATE DATABASE tripdb;
use tripdb;

CREATE TABLE `member`
(
    `member_id`         varchar(12)                                          NOT NULL,
    `password`          varchar(200)                                         NOT NULL,
    `nickname`          varchar(10)                                          NOT NULL unique,
    `email`             varchar(50)                                          NOT NULL,
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
    `description`        varchar(500)    NULL,
    `latitude`           decimal(20, 17) NOT NULL,
    `longitude`          decimal(20, 17) NOT NULL,
    PRIMARY KEY (`attraction_info_id`),
    FOREIGN KEY (`sido_id`) REFERENCES `sido` (`sido_id`)
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
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
);

CREATE TABLE `comment`
(
    `comment_id`  int          NOT NULL AUTO_INCREMENT,
    `member_id`   varchar(12)  NOT NULL,
    `review_id`   int          NOT NULL,
    `content`     varchar(300) NOT NULL,
    `create_date` timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`comment_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
);

CREATE TABLE `member_board_log`
(
    `member_board_log_id` int         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `member_id`           varchar(12) NOT NULL,
    `attraction_info_id`  int         NOT NULL,
    `create_date`         timestamp DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`attraction_info_id`) REFERENCES `attraction_info` (`attraction_info_id`)
);

CREATE TABLE `board`
(
    `board_id`           int NOT NULL AUTO_INCREMENT,
    `attraction_info_id` int NOT NULL,
    `location`           int NOT NULL,
    PRIMARY KEY (`board_id`),
    FOREIGN KEY (`attraction_info_id`) REFERENCES `attraction_info` (`attraction_info_id`)
);

CREATE TABLE `member_board_like`
(
    `member_board_like_id` int         NOT NULL AUTO_INCREMENT,
    `review_id`            int         NOT NULL,
    `member_id`            varchar(12) NOT NULL,
    PRIMARY KEY (`member_board_like_id`),
    FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
);

CREATE TABLE `score`
(
    `score_id`  int         NOT NULL AUTO_INCREMENT,
    `sido_id`   int         NOT NULL,
    `member_id` varchar(12) NOT NULL,
    `score`     int         NULL,
    PRIMARY KEY (`score_id`),
    FOREIGN KEY (`sido_id`) REFERENCES `sido` (`sido_id`),
    FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
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
    `expiration_date` timestamp DEFAULT (CURRENT_TIMESTAMP + INTERVAL 5 MINUTE),
    PRIMARY KEY (`email`)
);

insert into sido (name)
values ('서울');
insert into attraction_info (sido_id, name, addr, url, latitude, longitude)
VALUES (1, 'test', '서울주소', 'https://seoulismuseum.kr/data/cn/246/img/2022_04_002.jpg',
        37.51799330000000000, 126.95764780000000000);

INSERT INTO `sido` (`name`)
VALUES ('서울특별시'),
       ('부산광역시'),
       ('대구광역시'),
       ('인천광역시'),
       ('광주광역시'),
       ('대전광역시'),
       ('울산광역시'),
       ('세종특별자치시'),
       ('경기도'),
       ('강원도'),
       ('충청북도'),
       ('충청남도'),
       ('전라북도'),
       ('전라남도'),
       ('경상북도'),
       ('경상남도'),
       ('제주특별자치도');

-- 서울특별시
INSERT INTO `attraction_info` (`sido_id`, `name`, `addr`, `url`, `latitude`, `longitude`, `description`)
VALUES (1, '서울 타워', '서울특별시 용산구 남산공원길 105', 'https://www.nseoul.go.kr/', 37.5511694, 126.9882296,
        '서울 타워는 서울의 대표적인 랜드마크로, 남산의 정상에 위치하고 있습니다. 전망대에서는 서울의 아름다운 전경을 감상할 수 있으며, 다양한 문화 행사와 이벤트도 진행됩니다.'),
       (1, '경복궁', '서울특별시 종로구 사직로 161', 'http://www.royalpalace.go.kr/', 37.5776792, 126.976927,
        '경복궁은 조선 시대의 왕궁 중 하나로, 서울의 역사적 중심인 종로구에 위치하고 있습니다. 궁궐 건물과 아름다운 정원은 관광객들에게 인기 있는 관광 명소입니다.'),
       (1, '남산골 한옥마을', '서울특별시 중구 퇴계로34길 28', 'https://www.hanokmaeul.or.kr/', 37.5595985, 126.9944574,
        '남산골 한옥마을은 전통 한옥이 보존되어 있는 마을로, 서울 중심부에 위치하고 있습니다. 한국 전통 가옥과 문화를 경험할 수 있는 곳입니다.'),
       (1, '서울대공원', '경기도 과천시 대공원광장로 102', 'https://www.sgcf.or.kr/', 37.4348535, 127.0094591,
        '서울대공원은 서울과 경기도 과천에 걸쳐 있는 대규모 공원으로, 다양한 시설과 자연 경관을 즐길 수 있습니다.'),
       (1, '청계천', '서울특별시 종로구 세종대로 지하 100', 'https://www.sisul.or.kr/', 37.5680713, 126.9832099,
        '청계천은 서울 시내를 가로지르는 천으로, 산책로와 다양한 문화 행사가 열리는 곳입니다.'),
       (1, '국립중앙박물관', '서울특별시 용산구 서빙고로 137', 'http://www.museum.go.kr/', 37.5244928, 126.9803883,
        '국립중앙박물관은 한국의 역사와 문화를 소개하는 국립 박물관으로, 다양한 전시물을 감상할 수 있습니다.'),
       (1, '63빌딩', '서울특별시 영등포구 63로 50', 'https://www.63.co.kr/', 37.5197668, 126.9403947,
        '63빌딩은 한강을 바라보며 서울의 전망을 즐길 수 있는 랜드마크 건물입니다.'),
       (1, '롯데월드', '서울특별시 송파구 올림픽로 300', 'http://www.lotteworld.com/', 37.5113259, 127.0994847,
        '롯데월드는 대한민국의 대표적인 테마파크로, 다양한 놀이기구와 테마존을 즐길 수 있습니다.'),
       (1, '한강시민공원', '서울특별시 마포구 양화로 154', 'https://parks.seoul.go.kr/', 37.5346193, 126.9947081,
        '한강시민공원은 한강변을 따라 조성된 공원으로, 산책로와 자전거 도로, 편의시설 등을 제공합니다.'),
       (1, '북촌한옥마을', '서울특별시 종로구 북촌로 37', 'http://hanok.seoul.go.kr/', 37.5811072, 126.9822111,
        '북촌한옥마을은 전통 가옥이 잘 보존된 마을로, 한국 전통 문화를 경험할 수 있는 곳입니다.'),
       (1, '서울랜드', '경기도 과천시 막계동 186', 'https://www.seoulland.co.kr/', 37.4295637, 127.0177484,
        '서울랜드는 가족 단위로 즐길 수 있는 대규모 테마파크로, 다양한 놀이시설과 쇼, 이벤트를 제공합니다.'),
       (1, '잠실 종합운동장', '서울특별시 송파구 올림픽로 25', 'https://www.jamsilarena.co.kr/', 37.5151132, 127.0720225,
        '잠실 종합운동장은 다양한 스포츠 경기와 공연이 열리는 다목적 경기장으로, 대규모 이벤트의 주최지입니다.'),
       (1, '한양도성', '서울특별시 성동구 왕십리도선로 110', 'http://www.hanyangdoseong.org/', 37.5568199, 127.0412793,
        '한양도성은 한양(서울)을 둘러싸고 있던 고려시대의 도성으로, 서울의 역사적인 유적지입니다.'),
       (1, '광장시장', '서울특별시 종로구 창경궁로 88', 'http://www.kwangjangmarket.co.kr/', 37.5696902, 126.9938306,
        '광장시장은 서울의 전통 시장으로, 다양한 먹거리와 물품을 쇼핑할 수 있는 곳입니다.'),
       (1, '예술의 전당', '서울특별시 서초구 남부순환로 2364', 'https://www.sac.or.kr/', 37.4785799, 127.014217,
        '예술의 전당은 다양한 공연과 전시가 열리는 문화 예술 공간으로, 서울의 대표적인 문화 시설 중 하나입니다.'),
       (1, '한국전쟁기념관', '서울특별시 용산구 이태원로 29', 'https://www.warmemo.or.kr/', 37.5368264, 126.9770163,
        '한국전쟁기념관은 한국전쟁에 관한 역사적인 자료와 유물을 전시하고 있는 기념관으로, 전쟁의 역사를 되새기는 곳입니다.'),
       (1, '경희궁', '서울특별시 종로구 창경궁로 185', 'http://www.chf.or.kr/', 37.5716843, 126.9663522,
        '경희궁은 조선시대의 왕궁 중 하나로, 서울 종로구에 위치하고 있습니다. 역사적인 건물과 아름다운 정원을 감상할 수 있습니다.'),
       (1, '선릉과 정릉', '서울특별시 강남구 헌릉로 240', 'https://www.jungnangyeok.or.kr/', 37.6013937, 127.059717,
        '선릉과 정릉은 조선시대 왕들의 묘역으로, 한국의 역사와 문화를 엿볼 수 있는 곳입니다.'),
       (1, '서울숲', '서울특별시 성동구 뚝섬로 273', 'https://www.sisul.or.kr/', 37.5449161, 127.0423029,
        '서울숲은 도심 속에 위치한 자연 공원으로, 산책로와 자전거 도로, 호수 등이 조성되어 있습니다.'),
       (1, '석촌호수', '서울특별시 송파구 석촌호수로 257', 'https://www.songpa.go.kr/', 37.5050285, 127.1062441,
        '석촌호수는 송파구에 위치한 인공 호수로, 주변에는 산책로와 휴식 공간이 조성되어 있습니다.'),
       (1, '국립중앙도서관', '서울특별시 서초구 반포대로 201', 'https://www.nl.go.kr/', 37.5008221, 127.0055894,
        '국립중앙도서관은 대한민국의 중앙 도서관으로, 다양한 도서와 전문 서비스를 제공하고 있습니다.'),
       (1, '남산서울타워 버스 정류장', '서울특별시 용산구 용산2가동 산1-3', 'https://bus.go.kr/', 37.5496205, 126.990646,
        '남산서울타워 버스 정류장은 서울의 중심지인 남산에 위치한 버스 정류장입니다. 관광객들이 쉽게 접근할 수 있도록 서비스되고 있습니다.'),
       (1, '서울숲역 버스 정류장', '서울특별시 성동구 성수이로 122', 'https://bus.go.kr/', 37.541159, 127.04197,
        '서울숲역 버스 정류장은 서울숲 근처에 위치한 버스 정류장으로, 대중교통 이용이 편리한 곳입니다.'),
       (1, '경복궁역', '서울특별시 종로구 사직로 161', 'https://bus.go.kr/', 37.5779647, 126.9766557,
        '경복궁역은 경복궁 근처에 위치한 지하철 역으로, 관광객들이 궁궐을 방문할 때 이용하는 주요 교통수단입니다.'),
       (1, '동대문 디자인 플라자', '서울특별시 중구 을지로 281', 'https://www.ddp.or.kr/', 37.5666106, 127.0095279,
        '동대문 디자인 플라자는 디자인과 문화를 즐길 수 있는 공간으로, 다양한 전시와 이벤트가 열리는 문화 예술 시설입니다.'),
       (1, '명동거리', '서울특별시 중구 명동길 25', 'https://www.myeongdong.or.kr/', 37.5634174, 126.9853967,
        '명동거리는 서울의 대표적인 상업 지역 중 하나로, 다양한 쇼핑과 먹거리를 즐길 수 있는 곳입니다.'),
       (1, '남대문시장', '서울특별시 중구 남대문로4가 30', 'https://www.namdaemunmarket.co.kr/', 37.5613447, 126.9743307,
        '남대문시장은 서울의 대표적인 전통 시장으로, 다양한 물품과 식품을 저렴하게 구매할 수 있는 곳입니다.'),
       (1, '서울 식자재시장', '서울특별시 중구 퇴계로 249', 'https://www.sijang.co.kr/', 37.5609157, 127.0075132,
        '서울 식자재시장은 식재료와 관련된 다양한 상품을 쇼핑할 수 있는 곳으로, 식당과 음식점에서 이용될 식자재를 구입할 수 있습니다.'),
       (1, '인사동', '서울특별시 종로구 인사동길 14', 'https://www.insa-dong.com/', 37.5737689, 126.9858082,
        '인사동은 전통과 문화가 공존하는 서울의 대표적인 관광 명소로, 다양한 전통 공예품과 먹거리를 즐길 수 있는 곳입니다.'),
       (1, '삼청동', '서울특별시 종로구 삼청로 111-1', 'https://www.samcheong.or.kr/', 37.585918, 126.981055,
        '삼청동은 한국 전통 가옥이 보존되어 있는 지역으로, 골목길을 거닐며 한옥 카페와 갤러리를 방문할 수 있습니다.');
