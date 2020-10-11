package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static String PREFIX = "52067";

    public static String getAccountNumber(String base, String login) {
        String result = "";
        try {
            Connection conn = DatabaseConnection.initializeDatabase(base);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM klienci where login = '" + login + "';";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("idKlienta");
            System.out.println("ID = " + id);
            result = PREFIX + getString(id);
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println("!!!" + cnfe.getMessage());
        }
        catch(SQLException se) {
            System.out.println("@@@"  +se.getMessage());
        }
        return result;
    }
    private static String getString(int id) {
        int dzielnik = 10000;
        String res = "";
        while (id > 0) {
            int x = id / dzielnik;
            id = id - x * dzielnik;
            res = res + x;
            dzielnik /=10;
        }
        return res;
    }
    public static int getBonus() {
        return 500;
    }

    public static boolean ifAccountExists(String base, String konto) throws SQLException, ClassNotFoundException {
        boolean jest = false;
        Connection conn = DatabaseConnection.initializeDatabase(base);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM klienci;";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()) {
            if (rs.getString("konto").equals(konto)) {
                jest = true;
                break;
            }
        }
        return jest;
    }
    public static List<Lokata>  getDeposits(String base, String user) throws SQLException, ClassNotFoundException {
        Connection conn = DatabaseConnection.initializeDatabase(base);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM klienci where login = '" + user + "';";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("result set: " + rs.first());
        String konto = rs.getString("konto");
        double stan = rs.getDouble("stanKonta");
        String imie = rs.getString("imie");
        String nazwisko = rs.getString("nazwisko");
        String adres = rs.getString("adres");
        int idKlienta = rs.getInt("idKlienta");
        sql = "SELECT * FROM lokaty WHERE IDKlienta = " + idKlienta + ";";
        rs = stmt.executeQuery(sql);
        List<Lokata> lokaty = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("ID");
            int idK = rs.getInt("IDKlienta");
            double kwota = rs.getDouble("kwota");
            double procent = rs.getDouble("oprocentowanie");
            int czasTrwania = rs.getInt("czasTrwania");
            Date dataPocz = rs.getDate("dataPoczatkowa");
            Lokata lokata = new Lokata(id, idK, kwota, czasTrwania, dataPocz, procent);
            lokaty.add(lokata);
        }
        return lokaty;
    }
    public static List<Zlecenie> getStandingOrders(String base, int idKlienta) throws SQLException, ClassNotFoundException {
        Connection conn = DatabaseConnection.initializeDatabase(base);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM zlecenia WHERE IDKlienta = " + idKlienta + ";";
        System.out.println("SQL=" + sql);
        ResultSet rs = stmt.executeQuery(sql);
        List<Zlecenie> zlecenia = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt("ID");
            int idK = rs.getInt("IDKlienta");
            double kwota = rs.getDouble("kwota");
            String odbiorca = rs.getString("odbiorca");
            int dzien = rs.getInt("dzien");
            Zlecenie zlecenie = new Zlecenie(id, idK, dzien, kwota, odbiorca);
            zlecenia.add(zlecenie);
        }
        return zlecenia;
    }
    public static Klient getAccountDetails(String base, String user) throws SQLException, ClassNotFoundException {
        Connection conn = DatabaseConnection.initializeDatabase(base);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM klienci where login = '" + user + "';";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("result set: " + rs.first());
        String konto = rs.getString("konto");
        double stan = rs.getDouble("stanKonta");
        String imie = rs.getString("imie");
        String nazwisko = rs.getString("nazwisko");
        String adres = rs.getString("adres");
        int idKlienta = rs.getInt("idKlienta");
        Klient klient = new Klient(idKlienta, nazwisko, imie, konto, stan);
        return klient;
    }
    public static int transferMoneyToAccount(String base, String konto, double kwota) throws SQLException, ClassNotFoundException {
        Connection conn = DatabaseConnection.initializeDatabase(base);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM klienci WHERE konto = '" + konto + "';";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        double val = rs.getDouble("stanKonta");
        val = val + kwota;
        sql = "UPDATE klienci SET stanKonta = " + val + " WHERE konto = '" + konto + "';";
        int ile = stmt.executeUpdate(sql);
        return ile;
    }
    public static int transferMoneyFromAccount(String base, String konto, double kwota) throws SQLException, ClassNotFoundException {
        Connection conn = DatabaseConnection.initializeDatabase(base);
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM klienci WHERE konto = '" + konto + "';";
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        double val = rs.getDouble("stanKonta");
        val = val - kwota;
        sql = "UPDATE klienci SET stanKonta = " + val + " WHERE konto = '" + konto + "';";
        int ile = stmt.executeUpdate(sql);
        return ile;
    }
}
