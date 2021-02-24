//package com.movie_aggregator.controller;
//
//import com.movie_aggregator.repository.MovieDAO;
//import com.movie_aggregator.repository.MovieDAOImpl;
//import com.movie_aggregator.entity.Movie;
//import com.movie_aggregator.utils.ImdbApiReader;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
///**
// * @author mturchanov
// */
//
//@WebServlet(
//        urlPatterns = {"/deleteMovie"}
//)
//public class DeleteMyMovieServlet extends HttpServlet {
//    /**
//     *
//     * @param req  request
//     * @param resp response
//     * @throws ServletException servlet Exception
//     * @throws IOException      io Exception
//     */
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//
//        int movieId = Integer.parseInt(req.getParameter("movieId"));
//        System.out.println("TESTING TIME");
//        System.out.println(movieId);
//
//        MovieDAO movieDAO = new MovieDAOImpl();
//        Movie movie = movieDAO.getMovieByID(movieId);
//        movieDAO.deleteMovie(movie);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
//        dispatcher.forward(req, resp);    }
//}
