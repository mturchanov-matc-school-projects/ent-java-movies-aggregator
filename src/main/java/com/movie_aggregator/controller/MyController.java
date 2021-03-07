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
       // List<Movie> isSearchValInDB = genericService.getAllByColumProperty("name", searchVal, Search.class);
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
        //List<Search> searches = genericService.getAllByColumProperty("name", "test", Search.class);
        List<Movie> movies = new ArrayList<>();
        Movie newMovie = new Movie();
        newMovie.setName("newMovieThatCanBeFoundByTest2SearchVal");
        newMovie.setImage("testImage");
        Search search = new Search("test2");
        newMovie.addSearchToMovie(search);
        movies.add(newMovie);

        genericService.addEntityListToTableIfOtherTableHasntSpecifiedColumnProperty(       movies,
                "name", "test2", Search.class);

       // Movie m1 = new Movie();
       // m1.setName("1234");
       // m1.setImage("asdd4");
       // Search search = new Search("asd4");
       // m1.addSearchToMovie(search);
        //genericService.save(m1);
        return "test";
    }


    @RequestMapping("/")
    public String showFirstView() {
        return "index";
    }
}





