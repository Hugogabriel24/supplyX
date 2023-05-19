/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vendas;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import static pages.TelaPrincipal.empresa;
import static pages.TelaPrincipal.username;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

/**
 *
 * @author Hugo Gabriel
 */
public class TelaCancelVenda extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCancelVenda() {
        initComponents();
        conexao = ModuloConexao.conector();
        TablesvendasAtivas.getTableHeader().setResizingAllowed(false);
        TablesvendasAtivas.getTableHeader().setReorderingAllowed(false);
        motivoC.setDocument(new UpperCase());
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        txProcurar.addOption(new SearchOption("Nome, CPF/CNPJ, RG, Data de nascimento", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Telefone", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.addOption(new SearchOption("E-mail", new ImageIcon(getClass().getResource("/src/email.png"))));
        txProcurar.addOption(new SearchOption("CEP ou Endereço", new ImageIcon(getClass().getResource("/src/address.png"))));
        txProcurar.setSelectedIndex(0);
        colocarDadosVendas();
        setIcon();
        setColorFromEmpresa();
        setDate();
        setLog();
    }

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel2.setBackground(new Color(15, 102, 122));
        } else {
            jPanel2.setBackground(new Color(147, 167, 39));
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    private void colocarDadosVendas() {
        String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT(datacompra,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,total as Total from vendas where status = 'concluida' order by nome";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            TablesvendasAtivas.setModel(DbUtils.resultSetToTableModel(rs));
            TablesvendasAtivas.getColumnModel().getColumn(0).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setDate() {
        Date now = new Date();
        SimpleDateFormat datayes = new SimpleDateFormat("dd/MM/yyyy");
        String DataFormated = datayes.format(now);
        dataC.setText(DataFormated);
    }

    public void setLog() {
        Date now = new Date();
        SimpleDateFormat datayes = new SimpleDateFormat("dd/MM/yyyy");
        String DataFormated = datayes.format(now);
        logC.setText("Venda cancelada na data: " + DataFormated + " pelo usuario: " + username.getText());
    }

    private void updateVenda() {
        String sql = "update vendas set status=? where idvendas=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "cancelada");
            pst.setString(2, idvendaC.getText());

            if ((idvendaC.getText().isEmpty())) {

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                }
            }
        } catch (Exception e) {

        }
    }

    public void cancel() {

        String sql = "insert into cancelamentosvenda(idvenda,idclientevenda,clientevenda,cpfclientevenda,vendedorvenda,motivo,data,log) values(?,?,?,?,?,?,?,?)";

        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, idvendaC.getText());
            pst.setString(2, idclienteC.getText());
            pst.setString(3, clienteC.getText());
            pst.setString(4, cpfC.getText());
            pst.setString(5, vendedorC.getText());
            pst.setString(6, motivoC.getText());
            pst.setString(7, dataC.getText());
            pst.setString(8, logC.getText());

            if ((idvendaC.getText().isEmpty()) || (motivoC.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Venda cancelada com sucesso!", "MENSAGEM", JOptionPane.INFORMATION_MESSAGE, icon);
                    updateVenda();
                    colocarDadosVendas();
                    idvendaC.setText(null);

                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cancelar venda!", "ERRO", JOptionPane.ERROR_MESSAGE);
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

        clienteC = new javax.swing.JTextField();
        cpfC = new javax.swing.JTextField();
        vendedorC = new javax.swing.JTextField();
        idclienteC = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        motivoC = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        dataC = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        logC = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        idvendaC = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablesvendasAtivas = new javax.swing.JTable();

        clienteC.setText("jTextField1");

        cpfC.setText("jTextField1");

        vendedorC.setText("jTextField1");

        idclienteC.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jLabel15.setText("   Cancelamento de vendas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel16.setText("MOTIVO DE CANCELAMENTO");

        motivoC.setColumns(20);
        motivoC.setRows(5);
        jScrollPane1.setViewportView(motivoC);

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel18.setText("DATA DE CANCELAMENTO");

        dataC.setEnabled(false);

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel19.setText("LOG DE CANCELAMENTO");

        logC.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        jButton1.setText("Cancelar venda");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("Nº DA VENDA SELECIONADA :");

        idvendaC.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        idvendaC.setEnabled(false);

        jCheckBox1.setText("Colocar os produtos vendidos em estoque novamente");

        txProcurar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        TablesvendasAtivas.setModel(new javax.swing.table.DefaultTableModel(
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
        TablesvendasAtivas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablesvendasAtivasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablesvendasAtivas);

        tableScrollButton1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txProcurar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dataC, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(logC)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(idvendaC, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(idvendaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jCheckBox1))
                .addContainerGap(14, Short.MAX_VALUE))
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

    private void TablesvendasAtivasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablesvendasAtivasMouseClicked
        // TODO add your handling code here:
        int setar = TablesvendasAtivas.getSelectedRow();

        if (setar >= 0) {

            String sql = "select idvendas,idcliente,nomecli,cpfcli,telcli,datacompra,garantia,vendedor,canalvenda,formareceb,endentrega,valorfrete,dataentrega,pagador,formapagamento,nparcelas,valordinheiro,valorcartao,descontoaplicado,valorcdesconto,valorrecebido,total from vendas where idvendas like ?";
            String id = (TablesvendasAtivas.getModel().getValueAt(setar, 0).toString());

            try {
                pst = conexao.prepareStatement(sql);

                pst.setString(1, id);
                rs = pst.executeQuery();

                while (rs.next()) {
                    idvendaC.setText(rs.getString(1));
                    idclienteC.setText(rs.getString(2));
                    clienteC.setText(rs.getString(3));
                    cpfC.setText(rs.getString(4));
                    vendedorC.setText(rs.getString(8));

                }
            } catch (SQLException e) {

            }
        } else {
            
        }
    }//GEN-LAST:event_TablesvendasAtivasMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cancel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
//        if (empresa.getText().equals("tanamao")) {
//            if (txProcurar.isSelected()) {
//                int option = txProcurar.getSelectedIndex();
//                String text = "%" + txProcurar.getText() + "%";
//                if (option == 0) {
//                    pesquisar_clienteTNM("where empresa = 'tanamao' and (nome like ? OR cpf like ? OR rg like ? OR datanasc like ?)", text, text, text, text);
//                } else if (option == 1) {
//                    pesquisar_clienteTNM("where tel like ? and empresa like 'tanamao'", text);
//                } else if (option == 2) {
//                    pesquisar_clienteTNM("where email like ? and empresa like 'tanamao'", text);
//                } else if (option == 3) {
//                    pesquisar_clienteTNM("where empresa = 'tanamao' and (cep like ? or endereco like ?)", text, text);
//                }
//            }
//        }
//        if (empresa.getText().equals("docatec")) {
//            if (txProcurar.isSelected()) {
//                int option = txProcurar.getSelectedIndex();
//                String text = "%" + txProcurar.getText() + "%";
//                if (option == 0) {
//                    pesquisar_clienteDC("where empresa = 'docatec' and (nome like ? OR cpf like ? OR rg like ? OR datanasc like ?)", text, text, text, text);
//                } else if (option == 1) {
//                    pesquisar_clienteDC("where tel like ? and empresa like 'docatec'", text);
//                } else if (option == 2) {
//                    pesquisar_clienteDC("where email like ? and empresa like 'docatec'", text);
//                } else if (option == 3) {
//                    pesquisar_clienteDC("where empresa = 'docatec' and (cep like ? or endereco like ?)", text, text);
//                }
//            }
//        }
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
            java.util.logging.Logger.getLogger(TelaCancelVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCancelVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCancelVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCancelVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCancelVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablesvendasAtivas;
    private javax.swing.JTextField clienteC;
    private javax.swing.JTextField cpfC;
    private javax.swing.JTextField dataC;
    private javax.swing.JTextField idclienteC;
    public static javax.swing.JTextField idvendaC;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField logC;
    private javax.swing.JTextArea motivoC;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    private javax.swing.JTextField vendedorC;
    // End of variables declaration//GEN-END:variables
}
