package com.movie_aggregator.dao;

import com.movie_aggregator.enity.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.movie_aggregator.utils.SessionFactoryProvider;
import java.util.List;

/**
 * The type Movie dao.
 *
 * @author mturchanov
 */
public class MovieDAOImpl implements MovieDAO{
    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The Session factory.
     */
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();


    /**
     * update movie
     * @param movie  Movie to be inserted or updated
     */
    public void saveOrUpdate(Movie movie) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(movie);
        transaction.commit();
        session.close();
        logger.info(movie);

    }

    @Override
    public List<Movie> getAllMovies() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Movie> movies = session
                .createQuery("from com.movie_aggregator.enity.Movie", Movie.class)
                .getResultList();
        transaction.commit();

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

    @Override
    public Movie getMovieByID(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Movie movie = session.get(Movie.class, id);
        transaction.commit();
        session.close();
        return movie;
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
//        Movie m1 = new Movie("testName", "imdbID", 7.54, 66, 6.54, 54, 5.54, 4.45, "https://media.wired.com/photos/598e35994ab8482c0d6946e0/master/w_2560%2Cc_limit/phonepicutres-TA.jpg",
//                "1234");
//        new MovieDAOImpl().saveOrUpdate(m1);
    }
}
