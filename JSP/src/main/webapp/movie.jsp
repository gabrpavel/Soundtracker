<%@ page import="com.website.jdbc.dao.MoviesDAO" %>
<%@ page import="com.website.jdbc.model.Movie" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movie Info</title>
    <link rel="stylesheet" type="text/css" href="css/movie.css">
</head>
<body>
<div class="container">
    <div class="movie-header">
        <h1>Movie Details</h1>
    </div>
    <%
        MoviesDAO moviesDAO = new MoviesDAO();
        Long id = Long.valueOf(request.getParameter("id"));
        Movie movie = moviesDAO.getMovieById(id);
    %>
    <div class="movie-poster">
        <img src="<%= movie.getPoster() %>" alt="Poster">
    </div>
    <div class="movie-details">
        <h2 class="movie-title"><%= movie.getRuTitle() %></h2>
        <p class="movie-info">Year: <%= movie.getReleaseYear() %></p>
        <p class="movie-info">Length: <%= movie.getLength() %> minutes</p>
        <p class="movie-description"><%= movie.getDescription() %></p>
        <div class="back-button">
            <a href="movies.jsp">Back to Movies</a>
        </div>
    </div>
</div>
</body>
</html>
