package com.movie_aggregator.service;

import com.movie_aggregator.entity.*;
import com.movie_aggregator.repository.GenericDao;
import com.movie_aggregator.utils.MovieApisReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Generic service.
 * Includes business logic
 * that is done with dao
 *
 * @author mturchanov
 */
@Service
public class GenericService {

    @Autowired
    private GenericDao genericDao;
    @Autowired
    private MovieApisReader movieApisReader;
    private final Logger logger = LogManager.getLogger(this.getClass());


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




    public List<Movie> getMoviesByProperty(String field, String searchVal, String propertyEntity) {
        return genericDao.getMoviesByProperty(field, searchVal, propertyEntity);
    }

    /**
     * Gets movies either by generating new ones from apis
     * if they are not in db, otherwise by
     * retrieving them from db
     *
     * @param searchVal
     * @param movieSourceBase
     * @returnlist list of genereated movies
     */
    public List<Movie> getMovies(String searchVal, String movieSourceBase) {
        Search existedSearch = getOneEntryByColumProperty("name", searchVal, Search.class);
        searchVal = searchVal.trim().toLowerCase();
        List<Movie> movies = getMoviesBasedOnSearchName(searchVal);
        logger.info("getMoviesBasedOnSearchName: " + movies);

        if (movies == null || movies.isEmpty()){ // generating new movies
            movies = recordNewMovies(searchVal, movieSourceBase);
        } else if (existedSearch != null // movies are in db but not kinopoisk version -> update needed
                && movieSourceBase.equals("kinopoisk")
                && existedSearch.getIsKinopoiskLaunched() == 0) {
            List<Movie> updateMovies = movieApisReader.parseGeneralKinopoiskMoviesJson(searchVal);
            MovieApisReader.mergeLists(updateMovies, movies);
            existedSearch.setIsKinopoiskLaunched(1);
            merge(existedSearch);
            for (Movie m : updateMovies) {
                saveOrUpdate(m);
            }
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
            return updateMovies;
        } else if (existedSearch != null // // movies are in db but not imdb version -> update needed
                && movieSourceBase.equals("imdb")
                && existedSearch.getIsOmdbLaunched() == 0) {
            List<Movie> updateMovies = movieApisReader.parseGeneralImdbMoviesJson(searchVal);
            MovieApisReader.mergeLists(updateMovies, movies);
            existedSearch.setIsOmdbLaunched(1);
            genericDao.merge(existedSearch);
            for (Movie m : updateMovies) {
                saveOrUpdate(m);
            }
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
            return updateMovies;
        } else if (existedSearch != null) { // needed movies version is in db -> retrieve them
            movies = getMoviesBasedOnSearchName(searchVal);
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
        }
        return movies;
    }

    /**
     * Records new movies to db
     * making needed api requests based on chosen movieSourceBase
     *
     * @param searchVal
     * @param movieSourceBase
     * @returnlist list of genereated movies
     */
    private List<Movie> recordNewMovies(String searchVal, String movieSourceBase) {
        List<Movie> movies = null;
        Search newSearch;
        //generating new search
        Search lastSearch = getLastSearch();
        int id = 1;
        if (lastSearch != null) {
            id = lastSearch.getId() + 1;
        }
        newSearch = new Search(id, searchVal);

        //generating movies from api request
        if (movieSourceBase.equals("kinopoisk")) {
            movies = movieApisReader.parseGeneralKinopoiskMoviesJson(searchVal);
            newSearch.setIsKinopoiskLaunched(1);
        } else if (movieSourceBase.equals("imdb")) {
            movies = movieApisReader.parseGeneralImdbMoviesJson(searchVal);
            System.out.println("recordNewMovies:" + movies);
            newSearch.setIsOmdbLaunched(1);
        }

        if (movies == null) { // no movies found with selected search name/movie source -> exit
            return null;
        }
        save(newSearch); // save new search manually because only once needed

        for (Movie movie : movies) { // update movie_search via cascade
            movie.addSearchToMovie(newSearch); //1search-manyMovies case
        }
        // check if movie was added to db by different search -> update movie_search via cascade
        for (Movie movie : movies) {
            int filmId = 0;
            String nameEn = movie.getEngName();
            String year = movie.getYear();
            String nameRu = movie.getRusName();
            //generating unique film id
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
     * Merge object.
     *
     * @param <T> the type parameter
     * @param o   the o
     * @return the t
     */
    public <T> T merge(final T o) {
        return (T) genericDao.merge(o);
    }

    /**
     * Save user with default user-role.
     * Provides bcrypt encryption for password
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

        user.setPassword(encodedPassword);

        Authority authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority("ROLE_USER");

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

    /**
     * Gets last search.
     *
     * @return the last search
     */
    public Set<ReviewsSourcesLookup> getMovieReviewSourcesForView(Set<ReviewsSourcesLookup> pickedSourcesLookups, Movie movie) {

        Set<MovieReviewSource> reviewSources;
        String[] names = pickedSourcesLookups.stream()
                .map(ReviewsSourcesLookup::getName).toArray(size -> new String[pickedSourcesLookups.size()]);

        if (movie.getMovieReviewSources().isEmpty()) {
            Set<ReviewsSourcesLookup> lookups = new HashSet<>(getAll(ReviewsSourcesLookup.class));

            movie = movieApisReader.parseJSONWikiDataReviewSources(movie, lookups);
            reviewSources = movie.getMovieReviewSources();
            merge(movie);
        } else { // movie's reviews sources already in db by previous request
            reviewSources = movie.getMovieReviewSources();
        }
        Set<ReviewsSourcesLookup> result = new HashSet<>();

        //preparing collection for view
        for (MovieReviewSource movieReviewSource : reviewSources) {
            String lookupName = movieReviewSource.getReviewSource().getName();
            boolean isSourceAmongPickedSources = Arrays.stream(names).anyMatch(lookupName::contains);
            if (isSourceAmongPickedSources) {
                ReviewsSourcesLookup finalLookup = movieReviewSource.getReviewSource();
                finalLookup.setUrl(movieReviewSource.getUrl());
                result.add(finalLookup);
            }
        }
        return result;
    }

    public List<Search> getMostRecentSearches() {
        return genericDao.getMostRecentSearches();
    }

    /**
     * Increment search number counter.
     *
     * @param id the id
     */
    public void incrementSearchNumberCounter(int id) {
        genericDao.incrementSearchNumberCounter(id);
    }

    public Map<String, Integer> getCountForEachReviewSource() {
        List<Object[]> rows = genericDao.getCountForEachReviewSource();
        Map<String, Integer> reviewSourceCountMap = new TreeMap<>();
        for (Object[] row: rows) {
           String name = (String) row[0];
            BigInteger count = (BigInteger) row[1];
            Integer finalCount = Integer.parseInt(count.toString());
           reviewSourceCountMap.put(name, finalCount);
        }
        return reviewSourceCountMap;
    }

    /**
     * Generates user's picked review sources
     * based on string array
     *
     * @param reviewsSources review sources array
     * @return set of user's new picked review sources
     */
        public Set<ReviewsSourcesLookup> generateNewPickedReviewSources(String[] reviewsSources) {
        Set<ReviewsSourcesLookup> newPickedReviewSources = new HashSet<>();
        for (String reviewSource : reviewsSources) {
            ReviewsSourcesLookup reviewsSourcesLookup = getOneEntryByColumProperty("name",
                    reviewSource, ReviewsSourcesLookup.class);
            newPickedReviewSources.add(reviewsSourcesLookup);
        }
        return newPickedReviewSources;
    }
}
