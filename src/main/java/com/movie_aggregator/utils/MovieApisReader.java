package com.movie_aggregator.utils;

import com.movie_aggregator.entity.Movie;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author mturchanov
 */
public class MovieApisReader implements PropertiesLoader {
    private Properties properties;
    public static final String KINOPOISK_ROOT = "https://kinopoiskapiunofficial.tech/api/v2.1/films/";
    public static final String OMDB_ROOT = "http://www.omdbapi.com/";

    public MovieApisReader() {
        properties = loadProperties("/api_keys.properties");
    }


    public String getJSONFromApi(String searchSource , String searchType, String searchVal) throws IOException {
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

    public List<Movie> parseJSONKinopoiskMovies(String searchVal) throws IOException {
        String JSONMovies = getJSONFromApi("general", "kinopoisk", searchVal);
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

            String movieDetails = getJSONFromApi("specific","kinopoisk", filmId);

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
                String imdbDataJSON = getJSONFromApi("specific", "omdb", imdbId);
                System.out.println(imdbDataJSON);
                //String imdbDataJSON = "{\"Title\":\"Matrix\",\"Year\":\"1993\",\"Rated\":\"N/A\",\"Released\":\"01 Mar 1993\",\"Runtime\":\"60 min\",\"Genre\":\"Action, Drama, Fantasy, Thriller\",\"Director\":\"N/A\",\"Writer\":\"Grenville Case\",\"Actors\":\"Nick Mancuso, Phillip Jarrett, Carrie-Anne Moss, John Vernon\",\"Plot\":\"Steven Matrix is one of the underworld's foremost hitmen until his luck runs out, and someone puts a contract out on him. Shot in the forehead by a .22 pistol, Matrix \\\"dies\\\" and finds ...\",\"Language\":\"English\",\"Country\":\"Canada\",\"Awards\":\"1 win.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYzUzOTA5ZTMtMTdlZS00MmQ5LWFmNjEtMjE5MTczN2RjNjE3XkEyXkFqcGdeQXVyNTc2ODIyMzY@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.9/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"7.9\",\"imdbVotes\":\"138\",\"imdbID\":\"tt0106062\",\"Type\":\"series\",\"totalSeasons\":\"N/A\",\"Response\":\"True\"}";
                movies.add(parseJSONImdbMovie(imdbDataJSON, movie));
                continue;
            }

            movies.add(movie);
        }
        return movies;
    }

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


        //System.out.println(movie);

        return movie;
    }

    public static void main(String[] args) throws IOException {
        MovieApisReader reader = new MovieApisReader();
       // List<Movie> movies = reader.parseJSONKinopoiskMovies("{\"keyword\":\"Django\",\"pagesCount\":5,\"films\":[{\"filmId\":77394,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Серджио Корбуччи(боевик)\",\"filmLength\":\"1:31\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"7.5\",\"ratingVoteCount\":6329,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/77394.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/77394.jpg\"},{\"filmId\":586397,\"nameRu\":\"Джанго освобожденный\",\"nameEn\":\"Django Unchained\",\"type\":\"UNKNOWN\",\"year\":\"2012\",\"description\":\"США, Квентин Тарантино(вестерн)\",\"filmLength\":\"2:45\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"вестерн\"},{\"genre\":\"боевик\"},{\"genre\":\"драма\"}],\"rating\":\"8.2\",\"ratingVoteCount\":454665,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/586397.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/586397.jpg\"},{\"filmId\":1008657,\"nameRu\":\"Джанго\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"2017\",\"description\":\"Франция, Этьен Комар(драма)\",\"filmLength\":\"1:57\",\"countries\":[{\"country\":\"Франция\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"военный\"},{\"genre\":\"биография\"}],\"rating\":\"5.9\",\"ratingVoteCount\":130,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1008657.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1008657.jpg\"},{\"filmId\":4367759,\"nameRu\":\"\",\"nameEn\":\"Django (сериал)\",\"type\":\"UNKNOWN\",\"year\":\"2022-...\",\"description\":\"Франция, Франческа Коменчини(драма)\",\"countries\":[{\"country\":\"Франция\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/4367759.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/4367759.jpg\"},{\"filmId\":91547,\"nameRu\":\"Кеома\",\"nameEn\":\"Keoma\",\"type\":\"UNKNOWN\",\"year\":\"1976\",\"description\":\"Италия, Энцо Дж. Кастеллари(драма)\",\"filmLength\":\"1:45\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.9\",\"ratingVoteCount\":450,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/91547.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/91547.jpg\"},{\"filmId\":110902,\"nameRu\":\"Вива, Джанго!\",\"nameEn\":\"W Django!\",\"type\":\"UNKNOWN\",\"year\":\"1971\",\"description\":\"Италия, Эдуардо Мулагрия(вестерн)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/110902.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/110902.jpg\"},{\"filmId\":79817,\"nameRu\":\"Ублюдок Джанго\",\"nameEn\":\"Django il bastardo\",\"type\":\"UNKNOWN\",\"year\":\"1969\",\"description\":\"Италия, Серджо Гарроне(вестерн)\",\"filmLength\":\"1:47\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.9\",\"ratingVoteCount\":201,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79817.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79817.jpg\"},{\"filmId\":1272234,\"nameRu\":\"\",\"nameEn\":\"Django/Zorro\",\"type\":\"UNKNOWN\",\"year\":\"2022\",\"description\":\"США(драма)\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"драма\"},{\"genre\":\"приключения\"},{\"genre\":\"вестерн\"}],\"rating\":\"97%\",\"ratingVoteCount\":1134,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1272234.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1272234.jpg\"},{\"filmId\":271836,\"nameRu\":\"Сукияки Вестерн Джанго\",\"nameEn\":\"Sukiyaki Western Django\",\"type\":\"UNKNOWN\",\"year\":\"2007\",\"description\":\"Япония, Такаси Миике(боевик)\",\"filmLength\":\"1:36\",\"countries\":[{\"country\":\"Япония\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"rating\":\"6.0\",\"ratingVoteCount\":4090,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/271836.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/271836.jpg\"},{\"filmId\":14512,\"nameRu\":\"Джанго 2: Возвращение\",\"nameEn\":\"Django 2 - Il grande ritorno\",\"type\":\"UNKNOWN\",\"year\":\"1987\",\"description\":\"Италия, Нелло Россати(вестерн)\",\"filmLength\":\"1:28\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"rating\":\"5.7\",\"ratingVoteCount\":427,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/14512.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/14512.jpg\"},{\"filmId\":152269,\"nameRu\":\"Чаманго\",\"nameEn\":\"Cjamango\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Эдуардо Мулагрия(вестерн)\",\"filmLength\":\"1:30\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/152269.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/152269.jpg\"},{\"filmId\":270561,\"nameRu\":\"\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"1999\",\"description\":\"США, Гарри Максон\",\"countries\":[{\"country\":\"США\"}],\"genres\":[],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/270561.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/270561.jpg\"},{\"filmId\":221920,\"nameRu\":\"Головорезы\",\"nameEn\":\"El desperado\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Франко Россетти(вестерн)\",\"filmLength\":\"1:43\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/221920.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/221920.jpg\"},{\"filmId\":79900,\"nameRu\":\"Джанго, эта пуля для тебя!\",\"nameEn\":\"Pochi dollari per Django\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Леон Климовский(криминал)\",\"filmLength\":\"1:25\",\"countries\":[{\"country\":\"Италия\"},{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"криминал\"},{\"genre\":\"вестерн\"}],\"rating\":\"5.7\",\"ratingVoteCount\":90,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/79900.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/79900.jpg\"},{\"filmId\":1378064,\"nameRu\":\"\",\"nameEn\":\"Django\",\"type\":\"UNKNOWN\",\"year\":\"2014\",\"description\":\"Ливан, Scarlett Saad(короткометражка)\",\"filmLength\":\"0:18\",\"countries\":[{\"country\":\"Ливан\"}],\"genres\":[{\"genre\":\"короткометражка\"},{\"genre\":\"мюзикл\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/1378064.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/1378064.jpg\"},{\"filmId\":230260,\"nameRu\":\"Джанго стреляет первым\",\"nameEn\":\"Django spara per primo\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Италия, Альберто Де Мартино(мелодрама)\",\"filmLength\":\"1:23\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"мелодрама\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/230260.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/230260.jpg\"},{\"filmId\":267352,\"nameRu\":\"Не медли, Джанго... Стреляй!\",\"nameEn\":\"Non aspettare Django, spara\",\"type\":\"UNKNOWN\",\"year\":\"1967\",\"description\":\"Италия, Эдуардо Мулагрия(боевик)\",\"filmLength\":\"1:28\",\"countries\":[{\"country\":\"Италия\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/267352.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/267352.jpg\"},{\"filmId\":115100,\"nameRu\":\"Метис\",\"nameEn\":\"Mestizo\",\"type\":\"UNKNOWN\",\"year\":\"1966\",\"description\":\"Испания, Хулио Бучс(вестерн)\",\"filmLength\":\"1:35\",\"countries\":[{\"country\":\"Испания\"}],\"genres\":[{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/115100.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/115100.jpg\"},{\"filmId\":448711,\"nameRu\":\"Кид Монтана\",\"nameEn\":\"Gunless\",\"type\":\"UNKNOWN\",\"year\":\"2010\",\"description\":\"Канада, Уильям Филлипс(боевик)\",\"filmLength\":\"1:29\",\"countries\":[{\"country\":\"Канада\"}],\"genres\":[{\"genre\":\"боевик\"},{\"genre\":\"драма\"},{\"genre\":\"комедия\"}],\"rating\":\"6.7\",\"ratingVoteCount\":981,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/448711.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/448711.jpg\"},{\"filmId\":740489,\"nameRu\":\"\",\"nameEn\":\"Scenes from Django Unchained - UK Winner\",\"type\":\"UNKNOWN\",\"year\":\"2013\",\"description\":\"Великобритания, Тоби Робертс(короткометражка)\",\"filmLength\":\"0:05\",\"countries\":[{\"country\":\"Великобритания\"}],\"genres\":[{\"genre\":\"короткометражка\"},{\"genre\":\"вестерн\"}],\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/740489.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/740489.jpg\"}],\"searchFilmsCountResult\":100}", "Django");

        List<Movie> movies = reader.parseJSONKinopoiskMovies("Django");
        for (Movie m : movies) {
            System.out.println(m);
        }
    }
}


