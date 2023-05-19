package pagesSearchs;

import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import static pages.TelaPrincipal.empresa;
import static pagesCadastro.TelaCadPatrimonio.Painel1ImagePT;
import static pagesCadastro.TelaCadPatrimonio.cbLocal;
import static pagesCadastro.TelaCadPatrimonio.cbPrateleira;
import static pagesCadastro.TelaCadPatrimonio.dataAquis;
import static pagesCadastro.TelaCadPatrimonio.txDescri;
import static pagesCadastro.TelaCadPatrimonio.txEan;
import static pagesCadastro.TelaCadPatrimonio.txEstadoPatrimonio;
import static pagesCadastro.TelaCadPatrimonio.txID;
import static pagesCadastro.TelaCadPatrimonio.txItem;
import static pagesCadastro.TelaCadPatrimonio.txserialPatrimonio;
import static pagesCadastro.TelaCadPatrimonio.txvalor;
import static pagesCadastro.TelaCadPatrimonio.viewImage1PT;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public final class SearchPatrimonio extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchPatrimonio() {
        initComponents();
        conexao = ModuloConexao.conector();

        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        setIcon();
        tbPatrimonio.getTableHeader().setResizingAllowed(false);
        tbPatrimonio.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Descrição, item", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Codigo, serial, ID", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_Patrim("");
        colocarDados();
        setColorFromEmpresa();

    }

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            PainelUp.setBackground(new Color(15, 102, 122));
        } else {
            PainelUp.setBackground(new Color(147, 167, 39));
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void colocarDados() {

        String sql = "select idpatrimonio as ID,item as Item,descricao as Descrição,codigo as Codigo from patrimonio order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tbPatrimonio.setModel(DbUtils.resultSetToTableModel(rs));
            tbPatrimonio.getColumnModel().getColumn(0).setMaxWidth(70);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_Patrim(String wheredc, Object... searchdc) {

        String sql = "select idpatrimonio as ID,item as Item,descricao as Descrição,codigo as Codigo from patrimonio " + wheredc + "";
        try {

            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchdc.length; i++) {
                pst.setObject(i + 1, searchdc[i]);
            }
            rs = pst.executeQuery();
            tbPatrimonio.setModel(DbUtils.resultSetToTableModel(rs));
            tbPatrimonio.getColumnModel().getColumn(0).setMaxWidth(70);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setarCamposTabela() {

        int setar = tbPatrimonio.getSelectedRow();
        String sql = "select * from patrimonio where idpatrimonio like ?";
        String id = (tbPatrimonio.getModel().getValueAt(setar, 0).toString());

        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                txID.setText(rs.getString(1));
                txItem.setText(rs.getString(2));
                txDescri.setText(rs.getString(3));
                txEan.setText(rs.getString(4));
                dataAquis.setDate(rs.getDate(5));
                txvalor.setText(rs.getString(6));
                txserialPatrimonio.setText(rs.getString(7));
                txEstadoPatrimonio.setSelectedItem(rs.getString(8));
                cbLocal.setSelectedItem(rs.getString(9));
                cbPrateleira.setSelectedItem(rs.getString(10));
                viewImage1PT.setIcon(null);

                if (rs.getBytes(11) == null) {

                } else {
                    ImageIcon icone1 = new ImageIcon(rs.getBytes(11));
                    icone1.setImage(icone1.getImage().getScaledInstance(Painel1ImagePT.getWidth() - 1, Painel1ImagePT.getWidth() - 1, 100));
                    viewImage1PT.setIcon(icone1);
                }

                this.dispose();

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PainelUp = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        btCancel = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPatrimonio = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar clientes");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        PainelUp.setBackground(new java.awt.Color(147, 167, 39));

        txProcurar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout PainelUpLayout = new javax.swing.GroupLayout(PainelUp);
        PainelUp.setLayout(PainelUpLayout);
        PainelUpLayout.setHorizontalGroup(
            PainelUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelUpLayout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PainelUpLayout.setVerticalGroup(
            PainelUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelUpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btCancel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        btCancel.setText("Cancelar");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        tbPatrimonio.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbPatrimonio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "TIPO PESSOA", "CPF/CNPJ", "TELEFONE", "CEP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPatrimonio.getTableHeader().setReorderingAllowed(false);
        tbPatrimonio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPatrimonioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPatrimonio);
        if (tbPatrimonio.getColumnModel().getColumnCount() > 0) {
            tbPatrimonio.getColumnModel().getColumn(0).setResizable(false);
            tbPatrimonio.getColumnModel().getColumn(1).setResizable(false);
            tbPatrimonio.getColumnModel().getColumn(2).setResizable(false);
            tbPatrimonio.getColumnModel().getColumn(3).setResizable(false);
            tbPatrimonio.getColumnModel().getColumn(4).setResizable(false);
            tbPatrimonio.getColumnModel().getColumn(5).setResizable(false);
        }

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PainelUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btCancel))
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PainelUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void tbPatrimonioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPatrimonioMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tbPatrimonioMouseClicked

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
        //  btSearch.setEnabled(true);
    }//GEN-LAST:event_btCancelActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        //   btSearch.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (txProcurar.isSelected()) {
            int option = txProcurar.getSelectedIndex();
            String text = "%" + txProcurar.getText() + "%";
            if (option == 0) {
                pesquisar_Patrim("where (item like ? OR descricao like ?)", text, text);
            } else if (option == 1) {
                pesquisar_Patrim("where codigo like ? OR serial like ?", text, text);
            }
        }

    }//GEN-LAST:event_txProcurarKeyReleased

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
            java.util.logging.Logger.getLogger(SearchPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchPatrimonio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelUp;
    private javax.swing.JButton btCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private javax.swing.JTable tbPatrimonio;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
