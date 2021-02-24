package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.service.MovieService;
import com.movie_aggregator.utils.ImdbApiReader;
import com.movie_aggregator.utils.MovieApiReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mturchanov
 */

@Controller
public class MyController {

    @Autowired
    private MovieService movieService;


    @RequestMapping("/searchMovie")
    public String getHomePage(@RequestParam("searchVal") String searchVal, Model model)
            throws IOException {
//        ImdbApiReader apiReader = new ImdbApiReader();
        MovieApiReader reader = new MovieApiReader();

//        String JSONMovies = apiReader.getResults("SearchMovie",searchVal, null);
//        List<Movie> imdbMovies = apiReader.parseJSONMovies(JSONMovies);
//        List<Movie> kinopoiskMovies = apiReader.parseKinopoiskSearchHtml(searchVal);
//        List<Movie> movies = apiReader.getMovieListFromApis(searchVal);
        List<Movie> movies = reader.getMovieListFromApis(searchVal);
        //for (Movie m : movies) {
        //    movieService.saveMovie(m);
        //}

        //System.out.println(movieService.getColumnProperties("imdb_rating"));
        model.addAttribute("movies", movies);
        return "result";
    }


    @RequestMapping("/movie")
    public String getMovieInfo(@RequestParam int id) {
        return null;
    }


    @RequestMapping("/")
    public String showFirstView() {
        return "index";
    }
}





