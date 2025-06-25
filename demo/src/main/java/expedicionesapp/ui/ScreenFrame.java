
package expedicionesapp.ui;


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
        jPanel1.add(logoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 120, 300, 250));

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
        jPanel1.add(expedicionesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, 179, 52));
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

    private void expedicionesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expedicionesButtonMouseClicked
        // TODO add your handling code here:
        // 1. Ocultar la ventana actual (ScreenFrame)
        this.setVisible(false);
        

        // 2. Crear una nueva instancia del JFrame ExpedicionesFrame
        ExpedicionesFrame expedicionesFrame = new ExpedicionesFrame();

        // 3. Hacer visible el nuevo JFrame
        expedicionesFrame.setVisible(true);

        // Opcional: Centrar la nueva ventana en la pantalla
        expedicionesFrame.setLocationRelativeTo(null);
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
