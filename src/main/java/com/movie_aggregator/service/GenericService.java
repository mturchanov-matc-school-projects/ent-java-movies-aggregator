package com.movie_aggregator.service;

import com.movie_aggregator.entity.*;
import com.movie_aggregator.repository.GenericDao;
import com.movie_aggregator.utils.MovieApisReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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
    public <T> int save(final T o)
    {
        return (Integer) genericDao.save(o);
    }

    /**
     * Delete.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @param id   the id
     */
    public <T> void  delete(final Class<T> type, Integer id){
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


    /**
     * Gets movies.
     *
     * @param searchVal     the search val
     * @return the movies
     * @throws IOException the io exception
     */
    public List<Movie> getMovies (String searchVal) throws IOException, URISyntaxException {
        Search existedSearch = getOneEntryByColumProperty("name", searchVal, Search.class);
        searchVal = searchVal.trim().toLowerCase();
        // Search existedSearch = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        List<Movie> movies = null;
        //System.out.printf("existedSearch is null : %b%n", existedSearch == null);
        if (existedSearch != null) { //IF SEARCH IN DB THEN NO NEED FOR APIS REQUESTS
            // GET movies from DB
            movies = getMoviesBasedOnSearchName(searchVal);
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
        } else  { // OTHERWISE, NO SEARCH IN DB == NO MOVIES TO GET -> CALL APIS REQUESTS
            //MovieApisReader movieApisReader = new MovieApisReader();
            movies = movieApisReader.parseJSONKinopoiskMovies(searchVal);
            if (movies == null) {
                return null;
            }
            Search lastSearch = getLastSearch();
            int id = 1;
            if (lastSearch != null) {
                id = lastSearch.getId() + 1;
            }
            Search newSearch = new Search(id, searchVal);
            saveOrUpdate(newSearch); // save new search manually because only once needed

            for (Movie movie : movies) { // update movie_search via cascade
                movie.addSearchToMovie(new Search(id, searchVal)); //1search-manyMovies case
            }

            // check if movie was added to db by different search -> update movie_search via cascade
            for (Movie movie : movies) {
                Movie getMovie = getOneEntryByColumProperty("kinopoiskId",
                        movie.getKinopoiskId(), Movie.class);
                if (getMovie != null) { //if movie in db then it was added via another search
                    getMovie.addSearchToMovie(newSearch);
                    genericDao.merge(getMovie); // update movie_search -> 1movie-manySearches case
                    continue;
                }
                genericDao.saveOrUpdate(movie);
            }
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
    public <T> T merge(final T o)   {
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
