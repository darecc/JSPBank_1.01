package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/waliduj")
public class waliduj extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public waliduj() {
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login    = request.getParameter("user");
        String password = request.getParameter("password");
        String base = request.getParameter("base");
        String md5 = makeMD5(password);
        System.out.println("Login: " + login);
        System.out.println("Password: " + password);
        System.out.println("MD5 = " + md5);
        String role = checkIfUserExists(base, login, md5);
        String page = "";
        if (role == "no") {
            page = "createAccount.jsp";
        }
        else {
            // wybór typu użytkownika
            if (role.equals("employee")) {
                HttpSession session = request.getSession();
                session.setAttribute("user", login);
                response.sendRedirect("changeOrdersState.jsp");
            }
            // pobranie ciasteczka o kluczu = login
            // utworzenie zmiennych sesyjnych dla user (login)
            HttpSession session = request.getSession();
            session.setAttribute("user", login);
            page = "client.jsp";
        }
        response.sendRedirect(page);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    /**
     * Utworzenie MD5 dla hasla podanego przez uzytkownika
     * @return
     */
    private String makeMD5(String password)  {
        //region zamiana hasła na MD5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            String md5 = sb.toString().toUpperCase();
            System.out.println("md5=" + md5);
            return md5;
        }
        catch(NoSuchAlgorithmException nsae) {
            return null;
        }
    }
    private String checkIfUserExists(String base, String login, String md5) {
        Statement stmt = null;
        ResultSet rs = null;
        String role = "no";
        boolean jest = false;
        try {
            Connection conn = DatabaseConnection.initializeDatabase(base);
            if (conn == null) {
                System.out.println("Nie można połączyć się z bazą danych. ");
            }
            stmt = conn.createStatement();
            String sql = "SELECT * FROM klienci where login = '" + login + "' AND password='" + md5 + "';";
            rs = stmt.executeQuery(sql);
            jest = rs.first();
            role = rs.getString("role");
            System.out.println("result set: " + jest);
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println("!!!" + cnfe.getMessage());
            return role;
        }
        catch(SQLException se) {
            System.out.println("@@@"  +se.getMessage());
            return role;
        }
        return role;
    }
}
