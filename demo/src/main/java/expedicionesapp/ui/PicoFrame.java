package expedicionesapp.ui;

import expedicionesapp.dao.PicoDao;
import expedicionesapp.model.Pico;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PicoFrame extends javax.swing.JFrame {

    private JTable tablaPicos;
    private PicoTableModel modelo;
    private final PicoDao dao;

    public PicoFrame() {
        setTitle("Listado de Picos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        dao = new PicoDao();
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        modelo = new PicoTableModel(dao.getAllPeaks());
        tablaPicos = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tablaPicos);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel();
        JButton agregarBtn = new JButton("Agregar");
        JButton eliminarBtn = new JButton("Eliminar");
        JButton modificarBtn = new JButton("Modificar");
        JButton volverBtn = new JButton("Volver");

        agregarBtn.addActionListener(e -> agregarPico());
        eliminarBtn.addActionListener(e -> eliminarPico());
        modificarBtn.addActionListener(e -> modificarPico());
        volverBtn.addActionListener(e -> dispose());

        buttonPanel.add(agregarBtn);
        buttonPanel.add(eliminarBtn);
        buttonPanel.add(modificarBtn);
        buttonPanel.add(volverBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void agregarPico() {
        Pico nuevo = pedirDatosPico(null);
        if (nuevo != null) {
            dao.create(nuevo);
            actualizarTabla();
        }
    }

    private void eliminarPico() {
        int fila = tablaPicos.getSelectedRow();
        if (fila >= 0) {
            Pico pico = modelo.getPicoAt(fila);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar pico: " + pico.getNombrePico() + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                eliminarPicoPorId(pico.getId());
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pico para eliminar.");
        }
    }

    private void modificarPico() {
        int fila = tablaPicos.getSelectedRow();
        if (fila >= 0) {
            Pico original = modelo.getPicoAt(fila);
            Pico modificado = pedirDatosPico(original);
            if (modificado != null) {
                dao.modify(modificado);
                actualizarTabla();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pico para modificar.");
        }
    }

    private void actualizarTabla() {
        modelo.setPicos(dao.getAllPeaks());
        modelo.fireTableDataChanged();
    }

    private void eliminarPicoPorId(int id) {
        String sql = "DELETE FROM Pico WHERE id = ?";
        try (var conn = expedicionesapp.util.DBConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

    // Diálogo para crear/modificar un pico
    private Pico pedirDatosPico(Pico original) {
        JTextField nombreField = new JTextField();
        JTextField locField = new JTextField();
        JTextField abiertoField = new JTextField();
        JTextField alturaField = new JTextField();
        JTextField trekkingField = new JTextField();
        JTextField sinAprobField = new JTextField();
        JTextField estadoField = new JTextField();
        JTextField hostField = new JTextField();
        JTextField restricField = new JTextField();

        if (original != null) {
            nombreField.setText(original.getNombrePico());
            locField.setText(original.getLocalizacion());
            abiertoField.setText(String.valueOf(original.getAbierto()));
            alturaField.setText(String.valueOf(original.getAltura()));
            trekkingField.setText(String.valueOf(original.getCambio_trekking()));
            sinAprobField.setText(String.valueOf(original.getSin_aprobacion()));
            estadoField.setText(original.getEstado());
            hostField.setText(String.valueOf(original.getHost()));
            restricField.setText(String.valueOf(original.getRestricciones()));
        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Nombre:")); panel.add(nombreField);
        panel.add(new JLabel("Localización:")); panel.add(locField);
        panel.add(new JLabel("Abierto (1/0):")); panel.add(abiertoField);
        panel.add(new JLabel("Altura:")); panel.add(alturaField);
        panel.add(new JLabel("Cambio Trekking (1/0):")); panel.add(trekkingField);
        panel.add(new JLabel("Sin Aprobación (1/0):")); panel.add(sinAprobField);
        panel.add(new JLabel("Estado:")); panel.add(estadoField);
        panel.add(new JLabel("Host ID:")); panel.add(hostField);
        panel.add(new JLabel("Restricciones ID:")); panel.add(restricField);

        int result = JOptionPane.showConfirmDialog(this, panel, original == null ? "Agregar Pico" : "Modificar Pico", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Pico pico = new Pico();
            if (original != null) pico.setId(original.getId());
            pico.setNombrePico(nombreField.getText());
            pico.setLocalizacion(locField.getText());
            pico.setAbierto(Integer.parseInt(abiertoField.getText()));
            pico.setAltura(Float.parseFloat(alturaField.getText()));
            pico.setCambio_trekking(Integer.parseInt(trekkingField.getText()));
            pico.setSin_aprobacion(Integer.parseInt(sinAprobField.getText()));
            pico.setEstado(estadoField.getText());
            pico.setHost(Integer.parseInt(hostField.getText()));
            pico.setRestricciones(Integer.parseInt(restricField.getText()));
            return pico;
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PicoFrame().setVisible(true));
    }
}
