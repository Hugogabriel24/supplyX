/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PagesAdmin;

import java.awt.Toolkit;

/**
 *
 * @author Hugo Gabriel
 */
public class visuAcessos extends javax.swing.JFrame {

    /**
     * Creates new form visuAcessos
     */
    public visuAcessos() {
        initComponents();
        setIcon();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/iconeCar64x.png")));
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txServico = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableService = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        urlAcesso = new javax.swing.JTextField();
        passwordAcesso = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txServico2 = new javax.swing.JTextField();
        ShowHide = new javax.swing.JToggleButton();
        ShowHide1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("SENHA");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 460, -1, -1));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("LOGIN");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 399, -1, -1));
        jPanel1.add(txServico, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 424, 286, -1));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("VISUALIZADOR DE CONTAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de contas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N

        tableService.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "DESCRICAO", "LOGIN"
            }
        ));
        tableService.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableServiceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableService);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 49, -1, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("URL DE ACESSO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 338, -1, -1));
        jPanel1.add(urlAcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 363, 286, -1));

        passwordAcesso.setEchoChar('#');
        jPanel1.add(passwordAcesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 485, 286, -1));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("DESCRIÇÃO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 280, -1, -1));
        jPanel1.add(txServico2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 305, 286, -1));

        ShowHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/viewPass.png"))); // NOI18N
        ShowHide.setContentAreaFilled(false);
        ShowHide.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ShowHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHideActionPerformed(evt);
            }
        });
        jPanel1.add(ShowHide, new org.netbeans.lib.awtextra.AbsoluteConstraints(304, 483, -1, -1));

        ShowHide1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/enviar-mensagem (1).png"))); // NOI18N
        ShowHide1.setContentAreaFilled(false);
        ShowHide1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ShowHide1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHide1ActionPerformed(evt);
            }
        });
        jPanel1.add(ShowHide1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 28, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableServiceMouseClicked
        // TODO add your handling code here:
        //        setarCamposTabela();
    }//GEN-LAST:event_tableServiceMouseClicked

    private void ShowHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHideActionPerformed
        // TODO add your handling code here:

        if (ShowHide.isSelected()) {
            passwordAcesso.setEchoChar('\u0000');
        } else {
            passwordAcesso.setEchoChar('*');
        }
    }//GEN-LAST:event_ShowHideActionPerformed

    private void ShowHide1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHide1ActionPerformed
        // TODO add your handling code here:
        String getURL = urlAcesso.getText();
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(getURL));
        } catch (Exception erro) {
            System.out.println(erro);
        }
    }//GEN-LAST:event_ShowHide1ActionPerformed

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
            java.util.logging.Logger.getLogger(visuAcessos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(visuAcessos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(visuAcessos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(visuAcessos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new visuAcessos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton ShowHide;
    private javax.swing.JToggleButton ShowHide1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPasswordField passwordAcesso;
    private javax.swing.JTable tableService;
    private javax.swing.JTextField txServico;
    private javax.swing.JTextField txServico2;
    private javax.swing.JTextField urlAcesso;
    // End of variables declaration//GEN-END:variables
}
