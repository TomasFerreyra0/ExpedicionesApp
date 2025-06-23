package expedicionesapp;
import expedicionesapp.util.DBConnection;
import expedicionesapp.dao.PicoDao;
import expedicionesapp.dao.MiembrosDao;
import expedicionesapp.dao.ExpedicionesDao;
import expedicionesapp.ui.ScreenFrame;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        /*PicoDao dao=new PicoDao();
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
/*
lo que necesitamos:
	id expedicion, id fecha, id accidente, id miembro, id pico, id resultado, id ruta, altitud, cupos, conteo mortalidad.

Como lo tenemos:
	id_expedicion,codigo_pico,Año,host,Temporada,nombre_Temporada,Pais,Lideres,Exito,motivo_terminacion,altitud,cupo,Muertes,Accidente,Ruta,Fecha,Dia_Mas_Alto

El orden a seguir para crear las bases de datos va a ser el siguente.

1-[dbo].[Fecha] hecho.
2-[dbo].[Accidente] hecho.
3-[dbo].[Nacionalidad] (para miembros.) hecho.
4-[dbo].[Miembros] hecho.
5-[dbo].[Host] hecho.
6-[dbo].[Localizacion] hecho.
7-[dbo].[Pico] hecho.
8-[dbo].[Motivo] hecho.
9-[dbo].[Resultado] hecho
10-[dbo].[Ruta] hecho.
11-[dbo].[Expediciones] hecho.


1- Para fecha necesitamos:
	fecha será sacado de la tabla expediciones.
	fecha (date), año (int), temporada (int [verano = 2, otoño = 3, invierno = 4, primavera = 1, ya esta asi, no se pregunta.. xd]);
	Fecha,Año,Temporada
2- para Accidente

4- para miembros vamos a necesitar:
    id_nacionalidad, tenemos que comparar el string del campo nacionalidad en miembros.csv, con el campo descripcion del csv Nacionalidad.csv y colocar el id del mismo registro donde encuentre la coincidencia.
    nombre,apellido,sexo,es_lider,es_staff,año_nacimiento,fallecido. los va a sacar directamente de los campos de miembros.csv: id_expedicion,nombre,apellido,sexo,año_nacimiento,es_lider,contratado,exito_cumbre,fallecido,nacionalidad,año.

7- para picos vamos a necesitar:
    id_localizacion, tenemos que comparar el string del campo Localizacion, en picos.csv, con el campo descripcion del Localizacion.csv y colocar el id del mismo registro donde encuentre la coincidencia.
    todo el resto lo saca de los campos de picos.csv
    id_pico,id_localizacion,abierto,altura,cambio_trekking,sin_aprobacion,año_de_cambio,estado,restricciones.
    id_pico es autoincremental,
    abierto,sin_aprobacion,cambio_trekking son bits.
    estado,restricciones son strings

11- para expediciones, necesitamos:
id_expedicion: autoincremental, commienza en 1.
id_fecha: toma el campo Fecha, de expediciones.csv, y lo compara con las fechas del archivo Fecha.csv, cuando encuentra la coincidencia, coloca el id correspondiente.
id_accidente: toma el campo Accidente, de expediciones.csv, y lo compara con descripcion del archivo Accidente.csv, cuando encuentra la coincidencia, coloca el id correspondiente.
id_miembro: toma el campo id_expedicion, de expediciones.csv, y lo compara con id_expedicion de Miembros.csv, cuando encuentra la coincidencia, coloca el id correspondiente.
id_pico:toma el campo codigo_pico, de expediciones.csv, y lo compara con codigo_pico del archivo Pico.csv, cuando encuentra la coincidencia, coloca el id correspondiente.
id_resultado: toma los campos Exito y motivo_terminacion de expediciones.csv y los compara con [Exito con descripcion de Resultado.csv] y [motivo_terminacion con descripcion de Motivo.csv]
id_ruta: toma el campo Ruta, de expediciones.csv, y lo compara con descripcion del archivo Ruta.csv, cuando encuentra la coincidencia, coloca el id correspondiente.
altitud: lo toma del campo Altura de expediciones.csv
    cupos: lo toma del campo cupo de expediciones.csv
    conteo_mortalidad: lo toma del campo Muertes de expediciones.csv
    nos tiene que quedar:
    id_expedicion,id_fecha,id_accidente,id_miembro,id_pico,id_resultado,id_ruta,altitud,cupos,conteo_mortalidad
como estamos utilizando datawarehouse para el proyecto, la idea es que guarde multiples registros "iguales", pero para cada uno de los miembros que participaron de esa expedicion.
esto se puede lograr, ya que en Miembros.csv, tenemos el id_expedicion, y en expediciones.csv tenemos el mismo, asi que tendremos que crear el "mismo" registro por cada coincidencia que encuentre.
otra consideracion a tomar, es el campo id_resultado, en Resultado.csv, tenemos los campos id_resultado,descripcion,id_motivo. en este ultimo, id_motivo hace referencia al campo id_motivo del Motivo.csv,
por lo que van a haber habiertos 3 archivos al mismo tiempo.


consideraciones antes de realizarlo:
    tenemos que agregarle el campo id_expedicion, al Miembro.csv, al menos momentaneamente, para realizar el emparejamiento, despues se saca.
    tenemos que agregarle el campo codigo_pico al Picos.csv, al menos momentaneamente, para realizar el emparejamiento, despues se saca.




*/  