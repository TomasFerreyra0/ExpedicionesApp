package expedicionesapp;
import expedicionesapp.util.DBConnection;
/*import expedicionesapp.dao.PicoDao;
import expedicionesapp.dao.MiembrosDao;
import expedicionesapp.dao.ExpedicionesDao;*/
import expedicionesapp.ui.ScreenFrame;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        /*PicoDao dao=new PicoDao(); Probamos las cosas
        MiembrosDao daom=new MiembrosDao();
        ExpedicionesDao daoe=new ExpedicionesDao();*/
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ScreenFrame screenFrame = new ScreenFrame();
                screenFrame.setVisible(true);
                screenFrame.setLocationRelativeTo(null); // Para centrar la ventana
                screenFrame.setTitle("Himalayan Travel Boutique");
                screenFrame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            }
        });
        
        try{
            Connection conn=DBConnection.getConnection();
            if(conn!=null && !conn.isClosed()){
                System.err.println("Conexion establecida");
            }
        }catch(SQLException e){
            System.err.println("Error al probar Conexion"+e.getMessage());
        }finally{
            DBConnection.closeConnection();
        }
    }
}