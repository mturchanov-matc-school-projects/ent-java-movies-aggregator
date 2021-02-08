package com.movie_aggregator.dao;

import com.movie_aggregator.enity.Movie;

import java.util.List;

/**
 * The interface Movie dao.
 *
 * @author mturchanov
 */
public interface MovieDAO {
    /**
     * Save or update.
     *
     * @param movie the movie
     */
    void saveOrUpdate(Movie movie);

    /**
     * Gets all movies.
     *
     * @return the all movies
     */
    List<Movie> getAllMovies();

    /**
     * Delete movie.
     *
     * @param movie the movie
     */
    void deleteMovie(Movie movie);

    /**
     * Gets movie by id.
     *
     * @param id the id
     * @return the movie by id
     */
    Movie getMovieByID(int id);

}
