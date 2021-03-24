package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.entity.User;
import com.movie_aggregator.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
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

    @GetMapping("/registrationProcessing")
    public String registrationProcessing(Model model) {
        model.addAttribute("user", new User());
        return "/sign-up";
    }

    @RequestMapping(value = "/registrationProcessing", method = RequestMethod.POST)
    public String registrationProcessing(final @Valid @ModelAttribute("user") User user,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.hasErrors());
            return "/test";
        }
        user.setEnabled(0);

        genericService.save(user);
        return "/index";
    }
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
        System.out.println(movies);
        model.addAttribute("movies", movies);
        return "/result";
    }


    /**
     * Gets movie info.
     *
     * @param model the model
     * @return the movie info
     */
    @RequestMapping("/movie")
    public String getMovieInfo( @RequestParam int id, Model model) {
        //model.addAttribute("movie", movieService.getMovie(id));
        model.addAttribute("movie", genericService.get(Movie.class, id));

        return "/movie";
    }

    /**
     * Test string.
     *
     * @param model the model
     * @return the string
     */
//Testing time
    @RequestMapping("/test")
    public String test(Model model) throws IOException {
        //Movie newMovie = new Movie();
        //Search search = new Search( 14,"search11212");
        //newMovie.setName("m4 - 2 movied added as a list");
        //newMovie.setId(1234);
        //newMovie.setImage("https://ichef.bbci.co.uk/news/976/cpsprodpb/12A9B/production/_111434467_gettyimages-1143489763.jpg");
        //newMovie.addSearchToMovie(search);
        //genericService.save(newMovie);


        //String searchVal = "Django";
        //Search existedSearch = genericService.getOneEntryByColumProperty("name", searchVal, Search.class);
        //List<Movie> movies = genericService.getMovies(existedSearch, searchVal);
        //model.addAttribute("movies", movies);

        //User user = new User();
        //user.setPassword("123");
        //user.setUsername("My new user with authority added by cascade");
        //Authority authority = new Authority();
        //authority.setUsername(user.getUsername());
        //authority.setAuthority("ROLE_USER");
        //user.addAuthorityToUser(authority);
        //genericService.saveOrUpdate(user);
        return "/test";
    }


    /**
     * Show first view string.
     *
     * @return the string
     */
    @RequestMapping("/")
    public String showFirstView() {
        return "/index.html";
    }
}





