
package expedicionesapp.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
     // Datos de conexión (cambiá estos por los tuyos)
    private static final String URL = "jdbc:sqlserver:// 206.189.237.186:14111;databaseName=HimalayaProgra;encrypt=false";///Cambiar datos de user,password
    private static final String USER =  "racunia";
    private static final String PASSWORD ="Cba.654321";

    // Objeto conexión único (singleton)
    private static Connection connection = null;

    // Constructor privado para que no se instancie la clase
    private DBConnection() {
    }

    // Método que devuelve una conexión activa y válida
    public static Connection getConnection() throws SQLException {
        // Si no hay conexión o está cerrada, se crea una nueva
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Error al conectar: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    // Método para cerrar la conexión si está abierta
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
