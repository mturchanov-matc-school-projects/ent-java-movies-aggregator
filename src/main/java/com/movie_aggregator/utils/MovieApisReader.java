package com.movie_aggregator.utils;

import com.movie_aggregator.entity.Movie;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The type Movie apis reader.
 *
 * @author mturchanov
 */
public class MovieApisReader implements PropertiesLoader {
    private Properties properties;
    /**
     * The constant KINOPOISK_ROOT.
     */
    public static final String KINOPOISK_ROOT = "https://kinopoiskapiunofficial.tech/api/v2.1/films/";
    /**
     * The constant OMDB_ROOT.
     */
    public static final String OMDB_ROOT = "http://www.omdbapi.com/";
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Instantiates a new Movie apis reader.
     */
    public MovieApisReader() {
        properties = loadProperties("/api_keys.properties");
    }


    /**
     * Gets json from api.
     *
     * @param searchSource the search source
     * @param searchType   the search type
     * @param searchVal    the search val
     * @return the json from api
     * @throws IOException the io exception
     */
    public String getJSONFromApi(String searchSource , String searchType, String searchVal)
            throws IOException {
        searchVal =  URLEncoder.encode(searchVal, StandardCharsets.UTF_8);
        OkHttpClient client = new OkHttpClient();
        String requestURL = null;
        Request request = null;

        switch (searchType) {
            case "omdb": {
                String apiKey = properties.getProperty("omdb_api");
                requestURL = String.format("%s?i=%s&apiKey=%s&plot=full", OMDB_ROOT, searchVal, apiKey);
                request = new Request.Builder()
                        .url(requestURL)
                        .method("GET", null)
                        .build();
                break;
            }
            case "kinopoisk": {
                String apiKey = properties.getProperty("kinopoisk_unofficial_api");
                if (searchSource.equals("general")) {
                    requestURL = String.format("%s%s%s%s", KINOPOISK_ROOT, "search-by-keyword?keyword=", searchVal, "&page=1");
                } else if (searchSource.equals("specific")) {
                    requestURL = String.format("%s%s", KINOPOISK_ROOT, searchVal);
                }
                request = new Request.Builder()
                        .url(requestURL)
                        .addHeader("X-API-KEY", apiKey)
                        .method("GET", null)
                        .build();
                break;

            }
        }

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response.body().string();
    }

    /**
     * Parse json kinopoisk movies list.
     *
     * @param searchVal the search val
     * @return the list
     */
//TODO: run only kinopoisk if ASCII has russian characters
    public List<Movie> parseJSONKinopoiskMovies(String searchVal) {
        //get general movie info json data
        //String JSONMovies = getJSONFromApi("general", "kinopoisk", searchVal);
        //test
        String JSONMovies = "{\"keyword\":\"Django\",\"pagesCount\":5,\"films\":[{\"filmId\":77394,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Серджио Корбуччи(боевик)\",\"filmLength\":\"1:31\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"7.5\",\"ratingVoteCount\":6339,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/77394.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg\"},{\"filmId\":586397,\"nameRu\":\"Джанго освобожденный\",\"nameEn\":\"Django Unchained\",\"type\":\"UNKNOWN\",\"year\":\"2012\",\"description\":\"США, Квентин Тарантино(вестерн)\",\"filmLength\":\"2:45\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"вестерн\"},{\"genre\":\"боевик\"},{\"genre\":\"драма\"}],\"rating\":\"8.2\",\"ratingVoteCount\":455187,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/586397.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/586397.jpg\"},{\"filmId\":1008657,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"2017\",\"description\":\"Франция, Этьен Комар(драма)\",\"filmLength\":\"1:57\",\"countries\":[{\"country\":\"Франция\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"военный\"},{\"genre\":\"биография\"}],\"rating\":\"5.9\",\"ratingVoteCount\":130,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1008657.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1008657.jpg\"},{\"filmId\":4367759,\"nameRu\":\"\",\"nameEn\":\"Django (сериал)\",\"type\":\"UNKNOWN\",\"year\":\"2022-...\",\"description\":\"Франция, Франческа Коменчини(драма)\",\"countries\":[{\"country\":\"Франция\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/4367759.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/4367759.jpg\"},{\"filmId\":79817,\"nameRu\":\"Ублюдок Джанго\",\"nameEn\":\"Django il bastardo\",\"type\":\"UNKNOWN\",\"year\":\"1969\",\"description\":\"Италия, Серджо Гарроне(вестерн)\",\"filmLength\":\"1:47\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.9\",\"ratingVoteCount\":201,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79817.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79817.jpg\"},{\"filmId\":1272234,\"nameRu\":\"\",\"nameEn\":\"Django/Zorro\",\"type\":\"UNKNOWN\",\"year\":\"2022\",\"description\":\"США(драма)\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"приключения\"},{\"genre\":\"вестерн\"}],\"rating\":\"97%\",\"ratingVoteCount\":1142,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1272234.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1272234.jpg\"},{\"filmId\":91547,\"nameRu\":\"Кеома\",\"nameEn\":\"Keoma\",\"type\":\"UNKNOWN\",\"year\":\"1976\",\"description\":\"Италия, Энцо Дж. Кастеллари(драма)\",\"filmLength\":\"1:45\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.9\",\"ratingVoteCount\":451,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/91547.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/91547.jpg\"},{\"filmId\":271836,\"nameRu\":\"Сукияки Вестерн Джанго\",\"nameEn\":\"Sukiyaki Western Django\",\"type\":\"UNKNOWN\",\"year\":\"2007\",\"description\":\"Япония, Такаси Миике(боевик)\",\"filmLength\":\"1:36\",\"countries\":[{\"country\":\"Япония\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.0\",\"ratingVoteCount\":4092,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/271836.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/271836.jpg\"},{\"filmId\":152269,\"nameRu\":\"Чаманго\",\"nameEn\":\"Cjamango\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Эдуардо Мулагрия(вестерн)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/152269.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/152269.jpg\"},{\"filmId\":270561,\"nameRu\":\"\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"1999\",\"description\":\"США, Гарри Максон\",\"countries\":[{\"country\":\"США\"}],\"genres\":[],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/270561.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/270561.jpg\"},{\"filmId\":14512,\"nameRu\":\"Джанго 2: Возвращение\",\"nameEn\":\"Django 2 - Il grande ritorno\",\"type\":\"UNKNOWN\",\"year\":\"1987\",\"description\":\"Италия, Нелло Россати(вестерн)\",\"filmLength\":\"1:28\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.7\",\"ratingVoteCount\":427,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/14512.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/14512.jpg\"},{\"filmId\":221920,\"nameRu\":\"Головорезы\",\"nameEn\":\"El desperado\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Франко Россетти(вестерн)\",\"filmLength\":\"1:43\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/221920.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/221920.jpg\"},{\"filmId\":110902,\"nameRu\":\"Вива, Джанго!\",\"nameEn\":\"W Django!\",\"type\":\"UNKNOWN\",\"year\":\"1971\",\"description\":\"Италия, Эдуардо Мулагрия(вестерн)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/110902.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/110902.jpg\"},{\"filmId\":79900,\"nameRu\":\"Джанго, эта пуля для тебя!\",\"nameEn\":\"Pochi dollari per Django\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Леон Климовский(криминал)\",\"filmLength\":\"1:25\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"криминал\"},{\"genre\":\"вестерн\"}],\"rating\":\"5.7\",\"ratingVoteCount\":90,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79900.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79900.jpg\"},{\"filmId\":1378064,\"nameRu\":\"\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"2014\",\"description\":\"Ливан, Scarlett Saad(короткометражка)\",\"filmLength\":\"0:18\",\"countries\":[{\"country\":\"Ливан\"}],\"genres\":[{\"genre\":\"короткометражка\"},{\"genre\":\"мюзикл\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1378064.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1378064.jpg\"},{\"filmId\":230260,\"nameRu\":\"Джанго стреляет первым\",\"nameEn\":\"Django spara per primo\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Альберто Де Мартино(мелодрама)\",\"filmLength\":\"1:23\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"мелодрама\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/230260.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/230260.jpg\"},{\"filmId\":267352,\"nameRu\":\"Не медли, Джанго... Стреляй!\",\"nameEn\":\"Non aspettare Django, spara\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Эдуардо Мулагрия(боевик)\",\"filmLength\":\"1:28\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/267352.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/267352.jpg\"},{\"filmId\":115100,\"nameRu\":\"Метис\",\"nameEn\":\"Mestizo\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Испания, Хулио Бучс(вестерн)\",\"filmLength\":\"1:35\",\"countries\":[{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/115100.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/115100.jpg\"},{\"filmId\":448711,\"nameRu\":\"Кид Монтана\",\"nameEn\":\"Gunless\",\"type\":\"UNKNOWN\",\"year\":\"2010\",\"description\":\"Канада, Уильям Филлипс(боевик)\",\"filmLength\":\"1:29\",\"countries\":[{\"country\":\"Канада\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"драма\"},{\"genre\":\"комедия\"}],\"rating\":\"6.7\",\"ratingVoteCount\":981,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/448711.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/448711.jpg\"},{\"filmId\":740489,\"nameRu\":\"\",\"nameEn\":\"Scenes from Django Unchained - UK Winner\",\"type\":\"UNKNOWN\",\"year\":\"2013\",\"description\":\"Великобритания, Тоби Робертс(короткометражка)\",\"filmLength\":\"0:05\",\"countries\":[{\"country\":\"Великобритания\"}],\"genres\":[{\"genre\":\"короткометражка\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/740489.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/740489.jpg\"}],\"searchFilmsCountResult\":100}";
        //String JSONMovies = "{\"keyword\":\"test\",\"pagesCount\":68,\"films\":[{\"filmId\":709570,\"nameRu\":\"Тест\",\"nameEn\":\"Test\",\"type\":\"UNKNOWN\",\"year\":\"2013\",\"description\":\"США, Кристофер Мэйсон Джонсон(драма)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"}],\"rating\":\"6.5\",\"ratingVoteCount\":313,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/709570.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/709570.jpg\"},{\"filmId\":5446,\"nameRu\":\"Завещание\",\"nameEn\":\"Testament\",\"type\":\"UNKNOWN\",\"year\":\"1983\",\"description\":\"США, Линн Литман(драма)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"}],\"rating\":\"5.8\",\"ratingVoteCount\":693,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/5446.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/5446.jpg\"},{\"filmId\":437979,\"nameRu\":\"Подопытные (сериал)\",\"nameEn\":\"Testees\",\"type\":\"UNKNOWN\",\"year\":\"2008-2008\",\"description\":\"США, Самир Реэм(комедия)\",\"filmLength\":\"0:22\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"комедия\"}],\"rating\":\"7.0\",\"ratingVoteCount\":835,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/437979.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/437979.jpg\"},{\"filmId\":1073042,\"nameRu\":\"\",\"nameEn\":\"Test\",\"type\":\"UNKNOWN\",\"year\":\"2019\",\"description\":\"США, Умашанкар(драма)\",\"filmLength\":\"1:46\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1073042.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1073042.jpg\"},{\"filmId\":840470,\"nameRu\":\"Новейший завет\",\"nameEn\":\"Le tout nouveau testament\",\"type\":\"UNKNOWN\",\"year\":\"2015\",\"description\":\"Бельгия, Жако ван Дормель(фэнтези)\",\"filmLength\":\"1:54\",\"countries\":[{\"country\":\"Бельгия\"},{\"country\":\"Франция\"},{\"country\":\"Люксембург\"}],\"genres\":[{\"genre\":\"фэнтези\"},{\"genre\":\"комедия\"}],\"rating\":\"6.9\",\"ratingVoteCount\":32225,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/840470.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/840470.jpg\"},{\"filmId\":77600,\"nameRu\":\"Свидетельство\",\"nameEn\":\"Testimony\",\"type\":\"UNKNOWN\",\"year\":\"1987\",\"description\":\"Нидерланды, Тони Палмер(драма)\",\"filmLength\":\"2:37\",\"countries\":[{\"country\":\"Нидерланды\"},{\"country\":\"Великобритания\"},{\"country\":\"Дания\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"биография\"},{\"genre\":\"история\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/77600.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/77600.jpg\"},{\"filmId\":1045615,\"nameRu\":\"Тест на беременность 2 (сериал)\",\"nameEn\":\"\",\"type\":\"UNKNOWN\",\"year\":\"2019-...\",\"description\":\"Россия, Клим Шипенко(мелодрама)\",\"filmLength\":\"0:48\",\"countries\":[{\"country\":\"Россия\"}],\"genres\":[{\"genre\":\"мелодрама\"}],\"rating\":\"7.3\",\"ratingVoteCount\":7410,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1045615.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1045615.jpg\"},{\"filmId\":47379,\"nameRu\":\"Тестостерон\",\"nameEn\":\"Testosterone\",\"type\":\"UNKNOWN\",\"year\":\"2003\",\"description\":\"Аргентина, Дэвид Моретоун(драма)\",\"filmLength\":\"1:45\",\"countries\":[{\"country\":\"Аргентина\"},{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"мелодрама\"},{\"genre\":\"комедия\"}],\"rating\":\"6.2\",\"ratingVoteCount\":89,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/47379.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/47379.jpg\"},{\"filmId\":682688,\"nameRu\":\"Воспоминания о будущем\",\"nameEn\":\"Testament of Youth\",\"type\":\"UNKNOWN\",\"year\":\"2014\",\"description\":\"Великобритания, Джеймс Кент(драма)\",\"filmLength\":\"2:09\",\"countries\":[{\"country\":\"Великобритания\"},{\"country\":\"Дания\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"военный\"},{\"genre\":\"биография\"}],\"rating\":\"7.6\",\"ratingVoteCount\":20586,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/682688.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/682688.jpg\"},{\"filmId\":973455,\"nameRu\":\"О теле и душе\",\"nameEn\":\"Testről és Lélekről\",\"type\":\"UNKNOWN\",\"year\":\"2017\",\"description\":\"Венгрия, Ильдико Эньеди(фэнтези)\",\"filmLength\":\"1:56\",\"countries\":[{\"country\":\"Венгрия\"}],\"genres\":[{\"genre\":\"фэнтези\"},{\"genre\":\"драма\"},{\"genre\":\"мелодрама\"}],\"rating\":\"7.2\",\"ratingVoteCount\":10125,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/973455.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/973455.jpg\"},{\"filmId\":112870,\"nameRu\":\"Тестостерон\",\"nameEn\":\"Testosteroni\",\"type\":\"UNKNOWN\",\"year\":\"2004\",\"description\":\"Греция, Йоргос Панусопулос(комедия)\",\"filmLength\":\"1:32\",\"countries\":[{\"country\":\"Греция\"}],\"genres\":[{\"genre\":\"комедия\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/112870.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/112870.jpg\"},{\"filmId\":1313399,\"nameRu\":\"Альфа-тест\",\"nameEn\":\"The Alpha Test\",\"type\":\"UNKNOWN\",\"year\":\"2020\",\"description\":\"США, Аарон Миртес(фантастика)\",\"filmLength\":\"1:27\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"фантастика\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1313399.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1313399.jpg\"},{\"filmId\":94824,\"nameRu\":\"Тесты для настоящих мужчин\",\"nameEn\":\"\",\"type\":\"UNKNOWN\",\"year\":\"1998\",\"description\":\"Россия, Андрей Разенков(драма)\",\"filmLength\":\"1:09\",\"countries\":[{\"country\":\"Россия\"}],\"genres\":[{\"genre\":\"драма\"}],\"rating\":\"7.4\",\"ratingVoteCount\":2530,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/94824.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/94824.jpg\"},{\"filmId\":826743,\"nameRu\":\"Тест на беременность (сериал)\",\"nameEn\":\"\",\"type\":\"UNKNOWN\",\"year\":\"2014\",\"description\":\"Россия, Михаил Вайнберг(мелодрама)\",\"filmLength\":\"0:52\",\"countries\":[{\"country\":\"Россия\"}],\"genres\":[{\"genre\":\"мелодрама\"}],\"rating\":\"8.1\",\"ratingVoteCount\":49665,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/826743.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/826743.jpg\"},{\"filmId\":1009402,\"nameRu\":\"Тесть\",\"nameEn\":\"\",\"type\":\"UNKNOWN\",\"year\":\"2015\",\"description\":\"Россия, Андрей Митёшин(короткометражка)\",\"filmLength\":\"0:19\",\"countries\":[{\"country\":\"Россия\"}],\"genres\":[{\"genre\":\"короткометражка\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1009402.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1009402.jpg\"},{\"filmId\":42234,\"nameRu\":\"Дознание пилота Пиркса\",\"nameEn\":\"Test pilota Pirxa\",\"type\":\"UNKNOWN\",\"year\":\"1978\",\"description\":\"Польша, Марек Пестрак(фантастика)\",\"filmLength\":\"1:40\",\"countries\":[{\"country\":\"Польша\"},{\"country\":\"СССР\"}],\"genres\":[{\"genre\":\"фантастика\"},{\"genre\":\"драма\"}],\"rating\":\"7.0\",\"ratingVoteCount\":2310,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/42234.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/42234.jpg\"},{\"filmId\":1311768,\"nameRu\":\"Небеременная\",\"nameEn\":\"Unpregnant\",\"type\":\"UNKNOWN\",\"year\":\"2020\",\"description\":\"США, Рэйчел Голденберг(драма)\",\"filmLength\":\"1:43\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"комедия\"}],\"rating\":\"6.5\",\"ratingVoteCount\":343,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1311768.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1311768.jpg\"},{\"filmId\":418762,\"nameRu\":\"Экзамен\",\"nameEn\":\"Exam\",\"type\":\"UNKNOWN\",\"year\":\"2009\",\"description\":\"Великобритания, Стюарт Хэзелдайн(триллер)\",\"filmLength\":\"1:41\",\"countries\":[{\"country\":\"Великобритания\"}],\"genres\":[{\"genre\":\"триллер\"},{\"genre\":\"детектив\"}],\"rating\":\"7.1\",\"ratingVoteCount\":93493,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/418762.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/418762.jpg\"},{\"filmId\":4917,\"nameRu\":\"Святые из Бундока\",\"nameEn\":\"The Boondock Saints\",\"type\":\"UNKNOWN\",\"year\":\"1999\",\"description\":\"США, Трой Даффи(боевик)\",\"filmLength\":\"1:49\",\"countries\":[{\"country\":\"США\"},{\"country\":\"Канада\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"триллер\"},{\"genre\":\"драма\"}],\"rating\":\"7.9\",\"ratingVoteCount\":114499,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/4917.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/4917.jpg\"},{\"filmId\":698047,\"nameRu\":\"Испытайте свой мозг (сериал)\",\"nameEn\":\"Test Your Brain\",\"type\":\"UNKNOWN\",\"year\":\"2011\",\"description\":\"США, Джереми Кроуэлл(документальный)\",\"filmLength\":\"0:45\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"документальный\"}],\"rating\":\"8.1\",\"ratingVoteCount\":416,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/698047.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/698047.jpg\"}],\"searchFilmsCountResult\":1354}";
        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject obj = new JSONObject(JSONMovies);
        JSONArray films = obj.getJSONArray("films");

        for (int i = 0; i < films.length(); i++) {
            JSONObject movieJSON = films.getJSONObject(i);
            String filmId = String.valueOf(movieJSON.getInt("filmId"));
            String nameRu = movieJSON.getString("nameRu");
            String nameEn = movieJSON.getString("nameEn");
            String shortDesc = movieJSON.getString("description");

            if(!nameEn.contains(searchVal) || nameEn.contains("(сериал)")
                    || shortDesc.contains("короткометражка")
                    || nameRu.contains("(ТВ)")
            ) {
                continue;
            }
            String duration = movieJSON.has("filmLength")
                    ? movieJSON.getString("filmLength")
                    : "n/a";
            String year = movieJSON.has("year")
                    ? movieJSON.getString("year")
                    : "n/a";
            String kVotes = movieJSON.has("ratingVoteCount")
                    ? String.valueOf(movieJSON.getInt("ratingVoteCount"))
                    : "n/a";

            String rating = movieJSON.has("rating")
                    ? movieJSON.getString("rating")
                    : "";

            //make requests to get json with kinopoisk movie details
            //String movieDetails = getJSONFromApi("specific","kinopoisk", filmId);

            //test
            String movieDetails = "{\"data\":{\"filmId\":77394,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"webUrl\":\"http://www.kinopoisk.ru/film/77394/\",\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/77394.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg\",\"year\":\"1966\",\"filmLength\":\"1:31\",\"slogan\":\"The movie that spawned a genre.\",\"description\":\"Как всегда, Дикий Запад наполнен бандитами и головорезами. Терроризм процветает на территории небольшого городка, пока в нем не появляется защитник слабых и угнетенных Джанго. Он появляется ниоткуда, притащив за собой на веревке гроб с «секретом». Трудна миссия по защите угнетенных, но, как всегда, выполнима, тем более, если за дело берется Джанго.\",\"type\":\"FILM\",\"ratingMpaa\":\"R\",\"ratingAgeLimits\":16,\"premiereRu\":null,\"distributors\":null,\"premiereWorld\":\"1966-04-06\",\"premiereDigital\":null,\"premiereWorldCountry\":\"Италия\",\"premiereDvd\":\"2002-09-26\",\"premiereBluRay\":null,\"distributorRelease\":\"DVD Магия\",\"countries\":[{\"country\":\"Испания\"},{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"facts\":[\"Пулемёт Джанго - фальшивка. Это гибрид между французской «митральезой де Реффи», орудием Гатлинга и пулемётом Максима с водяным охлаждением. Пулемётная лента не движется и патроны есть с обеих сторон оружия. Как минимум с другой стороны должна быть пустая лента. Лента так же размещена слишком далеко от механизма пулемёта.\",\"Японский режиссер Такаси Миике снял фильм «Сукияки Вестерн Джанго» (2007) с явной отсылкой к фильму Серджио Корбуччи. В частности, в японском фильме также присутствует гроб.\",\"В картине Квентина Тарантино «Джанго освобождённый» Франко Неро исполнил эпизодическую роль с иронической отсылкой к его роли в фильме «Джанго».\",\"В финальной сцене есть ляп: револьвер, из которого стреляет Джанго, делает семь выстрелов, немотря на то, что емкость барабана - шесть патронов.\"],\"seasons\":[]},\"externalId\":{\"imdbId\":\"tt0060315\"},\"rating\":{\"rating\":7.5,\"ratingVoteCount\":6105,\"ratingImdb\":7.2,\"ratingImdbVoteCount\":24319,\"ratingFilmCritics\":\"92%\",\"ratingFilmCriticsVoteCount\":13,\"ratingAwait\":\"‒\",\"ratingAwaitCount\":0,\"ratingRfCritics\":\"\",\"ratingRfCriticsVoteCount\":0},\"budget\":{\"grossRu\":null,\"grossUsa\":25916,\"grossWorld\":null,\"budget\":null,\"marketing\":null},\"review\":{\"reviewsCount\":19,\"ratingGoodReview\":\"92%\",\"ratingGoodReviewVoteCount\":16}}";

            JSONObject movieDetailsJSON = new JSONObject(movieDetails);
            JSONObject movieDataJSON = movieDetailsJSON.getJSONObject("data");
            String image = movieDataJSON.getString("posterUrlPreview");
            String description = !movieDataJSON.isNull("description")
                    ? movieDataJSON.getString("description")
                    : "";
            JSONObject externalIdObj = movieDetailsJSON.getJSONObject("externalId");
            String imdbId = !externalIdObj.isNull("imdbId")
                    ? externalIdObj.getString("imdbId")
                    : "null";

            Movie movie = new Movie(nameEn, nameRu,imdbId, filmId, shortDesc, duration, year, kVotes, rating, image, description);
            //JSONObject movieReview = movieDetailsJSON.getJSONObject("review");
            movie.setId(Integer.parseInt(filmId));
            if (!imdbId.equals("null")) {
                //make requests to get json with imdb movie details
                //String imdbDataJSON = getJSONFromApi("specific", "omdb", imdbId);
                //test
                String imdbDataJSON = "{\"Title\":\"Django\",\"Year\":\"1966\",\"Rated\":\"Not Rated\",\"Released\":\"01 Dec 1966\",\"Runtime\":\"91 min\",\"Genre\":\"Action, Western\",\"Director\":\"Sergio Corbucci\",\"Writer\":\"Sergio Corbucci (story), Bruno Corbucci (story), Sergio Corbucci (screenplay), Bruno Corbucci (screenplay), Franco Rossetti (screenplay in collaboration with), Piero Vivarelli (screenplay in collaboration with), Geoffrey Copleston (English version by)\",\"Actors\":\"Franco Nero, José Bódalo, Loredana Nusciak, Ángel Álvarez\",\"Plot\":\"In the opening scene a lone man walks, behind him he drags a coffin. That man is Django. He rescues a woman from bandits and, later, arrives in a town ravaged by the same bandits. The scene for confrontation is set. But why does he drag that coffin everywhere and who, or what, is in it?\",\"Language\":\"Italian\",\"Country\":\"Italy, Spain\",\"Awards\":\"N/A\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMTA4M2NmZTgtOGJlOS00NDExLWE4MzItNWQxNTRmYzIzYmM0L2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.2/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"92%\"},{\"Source\":\"Metacritic\",\"Value\":\"75/100\"}],\"Metascore\":\"75\",\"imdbRating\":\"7.2\",\"imdbVotes\":\"24,995\",\"imdbID\":\"tt0060315\",\"Type\":\"movie\",\"DVD\":\"07 Jun 2017\",\"BoxOffice\":\"$25,916\",\"Production\":\"B.R.C. Produzione S.r.l.\",\"Website\":\"N/A\",\"Response\":\"True\"}";

                //logger.info(imdbDataJSON);
                //String imdbDataJSON = "{\"Title\":\"Matrix\",\"Year\":\"1993\",\"Rated\":\"N/A\",\"Released\":\"01 Mar 1993\",\"Runtime\":\"60 min\",\"Genre\":\"Action, Drama, Fantasy, Thriller\",\"Director\":\"N/A\",\"Writer\":\"Grenville Case\",\"Actors\":\"Nick Mancuso, Phillip Jarrett, Carrie-Anne Moss, John Vernon\",\"Plot\":\"Steven Matrix is one of the underworld's foremost hitmen until his luck runs out, and someone puts a contract out on him. Shot in the forehead by a .22 pistol, Matrix \\\"dies\\\" and finds ...\",\"Language\":\"English\",\"Country\":\"Canada\",\"Awards\":\"1 win.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYzUzOTA5ZTMtMTdlZS00MmQ5LWFmNjEtMjE5MTczN2RjNjE3XkEyXkFqcGdeQXVyNTc2ODIyMzY@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.9/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"7.9\",\"imdbVotes\":\"138\",\"imdbID\":\"tt0106062\",\"Type\":\"series\",\"totalSeasons\":\"N/A\",\"Response\":\"True\"}";
                movies.add(parseJSONImdbMovie(imdbDataJSON, movie));
                continue;
            }
            logger.info("sout parseJSONKinopoiskMovies(): single movie: " + movie);
            movies.add(movie);
        }
        logger.info("sout parseJSONKinopoiskMovies: movie list: " + movies);
        return movies;
    }

    /**
     * parses imdb movie JSON
     *
     * @param imdbData
     * @param movie
     * @return
     */
    private Movie parseJSONImdbMovie(String imdbData, Movie movie) {
        JSONObject movieDetailsJSON = new JSONObject(imdbData);
//                JSONObject movieDetailsJSON = new JSONObject("{\"Title\":\"The Lord of the Rings: The Return of the King\",\"Year\":\"2003\",\"Rated\":\"PG-13\",\"Released\":\"17 Dec 2003\",\"Runtime\":\"201 min\",\"Genre\":\"Action, Adventure, Drama, Fantasy\",\"Director\":\"Peter Jackson\",\"Writer\":\"J.R.R. Tolkien (novel), Fran Walsh (screenplay), Philippa Boyens (screenplay), Peter Jackson (screenplay)\",\"Actors\":\"Noel Appleby, Ali Astin, Sean Astin, David Aston\",\"Plot\":\"The final confrontation between the forces of good and evil fighting for control of the future of Middle-earth. Hobbits: Frodo and Sam reach Mordor in their quest to destroy the \\\"one ring\\\", while Aragorn leads the forces of good against Sauron's evil army at the stone city of Minas Tirith.\",\"Language\":\"English, Quenya, Old English, Sindarin\",\"Country\":\"New Zealand, USA\",\"Awards\":\"Won 11 Oscars. Another 199 wins & 124 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.9/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"93%\"},{\"Source\":\"Metacritic\",\"Value\":\"94/100\"}],\"Metascore\":\"94\",\"imdbRating\":\"8.9\",\"imdbVotes\":\"1,645,871\",\"imdbID\":\"tt0167260\",\"Type\":\"movie\",\"DVD\":\"N/A\",\"BoxOffice\":\"$377,845,905\",\"Production\":\"New Line Cinema, Saul Zaentz Company\",\"Website\":\"N/A\",\"Response\":\"True\"}");
//                System.out.println(movieDetaiulsString);

        String isWorking = movieDetailsJSON.getString("Response");
        if(isWorking.equals("False")) {
            return movie;
        }
        String description = movieDetailsJSON.getString("Plot");
        String genre = movieDetailsJSON.getString("Genre");
        String director = movieDetailsJSON.getString("Director");
        String actors = movieDetailsJSON.getString("Actors");
        String language = movieDetailsJSON.getString("Language");
        String country = movieDetailsJSON.getString("Country");
        String metascore = movieDetailsJSON.getString("Metascore");
        String imdbRating = movieDetailsJSON.getString("imdbRating");
        String imdbVotes = movieDetailsJSON.getString("imdbVotes");
        String boxOffice = movieDetailsJSON.has("BoxOffice")
                ? movieDetailsJSON.getString("BoxOffice")
                : "n/a";

        movie.setGenre(genre);
        movie.setDescription(description);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setLanguage(language);
        movie.setCountry(country);
        movie.setMetascore(metascore);
        movie.setImdbRating(imdbRating);
        movie.setImdbVotes(imdbVotes);
        movie.setBoxOffice(boxOffice);

        JSONArray ratingsArrayJSON = movieDetailsJSON.has("Ratings")
                ? movieDetailsJSON.getJSONArray("Ratings")
                : null;

            for (int j = 0; j < ratingsArrayJSON.length(); j++) {
                JSONObject ratingsJSON = ratingsArrayJSON.getJSONObject(j);
                if (ratingsJSON.getString("Source").equals("Internet Movie Database")) {
                    movie.setTheMovieDbRating(ratingsJSON.getString("Value"));
                } else if (ratingsJSON.getString("Source").equals("Rotten Tomatoes")) {
                    movie.setRottenTomatoesRating(ratingsJSON.getString("Value"));
                } else if (ratingsJSON.getString("Source").equals("Metacritic")) {
                    movie.setMetacriticRating(ratingsJSON.getString("Value"));
                }
            }
        logger.info(movie);
        return movie;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
//dirty and rough testing
    public static void main(String[] args) throws IOException {
        MovieApisReader reader = new MovieApisReader();
        //test
        List<Movie> movies = reader.parseJSONKinopoiskMovies("Django");

        // test with requests for apis
        //List<Movie> movies = reader.parseJSONKinopoiskMovies("Django");
        for (Movie m : movies) {
            System.out.println(m);
        }
    }

}


