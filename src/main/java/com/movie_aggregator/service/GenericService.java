package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.repository.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mturchanov
 */
@Service
public class GenericService {

    @Autowired
    private GenericDao genericDao;

    public <T> T save(final T o)
    {
        return (T) genericDao.save(o);
    }

    public <T> void  delete(final Class<T> type, Integer id){
      genericDao.delete(type, id);
    }

    /***/
    public <T> T get(final Class<T> type, final Integer id) {
        return (T) genericDao.get(type, id);
    }

    /***/
    public <T> T merge(final T o) {
        return (T) genericDao.merge(o);
    }

    /***/
    public <T> void saveOrUpdate(final T o) {
       genericDao.saveOrUpdate(o);
    }

    public <T> List<T> getAll(final Class<T> type) {
        return genericDao.getAll(type);
    }

    public <T> T getOneEntryByColumProperty(String propertyName, String searchVal, Class<T> type) {
        return genericDao.getOneEntryByColumProperty(propertyName, searchVal, type);
    }

    public Integer addMovieListToMovieTable(List<Movie> moviesToAdd, String propertyName, String searchVal, Class<Search> searchClass) {
        Search search = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        // if there is no such search in db then db hasnt movie list yet
        if (!moviesToAdd.isEmpty() && search == null) {
            for (Movie movie : moviesToAdd) {
                //however if movie was already there then it was found by another search value
                // and new search must be recorded
                //TODO: When movie.searches is updated then error
                //      related to cascade?
                // Low priority
                Movie getMovie = get(Movie.class, movie.getId());
                if (getMovie != null) {
                    getMovie.addSearchToMovie(new Search(searchVal));
                    genericDao.saveOrUpdate(getMovie);
                    continue;
                }
                genericDao.saveOrUpdate(movie);
            }
            return 1;
        }

        return 0;
    }
}
