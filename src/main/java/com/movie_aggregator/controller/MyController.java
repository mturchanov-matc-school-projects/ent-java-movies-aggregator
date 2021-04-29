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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
                              //@RequestParam(name = "reviewSources") String[] reviewSources,
                              @RequestParam("searchVal") String searchVal, Model model) {
        System.out.println(movieSourceBase);
        //for (String s : reviewSources) {
        //    System.out.println(s);
        //}
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


         //HttpSession session = request.getSession();
        //model.addAttribute("reviewsSources", reviewSources);

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
                                 Model model) {
        //model.addAttribute("movie", movieService.getMovie(id));
        Movie movie = genericService.get(Movie.class, id);
        //ReviewSource reviewSource = apisReader.parseJSONWikiDataReviewSources(movie.getImdbId());
        // check if api specific-movie-request was launched before
        if (movieSourceBase.equals("kinopoisk") && movie.getKinopoiskReviews() == null) {
            movie = apisReader.parseSpecificKinopoiskMoviesJson(movie);
        } else if (movieSourceBase.equals("imdb") && movie.getWriter() == null) {
            movie = apisReader.parseSpecificImdbMovieJson(movie);
        }
        model.addAttribute("movie", movie);
        //model.addAttribute("reviewSource", reviewSource);
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
        //User user = genericService.getOneEntryByColumProperty("username", "2", User.class);
        //ReviewsSourcesLookup lookup = genericService.get(ReviewsSourcesLookup.class, 4);
        Movie movie = genericService.get(Movie.class, -1778224323);
        Set<ReviewsSourcesLookup> reviewsSourcesLookups = new HashSet<>(genericService.getAll(ReviewsSourcesLookup.class));
        //String sparqlResponse = "{ \"head\": { \"vars\": [ \"film\", \"film_web_id_pl\", \"film_web_name_pl\", \"all_cinema_jp\", \"allcine_fr\", \"cine_gr\", \"cinema_de\", \"common_sense\", \"eiga_jp\", \"film_affinity\", \"filmfront_no\", \"film_tv_it\", \"google_play_tv\", \"kinenote_jp\", \"kvikmyndir_is\", \"ldif_de\", \"letterbox\", \"metacritic\", \"mrqe\", \"movie_walker_jp\", \"moviemeter_nl\", \"movies_anywhere\", \"mubi\", \"mymovies_it\", \"netflix\", \"port_hu\", \"quora_topic\", \"rotten_tomatoes\", \"skope_dk\", \"sratim_il\", \"tmdb\", \"tv_com\", \"anidb\", \"anime_news_newtwork\", \"anime_click\", \"imfdb\", \"mal\", \"trakt_tv\", \"anilist\" ] }, \"results\": { \"bindings\": [ { \"film\": { \"type\": \"uri\", \"value\": \"http://www.wikidata.org/entity/Q131074\" }, \"all_cinema_jp\": { \"type\": \"literal\", \"value\": \"241899\" }, \"allcine_fr\": { \"type\": \"literal\", \"value\": \"39187\" }, \"cine_gr\": { \"type\": \"literal\", \"value\": \"703026\" }, \"cinema_de\": { \"type\": \"literal\", \"value\": \"1296738\" }, \"film_affinity\": { \"type\": \"literal\", \"value\": \"226427\" }, \"filmfront_no\": { \"type\": \"literal\", \"value\": \"1441\" }, \"ldif_de\": { \"type\": \"literal\", \"value\": \"521596\" }, \"netflix\": { \"type\": \"literal\", \"value\": \"60004484\" }, \"movies_anywhere\": { \"type\": \"literal\", \"value\": \"the-lord-of-the-rings-the-return-of-the-king\" }, \"moviemeter_nl\": { \"type\": \"literal\", \"value\": \"3934\" }, \"movie_walker_jp\": { \"type\": \"literal\", \"value\": \"3934\" }, \"mrqe\": { \"type\": \"literal\", \"value\": \"the-lord-of-the-rings-the-return-of-the-king-m100021048\" }, \"metacritic\": { \"type\": \"literal\", \"value\": \"movie/the-lord-of-the-rings-the-return-of-the-king\" }, \"letterbox\": { \"type\": \"literal\", \"value\": \"the-lord-of-the-rings-the-return-of-the-king\" }, \"port_hu\": { \"type\": \"literal\", \"value\": \"57193\" }, \"rotten_tomatoes\": { \"type\": \"literal\", \"value\": \"m/the_lord_of_the_rings_the_return_of_the_king\" }, \"tmdb\": { \"type\": \"literal\", \"value\": \"122\" }, \"tv_com\": { \"type\": \"literal\", \"value\": \"movies/the-lord-of-the-rings-the-return-of-the-king\" }, \"google_play_tv\": { \"type\": \"literal\", \"value\": \"SEUOCKcqXsM\" }, \"kinenote_jp\": { \"type\": \"literal\", \"value\": \"36968\" }, \"film_web_name_pl\": { \"type\": \"literal\", \"value\": \"Powrot.Krola\" } } ] } }";
        //sparqlResponse = sparqlResponse.replaceAll("[\n\\]]", "")
        //        .replaceAll(".+: \\[", "");
        //sparqlResponse = sparqlResponse.substring(0, sparqlResponse.length() - 2);
        //MovieReviewSource movieReviewSource = genericService.getMovieReviewSourceBasedOnColumns(movie, lookup, sparqlResponse);
        //System.out.println(movieReviewSource);

        Set <ReviewsSourcesLookup>asd = new HashSet<>();
        Set<MovieReviewSource> reviewSources = apisReader.parseJSONWikiDataReviewSources(movie, reviewsSourcesLookups);
        for (MovieReviewSource reviewSource : reviewSources); {
            System.out.println(reviewSources);
        }
        return "/test";
    }
}





