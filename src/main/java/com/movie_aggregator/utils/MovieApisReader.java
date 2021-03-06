package com.movie_aggregator.utils;

import com.jayway.jsonpath.JsonPath;
import com.movie_aggregator.entity.*;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Movie apis reader.
 * Includes all operations/requests with
 * api(s)/services
 *
 * @author mturchanov
 */
@Component
public class MovieApisReader implements PropertiesLoader {
    private static final int PRIME = 31;
    private Properties properties;
    /**
     * The constant KINOPOISK_ROOT.
     */
    public static final String KINOPOISK_ROOT = "https://kinopoiskapiunofficial.tech/api/v2.1/films/";
    /**
     * The constant OMDB_ROOT.
     */
    public static final String OMDB_ROOT = "http://www.omdbapi.com/";

    /**
     * The constant QUERY_WIKI_DATA.
     */
    public static final String QUERY_WIKI_DATA = "https://query.wikidata.org/sparql?format=json&query=";
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Instantiates a new Movie apis reader.
     */
    public MovieApisReader() {
        properties = loadProperties("/api_keys.properties");
    }


    /**
     * Gets json from api/service
     * For movies api hndles
     * search requests that provide general information
     * and requests for specific movie that provides full info,
     * In addition, handles request for receiving movie's frames
     * and request from sparql query
     *
     * @param searchType the search source
     * @param source     the search type
     * @param searchVal  the search val
     * @param movie      the movie
     * @return the json from api
     * @throws IOException the io exception
     */
    public String getJSONFromApi(String searchType, String source, String searchVal, Movie movie) {
        searchVal = URLEncoder.encode(searchVal, StandardCharsets.UTF_8);
        OkHttpClient client = new OkHttpClient();
        String requestURL = null;
        Request request = null;

        switch (source) {
            case "omdb": {
                String apiKey = properties.getProperty("omdb_api");
                if (searchType.equals("general")) {
                    requestURL = String.format("%s?s=%s&apiKey=%s&type=movie", OMDB_ROOT, searchVal, apiKey);
                } else if (searchType.equals("specific")) {
                    requestURL = String.format("%s?i=%s&apiKey=%s&plot=full&type=movie", OMDB_ROOT, searchVal, apiKey);
                }
                request = new Request.Builder()
                        .url(requestURL)
                        .method("GET", null)
                        .build();
                break;
            }
            case "kinopoisk": {
                String apiKey = properties.getProperty("kinopoisk_unofficial_api");
                if (searchType.equals("general")) {
                    requestURL = String.format("%s%s%s%s", KINOPOISK_ROOT, "search-by-keyword?keyword=", searchVal, "&page=1");
                } else if (searchType.equals("specific")) {
                    String url = String.format("%s%s?append_to_response=BUDGET,RATING,REVIEW", KINOPOISK_ROOT, searchVal);
                    requestURL = url;
                } else if (searchType.equals("frames")) {
                    requestURL = String.format("%s%s/frames", KINOPOISK_ROOT, searchVal);
                }
                request = new Request.Builder()
                        .url(requestURL)
                        .addHeader("X-API-KEY", apiKey)
                        .method("GET", null)
                        .build();
                break;
            }
            case "sparql": {
                URL sparqlQueryRaw = MovieApisReader.class.getResource("/sparqlQuery.txt");
                String filePathForSparqlQuery = null;
                String sparqlWithoutId = null;

                try {
                    filePathForSparqlQuery = Paths.get(sparqlQueryRaw.toURI()).toFile().getAbsolutePath();
                    sparqlWithoutId = new String(Files.readAllBytes(Paths.get(filePathForSparqlQuery)));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
                String sparqlQueryFormatted = String.format(sparqlWithoutId, movie.getKinopoiskId(), movie.getImdbId());
                requestURL = String.format("%s%s", QUERY_WIKI_DATA, sparqlQueryFormatted);


                request = new Request.Builder()
                        .url(requestURL)
                        .method("GET", null)
                        .build();
                break;
            }
        }

        Response response;
        try {
            response = client.newCall(request).execute();
            if (response == null) {
                return null;
            }
            return response.body().string();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }


    /**
     * Parse general kinopoisk movies json list.
     *
     * @param searchVal the search val
     * @return the list
     */
    public List<Movie> parseGeneralKinopoiskMoviesJson(String searchVal) {
        //get general movie info json data
        String JSONMovies = getJSONFromApi("general", "kinopoisk", searchVal, null);
        if (JSONMovies == null) {
            return null;
        }
        ArrayList<Movie> movies = new ArrayList<>();

        JSONObject obj = new JSONObject(JSONMovies);
        JSONArray films = obj.getJSONArray("films");

        for (int i = 0; i < films.length(); i++) {
            JSONObject movieJSON = films.getJSONObject(i);
            String nameRu = movieJSON.getString("nameRu");
            String nameEn = movieJSON.has("nameEn")
                    ? movieJSON.getString("nameEn")
                    : "";
            String shortInfo = movieJSON.has("description")
                    ? movieJSON.getString("description")
                    : null;
            if ((!nameEn.isEmpty() && !nameEn.toLowerCase(Locale.ROOT).contains(searchVal))
                    || nameEn.contains("(видео)")
            ) {
                continue;
            }
            String year = movieJSON.has("year")
                    ? movieJSON.getString("year")
                    : "";
            // String director = shortInfo.substring(shortInfo.indexOf(' '), shortInfo.indexOf('('));
            String director = "";

            String duration = movieJSON.has("filmLength")
                    ? movieJSON.getString("filmLength")
                    : "n/a";
            String kVotes = movieJSON.has("ratingVoteCount")
                    ? String.valueOf(movieJSON.getInt("ratingVoteCount"))
                    : "";
            String kinopoiskId = movieJSON.has("filmId")
                    ? String.valueOf(movieJSON.getInt("filmId"))
                    : "";
            String rating = movieJSON.has("rating")
                    ? movieJSON.getString("rating")
                    : "";
            String image = movieJSON.has("posterUrlPreview")
                    ? movieJSON.getString("posterUrlPreview")
                    : "https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png";
            JSONArray countriesJSONArr = movieJSON.has("countries")
                    ? movieJSON.getJSONArray("countries")
                    : null;
            StringBuilder countries = new StringBuilder();
            for (int j = 0; j < countriesJSONArr.length(); j++) {
                JSONObject ratingsJSON = countriesJSONArr.getJSONObject(j);
                countries.append(ratingsJSON.getString("country"));
                countries.append(",");
            }
            if (countries.length() > 1) {
                countries.setLength(countries.length() - 1);
            }
            JSONArray genresJSONArr = movieJSON.has("genres")
                    ? movieJSON.getJSONArray("genres")
                    : null;
            StringBuilder genres = new StringBuilder();
            for (int j = 0; j < genresJSONArr.length(); j++) {
                JSONObject ratingsJSON = genresJSONArr.getJSONObject(j);
                genres.append(ratingsJSON.getString("genre"));
                genres.append(",");
            }
            if (genres.length() > 1) {
                genres.setLength(genres.length() - 1);
            }
            int filmId;
            if (!nameEn.isEmpty()) {
                filmId = hashCode(nameEn + year);
            } else {
                filmId = hashCode(nameRu + year);
            }

            Movie movie = new Movie(filmId, nameEn, nameRu, rating, duration, genres.toString(),
                    director, countries.toString(), image, year, kVotes, kinopoiskId);
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Parse specific kinopoisk movies json movie.
     *
     * @param movie the movie
     * @return the movie
     */
    public Movie parseSpecificKinopoiskMoviesJson(Movie movie) {
        String movieDetails = getJSONFromApi("specific", "kinopoisk", movie.getKinopoiskId(), null);
        logger.info("details: " + movieDetails);
        JSONObject movieDetailsJSON = new JSONObject(movieDetails);
        JSONObject movieDataJSON = movieDetailsJSON.getJSONObject("data");
        String description = !movieDataJSON.isNull("description")
                ? movieDataJSON.getString("description")
                : "";
        JSONObject externalIdObj = movieDetailsJSON.getJSONObject("externalId");
        String imdbId = !externalIdObj.isNull("imdbId")
                ? externalIdObj.getString("imdbId")
                : "";
        String audienceRating = !movieDetailsJSON.isNull("ratingMpaa")
                ? movieDataJSON.getString("ratingMpaa")
                : "";
        String distributors = !movieDetailsJSON.isNull("distributors")
                ? movieDataJSON.getString("distributors")
                : "";
        String ratingAgeLimits = !movieDetailsJSON.isNull("ratingAgeLimits")
                ? String.valueOf(movieDetailsJSON.getInt("ratingAgeLimits"))
                : "";
        audienceRating = !ratingAgeLimits.isEmpty() ? audienceRating += "(" + ratingAgeLimits + ")" : audienceRating;

        JSONObject reviewJSON = movieDetailsJSON.getJSONObject("review");
        int reviews = !reviewJSON.isNull("reviewsCount")
                ? reviewJSON.getInt("reviewsCount") :
                0;
        int goodReviews = !reviewJSON.isNull("ratingGoodReviewVoteCount")
                ? reviewJSON.getInt("ratingGoodReviewVoteCount") :
                0;
        String imdbRating = !reviewJSON.isNull("ratingImdb")
                ? String.valueOf(reviewJSON.getInt("ratingImdb")) :
                "";
        String ratingImdbVoteCount = !reviewJSON.isNull("ratingImdbVoteCount")
                ? String.valueOf(reviewJSON.getInt("ratingImdbVoteCount")) :
                "";
        JSONObject budgetJSON = movieDetailsJSON.getJSONObject("budget");
        String boxOffice = !budgetJSON.isNull("grossWorld")
                ? String.valueOf(budgetJSON.getInt("grossWorld")) :
                "";
        String kinopoiskReviewsFormatted = String.format("%s(%s)", reviews, goodReviews);
        Movie updateMovie = new Movie(imdbId, description, imdbRating, ratingImdbVoteCount, boxOffice, audienceRating, kinopoiskReviewsFormatted);
        mergeObjects(updateMovie, movie);
        //processFramesAndReview(filmId, reviews, goodReviews, movie);
        return updateMovie;
    }

    /**
     * Load frames movie.
     *
     * @param movie the movie
     * @return the movie
     */
    public Movie loadFrames(Movie movie) {
        //TODO: handle if no kinopoisk id
        logger.info("KINOPOISK ID: " + movie.getKinopoiskId());
        String framesDetails = getJSONFromApi("frames", "kinopoisk", movie.getKinopoiskId(), null);
        if (!framesDetails.isEmpty()) {
            //StringBuilder framesSb = new StringBuilder();
            JSONObject framesDetailsJSON = new JSONObject(framesDetails);
            JSONArray frames = framesDetailsJSON.getJSONArray("frames");
            for (int j = 0; j < frames.length(); j++) {
                JSONObject frameJSON = frames.getJSONObject(j);
                String frame = frameJSON.getString("image");
                if (!frame.isEmpty()) {
                    movie.addImageToMovie(new Image(movie, frame));
                }
            }
        }
        return movie;
    }

    /**
     * parses imdb movie JSON
     *
     * @param movie the movie
     * @return movie
     */
    public Movie parseSpecificImdbMovieJson(Movie movie) {
        String JSONMovies = getJSONFromApi("specific", "omdb", movie.getImdbId(), null);
        if (JSONMovies == null) {
            return null;
        }
        JSONObject movieDetailsJSON = new JSONObject(JSONMovies);
        //JSONObject movieDetailsJSON = new JSONObject(imdbData);
        String isWorking = movieDetailsJSON.getString("Response");
        if (isWorking.equals("False")) {
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
                : null;
        String awards = movieDetailsJSON.has("Awards")
                ? movieDetailsJSON.getString("Awards")
                : null;
        String production = movieDetailsJSON.has("Production")
                ? movieDetailsJSON.getString("Production")
                : null;
        String released = movieDetailsJSON.getString("Released");
        String writer = movieDetailsJSON.has("Writer")
                ? movieDetailsJSON.getString("Writer")
                : null;
        String audienceRating = movieDetailsJSON.has("Rated")
                ? movieDetailsJSON.getString("Rated")
                : null;
        String duration = movieDetailsJSON.has("Runtime")
                ? movieDetailsJSON.getString("Runtime")
                : null;

        JSONArray ratingsArrayJSON = movieDetailsJSON.has("Ratings")
                ? movieDetailsJSON.getJSONArray("Ratings")
                : null;
        String metacrtiticRating = null;
        String rottenTomatoesRating = null;
        for (int j = 0; j < ratingsArrayJSON.length(); j++) {
            JSONObject ratingsJSON = ratingsArrayJSON.getJSONObject(j);
            if (ratingsJSON.getString("Source").equals("Rotten Tomatoes")) {
                metacrtiticRating = ratingsJSON.getString("Value");
            } else if (ratingsJSON.getString("Source").equals("Metacritic")) {
                rottenTomatoesRating = ratingsJSON.getString("Value");
            }
        }

        Movie updateMovie = new Movie(description, imdbRating, imdbVotes, metacrtiticRating, rottenTomatoesRating,
                boxOffice, duration, genre, director, actors, language, country, metascore, awards, writer, released,
                production, audienceRating);
        mergeObjects(updateMovie, movie);
        logger.info("MODEL ID:" + movie.getId());
        //logger.info(movie);
        return updateMovie;
    }

    /**
     * Parse json wiki data review sources movie.
     *
     * @param movie   the movie
     * @param lookups the lookups
     * @return the movie
     */
    public Movie parseJSONWikiDataReviewSources(Movie movie, Set<ReviewsSourcesLookup> lookups) {
        String sparqlResponseJSON = getJSONFromApi(null, "sparql", "null", movie);
        logger.info("before:  " + sparqlResponseJSON);
        sparqlResponseJSON = sparqlResponseJSON.replaceAll("[\n\\]]", "")
                .replaceAll(".+: \\[", "");
        sparqlResponseJSON = sparqlResponseJSON.substring(0, sparqlResponseJSON.length() - 2);
        logger.info("after:  " + sparqlResponseJSON);

        return generateAllMovieReviewSourcesForMovie(movie, lookups, sparqlResponseJSON);
    }

    private Movie generateAllMovieReviewSourcesForMovie(Movie movie, Set<ReviewsSourcesLookup> lookups,
                                                        String sparqlResponseJSON) {
        Set<MovieReviewSource> movieReviewSources = new HashSet<>();
        for (ReviewsSourcesLookup lookup : lookups) {
            String reviewSourceName = lookup.getName();
            if (sparqlResponseJSON.contains("film_web_name_pl")
                    && reviewSourceName.equals("film_web_pl")) {       // check whether such review_source was requested
                String filmId = JsonPath.read(sparqlResponseJSON, "$.film_web_name_pl.value");
                String movieReviewUrl = String.format(lookup.getUrl(), filmId);
                MovieReviewSource movieReviewSource = new MovieReviewSource(lookup, movie, movieReviewUrl);
                movieReviewSources.add(movieReviewSource);
            } else if (sparqlResponseJSON.contains(reviewSourceName)) {
                String jsonPathExpression = String.format("$.%s.value", reviewSourceName);
                String movieReviewIdentifier = JsonPath.read(sparqlResponseJSON, jsonPathExpression);
                String movieReviewUrl = String.format(lookup.getUrl(), movieReviewIdentifier);
                //System.out.printf("id: %s, name:%s, url:%s", movieReviewIdentifier, lookup.getName(), lookup.getUrl());;

                MovieReviewSource movieReviewSource = new MovieReviewSource(lookup, movie, movieReviewUrl);
                movieReviewSources.add(movieReviewSource);
                ;
            }
        }
        String kinId = movie.getKinopoiskId();
        if (kinId == null && sparqlResponseJSON.contains("kinopoisk")) {
            String filmId = JsonPath.read(sparqlResponseJSON, "$.kinopoisk.value");
            movie.setKinopoiskId(filmId);
        }
        logger.info("\n\n00000000000000000000 -  kinId==null(" + kinId == null + "),KID: " + movie.getKinopoiskId());
        movieReviewSources = movieReviewSources.stream()
                .filter(p -> !p.getUrl().contains("%s")).collect(Collectors.toSet());
        movie.setMovieReviewSources(movieReviewSources);
        return movie;
    }

    /**
     * Parse general imdb movies json list.
     *
     * @param searchVal the search val
     * @return the list
     */
    public List<Movie> parseGeneralImdbMoviesJson(String searchVal) {
        String JSONMovies = getJSONFromApi("general", "omdb", searchVal, null);
        logger.info("parseGeneralImdbMoviesJson:" + JSONMovies);
        if (JSONMovies == null) {
            return null;
        }
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject obj = new JSONObject(JSONMovies);
        JSONArray films = obj.has("Search")
                ? obj.getJSONArray("Search")
                : null;
        if (films == null) {
            return null;
        }
        for (int i = 0; i < films.length(); i++) {
            JSONObject movieJSON = films.getJSONObject(i);
            String imdbId = movieJSON.getString("imdbID");
            String name = movieJSON.getString("Title");
            String imdbPoster = movieJSON.getString("Poster");
            if (imdbPoster != null && imdbPoster.equals("N/A")) {
                imdbPoster = "https://user-images.githubusercontent.com/24848110/33519396-7e56363c-d79d-11e7-969b-09782f5ccbab.png";
            }
            String year = movieJSON.getString("Year");
            int filmId = hashCode(name + year);

            Movie movie = new Movie(filmId, name, imdbPoster, year, imdbId);
            movies.add(movie);
        }
        return movies;
    }

    /**
     * Hash code int.
     *
     * @param string the string
     * @return the int
     */
    public static int hashCode(@NonNull String string) {
        return string.hashCode() * PRIME;
    }

    /**
     * Merge objects.
     *
     * @param obj    the obj
     * @param update the update
     */
    public static void mergeObjects(Object obj, Object update) {
        if (!obj.getClass().isAssignableFrom(update.getClass())) {
            return;
        }
        Method[] methods = obj.getClass().getMethods();
        for (Method fromMethod : methods) {
            if (fromMethod.getDeclaringClass().equals(obj.getClass())
                    && fromMethod.getName().startsWith("get")) {
                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");
                try {
                    Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(update, (Object[]) null);
                    if (value != null) {
                        toMetod.invoke(obj, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Merge lists.
     *
     * @param movies       the movies
     * @param updateMovies the update movies
     */
    public static void mergeLists(List<Movie> movies, List<Movie> updateMovies) {
        for (Movie movie : movies) {
            for (Movie updateMovie : updateMovies) {
                if (movie.getId() == updateMovie.getId()) {
                    mergeObjects(movie, updateMovie);
                }
            }
        }

    }


}




