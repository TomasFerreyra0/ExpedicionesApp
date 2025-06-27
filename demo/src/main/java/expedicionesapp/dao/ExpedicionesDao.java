package expedicionesapp.dao;

import expedicionesapp.util.DBConnection;
import expedicionesapp.model.Expediciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class ExpedicionesDao implements DAO {

   @Override
    public boolean create(Object o) {
        boolean estado = false;
        if(o instanceof Expediciones){
            Expediciones expedicion = (Expediciones) o;
            String sql = """
            INSERT INTO Expediciones (id_Pico, id_accidente, altitud, cupos, conteo_mortalidad, fecha, resultado, motivo, ruta)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """; 
            try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, expedicion.getIdPico());
            stmt.setInt(2, expedicion.getIdAccidente());
            stmt.setFloat(3, expedicion.getAltitud());
            stmt.setInt(4, expedicion.getCupos());
            stmt.setInt(5, expedicion.getConteoMortalidad());
            stmt.setDate(6, new Date(expedicion.getFecha().getTime()));
            stmt.setString(7, expedicion.getResultado());
            stmt.setString(8, expedicion.getMotivo());
            stmt.setString(9, expedicion.getRuta());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Recuperar el ID generado si es auto-incremental
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        expedicion.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Expedición insertada correctamente con ID: " + expedicion.getId());
                estado = true;
            } else {
                System.out.println("No se pudo insertar la expedición.");
                estado = false;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar la expedición: " + e.getMessage());
            estado = false;
        }
        }
        return estado;
    }

    @Override
    public boolean modify(Object o) {
        boolean estado = false;
        if(o instanceof Expediciones){
        Expediciones expedicion = (Expediciones) o;
        String sql = """
            UPDATE Expediciones SET
                id_Pico = ?,
                id_accidente = ?,
                altitud = ?,
                cupos = ?,
                conteo_mortalidad = ?,
                fecha = ?,
                resultado = ?,
                motivo = ?,
                ruta = ?
            WHERE id = ?
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expedicion.getIdPico());
            stmt.setInt(2, expedicion.getIdAccidente());
            stmt.setFloat(3, expedicion.getAltitud());
            stmt.setInt(4, expedicion.getCupos());
            stmt.setInt(5, expedicion.getConteoMortalidad());
            stmt.setDate(6, new Date(expedicion.getFecha().getTime()));
            stmt.setString(7, expedicion.getResultado());
            stmt.setString(8, expedicion.getMotivo());
            stmt.setString(9, expedicion.getRuta());
            stmt.setInt(10, expedicion.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Expedición con ID " + expedicion.getId() + " actualizada correctamente.");
                estado = true;
            } else {
                System.out.println("No se encontró la expedición con ID " + expedicion.getId() + " para actualizar.");
                estado =  false;
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar la expedición: " + e.getMessage());
            estado =  false;
        }
        }
        return estado;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Expediciones WHERE id = ?"; // Asumo 'id' es el PK

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Expedición con ID " + id + " eliminada correctamente.");
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

    @Override
    public void showObjectById(int id) {
        String sql = "SELECT * FROM Expediciones WHERE id = ?"; 

         try (Connection conn = DBConnection.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

             stmt.setInt(1, id);
             ResultSet rs = stmt.executeQuery();

             if (rs.next()) {
                 System.out.println("ID Expedicion: " + rs.getInt("id")); 
                 System.out.println("ID_Pico: " + rs.getInt("id_Pico"));
                 System.out.println("ID_Accidente: " + rs.getInt("id_accidente"));
                 System.out.println("Altitud: " + rs.getFloat("altitud"));
                 System.out.println("Cant.Miembros: " + rs.getInt("cupos"));
                 System.out.println("Conteo Mortalidad: " + rs.getInt("conteo_mortalidad"));
                 System.out.println("Fecha: " + rs.getDate("fecha")); // Usar getDate() si es DATE
                 System.out.println("Resultado: " + rs.getString("resultado"));
                 System.out.println("Motivo: " + rs.getString("motivo"));
                 System.out.println("Ruta: " + rs.getString("ruta"));
             } else {
                 System.out.println("No se encontro ninguna expedicion con el ID " + id);
             }

         } catch (SQLException e) {
             System.err.println("Error al mostrar la expedicion: " + e.getMessage());
         }
    }

    @Override
    public Object getEntityById(int id) {
        String sql = "SELECT * FROM Expediciones WHERE id = ?";
        Expediciones expedicion = null; // Inicializamos a null por si no se encuentra

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    expedicion = new Expediciones();
                    expedicion.setId(rs.getInt("id"));
                    expedicion.setIdPico(rs.getInt("id_Pico"));
                    expedicion.setIdAccidente(rs.getInt("id_accidente"));
                    expedicion.setAltitud(rs.getFloat("altitud"));
                    expedicion.setCupos(rs.getInt("cupos"));
                    expedicion.setConteoMortalidad(rs.getInt("conteo_mortalidad"));
                    expedicion.setFecha(rs.getDate("fecha"));
                    expedicion.setResultado(rs.getString("resultado"));
                    expedicion.setMotivo(rs.getString("motivo"));
                    expedicion.setRuta(rs.getString("ruta"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la expedición por ID: " + e.getMessage());
        }
        return expedicion; // Devuelve el objeto Expediciones o null si no se encontró
    }

    public List<Expediciones> getAllExpeditions() {
        List<Expediciones> expedicionesList = new ArrayList<>();
        String sql = "SELECT * FROM Expediciones";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Expediciones expedicion = new Expediciones();
                expedicion.setId(rs.getInt("id"));
                expedicion.setIdPico(rs.getInt("id_Pico"));
                expedicion.setIdAccidente(rs.getInt("id_accidente"));
                expedicion.setAltitud(rs.getFloat("altitud"));
                expedicion.setCupos(rs.getInt("cupos"));
                expedicion.setConteoMortalidad(rs.getInt("conteo_mortalidad"));
                expedicion.setFecha(rs.getDate("fecha")); 
                expedicion.setResultado(rs.getString("resultado"));
                expedicion.setMotivo(rs.getString("motivo"));
                expedicion.setRuta(rs.getString("ruta"));
                expedicionesList.add(expedicion);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todas las expediciones: " + e.getMessage());
        }
        return expedicionesList;
    }

    public List<Expediciones> getExpeditionsByIdPeak(int idPico) {
        List<Expediciones> expedicionesList = new ArrayList<>();
        String sql = "SELECT * FROM Expediciones WHERE id_Pico = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Expediciones expedicion = new Expediciones();
                    expedicion.setId(rs.getInt("id"));
                    expedicion.setIdPico(rs.getInt("id_Pico"));
                    expedicion.setIdAccidente(rs.getInt("id_accidente"));
                    expedicion.setAltitud(rs.getFloat("altitud"));
                    expedicion.setCupos(rs.getInt("cupos"));
                    expedicion.setConteoMortalidad(rs.getInt("conteo_mortalidad"));
                    expedicion.setFecha(rs.getDate("fecha")); 
                    expedicion.setResultado(rs.getString("resultado"));
                    expedicion.setMotivo(rs.getString("motivo"));
                    expedicion.setRuta(rs.getString("ruta"));
                    expedicionesList.add(expedicion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener expediciones por ID de Pico: " + e.getMessage());
        }
        return expedicionesList;
    }

    public int getAllExpeditionsCount() {
        String sql = "SELECT COUNT(*) FROM Expediciones";
        int count = 0;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar las expediciones: " + e.getMessage());
        }
        return count;
    }

    public List<Expediciones> getExpeditionsByDateRange(Date startDate, Date endDate) {
        List<Expediciones> expedicionesList = new ArrayList<>();
        String sql = "SELECT * FROM Expediciones WHERE fecha BETWEEN ? AND ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Expediciones expedicion = new Expediciones();
                    expedicion.setId(rs.getInt("id"));
                    expedicion.setIdPico(rs.getInt("id_Pico"));
                    expedicion.setIdAccidente(rs.getInt("id_accidente"));
                    expedicion.setAltitud(rs.getFloat("altitud"));
                    expedicion.setCupos(rs.getInt("cupos"));
                    expedicion.setConteoMortalidad(rs.getInt("conteo_mortalidad"));
                    expedicion.setFecha(rs.getDate("fecha"));
                    expedicion.setResultado(rs.getString("resultado"));
                    expedicion.setMotivo(rs.getString("motivo"));
                    expedicion.setRuta(rs.getString("ruta"));
                    expedicionesList.add(expedicion);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener expediciones por rango de fechas: " + e.getMessage());
        }
        return expedicionesList;
    }
    

    
    
}