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

public class MiembrosDao implements DAO {
    //insertar un nuevo miembro
    @Override
    public boolean create(Object o) {
        boolean estado = false;
        if(o instanceof Miembros){
            Miembros miembro = (Miembros) o;
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
                estado = true;
            } else {
                System.out.println("No se pudo insertar la expedición.");
                estado =  false;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar la expedición: " + e.getMessage());
            estado = false;
        }
        }
        return estado;
    }

    //modificar un miembro existente
    @Override
    public boolean modify(Object o) {
        boolean estado = false;
        if(o instanceof Miembros){
            Miembros miembro = (Miembros) o;
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
                estado = true;
            } else {
                System.out.println("No se encontró la expedición con ID " + miembro.getId() + " para actualizar.");
                estado = false;
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar la expedición: " + e.getMessage());
            estado = false;
        }
        }
        return estado;
    }

    //eliminar un miembro por id
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Miembros WHERE id = ?"; // Asumo 'id' es el PK

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Expedición con ID " + id+ " eliminada correctamente.");
                return true;
            } else {
                System.out.println("No se encontró la expedición con ID " + id + " para eliminar.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al borrar la expedición: " + e.getMessage());
            return false;
        }
    }

    //obtener un miembro por id
    @Override
    public Object getEntityById(int id) {
        String sql = "SELECT * FROM Miembros WHERE id = ?";
        Miembros miembro = null; // Inicializamos a null por si no se encuentra

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
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

    @Override
    public void showObjectById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    //Obtener todos los miembros
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

    //Contar Miembros
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
    
    public boolean tieneMiembrosAsociados(int idExpedicion) {
    String sql = "SELECT COUNT(*) FROM miembros_expediciones WHERE id_expedicion = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idExpedicion);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (SQLException e) {
        System.err.println("Error al verificar miembros asociados: " + e.getMessage());
    }

    return false;
}
    
    //Busca si el miembro existe
    public int miembroYaExiste(Miembros miembro) throws SQLException {
    Connection conn = DBConnection.getConnection();
    String sql = "SELECT id FROM miembros WHERE " +
                 "nombre = ? AND apellido = ? AND nacionalidad = ? AND sexo = ? " +
                 "AND es_lider = ? AND es_staff = ? AND año_nacimiento = ? AND fallecido = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, miembro.getNombre());
        stmt.setString(2, miembro.getApellido());
        stmt.setString(3, miembro.getNacionalidad());
        stmt.setString(4, miembro.getSexo());
        stmt.setInt(5, miembro.getEs_lider());
        stmt.setInt(6, miembro.getEs_staff());
        stmt.setInt(7, miembro.getAno_nacimiento());
        stmt.setInt(8, miembro.getFallecido());

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");//Devuelve el id encontrado
        }else{
            return -1;//devuelve-1 porq no encontro nada
        }
    }
    }
    
    //Devuelve el ultimo miembro
    public Miembros obtenerUltimoMiembro() throws SQLException {
    Connection conn = DBConnection.getConnection();
    String sql = "SELECT TOP 1 * FROM Miembros ORDER BY id DESC";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Miembros mmiembroB = new Miembros();
            mmiembroB.setId(rs.getInt("id"));
            mmiembroB.setNombre(rs.getString("nombre"));
            mmiembroB.setApellido(rs.getString("apellido"));
            mmiembroB.setNacionalidad(rs.getString("nacionalidad"));
            mmiembroB.setSexo(rs.getString("sexo"));
            mmiembroB.setEs_lider(rs.getInt("es_lider"));
            mmiembroB.setEs_staff(rs.getInt("es_staff"));
            mmiembroB.setAno_nacimiento(rs.getInt("año_nacimiento"));
            mmiembroB.setFallecido(rs.getInt("fallecido"));
            return mmiembroB;
        }else{return null;}
    }
    }
    
    //Inserta miembro pero devolviendo el objeto que inserto
    public Miembros insertMember2(Miembros miembro) {
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
                return miembro;
            } else {
                System.out.println("No se pudo insertar la expedición.");
                return null ;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar la expedición: " + e.getMessage());
            return null;
        }
    }
}
