<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Walidacja formularza</title>
  </head>
  <body>
  <%
    String user = request.getParameter("user");
    String pass = request.getParameter("password");
    out.print("<p>User: " + user + "</p>");
    out.print("<p>Password: " + pass + "</p>");
  %>
  </body>
</html>
