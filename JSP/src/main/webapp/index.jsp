<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Soundtracker</title>
  <link rel="icon" type="image/x-icon" href="MAIN/img/logo.webp">
    <link rel="stylesheet" href="css/index.css">

</head>
<body>
<h1>
  <jsp:text>Hello! Welcome to Soundtracker.</jsp:text>
</h1>
<a href="${pageContext.request.contextPath}/movies.jsp">Show movies</a>
</body>
</html>