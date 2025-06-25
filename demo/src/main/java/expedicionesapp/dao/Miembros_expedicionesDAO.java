package expedicionesapp.dao;

import expedicionesapp.model.Miembros;
import expedicionesapp.model.Miembros_expediciones;
import expedicionesapp.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Miembros_expedicionesDAO {
   
    public boolean unirMiembrosExpediciones(Miembros_expediciones expedicion) {
        //Prepara el codigo y le pone null al stmt para no crearlo en el foreach
        String sql = """
            INSERT INTO Miembros_expediciones (id_Expedicion, id_accidente)
            VALUES (?, ?)
            """; 
        PreparedStatement stmt = null;
        //si se conecta
        try (Connection conn = DBConnection.getConnection()) {
            //El for esta para que haga todas las inserciones por cada Miembro en la lista de Miembros_expediciones
            for(Miembros miembro : expedicion.getId_miembros()){
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, expedicion.getId_expediciones().getId());
                stmt.setInt(2, miembro.getId());
            }
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Recuperar el ID generado si es auto-incremental
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        expedicion.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Miembros conectadis a expediciones con ID: " + expedicion.getId());
                return true;
            } else {
                System.out.println("No se pudieron insertar los miembros.");
                return false;
            } 
        } catch (SQLException e) {
            System.err.println("Error al insertar miembros a la expedición: " + e.getMessage());
            return false;
        }       
    }
}
    
