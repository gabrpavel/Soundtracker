<%@ page import="com.website.jdbc.dao.MoviesDAO" %>
<%@ page import="com.website.jdbc.model.Movie" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title>Movies</title>
    <link rel="stylesheet" type="text/css" href="css/movies.css">
</head>
<body>
<div class="container">
    <div class="home-button">
        <a href="index.jsp">Home</a>
    </div>
    <%
        MoviesDAO moviesDAO = new MoviesDAO();
        List<Movie> movies = moviesDAO.getAllMovies();
        for (Movie movie : movies) { %>
    <div class="movie-card">
        <a href="movie.jsp?id=<%= movie.getId() %>">
            <img src="<%= movie.getPoster()%>" alt="Poster" class="movie-poster">
        </a>
        <div class="movie-info">
            <p class="movie-title"><%= movie.getRuTitle()%>
            </p>
            <p class="movie-release-year"><%= movie.getReleaseYear()%>
            </p>
        </div>
    </div>
    <% } %>
</div>
</body>
</html>
