package com.movie_aggregator.service;

import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.*;
import com.movie_aggregator.repository.GenericDao;
import com.movie_aggregator.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author mturchanov
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MyConfig.class, MyWebInitializer.class})
public class ServiceTest extends AbstractTest {
    @Autowired
    GenericService service;

    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * Run set up tasks before each test:
     * 1. execute sql which deletes everything from the table and inserts records)
     * 2. Create any objects needed in the tests
     */
    @BeforeEach
    void setUp() throws SQLException {
        Database database = Database.getInstance();

        database.runSQL("cleandb.sql");
    }
    @Test
    void saveUser() {
        User newUser = new User();
        newUser.setUsername("newUsername");
        newUser.setPassword("${noop}1");
        service.saveUser(newUser);
        User insertedUser = service.getOneEntryByColumProperty("username", "newUsername", User.class);
        assertEquals(newUser.getUsername(), insertedUser.getUsername());
    }

    @Test
    void generateNewPickedReviewSources() {
        String[] pickedRevs = new String[]{"all_cinema_jp"};
        ReviewsSourcesLookup expectedGeneratedLookup =
                service.getOneEntryByColumProperty("name",
                        "all_cinema_jp",
                        ReviewsSourcesLookup.class);

        Set<ReviewsSourcesLookup> lookups = service.generateNewPickedReviewSources(pickedRevs);
        assertTrue(lookups.contains(expectedGeneratedLookup));
    }

    @Test
    void getMoviesKinopoiskTest() {
        List<Movie> movies = service.getMovies("test", "kinopoisk");
        assertEquals(14, service.getAll(Movie.class).size());
        Search lastSearch = service.getLastSearch();
        assertEquals("test", lastSearch.getName());
        assertTrue(1 == lastSearch.getIsKinopoiskLaunched());


        List<Movie> existedmovies = service.getMovies("Redemption", "kinopoisk");
        assertEquals("Undisputed III: Redemption" ,existedmovies.get(0).getEngName());

        List<Movie> existedMovieWithDiffSource = service.getMovies("Redemption", "imdb");
        assertEquals(10 ,existedMovieWithDiffSource.size());
    }

    @Test
    void getMoviesImdbTest() {
        List<Movie> movies = service.getMovies("test", "imdb");
        assertEquals(15, service.getAll(Movie.class).size());
        Search lastSearch = service.getLastSearch();
        assertEquals("test", lastSearch.getName());
        assertEquals(1, (int) lastSearch.getIsOmdbLaunched());
    }

    @Test
    void getMovieReviewSourcesForView() {
        String[] picked = new String[]{"all_cinema_jp"};
        Set<ReviewsSourcesLookup> pickedRevs = service.generateNewPickedReviewSources(picked);
        Movie movie = service.get(Movie.class, -1023705264);
        Set<ReviewsSourcesLookup> result = service.getMovieReviewSourcesForView(pickedRevs, movie);
        ReviewsSourcesLookup rev = result.iterator().next();
        assertEquals("testUrl", rev.getUrl());
    }

}
