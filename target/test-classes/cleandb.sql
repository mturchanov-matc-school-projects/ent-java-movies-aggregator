-- Adminer 4.7.7 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
                               `username` varchar(50) NOT NULL,
                               `id` int NOT NULL AUTO_INCREMENT,
                               `authority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'ROLE_USER',
                               PRIMARY KEY (`id`),
                               KEY `ix_auth_username` (`username`,`authority`),
                               CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `authorities` (`username`, `authority`)
VALUES ('11', 'ROLE_TEST');

DROP TABLE IF EXISTS `images`;
CREATE TABLE `images` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `url` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `movie_id` int NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `movie_id` (`movie_id`),
                          CONSTRAINT `images_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `images` (`url`, `movie_id`) VALUES
('https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/bdad2d6f-ccc7-482f-87bf-1e4029ef4748/1680x1680', -1023705264);

INSERT INTO `images` (`url`, `movie_id`) VALUES
('https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/c76ed328-978a-4960-871e-69561697a805/1680x1680', -1023705264);

DROP TABLE IF EXISTS `movie_review_source`;
CREATE TABLE `movie_review_source` (
                                       `review_source_name` varchar(200) NOT NULL,
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `movie_id` int NOT NULL,
                                       `url` varchar(200) NOT NULL,
                                       PRIMARY KEY (`id`),
                                       KEY `movie_id` (`movie_id`),
                                       KEY `review_source_name` (`review_source_name`),
                                       CONSTRAINT `movie_review_source_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
                                       CONSTRAINT `movie_review_source_ibfk_2` FOREIGN KEY (`review_source_name`) REFERENCES `reviews_sources_lookup` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `movie_search`;
CREATE TABLE `movie_search` (
                                `movie_id` int NOT NULL,
                                `search_id` int NOT NULL,
                                PRIMARY KEY (`movie_id`,`search_id`),
                                KEY `movie_id` (`movie_id`),
                                KEY `search_id` (`search_id`),
                                CONSTRAINT `movie_search_ibfk_7` FOREIGN KEY (`search_id`) REFERENCES `search` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                CONSTRAINT `movie_search_ibfk_8` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `imdb_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_rating` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_votes` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `eng_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                          `rus_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_description` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `duration` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_director` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_director` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `actors` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `language` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_country` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `metascore` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_genre` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `box_office` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `metacritic_rating` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `movie_db_rating` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `rotten_tomatoes_rating` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `tv_com_rating` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `film_affinity_rating` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_rating` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_votes` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_poster` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `imdb_poster` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `year` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_reviews` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `awards` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `writer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `released` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `audience_rating` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `production` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_distibutor` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                          `kinopoisk_country` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `imdb_distributor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `kinopoisk_distributor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `kinopoisk_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          `kinopoisk_genre` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `reviews_sources_lookup`;
CREATE TABLE `reviews_sources_lookup` (
  `name` varchar(200) NOT NULL,
  `full_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `feature` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `url` varchar(200) NOT NULL,
  `icon` varchar(2002) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `search`;
CREATE TABLE `search` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `number` int DEFAULT '1',
  `kinopoisk_source` int DEFAULT '0',
  `imdb_source` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_review_source_lookup`;
CREATE TABLE `user_review_source_lookup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `review_source_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  KEY `review_source_name` (`review_source_name`),
  CONSTRAINT `user_review_source_lookup_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  CONSTRAINT `user_review_source_lookup_ibfk_2` FOREIGN KEY (`review_source_name`) REFERENCES `reviews_sources_lookup` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `search`;
CREATE TABLE `search` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(100) NOT NULL,
                          `number` int DEFAULT '1',
                          `kinopoisk_source` int DEFAULT '0',
                          `imdb_source` int DEFAULT '0',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`username`, `id`, `password`, `enabled`, `token`) VALUES
('11',	18,	'{bcrypt}$2a$10$T/vvZbepLSCyJUG.tOb7Per3W3.24Qdtn5S8jPOQM4c5oWDZkkvAW',	1,	NULL);
INSERT INTO `users` (`username`, `id`, `password`, `enabled`, `token`) VALUES
('12',	19,	'{bcrypt}$2a$10$T/vvZbepLSCyJUG.tOb7Per3W3.24Qdtn5S8jPOQM4c5oWDZkkvAW',	1,	NULL);




INSERT INTO `search` (`id`, `name`, `number`, `kinopoisk_source`, `imdb_source`) VALUES
(1,	'Redemption',	1,	1,	0),
(2,	'Stargate',	2,	1,	0),
(47,	'Pink Floyd',	1,	0,	0);

INSERT INTO `reviews_sources_lookup` (`name`, `full_name`, `feature`, `url`, `icon`, `description`) VALUES
('all_cinema_jp',	'Allcinema',	'Japan',	'https://www.allcinema.net/cinema/%s#usercomments',	'https://pbs.twimg.com/profile_images/1190137310882521090/CiI1nupk_400x400.png',	'An online database of movies released in Japan . It has been operated by Sting Ray Co., Ltd. since 2003');

INSERT INTO `movies` (`id`, `imdb_id`, `kinopoisk_id`, `imdb_rating`, `imdb_votes`, `eng_name`, `rus_name`, `imdb_description`, `duration`, `imdb_director`, `kinopoisk_director`, `actors`, `language`, `imdb_country`, `metascore`, `imdb_genre`, `box_office`, `metacritic_rating`, `movie_db_rating`, `rotten_tomatoes_rating`, `tv_com_rating`, `film_affinity_rating`, `kinopoisk_rating`, `kinopoisk_votes`, `kinopoisk_poster`, `imdb_poster`, `year`, `kinopoisk_reviews`, `awards`, `writer`, `released`, `audience_rating`, `production`, `kinopoisk_distibutor`, `kinopoisk_country`, `imdb_distributor`, `kinopoisk_distributor`, `kinopoisk_description`, `kinopoisk_genre`) VALUES
(-1023705264,	NULL,	'402614',	NULL,	NULL,	'Undisputed III: Redemption',	'Неоспоримый 3 (видео)',	NULL,	'1:36',	NULL,	'director',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'7.6',	'38452',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/402614.jpg',	NULL,	'2010',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'США',	NULL,	NULL,	NULL,	'боевик,драма,криминал'),
(-597990361,	'tt0929629',	'307419',	NULL,	NULL,	'Stargate: Continuum',	'Звездные врата: Континуум',	NULL,	'1:38',	NULL,	'director',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'7.4',	'4706',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/307419.jpg',	'https://m.media-amazon.com/images/M/MV5BMjE4MTc1NTYyNF5BMl5BanBnXkFtZTcwMjExNDI3NA@@._V1_SX300.jpg',	'2008',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'Канада,США',	NULL,	NULL,	NULL,	'фантастика,фэнтези,боевик'),
(-91925579,	'tt0084503',	'23712',	NULL,	NULL,	'Pink Floyd: The Wall',	'Стена',	NULL,	'1:39',	NULL,	' Алан Паркер',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'8.1',	'35955',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/23712.jpg',	'https://m.media-amazon.com/images/M/MV5BZDhlZTYxOTYtYTk3Ny00ZDljLTk3ZmItZTcxZWU5YTIyYmFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg',	'1982',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'Великобритания',	NULL,	NULL,	NULL,	'мюзиклдрама'),
(690018291,	'tt10863666',	NULL,	NULL,	NULL,	'Tomás Klus: Redemption',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	'https://m.media-amazon.com/images/M/MV5BNTFlZTcyMzUtMzg2MC00NjQ2LTk4ZGMtZmI3MmZkNzlhMzE2XkEyXkFqcGdeQXVyMjIxMzMyMQ@@._V1_SX300.jpg',	'2018',	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL,	NULL);


INSERT INTO `movie_search` (`movie_id`, `search_id`) VALUES
(-1023705264,	1),
(-597990361,	2),
(-91925579,	47),
(690018291,	1);

INSERT INTO `movie_review_source` (`review_source_name`, `id`, `movie_id`, `url`) VALUES
('all_cinema_jp',	534,	-1023705264,	'testUrl');

INSERT INTO `movie_review_source` (`review_source_name`, `movie_id`, `url`) VALUES
    ('all_cinema_jp',-597990361,'finalUrlToMovieSourceReviewOnSelectedMovie');