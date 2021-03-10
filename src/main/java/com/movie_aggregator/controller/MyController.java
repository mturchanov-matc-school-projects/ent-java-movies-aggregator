package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.service.GenericService;
import com.movie_aggregator.utils.MovieApisReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type My controller.
 *
 * @author mturchanov
 */
@Controller
public class MyController {

    @Autowired
    private GenericService genericService;


    /**
     * Search movie string.
     *
     * @param searchVal the search val
     * @param model     the model
     * @return the string
     * @throws IOException the io exception
     */
    @RequestMapping("/searchMovie")
    public String searchMovie(@RequestParam("searchVal") String searchVal, Model model)
            throws IOException {
        Search existedSearch = genericService.getOneEntryByColumProperty("name", searchVal, Search.class);
        List<Movie> movies = genericService.getMovies(existedSearch, searchVal);
        model.addAttribute("movies", movies);
        return "result";
    }


    /**
     * Gets movie info.
     *
     * @param movie the movie
     * @param model the model
     * @return the movie info
     */
    @RequestMapping("/movie")
    public String getMovieInfo(@RequestParam Movie movie, Model model) {
        //model.addAttribute("movie", movieService.getMovie(id));
        model.addAttribute("movie", movie);

        return "movie";
    }

    /**
     * Test string.
     *
     * @param model the model
     * @return the string
     */
//Testing time
    @RequestMapping("/test")
    public String test(Model model) {
        //Search search = new Search("search1");
        //newMovie.addSearchToMovie(search);
        //movies.add(newMovie);
        //Movie newMovie2 = new Movie();
        //newMovie2.setName("m2 - 2 movied added as a list");
        //newMovie2.setImage("https://ichef.bbci.co.uk/news/976/cpsprodpb/12A9B/production/_111434467_gettyimages-1143489763.jpg");
        //newMovie2.addSearchToMovie(search);
        //movies.add(newMovie2);
        //genericService.addMovieListToMovieTable(
        //        movies,"name", "search1", Search.class);
        return "test";
    }


    /**
     * Show first view string.
     *
     * @return the string
     */
    @RequestMapping("/")
    public String showFirstView() {
        return "index";
    }
}





