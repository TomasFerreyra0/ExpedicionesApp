package expedicionesapp.model;

import java.util.ArrayList;
import java.util.List;

public class Miembros_expediciones {
    private int id;
    private Expediciones id_expediciones;
    private List<Miembros> id_miembros;

    public Miembros_expediciones() {
        this.id_miembros=new ArrayList();
    }

    public Miembros_expediciones(Expediciones id_expediciones, List<Miembros> id_miembros) {
        this.id_expediciones = id_expediciones;
        this.id_miembros = id_miembros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Expediciones getId_expediciones() {
        return id_expediciones;
    }

    public void setId_expediciones(Expediciones id_expediciones) {
        this.id_expediciones = id_expediciones;
    }

    public List<Miembros> getId_miembros() {
        return id_miembros;
    }

    public void setId_miembros(List<Miembros> id_miembros) {
        this.id_miembros = id_miembros;
    }

}
