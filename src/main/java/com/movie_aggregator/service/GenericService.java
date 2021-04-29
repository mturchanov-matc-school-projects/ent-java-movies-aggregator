package com.movie_aggregator.service;

import com.movie_aggregator.entity.*;
import com.movie_aggregator.repository.GenericDao;
import com.movie_aggregator.utils.MovieApisReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Generic service.
 *
 * @author mturchanov
 */
@Service
public class GenericService {

    @Autowired
    private GenericDao genericDao;
    @Autowired
    private MovieApisReader movieApisReader;
    //private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Save int.
     *
     * @param <T> the type parameter
     * @param o   the o
     * @return the int
     */
    public <T> int save(final T o) {
        return (Integer) genericDao.save(o);
    }

    /**
     * Delete.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @param id   the id
     */
    public <T> void delete(final Class<T> type, Integer id) {
        genericDao.delete(type, id);
    }

    /**
     * Get t.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @param id   the id
     * @return the t
     */
    public <T> T get(final Class<T> type, final Integer id) {
        return (T) genericDao.get(type, id);
    }

    /**
     * Save or update.
     *
     * @param <T> the type parameter
     * @param o   the o
     */
    public <T> void saveOrUpdate(final T o) {
        genericDao.saveOrUpdate(o);
    }

    /**
     * Gets all.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the all
     */
    public <T> List<T> getAll(final Class<T> type) {
        return genericDao.getAll(type);
    }

    /**
     * Gets one entry by colum property.
     *
     * @param <T>          the type parameter
     * @param propertyName the property name
     * @param searchVal    the search val
     * @param type         the type
     * @return the one entry by colum property
     */
    public <T> T getOneEntryByColumProperty(String propertyName, String searchVal, Class<T> type) {
        return genericDao.getFirstEntryBasedOnAnotherTableColumnProperty(propertyName, searchVal, type);
    }


    /**
     * Gets movies by token.
     *
     * @param token the token
     * @return the movies by token
     */
    public List<Movie> getMoviesByToken(String token) {
        return genericDao.getMoviesByToken(token);
    }

    public List<Movie> getMoviesByProperty(String field, String searchVal, String propertyEntity) {
        return genericDao.getMoviesByProperty(field, searchVal, propertyEntity);
    }

    public List<Movie> getMovies(String searchVal, String movieSourceBase) {
        Search existedSearch = getOneEntryByColumProperty("name", searchVal, Search.class);

        searchVal = searchVal.trim().toLowerCase();
        Search newSearch = null;
        // Search existedSearch = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        List<Movie> movies = null;
        if (existedSearch != null
                && movieSourceBase.equals("kinopoisk")
                && existedSearch.getIsKinopoiskLaunched() == 0 ) { //IF SEARCH IN DB THEN NO NEED FOR APIS REQUESTS

            List<Movie> updateMovies = movieApisReader.parseGeneralKinopoiskMoviesJson(searchVal);
            movies = getMoviesBasedOnSearchName(searchVal);
            MovieApisReader.merge1(updateMovies, movies);
            existedSearch.setIsKinopoiskLaunched(1);
            genericDao.merge(existedSearch);
            for (Movie m : updateMovies) {
                saveOrUpdate(m);
            }
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
            return  updateMovies;

        } else if (existedSearch != null
                && movieSourceBase.equals("imdb")
                && existedSearch.getIsOmdbLaunched() == 0) {

            List<Movie> updateMovies = movieApisReader.parseGeneralImdbMoviesJson(searchVal);
            movies = getMoviesBasedOnSearchName(searchVal);
            MovieApisReader.merge1(updateMovies, movies);
            existedSearch.setIsOmdbLaunched(1);
            genericDao.merge(existedSearch);
            for (Movie m : updateMovies) {
                saveOrUpdate(m);
            }
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
            return  updateMovies;
        } else if(existedSearch != null) {
            movies = getMoviesBasedOnSearchName(searchVal);
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
        }
            else {
            movies = recordNewMovies(searchVal, movieSourceBase);
            if (movies == null) return null;
        }

        return movies;
    }

    private List<Movie> recordNewMovies(String searchVal, String movieSourceBase) {
        List<Movie> movies = null;
        Search newSearch;
        Search lastSearch = getLastSearch();
        int id = 1;
        if (lastSearch != null) {
            id = lastSearch.getId() + 1;
        }
        newSearch = new Search(id, searchVal);
        if (movieSourceBase.equals("kinopoisk")) {
            movies = movieApisReader.parseGeneralKinopoiskMoviesJson(searchVal);
            newSearch.setIsKinopoiskLaunched(1);
        } else if (movieSourceBase.equals("imdb")) {
            movies = movieApisReader.parseGeneralImdbMoviesJson(searchVal);
            newSearch.setIsOmdbLaunched(1);
        }

        if (movies == null) {
            return null;
        }
        saveOrUpdate(newSearch); // save new search manually because only once needed

        for (Movie movie : movies) { // update movie_search via cascade
            movie.addSearchToMovie(newSearch); //1search-manyMovies case
        }
        // check if movie was added to db by different search -> update movie_search via cascade
        for (Movie movie : movies) {
            int filmId = 0;
            String nameEn = movie.getEngName();
            String year = movie.getYear();
            String nameRu = movie.getRusName();
            if (!movie.getEngName().isEmpty()) {
                filmId = MovieApisReader.hashCode(nameEn + year);
            } else {
                filmId = MovieApisReader.hashCode(nameRu + year);
            }

            Movie getMovie = get(Movie.class, filmId);
            if (getMovie != null) { //if movie in db then it was added via another search
                getMovie.addSearchToMovie(newSearch);
                genericDao.merge(getMovie); // update movie_search -> 1movie-manySearches case
                continue;
            }
            genericDao.saveOrUpdate(movie);
        }
        return movies;
    }

    /**
     * Merge t.
     *
     * @param <T> the type parameter
     * @param o   the o
     * @return the t
     */
    public <T> T merge(final T o) {
        return (T) genericDao.merge(o);
    }

    /**
     * Save user int.
     *
     * @param user the user
     * @return the int
     */
    public int saveUser(User user) {
        User existedUserWithTheSameUsername = getOneEntryByColumProperty("username", user.getUsername(), User.class);
        if (existedUserWithTheSameUsername != null) {
            return 0;
        }
        user.setEnabled(1);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());

        //String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Authority authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority("ROLE_USER");
        //authority.setAuthority("ROLE_ADMIN");

        user.addAuthorityToUser(authority);
        saveOrUpdate(user);
        return 1;
    }

    /**
     * Gets movies based on search name.
     *
     * @param searchName the search name
     * @return the movies based on search name
     */
//TODO: make it generic
    public List<Movie> getMoviesBasedOnSearchName(String searchName) {
        return genericDao.getMoviesBasedOnSearchName(searchName);
    }

    /**
     * Gets last search.
     *
     * @return the last search
     */
    public Search getLastSearch() {
        return genericDao.getLastSearch();
    }

    public Set<ReviewsSourcesLookup> getMovieReviewSourcesForView(Set<ReviewsSourcesLookup> sourcesLookups, Movie movie) {

        System.out.println("getMovieReviewSources size:" + movie.getMovieReviewSources().size());
        System.out.println(movie.getMovieReviewSources().isEmpty());
        Set<MovieReviewSource> reviewSources = new HashSet<>();

        if (movie.getMovieReviewSources().isEmpty()) {
            Set<ReviewsSourcesLookup> lookups = new HashSet<>(getAll(ReviewsSourcesLookup.class));
           // lookups.forEach(lookup -> lookup.se);
            reviewSources = movieApisReader.parseJSONWikiDataReviewSources(movie, lookups);
            movie.setMovieReviewSources(reviewSources);
            System.out.println(movie);
            merge(movie); // or merge?
        }
        //TODO: decide how to take movieSource via forloop inside forloop or sql
        for (ReviewsSourcesLookup lookup : sourcesLookups) {
            //System.out.printf("id:%d, name: %s%n", movie.getId(), lookup.getName());

            //MovieReviewSource movieReviewSource = getMovieReviewSourceBasedOnColumns(movie, lookup);
            Optional<MovieReviewSource> matchingObject = reviewSources.stream().
                    filter(p -> p.getMovie().getId() == movie.getId() && p.getReviewSource().getName().equals(lookup.getName())).
                    findFirst();
            MovieReviewSource reviewSource = matchingObject.orElse(null);
            if (reviewSource == null) {
                continue;
            }

            lookup.setUrl(reviewSource.getUrl());
        }
        return sourcesLookups;
    }


    public MovieReviewSource getMovieReviewSourceBasedOnColumns(Movie movie, ReviewsSourcesLookup reviewSource) {
        String reviewSourceName = reviewSource.getName();
        int movieId = movie.getId();
        //take movieReviewSource woth review url if already was such request
        MovieReviewSource movieReviewSource =
                genericDao.getMovieReviewSourceBasedOnColumns(movieId, reviewSourceName);

        // logic if there is no such movieReviewSource in db/no request was maiden
        //if (movieReviewSource == null
        //        && sparqlResponseJSON.contains("film_web_name_pl") // wikidata's response on film has such property
        //        && reviewSourceName.equals("film_web_pl")) {       // check whether such review_source was requested
        //    // film_web's final identifier is messed up - it's www/name-year-id, meh
        //    String filmName = JsonPath.read(sparqlResponseJSON, "$.film_web_name_pl.value");
        //    String filmId =  JsonPath.read(sparqlResponseJSON, "$.film_web_id_pl.value");
        //    String urlMovieIdentifier = String.format(reviewSourceName,
        //            filmName, movie.getYear(), filmId);
        //    String movieReviewUrl = String.format(reviewSource.getUrl(), urlMovieIdentifier);
        //    movieReviewSource = new MovieReviewSource(reviewSource, movie, movieReviewUrl);
        //} else if (movieReviewSource == null
        //        && sparqlResponseJSON.contains(reviewSourceName)) {
        //    String jsonPathExpression = String.format("$.%s.value", reviewSourceName);
        //    String urlMovieIdentifier = JsonPath.read(sparqlResponseJSON, jsonPathExpression);
        //    String movieReviewUrl = String.format(reviewSource.getUrl(), urlMovieIdentifier);
        //    movieReviewSource = new MovieReviewSource(reviewSource, movie, movieReviewUrl);
        //}
        return movieReviewSource;
    }

    /**
     * Increment search number counter.
     *
     * @param id the id
     */
//TODO: make it generic
    public void incrementSearchNumberCounter(int id) {
        genericDao.incrementSearchNumberCounter(id);
    }


}
