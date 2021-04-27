package com.movie_aggregator.controller;

import com.movie_aggregator.entity.*;
import com.movie_aggregator.service.GenericService;
import com.movie_aggregator.utils.MovieApisReader;
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
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

/**
 * The type My controller.
 *
 * @author mturchanov
 */
@Controller
public class MyController {

    @Autowired
    private GenericService genericService;
    @Autowired
    private MovieApisReader apisReader;



    //TODO: take somehow username if user is logged in -> put filtered movies to model
    @RequestMapping("/myMovies")
    public String getMyMovies(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Movie> userMovies;
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }

        userMovies = genericService.getMoviesByProperty("username", username, "users");
        for (Movie movie : userMovies) {
            movie.setAddedToUserList(true);
        }
        model.addAttribute("movies", userMovies);
        return "/result";
    }

    @GetMapping(value = "/addMovie")
    public String addMovie(@RequestParam("movieId") int movieId, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }

        User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        Movie movie = genericService.get(Movie.class, movieId);

        user.addMovieToUser(movie);
        genericService.merge(user);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @GetMapping(value = "/deleteMovie")
    public String deleteMovie(@RequestParam("movieId") int movieId, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }

        User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        Movie movie = genericService.get(Movie.class, movieId);
        user.removeMovieFromUser(movie);
        genericService.merge(user);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }





    //TODO:  add info-error if username is used already
    //TODO: add to view 'my_movies_list' ->
    //    1. configure MySecurityConfig - anonym_role cannot see link + cannot access
    //    2. admin/user can see ;imk and access
    //    + store somehow user in session to not login everytime  when access page with extra

    @RequestMapping(value = "/registrationProcessing", method = RequestMethod.POST)
    public String registrationProcessing(final @Valid @ModelAttribute("user") User newUser,
                                         final BindingResult bindingResult,
                                         final Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.hasErrors());
            System.out.println("existing username?");
            return "/test";
        }

        int isSaved =  genericService.saveUser(newUser);
        if (isSaved == 0) { // if no saved then such username already exists
            model.addAttribute("warning", "Such username already in use! Try again");
            return "redirect:/registrationProcessing";
        }

        return "redirect:/login";
    }

    /**
     * get-redirect for prg pattern
     *
     * @return default spring security login
     */
    @GetMapping("/login")
    public String login() {
        return "/login";
    }


        @GetMapping("/registrationProcessing")
    public String registrationProcessing(Model model, @ModelAttribute("warning") String warning) {
        model.addAttribute("user", new User());
        model.addAttribute("warning", warning);
        return "/sign-up";
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
            throws IOException, URISyntaxException {
        List<Movie> movies = genericService.getMovies(searchVal);
        if (movies == null) {
            model.addAttribute("headerTitle", String.format("Sorry, no result with '%s' input", searchVal));
            return "/index";
        }

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


    @RequestMapping("/uploadImages")
    public String loadImages( @RequestParam int id, Model model) throws IOException, URISyntaxException {
        //model.addAttribute("id", movieService.getMovie(id));
        Movie movie = genericService.get(Movie.class, id);
        movie = apisReader.loadFrames(movie);
        genericService.merge(movie);
        model.addAttribute("movie", movie);
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
    public String test(Model model) throws IOException, URISyntaxException {
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
        User user = genericService.getOneEntryByColumProperty("username", "2", User.class);
        ReviewsSourcesLookup reviewsSources = genericService.get(ReviewsSourcesLookup.class, 3);
        user.addReviewSourceToUser(reviewsSources);
        genericService.merge(user);
        //user.addMovieToUser(new Movie(111, "11", "11", "11"));
        //genericService.merge(user);
       // User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        //List<Movie> movies = genericService.getMoviesByProperty("username", "2", "users");
        //List<Movie> movies = genericService.getMoviesByToken("1");

      // Movie newMovie = new Movie();
       //Search search = new Search( 5,"test search1");
       //genericService.saveOrUpdate(search);

        //Movie newMovie = genericService.get(Movie.class, 326);
        //ReviewSource reviewSource = apisReader.parseJSONWikiDataReviewSources("326");
        //reviewSource.setMovie(newMovie);
        //newMovie.setReviewSources(reviewSource);
        //User user = genericService.get(User.class, )

        //ReviewsSources reviewsSource = genericService.get(ReviewsSources.class, 6);
        //MovieReviewSource movieReviewSource = apisReader.parseJSONWikiDataReviewSources(newMovie, reviewsSource);
        //newMovie.addReviewSourceToMovie(movieReviewSource);
        //System.out.println(reviewSource);
        //genericService.merge(newMovie);
        //Movie newMovie = new Movie();
        //newMovie.setName("testReviews2");
        //newMovie.setYear("testReviews2");
        //newMovie.setImage("testReviews2");
        //newMovie.setId(12376);


        //newMovie.addImageToMovie(new Image( newMovie, "image1"));
        //newMovie.addImageToMovie(new Image( newMovie,"image2"));
       //genericService.merge(newMovie);
        //List<Movie> movies = genericService.getMovies("Шурик");
        //System.out.println(movies.size());
//
        //for (Movie m : movies) {
        //    System.out.println(m);
        //}
        //ReviewSource reviewSource = apisReader.parseJSONWikiDataReviewSources("9714");
        //reviewSource.setMovie(newMovie);
        //newMovie.setReviewSources(reviewSource);

        //genericService.merge(newMovie);





       //Search search2 = new Search( 6,"test search2");
       //genericService.saveOrUpdate(search2);

       //Movie getMovie = genericService.getOneEntryByColumProperty("kinopoiskId",
       //        newMovie.getKinopoiskId(), Movie.class);
       //etMovie.addSearchToMovie(search2);
       //genericService.merge(getMovie);

       // model.addAttribute("movies", movies);
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





