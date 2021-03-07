package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.repository.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mturchanov
 */
@Service
public class MovieService  {
    @Autowired
    private GenericDao dao;

    //@Transactional(readOnly = true)
    public List<Movie> getAll() {

        return dao.getAll(Movie.class);
    }

    public void delete(Movie movie, Integer id) {
        dao.delete(movie.getClass(), id);
    }

    /***/
    public Movie get(Movie movie, Integer id) {
        return dao.get(movie.getClass(), id);
    }

    /***/
    public Movie merge(Movie movie) {
        return dao.merge(movie);
    }

    /***/
    public void saveOrUpdate(Movie movie) {
        dao.saveOrUpdate(movie);
    }



}
