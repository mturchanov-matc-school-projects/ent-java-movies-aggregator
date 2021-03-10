package com.movie_aggregator.repository;

import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import com.movie_aggregator.service.GenericService;
import com.movie_aggregator.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mturchanov
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MyConfig.class, MyWebInitializer.class})
public class GenericDaoTest extends AbstractTest {
    @Autowired
    GenericDao dao;

    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Run set up tasks before each test:
     * 1. execute sql which deletes everything from the table and inserts records)
     * 2. Create any objects needed in the tests
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    /**
     * Verify successful retrieval of a Author
     */
    @Test
    void getByIdSuccess() {
        Search retrievedSearch = dao.get(Search.class, 1);
        logger.info("TESTTEST");
        assertEquals("Django", retrievedSearch.getName());
    }

    /**
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        dao.delete(Movie.class, 230260);
        assertNull(dao.get(Movie.class,230260));
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<Movie> movies = dao.getAll(Movie.class);
        assertEquals(12, movies.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        Movie newMovie = new Movie(20, "TestName", "image:url", "1923");
        Search newSearch = new Search(20, "new Serch val");
        newMovie.addSearchToMovie(newSearch);
        int searchId = dao.save(newSearch);
        int movieId = dao.save(newMovie);
        assertNotEquals(0,searchId);
        assertNotEquals(0,movieId);
        Movie insertedMovie = dao.get(Movie.class, movieId);
        assertNotNull(insertedMovie);
        assertEquals(insertedMovie.getId(), newMovie.getId());
    }

    @Test
    void saveOrUpdateTest() {
        Movie getMovie = dao.get(Movie.class, 77394);
        getMovie.setName("modifiedName");
        dao.saveOrUpdate(getMovie);
        Movie updatedMovie = dao.get(Movie.class, 77394);
        assertEquals("modifiedName", updatedMovie.getName());
    }

    @Test
    void getLastSearchTest() {
        Search newSearch = new Search(20, "new Serch val");
        dao.save(newSearch);
        Search lastSearch = dao.getLastSearch();
        assertEquals(lastSearch, newSearch);
    }

    @Test
    public void incrementSearchNumberCounterTest() {
        Search search = dao.getLastSearch();
        int expectedSearchNumber = search.getNumber() + 1;
        dao.incrementSearchNumberCounter(search.getId());
        Search updatedSearch = dao.get(Search.class, search.getId());
        assertEquals(expectedSearchNumber, updatedSearch.getNumber());
    }

    @Test
    void getMoviesBasedOnSearchNameTest() {
        List<Movie> movies = dao.getMoviesBasedOnSearchName("Django");
        assertEquals(12, movies.size());
    }

    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        Movie movie = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("name", "Django", Movie.class);
        assertEquals(77394, movie.getId());
    }
}
