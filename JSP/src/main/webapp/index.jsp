<!-- index.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Soundtracker</title>
  <link rel="icon" type="image/x-icon" href="MAIN/img/logo.webp">
  <link rel="stylesheet" href="css/index.css">
</head>
<body>
<h1>Welcome to Soundtracker</h1>
<div class="show-movies-button">
  <a href="${pageContext.request.contextPath}/movies.jsp">Show movies</a>
</div>
</body>
</html>
