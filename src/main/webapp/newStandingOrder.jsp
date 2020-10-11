<%@ page import="model.Bank" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="model.DatabaseConnection" %>
<%@ page import="java.sql.Statement" %><%--
 Utworzenie nowego zlecenia stałego
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Dodanie nowego klienta</title>
  <meta charset="utf-8"/>
</head>
<body>
<%
  String base = (String)application.getAttribute("base");
  String user = (String)session.getAttribute("user");
  String IDKlienta = request.getParameter("IDKlienta");
  String dzien = request.getParameter("dzien");
  String konto = request.getParameter("konto");
  String kwota = request.getParameter("kwota");
  int bonus = Bank.getBonus();
  Connection conn = DatabaseConnection.initializeDatabase(base);
  Statement stmt = conn.createStatement();
  String sql = "INSERT INTO zlecenia " +
          "(IDKlienta, kwota, dzien, odbiorca) " +
          "values (" + IDKlienta + "," + kwota + ",'" + dzien + "','" + konto + "');";
  System.out.println("sql=@" + sql + "@");
  int rs = stmt.executeUpdate(sql);

  response.sendRedirect("client.jsp");
%>
</body>
</html>