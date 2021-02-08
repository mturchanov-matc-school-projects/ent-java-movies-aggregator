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
 * @author mturchanov
 */
public class ImdbApiReader implements PropertiesLoader {
    //https://imdb-api.com/en/API/SearchMovie/API_KEY/NAME
    private static final String ROOT_URL = "https://imdb-api.com/en/API/";
    private String movieToSearch;
    private Properties properties;

    public ImdbApiReader(String movieToSearch) {
        this.movieToSearch = movieToSearch;
        properties = loadProperties("/api_keys.properties");
    }

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

                double imdbRating = movieRatingsObj.getDouble("imDb");
                int metacriticRating = movieRatingsObj.getInt("metacritic");
                double theMovieDbRating = movieRatingsObj.getDouble("theMovieDb");
                int rottenTomatoesRating = movieRatingsObj.getInt("rottenTomatoes");
                double tV_comRating = movieRatingsObj.getDouble("tV_com");
                double filmAffinityRating = movieRatingsObj.getDouble("filmAffinity");


                Movie movie = new Movie(name, imdb_id, imdbRating, metacriticRating, theMovieDbRating, rottenTomatoesRating, tV_comRating,
                        filmAffinityRating, image, year);

                movies.add(movie);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return movies;
    }




    public static void main(String[] args) throws IOException {
//        System.out.println(new ImdbApiReader().getResults("Inception"));
//        System.out.println(new ImdbApiReader().parseJSONBooks("ads"));
    }

}
