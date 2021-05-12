package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Authority;
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
public class AuthorityDaoTest extends AbstractTest{

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
        Authority authority = dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "11", Authority.class);
        dao.deleteObject(authority);
        Authority deleted = dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "11", Authority.class);
        assertNull(deleted);
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<Authority> authorities = dao.getAll(Authority.class);
        assertEquals(1, authorities.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        Authority newAuthority = new Authority();
        newAuthority.setAuthority("ROLE_MANAGER");
        newAuthority.setUsername("11");
        dao.saveObject(newAuthority);
        Authority insertedAuthority = dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "11", Authority.class);

        logger.info(insertedAuthority);
        assertNotNull(insertedAuthority);
        assertEquals(insertedAuthority.getUsername(), newAuthority.getUsername());
    }

    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        Authority getAuthority =
                dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "11", Authority.class);
        getAuthority.setAuthority("ROLE_ADMIN");
        dao.saveOrUpdate(getAuthority);
        Authority updatedAuthority =
                dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "11", Authority.class);
        assertEquals("ROLE_ADMIN", updatedAuthority.getAuthority());
    }

    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        Authority Authority = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("username", "11", Authority.class);
        assertEquals("11", Authority.getUsername());
    }
}
