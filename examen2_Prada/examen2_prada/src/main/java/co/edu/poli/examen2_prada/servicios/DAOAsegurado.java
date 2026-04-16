package co.edu.poli.examen2_prada.servicios;

import co.edu.poli.examen2_prada.modelo.Asegurado;
import java.sql.*;
import java.util.*;

public class DAOAsegurado implements CRUD<Asegurado> {

    @Override
    public String create(Asegurado a) { return null; }

    @Override
    public <K> Asegurado readone(K id) { return null; }

    @Override
    public List<Asegurado> readall() throws Exception {
        Connection con = ConexionBD.getInstancia().getConexion();
        List<Asegurado> lista = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(
            "SELECT id, nombre FROM asegurado");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(new Asegurado(
                rs.getString("id"), 
                rs.getString("nombre")));
        }
        return lista;
    }
}
