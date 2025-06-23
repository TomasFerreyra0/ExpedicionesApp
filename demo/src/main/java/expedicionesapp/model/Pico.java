package expedicionesapp.model;

public class Pico {
    private String localizacion;
    private String nombrePico;
    private int id;
    private int abierto;//si o no
    private float altura;
    private int cambio_trekking;//si o no
    private int sin_aprobacion;//si o no
    private String estado;//Escalado sin escalar
    private int host;//
    private int restricciones;

    public Pico(String localizacion, String nombrePico, int id, int abierto, float altura, int cambio_trekking, int sin_aprobacion, String estado, int host, int restricciones) {
        this.localizacion = localizacion;
        this.nombrePico = nombrePico;
        this.id = id;
        this.abierto = abierto;
        this.altura = altura;
        this.cambio_trekking = cambio_trekking;
        this.sin_aprobacion = sin_aprobacion;
        this.estado = estado;
        this.host = host;
        this.restricciones = restricciones;
    }

    public Pico() {
    }
    
    

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getNombrePico() {
        return nombrePico;
    }

    public void setNombrePico(String nombrePico) {
        this.nombrePico = nombrePico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public int getAbierto() {
        return abierto;
    }

    public void setAbierto(int abierto) {
        this.abierto = abierto;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public int getCambio_trekking() {
        return cambio_trekking;
    }

    public void setCambio_trekking(int cambio_trekking) {
        this.cambio_trekking = cambio_trekking;
    }

    public int getSin_aprobacion() {
        return sin_aprobacion;
    }

    public void setSin_aprobacion(int sin_aprobacion) {
        this.sin_aprobacion = sin_aprobacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public int getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(int restricciones) {
        this.restricciones = restricciones;
    }

     
}
