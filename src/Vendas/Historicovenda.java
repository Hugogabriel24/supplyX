package Vendas;

import ArchiveTablesVendas.TableCustom;
import ModelTableCores.HistVendaCor;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.itemVendaDAO;
import br.com.tanamao.model.itemVenda;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import static Vendas.TelaDetalhesVenda.TableProdutos;
import static pages.TelaPrincipal.empresa;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public final class Historicovenda extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private DateChooser chDate = new DateChooser();

    public Historicovenda() {
        initComponents();
        setIcon();
        chDate.setTextField(SearchDate);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setDateFormat(new SimpleDateFormat("dd-MMM-yyyy"));
        chDate.addActionDateChooserListener(new DateChooserAdapter() {

            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String DateFrom = df.format(date.getFromDate());
                String toDate = df.format(date.getToDate());
                loadDate("select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where datacompra BETWEEN '" + DateFrom + "' and '" + toDate + "'");

            }

        });

        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);

        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        conexao = ModuloConexao.conector();
        tablevendas.getTableHeader().setResizingAllowed(false);
        tablevendas.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Id Cliente, Nome, CPF/CNPJ", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.addOption(new SearchOption("Telefone", new ImageIcon(getClass().getResource("/src/tel.png"))));
        txProcurar.addOption(new SearchOption("Id Venda", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Vendedor", new ImageIcon(getClass().getResource("/src/user.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_vendasGeral("");
        colocarDadosVendas();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    private void loadDate(String sql) {

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
            tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
            tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tablevendas.setDefaultRenderer(Object.class, new HistVendaCor());
    }

    private void pesquisar_vendasGeral(String where, Object... search) {
        String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas " + where + "";

        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
            tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
            tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        tablevendas.setDefaultRenderer(Object.class, new HistVendaCor());
    }

    private void colocarDadosVendas() {
        if (empresa.getText().equals("tanamao")) {
            String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where empresa = 'tanamao' order by datacompra";
            try {
                pst = conexao.prepareStatement(sql);

                rs = pst.executeQuery();
                tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            tablevendas.setDefaultRenderer(Object.class, new HistVendaCor());
        }

        if (empresa.getText().equals("docatec")) {
            String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where empresa = 'docatec' order by datacompra";
            try {
                pst = conexao.prepareStatement(sql);

                rs = pst.executeQuery();
                tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
            tablevendas.setDefaultRenderer(Object.class, new HistVendaCor());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        SearchDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        vendaconcluida = new javax.swing.JRadioButton();
        vendacancelada = new javax.swing.JRadioButton();
        vendatotal = new javax.swing.JRadioButton();
        jButton4 = new javax.swing.JButton();
        tableScrollButton2 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablevendas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(1070, 685));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(31, 55, 81));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 3, 28)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/aumentar.png"))); // NOI18N
        jLabel15.setText("    HISTÓRICO DE VENDAS");

        jPanel4.setBackground(new java.awt.Color(31, 55, 81));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FILTRO AVANÇADO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        txProcurar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Filtro por data:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(SearchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/open.png"))); // NOI18N
        jButton1.setText("  Abrir venda");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/conta.png"))); // NOI18N
        jButton2.setText(" Gerar recibo em PDF");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(vendaconcluida);
        vendaconcluida.setText("Vendas concluidas");
        vendaconcluida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendaconcluidaActionPerformed(evt);
            }
        });

        buttonGroup1.add(vendacancelada);
        vendacancelada.setText("Vendas canceladas");
        vendacancelada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendacanceladaActionPerformed(evt);
            }
        });

        buttonGroup1.add(vendatotal);
        vendatotal.setSelected(true);
        vendatotal.setText("Vendas totais");
        vendatotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendatotalActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/nfe.png"))); // NOI18N
        jButton4.setText(" Gerar NFE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tablevendas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablevendas);

        tableScrollButton2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(vendatotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vendaconcluida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vendacancelada)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(tableScrollButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vendaconcluida)
                    .addComponent(vendacancelada)
                    .addComponent(vendatotal))
                .addGap(18, 18, 18)
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int setar = tablevendas.getSelectedRow();
        Object status = tablevendas.getValueAt(setar, 8);
        if (status.equals("concluida")) {
            if (setar >= 0) {
                TelaDetalhesVenda tld = new TelaDetalhesVenda();

                String sql = "select idvendas,idcliente,nomecli,cpfcli,telcli,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,DATE_FORMAT (`garantia`,'%d/%m/%Y') as Garantia,vendedor,canalvenda,formareceb,endentrega,valorfrete,dataentrega,pagador,formapagamento,nparcelas,valordinheiro,valorcartao,descontoaplicado,valorcdesconto,valorrecebido,total from vendas where idvendas like ?";
                String id = (tablevendas.getModel().getValueAt(setar, 0).toString());

                try {
                    pst = conexao.prepareStatement(sql);

                    pst.setString(1, id);
                    rs = pst.executeQuery();
                    status.toString();

                    while (rs.next()) {

                        tld.idvendaDetail.setText(rs.getString(1));
                        tld.txtIDvenda.setText(rs.getString(1));
                        tld.txtIDcliente.setText(rs.getString(2));
                        tld.txtName.setText(rs.getString(3));
                        tld.txtCPF.setText(rs.getString(4));
                        tld.txtFone.setText(rs.getString(5));
                        tld.DataCompra.setText(rs.getString(6));
                        tld.Garantia.setText(rs.getString(7));
                        tld.Vendedor.setSelectedItem(rs.getString(8));
                        tld.CanalVenda.setSelectedItem(rs.getString(9));
                        tld.FormaReceb.setSelectedItem(rs.getString(10));
                        tld.EndEntrega.setText(rs.getString(11));
                        tld.ValorFrete.setText(rs.getString(12));
                        tld.txtDataEntrega.setText(rs.getString(13));
                        tld.cbPagador.setSelectedItem(rs.getString(14));
                        tld.CBOFormapgm.setSelectedItem(rs.getString(15));
                        tld.CBOParcelas.setSelectedItem(rs.getString(16));
                        tld.valorDinheiro.setText(rs.getString(17));
                        tld.valorCartao.setText(rs.getString(18));
                        tld.DescontoApl.setText(rs.getString(19));
                        tld.ValorCdesconto.setText(rs.getString(20));
                        tld.txTotal.setText(rs.getString(22));
                        tld.ValorLoja.setText(rs.getString(21));
                        tld.labelStatus.setText("CONCLUIDA");
                        Color color = new Color(147, 167, 39);
                        tld.labelStatus.setForeground(color);

                        String idVenda = (tablevendas.getModel().getValueAt(setar, 0).toString());

                        itemVendaDAO daoItem = new itemVendaDAO();
                        List<itemVenda> listaitem = daoItem.listaItemVenda(idVenda);

                        DefaultTableModel dados = (DefaultTableModel) TableProdutos.getModel();
                        dados.setNumRows(0);

                        for (itemVenda c : listaitem) {

                            dados.addRow(new Object[]{
                                c.getIdItemVenda(),
                                c.getIdproduto(),
                                c.getProduto(),
                                c.getDescricao(),
                                c.getCodigob(),
                                c.getSerial(),
                                c.getMarca(),
                                c.getPreco(),
                                c.getQtd(),
                                c.getSubtotal()

                            });

                        }

                    }

                } catch (SQLException e) {

                }
                tld.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma venda selecionada!!");
            }

        }
        if (status.equals("cancelada")) {
            if (setar >= 0) {
                TelaDetalhesVenda tld = new TelaDetalhesVenda();

                String sql = "select idvendas,idcliente,nomecli,cpfcli,telcli,datacompra,garantia,vendedor,canalvenda,formareceb,endentrega,valorfrete,dataentrega,pagador,formapagamento,nparcelas,valordinheiro,valorcartao,descontoaplicado,valorcdesconto,valorrecebido,total,motivo,data,log from vendas INNER JOIN cancelamentosvenda ON vendas.idvendas = cancelamentosvenda.idvenda where idvendas like ?";
                String id = (tablevendas.getModel().getValueAt(setar, 0).toString());

                try {
                    pst = conexao.prepareStatement(sql);

                    pst.setString(1, id);
                    rs = pst.executeQuery();
                    status.toString();

                    while (rs.next()) {

                        tld.idvendaDetail.setText(rs.getString(1));
                        tld.txtIDvenda.setText(rs.getString(1));
                        tld.txtIDcliente.setText(rs.getString(2));
                        tld.txtName.setText(rs.getString(3));
                        tld.txtCPF.setText(rs.getString(4));
                        tld.txtFone.setText(rs.getString(5));
                        tld.DataCompra.setText(rs.getString(6));
                        tld.Garantia.setText(rs.getString(7));
                        tld.Vendedor.setSelectedItem(rs.getString(8));
                        tld.CanalVenda.setSelectedItem(rs.getString(9));
                        tld.FormaReceb.setSelectedItem(rs.getString(10));
                        tld.EndEntrega.setText(rs.getString(11));
                        tld.ValorFrete.setText(rs.getString(12));
                        tld.txtDataEntrega.setText(rs.getString(13));
                        tld.cbPagador.setSelectedItem(rs.getString(14));
                        tld.CBOFormapgm.setSelectedItem(rs.getString(15));
                        tld.CBOParcelas.setSelectedItem(rs.getString(16));
                        tld.valorDinheiro.setText(rs.getString(17));
                        tld.valorCartao.setText(rs.getString(18));
                        tld.DescontoApl.setText(rs.getString(19));
                        tld.ValorCdesconto.setText(rs.getString(20));
                        tld.txTotal.setText(rs.getString(22));
                        tld.ValorLoja.setText(rs.getString(21));
                        tld.textCancelamentoArea.setText(rs.getString(23));
                        tld.datacancelamentoArea.setText(rs.getString(24));
                        tld.logcancelamentoarea.setText(rs.getString(25));
                        tld.labelStatus.setText("CANCELADA");
                        tld.labelStatus.setForeground(Color.red);

                        String idVenda = (tablevendas.getModel().getValueAt(setar, 0).toString());

                        itemVendaDAO daoItem = new itemVendaDAO();
                        List<itemVenda> listaitem = daoItem.listaItemVenda(idVenda);

                        DefaultTableModel dados = (DefaultTableModel) TableProdutos.getModel();
                        dados.setNumRows(0);

                        for (itemVenda c : listaitem) {

                            dados.addRow(new Object[]{
                                c.getIdItemVenda(),
                                c.getIdproduto(),
                                c.getProduto(),
                                c.getDescricao(),
                                c.getCodigob(),
                                c.getSerial(),
                                c.getMarca(),
                                c.getPreco(),
                                c.getQtd(),
                                c.getSubtotal()

                            });

                        }

                    }

                } catch (Exception e) {

                }
                tld.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma venda selecionada!!");
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (empresa.getText().equals("tanamao")) {
            int setarVendas = tablevendas.getSelectedRow();
            if (setarVendas >= 0) {
                String Name = (tablevendas.getModel().getValueAt(setarVendas, 2).toString());
                String[] botoes = {"Via cliente/estabelecimento", "Via cliente", "Via estabelecimento"};
                int option = JOptionPane.showOptionDialog(null, "Escolha a opção para gerar o recibo de venda!", "<html><b>GERAR RECIBO</html></b>", 0, JOptionPane.QUESTION_MESSAGE, null, botoes, "");

                if (option == 0) {
                    //Via estabelecimento e cliente
                }
                if (option == 1) {
                    //via cliente
                    int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração deste recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        try {
                            String sql;
                            sql = "select idvendas as N_VENDA,idcliente as IDCLI,nomecli as Nome,cpfcli as CPF,telcli as TEL,datacompra as DataCompra,ean13 as EAN,vendedor as Vendedor,canalvenda as CanalVenda,garantia as Garantia,formapagamento as FormaPG,valorcdesconto as Valorc,total as Total,status as Status,itemvenda.idproduto as ID,itemvenda.descricaoprod as Descricao,nparcelas as Parc,itemvenda.serialprod as Serial,itemvenda.marcaprod as Marca,itemvenda.precoprod as Preco,itemvenda.qtdeprod as Qtde,itemvenda.subtotal as SUB from vendas INNER JOIN itemvenda on vendas.idvendas = itemvenda.idvenda where idvendas=?";
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, tablevendas.getModel().getValueAt(setarVendas, 0).toString());
                            rs = pst.executeQuery();
                            InputStream report = getClass().getResourceAsStream("/recibos/ReciboVendaTNMCliente.jasper");

                            String os = System.getProperty("os.name");
                            if (os.startsWith("Windows")) {

                                JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                                JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/tempreciboCliente.pdf");
                                File file = new File("C://convtec/Supply-x/trash/tempreciboCliente.pdf");

                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                            }

                        } catch (HeadlessException | SQLException | JRException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
                if (option == 2) {
                    //via estabelecimento
                    int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração deste recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        try {
                            String sql;
                            sql = "select idvendas as N_VENDA,idcliente as IDCLI,nomecli as Nome,cpfcli as CPF,telcli as TEL,datacompra as DataCompra,ean13 as EAN,vendedor as Vendedor,pagamento as pgm,canalvenda as CanalVenda,garantia as Garantia,formapagamento as FormaPG,valorcdesconto as Valorc,total as Total,status as Status,itemvenda.idproduto as ID,itemvenda.descricaoprod as Descricao,nparcelas as Parc,descontoaplicado as descontoaplica,valorfrete as valorfrete,formareceb as formareceb,endentrega as endentrega,obsvenda as obs,itemvenda.serialprod as Serial,itemvenda.marcaprod as Marca,itemvenda.precoprod as Preco,itemvenda.qtdeprod as Qtde,itemvenda.subtotal as SUB from vendas INNER JOIN itemvenda on vendas.idvendas = itemvenda.idvenda where idvendas=?";
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, tablevendas.getModel().getValueAt(setarVendas, 0).toString());
                            rs = pst.executeQuery();
                            InputStream report = getClass().getResourceAsStream("/recibos/ReciboVendaTNMEstabelecimento.jasper");

                            String os = System.getProperty("os.name");
                            if (os.startsWith("Windows")) {

                                JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                                JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/Recibo#EST" + Name + ".pdf");
                                File file = new File("C://convtec/Supply-x/trash/Recibo#EST" + Name + ".pdf");

                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nenhuma venda selecionada!", "Mensagem...", JOptionPane.ERROR_MESSAGE);
            }
        }
        //----------RECIBOS DOCATEC
        if (empresa.getText().equals("docatec")) {
            int setarVendas = tablevendas.getSelectedRow();
            if (setarVendas >= 0) {
                String Name = (tablevendas.getModel().getValueAt(setarVendas, 2).toString());
                String[] botoes = {"Via cliente/estabelecimento", "Via cliente", "Via estabelecimento"};
                int option = JOptionPane.showOptionDialog(null, "Escolha a opção de impressão do recibo de venda!", "IMPRIMIR RECIBO", 0, JOptionPane.QUESTION_MESSAGE, null, botoes, "");

                if (option == 0) {
                    //Via estabelecimento e cliente
                }
                if (option == 1) {
                    //via cliente
                    int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração deste recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        try {
                            String sql;
                            sql = "select idvendas as N_VENDA,idcliente as IDCLI,nomecli as Nome,cpfcli as CPF,telcli as TEL,datacompra as DataCompra,ean13 as EAN,vendedor as Vendedor,canalvenda as CanalVenda,garantia as Garantia,formapagamento as FormaPG,valorcdesconto as Valorc,total as Total,status as Status,itemvenda.idproduto as ID,itemvenda.descricaoprod as Descricao,nparcelas as Parc,itemvenda.serialprod as Serial,itemvenda.marcaprod as Marca,itemvenda.precoprod as Preco,itemvenda.qtdeprod as Qtde,itemvenda.subtotal as SUB from vendas INNER JOIN itemvenda on vendas.idvendas = itemvenda.idvenda where idvendas=?";
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, tablevendas.getModel().getValueAt(setarVendas, 0).toString());
                            rs = pst.executeQuery();
                            InputStream report = getClass().getResourceAsStream("/recibos/ReciboVendaDocatecCliente.jasper");

                            String os = System.getProperty("os.name");
                            if (os.startsWith("Windows")) {

                                JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                                JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/tempreciboClienteDocatec.pdf");
                                File file = new File("C://convtec/Supply-x/trash/tempreciboClienteDocatec.pdf");

                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                            }

                        } catch (HeadlessException | SQLException | JRException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
                if (option == 2) {
                    //via estabelecimento
                    int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração deste recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
                    if (confirma == JOptionPane.YES_OPTION) {
                        try {
                            String sql;
                            sql = "select idvendas as N_VENDA,idcliente as IDCLI,nomecli as Nome,cpfcli as CPF,telcli as TEL,datacompra as DataCompra,ean13 as EAN,vendedor as Vendedor,pagamento as pgm,canalvenda as CanalVenda,garantia as Garantia,formapagamento as FormaPG,valorcdesconto as Valorc,total as Total,status as Status,itemvenda.idproduto as ID,itemvenda.descricaoprod as Descricao,nparcelas as Parc,descontoaplicado as descontoaplica,valorfrete as valorfrete,formareceb as formareceb,endentrega as endentrega,obsvenda as obs,itemvenda.serialprod as Serial,itemvenda.marcaprod as Marca,itemvenda.precoprod as Preco,itemvenda.qtdeprod as Qtde,itemvenda.subtotal as SUB from vendas INNER JOIN itemvenda on vendas.idvendas = itemvenda.idvenda where idvendas=?";
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, tablevendas.getModel().getValueAt(setarVendas, 0).toString());
                            rs = pst.executeQuery();
                            InputStream report = getClass().getResourceAsStream("/recibos/ReciboVendaTNMEstabelecimento.jasper");

                            String os = System.getProperty("os.name");
                            if (os.startsWith("Windows")) {

                                JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                                JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/ReciboDocatec#EST" + Name + ".pdf");
                                File file = new File("C://convtec/Supply-x/trash/ReciboDocatec#EST" + Name + ".pdf");

                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(null, e);
                                }
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
            } else {

                JOptionPane.showMessageDialog(null, "Nenhuma venda selecionada!", "Mensagem...", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void vendacanceladaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendacanceladaActionPerformed
        // TODO add your handling code here:

        if (vendacancelada.isSelected()) {
            if (empresa.getText().equals("tanamao")) {
                String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status,motivo as Motivo from vendas INNER JOIN cancelamentosvenda ON vendas.idvendas = cancelamentosvenda.idvenda where status = 'cancelada' AND empresa = 'tanamao' order by nome";
                try {
                    pst = conexao.prepareStatement(sql);

                    rs = pst.executeQuery();
                    tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                    tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                    tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (vendacancelada.isSelected()) {
            if (empresa.getText().equals("docatec")) {
                String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status,motivo as Motivo from vendas INNER JOIN cancelamentosvenda ON vendas.idvendas = cancelamentosvenda.idvenda where status = 'cancelada' AND empresa = 'docatec' order by nome";
                try {
                    pst = conexao.prepareStatement(sql);

                    rs = pst.executeQuery();
                    tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                    tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                    tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

    }//GEN-LAST:event_vendacanceladaActionPerformed

    private void vendaconcluidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendaconcluidaActionPerformed
        // TODO add your handling code here:
        if (vendaconcluida.isSelected()) {
            if (empresa.getText().equals("docatec")) {
                String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where status = 'concluida' AND empresa = 'docatec' order by nome";
                try {
                    pst = conexao.prepareStatement(sql);

                    rs = pst.executeQuery();
                    tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                    tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                    tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        if (vendaconcluida.isSelected()) {
            if (empresa.getText().equals("tanamao")) {
                String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where status = 'concluida' AND empresa = 'tanamao' order by nome";
                try {
                    pst = conexao.prepareStatement(sql);

                    rs = pst.executeQuery();
                    tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                    tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                    tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

    }//GEN-LAST:event_vendaconcluidaActionPerformed

    private void vendatotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendatotalActionPerformed
        // TODO add your handling code here:
        if (vendatotal.isSelected()) {
            if (empresa.getText().equals("tanamao")) {
                String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where empresa = 'tanamao' order by nome";
                try {
                    pst = conexao.prepareStatement(sql);

                    rs = pst.executeQuery();
                    tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                    tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                    tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
        if (vendatotal.isSelected()) {
            if (empresa.getText().equals("docatec")) {
                String sql = "select idvendas as ID,nomecli as Nome,cpfcli as CPF,DATE_FORMAT (`datacompra`,'%d/%m/%Y') as DataCompra,vendedor as Vendedor,canalvenda as CanalVenda,formapagamento as FormaPG,total as Total,status as Status from vendas where empresa = 'docatec' order by nome";
                try {
                    pst = conexao.prepareStatement(sql);

                    rs = pst.executeQuery();
                    tablevendas.setModel(DbUtils.resultSetToTableModel(rs));
                    tablevendas.getColumnModel().getColumn(0).setMaxWidth(60);
                    tablevendas.getColumnModel().getColumn(7).setMaxWidth(70);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_vendatotalActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (empresa.getText().equals("tanamao")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                if (option == 0) {
                    pesquisar_vendasGeral("where empresa like 'tanamao' and (idcliente like ? or nomecli like ? or cpfcli like ?)", text, text, text);
                } else if (option == 1) {
                    pesquisar_vendasGeral("where telcli like ? and empresa like 'tanamao'", text);
                } else if (option == 2) {
                    pesquisar_vendasGeral("where idvendas like ? and empresa like 'tanamao'", text);
                } else if (option == 3) {
                    pesquisar_vendasGeral("where vendedor like ? and empresa like 'tanamao'", text);
                }
            }
        }
        if (empresa.getText().equals("docatec")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                if (option == 0) {
                    pesquisar_vendasGeral("where empresa like 'docatec' and (idcliente like ? or nomecli like ? or cpfcli like ?)", text, text, text);
                } else if (option == 1) {
                    pesquisar_vendasGeral("where telcli like ? and empresa like 'docatec'", text);
                } else if (option == 2) {
                    pesquisar_vendasGeral("where idvendas like ? and empresa like 'docatec'", text);
                } else if (option == 3) {
                    pesquisar_vendasGeral("where empresa like 'docatec' and (vendedor like ?)", text);
                }
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int setarVendas = tablevendas.getSelectedRow();
        if (setarVendas >= 0) {
            try {
                String sql = "select nfe from vendas where idvendas=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, tablevendas.getModel().getValueAt(setarVendas, 0).toString());
                rs = pst.executeQuery();

                while (rs.next()) {
                    int nfe = rs.getInt(1);
                    if (nfe >= 1) {

                    } else {
                        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/nfe.png")));
                        JOptionPane.showMessageDialog(null, "NFE já foi gerada!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                    }

                }

            } catch (Exception e) {
            }

        } else {

        }
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
        FlatIntelliJLaf.setup();
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Historicovenda.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Historicovenda.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Historicovenda.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Historicovenda.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Historicovenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField SearchDate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton2;
    public static javax.swing.JTable tablevendas;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    private javax.swing.JRadioButton vendacancelada;
    private javax.swing.JRadioButton vendaconcluida;
    private javax.swing.JRadioButton vendatotal;
    // End of variables declaration//GEN-END:variables
}
