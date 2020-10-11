<%--
  Strona tylko dla uprawnionego pracownika banku: obsługa zleceń
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  errorPage="error.jsp" %>
<html>
  <head>
    <title>Obsługa zleceń</title>
  </head>
  <body>
  <%
    String rola = request.getParameter("rola");
    if (!rola.equals("admin"))
      response.sendRedirect("error.jsp");
  %>
  </body>
</html>
