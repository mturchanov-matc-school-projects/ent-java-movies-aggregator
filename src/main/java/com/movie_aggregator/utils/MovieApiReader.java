package com.movie_aggregator.utils;

import com.movie_aggregator.entity.Movie;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author mturchanov
 */
public class MovieApiReader implements PropertiesLoader {
    private Properties properties;
    public static final String KINOPOISK_ROOT = "https://kinopoiskapiunofficial.tech/api/v2.1/films/";
    public static final String OMDB_ROOT = "http://www.omdbapi.com/";

    public MovieApiReader() {
        properties = loadProperties("/api_keys.properties");
    }




    public String getJSONFromApi(String searchType, String searchVal) throws IOException {
        searchVal =  URLEncoder.encode(searchVal, StandardCharsets.UTF_8);
        OkHttpClient client = new OkHttpClient();
        String requestURL = null;
        Request request = null;

        switch (searchType) {
            case "omdbGeneralSearch": {
                String apiKey = properties.getProperty("omdb_api");
                requestURL = String.format("%s?s=%s&apiKey=%s", OMDB_ROOT, searchVal, apiKey);
                break;
            }
            case "omdbSpecificSearch": {
                String apiKey = properties.getProperty("omdb_api");
                requestURL = String.format("%s?i=%s&apiKey=%s&plot=full", OMDB_ROOT, searchVal, apiKey);
                break;
            }
            case "kinopoiskGeneralSearch": {
                requestURL = String.format("%s%s%s%s", KINOPOISK_ROOT, "search-by-keyword?keyword=", searchVal, "&page=1");
                break;
            }
            case "kinopoiskSpecificSearch": {
                requestURL = String.format("%s%s", KINOPOISK_ROOT, searchVal);
                break;
            }
        }

        if (searchType.contains("kinopoisk")) {
            String apiKey = properties.getProperty("kinopoisk_unofficial_api");
            request = new Request.Builder()
                    .url(requestURL)
                    .addHeader("X-API-KEY", apiKey)
                    .method("GET", null)
                    .build();
        } else if (searchType.contains("omdb")) {
            request = new Request.Builder()
                    .url(requestURL)
                    .method("GET", null)
                    .build();
        }

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return response.body().string();
    }

    public List<Movie> parseJSONOmdbMovies(String JSONMovies) throws IOException {
        ArrayList<Movie> movies = new ArrayList<>();

            JSONObject obj = new JSONObject(JSONMovies);
            String isWorking = obj.getString("Response");
            if(isWorking.equals("False")) {
                return movies;
            }
            JSONArray items = obj.getJSONArray("Search");

            for (int i = 0; i < items.length(); i++) {
                JSONObject movieJSON = items.getJSONObject(i);
                String name = movieJSON.getString("Title");
                String year = movieJSON.getString("Year");
                String imdbId = movieJSON.getString("imdbID");
                String image = movieJSON.getString("Poster");

//                System.out.printf("%s(%s) %s -- %s%n", name, year, imdbId, image);
                String movieDetailsStr = getJSONFromApi("omdbSpecificSearch",imdbId);
                JSONObject movieDetailsJSON = new JSONObject(movieDetailsStr);
//                JSONObject movieDetailsJSON = new JSONObject("{\"Title\":\"The Lord of the Rings: The Return of the King\",\"Year\":\"2003\",\"Rated\":\"PG-13\",\"Released\":\"17 Dec 2003\",\"Runtime\":\"201 min\",\"Genre\":\"Action, Adventure, Drama, Fantasy\",\"Director\":\"Peter Jackson\",\"Writer\":\"J.R.R. Tolkien (novel), Fran Walsh (screenplay), Philippa Boyens (screenplay), Peter Jackson (screenplay)\",\"Actors\":\"Noel Appleby, Ali Astin, Sean Astin, David Aston\",\"Plot\":\"The final confrontation between the forces of good and evil fighting for control of the future of Middle-earth. Hobbits: Frodo and Sam reach Mordor in their quest to destroy the \\\"one ring\\\", while Aragorn leads the forces of good against Sauron's evil army at the stone city of Minas Tirith.\",\"Language\":\"English, Quenya, Old English, Sindarin\",\"Country\":\"New Zealand, USA\",\"Awards\":\"Won 11 Oscars. Another 199 wins & 124 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtM2NhNS00NDJjLTk4NDItYTRmY2EwMWZlMTY3XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.9/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"93%\"},{\"Source\":\"Metacritic\",\"Value\":\"94/100\"}],\"Metascore\":\"94\",\"imdbRating\":\"8.9\",\"imdbVotes\":\"1,645,871\",\"imdbID\":\"tt0167260\",\"Type\":\"movie\",\"DVD\":\"N/A\",\"BoxOffice\":\"$377,845,905\",\"Production\":\"New Line Cinema, Saul Zaentz Company\",\"Website\":\"N/A\",\"Response\":\"True\"}");
//                System.out.println(movieDetaiulsString);
                String description = movieDetailsJSON.getString("Plot");
                String rating = movieDetailsJSON.getString("Rated");
                String runtime = movieDetailsJSON.getString("Runtime");
                String genre = movieDetailsJSON.getString("Genre");
                String director = movieDetailsJSON.getString("Director");
                String actors = movieDetailsJSON.getString("Actors");
                String language = movieDetailsJSON.getString("Language");
                String country = movieDetailsJSON.getString("Country");
                String metascore = movieDetailsJSON.getString("Metascore");
                String imdbRating = movieDetailsJSON.getString("imdbRating");
                String imdbVotes = movieDetailsJSON.getString("imdbVotes");
                String boxOffice = movieDetailsJSON.getString("BoxOffice");


                Movie newMovie = new Movie(
                        name, imdbId, description, imdbRating, imdbVotes, boxOffice,
                        runtime, genre, director, actors, language, country, metascore, image, year
                );

                JSONArray ratingsArrayJSON = movieDetailsJSON.getJSONArray("Ratings");
                for (int j = 0; j < ratingsArrayJSON.length(); j++) {
                    JSONObject ratingsJSON = ratingsArrayJSON.getJSONObject(j);
                    if (ratingsJSON.getString("Source").equals("Internet Movie Database")) {

                        newMovie.setTheMovieDbRating(ratingsJSON.getString("Value"));
                    } else if (ratingsJSON.getString("Source").equals("Rotten Tomatoes")) {
                        newMovie.setRottenTomatoesRating(ratingsJSON.getString("Value"));
                    } else if (ratingsJSON.getString("Source").equals("Metacritic")) {
                        newMovie.setMetacriticRating(ratingsJSON.getString("Value"));
                    }
                }
//                System.out.println(newMovie);
//                System.out.println("-----------------\n");
                movies.add(newMovie);
            }

        return movies;
    }

    public List<Movie> parseJSONKinopoiskMovies(String JSONMovies, String searchVal) throws IOException {
        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject obj = new JSONObject(JSONMovies);
        JSONArray films = obj.getJSONArray("films");

        for (int i = 0; i < films.length(); i++) {
            JSONObject movieJSON = films.getJSONObject(i);
            String filmId = String.valueOf(movieJSON.getInt("filmId"));
            String nameRu = movieJSON.getString("nameRu");
            String nameEn = movieJSON.getString("nameEn");
            String shortDesc = movieJSON.getString("description");

            if (!nameRu.contains(searchVal) || nameRu.contains("(сериал)") || shortDesc.contains("короткометражка")) {
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

            String movieDetails = getJSONFromApi("kinopoiskSpecificSearch", filmId);
            JSONObject movieDetailsJSON = new JSONObject(movieDetails);
            JSONObject movieDataJSON = movieDetailsJSON.getJSONObject("data");
            String image = movieDataJSON.getString("posterUrlPreview");
            String description = movieJSON.has("description")
                    ? movieJSON.getString("description")
                    : "n/a";
            //Movie newMovie = new Movie(filmId, nameEn, nameRu, shortDesc, duration,  description, year, kVotes, rating, image);
            //movies.add(newMovie);
        }

        return movies;
    }

    private List<Movie> mergeMovieLists(List<Movie> imdbMovieList, List<Movie> kinopoiskMovieList) {
        Map<String, Movie> map = new HashMap<>();
        for (Movie m : imdbMovieList) {
            String key = m.getName() + m.getYear();
//            System.out.println("ikey:" + key);
            map.put(key, m);
        }
        for (Movie m : kinopoiskMovieList) {
            String key = m.getName() + m.getYear();
//            System.out.println("kkey:" + key);

            if(map.containsKey(key)) {
                map.get(key).setKinopoiskId(m.getKinopoiskId());
                map.get(key).setKinopoiskRating(m.getKinopoiskRating());
                map.get(key).setEasternName(m.getEasternName());
            }
        }

        //setting a unique id for each movie based on imdb+kinoopoisk ids
        List<Movie> mergedMovies = new ArrayList<>(map.values());
        for (Movie movie : mergedMovies) {

            String strImdbId = movie.getImdbId();
            String strKinopoiskId = movie.getKinopoiskId();
            if (strImdbId != null) {
                movie.setId(Integer.parseInt(strImdbId.replaceAll("\\D", "")));
                continue;
            }
            if (strKinopoiskId != null) {
                movie.setId(Integer.parseInt(strKinopoiskId));
            }
        }
        return mergedMovies;
    }

    public List<Movie> getMovieListFromApis(String searchVal) throws IOException {
        String omdbJSON = getJSONFromApi("omdbGeneralSearch", searchVal);
        String kinopoiskJSON = getJSONFromApi("kinopoiskGeneralSearch", searchVal);
        List<Movie> imdbMovies = parseJSONOmdbMovies(omdbJSON); //limit 100 -> use only for special occasions
        List<Movie> kinopoiskMovies = parseJSONKinopoiskMovies(kinopoiskJSON, searchVal);
        if (imdbMovies.isEmpty()) {
            return kinopoiskMovies;
        }
        return mergeMovieLists(imdbMovies, kinopoiskMovies);
    }

    public static void main(String[] args) throws IOException {
        MovieApiReader reader = new MovieApiReader();
        //String json = reader.getJSONFromApi("omdbGeneralSearch", "The Lord of the Rings: The Return of the King");
//        System.out.println(reader.parseJSONOmdbMovies(json));
       // String kJson = reader.getJSONFromApi("kinopoiskGeneralSearch", "Брат");
        //System.out.println(reader.parseJSONKinopoiskMovies(kJson));



//        System.out.println(reader.getJSONFromApi("kinopoiskGeneralSearch", "Django"));
        String kinopoiskJSON = "{\"keyword\":\"The Lord of the Rings  The Return of the King\",\"pagesCount\":1,\"films\":[{\"filmId\":3498,\"nameRu\":\"Властелин колец: Возвращение Короля\",\"nameEn\":\"The Lord of the Rings: The Return of the King\",\"type\":\"UNKNOWN\",\"year\":\"2003\",\"description\":\"Новая Зеландия, Питер Джексон(фэнтези)\",\"filmLength\":\"3:21\",\"countries\":[{\"country\":\"Новая Зеландия\"},{\"country\":\"США\"}],\"genres\":[{\"genre\":\"фэнтези\"},{\"genre\":\"приключения\"},{\"genre\":\"драма\"}],\"rating\":\"8.6\",\"ratingVoteCount\":445330,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/3498.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/3498.jpg\"},{\"filmId\":581525,\"nameRu\":\"НГО: За кадром – Властелин колец: Возвращение Короля (ТВ)\",\"nameEn\":\"National Geographic: Beyond the Movie - The Lord of the Rings: Return of the King\",\"type\":\"UNKNOWN\",\"year\":\"2003\",\"description\":\"США(документальный)\",\"filmLength\":\"0:52\",\"countries\":[{\"country\":\"США\"}],\"genres\":[{\"genre\":\"документальный\"}],\"rating\":\"7.0\",\"ratingVoteCount\":214,\"posterUrl\":\"https://kinopoiskapiunofficial.tech/images/posters/kp/581525.jpg\",\"posterUrlPreview\":\"https://kinopoiskapiunofficial.tech/images/posters/kp_small/581525.jpg\"}],\"searchFilmsCountResult\":2}";
        List<Movie> kMovies = reader.parseJSONKinopoiskMovies(kinopoiskJSON, "Lord of the Rings");
        System.out.println(kMovies);
        //System.out.println(reader.getJSONFromApi("kinopoiskSpecificSearch", "233634"));
        //List<Movie> iMovies = reader.parseJSONOmdbMovies(reader.getJSONFromApi("omdbGeneralSearch", "Django"));

       // List<Movie> finalMovies = reader.mergeMovieLists(iMovies, kMovies);
        //for (Movie m : finalMovies) {
        //    System.out.println("kinopoisk id: " + m.getKinopoiskId() + "/imdb id: "
        //            + m.getImdbId() + ", id: " + m.getId());
        //}
//        System.out.println("km:" + kMovies);
//        System.out.println("im:" + iMovies);
//        System.out.println("fm:" + finalMovies);
//        System.out.println(reader.parseJSONKinopoiskMovies(kinopoiskJSON));
//        System.out.println(reader.parseJSONMovies(json));
    }
}
