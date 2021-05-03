package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
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
public class SearchDaoTest extends AbstractTest{

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
        Search retrievedSearch = dao.get(Search.class, 1);
        Search expectedSearch = new Search("Pink Floyd");
        assertEquals(expectedSearch, retrievedSearch);
    }

    /**
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        dao.delete(Search.class, 47);
        assertNull(dao.get(Search.class,47));
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<Search> searches = dao.getAll(Search.class);
        assertEquals(3, searches.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        Search newSearch = new Search( "new Serch val");
        int searchId = dao.save(newSearch);
        System.out.println(searchId);
        Search insertedSearch = dao.get(Search.class, searchId);
        System.out.println(insertedSearch);
        assertNotNull(insertedSearch);
        assertEquals(insertedSearch, newSearch);
    }

    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        Search getSearch = dao.get(Search.class, 47);
        getSearch.setName("modifiedName");
        dao.saveOrUpdate(getSearch);
        Search updatedSearch = dao.get(Search.class, 47);
        assertEquals("modifiedName", updatedSearch.getName());
    }

    /**
     * Verify Geting last search test.
     */
    @Test
    void getLastSearchTest() {
        Search newSearch = new Search(20, "new Serch val");
        dao.save(newSearch);
        Search lastSearch = dao.getLastSearch();
        assertEquals(lastSearch, newSearch);
    }

    /**
     * Verify Incrementing search number counter test.
     */
    @Test
    public void incrementSearchNumberCounterTest() {
        Search search = dao.getLastSearch();
        int expectedSearchNumber = search.getNumber() + 1;
        dao.incrementSearchNumberCounter(search.getId());
        Search updatedSearch = dao.get(Search.class, search.getId());
        assertEquals(expectedSearchNumber, updatedSearch.getNumber());
    }


    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        Search search = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("name", "Stargate", Search.class);
        assertEquals(2, search.getId());
    }
}
