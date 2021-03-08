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
    private GenericService genericService;

    //@Autowired
    private MovieApisReader reader;


    @RequestMapping("/searchMovie")
    public String searchMovie(@RequestParam("searchVal") String searchVal, Model model)
            throws IOException {
        MovieApisReader reader = new MovieApisReader();

        //TODO: Need fix - for addMovieListToMovieTable()
        // Broken order: select search... -> select movie... -> update movie...
        // Should be: select search... -> select movie...
        //      -> insert movies... -> insert search... -> insert movie_search...
        // Note: when calling addMovieListToMovieTable() in MyController.test
        //      it is working. Maybe try to add movies with full details?
        // Exception: org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect) : [com.movie_aggregator.entity.Movie#77394]

        List<Movie> movies = reader.parseJSONKinopoiskMovies(searchVal);
        //Search existedSearch = genericService.getOneEntryByColumProperty("name", searchVal, Search.class);


        //System.out.println("Controller getHomePage()'s movies: " + movies);
        genericService.addMovieListToMovieTable(movies, "name", searchVal, Search.class);
        //System.out.printf("is added? - %d%n", isAddedToTable);


        //List<Movie> movies = new ArrayList<>();
        //Movie newMovie = new Movie();
        //newMovie.setName("newMovieThatCanBeFoundByTest2SearchVal");
        //newMovie.setImage("testImage");
        //Search search = new Search("test2");
        //newMovie.addSearchToMovie(search);
        //movies.add(newMovie);
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

    //Testing time
    @RequestMapping("/test")
    public String test(Model model) {
        //Search searches = genericService.getOneEntryByColumProperty("name", "test", Search.class);
        //List<Movie> movies = new ArrayList<>();
        //Movie newMovie = new Movie();
        //newMovie.setName("m1 - 2 movied added as a list");
        //newMovie.setImage("https://ichef.bbci.co.uk/news/976/cpsprodpb/12A9B/production/_111434467_gettyimages-1143489763.jpg");
        //newMovie.setId(17);
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



        //causes the error
        // problem search without identity + no id causes err
        //     and search with identity + id causes err
        Movie m1 = new Movie(  6,"Name4", "Image4", "YEAR4");
        Search search = new Search(1, "searchWithID4"); //this line for some reason causes it
       // search.addMovieToSearch(m1);
        m1.addSearchToMovie(search);
        //System.out.println(search.getMovies());
        //ystem.out.printf("m'searches: %s, m - %s\n",  m1.getSearches(), m1);
        genericService.save(m1);






        //Search s1 = new Search("testSearchWithoutID");
       // s1.setId(105);
        //System.out.println(s1);
        //genericService.save(s1);
        //System.out.println(s1);
        //model.addAttribute("movie", search);


        //TODO: Need fix - when movie is updated wrong behaviour
        // Broken order: updated movie inserted as new entity
        // -> old movie entry without update(s) is deleted from `movies`
        // -> old movie entry is deleted from `movie_search`

        //Movie getMovie = genericService.get(Movie.class, 46);
        //getMovie.setImdbRating("3.66");
        //System.out.println("MyControler.test.getMovie.Entity before save: " + getMovie);

        //genericService.save(getMovie);
        //System.out.println("MyControler.test.getMovie.Entity after save: " + getMovie);


        //Search search = genericService.getOneEntryByColumProperty("name", "test", Search.class);
        //model.addAttribute("getMovie", getMovie);

        return "test";
    }


    @RequestMapping("/")
    public String showFirstView() {
        return "index";
    }
}





