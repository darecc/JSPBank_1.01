<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="model.*" %><%--
 Strona główna klienta banku
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Strona klienta banku</title>
    <meta charset="utf-8"/>
    <link href="styl.css" rel="stylesheet"/>
  </head>
<body class="page">
<div>
  <%
    String user = (String)session.getAttribute("user");
    String base = (String)application.getAttribute("base");
    Connection conn = DatabaseConnection.initializeDatabase(base);
    Statement stmt = conn.createStatement();
    // pobranie danych zalogowanego użytkownika
    Klient klient = Bank.getAccountDetails(base, user);
    // pobranie lokat danego klienta banku (zmienna 'user')
    List<Lokata> lokaty = Bank.getDeposits(base, user);
    List<Zlecenie> zlecenia = Bank.getStandingOrders(base, klient.getID());
  %>
  <b>Twoje dane</b></br/>
  <table class="table">
    <thead><tr><th>Imię</th><th>Nazwisko</th></tr></thead>
    <tr><td><%=klient.getImie()%></td><td><%=klient.getNazwisko()%></td></tr>
    <tr><td>Twój numer rachunku</td><td><b><%=klient.getNumerKonta()%></b></td></tr>
    <tr><td>Stan rachunku</td><td><b><%=klient.getStanKonta()%></b></td></tr>
  </table>
  <p><b>Twoje lokaty</b>:
    <table class="table">
    <%
      if (lokaty.size() > 0) {
        out.print("<thead><tr>");
        out.print("<th>ID</th><th>Data założenia</th><th>Kwota</th><th>Oprocentowanie</th><th>Czas</th>");
        out.print("</tr></thead>");
      }
      for(Lokata lok : lokaty) {
        out.print("<tr><td>" + lok.getID() + "</td><td>" + lok.getDataPoczatkowa().toString() + "</td><td>" + lok.getKwota() + "</td><td>" + lok.getOprocentowanie() + "</td><td>" + lok.getCzasTrwania() + "</td>");
        out.print("</tr>");
      }
    %>
    </table>
  <a href="addDeposit.jsp">Załóż nową lokatę</a>
  </p>
  <p><b>Twoje zlecenia stałe</b>
  <table class="table">
    <%
      if (zlecenia.size() > 0) {
        out.print("<thead><tr>");
        out.print("<th>ID</th><th>Dzień miesiąca</th><th>Kwota</th><th>Konto</th>");
        out.print("</tr></thead>");
      }
      for(Zlecenie zle : zlecenia) {
        out.print("<tr><td>" + zle.getID() + "</td><td>" + zle.getDzien() + "</td><td>" + zle.getKwota() + "</td><td>" + zle.getOdbiorca() + "</td>");
        out.print("</tr>");
      }
    %>
  </table>
  <div class="button">
  <p>Nowe zlecenie:</p>
      <form action="newStandingOrder.jsp" method="post">
        <label>Dzień: </label>
        <input name="dzien" type="number"/><br/>
        <label>Kwota: </label>
        <input name="kwota" type="number"/><br/>
        <label>Numer rachunku</label>
        <input name="konto" type="text"/>
        <input name="IDKlienta" id="IDKlienta" type="hidden" value="<%=klient.getID()%>"/>
        <input type = "submit" value="Utwórz zlecenie" class="button"/>
      </form>
  </div>
  <div class="button">
  <p>Nowy przelew:</p>
  <form action="newTransfer.jsp" method="post">
      <label>Kwota: </label>
      <input name="kwota" type="number"/>
      <label>Numer rachunku</label>
      <input name="konto" type="text"/>
      <input name="IDKlient" id="IDKlient" type="hidden" value="<%=klient.getID()%>"/>
      <input name="stan" id="stan" type="hidden" value="<%=klient.getStanKonta()%>"/>
      <input name="numerKonta" id="numerKonta" type="hidden" value="<%=klient.getNumerKonta()%>"/>
      <input type = "submit" value="Utwórz przelew" class="button"/>
  </form>
  </p>
  </div>
</div>
</body>
</html>
