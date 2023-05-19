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
import static pagesCadastro.TelaCadCli.CBOSexo;
import static pagesCadastro.TelaCadCli.CBOuf;
import static pagesCadastro.TelaCadCli.CancelimgCliente1;
import static pagesCadastro.TelaCadCli.CancelimgCliente2;
import static pagesCadastro.TelaCadCli.CancelimgCliente3;
import static pagesCadastro.TelaCadCli.CheckPF;
import static pagesCadastro.TelaCadCli.CheckPJ;
import static pagesCadastro.TelaCadCli.DescontoPadrao;
import static pagesCadastro.TelaCadCli.Inativo;
import static pagesCadastro.TelaCadCli.LimiteCredito;
import static pagesCadastro.TelaCadCli.Painel1ImageCliente;
import static pagesCadastro.TelaCadCli.TextBairro;
import static pagesCadastro.TelaCadCli.TextCEP;
import static pagesCadastro.TelaCadCli.TextCOMP;
import static pagesCadastro.TelaCadCli.TextCPF;
import static pagesCadastro.TelaCadCli.TextCity;
import static pagesCadastro.TelaCadCli.TextEND;
import static pagesCadastro.TelaCadCli.TextEmail;
import static pagesCadastro.TelaCadCli.TextFONE;
import static pagesCadastro.TelaCadCli.TextFantasia;
import static pagesCadastro.TelaCadCli.TextID;
import static pagesCadastro.TelaCadCli.TextNASC;
import static pagesCadastro.TelaCadCli.TextNome;
import static pagesCadastro.TelaCadCli.TextNumber;
import static pagesCadastro.TelaCadCli.TextRazao;
import static pagesCadastro.TelaCadCli.btCad;
import static pagesCadastro.TelaCadCli.btImprimir;
import static pagesCadastro.TelaCadCli.btEdit;
import static pagesCadastro.TelaCadCli.btExcluir;
import static pagesCadastro.TelaCadCli.btSearch;
import static pagesCadastro.TelaCadCli.btWPP;
import static pagesCadastro.TelaCadCli.cboEtiquetas;
import static pagesCadastro.TelaCadCli.corHex;
import static pagesCadastro.TelaCadCli.habilitar1;
import static pagesCadastro.TelaCadCli.habilitar2;
import static pagesCadastro.TelaCadCli.habilitar3;
import static pagesCadastro.TelaCadCli.searchCNPJ;
import static pagesCadastro.TelaCadCli.showColor;
import static pagesCadastro.TelaCadCli.textBanco;
import static pagesCadastro.TelaCadCli.textPix;
import static pagesCadastro.TelaCadCli.tipoChave;
import static pagesCadastro.TelaCadCli.txtDataC;
import static pagesCadastro.TelaCadCli.txtInfo;
import static pagesCadastro.TelaCadCli.txtObs;
import static pagesCadastro.TelaCadCli.uploadimgCliente1;
import static pagesCadastro.TelaCadCli.viewImage1Cliente;
import static pagesCadastro.TelaCadCli.viewImage2Cliente;
import static pagesCadastro.TelaCadCli.viewImage3Cliente;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public final class SearchCliente extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchCliente() {
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
        txProcurar.addOption(new SearchOption("Nome, CPF/CNPJ, RG, Data de nascimento", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Telefone", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.addOption(new SearchOption("E-mail", new ImageIcon(getClass().getResource("/src/email.png"))));
        txProcurar.addOption(new SearchOption("CEP ou Endereço", new ImageIcon(getClass().getResource("/src/address.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_clienteDC("");
        pesquisar_clienteTNM("");
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
        if (empresa.getText().equals("tanamao")) {

            String sql = "select idclientes as ID,nome as Nome,pessoa as Pessoa,cpf as CPFCNPJ,tel as Fone,cep as CEP from clientes where empresa like 'tanamao' order by nome";
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
        if (empresa.getText().equals("docatec")) {

            String sql = "select idclientes as ID,nome as Nome,pessoa as Pessoa,cpf as CPFCNPJ,tel as Fone,cep as CEP from clientes where empresa like 'docatec' order by nome";
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
    }

    private void pesquisar_clienteDC(String wheredc, Object... searchdc) {

        String sql = "select idclientes as ID,nome as Nome,pessoa as Pessoa,cpf as CPFCNPJ,tel as Fone,cep as CEP from clientes " + wheredc + "";
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

    private void pesquisar_clienteTNM(String wheretn, Object... searchtn) {

        String sql = "select idclientes as ID,nome as Nome,pessoa as Pessoa,cpf as CPFCNPJ,tel as Fone,cep as CEP from clientes " + wheretn + "";
        try {

            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchtn.length; i++) {
                pst.setObject(i + 1, searchtn[i]);
            }
            rs = pst.executeQuery();
            tbClientes.setModel(DbUtils.resultSetToTableModel(rs));
            tbClientes.getColumnModel().getColumn(0).setMaxWidth(70);
            tbClientes.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    
    public void setColorIndex(){
        String sql = "select etiqueta,corhex from etiquetas where etiqueta = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cboEtiquetas.getSelectedItem().toString());
            rs = pst.executeQuery();

            if (cboEtiquetas.getSelectedItem().equals("S/E")) {
                showColor.setBackground(Color.GRAY);
            } else {

                while (rs.next()) {
                    String cor = rs.getString(2);
                    corHex.setText(cor);
                    showColor.setBackground(Color.decode(cor));

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void setarCamposTabela() {

        int setar = tbClientes.getSelectedRow();
        btCad.setEnabled(true);
        String sql = "select idclientes,pessoa,nome,cpf,razaosoc,fantasia,rg,orgaoexp,sexo,datanasc,tel,email,cep,endereco,cidade,bairro,numero,complemento,uf,inativo,info,obs,DATE_FORMAT (`datacad`,'%d/%m/%Y') as DataCad,tipochave,pix,banco,etiqueta,anexo1,anexo2,anexo3,anexo4,limitecredito,descontopadrao from clientes where idclientes like ?";

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
                    LimiteCredito.setText(rs.getString(32));
                    DescontoPadrao.setText(rs.getString(33));
                    viewImage1Cliente.setIcon(null);
                    viewImage2Cliente.setIcon(null);
                    viewImage3Cliente.setIcon(null);
                    if (rs.getBytes(28) == null) {
                    } else {
                        ImageIcon icone1 = new ImageIcon(rs.getBytes(28));
                        icone1.setImage(icone1.getImage().getScaledInstance(Painel1ImageCliente.getWidth() - 1, Painel1ImageCliente.getWidth() - 1, 100));
                        viewImage1Cliente.setIcon(icone1);
                    }

                    //-------------------------------------
                    if (rs.getBytes(29) == null) {
                    } else {
                        ImageIcon icone2 = new ImageIcon(rs.getBytes(29));
                        icone2.setImage(icone2.getImage().getScaledInstance(Painel1ImageCliente.getWidth() - 1, Painel1ImageCliente.getWidth() - 1, 100));
                        viewImage2Cliente.setIcon(icone2);
                    }
                    //-------------------------------------
                    if (rs.getBytes(30) == null) {
                    } else {
                        ImageIcon icone3 = new ImageIcon(rs.getBytes(30));
                        icone3.setImage(icone3.getImage().getScaledInstance(Painel1ImageCliente.getWidth() - 1, Painel1ImageCliente.getWidth() - 1, 100));
                        viewImage3Cliente.setIcon(icone3);
                    }
                    //-------------------------------------}
                    //=======================================================================
                    habilitar1.setVisible(true);
                    uploadimgCliente1.setEnabled(false);
                    CancelimgCliente1.setEnabled(false);
                    habilitar2.setVisible(true);
                    CancelimgCliente2.setEnabled(false);
                    habilitar3.setVisible(true);
                    CancelimgCliente3.setEnabled(false);

                    //=======================================================================
                    if (tipoChave.getSelectedItem().equals("--")) {
                        textPix.setEnabled(false);
                        textBanco.setEnabled(false);
                    } else {
                        textPix.setEnabled(true);
                        textBanco.setEnabled(true);
                    }
                    CheckPF.setEnabled(true);
                    btWPP.setVisible(true);
                    searchCNPJ.setEnabled(false);
                    btEdit.setEnabled(true);
                    btExcluir.setEnabled(true);
                    btCad.setEnabled(false);
                    btImprimir.setVisible(true);
                    setColorIndex();
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
                    LimiteCredito.setText(rs.getString(32));
                    DescontoPadrao.setText(rs.getString(33));
                    viewImage1Cliente.setIcon(null);
                    viewImage2Cliente.setIcon(null);
                    viewImage3Cliente.setIcon(null);

                    if (rs.getBytes(28) == null) {
                    } else {
                        ImageIcon icone1 = new ImageIcon(rs.getBytes(28));
                        icone1.setImage(icone1.getImage().getScaledInstance(Painel1ImageCliente.getWidth() - 1, Painel1ImageCliente.getWidth() - 1, 100));
                        viewImage1Cliente.setIcon(icone1);
                    }

                    //-------------------------------------
                    if (rs.getBytes(29) == null) {
                    } else {
                        ImageIcon icone2 = new ImageIcon(rs.getBytes(29));
                        icone2.setImage(icone2.getImage().getScaledInstance(Painel1ImageCliente.getWidth() - 1, Painel1ImageCliente.getWidth() - 1, 100));
                        viewImage2Cliente.setIcon(icone2);
                    }
                    //-------------------------------------
                    if (rs.getBytes(30) == null) {
                    } else {
                        ImageIcon icone3 = new ImageIcon(rs.getBytes(30));
                        icone3.setImage(icone3.getImage().getScaledInstance(Painel1ImageCliente.getWidth() - 1, Painel1ImageCliente.getWidth() - 1, 100));
                        viewImage3Cliente.setIcon(icone3);
                    }
                    //-------------------------------------
                    //=======================================
                    
                    habilitar1.setVisible(true);
                    uploadimgCliente1.setEnabled(false);
                    CancelimgCliente1.setEnabled(false);
                    habilitar2.setVisible(true);
                    CancelimgCliente2.setEnabled(false);
                    habilitar3.setVisible(true);
                    CancelimgCliente3.setEnabled(false);
                    if (tipoChave.getSelectedItem().equals("--")) {
                        textPix.setEnabled(false);
                        textBanco.setEnabled(false);
                    } else {
                        textPix.setEnabled(true);
                        textBanco.setEnabled(true);
                    }
                    CheckPJ.setEnabled(true);
                    TextFantasia.setEnabled(true);
                    TextRazao.setEnabled(true);
                    btWPP.setVisible(true);
                    searchCNPJ.setEnabled(true);
                    btEdit.setEnabled(true);
                    btExcluir.setEnabled(true);
                    btImprimir.setVisible(true);
                    btCad.setEnabled(false);
                    setColorIndex();
                    this.dispose();

                }
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
        tbClientes = new javax.swing.JTable();

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
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbClientesMousePressed(evt);
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
        if (empresa.getText().equals("tanamao")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                if (option == 0) {
                    pesquisar_clienteTNM("where empresa = 'tanamao' and (nome like ? OR cpf like ? OR rg like ? OR datanasc like ?)", text, text, text, text);
                } else if (option == 1) {
                    pesquisar_clienteTNM("where tel like ? and empresa like 'tanamao'", text);
                } else if (option == 2) {
                    pesquisar_clienteTNM("where email like ? and empresa like 'tanamao'", text);
                } else if (option == 3) {
                    pesquisar_clienteTNM("where empresa = 'tanamao' and (cep like ? or endereco like ?)", text, text);
                }
            }
        }
        if (empresa.getText().equals("docatec")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                if (option == 0) {
                    pesquisar_clienteDC("where empresa = 'docatec' and (nome like ? OR cpf like ? OR rg like ? OR datanasc like ?)", text, text, text, text);
                } else if (option == 1) {
                    pesquisar_clienteDC("where tel like ? and empresa like 'docatec'", text);
                } else if (option == 2) {
                    pesquisar_clienteDC("where email like ? and empresa like 'docatec'", text);
                } else if (option == 3) {
                    pesquisar_clienteDC("where empresa = 'docatec' and (cep like ? or endereco like ?)", text, text);
                }
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void tbClientesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbClientesMousePressed
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tbClientesMousePressed

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
            java.util.logging.Logger.getLogger(SearchCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelUp;
    private javax.swing.JButton btCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private javax.swing.JTable tbClientes;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
