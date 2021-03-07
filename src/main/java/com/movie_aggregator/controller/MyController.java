package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.service.GenericService;
import com.movie_aggregator.utils.MovieApisReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @author mturchanov
 */

@Controller
public class MyController {

    @Autowired
    private GenericService genericService;;


    @RequestMapping("/searchMovie")
    public String getHomePage(@RequestParam("searchVal") String searchVal, Model model)
            throws IOException {

        MovieApisReader reader = new MovieApisReader();


        List<Movie> movies = reader.parseJSONKinopoiskMovies(searchVal);
        model.addAttribute("movies", movies);
        return "result";
    }


    @RequestMapping("/movie")
    public String getMovieInfo(@RequestParam Movie movie, Model model) {
        //model.addAttribute("movie", movieService.getMovie(id));
        model.addAttribute("movie", movie);

        return "movie";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        genericService.delete(Movie.class, 29);
        return "test";
    }


    @RequestMapping("/")
    public String showFirstView() {
        return "index";
    }
}





