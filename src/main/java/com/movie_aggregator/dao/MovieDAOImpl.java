package com.movie_aggregator.dao;

import com.movie_aggregator.enity.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.movie_aggregator.utils.SessionFactoryProvider;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author mturchanov
 */
public class MovieDAOImpl implements MovieDAO{
    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();


    /**
     * update movie
     * @param movie  Movie to be inserted or updated
     */
    public void saveOrUpdate(Movie movie) {
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(movie);
        session.close();
        logger.info(movie);

    }

    @Override
    public List<Movie> getAllMovies() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> query = builder.createQuery( Movie.class );
        Root<Movie> root = query.from( Movie.class );
        List<Movie> movies = session.createQuery("from Movies").getResultList();

        logger.debug("The list of movies " + movies);

        session.close();
        return movies;
    }
    /**
     * Delete a movie
     * @param movie User to be deleted
     */
    @Override
    public void deleteMovie(Movie movie) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(movie);
        transaction.commit();
        session.close();
    }


    public static void main(String[] args) {
        Movie m1 = new Movie("testName", "imdbID", 7.54, 66, 6.54, 54, 5.54, 4.45, "https://media.wired.com/photos/598e35994ab8482c0d6946e0/master/w_2560%2Cc_limit/phonepicutres-TA.jpg",
                "1234");
        new MovieDAOImpl().saveOrUpdate(m1);
    }
}
