package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.repository.GenericDao;
import com.movie_aggregator.utils.MovieApisReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
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
    public <T> void saveOrUpdate(final T o) {
       genericDao.saveOrUpdate(o);
    }

    public <T> List<T> getAll(final Class<T> type) {
        return genericDao.getAll(type);
    }

    public <T> T getOneEntryByColumProperty(String propertyName, String searchVal, Class<T> type) {
        return genericDao.getFirstEntryBasedOnAnotherTableColumnProperty(propertyName, searchVal, type);
    }

    public List<Movie> getMovies (Search existedSearch, String searchVal) throws IOException {
       // Search existedSearch = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        List<Movie> movies = null;
        System.out.println(existedSearch == null);
        if (existedSearch != null) { //IF SEARCH IN DB THEN NO NEED FOR APIS REQUESTS
            // GET movies from DB
            movies = getMoviesBasedOnSearchName(searchVal);
            System.out.println("movie list size: " + movies.size());
            incrementSearchNumberCounter(existedSearch.getId()); // increment Search.number
        } else  {
            MovieApisReader movieApisReader = new MovieApisReader();
            movies = movieApisReader.parseJSONKinopoiskMovies(searchVal);
            Search lastSearch = getLastSearch();
            int id = 1;
            if (lastSearch != null) {
                id = lastSearch.getId() + 1;
            }
            save(new Search(id, searchVal));

            for (Movie movie : movies) {
                movie.addSearchToMovie(new Search(id, searchVal));
                System.out.println(movie.getSearches());
            }

            for (Movie movie : movies) {
                // check if movie was added to db by different search
                Movie getMovie = getOneEntryByColumProperty("kinopoiskId", movie.getKinopoiskId(), Movie.class);
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

    //TODO: make it generic
    public List<Movie> getMoviesBasedOnSearchName(String searchName) {
       return genericDao.getMoviesBasedOnSearchName(searchName);
    }

    public Integer addMovieListToMovieTable(List<Movie> moviesToAdd, String propertyName, String searchVal, Class<Search> searchClass) {
        Search existedSearch = getOneEntryByColumProperty(propertyName, searchVal, searchClass);
        Search lastSearch = getLastSearch();

        //TODO: refactor to seperate method
        //update movie with appropriate search
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
        incrementSearchNumberCounter(existedSearchID);
        return 0;
    }

    public Search getLastSearch() {
        return genericDao.getLastSearch();
    }

    //TODO: make it generic
    public void incrementSearchNumberCounter(int id) {
       genericDao.incrementSearchNumberCounter(id);
    }


}
