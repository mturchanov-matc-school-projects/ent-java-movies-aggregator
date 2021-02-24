
package com.movie_aggregator.repository;

import com.movie_aggregator.entity.Movie;

import java.util.List;
import java.util.Set;


/**
 * The interface Movie repository.
 *
 * @author mturchanov
 */

public interface MovieDAO {
    public List<Movie> getAllMovies();

    public void saveMovie(Movie movie);

    public Movie getMovie(int id);

    public void deleteMovie(int id);

    void updateMovie(int id, Movie movie);

    Set<String> getColumnProperties(String column);
}

