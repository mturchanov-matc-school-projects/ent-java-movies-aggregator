package com.movie_aggregator.repository;

import com.movie_aggregator.entity.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Movie repository.
 *
 * @author mturchanov
 */
@Repository
public class MovieDAOImpl implements MovieDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Movie> getAllMovies() {

        Session session = sessionFactory.getCurrentSession();
        List<Movie> allMovies = session
                .createQuery("from Movie", Movie.class)
                .getResultList();

//        Query<Movie> query = session.createQuery("from ", Movie.class);
//        List<Movie> allMovies = query.getResultList();
        return allMovies;
    }

    @Override
    public void saveMovie(Movie Movie) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(Movie);
    }

    @Override
    public Movie getMovie(int id) {
        Session session = sessionFactory.getCurrentSession();
        Movie Movie = session.get(Movie.class, id);
        return Movie;
    }

    @Override
    public void deleteMovie(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Movie> query = session.createQuery("delete from Movie "
                + "where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void updateMovie(int id, Movie movie) {
        Movie updatedMovie = getMovie(id);
        if (movie.getName() != null) {
            String name = movie.getName();
            updatedMovie.setName(name);
        }
        //TODO: write conditional updates
        return ;
    }

    @Override
    public Set<String> getColumnProperties(String column) {
        Session session = sessionFactory.getCurrentSession();
        List<Movie> movies = session.createQuery("select imdb_id from Movie")
                .getResultList();
        Set<String> sMovies = new HashSet();
        movies.forEach(movie -> sMovies.add(movie.getImdbId()));
        return sMovies;
    }
}
