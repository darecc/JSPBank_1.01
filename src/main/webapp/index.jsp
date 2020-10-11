<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Witamy w ABC Banku</title>
    <meta charset="utf-8"/>
    <link href="styl.css" rel="stylesheet"/>
  </head>
<body class="page">
  <div>
    <%
      application.setAttribute("base", "dceglarek_bank");
    %>
      <div>
        <h3>Witamy w ABC Banku</h3>
      </div>
    <div>
      <img src="https://subiektywnieofinansach.pl/wp-content/uploads/2017/12/robot-2167836_1920-1024x641.jpg.webp" class="image"/>
      <p>Mamy naprawdę świetną ofertę dla każdego</p>
      <p>Jesteśmy najlepsi</p>
      <p>U nas poczujesz XXI wiek</p><br/>
    </div>
    <p><a href="login.jsp">Zaloguj się</a></p>
    <p><a href="createAccount.jsp">Utwórz konto </a></p>
  </div>
</body>
</html>
