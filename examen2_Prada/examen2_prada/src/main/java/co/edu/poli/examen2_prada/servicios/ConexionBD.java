package co.edu.poli.examen2_prada.servicios;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    // El constructor debe ser privado
    private ConexionBD() throws Exception {
        String url = "jdbc:mysql://localhost:3306/examen2_prada";
        String user = "root";
        String pass = "1234";
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection(url, user, pass);
    }

    public static ConexionBD getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() throws Exception {
        // Si la conexión se cerró por tiempo de espera, la reabrimos
        if (conexion == null || conexion.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/examen2_prada";
            String user = "root";
            String pass = "1234";
            conexion = DriverManager.getConnection(url, user, pass);
        }
        return conexion;
    }
}