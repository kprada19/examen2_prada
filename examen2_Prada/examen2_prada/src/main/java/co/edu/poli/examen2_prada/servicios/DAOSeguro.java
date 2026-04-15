package co.edu.poli.examen2_prada.servicios;

import co.edu.poli.examen2_prada.modelo.*;
import java.sql.*;

public class DAOSeguro implements CRUD<Seguro> {

    @Override
    public String create(Seguro s) throws Exception {
        Connection con = ConexionBD.getConexion();
        con.setAutoCommit(false); // Iniciar transacción

        try {
            // 1. Insertar en tabla Seguro (Padre)
            String sqlPadre = "INSERT INTO seguro (numero, fecha_exp, estado, asegurado_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sqlPadre);
            ps.setString(1, s.getNumero());
            ps.setString(2, s.getFechaExpedicion());
            ps.setBoolean(3, s.isEstado());
            ps.setString(4, s.getAsegurado().getId());
            ps.executeUpdate();

            // 2. Insertar en tabla Hija
            if (s instanceof SeguroVehiculo) {
                ps = con.prepareStatement("INSERT INTO seguro_vehiculo (numero, marca) VALUES (?, ?)");
                ps.setString(1, s.getNumero());
                ps.setString(2, ((SeguroVehiculo) s).getMarca());
            } else {
                ps = con.prepareStatement("INSERT INTO seguro_vida (numero, beneficiario) VALUES (?, ?)");
                ps.setString(1, s.getNumero());
                ps.setString(2, ((SeguroVida) s).getBeneficiario());
            }
            ps.executeUpdate();

            con.commit();
            return "✔ Seguro [" + s.getNumero() + "] creado con éxito.";
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    @Override
    public <K> Seguro readone(K num) throws Exception {
        Connection con = ConexionBD.getConexion();
        
        // Intento buscar en Vehículo
        String sqlVeh = "SELECT s.*, a.nombre, v.marca FROM seguro s " +
                        "JOIN asegurado a ON s.asegurado_id = a.id " +
                        "JOIN seguro_vehiculo v ON s.numero = v.numero WHERE s.numero = ?";
        PreparedStatement ps = con.prepareStatement(sqlVeh);
        ps.setString(1, (String) num);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return new SeguroVehiculo(rs.getString("numero"), rs.getString("fecha_exp"), 
                   rs.getBoolean("estado"), new Asegurado(rs.getString("asegurado_id"), 
                   rs.getString("nombre")), rs.getString("marca"));
        }

        // Intento buscar en Vida
        String sqlVid = "SELECT s.*, a.nombre, v.beneficiario FROM seguro s " +
                        "JOIN asegurado a ON s.asegurado_id = a.id " +
                        "JOIN seguro_vida v ON s.numero = v.numero WHERE s.numero = ?";
        ps = con.prepareStatement(sqlVid);
        ps.setString(1, (String) num);
        rs = ps.executeQuery();

        if (rs.next()) {
            return new SeguroVida(rs.getString("numero"), rs.getString("fecha_exp"), 
                   rs.getBoolean("estado"), new Asegurado(rs.getString("asegurado_id"), 
                   rs.getString("nombre")), rs.getString("beneficiario"));
        }
        con.close();
        return null;
    }

    @Override
    public java.util.List<Seguro> readall() throws Exception { return null; }
}