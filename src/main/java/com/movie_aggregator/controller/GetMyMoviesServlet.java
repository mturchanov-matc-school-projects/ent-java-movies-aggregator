package com.movie_aggregator.controller;

import com.movie_aggregator.dao.MovieDAO;
import com.movie_aggregator.dao.MovieDAOImpl;
import com.movie_aggregator.enity.Movie;
import com.movie_aggregator.utils.ImdbApiReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author mturchanov
 */

@WebServlet(
        urlPatterns = {"/myMovies"}
)
public class GetMyMoviesServlet extends HttpServlet {
    /**
     *
     * @param req  request
     * @param resp response
     * @throws ServletException servlet Exception
     * @throws IOException      io Exception
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        MovieDAO movieDAO = new MovieDAOImpl();
        List<Movie> movies = movieDAO.getAllMovies();
        req.setAttribute("movies", movies);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/result.jsp");
        dispatcher.forward(req, resp);
    }
}
