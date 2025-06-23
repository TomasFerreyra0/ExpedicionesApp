package expedicionesapp.model;

import java.util.Date;

public class Expediciones {
    private int id;
    private int idPico;
    private int idAccidente;
    private float altitud;
    private int cupos;
    private int conteoMortalidad;
    private Date fecha;
    private String resultado;
    private String motivo;
    private String ruta;

    public Expediciones() {
    }

    public Expediciones(int id, int idPico, int idAccidente, float altitud, int cupos, int conteoMortalidad, Date fecha, String resultado, String motivo, String ruta) {
        this.id = id;
        this.idPico = idPico;
        this.idAccidente = idAccidente;
        this.altitud = altitud;
        this.cupos = cupos;
        this.conteoMortalidad = conteoMortalidad;
        this.fecha = fecha;
        this.resultado = resultado;
        this.motivo = motivo;
        this.ruta = ruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPico() {
        return idPico;
    }

    public void setIdPico(int idPico) {
        this.idPico = idPico;
    }

    public int getIdAccidente() {
        return idAccidente;
    }

    public void setIdAccidente(int idAccidente) {
        this.idAccidente = idAccidente;
    }

    public float getAltitud() {
        return altitud;
    }

    public void setAltitud(float altitud) {
        this.altitud = altitud;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public int getConteoMortalidad() {
        return conteoMortalidad;
    }

    public void setConteoMortalidad(int conteoMortalidad) {
        this.conteoMortalidad = conteoMortalidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    
}
