/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesSearchs;

import br.com.tanamao.dal.ModuloConexao;
import com.lowagie.text.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Formatter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import static pages.TelaPrincipal.empresa;
import static pagesCadastro.TelaCadFuncionario.CBOSexo;
import static pagesCadastro.TelaCadFuncionario.CBOuf;
import static pagesCadastro.TelaCadFuncionario.DateContrato;
import static pagesCadastro.TelaCadFuncionario.DatePgm;
import static pagesCadastro.TelaCadFuncionario.TextBairro;
import static pagesCadastro.TelaCadFuncionario.TextCEP;
import static pagesCadastro.TelaCadFuncionario.TextCOMP;
import static pagesCadastro.TelaCadFuncionario.TextCPF;
import static pagesCadastro.TelaCadFuncionario.TextCity;
import static pagesCadastro.TelaCadFuncionario.TextEND;
import static pagesCadastro.TelaCadFuncionario.TextEmail;
import static pagesCadastro.TelaCadFuncionario.TextFONE;
import static pagesCadastro.TelaCadFuncionario.TextID;
import static pagesCadastro.TelaCadFuncionario.TextNome;
import static pagesCadastro.TelaCadFuncionario.TextNumber;
import static pagesCadastro.TelaCadFuncionario.TextRG;
import static pagesCadastro.TelaCadFuncionario.btCad;
import static pagesCadastro.TelaCadFuncionario.btEdit;
import static pagesCadastro.TelaCadFuncionario.btExcluir;
import static pagesCadastro.TelaCadFuncionario.btSearchFunc;
import static pagesCadastro.TelaCadFuncionario.cbCargo;
import static pagesCadastro.TelaCadFuncionario.txDataN;
import static pagesCadastro.TelaCadFuncionario.txMat;
import static pagesCadastro.TelaCadFuncionario.txcomissao;
import static pagesCadastro.TelaCadFuncionario.txremunera;
import static pagesCadastro.TelaCadFuncionario.txtDatac;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

/**
 *
 * @author User
 */
public class SearchFuncionario extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchFuncionario() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        colocarDados();
        setColorFromEmpresa();
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        tbFuncionarios.getTableHeader().setResizingAllowed(false);
        tbFuncionarios.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Nome, CPF/CNPJ, RG, Data de nascimento", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Telefone", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.addOption(new SearchOption("E-mail", new ImageIcon(getClass().getResource("/src/email.png"))));
        txProcurar.addOption(new SearchOption("CEP ou Endereço", new ImageIcon(getClass().getResource("/src/address.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_func("");
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

    public void colocarDados() {

        String sql = "select idfunc as ID,nome as Nome,cpf as CPFCNPJ,telefone as Fone,cep as CEP, cargo as Cargo from funcionarios order by nome";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tbFuncionarios.setModel(DbUtils.resultSetToTableModel(rs));
            tbFuncionarios.getColumnModel().getColumn(0).setMaxWidth(70);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_func(String where, Object... search) {

        String sql = "select idfunc as ID,nome as Nome,cpf as CPFCNPJ,telefone as Fone,cep as CEP, cargo as Cargo from funcionarios " + where + "";
        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            tbFuncionarios.setModel(DbUtils.resultSetToTableModel(rs));
            tbFuncionarios.getColumnModel().getColumn(0).setMaxWidth(70);
            tbFuncionarios.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setarCamposTabela() {
        int setar = tbFuncionarios.getSelectedRow();
        String sql = "select nome,cpf,rg,sexo,telefone,datan,email,cep,endereco,cidade,bairro,numero,complemento,uf,cargo,datacontrato,datapagamento,remunera,comissao,dataadc,mat,idfunc from funcionarios where idfunc like ?";
        String id = (tbFuncionarios.getModel().getValueAt(setar, 0).toString());

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                TextNome.setText(rs.getString(1));
                TextCPF.setText(rs.getString(2));
                TextRG.setText(rs.getString(3));
                CBOSexo.setSelectedItem(rs.getString(4));
                TextFONE.setText(rs.getString(5));
                txDataN.setText(rs.getString(6));
                TextEmail.setText(rs.getString(7));
                TextCEP.setText(rs.getString(8));
                TextEND.setText(rs.getString(9));
                TextCity.setText(rs.getString(10));
                TextBairro.setText(rs.getString(11));
                TextNumber.setText(rs.getString(12));
                TextCOMP.setText(rs.getString(13));
                CBOuf.setSelectedItem(rs.getString(14));
                cbCargo.setSelectedItem(rs.getString(15));
                DateContrato.setDate(rs.getDate(16));
                DatePgm.setDate(rs.getDate(17));
                txremunera.setText(rs.getString(18));
                txcomissao.setText(rs.getString(19));
                txtDatac.setText(rs.getString(20));
                txMat.setText(rs.getString(21));
                TextID.setText(rs.getString(22));
                btEdit.setEnabled(true);
                btExcluir.setEnabled(true);
                btCad.setEnabled(false);
                this.dispose();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbFuncionarios = new javax.swing.JTable();
        btCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar funcionario");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        txProcurar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbFuncionarios.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "CPF/CNPJ", "TELEFONE", "CEP", "CARGO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbFuncionarios.getTableHeader().setReorderingAllowed(false);
        tbFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFuncionariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbFuncionarios);

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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btCancel)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

    private void tbFuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFuncionariosMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tbFuncionariosMouseClicked

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
        btSearchFunc.setEnabled(true);
    }//GEN-LAST:event_btCancelActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (txProcurar.isSelected()) {
            int option = txProcurar.getSelectedIndex();
            String text = "%" + txProcurar.getText() + "%";
            if (option == 0) {
                pesquisar_func("where (nome like ? or cpf like ? or rg like ? or datan like ?)", text, text, text, text);
            } else if (option == 1) {
                pesquisar_func("where telefone like ?", text);
            } else if (option == 2) {
                pesquisar_func("where email like ?", text);
            } else if (option == 3) {
                pesquisar_func("where (cep like ? or endereco like ?)", text, text);
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        btSearchFunc.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        btSearchFunc.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(SearchFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchFuncionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbFuncionarios;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
