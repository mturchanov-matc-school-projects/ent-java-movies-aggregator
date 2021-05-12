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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller where
 * all application's endpoints are provided
 *
 * @author mturchanov
 */
@Controller
public class MyController {

    @Autowired
    private GenericService genericService;
    @Autowired
    private MovieApisReader apisReader;
    private final Logger logger = LogManager.getLogger(this.getClass());



    @RequestMapping("/myMovies")
    public String getMyMovies(Model model) {
        List<Movie> userMovies;
        User user = getUser();
        userMovies = genericService.getMoviesByProperty("username", user.getUsername(), "users");
        for (Movie movie : userMovies) {
            movie.setAddedToUserList(true);
        }
        String resultTitle = String.format("%s's movies", user.getUsername());
        model.addAttribute("resultTitle", resultTitle);
        model.addAttribute("movies", userMovies);
        return "/result";
    }

    @GetMapping(value = "/addMovie")
    public String addMovie(@RequestParam("movieId") int movieId, HttpServletRequest request) {
        User user = getUser();
        Movie movie = genericService.get(Movie.class, movieId);

        user.addMovieToUser(movie);
        genericService.merge(user);

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        return genericService.getOneEntryByColumProperty("username", username, User.class);
    }

    @GetMapping(value = "/deleteMovie")
    public String deleteMovie(@RequestParam("movieId") int movieId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        //User user = (User) session.getAttribute("user");
        User user = getUser();
        Movie movie = genericService.get(Movie.class, movieId);
        user.removeMovieFromUser(movie);
        genericService.merge(user);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/registrationProcessing", method = RequestMethod.POST)
    public String registrationProcessing(final @Valid @ModelAttribute("user") User newUser,
                                         final BindingResult bindingResult,
                                         final Model model) {

        int isSaved = genericService.saveUser(newUser);
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
    public String searchMovie(@RequestParam(name = "movieSourceBase") String movieSourceBase,
                              @RequestParam(name = "reviewsSources", required = false) String[] reviewsSources,
                              @RequestParam("searchVal") String searchVal, Model model, HttpServletRequest request) {

        searchVal = searchVal.toLowerCase(Locale.ROOT);
        HttpSession session = request.getSession();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = getUser();
        Set<ReviewsSourcesLookup> newUserPickedReviews = new HashSet<>();

        //no results found with such search word(s)
        List<Movie> movies = genericService.getMovies(searchVal, movieSourceBase);
        if (movies == null) {
            model.addAttribute("headerTitle", String.format("Sorry, no result with '%s' input", searchVal));
            return "/index";
        }


        if (reviewsSources != null && reviewsSources.length > 0) { // new reviews sources were checked
            newUserPickedReviews = genericService.generateNewPickedReviewSources(reviewsSources);
        }


        if (user != null && !newUserPickedReviews.isEmpty()) { //user's reviewsSources are updated
            user.setReviewsSources(newUserPickedReviews);
            genericService.merge(user);
        } else {
            user = new User("anonymous", newUserPickedReviews);
            logger.info("anon set: " + newUserPickedReviews);
        }

        session.setAttribute("user", user);


        // this and mapping('/addMovie') are connected
        // particularly this flag is for view (when btn 'add' is clicked, next time no btn 'add' as option)
        List<Movie> userMovies;
        User user1 = getUser();
        if (user1 != null) {
            userMovies = genericService.getMoviesByProperty("username", user1.getUsername(), "users");
            for (Movie m : movies) {
                if (userMovies.contains(m)) { // if user has movie in list then no btn for view)
                    logger.info("m.setAddedToUserList(true);");
                    m.setAddedToUserList(true);
                }
            }
        }


        model.addAttribute("resultTitle", "Found movies");
        model.addAttribute("movieSourceBase", movieSourceBase);
        model.addAttribute("movies", movies);
        if(movieSourceBase == "kinopoisk") {
            return "kin_result";
        }
        return "/result";
    }


    /**
     * Gets movie info.
     *
     * @param model the model
     * @return the movie info
     */
    @RequestMapping("/movie")
    public String getMovieInfo(@RequestParam int id, @RequestParam String movieSourceBase,
                               HttpServletRequest request,
                               Model model) {
        HttpSession session = request.getSession();


        Movie movie = genericService.get(Movie.class, id);
        if (movieSourceBase.equals("kinopoisk") && movie.getKinopoiskReviews() == null) {
            movie = apisReader.parseSpecificKinopoiskMoviesJson(movie);
            genericService.merge(movie);
        } else if (movieSourceBase.equals("imdb") && movie.getWriter() == null) {
            movie = apisReader.parseSpecificImdbMovieJson(movie);
            genericService.merge(movie);
        }


        User user = (User) session.getAttribute("user");
        Set<ReviewsSourcesLookup> userReviews = user.getReviewsSources();

        Set<ReviewsSourcesLookup> reviewSourcesForView = null;
        if (userReviews != null && userReviews.size() > 0) {
            reviewSourcesForView = genericService.getMovieReviewSourcesForView(userReviews, movie);
        }
        model.addAttribute("movie", movie);
        //session preferred because if /uploadImages(reload of the page) all binded data will disappear
        //    images data are too long to rethrow them via model + it just doesn't worth it
        session.setAttribute("reviewsSources", reviewSourcesForView);

        if (movieSourceBase.equals("kinopoisk")) {
            return "/kin_movie";
        }

        return "/movie";
    }


    @RequestMapping("/uploadImages")
    public String loadImages(@RequestParam int id, Model model) {
        Movie movie = genericService.get(Movie.class, id);
        movie = apisReader.loadFrames(movie);
        genericService.merge(movie);
        model.addAttribute("movie", movie);
        return "/movie";
    }

    @RequestMapping("/home")
    public String showReviewSources(Model model, HttpServletRequest request) {
        List<ReviewsSourcesLookup> reviewsSourcesLookups = genericService.getAll(ReviewsSourcesLookup.class);
        HttpSession session = request.getSession();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) session.getAttribute("user");
        if (user != null && !user.getReviewsSources().isEmpty()) { // multiuse + some view reviews were checked
            setPickedReviewSources(model, reviewsSourcesLookups, user);
            logger.info(user.getReviewsSources());
        } else if (principal instanceof UserDetails) { //first time index with checked checkboxes based on user
            String username = ((UserDetails) principal).getUsername();
            user = genericService.getOneEntryByColumProperty("username", username, User.class);
            setPickedReviewSources(model, reviewsSourcesLookups, user);
        }
        List<Search> topSearches = genericService.getMostRecentSearches();
        Map<String, Integer> topReviewSources = genericService.getCountForEachReviewSource();
        model.addAttribute("allReviewSources", reviewsSourcesLookups);
        session.setAttribute("topSearches", topSearches); //init topSearches
        session.setAttribute("topRevs", topReviewSources); //init topReviewSources
        logger.info(topReviewSources);
        logger.info("test /home");
        return "revs";
    }

    private void setPickedReviewSources(Model model, List<ReviewsSourcesLookup> reviewsSourcesLookups, User user) {
        Set<ReviewsSourcesLookup> chosenLookups = user.getReviewsSources();
        for (ReviewsSourcesLookup lookup : reviewsSourcesLookups) {
            if (chosenLookups.contains(lookup)) {
                lookup.setChecked(true);
            }
        }
        model.addAttribute("reviewsSources2", chosenLookups);
    }

    /**
     * Test string.
     *
     * @param model the model
     * @return the string
     */
//Testing time
//    @RequestMapping("/test")
//    public String test(Model model) throws IOException, URISyntaxException {
//        genericService.getCountForEachReviewSource();
//
//        return "/test";
//    }
}




