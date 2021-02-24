//package com.movie_aggregator.controller;
//
//import com.movie_aggregator.entity.Movie;
////import com.movie_aggregator.utils.ImdbApiReader;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
///**
// * @author mturchanov
// */
//
//@WebServlet(
//        urlPatterns = {"/searchMovie"}
//)
//public class SearchMovieServlet extends HttpServlet {
//    /**
//     *
//     * @param req  request
//     * @param resp response
//     * @throws ServletException servlet Exception
//     * @throws IOException      io Exception
//     */
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        String movieToSearch = req.getParameter("search");
//        ImdbApiReader apiReader = new ImdbApiReader(movieToSearch);
//
//        String JSONMovies = apiReader.getResults("SearchMovie", null);
//        List<Movie> movies = apiReader.parseJSONMovies(JSONMovies);
//        req.setAttribute("movies", movies);
//
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/result.jsp");
//        dispatcher.forward(req, resp);
//    }
//}
