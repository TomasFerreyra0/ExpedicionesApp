
package expedicionesapp.ui;
import expedicionesapp.model.Miembros; 
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList; 

public class MiembrosTableModel extends AbstractTableModel {
        private List<Miembros> miembros;
    private String[] columnNames = {
        "ID miembro", "Nacionalidad", "Nombre", "Apellido",
        "Sexo", "Es Lider", "Es Staff", "Año nacimiento", "Fallecido"
    };
     public MiembrosTableModel() {
        this.miembros = new ArrayList<>(); // Inicializa una lista vacía
    }

    public MiembrosTableModel(List<Miembros> miembros) {
        this.miembros = miembros;
    }
        // Método para actualizar los datos del modelo
    public void setMiembros(List<Miembros> miembros) {
        this.miembros = miembros;
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
    }
    public void AddMiembros(Miembros miembro){
         this.miembros.add(miembro);
    // Notifica a la JTable que se ha insertado una nueva fila al final.
    // Esto hace que la tabla se redibuje y muestre la nueva expedición.
    fireTableRowsInserted(miembros.size() - 1, miembros.size() - 1);
    }
    @Override
    public int getRowCount() {
        return miembros.size();
    }

    @Override
    public int getColumnCount() {
       return columnNames.length;
    }
        @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

@Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Miembros miembro = miembros.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> miembro.getId();
            case 1 -> miembro.getNacionalidad();
            case 2 -> miembro.getNombre();
            case 3 -> miembro.getApellido();
            case 4 -> miembro.getSexo();
            case 5 -> miembro.getEs_lider();
            case 6 -> miembro.getEs_staff(); // Devuelve el objeto Date
            case 7 -> miembro.getAno_nacimiento();
            case 8 -> miembro.getFallecido();
            default -> null;
        };
    }
       // Para especificar el tipo de dato de cada columna (útil para renderers)
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 5, 6, 7, 8 -> Integer.class;
            case 1, 2, 3,4 -> String.class;
            default -> Object.class;
        };
    }
}