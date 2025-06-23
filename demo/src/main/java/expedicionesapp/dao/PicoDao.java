
package expedicionesapp.dao;
import expedicionesapp.util.DBConnection;
import expedicionesapp.model.Pico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PicoDao {
        // Muestra UN solo pico por su ID
   public void showPeakById(int id) {
        String sql = "SELECT * FROM Pico WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre del Pico: " + rs.getString("nombrePico"));
                System.out.println("Localización: " + rs.getString("localizacion"));
                System.out.println("Abierto: " + rs.getInt("abierto"));
                System.out.println("Altura: " + rs.getFloat("altura"));
                System.out.println("Cambio Trekking: " + rs.getInt("cambio_trekking"));
                System.out.println("Sin Aprobación: " + rs.getInt("sin_aprobacion"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("ID Host: " + rs.getInt("host"));
                System.out.println("ID Restricciones: " + rs.getInt("restricciones"));
            } else {
                System.out.println("No se encontro ningun pico con el ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error al mostrar el pico: " + e.getMessage());
        }
    }
   
   
       // Modifica los campos de estado del pico
   public void modifyPeak(Pico pico) {
        String sql = """
                UPDATE Picos SET
                    localizacion = ?,
                    nombrePico = ?,
                    abierto = ?,
                    altura = ?,
                    cambio_trekking = ?,
                    sin_aprobacion = ?,
                    estado = ?,
                    host = ?,
                    restricciones = ?
                WHERE id = ?""";

        try (Connection conn = DBConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pico.getLocalizacion());
            stmt.setString(2, pico.getNombrePico());
            stmt.setInt(3, pico.getAbierto());
            stmt.setFloat(4, pico.getAltura());
            stmt.setInt(5, pico.getCambio_trekking());
            stmt.setInt(6, pico.getSin_aprobacion());
            stmt.setString(7, pico.getEstado());
            stmt.setInt(8, pico.getHost());
            stmt.setInt(9, pico.getRestricciones());
            stmt.setInt(10, pico.getId()); // importante que tengas `id` en la clase Pico PARA VERIFICAR EL ID ES ESTE


            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Pico actualizado correctamente.");
            } else {
                System.out.println("No se encontró un pico con ese ID.");
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar el pico: " + e.getMessage());
        }
    }
   
   public Pico getPeakById(int id){
        String sql = "SELECT * FROM Pico WHERE id = ?";
        Pico encontrado = new Pico();

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,id); 
            ResultSet rs = stmt.executeQuery();
             if(rs.next()){
                 encontrado.setLocalizacion(rs.getString("localizacion"));
                 encontrado.setNombrePico(rs.getString("nombrePico"));
                 encontrado.setId(id);
                 encontrado.setAbierto(rs.getInt("abierto"));
                 encontrado.setAltura(rs.getFloat("altura"));
                 encontrado.setCambio_trekking(rs.getInt("cambio_trekking"));
                 encontrado.setSin_aprobacion(rs.getInt("sin_aprobacion"));
                 encontrado.setEstado(rs.getString("estado"));
                 encontrado.setHost(rs.getInt("host"));
                 encontrado.setRestricciones(rs.getInt("restricciones"));
             }
            
        }catch(SQLException e){
                 System.out.println("Ocurrio un error en la obtencion del pico. ERROR: "+e.getMessage());
             }   

        if(encontrado.getEstado().equals("")){
            return null;
        }else{
            return encontrado;
        }
   }
   
   
   // Obtiene una lista de todos los picos
public List<Pico> getAllPeaks() {
    List<Pico> picos = new ArrayList<>();
    String sql = "SELECT * FROM Pico";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) { // No necesitas PreparedStatement.RETURN_GENERATED_KEYS para SELECT

        while (rs.next()) {
            Pico pico = new Pico();
            pico.setId(rs.getInt("id"));
            pico.setLocalizacion(rs.getString("localizacion"));
            pico.setNombrePico(rs.getString("nombrePico"));
            pico.setAbierto(rs.getInt("abierto"));
            pico.setAltura(rs.getFloat("altura"));
            pico.setCambio_trekking(rs.getInt("cambio_trekking"));
            pico.setSin_aprobacion(rs.getInt("sin_aprobacion"));
            pico.setEstado(rs.getString("estado"));
            pico.setHost(rs.getInt("host"));
            pico.setRestricciones(rs.getInt("restricciones"));
            picos.add(pico);
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener todos los picos: " + e.getMessage());
    }
    return picos;
}
   
   
   
}

