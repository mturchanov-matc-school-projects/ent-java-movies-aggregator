package com.movie_aggregator.dao;

import com.movie_aggregator.enity.Movie;

import java.util.List;

/**
 * @author mturchanov
 */
public interface MovieDAO {
    void saveOrUpdate(Movie movie);
    List<Movie> getAllMovies();
    void deleteMovie(Movie movie);

}
