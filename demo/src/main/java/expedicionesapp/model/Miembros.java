
package expedicionesapp.model;

public class Miembros {
    private int id;
    private String nacionalidad;
    private String nombre;
    private String apellido;
    private String sexo;
    private int es_lider;
    private int es_staff;
    private int ano_nacimiento;
    private int fallecido;

    public Miembros() {
    }

    public Miembros(int id, String nacionalidad, String nombre, String apellido, String sexo, int es_lider, int es_staff, int ano_nacimiento, int fallecido) {
        this.id = id;
        this.nacionalidad = nacionalidad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.es_lider = es_lider;
        this.es_staff = es_staff;
        this.ano_nacimiento = ano_nacimiento;
        this.fallecido = fallecido;
    }

    public int getEs_lider() {
        return es_lider;
    }

    public void setEs_lider(int es_lider) {
        this.es_lider = es_lider;
    }

    public int getEs_staff() {
        return es_staff;
    }

    public void setEs_staff(int es_staff) {
        this.es_staff = es_staff;
    }

    public int getFallecido() {
        return fallecido;
    }

    public void setFallecido(int fallecido) {
        this.fallecido = fallecido;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id_miembro) {
        this.id = id_miembro;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


    public int getAno_nacimiento() {
        return ano_nacimiento;
    }

    public void setAno_nacimiento(int ano_nacimiento) {
        this.ano_nacimiento = ano_nacimiento;
    }


    
}
