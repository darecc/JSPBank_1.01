<%@ page import="java.sql.Connection" %>
<%@ page import="model.DatabaseConnection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.Month" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.Bank" %><%--
Nowy przelew
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Nowy przelew</title>
    <meta charset="utf-8"/>
    <link href="styl.css" rel="stylesheet"/>
  </head>
  <body class="page">
  <%
   /*
    <input name="IDKlienta" id="IDKlient" type="hidden"
    <input name="stan" id="stan" type="hidden" value
  */
    double kwota = Double.parseDouble(request.getParameter("kwota"));
    String konto = request.getParameter("konto");
    String IDKlienta = request.getParameter("IDKlient");
    String numerKonta = request.getParameter("numerKonta");
    double stan = Double.parseDouble(request.getParameter("stan"));
    //sprawdzenie czy kwota nie jest wyższa niż stan na koncie
    if (kwota > stan) {
      out.print("Nie wolno przelewać więcej niż jest na koncie");
      return;
    }
    //sprawdzenie czy kwota nie jest ujemna
    if (kwota <= 0) {
      out.print("Nie wolno przelewać ujemnych kwot");
      return;
    }
    //sprawdzenie czy odbiorca (konto) jest w banku i jeśli jest to zwiększyć mu stan konta,
    String user = (String)session.getAttribute("user");
    String base = (String)application.getAttribute("base");
    // sprawdzenie czy podane konto odbiorcy (zmienna 'konto') istnieje
    boolean jest = Bank.ifAccountExists(base, konto);
    if (jest == false) {
      out.print("Nie ma takiego konta odbiorcy!");
      return;
    }
    //powiększenie stanu konta odbiorcy o kwotę
    int ile = Bank.transferMoneyToAccount(base, konto, kwota);
    //pomniejszenie konta o kwotę
    ile = Bank.transferMoneyFromAccount(base, numerKonta, kwota);
    if (ile > 0) {
        out.print("<p>Dane przelewu:</p><table class=\"table\"><thead></thead><tr><th>Konto odbiorcy</th><th>Kwota przelewu</th><th>Data wykonania</th></tr></thead>");
        LocalDate data = LocalDate.now();
        Month month = data.getMonth();
        String dat = "" + data.getDayOfMonth() + "-" + month.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pl")) + "-" + data.getYear();
        out.print("<tr><td>" + konto + "</td><td>" + kwota + "</td><td>" + dat + "</td></tr>");
        out.print("</table>");
        out.print("<p>Przelew został przekazany do wykonania</p>");
    }
  %>
  <form action="client.jsp" method="post">
     <input type="submit" value="Wróć na stronę główną" class="button"/>
  </form>
  </body>
</html>
