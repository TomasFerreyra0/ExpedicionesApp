/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package expedicionesapp.ui;

import expedicionesapp.dao.ExpedicionesDao; 
import expedicionesapp.model.Expediciones; 
import java.util.List;
import javax.swing.table.DefaultTableModel; 
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField; 
import java.text.SimpleDateFormat; 
import java.util.Date;

/**
 *
 * @author 54224
 */
public class ExpedicionesFrame extends javax.swing.JFrame {
    

    // Declara una instancia de ExpedicionesDao
    private ExpedicionesDao expedicionesDao;
    
    /**
     * Creates new form Expediciones
     */
    public ExpedicionesFrame() {
        initComponents();
        expedicionesDao = new ExpedicionesDao(); // Inicializa el DAO
        cargarTablaExpediciones(); // Llama al método para cargar la tabla al iniciar la ventana
        
        expedicionesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = expedicionesTable.getSelectedRow();
                    if (selectedRow != -1) { 
                        seleccionarExpedicion(expedicionesTable, picoIdTxt, accidenteIdTxt,
                                              altitudTxt, cuposTxt, mortalidadTxt, fechaTxt,
                                              resultadoTxt, motivoTxt, rutaTxt);
                    } else {
                        // Opcional: Limpiar campos si no hay selección
                        // limpiarCampos(); // Si tienes un método para limpiar
                    }
                }
            }
        });
    }
    
    
    
    private void cargarTablaExpediciones() {
        // Define las columnas de la tabla
        String[] columnNames = {"ID", "Pico ID", "Accidente ID", "Altitud", "Cupos", 
                                "Mortalidad", "Fecha", "Resultado", "Motivo", "Ruta"};
        
        // Crea un DefaultTableModel. El segundo argumento es el número inicial de filas (0)
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); 
        
        // Obtiene la lista de expediciones del DAO
        List<Expediciones> listaExpediciones = expedicionesDao.getAllExpeditions();
        
        // Itera sobre la lista y añade cada expedición como una fila a la tabla
        for (Expediciones expedicion : listaExpediciones) {
            Object[] rowData = new Object[10]; // 10 columnas
            rowData[0] = expedicion.getId();
            rowData[1] = expedicion.getIdPico();
            rowData[2] = expedicion.getIdAccidente();
            rowData[3] = expedicion.getAltitud();
            rowData[4] = expedicion.getCupos();
            rowData[5] = expedicion.getConteoMortalidad();
            rowData[6] = expedicion.getFecha();
            rowData[7] = expedicion.getResultado();
            rowData[8] = expedicion.getMotivo();
            rowData[9] = expedicion.getRuta();
            
            model.addRow(rowData);
        }
        
        // Asigna el modelo al JTable
        expedicionesTable.setModel(model); 
    }

    private void seleccionarExpedicion(javax.swing.JTable paramTableExpediciones,
                                       javax.swing.JTextField paramPicoId,
                                       javax.swing.JTextField paramAccidenteId,
                                       javax.swing.JTextField paramAltitud,
                                       javax.swing.JTextField paramCupos,
                                       javax.swing.JTextField paramMortalidad,
                                       javax.swing.JTextField paramFecha,
                                       javax.swing.JTextField paramResultado,
                                       javax.swing.JTextField paramMotivo,
                                       javax.swing.JTextField paramRuta) {
        try {
            int fila = paramTableExpediciones.getSelectedRow();

            if (fila >= 0) {
                paramPicoId.setText(paramTableExpediciones.getValueAt(fila, 1).toString());      // Columna 1 (Pico ID)
                paramAccidenteId.setText(paramTableExpediciones.getValueAt(fila, 2).toString()); // Columna 2 (Accidente ID)
                paramAltitud.setText(paramTableExpediciones.getValueAt(fila, 3).toString());
                paramCupos.setText(paramTableExpediciones.getValueAt(fila, 4).toString());
                paramMortalidad.setText(paramTableExpediciones.getValueAt(fila, 5).toString());

                // La fecha requiere formateo si no es String en la tabla
                Object fechaObj = paramTableExpediciones.getValueAt(fila, 6);
                if (fechaObj instanceof java.util.Date || fechaObj instanceof java.sql.Date) {
                    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    paramFecha.setText(dateFormat.format((java.util.Date) fechaObj));
                } else {
                    paramFecha.setText(fechaObj != null ? fechaObj.toString() : "");
                }
                
                paramResultado.setText(paramTableExpediciones.getValueAt(fila, 7).toString());
                paramMotivo.setText(paramTableExpediciones.getValueAt(fila, 8).toString());
                paramRuta.setText(paramTableExpediciones.getValueAt(fila, 9).toString());
            } else {
                // Limpia todos los campos si no hay selección
                paramPicoId.setText("");
                paramAccidenteId.setText("");
                paramAltitud.setText("");
                paramCupos.setText("");
                paramMortalidad.setText("");
                paramFecha.setText("");
                paramResultado.setText("");
                paramMotivo.setText("");
                paramRuta.setText("");
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al seleccionar expedición: " + e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nuevaExpedicionBtn = new javax.swing.JButton();
        volverBtn = new javax.swing.JButton();
        cambiarResultadoBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        expedicionesTable = new javax.swing.JTable();
        expedicionesProcesoLabel = new javax.swing.JLabel();
        picoIdLabel = new javax.swing.JLabel();
        accidenteIdLabel = new javax.swing.JLabel();
        altitudLabel = new javax.swing.JLabel();
        cuposLabel = new javax.swing.JLabel();
        mortalidadLabel = new javax.swing.JLabel();
        fechaLabel = new javax.swing.JLabel();
        resultadoLabel = new javax.swing.JLabel();
        motivoLabel = new javax.swing.JLabel();
        picoIdTxt = new javax.swing.JTextField();
        rutaElegidaLabel = new javax.swing.JLabel();
        altitudTxt = new javax.swing.JTextField();
        accidenteIdTxt = new javax.swing.JTextField();
        cuposTxt = new javax.swing.JTextField();
        mortalidadTxt = new javax.swing.JTextField();
        fechaTxt = new javax.swing.JTextField();
        resultadoTxt = new javax.swing.JTextField();
        rutaTxt = new javax.swing.JTextField();
        motivoTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nuevaExpedicionBtn.setText("Nueva Expedicion");
        nuevaExpedicionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevaExpedicionBtnActionPerformed(evt);
            }
        });

        volverBtn.setText("Volver");
        volverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverBtnActionPerformed(evt);
            }
        });

        cambiarResultadoBtn.setText("Cambiar Resultado");
        cambiarResultadoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarResultadoBtnActionPerformed(evt);
            }
        });

        expedicionesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(expedicionesTable);

        expedicionesProcesoLabel.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        expedicionesProcesoLabel.setText("Expediciones en proceso");

        picoIdLabel.setText("Pico ID:");

        accidenteIdLabel.setText("Accidente ID:");

        altitudLabel.setText("Altitud:");

        cuposLabel.setText("Cupos:");

        mortalidadLabel.setText("Mortalidad");

        fechaLabel.setText("Fecha: ");

        resultadoLabel.setText("Resultado:");

        motivoLabel.setText("Motivo:");

        picoIdTxt.setEditable(false);
        picoIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picoIdTxtActionPerformed(evt);
            }
        });

        rutaElegidaLabel.setText("Ruta Elegida: ");

        altitudTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altitudTxtActionPerformed(evt);
            }
        });

        accidenteIdTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accidenteIdTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(expedicionesProcesoLabel)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(nuevaExpedicionBtn)
                                .addGap(58, 58, 58)
                                .addComponent(volverBtn)
                                .addGap(55, 55, 55)
                                .addComponent(cambiarResultadoBtn))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rutaElegidaLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(rutaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(accidenteIdLabel)
                                    .addComponent(picoIdLabel)
                                    .addComponent(altitudLabel)
                                    .addComponent(cuposLabel)
                                    .addComponent(mortalidadLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(picoIdTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(accidenteIdTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(altitudTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuposTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mortalidadTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resultadoLabel)
                                    .addComponent(motivoLabel)
                                    .addComponent(fechaLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resultadoTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(motivoTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fechaTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(expedicionesProcesoLabel)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(picoIdLabel)
                            .addComponent(picoIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accidenteIdLabel)
                            .addComponent(accidenteIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(altitudTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cuposTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cuposLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mortalidadTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mortalidadLabel)))
                            .addComponent(altitudLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fechaLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resultadoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(resultadoLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(motivoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(motivoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rutaElegidaLabel)
                            .addComponent(rutaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nuevaExpedicionBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volverBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cambiarResultadoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 740, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    private void accidenteIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accidenteIdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accidenteIdTxtActionPerformed

    private void altitudTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altitudTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_altitudTxtActionPerformed

    private void picoIdTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_picoIdTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_picoIdTxtActionPerformed

    private void cambiarResultadoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarResultadoBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cambiarResultadoBtnActionPerformed

    private void volverBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_volverBtnActionPerformed

    private void nuevaExpedicionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevaExpedicionBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nuevaExpedicionBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Expediciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExpedicionesFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accidenteIdLabel;
    private javax.swing.JTextField accidenteIdTxt;
    private javax.swing.JLabel altitudLabel;
    private javax.swing.JTextField altitudTxt;
    private javax.swing.JButton cambiarResultadoBtn;
    private javax.swing.JLabel cuposLabel;
    private javax.swing.JTextField cuposTxt;
    private javax.swing.JLabel expedicionesProcesoLabel;
    private javax.swing.JTable expedicionesTable;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JTextField fechaTxt;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel mortalidadLabel;
    private javax.swing.JTextField mortalidadTxt;
    private javax.swing.JLabel motivoLabel;
    private javax.swing.JTextField motivoTxt;
    private javax.swing.JButton nuevaExpedicionBtn;
    private javax.swing.JLabel picoIdLabel;
    private javax.swing.JTextField picoIdTxt;
    private javax.swing.JLabel resultadoLabel;
    private javax.swing.JTextField resultadoTxt;
    private javax.swing.JLabel rutaElegidaLabel;
    private javax.swing.JTextField rutaTxt;
    private javax.swing.JButton volverBtn;
    // End of variables declaration//GEN-END:variables
}
