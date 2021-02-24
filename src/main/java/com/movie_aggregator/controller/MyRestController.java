package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mturchanov
 */

@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> showAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();
        return allMovies; // jackson-api in maven will convert entity-object to json
    }

    //TODO: exception handling
    @GetMapping("movies/{id}")
    public Movie getMovie(@PathVariable int id) {
        return movieService.getMovie(id); // jackson-api in maven will convert entity-object to json
    }

    @PostMapping("/movies")
    public Movie addNewMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);;
        return movie;
    }

    @PutMapping("/movies/{id}")
//    @RequestMapping(value = "/movies/{id}",
//            produces = "application/json",
//            method = {RequestMethod.PUT})
    public Movie updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(id, movie);
        movieService.saveMovie(movie);
        return movie;
    }



    //TODO: exception handling
    @DeleteMapping("movies/{id}")
    public String deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
        return "Movie with ID " + id + " was deleted";
    }
}
