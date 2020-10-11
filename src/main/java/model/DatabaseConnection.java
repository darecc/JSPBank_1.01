package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection initializeDatabase(String base)
            throws SQLException, ClassNotFoundException
    {
        Connection conn = null;
        try {
            String dbDriver = "com.mysql.jdbc.Driver";
            String dbURL = "jdbc:mysql://sqlhosting5.dt.pl:3306/" + base + "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8";
            System.out.println(dbURL);
            Class.forName(dbDriver);
            // webserwer.pl user: dceglarek_bank password: sdauser
            //localhost user = root, password = admin
            conn = DriverManager.getConnection(dbURL, "dceglarek_bank", "sdauser");
        }
        catch(SQLException se) {
            System.out.println("Database connection:" + se.getMessage());
        }
        return conn;
    }
    public static String makeMD5(String password)  {
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
}