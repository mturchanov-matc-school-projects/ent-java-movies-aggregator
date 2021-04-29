package com.movie_aggregator.controller;

import com.movie_aggregator.entity.*;
import com.movie_aggregator.service.GenericService;
import com.movie_aggregator.utils.MovieApisReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
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
                              @RequestParam(name = "reviewSources", required = false) String[] reviewSources,
                              @RequestParam("searchVal") String searchVal, Model model, HttpServletRequest request) {

        searchVal = searchVal.toLowerCase(Locale.ROOT);
        List<Movie> movies = genericService.getMovies(searchVal, movieSourceBase);
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


        HttpSession session = request.getSession();
        session.setAttribute("reviewsSources", reviewSources);
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
        Set<ReviewsSourcesLookup> lookups = null;
        HttpSession session = request.getSession();
        String[] reviewSources = (String[]) session.getAttribute("reviewsSources");

        Movie movie = genericService.get(Movie.class, id);
        System.out.println("before: " + movie.getId());

        if (movieSourceBase.equals("kinopoisk") && movie.getKinopoiskReviews() == null) {
            movie = apisReader.parseSpecificKinopoiskMoviesJson(movie);
        } else if (movieSourceBase.equals("imdb") && movie.getWriter() == null) {
            movie = apisReader.parseSpecificImdbMovieJson(movie);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
        }
        Set<ReviewsSourcesLookup> reviewSourcesForView = null;
        if (reviewSources.length > 0) {
            lookups = new HashSet<>();
            for (String checkboxRev : reviewSources) {
                ReviewsSourcesLookup reviewsSourcesLookup =
                        genericService.getOneEntryByColumProperty("name", checkboxRev, ReviewsSourcesLookup.class);
                lookups.add(reviewsSourcesLookup);
            }
            reviewSourcesForView =  genericService.getMovieReviewSourcesForView(lookups, movie);
            System.out.println(reviewSourcesForView);
        }

        model.addAttribute("movie", movie);
        model.addAttribute("reviewSources", reviewSourcesForView);

        if (movieSourceBase.equals("kinopoisk")) {
            return "/kin_movie";
        }

        return "/movie";
    }


    @RequestMapping("/uploadImages")
    public String loadImages( @RequestParam int id, Model model)  {
        //model.addAttribute("id", movieService.getMovie(id));
        Movie movie = genericService.get(Movie.class, id);
        movie = apisReader.loadFrames(movie);
        genericService.merge(movie);
        model.addAttribute("movie", movie);
        return "/movie";
    }

    @RequestMapping("/showReviewSources")
    public String showReviewSources( Model model)  {
        List<ReviewsSourcesLookup> reviewsSourcesLookups =  genericService.getAll(ReviewsSourcesLookup.class);
        model.addAttribute("reviewsSources", reviewsSourcesLookups);
        return "/index";
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

        return "/test";
    }
}





