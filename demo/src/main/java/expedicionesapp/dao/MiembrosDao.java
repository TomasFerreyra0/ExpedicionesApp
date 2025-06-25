package expedicionesapp.dao;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import expedicionesapp.util.DBConnection;
import expedicionesapp.model.Miembros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MiembrosDao {
      public Miembros getMemberById(int idMiembro) {
        String sql = "SELECT * FROM Miembros WHERE id = ?";
        Miembros miembro = null; // Inicializamos a null por si no se encuentra

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMiembro);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    miembro = new Miembros();
                    miembro.setId(rs.getInt("id"));
                    miembro.setNacionalidad(rs.getString("nacionalidad"));
                    miembro.setNombre(rs.getString("nombre"));
                    miembro.setApellido(rs.getString("apellido"));
                    miembro.setSexo(rs.getString("sexo"));
                    miembro.setEs_lider(rs.getInt("es_lider"));
                    miembro.setEs_staff(rs.getInt("es_staff"));
                    miembro.setAno_nacimiento(rs.getInt("Año_nacimiento"));
                    miembro.setFallecido(rs.getInt("fallecido"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el miembro por ID: " + e.getMessage());
        }
        return miembro; // Devuelve el objeto Expediciones o null si no se encontró
    }

//Mostrar Miembros por expedicion
public List<Object[]> obtenerMiembrosPorExpedicion(int idExpedicion) {
    String sql = "SELECT * FROM expedicionxmiembro_vw WHERE id_expedicion = ?";
    List<Object[]> datos = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idExpedicion);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[]{
                rs.getInt("id_miembros"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("nacionalidad"),
                rs.getString("sexo"),
                rs.getInt("es_lider") == 1 ? "Sí" : "No",
                rs.getInt("es_staff") == 1 ? "Sí" : "No",
                rs.getInt("año_nacimiento"),
                rs.getInt("fallecido") == 1 ? "Sí" : "No"
            };
            datos.add(fila);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al consultar miembros: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    return datos;
}



    // 2. Insertar un nuevo miembro
    public boolean insertMember(Miembros miembro) {
        String sql = """
            INSERT INTO Miembros (nacionalidad, nombre, apellido, sexo, es_lider, es_staff, año_nacimiento,fallecido)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """; 

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, miembro.getNacionalidad());
            stmt.setString(2, miembro.getNombre());
            stmt.setString(3, miembro.getApellido());
            stmt.setString(4, miembro.getSexo());
            stmt.setInt(5, miembro.getEs_lider());
            stmt.setInt(6, miembro.getEs_staff());
            stmt.setInt(7, miembro.getAno_nacimiento());
            stmt.setInt(8,miembro.getFallecido());
            

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Recuperar el ID generado si es auto-incremental
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        miembro.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Expedición insertada correctamente con ID: " + miembro.getId());
                return true;
            } else {
                System.out.println("No se pudo insertar la expedición.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar la expedición: " + e.getMessage());
            return false;
        }
    }

    // 3. Actualizar un miembro existente
    public boolean modifyMember(Miembros miembro) {
        String sql = """
            UPDATE Miembros SET nacionalidad=?, nombre=?, apellido=?, sexo=?, es_lider=?, es_staff=?, año_nacimiento=?,fallecido=?
            WHERE id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, miembro.getNacionalidad());
            stmt.setString(2, miembro.getNombre());
            stmt.setString(3, miembro.getApellido());
            stmt.setString(4, miembro.getSexo());
            stmt.setInt(5, miembro.getEs_lider());
            stmt.setInt(6, miembro.getEs_staff());
            stmt.setInt(7, miembro.getAno_nacimiento());
            stmt.setInt(8,miembro.getFallecido());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Expedición con ID " + miembro.getId() + " actualizada correctamente.");
                return true;
            } else {
                System.out.println("No se encontró la expedición con ID " + miembro.getId() + " para actualizar.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar la expedición: " + e.getMessage());
            return false;
        }
    }

    // 4. Eliminar un miembro por ID
    public boolean deleteMember(int idMiembro) {
        String sql = "DELETE FROM Miembros WHERE id = ?"; // Asumo 'id' es el PK

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMiembro);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Expedición con ID " + idMiembro+ " eliminada correctamente.");
                return true;
            } else {
                System.out.println("No se encontró la expedición con ID " + idMiembro + " para eliminar.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al borrar la expedición: " + e.getMessage());
            return false;
        }
    }

    // 5. Obtener todos los miembros
    public List<Miembros> getAllMembers() {
        List<Miembros> membersList = new ArrayList<>();
        String sql = "SELECT * FROM Miembros";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Miembros miembro = new Miembros();
                miembro.setId(rs.getInt("id"));
                miembro.setNacionalidad(rs.getString("nacionalidad"));
                miembro.setNombre(rs.getString("nombre"));
                miembro.setApellido(rs.getString("apellido"));
                miembro.setSexo(rs.getString("sexo"));
                miembro.setEs_lider(rs.getInt("es_lider"));
                miembro.setEs_staff(rs.getInt("es_staff"));
                miembro.setAno_nacimiento(rs.getInt("año_nacimiento"));
                miembro.setFallecido(rs.getInt("fallecido"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todas las expediciones: " + e.getMessage());
        }
        return membersList;
    }


    // 6. Contar Miembros
    public int getAllMembersCount() {
        String sql = "SELECT COUNT(*) FROM Miembros";
        int count = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar los miembros de las expediciones: " + e.getMessage());
        }
        return count;
    }

}
