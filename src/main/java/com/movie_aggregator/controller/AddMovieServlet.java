package com.movie_aggregator.controller;

import com.movie_aggregator.dao.MovieDAO;
import com.movie_aggregator.dao.MovieDAOImpl;
import com.movie_aggregator.enity.Movie;
import com.movie_aggregator.utils.ImdbApiReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author mturchanov
 */

@WebServlet(
        urlPatterns = {"/addMovie"}
)
public class AddMovieServlet extends HttpServlet {
    /**
     *
     * @param req  request
     * @param resp response
     * @throws ServletException servlet Exception
     * @throws IOException      io Exception
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Movie movie = new Movie();
        movie.setImage(req.getParameter("movieImage"));
        movie.setMetacriticRating(req.getParameter("movieMetacriticRating"));
        movie.setImdbId(req.getParameter("movieImdbId"));
        movie.setImdbRating(req.getParameter("movieImdbRating"));
        movie.setFilmAffinityRating(req.getParameter("movieFilmAffinityRating"));
        movie.setName(req.getParameter("movieName"));
        movie.setRottenTomatoesRating(req.getParameter("movieRottenTomatoesRating"));
        movie.setTheMovieDbRating(req.getParameter("movieTheMovieDbRating"));
        movie.settV_comRating(req.getParameter("movieTV_comRating"));
        movie.setYear(req.getParameter("movieYear"));

        MovieDAO movieDAO = new MovieDAOImpl();
        movieDAO.saveOrUpdate(movie);
        resp.sendRedirect("/index.jsp");

    }
}
