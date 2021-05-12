package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Authority;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.User;
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
public class UserDaoTest extends AbstractTest{

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
        User user =
                dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "12", User.class);
        logger.info(user);
        dao.deleteObject(user);
        User deleted = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("username", "12", User.class);
        assertNull(deleted);
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<User> users = dao.getAll(User.class);
        assertEquals(2, users.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        User user = new User();
        user.setUsername("13");
        user.setPassword("{noop}13");
        dao.saveObject(user);
        User insertedUser = dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "13", User.class);
        assertNotNull(insertedUser);
        assertEquals(insertedUser.getUsername(), user.getUsername());
    }

    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        User getUser =
                dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "12", User.class);
        getUser.setPassword("12");
        dao.saveOrUpdate(getUser);
        User updatedUser =
                dao.getFirstEntryBasedOnAnotherTableColumnProperty("username", "12", User.class);
        assertEquals(getUser.getPassword(), updatedUser.getUsername());
    }






    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        User User = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("username", "11", User.class);
        assertEquals("11", User.getUsername());
    }
}
