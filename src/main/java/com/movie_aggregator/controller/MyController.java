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
        List<Movie> userMovies;
        User user = getUser();
        userMovies = genericService.getMoviesByProperty("username", user.getUsername(), "users");
        for (Movie movie : userMovies) {
            movie.setAddedToUserList(true);
        }
        for (Movie m : userMovies) {
            System.out.println(m.getImdbPoster());
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
        return "redirect:"+ referer;
    }

    private User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        }
        User user = genericService.getOneEntryByColumProperty("username", username, User.class);
        return user;
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
        return "redirect:"+ referer;
    }

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
    public String searchMovie(@RequestParam(name = "movieSourceBase") String movieSourceBase,
                              @RequestParam(name = "reviewsSources", required = false) String[] reviewsSources,
                              @RequestParam("searchVal") String searchVal, Model model, HttpServletRequest request) {

        searchVal = searchVal.toLowerCase(Locale.ROOT);
        HttpSession session = request.getSession();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
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
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            user = genericService.getOneEntryByColumProperty("username", username, User.class);
        }


        if (user != null && !newUserPickedReviews.isEmpty()) { //user's reviewsSources are updated
            user.setReviewsSources(newUserPickedReviews);
            genericService.merge(user);
        } else {
            user = new User("anonymous", newUserPickedReviews);
            System.out.println("anon set: " + newUserPickedReviews);
        }

        session.setAttribute("user", user);


        // this and mapping('/addMovie') are connected
        // particularly this flag is for view (when btn 'add' is clicked, next time no btn 'add' as option)
        if (!user.getUsername().equals("anonymous")) {
            List<Movie> userMovies = genericService.getMoviesByProperty("username", user.getUsername(), "users");
            for (Movie m : movies) {
                if (userMovies.contains(m)) { // if user has movie in list then no btn for view)
                    m.setAddedToUserList(true);
                }
            }
        }

        model.addAttribute("resultTitle", "Found movies");
        model.addAttribute("movieSourceBase", movieSourceBase);
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
    public String getMovieInfo( @RequestParam int id, @RequestParam String movieSourceBase,
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
            reviewSourcesForView =  genericService.getMovieReviewSourcesForView(userReviews, movie);
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
    public String loadImages( @RequestParam int id, Model model)  {
        Movie movie = genericService.get(Movie.class, id);
        movie = apisReader.loadFrames(movie);
        genericService.merge(movie);
        model.addAttribute("movie", movie);
        return "/movie";
    }

    @RequestMapping(value={"/home", "/index", "/",})
    public String showReviewSources( Model model, HttpServletRequest request)  {
        List<ReviewsSourcesLookup> reviewsSourcesLookups =  genericService.getAll(ReviewsSourcesLookup.class);
        HttpSession session = request.getSession();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) session.getAttribute("user");
        if (user != null && !user.getReviewsSources().isEmpty()) { // multiuse + some view reviews were checked
            setPickedReviewSources(model, reviewsSourcesLookups, user);
            System.out.println(user.getReviewsSources());
        } else if (principal instanceof UserDetails) { //first time index with checked checkboxes based on user
            String username = ((UserDetails)principal).getUsername();
            user = genericService.getOneEntryByColumProperty("username", username, User.class);
            setPickedReviewSources(model, reviewsSourcesLookups, user);
        }
        List<Search> topSearches = genericService.getMostRecentSearches();
        Map<String, Object> topReviewSources = genericService.getCountForEachReviewSource();
        model.addAttribute("allReviewSources", reviewsSourcesLookups);
        session.setAttribute("topSearches", topSearches); //init topSearches
        session.setAttribute("topRevs", topReviewSources); //init topReviewSources
        System.out.println(topReviewSources);
        return "/index";
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



    //@RequestMapping(value={"/home", "/index", "/",})
   // String indexMultipleMapping(){
//
    //    return "index";
   // }

    /**
     * Test string.
     *
     * @param model the model
     * @return the string
     */
//Testing time
    @RequestMapping("/test")
    public String test(Model model) throws IOException, URISyntaxException {


       // ReviewsSourcesLookup lookup = genericService.getOneEntryByColumProperty("name", "all_cinema_jp", ReviewsSourcesLookup.class);
        //Movie m1 = genericService.get(Movie.class, -1959952368);
        //MovieReviewSource movieReviewSource = new MovieReviewSource(lookup, m1, "testUrl");
        //Set<MovieReviewSource> set =new HashSet<>();
        //set.add(movieReviewSource);
        //m1.setMovieReviewSources(set);
        //genericService.merge(m1);

        //User user = genericService.getOneEntryByColumProperty("username", "11", User.class);
        //user.addReviewSourceToUser(lookup);
        genericService.getCountForEachReviewSource();

        return "/test";
    }
}





