delete from movie_search;
delete from search;
delete from movies;


-- Adminer 4.7.7 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

INSERT INTO `movie_search` (`movie_id`, `search_id`) VALUES
(14512,	1),
(77394,	1),
(79817,	1),
(79900,	1),
(110902, 1),
(230260, 1),
(267352, 1),
(270561, 1),
(271836, 1),
(586397, 1),
(1008657, 1),
(1272234, 1);

INSERT INTO `movies` (`id`, `imdb_id`, `kinopoisk_id`, `imdb_rating`, `imdb_votes`, `name`, `eastern_name`, `description`, `duration`, `director`, `actors`, `language`, `country`, `metascore`, `genre`, `box_office`, `metacritic_rating`, `movie_db_rating`, `rotten_tomatoes_rating`, `tv_com_rating`, `film_affinity_rating`, `kinopoisk_rating`, `kinopoisk_votes`, `image`, `year`) VALUES
(14512,	'tt0060315',	'14512',	'7.2',	'24,995',	'Django 2 - Il grande ritorno',	'Джанго 2: Возвращение',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:28',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'5.7',	'427',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1987'),
(77394,	'tt0060315',	'77394',	'7.2',	'24,995',	'Django',	'Джанго',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:31',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'7.5',	'6339',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1966'),
(79817,	'tt0060315',	'79817',	'7.2',	'24,995',	'Django il bastardo',	'Ублюдок Джанго',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:47',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'5.9',	'201',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1969'),
(79900,	'tt0060315',	'79900',	'7.2',	'24,995',	'Pochi dollari per Django',	'Джанго, эта пуля для тебя!',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:25',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'5.7',	'90',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1966'),
(110902,	'tt0060315',	'110902',	'7.2',	'24,995',	'W Django!',	'Вива, Джанго!',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:30',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'',	'n/a',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1971'),
(230260,	'tt0060315',	'230260',	'7.2',	'24,995',	'Django spara per primo',	'Джанго стреляет первым',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:23',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'',	'n/a',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1966'),
(267352,	'tt0060315',	'267352',	'7.2',	'24,995',	'Non aspettare Django, spara',	'Не медли, Джанго... Стреляй!',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:28',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'',	'n/a',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1967'),
(270561,	'tt0060315',	'270561',	'7.2',	'24,995',	'Django',	'',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'n/a',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'',	'n/a',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'1999'),
(271836,	'tt0060315',	'271836',	'7.2',	'24,995',	'Sukiyaki Western Django',	'Сукияки Вестерн Джанго',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:36',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'6.0',	'4092',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'2007'),
(586397,	'tt0060315',	'586397',	'7.2',	'24,995',	'Django Unchained',	'Джанго освобожденный',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'2:45',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'8.2',	'455187',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'2012'),
(1008657,	'tt0060315',	'1008657',	'7.2',	'24,995',	'Django',	'Джанго',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'1:57',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'5.9',	'130',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'2017'),
(1272234,	'tt0060315',	'1272234',	'7.2',	'24,995',	'Django/Zorro',	'',	'In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?',	'n/a',	'Sergio Corbucci',	'Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez',	'Italian',	'Italy, Spain',	'75',	'Action, Western',	'$25,916',	'75/100',	'7.2/10',	'92%',	NULL,	NULL,	'97%',	'1142',	'https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg',	'2022');

INSERT INTO `search` (`id`, `name`, `number`) VALUES
(1,	'Django',	2);

-- 2021-03-09 03:33:21


