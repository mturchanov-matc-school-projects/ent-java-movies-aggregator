package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.MovieReviewSource;
import com.movie_aggregator.entity.ReviewsSourcesLookup;
import com.movie_aggregator.entity.User;
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
public class ReviewSourcesLookupDaoTest extends AbstractTest{

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
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        ReviewsSourcesLookup lookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "name", "all_cinema_jp", ReviewsSourcesLookup.class);
        MovieReviewSource reviewSource = dao.get(MovieReviewSource.class, 534);
        MovieReviewSource reviewSource2 = dao.get(MovieReviewSource.class, 535);
        dao.deleteObject(reviewSource);
        dao.deleteObject(reviewSource2);
        dao.deleteObject(lookup);
        ReviewsSourcesLookup deletedLookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "name", "all_cinema_jp", ReviewsSourcesLookup.class);
        assertNull(deletedLookup);
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<ReviewsSourcesLookup> authorities = dao.getAll(ReviewsSourcesLookup.class);
        assertEquals(1, authorities.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        ReviewsSourcesLookup lookup = new ReviewsSourcesLookup();
        lookup.setName("movieReviewSoruce");
        lookup.setUrl("url");
        dao.saveObject(lookup);
        ReviewsSourcesLookup insertedLookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "name", "movieReviewSoruce", ReviewsSourcesLookup.class);
        System.out.println(insertedLookup);
        assertNotNull(insertedLookup);
        assertEquals(insertedLookup.getName(), lookup.getName());
    }

    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        ReviewsSourcesLookup lookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "name", "all_cinema_jp", ReviewsSourcesLookup.class);
        lookup.setFullName("updatedName");
        dao.saveOrUpdate(lookup);
        ReviewsSourcesLookup updatedlookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "fullName", "updatedName", ReviewsSourcesLookup.class);
        assertEquals(lookup.getFullName(),
                updatedlookup.getFullName());
    }






    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        ReviewsSourcesLookup lookup = dao.getFirstEntryBasedOnAnotherTableColumnProperty(
                "name", "all_cinema_jp", ReviewsSourcesLookup.class);
        assertEquals("all_cinema_jp", lookup.getName());
    }
}
