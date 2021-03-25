package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.service.GenericService;
import com.movie_aggregator.utils.MovieApisReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Show all movies list.
     *
     * @return the list
     */
    @GetMapping("/movies")
    public List<Movie> showAllMovies() {
        List<Movie> allMovies = genericService.getAll(Movie.class);
        for (Movie m : allMovies) {
            m.setSearches(null);
        }
        return allMovies; // jackson-api in maven will convert entity-object to json
    }

    @GetMapping("/movies/api/${api_key}")
    public List<Movie> showMoviesByToken() {
        List<Movie> allMovies = genericService.getAll(Movie.class);
        for (Movie m : allMovies) {
            m.setSearches(null);
        }
        return allMovies; // jackson-api in maven will convert entity-object to json
    }


    /**
     * Show all movies list.
     *
     * @return the list
     */
    @GetMapping("/movies/s/{searchVal}")
    public List<Movie> showAllMoviesBySearchName(@PathVariable String searchVal) throws IOException {
        Search existedSearch = genericService.getOneEntryByColumProperty("name", searchVal, Search.class);
        List<Movie> movies = genericService.getMovies(existedSearch, searchVal);
        System.out.println(movies);
        for (Movie m : movies) {
            m.setSearches(null);
        }

        return movies; // jackson-api in maven will convert entity-object to json
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
        Movie getMovie =  genericService.get(Movie.class, id); // jackson-api in maven will convert entity-object to json
        getMovie.setSearches(null);
        logger.info(String.format("MyRestController.getMovie(): %s", getMovie));
        System.out.println(String.format("MyRestController.getMovie(): %s", getMovie));
        return getMovie;
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
    //@PutMapping("/movies/{id}")
    //@RequestMapping(value = "/movies/{id}",
    //        produces = "application/json",
    //        method = {RequestMethod.PUT})
    //TODO: add generic update with id signature
   // public Movie updateMovie(@PathVariable int id, @RequestBody Movie movie) {
   //     //Movie updatedMovie = genericService.get(Movie.class, id);
   //     movie.setId(id);
   //     genericService.saveOrUpdate(movie);
   //     return movie;
   // }

    //TODO: exception handling
    @RequestMapping(value = "movies/{id}", method = RequestMethod.DELETE)
    public String deleteMovie(@PathVariable int id) {
        genericService.delete(Movie.class, id);
        return "Movie with ID " + id + " was deleted";
    }
}
