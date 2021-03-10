package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type My rest controller.
 *
 * @author mturchanov
 */
@RestController
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private GenericService genericService;

    /**
     * Show all movies list.
     *
     * @return the list
     */
    @GetMapping("/movies")
    public List<Movie> showAllMovies() {
        List<Movie> allMovies = genericService.getAll(Movie.class);
        return allMovies; // jackson-api in maven will convert entity-object to json
    }

    /**
     * Gets movie.
     *
     * @param id the id
     * @return the movie
     */
//TODO: exception handling
    @GetMapping("movies/{id}")
    public Movie getMovie(@PathVariable int id) {
        return genericService.get(Movie.class, id); // jackson-api in maven will convert entity-object to json
    }

    /**
     * Add new movie movie.
     *
     * @param movie the movie
     * @return the movie
     */
    @PostMapping("/movies")
    public Movie addNewMovie(@RequestBody Movie movie) {
        genericService.save(movie);;
        return movie;
    }

    /**
     * Delete movie string.
     *
     * @param id the id
     * @return the string
     */
    @PutMapping("/movies/{id}")
//    @RequestMapping(value = "/movies/{id}",
//            produces = "application/json",
//            method = {RequestMethod.PUT})
    //TODO: add generic update with id signature
    //public Movie updateMovie(@PathVariable int id, @RequestBody Movie movie) {
    //    Movie updatedMovie = genericService.updateMovie(id, movie);
    //    genericService.saveMovie(movie);
    //    return movie;
    //}



    //TODO: exception handling
    @DeleteMapping("movies/{id}")
    public String deleteMovie(@PathVariable int id) {
        genericService.delete(Movie.class, id);
        return "Movie with ID " + id + " was deleted";
    }
}
