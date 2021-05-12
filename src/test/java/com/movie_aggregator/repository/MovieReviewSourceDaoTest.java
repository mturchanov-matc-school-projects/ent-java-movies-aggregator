package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Image;
import com.movie_aggregator.entity.MovieReviewSource;
import com.movie_aggregator.entity.ReviewsSourcesLookup;
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
public class MovieReviewSourceDaoTest extends AbstractTest{

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
        MovieReviewSource reviewSource = dao.get(MovieReviewSource.class, 534);
        String expectedUrl = "testUrl";
        assertEquals(expectedUrl, reviewSource.getUrl());
    }

    ///**
    // * Verify successful delete of user
    // */
    //@Test
    //void deleteSuccess() {
    //    dao.delete(MovieReviewSource.class, 534);
    //    assertNull(dao.get(MovieReviewSource.class,534));
    //}

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<MovieReviewSource> reviewSources = dao.getAll(MovieReviewSource.class);
        assertEquals(3, reviewSources.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        MovieReviewSource movieReviewSource = new MovieReviewSource();
        movieReviewSource.setUrl("finalUrl");
        Movie movie = dao.get(Movie.class, -91925579	);
        ReviewsSourcesLookup lookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "name", "all_cinema_jp", ReviewsSourcesLookup.class);
        movieReviewSource.setReviewSource(lookup);
        movieReviewSource.setMovie(movie);;

        int movieReviewId = dao.save(movieReviewSource);
        MovieReviewSource insertedMovieRev = dao.get(MovieReviewSource.class, movieReviewId);
        assertNotNull(insertedMovieRev);
        assertEquals(insertedMovieRev.getUrl(), movieReviewSource.getUrl());
    }



    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        MovieReviewSource movieRev = dao.get(MovieReviewSource.class, 535);
        movieRev.setUrl("updatedUrl");
        dao.saveOrUpdate(movieRev);
        MovieReviewSource updatedMovieRev = dao.get(MovieReviewSource.class, 535);
        assertEquals(updatedMovieRev.getUrl(), movieRev.getUrl());
    }

    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        MovieReviewSource movieRev = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("id", "535", MovieReviewSource.class);
        assertEquals(535, movieRev.getId());
    }
}
