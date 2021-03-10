package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.repository.GenericDao;
import com.movie_aggregator.utils.MovieApisReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
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
    private final Logger logger = LogManager.getLogger(this.getClass());


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
     * Gets movies.
     *
     * @param existedSearch the existed search
     * @param searchVal     the search val
     * @return the movies
     * @throws IOException the io exception
     */
    public List<Movie> getMovies (Search existedSearch, String searchVal) throws IOException {
       // Search existedSearch = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        List<Movie> movies = null;
        if (existedSearch != null) { //IF SEARCH IN DB THEN NO NEED FOR APIS REQUESTS
            // GET movies from DB
            movies = getMoviesBasedOnSearchName(searchVal);
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
        } else  { // OTHERWISE, NO SEARCH IN DB == NO MOVIES TO GET -> CALL APIS REQUESTS
            MovieApisReader movieApisReader = new MovieApisReader();
            movies = movieApisReader.parseJSONKinopoiskMovies(searchVal);
            Search lastSearch = getLastSearch();
            int id = 1;
            if (lastSearch != null) {
                id = lastSearch.getId() + 1;
            }
            save(new Search(id, searchVal));

            for (Movie movie : movies) {
                movie.addSearchToMovie(new Search(id, searchVal)); // id set manually
            }

            for (Movie movie : movies) {
                // check if movie was added to db by different search
                Movie getMovie = getOneEntryByColumProperty("kinopoiskId",
                        movie.getKinopoiskId(), Movie.class);
                if (getMovie != null) {
                    getMovie.addSearchToMovie(new Search(searchVal));
                    genericDao.saveOrUpdate(getMovie);
                    continue;
                }
                genericDao.saveOrUpdate(movie);
            }
        }

        return movies;
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
