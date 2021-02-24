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
import java.util.*;


/**
 * The type Imdb api reader.
 *
 * @author mturchanov
 */
public class ImdbApiReader implements PropertiesLoader {
    //https://imdb-api.com/en/API/SearchMovie/API_KEY/NAME
    private static final String ROOT_URL = "https://imdb-api.com/en/API/";
    private String movieToSearch;
    private Properties properties;


    public ImdbApiReader() {
        properties = loadProperties("/api_keys.properties");
    }

    /**
     * Instantiates a new Imdb api reader.
     *
     * @param movieToSearch the movie to search
     */
    public ImdbApiReader(String movieToSearch) {
        this.movieToSearch = movieToSearch;
        properties = loadProperties("/api_keys.properties");
    }

    /**
     * Gets results.
     *
     * @param searchCategory the search category
     * @param movieId        the movie id
     * @return the results
     * @throws IOException the io exception
     */
    public String getResults(String searchCategory, String searchVal, String movieId) throws IOException {

        String imdbApi = properties.getProperty("imdb_api_key");
        OkHttpClient client = new OkHttpClient();
        String requestURL;
        if (movieId != null) {
            requestURL = String.format("%s%s/%s/%s", ROOT_URL, searchCategory, imdbApi, movieId);
        } else {
            requestURL = String.format("%s%s/%s/%s", ROOT_URL, searchCategory, imdbApi, movieToSearch);
        }
        System.out.println(requestURL);
        Request request = new Request.Builder()
                .url(requestURL)
                .method("GET", null)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
//        System.out.println(requestURL);
        System.out.println(response.body().string());
        return response.body().string();
    }

    public List<Movie> parseKinopoiskSearchHtml(String searchVal) throws IOException {

        String reqHtml = String.format("https://www.kinopoisk.ru/index.php?kp_query=%s", searchVal);
        Document doc = Jsoup.connect(reqHtml).get();
        Elements movies = doc.select("div.search_results div.element");
        List<Movie> moviesList = new ArrayList<>();
        for (Element e : movies) {
            Movie movie = new Movie();
            String title = e.select("div.info span.gray").text().replaceAll(",.+$", "");
            String year = e.select("p.name span.year").text();
            String rating = e.select("div.right div.rating").text();
            String easternTitle = e.select("div.info p.name").text().replaceAll(" \\d+$", "");
            String kinopoiskId = e.select("div.info p.name a").attr("data-id");

            if (title.isEmpty() || rating.isEmpty() || year.isEmpty() || easternTitle.isEmpty()) {
                continue;
            }

//            System.out.printf("%s(%s), %s, %s - %s%n", title, easternTitle, year, rating, kinopoiskId);
            movie.setName(title);
            movie.setKinopoiskId(kinopoiskId);
            movie.setYear(year);
            movie.setKinopoiskRating(rating);
            movie.setEasternName(easternTitle);
            moviesList.add(movie);
        }

        return moviesList;

    }


    /**
     * Parse json movies list.
     *
     * @param JSONMovies the json movies
     * @return the list
     */
    public List<Movie> parseJSONMovies(String JSONMovies, String searchVal) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(JSONMovies);
            if (obj.isNull("results")) {
                return movies;
            }
            JSONArray items = obj.getJSONArray("results");

            for (int i = 0; i < items.length(); i++) {
                JSONObject movieJSON = items.getJSONObject(i);
                String name = movieJSON.getString("title");
                String imdb_id = movieJSON.getString("id");
                String image = movieJSON.getString("image");
                if(!name.contains(searchVal) || name.contains("(video)")) {
                    continue;
                }

                String movieRatingsJSON = getResults("Ratings",null, imdb_id);
                JSONObject movieRatingsObj = new JSONObject(movieRatingsJSON);
                String year = movieRatingsObj.getString("year");
                String title = movieRatingsObj.getString("title");

                String imdbRating = movieRatingsObj.getString("imDb");
                String metacriticRating = movieRatingsObj.getString("metacritic");
                String theMovieDbRating = movieRatingsObj.getString("theMovieDb");
                String rottenTomatoesRating = movieRatingsObj.getString("rottenTomatoes");
                String tV_comRating = movieRatingsObj.getString("tV_com");
                String filmAffinityRating = movieRatingsObj.getString("filmAffinity");


                Movie movie = new Movie(name, imdb_id, imdbRating, metacriticRating, theMovieDbRating, rottenTomatoesRating, tV_comRating,
                        filmAffinityRating, image, year);

                movies.add(movie);
            }
        } catch (JSONException | IOException e) {
//            e.printStackTrace();
            return new ArrayList<>();
        }

        return movies;
    }

    public List<Movie> getMovieListFromApis(String searchVal) throws IOException {

//        return null;

        String JSONMovies = getResults("SearchMovie",searchVal, null);
        System.out.println(searchVal);
        List<Movie> imdbMovies = parseJSONMovies(JSONMovies, searchVal); //limit 100 -> use only for special occasions
        List<Movie> kinopoiskMovies = parseKinopoiskSearchHtml(searchVal);

        if (imdbMovies.isEmpty()) {
            return kinopoiskMovies;
        }
        return mergeMovieLists(imdbMovies, kinopoiskMovies);
    }

    private List<Movie> mergeMovieLists(List<Movie> imdbMovieList, List<Movie> kinopoiskMovieList) {

        Map<String, Movie> map = new HashMap<>();
        System.out.println("im: " + imdbMovieList.size());
        for (Movie m : imdbMovieList) {
            String key = m.getName() + m.getYear();
            map.put(key, m);
        }
        for (Movie m : kinopoiskMovieList) {
            String key = m.getName() + m.getYear();

            if(map.containsKey(key)) {
                map.get(key).setKinopoiskId(m.getKinopoiskId());
                map.get(key).setKinopoiskRating(m.getKinopoiskRating());
                map.get(key).setEasternName(m.getEasternName());
            }
        }
        return new ArrayList<>(map.values());
    }




    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        ImdbApiReader reader = new ImdbApiReader("Django");
//        String JSONMovies = reader.getResults("SearchMovie","Django", null);
//        List<Movie> imdbMovies = reader.parseJSONMovies(JSONMovies); //limit 100 -> use only for special occasions

        //for unlimited tests
//        List<Movie> imdbMovies = new ArrayList<>();
//        Movie m1 = new Movie("Django", "tt0060315", "7.8", "7.7", "6.6", "6.6", "5.5", "8.8",
//                "asdsadas", "1966");
//        imdbMovies.add(m1);


//        List<Movie> kinopoiskMovies = reader.parseKinopoiskSearchHtml("Django");
//        System.out.println(reader.mergeMovieLists(imdbMovies, kinopoiskMovies));

        System.out.println(reader.getMovieListFromApis("Django"));

//       reader.parseKinopoiskSearchHtml("Django");
    }

}
















