package co.edu.poli.examen2_prada.servicios;

import co.edu.poli.examen2_prada.modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSeguro implements CRUD<Seguro> {

    @Override
    public String create(Seguro s) throws Exception {
        Connection con = ConexionBD.getInstancia().getConexion();
        con.setAutoCommit(false); // Iniciamos transacción para seguridad
        try {
            // 1. Insertar en la tabla PADRE (seguro)
            String sqlPadre = "INSERT INTO seguro (numero, fecha_exp, estado, asegurado_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sqlPadre);
            ps.setString(1, s.getNumero());
            ps.setString(2, s.getFechaExpedicion());
            ps.setBoolean(3, s.isEstado());
            ps.setString(4, s.getAsegurado().getId());
            ps.executeUpdate();

            // 2. Insertar en la tabla HIJA correspondiente
            if (s instanceof SeguroVehiculo) {
                String sqlVeh = "INSERT INTO seguro_vehiculo (numero, marca) VALUES (?, ?)";
                ps = con.prepareStatement(sqlVeh);
                ps.setString(1, s.getNumero());
                ps.setString(2, ((SeguroVehiculo) s).getMarca());
            } else {
                String sqlVid = "INSERT INTO seguro_vida (numero, beneficiario) VALUES (?, ?)";
                ps = con.prepareStatement(sqlVid);
                ps.setString(1, s.getNumero());
                ps.setString(2, ((SeguroVida) s).getBeneficiario());
            }
            ps.executeUpdate();

            con.commit(); // Guardamos todo
            return "✔ " + s.getClass().getSimpleName() + " [" + s.getNumero() + "] creado con éxito.";
        } catch (Exception e) {
            con.rollback(); // Si algo falla, deshacemos todo
            throw new Exception("Error al crear el seguro: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public <K> Seguro readone(K id) throws Exception {
        Connection con = ConexionBD.getInstancia().getConexion();
        String numero = (String) id;

        // Intentar buscar primero como Seguro de Vehículo
        String sqlVeh = "SELECT s.numero, s.fecha_exp, s.estado, a.id, a.nombre, v.marca " +
                        "FROM seguro s " +
                        "INNER JOIN asegurado a ON s.asegurado_id = a.id " +
                        "INNER JOIN seguro_vehiculo v ON s.numero = v.numero " +
                        "WHERE s.numero = ?";
        
        PreparedStatement ps = con.prepareStatement(sqlVeh);
        ps.setString(1, numero);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Asegurado ase = new Asegurado(rs.getString("id"), rs.getString("nombre"));
            return new SeguroVehiculo(rs.getString("numero"), rs.getString("fecha_exp"), 
                                     rs.getBoolean("estado"), ase, rs.getString("marca"));
        }

        // Si no es vehículo, intentar buscar como Seguro de Vida
        String sqlVid = "SELECT s.numero, s.fecha_exp, s.estado, a.id, a.nombre, vi.beneficiario " +
                        "FROM seguro s " +
                        "INNER JOIN asegurado a ON s.asegurado_id = a.id " +
                        "INNER JOIN seguro_vida vi ON s.numero = vi.numero " +
                        "WHERE s.numero = ?";
        
        ps = con.prepareStatement(sqlVid);
        ps.setString(1, numero);
        rs = ps.executeQuery();

        if (rs.next()) {
            Asegurado ase = new Asegurado(rs.getString("id"), rs.getString("nombre"));
            return new SeguroVida(rs.getString("numero"), rs.getString("fecha_exp"), 
                                 rs.getBoolean("estado"), ase, rs.getString("beneficiario"));
        }

        return null; // No se encontró en ninguna tabla
    }

    @Override
    public List<Seguro> readall() throws Exception {
        // Implementación opcional si el profesor la pide
        return new ArrayList<>();
    }
}