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
import static CadastroProdutos.TelaCadNotebook.DataGarantiaFabrica;
import static CadastroProdutos.TelaCadNotebook.barra1;
import static CadastroProdutos.TelaCadNotebook.barra2;
import static CadastroProdutos.TelaCadNotebook.btCad;
import static CadastroProdutos.TelaCadNotebook.btEdit;
import static CadastroProdutos.TelaCadNotebook.btExcluir;
import static CadastroProdutos.TelaCadNotebook.capmemoriaNote;
import static CadastroProdutos.TelaCadNotebook.cbArmazena;
import static CadastroProdutos.TelaCadNotebook.cbArmazena1;
import static CadastroProdutos.TelaCadNotebook.cbBlue;
import static CadastroProdutos.TelaCadNotebook.cbCapArmazena;
import static CadastroProdutos.TelaCadNotebook.cbCapArmazena1;
import static CadastroProdutos.TelaCadNotebook.cbGarantia;
import static CadastroProdutos.TelaCadNotebook.cbLeitor;
import static CadastroProdutos.TelaCadNotebook.cbMarca;
import static CadastroProdutos.TelaCadNotebook.cbModelo;
import static CadastroProdutos.TelaCadNotebook.cbRefClass;
import static CadastroProdutos.TelaCadNotebook.cbRefGeral;
import static CadastroProdutos.TelaCadNotebook.cbReso;
import static CadastroProdutos.TelaCadNotebook.cbTamanho;
import static CadastroProdutos.TelaCadNotebook.cbTaxa;
import static CadastroProdutos.TelaCadNotebook.cbTeclado;
import static CadastroProdutos.TelaCadNotebook.codeBarNotebook;
import static CadastroProdutos.TelaCadNotebook.descricaoNotebook;
import static CadastroProdutos.TelaCadNotebook.duploArma;
import static CadastroProdutos.TelaCadNotebook.grupoNotebook;
import static CadastroProdutos.TelaCadNotebook.nEntradas;
import static CadastroProdutos.TelaCadNotebook.nForne;
import static CadastroProdutos.TelaCadNotebook.nID;
import static CadastroProdutos.TelaCadNotebook.nLocal;
import static CadastroProdutos.TelaCadNotebook.nMedida;
import static CadastroProdutos.TelaCadNotebook.nMemoria;
import static CadastroProdutos.TelaCadNotebook.nObs;
import static CadastroProdutos.TelaCadNotebook.nPrateleira;
import static CadastroProdutos.TelaCadNotebook.nStatus;
import static CadastroProdutos.TelaCadNotebook.nTecM;
import static CadastroProdutos.TelaCadNotebook.nVeloM;
import static CadastroProdutos.TelaCadNotebook.partnumber;
import static CadastroProdutos.TelaCadNotebook.serialMemoria;
import static CadastroProdutos.TelaCadNotebook.serialhd;
import static CadastroProdutos.TelaCadNotebook.slotsmemoria;
import static CadastroProdutos.TelaCadNotebook.spQtdEmb;
import static CadastroProdutos.TelaCadNotebook.spQtde;
import static CadastroProdutos.TelaCadNotebook.spUsb2;
import static CadastroProdutos.TelaCadNotebook.spUsb3;
import static CadastroProdutos.TelaCadNotebook.subgrupoNotebook;
import static CadastroProdutos.TelaCadNotebook.txtDataC;
import static CadastroProdutos.TelaCadNotebook.txtPrecoCusto;
import static CadastroProdutos.TelaCadNotebook.txtPrecoMin;
import static CadastroProdutos.TelaCadNotebook.txtPrecoVenda;
import static CadastroProdutos.TelaCadNotebook.txtSerial;
import static CadastroProdutos.TelaCadNotebook.txtrefUnica;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

/**
 *
 * @author hugogabriel
 */
public class SearchNotebooks extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchNotebooks() {
        initComponents();
        setIcon();
        setColorFromEmpresa();
        conexao = ModuloConexao.conector();
        colocarDados();
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        tbNoteb.getTableHeader().setResizingAllowed(false);
        tbNoteb.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Descrição", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.addOption(new SearchOption("Marca ou modelo", new ImageIcon(getClass().getResource("/src/marcas.png"))));
        txProcurar.addOption(new SearchOption("Fornecedor", new ImageIcon(getClass().getResource("/src/forne.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_notebook("");

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
        String sql = "select idnotebooks as ID,descricao as Descricao,marca as Marca,modelo as Modelo,nserial as Serial,precovenda as Preco,qtde as QTDE from notebooks order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tbNoteb.setModel(DbUtils.resultSetToTableModel(rs));
            tbNoteb.getColumnModel().getColumn(0).setMaxWidth(60);
            tbNoteb.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_notebook(String where, Object... search) {
        String sql = "select idnotebooks as ID,descricao as Descricao,marca as Marca,modelo as Modelo,nserial as Serial,precovenda as Preco,qtde as QTDE from notebooks " + where + "";

        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            tbNoteb.setModel(DbUtils.resultSetToTableModel(rs));
            tbNoteb.getColumnModel().getColumn(0).setMaxWidth(40);
            tbNoteb.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

//    public void setarCamposTabela() {
//
//        int setar = tbNoteb.getSelectedRow();
//        btCad.setEnabled(true);
//        String sql = "select idnotebooks codebar,descricao,grupo,subgrupo,pn,marca,modelo,nserial,ref,refclass,refunica,memoria,tecm,velocidadem,maxm,slots,seriememoria,processador,modelop,nucleosp,velocidadep,placaonboard,modeloonboard,tipomemoriaonboard,memoriaonboard,placaoffboard,modelooffboard,tipomemoriaoffboard,memoriaoffboard,duploarmazenamento,armazenamento1,capacidade1,armazenamento2,capacidade2,seriesarmazenamento,resolucaotela,taxaatualizacao,tamanhotela,tiporesolucao,tipotela,carregador,potencia,bateria,horas,cornotebook,sistemaop,leitor,bluet,tecladort,garantiafb,validadegarantiafb,usb2,usb3,entradas,precocusto,precomin,precovenda,precopromocao,qtde,undm,qtdeembalagem,localestoque,prateleira,status,fornecedor,skufornecedor,obs,datacadastro,olx,internoolx,externoolx,ml,internoml,externoml,fb,internofb,externofb,ec,internoec,externoec,img1,img2,img3,ncm,csticms,icms,cstpis,pis,cstcofins,cofins,cstipi,ipi,origem,motivodes,substituicaotb,reducaotb from notebooks where idnotebooks like ?";
//        String id = (tbNoteb.getModel().getValueAt(setar, 0).toString());
//
//        try {
//            pst = conexao.prepareStatement(sql);
//
//            pst.setString(1, id);
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                String duploarm = (rs.getString(25));
//
//                if (duploarm.equals("SIM")) {
//                    barra1.setVisible(true);
//                    barra2.setVisible(true);
//                    cbArmazena1.setVisible(true);
//                    cbCapArmazena1.setVisible(true);
//                    nID.setText(rs.getString(1));
//                    //DADOS GERAIS
//                    codeBarNotebook.setText(rs.getString(2));
//                    descricaoNotebook.setText(rs.getString(3));
//                    grupoNotebook.setSelectedItem(rs.getString(4));
//                    subgrupoNotebook.setSelectedItem(rs.getString(5));
//                    partnumber.setText(rs.getString(6));
//                    cbMarca.setSelectedItem(rs.getString(7));
//                    cbModelo.setSelectedItem(rs.getString(8));
//                    txtSerial.setText(rs.getString(9));
//                    cbRefGeral.setSelectedItem(rs.getString(10));
//                    cbRefClass.setSelectedItem(rs.getString(11));
//                    txtrefUnica.setText(rs.getString(12));
//                    //MEMORIA
//                    nMemoria.setSelectedItem(rs.getString(13));
//                    nTecM.setSelectedItem(rs.getString(14));
//                    nVeloM.setSelectedItem(rs.getString(15));
//                    capmemoriaNote.setSelectedItem(rs.getString(16));
//                    slotsmemoria.setValue(rs.getInt(17));
//                    serialMemoria.setText(rs.getString(18));
//                    //PROCESSADOR
//                    nProcess.setSelectedItem(rs.getString(17));
//                    nModelProcess.setText(rs.getString(18));
//                    nNucleosP.setText(rs.getString(19));
//                    nVelocidadep.setText(rs.getString(20));
//                    //PLACA DE VIDEO
//                    cbPlacaVideoNotebook.setSelectedItem(rs.getString(21));
//                    txModeloVideoNotebook.setText(rs.getString(22));
//                    cbTipoRamVideoNotebook.setSelectedItem(rs.getString(23));
//                    cbRamVideoNotebook.setSelectedItem(rs.getString(24));
//                    duploArma.setSelectedItem(rs.getString(25));
//                    cbArmazena.setSelectedItem(rs.getString(26));
//                    cbArmazena1.setSelectedItem(rs.getString(27));
//                    cbCapArmazena.setSelectedItem(rs.getString(28));
//                    cbCapArmazena1.setSelectedItem(rs.getString(29));
//                    serialhd.setText(rs.getString(30));
//                    cbReso.setSelectedItem(rs.getString(31));
//                    cbTaxa.setSelectedItem(rs.getString(32));
//                    cbTamanho.setSelectedItem(rs.getString(33));
//                    cbBateria.setSelectedItem(rs.getString(34));
//                    txDurab.setText(rs.getString(35));
//                    cbCarregador.setSelectedItem(rs.getString(36));
//                    cbLeitor.setSelectedItem(rs.getString(37));
//                    cbBlue.setSelectedItem(rs.getString(38));
//                    cbTeclado.setSelectedItem(rs.getString(39));
//                    cbGarantia.setSelectedItem(rs.getString(40));
//                    DataGarantiaFabrica.setText(rs.getString(41));
//                    spUsb2.setValue(rs.getInt(42));
//                    spUsb3.setValue(rs.getInt(43));
//                    nEntradas.setText(rs.getString(44));
//                    txtPrecoCusto.setText(rs.getString(45));
//                    txtPrecoMin.setText(rs.getString(46));
//                    txtPrecoVenda.setText(rs.getString(47));
//                    spQtde.setValue(rs.getInt(48));
//                    nMedida.setSelectedItem(rs.getString(49));
//                    spQtdEmb.setValue(rs.getInt(50));
//                    nLocal.setSelectedItem(rs.getString(51));
//                    nPrateleira.setSelectedItem(rs.getString(52));
//                    nStatus.setSelectedItem(rs.getString(53));
//                    nForne.setSelectedItem(rs.getString(54));
//                    nObs.setText(rs.getString(55));
//                    txtDataC.setText(rs.getString(56));
//
//                    btEdit.setEnabled(true);
//                    btExcluir.setEnabled(true);
//                    btCad.setEnabled(false);
//                    if (cbPlacaVideoNotebook.getSelectedItem().equals("NAO")) {
//                        cbRamVideoNotebook.setEnabled(false);
//                        cbTipoRamVideoNotebook.setEnabled(false);
//                        txModeloVideoNotebook.setEnabled(false);
//
//                    } else {
//                        cbRamVideoNotebook.setEnabled(true);
//                        cbTipoRamVideoNotebook.setEnabled(true);
//                        txModeloVideoNotebook.setEnabled(true);
//                    }
//                    if (cbGarantia.getSelectedItem().equals("NAO")) {
//                        DataGarantiaFabrica.setEnabled(false);
//                    } else {
//                        DataGarantiaFabrica.setEnabled(true);
//                    }
//                    this.dispose();
//
//                } else {
//                    barra1.setVisible(false);
//                    barra2.setVisible(false);
//                    cbArmazena1.setVisible(false);
//                    cbCapArmazena1.setVisible(false);
//                    nID.setText(rs.getString(1));
//                    nDescricao.setText(rs.getString(2));
//                    nCategoria.setSelectedItem(rs.getString(3));
//                    nCodBar.setText(rs.getString(4));
//                    serieNote.setText(rs.getString(5));
//                    nMarca.setSelectedItem(rs.getString(6));
//                    nModelo.setSelectedItem(rs.getString(7));
//                    nREF.setText(rs.getString(8));
//                    nCor.setText(rs.getString(9));
//                    nSystem.setSelectedItem(rs.getString(10));
//                    nMemoria.setSelectedItem(rs.getString(11));
//                    nTecM.setSelectedItem(rs.getString(12));
//                    nVeloM.setSelectedItem(rs.getString(13));
//                    capmemoriaNote.setSelectedItem(rs.getString(14));
//                    slotsmemoria.setValue(rs.getInt(15));
//                    serialMemoria.setText(rs.getString(16));
//                    nProcess.setSelectedItem(rs.getString(17));
//                    nModelProcess.setText(rs.getString(18));
//                    nNucleosP.setText(rs.getString(19));
//                    nVelocidadep.setText(rs.getString(20));
//                    cbPlacaVideoNotebook.setSelectedItem(rs.getString(21));
//                    txModeloVideoNotebook.setText(rs.getString(22));
//                    cbTipoRamVideoNotebook.setSelectedItem(rs.getString(23));
//                    cbRamVideoNotebook.setSelectedItem(rs.getString(24));
//                    duploArma.setSelectedItem(rs.getString(25));
//                    cbArmazena.setSelectedItem(rs.getString(26));
//                    cbArmazena1.setSelectedItem(rs.getString(27));
//                    cbCapArmazena.setSelectedItem(rs.getString(28));
//                    cbCapArmazena1.setSelectedItem(rs.getString(29));
//                    serialhd.setText(rs.getString(30));
//                    cbReso.setSelectedItem(rs.getString(31));
//                    cbTaxa.setSelectedItem(rs.getString(32));
//                    cbTamanho.setSelectedItem(rs.getString(33));
//                    cbBateria.setSelectedItem(rs.getString(34));
//                    txDurab.setText(rs.getString(35));
//                    cbCarregador.setSelectedItem(rs.getString(36));
//                    cbLeitor.setSelectedItem(rs.getString(37));
//                    cbBlue.setSelectedItem(rs.getString(38));
//                    cbTeclado.setSelectedItem(rs.getString(39));
//                    cbGarantia.setSelectedItem(rs.getString(40));
//                    DataGarantiaFabrica.setText(rs.getString(41));
//                    spUsb2.setValue(rs.getInt(42));
//                    spUsb3.setValue(rs.getInt(43));
//                    nEntradas.setText(rs.getString(44));
//                    txtPrecoCusto.setText(rs.getString(45));
//                    txtPrecoMin.setText(rs.getString(46));
//                    txtPrecoVenda.setText(rs.getString(47));
//                    spQtde.setValue(rs.getInt(48));
//                    nMedida.setSelectedItem(rs.getString(49));
//                    spQtdEmb.setValue(rs.getInt(50));
//                    nLocal.setSelectedItem(rs.getString(51));
//                    nPrateleira.setSelectedItem(rs.getString(52));
//                    nStatus.setSelectedItem(rs.getString(53));
//                    nForne.setSelectedItem(rs.getString(54));
//                    nObs.setText(rs.getString(55));
//                    txtDataC.setText(rs.getString(56));
//
//                    btEdit.setEnabled(true);
//                    btExcluir.setEnabled(true);
//                    btCad.setEnabled(false);
//                    if (cbPlacaVideoNotebook.getSelectedItem().equals("NAO")) {
//                        cbRamVideoNotebook.setEnabled(false);
//                        cbTipoRamVideoNotebook.setEnabled(false);
//                        txModeloVideoNotebook.setEnabled(false);
//
//                    } else {
//                        cbRamVideoNotebook.setEnabled(true);
//                        cbTipoRamVideoNotebook.setEnabled(true);
//                        txModeloVideoNotebook.setEnabled(true);
//                    }
//                    if (cbGarantia.getSelectedItem().equals("NAO")) {
//                        DataGarantiaFabrica.setEnabled(false);
//                    } else {
//                        DataGarantiaFabrica.setEnabled(true);
//                    }
//                    this.dispose();
//                }
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        }

//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNoteb = new javax.swing.JTable();
        btCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar Notebooks");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbNoteb.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbNoteb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRICAO", "GRUPO", "MARCA", "SERIAL", "PRECO", "QTDE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbNoteb.getTableHeader().setReorderingAllowed(false);
        tbNoteb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNotebMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbNoteb);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancel)
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

    private void tbNotebMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNotebMouseClicked
        // TODO add your handling code here:
        // setarCamposTabela();
    }//GEN-LAST:event_tbNotebMouseClicked

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
//        btEdit.setEnabled(false);
//        btExcluir.setEnabled(false);
//        btSearch.setEnabled(true);
    }//GEN-LAST:event_btCancelActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (txProcurar.isSelected()) {
            int option = txProcurar.getSelectedIndex();
            String text = "%" + txProcurar.getText() + "%";
            if (option == 0) {
                pesquisar_notebook("where (codebar like ? or ref like ? or nserial like ?)", text, text, text);
            } else if (option == 1) {
                pesquisar_notebook("where descricao like ?", text);
            } else if (option == 2) {
                pesquisar_notebook("where marca like ? or modelo like ?", text, text);
            } else if (option == 3) {
                pesquisar_notebook("where (fornecedor like ?)", text);
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
            java.util.logging.Logger.getLogger(SearchNotebooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchNotebooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchNotebooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchNotebooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchNotebooks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbNoteb;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
