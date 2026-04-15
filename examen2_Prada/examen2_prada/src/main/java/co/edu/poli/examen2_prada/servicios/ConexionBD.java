package co.edu.poli.examen2_prada.servicios;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    public static Connection getConexion() throws Exception {

        String url = "jdbc:mysql://localhost:3306/examen2_prada";
        String user = "root";
        String pass = "1234";

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, user, pass);
    }
}