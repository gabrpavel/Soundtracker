package com.website.jsp;

import com.website.jdbc.dao.MoviesDAO;
import com.website.jdbc.model.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/profile", name = "profileServlet")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        MoviesDAO moviesDAO = new MoviesDAO();

        List<Movie> movies = moviesDAO.getAllMovies();
        for (Movie movie: movies) {
            pw.println("<p>L"+movie.toString()+"</p>");
        }

    }
}
