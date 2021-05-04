package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Generic dao test.
 *
 * @author mturchanov
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MyConfig.class, MyWebInitializer.class})
public class MovieDaoTest extends AbstractTest{

    /**
     * The Dao.
     */
    @Autowired
    GenericDao dao;

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

    /**
     * Verify successful retrieval of a Author
     */
    @Test
    void getByIdSuccess() {
        Movie retrievedMovie = dao.get(Movie.class, -597990361	);
        String expectedName = "Stargate: Continuum";
        assertEquals(expectedName, retrievedMovie.getEngName());
    }

    /*
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        dao.delete(Movie.class, -597990361);
        assertNull(dao.get(Movie.class,-597990361));
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<Movie> movies = dao.getAll(Movie.class);
        assertEquals(5, movies.size());
    }

    @Test
    void getMoviesByProperty() {
        List<Movie> searchMovies = dao.getMoviesByProperty("name",
                "Redemption", "searches");
        assertEquals(2, searchMovies.size());
    }

    @Test
    void getMoviesBasedOnSearchName() {
        List<Movie> searchMovies = dao.getMoviesBasedOnSearchName("Redemption");
        assertEquals(2, searchMovies.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        Movie movie = new Movie(12, "title", "Movie", "tea", "year");
        int movieId = dao.save(movie);
        Movie insertedMovie = dao.get(Movie.class, movieId);
        assertNotNull(insertedMovie);
        assertEquals(insertedMovie.getEngName(), movie.getEngName());
    }

    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        Movie getMovie = dao.get(Movie.class, -597990361);
        getMovie.setEngName("modifiedTitle");
        dao.saveOrUpdate(getMovie);
        Movie updatedMovie = dao.get(Movie.class, -597990361);
        assertEquals("modifiedTitle", updatedMovie.getEngName());
    }






    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        Movie Movie = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("engName", "Undisputed III: Redemption", Movie.class);
        assertEquals(-1023705264, Movie.getId());
    }
}
