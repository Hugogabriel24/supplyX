package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import notification.Notification;

public final class TelaCadLocalestoque extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private DefaultListModel lista = new DefaultListModel();

    public TelaCadLocalestoque() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        ListarDados();

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        txLocal.setDocument(new UpperCase());
        txMore.setDocument(new UpperCase());

    }

    public void adicionar() {

        String sql = "insert into localestoque(local,more) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txLocal.getText());
            pst.setString(2, txMore.getText());

            if ((txLocal.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    lista.clear();
                    ListarDados();
                    txLocal.setText(null);
                    txMore.setText(null);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    btAdc.setEnabled(true);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Local adicionado com sucesso!!!");
                    noti.showNotification();
                }
            }

        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar o local.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {
        String sql = "update localestoque set local=?, more=? where local=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txLocal.getText());
            pst.setString(2, txMore.getText());
            pst.setString(3, txLocal.getText());

            if ((txLocal.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum local selecionado!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    lista.clear();
                    ListarDados();
                    txLocal.setText(null);
                    txMore.setText(null);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    btAdc.setEnabled(true);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Local editado com sucesso!!!");
                    noti.showNotification();
                }
            }
        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar o local.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o local?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from localestoque where local=?";
            try {

                if ((txLocal.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum local selecionado!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txLocal.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    lista.clear();
                    ListarDados();
                    txLocal.setText(null);
                    txMore.setText(null);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    btAdc.setEnabled(true);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Local removido com sucesso!!!");
                    noti.showNotification();

                }

            } catch (SQLException e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover o local.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void ListarDados() {
        String sql = "select local from localestoque order by local";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                lista.addElement(nome);
                listLocal.setModel(lista);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao listar os locais.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txLocal1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLocal = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        txLocal = new javax.swing.JTextField();
        btRemove = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btAdc = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txMore = new javax.swing.JTextArea();

        txLocal1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Local de Estoque");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        listLocal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Local de estoque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        listLocal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        listLocal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listLocal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listLocalMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listLocal);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("LOCAL DE ESTOQUE");

        txLocal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        btRemove.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemove.setText("Excluir");
        btRemove.setToolTipText("");
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText("Editar");
        btEdit.setToolTipText("");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        btAdc.setBackground(new java.awt.Color(2, 119, 222));
        btAdc.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdc.setForeground(new java.awt.Color(255, 255, 255));
        btAdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdc.setText("Adicionar");
        btAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton1.setText("  Limpar dados");
        jButton1.setToolTipText("Limpar campos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("DETALHES DO LOCAL");

        txMore.setColumns(20);
        txMore.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txMore.setRows(5);
        jScrollPane2.setViewportView(txMore);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(txLocal)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAdc)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(txLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void listLocalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listLocalMouseClicked
        // TODO add your handling code here:
        String SetMarca = listLocal.getSelectedValue();

        String sql = "select local,more from localestoque where local = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, SetMarca);
            rs = pst.executeQuery();

            while (rs.next()) {
                String Local = rs.getString(1);
                String nome = rs.getString(2);
                txLocal.setText(Local);
                txMore.setText(nome);
                btAdc.setEnabled(false);
                btRemove.setEnabled(true);
                btEdit.setEnabled(true);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao selecionar um local.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }

    }//GEN-LAST:event_listLocalMouseClicked

    private void btRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btRemoveActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditActionPerformed

    private void btAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btAdcActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txLocal.setText(null);
        txMore.setText(null);
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
        btAdc.setEnabled(true);
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadLocalestoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadLocalestoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadLocalestoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadLocalestoque.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadLocalestoque().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdc;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btRemove;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listLocal;
    private javax.swing.JTextField txLocal;
    private javax.swing.JTextField txLocal1;
    private javax.swing.JTextArea txMore;
    // End of variables declaration//GEN-END:variables
}
