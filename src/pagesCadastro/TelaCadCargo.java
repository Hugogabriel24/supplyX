package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import static pagesCadastro.TelaCadFuncionario.cbCargo;

public final class TelaCadCargo extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadCargo() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        ListarDados();
        formatarpreco();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        txCargo.setDocument(new UpperCase());
        txFuncao.setDocument(new UpperCase());

    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        txRemunera.setFormatterFactory(dfFactory);

    }

    public void adicionar() {

        String sql = "insert into cargo(cargo,funcaoatrib,remunera,comissao) values(?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txCargo.getText());
            pst.setString(2, txFuncao.getText());
            pst.setString(3, txRemunera.getText());
            pst.setString(4, txComissao.getText());

            if ((txCargo.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    txID.setText(null);
                    txCargo.setText(null);
                    txComissao.setText(null);
                    txRemunera.setText(null);
                    txFuncao.setText(null);
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cargo adicionado com sucesso!!!");
                    noti.showNotification();

                }
            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {
        String sql = "update cargo set cargo=?, funcaoatrib=?,remunera=?,comissao=? where idcargo=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txCargo.getText());
            pst.setString(2, txFuncao.getText());
            pst.setString(3, txRemunera.getText());
            pst.setString(4, txComissao.getText());
            pst.setString(5, txID.getText());

            if ((txCargo.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    txID.setText(null);
                    txCargo.setText(null);
                    txComissao.setText(null);
                    txRemunera.setText(null);
                    txFuncao.setText(null);
                    ListarDados();
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cargo editado com sucesso!!!");
                    noti.showNotification();

                }
            }
        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o cargo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from cargo where idcargo=?";
            try {

                if ((txCargo.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum cargo selecionado!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txID.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    txID.setText(null);
                    txCargo.setText(null);
                    txComissao.setText(null);
                    txRemunera.setText(null);
                    txFuncao.setText(null);
                    ListarDados();
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cargo removido com sucesso!!!");
                    noti.showNotification();

                }

            } catch (SQLException e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void ListarDados() {
        String sql = "select idcargo as ID, cargo as Cargo, remunera as Remuneracao from cargo order by cargo";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tableCargo.setModel(DbUtils.resultSetToTableModel(rs));
            tableCargo.getColumnModel().getColumn(0).setMaxWidth(40);

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao listar os cargos.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    public void setarCamposTabela() {
        int setar = tableCargo.getSelectedRow();
        String sql = "select cargo,funcaoatrib,remunera,comissao from cargo where idcargo like ?";
        String id = (tableCargo.getModel().getValueAt(setar, 0).toString());

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                txID.setText(id);
                txCargo.setText(rs.getString(1));
                txFuncao.setText(rs.getString(2));
                txRemunera.setText(rs.getString(3));
                txComissao.setText(rs.getString(4));
                btAdc.setEnabled(false);
                btRemove.setEnabled(true);
                btEdit.setEnabled(true);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao selecionar o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
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

        txID = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txCargo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btAdc = new javax.swing.JButton();
        btRemove = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txRemunera = new javax.swing.JFormattedTextField();
        txComissao = new javax.swing.JTextField();
        txFuncao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCargo = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();

        txID.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar cargo");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("REMUNERAÇÃO");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("CARGO");

        txCargo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("COMISSÃO");

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

        btRemove.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemove.setText("Excluir cargo");
        btRemove.setToolTipText("");
        btRemove.setEnabled(false);
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText("Editar cargo");
        btEdit.setToolTipText("");
        btEdit.setEnabled(false);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton4.setText("Limpar dados");
        jButton4.setToolTipText("Limpar campos");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txRemunera.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txComissao.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txFuncao.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("FUNÇÕES ATRIBUIDAS");

        tableCargo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tableCargo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Serviço"
            }
        ));
        tableCargo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCargoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCargo);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel5.setText("%");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)))
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                            .addComponent(btEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btAdc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txComissao)
                            .addComponent(txRemunera))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addComponent(txCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(15, 15, 15)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(15, 15, 15)
                        .addComponent(txCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel4)
                        .addGap(19, 19, 19)
                        .addComponent(txFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(txRemunera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txComissao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(35, 35, 35)
                        .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableCargoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCargoMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tableCargoMouseClicked

    private void btAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btAdcActionPerformed

    private void btRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btRemoveActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        txID.setText(null);
        txCargo.setText(null);
        txComissao.setText(null);
        txRemunera.setText(null);
        txFuncao.setText(null);
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
        btAdc.setEnabled(true);
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        //btCargo.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(TelaCadCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadCargo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadCargo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdc;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btRemove;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCargo;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private javax.swing.JTextField txCargo;
    private javax.swing.JTextField txComissao;
    private javax.swing.JTextField txFuncao;
    private javax.swing.JTextField txID;
    private javax.swing.JFormattedTextField txRemunera;
    // End of variables declaration//GEN-END:variables
}
