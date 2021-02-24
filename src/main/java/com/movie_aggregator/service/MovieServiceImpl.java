package com.movie_aggregator.service;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.repository.MovieDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author mturchanov
 */
//TODO: write custom exeptions with appropriatre response (404/400)
@Service
public class MovieServiceImpl implements MovieService{
    @Autowired
    private MovieDAO movieDAO;
//    @Autowired
//    private MovieRepository movieRepository;

    @Override
    @Transactional
    public List<Movie> getAllMovies() {
//        return movieRepository.findAll();
        return movieDAO.getAllMovies();
    }

    @Override
    @Transactional
    public void saveMovie(Movie movie) {

        movieDAO.saveMovie(movie);
//        movieRepository.save(movie);
    }

    @Override
    @Transactional
    public Movie getMovie(int id) {
//        Movie movie = null;
//        Optional<Movie> optional = movieRepository.findById(id);
//        if(optional.isPresent()) {
//            movie = optional.get();
//        }
//        return movie;
        return movieDAO.getMovie(id);
    }

    @Override
    @Transactional
    public void deleteMovie(int id) {

        movieDAO.deleteMovie(id);
//        movieRepository.deleteById(id);
    }

    @Override
    public Movie updateMovie(int id, Movie movie) {
        Movie updatedMovie = getMovie(id);
        if (movie.getName() != null) {
            String name = movie.getName();
            updatedMovie.setName(name);
        }
        return updatedMovie;
//        return movieRepository.save(updatedMovie);
    }

    @Override
    public Set<String> getColumnProperties(String column) {
        return movieDAO.getColumnProperties(column);
    }

}
