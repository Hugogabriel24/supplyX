package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import static pages.TelaPrincipal.empresa;

/**
 *
 * @author hugogabriel
 */
public class TelaCadServicos extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadServicos() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        ListarDados();
        formatarpreco();
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
    }

    public void uper() {
        txDetalhe.setDocument(new UpperCase());
        txServico.setDocument(new UpperCase());

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        txValor.setFormatterFactory(dfFactory);

    }

    public void adicionar() {

        String sql = "insert into servicos(servico,detalhes,valor) values(?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txServico.getText());
            pst.setString(2, txDetalhe.getText());
            pst.setString(3, txValor.getText());

            if ((txDetalhe.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    txServico.setText(null);
                    txDetalhe.setText(null);
                    txValor.setFormatterFactory(null);
                    txValor.setText(null);
                    txID.setText(null);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Serviço adicionado com sucesso!!!");
                    noti.showNotification();
                    formatarpreco();
                    ListarDados();

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        String sql = "update servicos set servico=?, detalhes=?,valor=? where idservicos=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txServico.getText());
            pst.setString(2, txDetalhe.getText());
            pst.setString(3, txValor.getText());
            pst.setString(4, txID.getText());

            if ((txDetalhe.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    txServico.setText(null);
                    txDetalhe.setText(null);
                    txValor.setFormatterFactory(null);
                    txValor.setText(null);
                    txID.setText(null);
                    ListarDados();
                    formatarpreco();
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Serviço editado com sucesso!!!");
                    noti.showNotification();

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar!!");
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o serviço?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from servicos where idservicos=?";
            try {

                if ((txDetalhe.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Serviço adicionado com sucesso!!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txID.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    txServico.setText(null);
                    txDetalhe.setText(null);
                    txValor.setFormatterFactory(null);
                    txValor.setText(null);
                    txID.setText(null);
                    ListarDados();
                    formatarpreco();
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Serviço removido com sucesso!!!");
                    noti.showNotification();

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    public void ListarDados() {
        String sql = "select idservicos as ID, servico as Servico, valor as Valor from servicos order by servico";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tableService.setModel(DbUtils.resultSetToTableModel(rs));
            tableService.getColumnModel().getColumn(0).setMaxWidth(40);
            tableService.getColumnModel().getColumn(2).setMaxWidth(60);
//            TableClientes.getColumnModel().getColumn(12).setMaxWidth(40);
//            TableClientes.getColumnModel().getColumn(8).setMaxWidth(40);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCamposTabela() {
        int setar = tableService.getSelectedRow();
        String sql = "select servico,detalhes,valor from servicos where idservicos like ?";
        String id = (tableService.getModel().getValueAt(setar, 0).toString());

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                txID.setText(id);
                txServico.setText(rs.getString(1));
                txDetalhe.setText(rs.getString(2));
                txValor.setText(rs.getString(3));
                btAdc.setEnabled(false);
                btRemove.setEnabled(true);
                btEdit.setEnabled(true);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txID = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txServico = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txValor = new javax.swing.JFormattedTextField();
        btAdc = new javax.swing.JButton();
        btRemove = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableService = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txDetalhe = new javax.swing.JTextArea();

        txID.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Serviços");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("DETALHES");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("SERVIÇO");

        txServico.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("VALOR");

        txValor.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

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
        btRemove.setText("Excluir serviço");
        btRemove.setToolTipText("");
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText("Editar serviço");
        btEdit.setToolTipText("");
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lista de serviços", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        tableService.setModel(new javax.swing.table.DefaultTableModel(
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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        txDetalhe.setColumns(20);
        txDetalhe.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txDetalhe.setRows(5);
        jScrollPane2.setViewportView(txDetalhe);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                                .addComponent(btRemove)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txServico))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txValor, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(15, 15, 15)
                        .addComponent(txServico, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(txValor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableServiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableServiceMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();

    }//GEN-LAST:event_tableServiceMouseClicked

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
        txServico.setText(null);
        txDetalhe.setText(null);
        txValor.setFormatterFactory(null);
        txValor.setText(null);
        txID.setText(null);
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
        btAdc.setEnabled(true);
        Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadServicos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadServicos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadServicos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadServicos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadServicos().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableService;
    private javax.swing.JTextArea txDetalhe;
    private javax.swing.JTextField txID;
    private javax.swing.JTextField txServico;
    private javax.swing.JFormattedTextField txValor;
    // End of variables declaration//GEN-END:variables
}
