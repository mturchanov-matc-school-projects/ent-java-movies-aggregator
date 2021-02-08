package com.movie_aggregator.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.movie_aggregator.enity.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    public String getResults(String searchCategory, String movieId) throws IOException {
        String imdbApi = properties.getProperty("imdb_api_key");
        OkHttpClient client = new OkHttpClient();
        String requestURL;
        if (movieId != null) {
            requestURL = String.format("%s%s/%s/%s", ROOT_URL, searchCategory, imdbApi, movieId);
        } else {
            requestURL = String.format("%s%s/%s/%s", ROOT_URL, searchCategory, imdbApi, movieToSearch);
        }
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
        return response.body().string();
    }


    /**
     * Parse json movies list.
     *
     * @param JSONMovies the json movies
     * @return the list
     */
    public List<Movie> parseJSONMovies(String JSONMovies) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(JSONMovies);
            JSONArray items = obj.getJSONArray("results");

            for (int i = 0; i < items.length(); i++) {
                JSONObject movieJSON = items.getJSONObject(i);
                String name = movieJSON.getString("title");
                String imdb_id = movieJSON.getString("id");
                String image = movieJSON.getString("image");

                String movieRatingsJSON = getResults("Ratings", imdb_id);
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
            e.printStackTrace();
        }

        return movies;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
//        System.out.println(new ImdbApiReader().getResults("Inception"));
//        System.out.println(new ImdbApiReader().parseJSONBooks("ads"));
    }

}
