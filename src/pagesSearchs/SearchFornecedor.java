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
import static pagesCadastro.TelaCadFornecedor.CBOSexo;
import static pagesCadastro.TelaCadFornecedor.CBOuf;
import static pagesCadastro.TelaCadFornecedor.CheckPF;
import static pagesCadastro.TelaCadFornecedor.CheckPJ;
import static pagesCadastro.TelaCadFornecedor.DescontoPadrao;
import static pagesCadastro.TelaCadFornecedor.Inativo;
import static pagesCadastro.TelaCadFornecedor.LimiteCreditoforne;
import static pagesCadastro.TelaCadFornecedor.TextBairro;
import static pagesCadastro.TelaCadFornecedor.TextCEP;
import static pagesCadastro.TelaCadFornecedor.TextCOMP;
import static pagesCadastro.TelaCadFornecedor.TextCPF;
import static pagesCadastro.TelaCadFornecedor.TextCity;
import static pagesCadastro.TelaCadFornecedor.TextEND;
import static pagesCadastro.TelaCadFornecedor.TextEmail;
import static pagesCadastro.TelaCadFornecedor.TextFONE;
import static pagesCadastro.TelaCadFornecedor.TextFantasia;
import static pagesCadastro.TelaCadFornecedor.TextID;
import static pagesCadastro.TelaCadFornecedor.TextNome;
import static pagesCadastro.TelaCadFornecedor.TextNumber;
import static pagesCadastro.TelaCadFornecedor.TextRazao;
import static pagesCadastro.TelaCadFornecedor.btCad;
import static pagesCadastro.TelaCadFornecedor.btEdit;
import static pagesCadastro.TelaCadFornecedor.btExcluir;
import static pagesCadastro.TelaCadFornecedor.btImprimirForne;
import static pagesCadastro.TelaCadFornecedor.btSearch;
import static pagesCadastro.TelaCadFornecedor.btWPP;
import static pagesCadastro.TelaCadFornecedor.cboEtiquetas;
import static pagesCadastro.TelaCadFornecedor.searchCNPJ;
import static pagesCadastro.TelaCadFornecedor.textBanco;
import static pagesCadastro.TelaCadFornecedor.textPix;
import static pagesCadastro.TelaCadFornecedor.tipoChave;
import static pagesCadastro.TelaCadFornecedor.txtDataC;
import static pagesCadastro.TelaCadFornecedor.txtInfo;
import static pagesCadastro.TelaCadFornecedor.txtObs;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public class SearchFornecedor extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchFornecedor() {
        initComponents();
        conexao = ModuloConexao.conector();
        colocarDados();
        setIcon();
        this.setAlwaysOnTop(true);
        setColorFromEmpresa();
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        tbForne.getTableHeader().setResizingAllowed(false);
        tbForne.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Nome, CPF/CNPJ, RG", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Telefone", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.addOption(new SearchOption("E-mail", new ImageIcon(getClass().getResource("/src/email.png"))));
        txProcurar.addOption(new SearchOption("CEP ou Endereço", new ImageIcon(getClass().getResource("/src/address.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_fornecedor("");
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            PainelUp.setBackground(new Color(15, 102, 122));
        } else {
            PainelUp.setBackground(new Color(147, 167, 39));
        }
    }

    public void colocarDados() {
        String sql = "select idfornecedores as ID,nome as Nome,pessoa as Pessoa,cpfcnpj as CPFCNPJ,telefone as Fone,cep as CEP from fornecedores order by nome";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tbForne.setModel(DbUtils.resultSetToTableModel(rs));
            tbForne.getColumnModel().getColumn(0).setMaxWidth(40);
            tbForne.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_fornecedor(String where, Object... search) {

        String sql = "select idfornecedores as ID,nome as Nome,pessoa as Pessoa,cpfcnpj as CPFCNPJ,telefone as Fone,cep as CEP from fornecedores " + where + "";
        //String sqv = "select idcliente as ID, nome as Nome,cep as CEP,endereco as Endereco,cidade as Cidade,bairro as Bairro,numero as N,complemento as Complemento,uf as UF,sexo as Sexo,email as Email,cpf as CPF, datanasc as Nascimento,tel as Telefone,rg as RG,orgaoexp as Orgao from clientes where cpf like ?";
        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            tbForne.setModel(DbUtils.resultSetToTableModel(rs));
            tbForne.getColumnModel().getColumn(0).setMaxWidth(70);
            tbForne.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setarCamposTabela() {
        int setar = tbForne.getSelectedRow();
        btCad.setEnabled(true);
        String sql = "select idfornecedores,pessoa,nome,cpfcnpj,razao,fantasia,rg,telefone,sexo,email,cep,endereco,cidade,bairro,numero,complemento,uf,outros,obs,datacad,status,tipochave,chave,banco,limite,desconto,etiqueta from fornecedores where idfornecedores like ?";
        String id = (tbForne.getModel().getValueAt(setar, 0).toString());
        String Pessoa = (tbForne.getModel().getValueAt(setar, 2).toString());

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
                    TextFONE.setText(rs.getString(8));
                    CBOSexo.setSelectedItem(rs.getString(9));
                    TextEmail.setText(rs.getString(10));
                    TextCEP.setText(rs.getString(11));
                    TextEND.setText(rs.getString(12));
                    TextCity.setText(rs.getString(13));
                    TextBairro.setText(rs.getString(14));
                    TextNumber.setText(rs.getString(15));
                    TextCOMP.setText(rs.getString(16));
                    CBOuf.setSelectedItem(rs.getString(17));
                    txtInfo.setText(rs.getString(18));
                    txtObs.setText(rs.getString(19));
                    txtDataC.setText(rs.getString(20));
                    Inativo.setSelectedItem(rs.getString(21));
                    tipoChave.setSelectedItem(rs.getString(22));
                    textPix.setText(rs.getString(23));
                    textBanco.setSelectedItem(rs.getString(24));
                    LimiteCreditoforne.setText(rs.getString(25));
                    DescontoPadrao.setText(rs.getString(26));
                    cboEtiquetas.setSelectedItem(rs.getString(27));
                    CheckPF.setEnabled(true);
                    btWPP.setVisible(true);
                    btImprimirForne.setVisible(true);
                    searchCNPJ.setEnabled(false);
                    btEdit.setEnabled(true);
                    btExcluir.setEnabled(true);
                    btCad.setEnabled(false);
                    if (tipoChave.getSelectedItem().equals("--")) {
                        textPix.setEnabled(false);
                        textBanco.setEnabled(false);
                    } else {
                        textPix.setEnabled(true);
                        textBanco.setEnabled(true);
                    }
                    this.dispose();

                } else {
                    CheckPJ.setSelected(true);
                    CheckPF.setSelected(false);
                    CheckPF.setEnabled(false);
                    TextID.setText(rs.getString(1));
                    TextNome.setText(rs.getString(3));
                    TextCPF.setText(rs.getString(4));
                    TextRazao.setText(rs.getString(5));
                    TextFantasia.setText(rs.getString(6));
                    TextFONE.setText(rs.getString(8));
                    CBOSexo.setSelectedItem(rs.getString(9));
                    TextEmail.setText(rs.getString(10));
                    TextCEP.setText(rs.getString(11));
                    TextEND.setText(rs.getString(12));
                    TextCity.setText(rs.getString(13));
                    TextBairro.setText(rs.getString(14));
                    TextNumber.setText(rs.getString(15));
                    TextCOMP.setText(rs.getString(16));
                    CBOuf.setSelectedItem(rs.getString(17));
                    txtInfo.setText(rs.getString(18));
                    txtObs.setText(rs.getString(19));
                    txtDataC.setText(rs.getString(20));
                    Inativo.setSelectedItem(rs.getString(21));
                    tipoChave.setSelectedItem(rs.getString(22));
                    textPix.setText(rs.getString(23));
                    textBanco.setSelectedItem(rs.getString(24));
                    LimiteCreditoforne.setText(rs.getString(25));
                    DescontoPadrao.setText(rs.getString(26));
                    cboEtiquetas.setSelectedItem(rs.getString(27));
                    CheckPJ.setEnabled(true);
                    TextFantasia.setEnabled(true);
                    TextRazao.setEnabled(true);
                    btWPP.setVisible(true);
                    btImprimirForne.setVisible(true);
                    searchCNPJ.setEnabled(true);
                    btEdit.setEnabled(true);
                    btExcluir.setEnabled(true);
                    btCad.setEnabled(false);
                    if (tipoChave.getSelectedItem().equals("--")) {
                        textPix.setEnabled(false);
                        textBanco.setEnabled(false);
                    } else {
                        textPix.setEnabled(true);
                        textBanco.setEnabled(true);
                    }
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
        tbForne = new javax.swing.JTable();
        btCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar fornecedores");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        tbForne.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbForne.setModel(new javax.swing.table.DefaultTableModel(
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
        tbForne.getTableHeader().setReorderingAllowed(false);
        tbForne.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbForneMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbForne);

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
            .addComponent(PainelUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbForneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbForneMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tbForneMouseClicked

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
                pesquisar_fornecedor("where nome like ? or cpfcnpj like ? or rg like ?", text, text, text);
            } else if (option == 1) {
                pesquisar_fornecedor("where telefone like ?", text);
            } else if (option == 2) {
                pesquisar_fornecedor("where email like ?", text);
            } else if (option == 3) {
                pesquisar_fornecedor("where (cep like ? or endereco like ?)", text, text);
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
            java.util.logging.Logger.getLogger(SearchFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchFornecedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchFornecedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelUp;
    private javax.swing.JButton btCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbForne;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
