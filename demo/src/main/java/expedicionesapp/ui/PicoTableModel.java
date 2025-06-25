package expedicionesapp.ui;

import expedicionesapp.model.Pico;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PicoTableModel extends AbstractTableModel {

    private List<Pico> picos;
    private final String[] columnas = {
        "ID", "Nombre", "Localización", "Abierto", "Altura",
        "Cambio Trekking", "Sin Aprobación", "Estado", "Host", "Restricciones"
    };

    public PicoTableModel(List<Pico> picos) {
        this.picos = picos;
    }

    @Override
    public int getRowCount() {
        return picos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pico pico = picos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> pico.getId();
            case 1 -> pico.getNombrePico();
            case 2 -> pico.getLocalizacion();
            case 3 -> pico.getAbierto();
            case 4 -> pico.getAltura();
            case 5 -> pico.getCambio_trekking();
            case 6 -> pico.getSin_aprobacion();
            case 7 -> pico.getEstado();
            case 8 -> pico.getHost();
            case 9 -> pico.getRestricciones();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    // 👇 Esta función la usa PicoFrame para actualizar toda la tabla
    public void setPicos(List<Pico> nuevosPicos) {
        this.picos = nuevosPicos;
        fireTableDataChanged();
    }

    // 👇 Esta función la usa PicoFrame para obtener un objeto específico
    public Pico getPicoAt(int row) {
        return picos.get(row);
    }
}