package com.movie_aggregator.controller;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.entity.User;
import com.movie_aggregator.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    //TODO: take somehow username if user is logged in -> put filtered movies to model
    @RequestMapping("/myMovies")
    public String getMyMovies(@RequestParam("searchVal") String searchVal, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Movie> userMovies;

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            //userMovies = genericService.
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
        }
        return "result";
    }

    @GetMapping(value = "/addMovie")
    public String addMovie(@RequestParam("movieId") int movieId, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        Movie movie = genericService.get(Movie.class, movieId);
        movie.setSearches(null);

        user.addMovieToUser(movie);
        genericService.merge(user);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
        //return "index";
    }





    //TODO:  add info-error if username is used already
    //TODO: add to view 'my_movies_list' ->
    //    1. configure MySecurityConfig - anonym_role cannot see link + cannot access
    //    2. admin/user can see ;imk and access
    //    + store somehow user in session to not login everytime  when access page with extra

    @RequestMapping(value = "/registrationProcessing", method = RequestMethod.POST)
    public String registrationProcessing(final @Valid @ModelAttribute("user") User user,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.hasErrors());
            return "/test";
        }

        int isSaved =  genericService.saveUser(user);
        return "/login";
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



        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }

        if (username != null) {
            List<Movie> userMovies = genericService.getMoviesByProperty("username", username, "users");
            for (Movie m : movies) {
                if (userMovies.contains(m)) {
                    m.setAddedToUserList(true);
                }
            }
        }




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


        //String searchVal = "Mor";
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


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        //User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        //Movie movie = genericService.get(Movie.class, 1209195);
        //movie.setSearches(null);
        //user.addMovieToUser(movie);
        //genericService.merge(user);
        //List<Movie> movies = genericService.getMoviesByToken(user.get);
        //model.addAttribute("movies", movies);



        //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
        //if (principal instanceof UserDetails) {
        //    String username = ((UserDetails)principal).getUsername();
        //    model.addAttribute("username", username);
        //} else {
        //    String username = principal.toString();
        //}
//
        //User user = genericService.getOneEntryByColumProperty("token", "1", User.class);
        //user.addMovieToUser(new Movie(111, "11", "11", "11"));
        //genericService.merge(user);
       // User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        List<Movie> movies = genericService.getMoviesByProperty("username", "2", "users");
        //List<Movie> movies = genericService.getMoviesByToken("1");

        model.addAttribute("movies", movies);
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





