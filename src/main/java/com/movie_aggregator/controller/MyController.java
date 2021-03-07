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
    private MovieService movieService;

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GenericService genericService;;


    @RequestMapping("/searchMovie")
    public String getHomePage(@RequestParam("searchVal") String searchVal, Model model)
            throws IOException {
//        ImdbApiReader apiReader = new ImdbApiReader();
        //MovieApiReader reader = new MovieApiReader();
        MovieApisReader reader = new MovieApisReader();

//        String JSONMovies = apiReader.getResults("SearchMovie",searchVal, null);
//        List<Movie> imdbMovies = apiReader.parseJSONMovies(JSONMovies);
//        List<Movie> kinopoiskMovies = apiReader.parseKinopoiskSearchHtml(searchVal);
//        List<Movie> movies = apiReader.getMovieListFromApis(searchVal);
        //List<Movie> movies = reader.getMovieListFromApis(searchVal);
        List<Movie> movies = reader.parseJSONKinopoiskMovies(searchVal);
        //for (Movie m : movies) {
        //    movieService.saveMovie(m);
        //}

        //System.out.println(movieService.getColumnProperties("imdb_rating"));
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
        //Movie m1 = new Movie();
        //m1.setName("123");
        //m1.setImage("asdd");
        //Search search = new Search("asd");
        //m1.addSearchToMovie(search);
        //movieService.delete(new Movie(), 27);
        //model.addAttribute("movie", m1);
        //movieServiceImpl.saveMovie(m1);
        //movieServiceImpl.deleteMovie(26);
        genericService.delete(Movie.class, 29);
        return "test";
    }


    @RequestMapping("/")
    public String showFirstView() {
        return "index";
    }
}





