package com.movie_aggregator.repository;

/**
 * @author mturchanov
 */


import com.movie_aggregator.AbstractTest;
import com.movie_aggregator.configuration.MyConfig;
import com.movie_aggregator.configuration.MyWebInitializer;
import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Image;
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
public class ImageDaoTest extends AbstractTest{

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
        Image retrievedImage = dao.get(Image.class, 1);
        String expectedUrl = "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/bdad2d6f-ccc7-482f-87bf-1e4029ef4748/1680x1680";
        assertEquals(expectedUrl, retrievedImage.getUrl());
    }

    /**
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        dao.delete(Image.class, 47);
        assertNull(dao.get(Image.class,47));
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void getAllSuccess() {
        List<Image> searches = dao.getAll(Image.class);
        assertEquals(3, searches.size());
    }

    /**
     * Verify successful retrieval of all users
     */
    @Test
    void saveSuccess() {
        Image newImage = new Image( "new Serch val");
        int searchId = dao.save(newImage);
        System.out.println(searchId);
        Image insertedImage = dao.get(Image.class, searchId);
        System.out.println(insertedImage);
        assertNotNull(insertedImage);
        assertEquals(insertedImage, newImage);
    }

    /**
     * Verify Saving or update test.
     */
    @Test
    void saveOrUpdateTest() {
        Image getImage = dao.get(Image.class, 47);
        getImage.setUrl("modifiedUrl");
        dao.saveOrUpdate(getImage);
        Image updatedImage = dao.get(Image.class, 47);
        assertEquals("modifiedUrl", updatedImage.getUrl());
    }






    /**
     * Verify Geting first entry based on another table column property test.
     */
    @Test
    void getFirstEntryBasedOnAnotherTableColumnPropertyTest() {
        Image Image = dao.getFirstEntryBasedOnAnotherTableColumnProperty
                ("url", "Stargate", Image.class);
        assertEquals(2, Image.getId());
    }
}
