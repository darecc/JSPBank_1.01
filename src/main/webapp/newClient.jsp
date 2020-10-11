<%@ page import="java.sql.Connection" %>
<%@ page import="model.DatabaseConnection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="model.Bank" %>
<%--
   Dodanie klienta do bazy
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
  <head>
    <title>Dodanie nowego klienta</title>
    <meta charset="utf-8"/>
  </head>
  <body>
  <%
    request.setCharacterEncoding("UTF-8");
    String imie = request.getParameter("imie");
    String nazwisko = request.getParameter("nazwisko");
    String pesel = request.getParameter("pesel");
    String adres = request.getParameter("adres");
    String login = request.getParameter("login");
    String haslo = request.getParameter("haslo");
    String base = (String)application.getAttribute("base");
    System.out.println("Baza: " + base);
    System.out.println("imię:" + imie);
    System.out.println("nazwisko:" + nazwisko);
    int bonus = Bank.getBonus();
    Connection conn = DatabaseConnection.initializeDatabase(base);
    Statement stmt = conn.createStatement();
    String md5 = DatabaseConnection.makeMD5(haslo);
    String sql = "INSERT INTO klienci " +
            "(imie, nazwisko, pesel, adres, login, password, role, stanKonta) " +
            "values ('" + imie + "','" + nazwisko + "','" + pesel + "','" +adres + "','" +login + "','" + md5 +"','client', " + bonus + ");";
    System.out.println("sql=@" + sql + "@");
    int rs = stmt.executeUpdate(sql);
    String accountNumber = Bank.getAccountNumber(base, login);
    System.out.println("account number " + accountNumber);
    sql = "UPDATE klienci SET konto = '" + accountNumber + "' WHERE login = '" + login + "';";
    rs = stmt.executeUpdate(sql);
    System.out.println("Wstawiono: " + rs + " rekord");
    if (rs > 0)
      session.setAttribute("user", login);
      response.sendRedirect("login.jsp");
  %>
  </body>
</html>
