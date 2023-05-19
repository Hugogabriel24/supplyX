/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesSearchs;

import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import static pages.TelaPrincipal.empresa;
import static pagesCadastro.TelaCadEntregador.CBOSexo;
import static pagesCadastro.TelaCadEntregador.CBOuf;
import static pagesCadastro.TelaCadEntregador.CheckPF;
import static pagesCadastro.TelaCadEntregador.CheckPJ;
import static pagesCadastro.TelaCadEntregador.Inativo;
import static pagesCadastro.TelaCadEntregador.TextBairro;
import static pagesCadastro.TelaCadEntregador.TextCEP;
import static pagesCadastro.TelaCadEntregador.TextCOMP;
import static pagesCadastro.TelaCadEntregador.TextCPF;
import static pagesCadastro.TelaCadEntregador.TextCity;
import static pagesCadastro.TelaCadEntregador.TextEND;
import static pagesCadastro.TelaCadEntregador.TextEmail;
import static pagesCadastro.TelaCadEntregador.TextFONE;
import static pagesCadastro.TelaCadEntregador.TextFantasia;
import static pagesCadastro.TelaCadEntregador.TextID;
import static pagesCadastro.TelaCadEntregador.TextNASC;
import static pagesCadastro.TelaCadEntregador.TextNome;
import static pagesCadastro.TelaCadEntregador.TextNumber;
import static pagesCadastro.TelaCadEntregador.TextOrgao;
import static pagesCadastro.TelaCadEntregador.TextRG;
import static pagesCadastro.TelaCadEntregador.TextRazao;
import static pagesCadastro.TelaCadEntregador.anoVeiculo;
import static pagesCadastro.TelaCadEntregador.btCad;
import static pagesCadastro.TelaCadEntregador.btEdit;
import static pagesCadastro.TelaCadEntregador.btExcluir;
import static pagesCadastro.TelaCadEntregador.btSearch;
import static pagesCadastro.TelaCadEntregador.btWPP;
import static pagesCadastro.TelaCadEntregador.cboEtiquetas;
import static pagesCadastro.TelaCadEntregador.corVeiculo;
import static pagesCadastro.TelaCadEntregador.finalPlaca;
import static pagesCadastro.TelaCadEntregador.marcaviculo;
import static pagesCadastro.TelaCadEntregador.modeloVeiculo;
import static pagesCadastro.TelaCadEntregador.placaVeiculo;
import static pagesCadastro.TelaCadEntregador.searchCNPJ;
import static pagesCadastro.TelaCadEntregador.textBanco;
import static pagesCadastro.TelaCadEntregador.textPix;
import static pagesCadastro.TelaCadEntregador.tipoChave;
import static pagesCadastro.TelaCadEntregador.tipoveiculo;
import static pagesCadastro.TelaCadEntregador.txtDataC;
import static pagesCadastro.TelaCadEntregador.txtInfo;
import static pagesCadastro.TelaCadEntregador.txtObs;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

/**
 *
 * @author hugogabriel
 */
public class SearchEntregador extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchEntregador() {
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
        tbClientes.getTableHeader().setResizingAllowed(false);
        tbClientes.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Nome, CPF/CNPJ, RG, Data de nascimento, placa do veiculo", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Telefone", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.addOption(new SearchOption("E-mail", new ImageIcon(getClass().getResource("/src/email.png"))));
        txProcurar.addOption(new SearchOption("CEP ou Endereço", new ImageIcon(getClass().getResource("/src/address.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_entreg("");
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
        String sql = "select identregadores as ID,nome as Nome,pessoa as Pessoa,cpf as CPFCNPJ,tel as Fone,cep as CEP,placaveiculo as Placa from entregadores order by nome";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tbClientes.setModel(DbUtils.resultSetToTableModel(rs));
            tbClientes.getColumnModel().getColumn(0).setMaxWidth(70);
            tbClientes.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_entreg(String where, Object... searchdc) {

        String sql = "select identregadores as ID,nome as Nome,pessoa as Pessoa,cpf as CPFCNPJ,tel as Fone,cep as CEP,placaveiculo as Placa from entregadores " + where + "";
        try {

            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchdc.length; i++) {
                pst.setObject(i + 1, searchdc[i]);
            }
            rs = pst.executeQuery();
            tbClientes.setModel(DbUtils.resultSetToTableModel(rs));
            tbClientes.getColumnModel().getColumn(0).setMaxWidth(70);
            tbClientes.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setarCamposTabela() {

        int setar = tbClientes.getSelectedRow();
        btCad.setEnabled(true);
        String sql = "select * from entregadores where identregadores like ?";

        String id = (tbClientes.getModel().getValueAt(setar, 0).toString());
        String Pessoa = (tbClientes.getModel().getValueAt(setar, 2).toString());

        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                if (Pessoa.equals("PF")) {
                    CheckPF.setSelected(true);
                    CheckPJ.setSelected(false);
                    CheckPJ.setEnabled(false);
                    TextRazao.setEnabled(false);
                    TextFantasia.setEnabled(false);
                    TextRazao.setText(null);
                    TextFantasia.setText(null);
                    TextID.setText(rs.getString(1));
                    TextNome.setText(rs.getString(3));
                    TextCPF.setText(rs.getString(4));
                    TextRazao.setText(rs.getString(5));
                    TextFantasia.setText(rs.getString(6));
                    TextRG.setText(rs.getString(7));
                    TextOrgao.setText(rs.getString(8));
                    CBOSexo.setSelectedItem(rs.getString(9));
                    TextNASC.setText(rs.getString(10));
                    TextFONE.setText(rs.getString(11));
                    TextEmail.setText(rs.getString(12));
                    TextCEP.setText(rs.getString(13));
                    TextEND.setText(rs.getString(14));
                    TextCity.setText(rs.getString(15));
                    TextBairro.setText(rs.getString(16));
                    TextNumber.setText(rs.getString(17));
                    TextCOMP.setText(rs.getString(18));
                    CBOuf.setSelectedItem(rs.getString(19));
                    Inativo.setSelectedItem(rs.getString(20));
                    txtInfo.setText(rs.getString(21));
                    txtObs.setText(rs.getString(22));
                    txtDataC.setText(rs.getString(23));
                    tipoChave.setSelectedItem(rs.getString(24));
                    textPix.setText(rs.getString(25));
                    textBanco.setSelectedItem(rs.getString(26));
                    cboEtiquetas.setSelectedItem(rs.getString(27));
                    tipoveiculo.setSelectedItem(rs.getString(28));
                    marcaviculo.setText(rs.getString(29));
                    modeloVeiculo.setText(rs.getString(30));
                    anoVeiculo.setSelectedItem(rs.getString(31));
                    corVeiculo.setSelectedItem(rs.getString(32));
                    placaVeiculo.setText(rs.getString(33));
                    finalPlaca.setText(rs.getString(34));

                    if (tipoChave.getSelectedItem().equals("--")) {
                        textPix.setEnabled(false);
                        textBanco.setEnabled(false);
                    } else {
                        textPix.setEnabled(true);
                        textBanco.setEnabled(true);
                    }
                    CheckPF.setEnabled(true);
                    TextRG.setEnabled(true);
                    TextOrgao.setEnabled(true);
                    btWPP.setVisible(true);
                    searchCNPJ.setEnabled(false);
                    btEdit.setEnabled(true);
                    btExcluir.setEnabled(true);
                    btCad.setEnabled(false);
                    this.dispose();

                } else {

                    CheckPJ.setSelected(true);
                    CheckPF.setSelected(false);
                    CheckPF.setEnabled(false);
                    TextRG.setText(null);
                    TextRG.setEnabled(false);
                    TextOrgao.setEnabled(false);
                    TextID.setText(rs.getString(1));
                    TextNome.setText(rs.getString(3));
                    TextCPF.setText(rs.getString(4));
                    TextRazao.setText(rs.getString(5));
                    TextFantasia.setText(rs.getString(6));
                    TextRG.setText(rs.getString(7));
                    TextOrgao.setText(rs.getString(8));
                    CBOSexo.setSelectedItem(rs.getString(9));
                    TextNASC.setText(rs.getString(10));
                    TextFONE.setText(rs.getString(11));
                    TextEmail.setText(rs.getString(12));
                    TextCEP.setText(rs.getString(13));
                    TextEND.setText(rs.getString(14));
                    TextCity.setText(rs.getString(15));
                    TextBairro.setText(rs.getString(16));
                    TextNumber.setText(rs.getString(17));
                    CBOuf.setSelectedItem(rs.getString(19));
                    Inativo.setSelectedItem(rs.getString(20));
                    txtInfo.setText(rs.getString(21));
                    txtObs.setText(rs.getString(22));
                    txtDataC.setText(rs.getString(23));
                    tipoChave.setSelectedItem(rs.getString(24));
                    textPix.setText(rs.getString(25));
                    textBanco.setSelectedItem(rs.getString(26));
                    cboEtiquetas.setSelectedItem(rs.getString(27));
                    tipoveiculo.setSelectedItem(rs.getString(28));
                    marcaviculo.setText(rs.getString(29));
                    modeloVeiculo.setText(rs.getString(30));
                    anoVeiculo.setSelectedItem(rs.getString(31));
                    corVeiculo.setSelectedItem(rs.getString(32));
                    placaVeiculo.setText(rs.getString(33));
                    finalPlaca.setText(rs.getString(34));
                    CheckPJ.setEnabled(true);
                    TextFantasia.setEnabled(true);
                    TextRazao.setEnabled(true);
                    btWPP.setVisible(true);
                    searchCNPJ.setEnabled(true);
                    btEdit.setEnabled(true);
                    btExcluir.setEnabled(true);
                    btCad.setEnabled(false);
                    this.dispose();

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PainelUp = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbClientes = new javax.swing.JTable();
        btCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar entregadores");
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

        tbClientes.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tbClientes.getTableHeader().setReorderingAllowed(false);
        tbClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbClientes);
        if (tbClientes.getColumnModel().getColumnCount() > 0) {
            tbClientes.getColumnModel().getColumn(0).setResizable(false);
            tbClientes.getColumnModel().getColumn(1).setResizable(false);
            tbClientes.getColumnModel().getColumn(2).setResizable(false);
            tbClientes.getColumnModel().getColumn(3).setResizable(false);
            tbClientes.getColumnModel().getColumn(4).setResizable(false);
            tbClientes.getColumnModel().getColumn(5).setResizable(false);
        }

        btCancel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        btCancel.setText("Cancelar");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btCancel)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(PainelUp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

    private void tbClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClientesMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tbClientesMouseClicked

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
        btSearch.setEnabled(true);
    }//GEN-LAST:event_btCancelActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        btSearch.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (txProcurar.isSelected()) {
            int option = txProcurar.getSelectedIndex();
            String text = "%" + txProcurar.getText() + "%";
            if (option == 0) {
                pesquisar_entreg("where (nome like ? OR cpf like ? OR rg like ? OR datanasc like ?)", text, text, text, text);
            } else if (option == 1) {
                pesquisar_entreg("where tel like ?", text);
            } else if (option == 2) {
                pesquisar_entreg("where email like ?", text);
            } else if (option == 3) {
                pesquisar_entreg("where (cep like ? or endereco like ?)", text, text);
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
            java.util.logging.Logger.getLogger(SearchEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchEntregador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelUp;
    private javax.swing.JButton btCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbClientes;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
