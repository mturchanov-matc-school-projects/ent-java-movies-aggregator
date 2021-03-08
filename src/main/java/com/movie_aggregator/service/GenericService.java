package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.repository.GenericDao;
import org.hibernate.Session;
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

    public <T> int save(final T o)
    {
        return (Integer) genericDao.save(o);
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
        Search existedSearch = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        Search lastSearch = getLastSearch();

        // update movie with appropriate search
        int existedSearchID = existedSearch == null ? 0 : existedSearch.getId();
        System.out.println("addMovieListToMovieTable().existedSearchID: " + existedSearchID);
        for (Movie movie : moviesToAdd) {
            if (existedSearchID != 0) {
                movie.addSearchToMovie(new Search(existedSearchID, searchVal));
            } else {
                int id = lastSearch.getId() + 1;
                movie.addSearchToMovie(new Search(id, searchVal));
            }
        }


        // check if db has such search
        if (!moviesToAdd.isEmpty() && existedSearch == null) {
            save(new Search(lastSearch.getId() + 1, searchVal));
            for (Movie movie : moviesToAdd) {
                System.out.println("addMovieListToMovieTable() moviesToAdd != null && existedSearch == null + in for loop");
               // check if movie was added to db by different search
                Movie getMovie = getOneEntryByColumProperty("kinopoiskId", movie.getKinopoiskId(), Movie.class);
                if (getMovie != null) {
                    getMovie.addSearchToMovie(new Search(searchVal));
                    genericDao.saveOrUpdate(getMovie);
                    continue;
                }
                System.out.printf("m'searches: %s, m - %s\n",  movie.getSearches(), movie);
                genericDao.saveOrUpdate(movie);
            }
            return 1;
        }
        return 0;
    }

    public Search getLastSearch() {
        return genericDao.getLastSearch();
    }


}
