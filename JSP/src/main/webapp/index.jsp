<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Soundtracker</title>
  <link rel="icon" type="image/x-icon" href="MAIN/img/logo.webp">
</head>
<body>
<h1>
  <jsp:text>Hello! Welcome to Soundtracker.</jsp:text>
  <input type="image" src="MAIN/img/logo.webp" alt="LOGO" width="89" height="91" />
</h1>
<a href="${pageContext.request.contextPath}/hello-servlet">Show users</a>
</body>
</html>