<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Exception</title>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Type: <%= exception.getClass().getName() %></p>
<p>Message: <%= exception.getMessage() %></p>
</body>
</html>

