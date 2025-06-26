
package expedicionesapp.ui;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;


public class ScreenFrame extends javax.swing.JFrame {
    public ScreenFrame() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        expedicionesButton = new javax.swing.JButton();
        bannerLabel = new javax.swing.JLabel();
        backgroundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/expedicionesapp/Img/HIMALAYA-LOGO.png"))); // NOI18N
        jPanel1.add(logoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 260, 170));

        expedicionesButton.setBackground(new java.awt.Color(60, 93, 119));
        expedicionesButton.setText("Iniciar Programa");
        expedicionesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expedicionesButtonMouseClicked(evt);
            }
        });
        expedicionesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expedicionesButtonActionPerformed(evt);
            }
        });
        jPanel1.add(expedicionesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, 179, 52));
        jPanel1.add(bannerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, -1, -1));
        jPanel1.add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void expedicionesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expedicionesButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expedicionesButtonActionPerformed

    private JDialog mostrarDialogoCarga(String mensaje) {
        final JDialog dialogo = new JDialog(this, true);
        dialogo.setUndecorated(true);
        dialogo.getContentPane().add(new JLabel(mensaje, javax.swing.SwingConstants.CENTER));
        dialogo.setSize(200, 80);
        dialogo.setLocationRelativeTo(this);
        dialogo.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        return dialogo;
}


    private void expedicionesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expedicionesButtonMouseClicked
 // 1. Crear el diálogo antes
    JDialog dialogo = mostrarDialogoCarga("Iniciando programa...");

    // 2. Mostrar el diálogo en otro hilo
    Thread hiloDialogo = new Thread(() -> dialogo.setVisible(true));
    hiloDialogo.start();

    // 3. Ejecutar carga con retardo mínimo
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() {
            try {
                // Simular que tarda un poquito (solo visual, podés ajustar)
                Thread.sleep(800);
            } catch (InterruptedException e) {
                // Ignorar
            }
            return null;
        }
        @Override
        protected void done() {
            dialogo.dispose();
            ExpedicionesFrame expedicionesFrame = new ExpedicionesFrame();
            expedicionesFrame.setLocationRelativeTo(null);
            expedicionesFrame.setVisible(true);
            ScreenFrame.this.dispose();
        }
    };
    worker.execute();
    }//GEN-LAST:event_expedicionesButtonMouseClicked

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
            java.util.logging.Logger.getLogger(ScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ScreenFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLabel;
    private javax.swing.JLabel bannerLabel;
    private javax.swing.JButton expedicionesButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel logoLabel;
    // End of variables declaration//GEN-END:variables
}
