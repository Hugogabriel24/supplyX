package pagesEstoque;

import ArchiveTablesVendas.TableCustom;
import ModelTableCores.CorNaLinhaEstoquePP;
import TableModels.ModelTableToPrint;
import TablesGetSetters.ProdutosToPrint;
import br.com.tanamao.dal.ModuloConexao;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import static pages.TelaPrincipal.empresa;
import pagesDetails.PPDetails;
import static pagesDetails.PPDetails.idPP0001;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public final class TelaEstoquePatrimonios extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ModelTableToPrint modelo;

    public TelaEstoquePatrimonios() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        modelo = new ModelTableToPrint();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        colocarDados();
        setColorFromEmpresa();
        tablePrint.setModel(modelo);
        tablePrint.getColumnModel().getColumn(0).setMaxWidth(60);

        txProcurar.addEventOptionSelected(new SearchOptinEvent() {

            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        tablePatrimonios.getTableHeader().setResizingAllowed(false);
        tablePatrimonios.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Descrição / Observações / Modelos compatíveis", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_EstoquePatrimonio("");

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setColorFromEmpresa() {

        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel9.setBackground(new Color(15, 102, 122));
        } else {
            jPanel9.setBackground(new Color(147, 167, 39));

        }
    }

    public void colocarDados() {
        String sql = "select idpatrimonio as ID,item as Item,descricao as Descrição,codigo as Codigo from patrimonio order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tablePatrimonios.setModel(DbUtils.resultSetToTableModel(rs));
            tablePatrimonios.getColumnModel().getColumn(0).setMaxWidth(50);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_EstoquePatrimonio(String where, Object... search) {

        String sql = "select idpatrimonio as ID,item as Item,descricao as Descrição,codigo as Codigo from patrimonio " + where + "";

        try {

            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            tablePatrimonios.setModel(DbUtils.resultSetToTableModel(rs));
            tablePatrimonios.getColumnModel().getColumn(0).setMaxWidth(50);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setarCamposTabela() {

        int setar = tablePatrimonios.getSelectedRow();
        String sql = "select * from partepecas where idpartepecas like ?";

        String id = (tablePatrimonios.getModel().getValueAt(setar, 0).toString());

        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void addToPrint() {
        ProdutosToPrint PPT = new ProdutosToPrint();
        int setar = tablePatrimonios.getSelectedRow();

        if (setar >= 0) {
            if (tablePrint.getRowCount() >= 3) {
                JOptionPane.showMessageDialog(null, "Número máximo de 3 produtos pra impressão atingido!");
            } else {
                String id = (tablePatrimonios.getModel().getValueAt(setar, 0).toString());
                String descri = (tablePatrimonios.getModel().getValueAt(setar, 2).toString());

                PPT.setID(id);
                PPT.setDescricao(descri);

                modelo = (ModelTableToPrint) tablePrint.getModel();
                tablePrint.setModel(modelo);
                modelo.addProdutosPPT(PPT);
                geraretq.setEnabled(true);
                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "" + descri + " adicionado a fila de impressão de etiqueta");
                noti.showNotification();
            }

        } else {

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idselected = new javax.swing.JTextField();
        descriselected = new javax.swing.JTextField();
        dialogPrint = new javax.swing.JDialog();
        itensPrint = new javax.swing.JScrollPane();
        tablePrint = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        geraretq = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePatrimonios = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        idselected.setText("jTextField1");

        descriselected.setText("jTextField1");

        dialogPrint.setUndecorated(true);
        dialogPrint.setSize(new java.awt.Dimension(373, 258));

        tablePrint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        itensPrint.setViewportView(tablePrint);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setText("Lista de impressão");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton5.setText("Fechar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton4.setText("Limpar lista de impressão");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton6.setText("Excluir item");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogPrintLayout = new javax.swing.GroupLayout(dialogPrint.getContentPane());
        dialogPrint.getContentPane().setLayout(dialogPrintLayout);
        dialogPrintLayout.setHorizontalGroup(
            dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(itensPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        dialogPrintLayout.setVerticalGroup(
            dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(itensPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(204, 204, 204));

        jPanel9.setBackground(new java.awt.Color(67, 118, 218));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 3, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("ESTOQUE DE PATRIMÔNIOS");

        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txProcurarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/detalhes.png"))); // NOI18N
        jButton2.setText("Detalhes do patrimônio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        geraretq.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        geraretq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/etiqueta-de-preco_1.png"))); // NOI18N
        geraretq.setText("Gerar etiqueta");
        geraretq.setEnabled(false);
        geraretq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geraretqActionPerformed(evt);
            }
        });

        tablePatrimonios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablePatrimonios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePatrimoniosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePatrimonios);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/addlista.png"))); // NOI18N
        jButton1.setText(" Adicionar item a lista de impressão");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(geraretq)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(geraretq)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (tablePatrimonios.getSelectedRow() >= 0) {
            PPDetails ppd = new PPDetails();
            ppd.setVisible(true);
            int setarNotes = tablePatrimonios.getSelectedRow();
            String getID = tablePatrimonios.getModel().getValueAt(setarNotes, 0).toString();
            idPP0001.setText(getID);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void geraretqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geraretqActionPerformed
        // TODO add your handling code here:
        printpp print = new printpp();
        print.setVisible(true);


    }//GEN-LAST:event_geraretqActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (txProcurar.isSelected()) {
            int option = txProcurar.getSelectedIndex();
            String text = "%" + txProcurar.getText() + "%";
            if (option == 0) {
                pesquisar_EstoquePatrimonio("where (codigob like ? or ref like ? or nserie like ?)", text, text, text);
            } else if (option == 1) {
                pesquisar_EstoquePatrimonio("where descricao like ? or obs like ?", text, text);
            } else if (option == 2) {
                pesquisar_EstoquePatrimonio("where grupo like ? or marca like ? or modelo like ?", text, text, text);
            } else if (option == 3) {
                pesquisar_EstoquePatrimonio("where (forne like ?)", text);
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void tablePatrimoniosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePatrimoniosMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tablePatrimoniosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        dialogPrint.setSize(373, 274);
        dialogPrint.setLocation(this.getLocationOnScreen().x + this.getWidth(), this.getLocationOnScreen().y);
        dialogPrint.setVisible(true);
        addToPrint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (tablePrint.getRowCount() == 0) {
            geraretq.setEnabled(false);
        }
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (tablePrint.getSelectedRow() >= 0) {
            modelo.remove(tablePrint.getSelectedRow());
            tablePrint.setModel(modelo);
            if (tablePrint.getRowCount() == 0) {
                geraretq.setEnabled(false);
            }
        } else {

        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        modelo.limpar();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Fila de impressão limpa com sucesso!");
        noti.showNotification();
        tablePrint.setModel(modelo);
        geraretq.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txProcurarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tablePatrimonios.getRowCount() == 1) {
                PPDetails ppd = new PPDetails();
                ppd.setVisible(true);
                String getID = tablePatrimonios.getModel().getValueAt(0, 0).toString();
                idPP0001.setText(getID);
            } else {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum produto encontrado na base de dados!! :/");
                noti.showNotification();
            }
        }
    }//GEN-LAST:event_txProcurarKeyPressed

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
            java.util.logging.Logger.getLogger(TelaEstoquePatrimonios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEstoquePatrimonios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEstoquePatrimonios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEstoquePatrimonios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEstoquePatrimonios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descriselected;
    public static javax.swing.JDialog dialogPrint;
    private javax.swing.JButton geraretq;
    private javax.swing.JTextField idselected;
    private javax.swing.JScrollPane itensPrint;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTable tablePatrimonios;
    public static javax.swing.JTable tablePrint;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
