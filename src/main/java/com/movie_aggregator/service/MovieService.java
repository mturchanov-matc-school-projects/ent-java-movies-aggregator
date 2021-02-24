package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;

import java.util.List;
import java.util.Set;

/**
 * @author mturchanov
 */
public interface MovieService {
    public List<Movie> getAllMovies();

    public void saveMovie(Movie movie);

    public Movie getMovie(int id);

    public void deleteMovie(int id);

    Movie updateMovie(int id, Movie movie);

    Set<String> getColumnProperties(String column);
}
