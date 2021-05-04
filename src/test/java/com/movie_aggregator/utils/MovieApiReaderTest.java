package com.movie_aggregator.utils;

import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.service.GenericService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mturchanov
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MyConfig.class, MyWebInitializer.class})
public class MovieApiReaderTest extends AbstractTest {
    @Autowired
    MovieApisReader reader;
    @Autowired
    GenericService service;

    private final Logger logger = LogManager.getLogger(this.getClass());
    @Test
    void getJSONFromApiKinopoiskGeneralTest() {
        String kinopoiskResponse = reader.getJSONFromApi("general", "kinopoisk", "django", null);
        String expectedResposne = "{\"keyword\":\"django\",\"pagesCount\":3,\"films\":[{\"filmId\":586397,\"nameRu\":\"Джанго освобожденный\",\"nameEn\":\"Django Unchained\",\"type\":\"UNKNOWN\",\"year\":\"2012\",\"description\":\"США, Квентин Тарантино(вестерн)\",\"filmLength\":\"2:45\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"вестерн\"},{\"genre\":\"боевик\"},{\"genre\":\"драма\"}],\"rating\":\"8.2\",\"ratingVoteCount\":462368,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/586397.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/586397.jpg\"},{\"filmId\":77394,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Серджио Корбуччи(боевик)\",\"filmLength\":\"1:31\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"7.5\",\"ratingVoteCount\":6429,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/77394.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg\"},{\"filmId\":1008657,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"2017\",\"description\":\"Франция, Этьен Комар(драма)\",\"filmLength\":\"1:57\",\"countries\":[{\"country\":\"Франция\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"военный\"},{\"genre\":\"биография\"}],\"rating\":\"6.0\",\"ratingVoteCount\":130,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1008657.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1008657.jpg\"},{\"filmId\":1272234,\"nameRu\":\"\",\"nameEn\":\"Django/Zorro\",\"type\":\"UNKNOWN\",\"year\":\"2022\",\"description\":\"США(драма)\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"приключения\"},{\"genre\":\"вестерн\"}],\"rating\":\"97%\",\"ratingVoteCount\":1268,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1272234.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1272234.jpg\"},{\"filmId\":14512,\"nameRu\":\"Джанго 2: Возвращение\",\"nameEn\":\"Django 2 - Il grande ritorno\",\"type\":\"UNKNOWN\",\"year\":\"1987\",\"description\":\"Италия, Нелло Россати(вестерн)\",\"filmLength\":\"1:28\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.7\",\"ratingVoteCount\":436,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/14512.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/14512.jpg\"},{\"filmId\":4367759,\"nameRu\":\"\",\"nameEn\":\"Django (сериал)\",\"type\":\"UNKNOWN\",\"year\":\"2022-...\",\"description\":\"Франция, Франческа Коменчини(драма)\",\"countries\":[{\"country\":\"Франция\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/4367759.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/4367759.jpg\"},{\"filmId\":271836,\"nameRu\":\"Сукияки Вестерн Джанго\",\"nameEn\":\"Sukiyaki Western Django\",\"type\":\"UNKNOWN\",\"year\":\"2007\",\"description\":\"Япония, Такаси Миике(боевик)\",\"filmLength\":\"1:36\",\"countries\":[{\"country\":\"Япония\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.0\",\"ratingVoteCount\":4119,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/271836.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/271836.jpg\"},{\"filmId\":91547,\"nameRu\":\"Кеома\",\"nameEn\":\"Keoma\",\"type\":\"UNKNOWN\",\"year\":\"1976\",\"description\":\"Италия, Энцо Дж. Кастеллари(драма)\",\"filmLength\":\"1:45\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.9\",\"ratingVoteCount\":464,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/91547.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/91547.jpg\"},{\"filmId\":807358,\"nameRu\":\"Джанго жив!\",\"nameEn\":\"Django Lives!\",\"type\":\"UNKNOWN\",\"description\":\"США, Кристиан Альварт(боевик)\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"драма\"},{\"genre\":\"криминал\"}],\"rating\":\"92%\",\"ratingVoteCount\":196,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/807358.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/807358.jpg\"},{\"filmId\":230260,\"nameRu\":\"Джанго стреляет первым\",\"nameEn\":\"Django spara per primo\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Альберто Де Мартино(мелодрама)\",\"filmLength\":\"1:23\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"мелодрама\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/230260.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/230260.jpg\"},{\"filmId\":79817,\"nameRu\":\"Ублюдок Джанго\",\"nameEn\":\"Django il bastardo\",\"type\":\"UNKNOWN\",\"year\":\"1969\",\"description\":\"Италия, Серджо Гарроне(вестерн)\",\"filmLength\":\"1:47\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.9\",\"ratingVoteCount\":207,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79817.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79817.jpg\"},{\"filmId\":79900,\"nameRu\":\"Джанго, эта пуля для тебя!\",\"nameEn\":\"Pochi dollari per Django\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Леон Климовский(криминал)\",\"filmLength\":\"1:25\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"криминал\"},{\"genre\":\"вестерн\"}],\"rating\":\"5.7\",\"ratingVoteCount\":93,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79900.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79900.jpg\"},{\"filmId\":188964,\"nameRu\":\"Приготовь гроб!\",\"nameEn\":\"Preparati la bara!\",\"type\":\"UNKNOWN\",\"year\":\"1968\",\"description\":\"Италия, Фердинандо Бальди(боевик)\",\"filmLength\":\"1:32\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.0\",\"ratingVoteCount\":179,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/188964.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/188964.jpg\"},{\"filmId\":183067,\"nameRu\":\"Джанго: Бог простит. Я – нет!\",\"nameEn\":\"Dio perdona... Io no!\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Джузеппе Колицци(вестерн)\",\"filmLength\":\"1:49\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"6.6\",\"ratingVoteCount\":536,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/183067.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/183067.jpg\"},{\"filmId\":94642,\"nameRu\":\"Джанго, прощай!\",\"nameEn\":\"Texas, addio\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Фердинандо Бальди(боевик)\",\"filmLength\":\"1:33\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.3\",\"ratingVoteCount\":299,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/94642.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/94642.jpg\"},{\"filmId\":110902,\"nameRu\":\"Вива, Джанго!\",\"nameEn\":\"W Django!\",\"type\":\"UNKNOWN\",\"year\":\"1971\",\"description\":\"Италия, Эдуардо Мулагрия(вестерн)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/110902.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/110902.jpg\"},{\"filmId\":448711,\"nameRu\":\"Кид Монтана\",\"nameEn\":\"Gunless\",\"type\":\"UNKNOWN\",\"year\":\"2010\",\"description\":\"Канада, Уильям Филлипс(боевик)\",\"filmLength\":\"1:29\",\"countries\":[{\"country\":\"Канада\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"драма\"},{\"genre\":\"комедия\"}],\"rating\":\"6.7\",\"ratingVoteCount\":990,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/448711.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/448711.jpg\"},{\"filmId\":79578,\"nameRu\":\"Джанго, стреляй...\",\"nameEn\":\"Se sei vivo spara\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Джулио Квести(ужасы)\",\"filmLength\":\"1:40\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"ужасы\"},{\"genre\":\"вестерн\"}],\"rating\":\"5.8\",\"ratingVoteCount\":263,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79578.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79578.jpg\"},{\"filmId\":313049,\"nameRu\":\"Один проклятый день в аду... Джанго встречает Сартану\",\"nameEn\":\"Quel maledetto giorno d'inverno... Django e Sartana all'ultimo sangue\",\"type\":\"UNKNOWN\",\"year\":\"1970\",\"description\":\"Италия, Демофилио Фидани(вестерн)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/313049.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/313049.jpg\"},{\"filmId\":94215,\"nameRu\":\"Доллар за мертвеца (ТВ)\",\"nameEn\":\"Dollar for the Dead\",\"type\":\"UNKNOWN\",\"year\":\"1998\",\"description\":\"Испания, Джин Квинтано(вестерн)\",\"filmLength\":\"1:34\",\"countries\":[{\"country\":\"Испания\"},{\"country\":\"США\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.9\",\"ratingVoteCount\":185,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/94215.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/94215.jpg\"}],\"searchFilmsCountResult\":57}";
        assertEquals(expectedResposne, kinopoiskResponse);
    }
    @Test
    void getJSONFromApiKinopoiskSpecificTest() {
        Movie movie = service.get(Movie.class, -1023705264);
        String kinopoiskSpecificResponse = reader.getJSONFromApi("specific", "kinopoisk", movie.getKinopoiskId(), null);
        String expectedResposne = "{\"data\":{\"filmId\":402614,\"nameRu\":\"Неоспоримый 3\",\"nameEn\":\"Undisputed III: Redemption\",\"webUrl\":\"http://www.kinopoisk.ru/film/402614/\",\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/402614.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/402614.jpg\",\"year\":\"2010\",\"filmLength\":\"1:36\",\"slogan\":\"One Deadly Tournament. A Last Fight For Freedom.\",\"description\":\"Юрий Бойка - российский чемпион подпольных боев без правил, постепенно оправляющийся от полученной в последнем поединке травмы, решает вернуться на ринг. Победив в бою нынешнего чемпиона тюрьмы «Чёрные Холмы», его босс - Гага - решает отправить его на международный чемпионат тюремных поединков. Поврежденное колено дает о себе знать в каждом бою и беспокоит Бойку, но он готов пройти до конца, чтобы сразиться с колумбийским бойцом и доказать, что он неоспоримый.\",\"type\":\"FILM\",\"ratingMpaa\":\"R\",\"ratingAgeLimits\":16,\"premiereRu\":null,\"distributors\":null,\"premiereWorld\":\"2010-04-17\",\"premiereDigital\":null,\"premiereWorldCountry\":\"США\",\"premiereDvd\":\"2010-11-18\",\"premiereBluRay\":null,\"distributorRelease\":\"Новый Диск\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"драма\"},{\"genre\":\"криминал\"}],\"facts\":[\"Каждый боец применяет определённый стиль. Турбо - боксёр, но при этом использует элементы джит кун-до. Родриго Сильва применяет бразильское боевое искусство «капоэйра», Джерри Лам - тхэквондо, а Бойка и другие пользуются различными видами смешанных боевых искусств.\"],\"seasons\":[]},\"externalId\":{\"imdbId\":\"tt1156466\"},\"rating\":{\"rating\":7.6,\"ratingVoteCount\":38470,\"ratingImdb\":7.4,\"ratingImdbVoteCount\":39440,\"ratingFilmCritics\":null,\"ratingFilmCriticsVoteCount\":0,\"ratingAwait\":\"89%\",\"ratingAwaitCount\":840,\"ratingRfCritics\":null,\"ratingRfCriticsVoteCount\":0},\"budget\":{\"grossRu\":0,\"grossUsa\":0,\"grossWorld\":282548,\"budget\":\"$3 000 000\",\"marketing\":0},\"review\":{\"reviewsCount\":54,\"ratingGoodReview\":\"94%\",\"ratingGoodReviewVoteCount\":50}}";
        assertEquals(expectedResposne, kinopoiskSpecificResponse);
    }

    @Test
    void getJSONFromApiImdbGeneralTest() {
        String imdbGeneralResponse = reader.getJSONFromApi("general", "omdb", "django", null);
        System.out.println(imdbGeneralResponse);
        String expectedResposne = "{\"Search\":[{\"Title\":\"Django Unchained\",\"Year\":\"2012\",\"imdbID\":\"tt1853728\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1_SX300.jpg\"},{\"Title\":\"Django\",\"Year\":\"1966\",\"imdbID\":\"tt0060315\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTA4M2NmZTgtOGJlOS00NDExLWE4MzItNWQxNTRmYzIzYmM0L2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\"},{\"Title\":\"Sukiyaki Western Django\",\"Year\":\"2007\",\"imdbID\":\"tt0906665\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNGIxY2E2ZjQtNGZjZi00MzFlLThhMzAtNzNmYWQwZTRlNDcxXkEyXkFqcGdeQXVyNzc5MjA3OA@@._V1_SX300.jpg\"},{\"Title\":\"Django Kill... If You Live, Shoot!\",\"Year\":\"1967\",\"imdbID\":\"tt0062082\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BOTczMzY4NzItMTI3ZC00ZTFiLTliYWQtY2UyYjIyMTVhMDg2XkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\"},{\"Title\":\"Django, Prepare a Coffin\",\"Year\":\"1968\",\"imdbID\":\"tt0062151\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYzgwMjRhMjctM2YyOS00MDRhLWJiMDgtMDVmMmNkNmEwZGQxL2ltYWdlXkEyXkFqcGdeQXVyMjU5OTg5NDc@._V1_SX300.jpg\"},{\"Title\":\"Django\",\"Year\":\"2017\",\"imdbID\":\"tt6247936\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BN2U4YjAxNWUtNDg3Yy00ZmZiLThkMGItODk0MDM3Y2RhYzNlXkEyXkFqcGdeQXVyMjQ3NzUxOTM@._V1_SX300.jpg\"},{\"Title\":\"Django Strikes Again\",\"Year\":\"1987\",\"imdbID\":\"tt0093113\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYjBkODcxMWYtYzcxMi00YTA1LTlkNGUtZTI0NzQyZTgxMGRiXkEyXkFqcGdeQXVyMjU5OTg5NDc@._V1_SX300.jpg\"},{\"Title\":\"Django the Bastard\",\"Year\":\"1969\",\"imdbID\":\"tt0064240\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BZDM0N2U5NDgtOGNhYS00ZmE2LWI0NTYtZjdmNWI2MGRjYTA0L2ltYWdlXkEyXkFqcGdeQXVyMjU5OTg5NDc@._V1_SX300.jpg\"},{\"Title\":\"Viva! Django\",\"Year\":\"1971\",\"imdbID\":\"tt0069479\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTY1MDI3MTc3N15BMl5BanBnXkFtZTcwOTc0MTA0MQ@@._V1_SX300.jpg\"},{\"Title\":\"A Few Dollars for Django\",\"Year\":\"1966\",\"imdbID\":\"tt0060084\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTdmN2Y5M2QtYTEwZC00OWEzLWFjZmQtMDk1ZjZmZGVhMmY1XkEyXkFqcGdeQXVyMjU4NzU2OTA@._V1_SX300.jpg\"}],\"totalResults\":\"55\",\"Response\":\"True\"}";
        assertEquals(expectedResposne, imdbGeneralResponse);
    }

    @Test
    void getJSONFromApiImdbSpecificTest() {
        Movie movie = service.get(Movie.class, -597990361);
        String imdbSpecificResponse = reader.getJSONFromApi("specific", "omdb", movie.getImdbId(), null);
        String expectedResposne = "{\"Title\":\"Stargate: Continuum\",\"Year\":\"2008\",\"Rated\":\"Not Rated\",\"Released\":\"29 Jul 2008\",\"Runtime\":\"98 min\",\"Genre\":\"Action, Adventure, Drama, Fantasy, Sci-Fi\",\"Director\":\"Martin Wood\",\"Writer\":\"Brad Wright\",\"Actors\":\"Ben Browder, Amanda Tapping, Christopher Judge, Michael Shanks\",\"Plot\":\"When the Stargate team goes to see Ba'al, the last of the System Lords, being extracted from his host. All of a sudden, Tealc, Vala and all of their allies start to vanish. Later Carter, Daniel, and Mitchell try to escape through the Stargate but find themselves not on earth but on a ship trapped beneath the polar ice cap. They learn they are on the freighter that was delivering the Stargate found in Egypt in 1939 to America. The ship is about to sink and they evacuate. They are picked up by a submarine and brought to a Naval Base where they learn the SG project never happened. They try to warn the government that the Gouald might attack earth. But the government doesn't believe and tells them that they're being released and given new identities and not to talk to each other or about their previous alternate timeline . One year later, the Gouald attack and the government asks for their help.\",\"Language\":\"English\",\"Country\":\"Canada, USA\",\"Awards\":\"3 wins & 10 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMjE4MTc1NTYyNF5BMl5BanBnXkFtZTcwMjExNDI3NA@@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.5/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"79%\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"7.5\",\"imdbVotes\":\"23,367\",\"imdbID\":\"tt0929629\",\"Type\":\"movie\",\"DVD\":\"27 Aug 2015\",\"BoxOffice\":\"N/A\",\"Production\":\"Metro-Goldwyn-Mayer\",\"Website\":\"N/A\",\"Response\":\"True\"}";
        assertEquals(expectedResposne, imdbSpecificResponse);
    }

    @Test
    void getJSONFromApiFrames() {
        Movie movie = service.get(Movie.class, -597990361);
        String framesJSON = reader.getJSONFromApi("frames", "kinopoisk", movie.getKinopoiskId(), null);
        String expectedResposne = "{\"frames\":[{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/00c91e5d-7f25-416a-9fd8-de48870e69d2/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/00c91e5d-7f25-416a-9fd8-de48870e69d2/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/99d31e28-5ea7-4834-a938-99a1b192781f/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/99d31e28-5ea7-4834-a938-99a1b192781f/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/11969cdd-150d-458c-a63a-47ed1f1f9776/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/11969cdd-150d-458c-a63a-47ed1f1f9776/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/478b310a-aad1-4bc4-b389-3c7e68c10ced/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/478b310a-aad1-4bc4-b389-3c7e68c10ced/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/df47fe9d-2742-40f4-ad57-fc976956398b/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/df47fe9d-2742-40f4-ad57-fc976956398b/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/db514335-d361-41ae-8bfb-244042925683/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/db514335-d361-41ae-8bfb-244042925683/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/b39c0390-4db1-4c17-9f67-5e0412bf285b/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/b39c0390-4db1-4c17-9f67-5e0412bf285b/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/4ac12f58-eb54-488a-a7f6-fbdc68d4582b/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/4ac12f58-eb54-488a-a7f6-fbdc68d4582b/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/089527ab-3f9f-4566-95a8-f40a48e28969/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/089527ab-3f9f-4566-95a8-f40a48e28969/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1898899/059d5d9d-1975-4786-a5ae-bca40345b917/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1898899/059d5d9d-1975-4786-a5ae-bca40345b917/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/2683f8e2-4525-4157-8ffe-2e89d7b7cfa2/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/2683f8e2-4525-4157-8ffe-2e89d7b7cfa2/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/92870ab6-fa32-4c21-906a-d464ee06a9c3/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/92870ab6-fa32-4c21-906a-d464ee06a9c3/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/064547a1-a792-41f4-ae1d-e0402cfa60e0/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/064547a1-a792-41f4-ae1d-e0402cfa60e0/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/2e08988e-05bf-4b74-b865-336df9108c3c/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/2e08988e-05bf-4b74-b865-336df9108c3c/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/391745a0-b78b-4dd8-8fc0-fa593f00f48f/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/391745a0-b78b-4dd8-8fc0-fa593f00f48f/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/a984c82d-2bda-4ba8-a585-aca3180e0067/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1629390/a984c82d-2bda-4ba8-a585-aca3180e0067/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/938f762e-e160-4dae-a55c-d6ffc8b34859/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/938f762e-e160-4dae-a55c-d6ffc8b34859/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/d7695487-5334-4c94-89c6-8e4b6742b461/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/d7695487-5334-4c94-89c6-8e4b6742b461/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/f00117ca-61a0-4dc5-abb2-f877c3767d9c/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1773646/f00117ca-61a0-4dc5-abb2-f877c3767d9c/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1704946/1c824bdb-562d-4469-8fb5-5f3fe5babbbf/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1704946/1c824bdb-562d-4469-8fb5-5f3fe5babbbf/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/cb580a22-da9a-49f0-922b-f38de3c03491/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1900788/cb580a22-da9a-49f0-922b-f38de3c03491/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/8df6968f-593c-404c-b762-62ae06b73ec6/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1946459/8df6968f-593c-404c-b762-62ae06b73ec6/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/9fd74f26-e080-4c3c-9e78-ae6f8f9bf44d/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/9fd74f26-e080-4c3c-9e78-ae6f8f9bf44d/360\"},{\"image\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/74a22aa4-da95-420c-bf9f-a786540390d1/1680x1680\",\"preview\":\"https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/74a22aa4-da95-420c-bf9f-a786540390d1/360\"}]}";
        assertEquals(expectedResposne, framesJSON);
    }

    @Test
    void getJSONFromApiWikiMediaSparql() {
        Movie movie = service.get(Movie.class, -597990361);
        String sparqlResponse = reader.getJSONFromApi(null, "sparql", "null", movie);
        sparqlResponse = sparqlResponse.replaceAll("\\s|\\n", "");
        String expectedResposne = "{ \"head\" : { \"vars\" : [ \"film\", \"film_web_id_pl\", \"film_web_name_pl\", \"all_cinema_jp\", \"allocine_fr\", \"cine_gr\", \"cinema_de\", \"common_sense\", \"eiga_jp\", \"film_affinity\", \"filmfront_no\", \"film_tv_it\", \"google_play_tv\", \"kinenote_jp\", \"kvikmyndir_is\", \"ldif_de\", \"letterbox\", \"metacritic\", \"mrqe\", \"movie_walker_jp\", \"moviemeter_nl\", \"movies_anywhere\", \"mubi\", \"mymovies_it\", \"netflix\", \"port_hu\", \"quora_topic\", \"rotten_tomatoes\", \"scope_dk\", \"sratim_il\", \"tmdb\", \"tv_com\", \"anidb\", \"anime_news_newtwork\", \"anime_click\", \"imfdb\", \"mal\", \"trakt_tv\", \"anilist\", \"kinopoisk\", \"kookooWHERE\" ] }, \"results\" : { \"bindings\" : [ { \"film\" : { \"type\" : \"uri\", \"value\" : \"http://www.wikidata.org/entity/Q735401\" }, \"all_cinema_jp\" : { \"type\" : \"literal\", \"value\" : \"331037\" }, \"allocine_fr\" : { \"type\" : \"literal\", \"value\" : \"135708\" }, \"film_affinity\" : { \"type\" : \"literal\", \"value\" : \"923497\" }, \"ldif_de\" : { \"type\" : \"literal\", \"value\" : \"531607\" }, \"netflix\" : { \"type\" : \"literal\", \"value\" : \"70099788\" }, \"mymovies_it\" : { \"type\" : \"literal\", \"value\" : \"61362\" }, \"mubi\" : { \"type\" : \"literal\", \"value\" : \"76590\" }, \"moviemeter_nl\" : { \"type\" : \"literal\", \"value\" : \"44153\" }, \"movie_walker_jp\" : { \"type\" : \"literal\", \"value\" : \"44153\" }, \"mrqe\" : { \"type\" : \"literal\", \"value\" : \"stargate-continuum-m100049851\" }, \"letterbox\" : { \"type\" : \"literal\", \"value\" : \"stargate-continuum\" }, \"port_hu\" : { \"type\" : \"literal\", \"value\" : \"106621\" }, \"rotten_tomatoes\" : { \"type\" : \"literal\", \"value\" : \"m/stargate_continuum\" }, \"tmdb\" : { \"type\" : \"literal\", \"value\" : \"12914\" }, \"kinopoisk\" : { \"type\" : \"literal\", \"value\" : \"307419\" }, \"tv_com\" : { \"type\" : \"literal\", \"value\" : \"movies/stargate-continuum\" }, \"film_tv_it\" : { \"type\" : \"literal\", \"value\" : \"41791\" }, \"kinenote_jp\" : { \"type\" : \"literal\", \"value\" : \"44730\" }, \"film_web_id_pl\" : { \"type\" : \"literal\", \"value\" : \"400842\" } } ] } }";
        expectedResposne = expectedResposne.replaceAll("\\s|\\n", "");
        assertEquals(expectedResposne, sparqlResponse);
    }


    @Test
    void mergeObjectsTest() {
        Movie mergedMovie = new Movie("Avengers","lon-long-url", "2007" );
        Movie updateInfoMovie = new Movie();
        updateInfoMovie.setId(123456);
        updateInfoMovie.setRusName("Мстители");
        MovieApisReader.mergeObjects(mergedMovie, updateInfoMovie);
        assertEquals(mergedMovie.getId(), updateInfoMovie.getId());
        assertEquals(mergedMovie.getRusName(), updateInfoMovie.getRusName());
    }

    @Test
    void mergeListsTest() {
        Movie movie1 = new Movie(123, "Avengers","lon-long-url", "2007" , "tt124124");
        Movie movie2 = new Movie(3467, "Avengers 2","lon-long-url2", "2209" , "tt1242134");
        Movie movie3 = new Movie();
        movie3.setId(123);
        movie3.setRusName("Мстители");
        Movie movie4 = new Movie("Avengers","lon-long-url", "2007" );
        movie4.setId(3467);
        movie4.setRusName("Мстители 2");

        List<Movie> updatedMovie = Arrays.asList(movie1, movie2);
        List<Movie> movies = Arrays.asList(movie3, movie4);

        MovieApisReader.mergeLists(updatedMovie, movies);

        assertEquals(updatedMovie.get(0).getRusName(), movies.get(0).getRusName());
        assertEquals(updatedMovie.get(1).getRusName(), movies.get(1).getRusName());
    }

    @Test
    void hashCodeTest() {
        int hashCoded = MovieApisReader.hashCode("myString");
        assertEquals(765506243, hashCoded);
    }

    @Test
    void parseGeneralKinopoiskMoviesJsonTest() {
        List<Movie> movies = reader.parseGeneralKinopoiskMoviesJson("django");
        System.out.println(movies.size());
        assertEquals(13, movies.size());
    }

    @Test
    void parseGeneralImdbMoviesJsonTest() {
        List<Movie> movies = reader.parseGeneralImdbMoviesJson("django");
        System.out.println(movies.size());
        assertEquals(10, movies.size());
    }

    @Test
    void parseSpecificKinopoiskMoviesJsonTest() {
        Movie movie = service.get(Movie.class, -597990361);
        Movie updatedMovie = reader.parseSpecificKinopoiskMoviesJson(movie);
        System.out.println(updatedMovie.getKinopoiskReviews());
        assertEquals("4(3)", updatedMovie.getKinopoiskReviews());
    }

    @Test
    void parseSpecificImdbMovieJsonTest() {
        Movie movie = service.get(Movie.class, -597990361);
        Movie updatedMovie = reader.parseSpecificImdbMovieJson(movie);
        System.out.println(updatedMovie.getImdbDirector());
        assertEquals("Martin Wood", updatedMovie.getImdbDirector());
    }

    @Test
    void loadFramesTest() {
        Movie movie = service.get(Movie.class, -597990361);
        Movie updatedMovie = reader.loadFrames(movie);
        System.out.println(updatedMovie.getImages().size());
        assertEquals(24, updatedMovie.getImages().size());
    }
}
