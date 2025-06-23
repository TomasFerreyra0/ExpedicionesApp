package expedicionesapp.ui; 

import expedicionesapp.model.Expediciones; 
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList; 

public class ExpedicionesTableModel extends AbstractTableModel {

    private List<Expediciones> expediciones;
    private String[] columnNames = {
        "ID", "ID Pico", "ID Accidente", "Altitud", "Cupos",
        "Mortalidad", "Fecha", "Resultado", "Motivo", "Ruta"
    };

    public ExpedicionesTableModel() {
        this.expediciones = new ArrayList<>(); // Inicializa una lista vacía
    }

    public ExpedicionesTableModel(List<Expediciones> expediciones) {
        this.expediciones = expediciones;
    }

    // Método para actualizar los datos del modelo
    public void setExpediciones(List<Expediciones> expediciones) {
        this.expediciones = expediciones;
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
    }

    public void addExpedicion(Expediciones expedicion) {
    this.expediciones.add(expedicion);
    // Notifica a la JTable que se ha insertado una nueva fila al final.
    // Esto hace que la tabla se redibuje y muestre la nueva expedición.
    fireTableRowsInserted(expediciones.size() - 1, expediciones.size() - 1);
}
    
    @Override
    public int getRowCount() {
        return expediciones.size();
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
        Expediciones expedicion = expediciones.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> expedicion.getId();
            case 1 -> expedicion.getIdPico();
            case 2 -> expedicion.getIdAccidente();
            case 3 -> expedicion.getAltitud();
            case 4 -> expedicion.getCupos();
            case 5 -> expedicion.getConteoMortalidad();
            case 6 -> expedicion.getFecha(); // Devuelve el objeto Date
            case 7 -> expedicion.getResultado();
            case 8 -> expedicion.getMotivo();
            case 9 -> expedicion.getRuta();
            default -> null;
        };
    }

    // Para especificar el tipo de dato de cada columna (útil para renderers)
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 1, 2, 4, 5 -> Integer.class;
            case 3 -> Float.class;
            case 6 -> java.util.Date.class; // O String.class si manejas la fecha como String
            case 7, 8, 9 -> String.class;
            default -> Object.class;
        };
    }
}