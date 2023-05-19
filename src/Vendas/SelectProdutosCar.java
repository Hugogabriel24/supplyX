package Vendas;

import ModelTableCores.CorNaLinhaNotebookCar;
import ModelTableCores.CorNaLinhaOutros;
import TableModels.ProdutosModels;
import TablesGetSetters.ProdutosPP;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import net.proteanit.sql.DbUtils;
import static Vendas.PDV.TableCar;
import static Vendas.PDV.btAddProduto;
import static pages.TelaPrincipal.empresa;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public class SelectProdutosCar extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    double total, preco, subtotal, superTotal;
    int qtd;

    ProdutosModels modelo;

    public SelectProdutosCar() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        allConfigTableNotebooks();
        allConfigTableOutros();
        spnQtd.setValue(1);
        colocarDadosOutros();
        colocarDadosNotebook();
        txProcurar.addOption(new SearchOption("Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Descrição", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.addOption(new SearchOption("Marca ou modelo", new ImageIcon(getClass().getResource("/src/marcas.png"))));
        txProcurar.addOption(new SearchOption("Fornecedor", new ImageIcon(getClass().getResource("/src/forne.png"))));
        txProcurar.setSelectedIndex(0);
    }

    public void allConfigTableNotebooks() {
        TableNotebooksCar.setDefaultRenderer(Object.class, new CorNaLinhaNotebookCar());
        TableNotebooksCar.getTableHeader().setResizingAllowed(false);
        TableNotebooksCar.getTableHeader().setReorderingAllowed(false);
    }

    public void allConfigTableOutros() {
        tableOutros.setDefaultRenderer(Object.class, new CorNaLinhaOutros());
        tableOutros.getTableHeader().setResizingAllowed(false);
        tableOutros.getTableHeader().setReorderingAllowed(false);
    }

    public void setIcon() {

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void colocarDadosOutros() {
        String sql = "select idpartepecas as ID,descricao as Descricao,codigob as CODB,nserie as SerialN,marca as Marca,precovenda as Preco,precopromocao as Promocao,qtde as Estoque from partepecas order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tableOutros.setModel(DbUtils.resultSetToTableModel(rs));
            tableOutros.getColumnModel().getColumn(0).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_PPtnm(String wheretnm, Object... searchtnm) {
        String sql = "select idpartepecas as ID,descricao as Descricao,codigob as CODB,nserie as SerialN,marca as Marca,precovenda as Preco,precopromocao as Promocao,qtde as Estoque from partepecas " + wheretnm + "";
        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchtnm.length; i++) {
                pst.setObject(i + 1, searchtnm[i]);
            }
            rs = pst.executeQuery();
            tableOutros.setModel(DbUtils.resultSetToTableModel(rs));
            tableOutros.getColumnModel().getColumn(0).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_PPdc(String wheredc, Object... searchdc) {
        String sql = "select idpartepecas as ID,descricao as Descricao,codigob as CODB,nserie as SerialN,marca as Marca,precovenda as Preco,precopromocao as Promocao,qtde as Estoque from partepecas " + wheredc + "";
        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchdc.length; i++) {
                pst.setObject(i + 1, searchdc[i]);
            }
            rs = pst.executeQuery();
            tableOutros.setModel(DbUtils.resultSetToTableModel(rs));
            tableOutros.getColumnModel().getColumn(0).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void colocarDadosNotebook() {
        String sql = "select idnotebooks as ID,descricao as Descricao,codebar as CODB, nserial as SerialN,marca as Marca,precovenda as Preco,precopromocao as Promocao,qtde as Estoque from notebooks order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            TableNotebooksCar.setModel(DbUtils.resultSetToTableModel(rs));
            TableNotebooksCar.getColumnModel().getColumn(0).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void baixaEstoqueCarrinho(String id, int qtdNovo) {

        String sql = "update partepecas set qtde=? where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
            }

        } catch (Exception e) {

        }

    }

    public void baixaEstoqueNotebook(String id, int qtdNovo) {

        String sql = "update notebooks set qtde=? where idnotebooks =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
            }

        } catch (Exception e) {

        }

    }

    public void addtocar() {

        int getselect = TabPanelProdutos.getSelectedIndex();
        ProdutosPP P = new ProdutosPP();

        if (getselect == 0) {

            int setarNote = TableNotebooksCar.getSelectedRow();

            if (setarNote >= 0) {
                String id = TableNotebooksCar.getModel().getValueAt(setarNote, 0).toString();
                Object nPreco = TableNotebooksCar.getModel().getValueAt(setarNote, 5);
                String nPrecoFormated = nPreco.toString().replace(".", "").replace(",", ".");
                qtd = Integer.parseInt(spnQtd.getValue().toString());
                preco = Double.parseDouble(nPrecoFormated);
                txQtdeN.setText(spnQtd.getValue().toString());

                superTotal = Double.parseDouble(PDV.txTotal.getText());
                subtotal = qtd * preco;

                total = superTotal + subtotal;

                txtSubtotalN.setText(String.valueOf(subtotal));

                int GetEstoque = Integer.parseInt(TableNotebooksCar.getModel().getValueAt(setarNote, 7).toString());

                if (GetEstoque <= 0) {
                    JOptionPane.showMessageDialog(null, "Notebook sem estoque disponível!!");

                } else {
                    P.setID(TableNotebooksCar.getModel().getValueAt(setarNote, 0).toString());
                    P.setProduto("Notebook");
                    P.setDescricao(TableNotebooksCar.getModel().getValueAt(setarNote, 1).toString());
                    P.setCodb(TableNotebooksCar.getModel().getValueAt(setarNote, 2).toString());
                    P.setSerial(TableNotebooksCar.getModel().getValueAt(setarNote, 3).toString());
                    P.setMarca(TableNotebooksCar.getModel().getValueAt(setarNote, 4).toString());
                    P.setPreco(TableNotebooksCar.getModel().getValueAt(setarNote, 5).toString());
                    P.setQtde(txQtdeN.getText());
                    P.setSubtotal(txtSubtotalN.getText());

                    modelo = (ProdutosModels) TableCar.getModel();
                    TableCar.setModel(modelo);
                    modelo.addProdutosPP(P);
                    PDV.txTotal.setText(String.valueOf(total));

                    Object obj = new Object();
                    int qtdx = Integer.parseInt(spnQtd.getValue().toString());
                    int qtdAtualizada = GetEstoque - qtdx;
                    String getAtualizaQTDE = Integer.toString(qtdAtualizada);
                    obj = getAtualizaQTDE;
                    TableNotebooksCar.getModel().setValueAt(obj, setarNote, 7);
                    baixaEstoqueNotebook(id, qtdAtualizada);

                }

            } else {

                JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!!");

            }
        }

        if (getselect == 3) {

            ProdutosPP O = new ProdutosPP();
            int setarOutros = tableOutros.getSelectedRow();

            if (setarOutros >= 0) {
                String id = tableOutros.getModel().getValueAt(setarOutros, 0).toString();
                Object OPreco = tableOutros.getModel().getValueAt(setarOutros, 5);
                String nPrecoFormated = OPreco.toString().replace(".", "").replace(",", ".");
                qtd = (int) spnQtd.getValue();
                preco = Double.parseDouble(nPrecoFormated);
                txQtdeO.setText(spnQtd.getValue().toString());

                superTotal = Double.parseDouble(PDV.txTotal.getText());
                subtotal = qtd * preco;

                total = superTotal + subtotal;

                txSubtotalO.setText(String.valueOf(subtotal));

                int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarOutros, 7).toString());

                if (GetEstoque <= 0) {
                    JOptionPane.showMessageDialog(null, "Produto sem estoque disponivel!!");
                } else {

                    O.setID(tableOutros.getModel().getValueAt(setarOutros, 0).toString());
                    O.setProduto("Outros");
                    O.setDescricao(tableOutros.getModel().getValueAt(setarOutros, 1).toString());
                    O.setCodb(tableOutros.getModel().getValueAt(setarOutros, 2).toString());
                    O.setSerial(tableOutros.getModel().getValueAt(setarOutros, 3).toString());
                    O.setMarca(tableOutros.getModel().getValueAt(setarOutros, 4).toString());
                    O.setPreco(tableOutros.getModel().getValueAt(setarOutros, 5).toString());
                    O.setQtde(txQtdeO.getText());
                    O.setSubtotal(txSubtotalO.getText());

                    modelo = (ProdutosModels) TableCar.getModel();
                    TableCar.setModel(modelo);
                    modelo.addProdutosPP(O);
                    PDV.txTotal.setText(String.valueOf(total));

                    Object obj = new Object();

                    int qtdx = Integer.parseInt(spnQtd.getValue().toString());
                    int qtdAtualizada = GetEstoque - qtdx;
                    String getAtualizaQTDE = Integer.toString(qtdAtualizada);
                    obj = getAtualizaQTDE;
                    tableOutros.getModel().setValueAt(obj, setarOutros, 7);
                    baixaEstoqueCarrinho(id, qtdAtualizada);

                }

            } else {

                JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!!");

            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txQtdeN = new javax.swing.JTextField();
        txtSubtotalN = new javax.swing.JTextField();
        txQtdeO = new javax.swing.JTextField();
        txSubtotalO = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        TabPanelProdutos = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableNotebooksCar = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableFindProduto1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableFindProduto2 = new javax.swing.JTable();
        scrollOutros = new ArchiveTablesVendas.TableScrollButton();
        scrollOutros1 = new javax.swing.JScrollPane();
        tableOutros = new javax.swing.JTable();
        buttonAdd = new javax.swing.JButton();
        spnQtd = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        buttonAddpromo = new javax.swing.JButton();

        txQtdeN.setText("jTextField6");

        txtSubtotalN.setText("jTextField1");

        txQtdeO.setText("jTextField1");

        txSubtotalO.setText("jTextField2");

        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        TabPanelProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPanelProdutosMouseClicked(evt);
            }
        });

        TableNotebooksCar = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex,int colIndex){
                return false;
            }
        };
        TableNotebooksCar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRICAO", "SERIAL", "PRECO", "QTDE"
            }
        ));
        TableNotebooksCar.setEditingRow(0);
        TableNotebooksCar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableNotebooksCarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TableNotebooksCarMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(TableNotebooksCar);

        TabPanelProdutos.addTab("Notebooks", jScrollPane2);

        TableFindProduto1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "MARCA", "SERIAL"
            }
        ));
        TableFindProduto1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableFindProduto1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TableFindProduto1MouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(TableFindProduto1);

        TabPanelProdutos.addTab("Desktop", jScrollPane3);

        TableFindProduto2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "MARCA", "SERIAL"
            }
        ));
        TableFindProduto2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableFindProduto2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TableFindProduto2MouseEntered(evt);
            }
        });
        jScrollPane5.setViewportView(TableFindProduto2);

        TabPanelProdutos.addTab("Monitores", jScrollPane5);

        tableOutros = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex,int colIndex){
                return false;
            }
        };
        tableOutros.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        tableOutros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRICAO", "CODB", "MARCA", "SERIAL", "PRECO", "QTDE"
            }
        ));
        tableOutros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOutrosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tableOutrosMouseEntered(evt);
            }
        });
        scrollOutros1.setViewportView(tableOutros);

        scrollOutros.add(scrollOutros1, java.awt.BorderLayout.CENTER);

        TabPanelProdutos.addTab("Outros", scrollOutros);

        buttonAdd.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        buttonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/CARRINHO_OFF.png"))); // NOI18N
        buttonAdd.setText("Adicionar ao carrinho");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        spnQtd.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("QUANTIDADE");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/estoque-2.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/botao-de-rebobinar.png"))); // NOI18N
        jLabel3.setText("Itens sem estoque disponível!");

        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txProcurarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        buttonAddpromo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        buttonAddpromo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/etiqueta-de-desconto.png"))); // NOI18N
        buttonAddpromo.setText("Adicionar com promoção");
        buttonAddpromo.setEnabled(false);
        buttonAddpromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddpromoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TabPanelProdutos, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonAddpromo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(spnQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonAdd)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(TabPanelProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonAdd)
                        .addComponent(jLabel1)
                        .addComponent(spnQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonAddpromo))
                .addContainerGap(13, Short.MAX_VALUE))
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

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        addtocar();
    }//GEN-LAST:event_buttonAddActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        btAddProduto.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // TODO add your handling code here:
        btAddProduto.setEnabled(true);
    }//GEN-LAST:event_formWindowDeactivated

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        int getselect = TabPanelProdutos.getSelectedIndex();
        if (getselect == 3) {
            if (empresa.getText().equals("tanamao")) {
                if (txProcurar.isSelected()) {
                    int option = txProcurar.getSelectedIndex();
                    String text = "%" + txProcurar.getText() + "%";
                    if (option == 0) {
                        pesquisar_PPtnm("where empresa ='tanamao' and (codigob like ? or ref like ? or nserie like ?)", text, text, text);
                    } else if (option == 1) {
                        pesquisar_PPtnm("where descricao like ? and empresa ='tanamao'", text);
                    } else if (option == 2) {
                        pesquisar_PPtnm("where empresa ='tanamao' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
                    } else if (option == 3) {
                        pesquisar_PPtnm("where forne like ? and empresa ='tanamao'", text);
                    }
                }
            }
            if (empresa.getText().equals("docatec")) {
                if (txProcurar.isSelected()) {
                    int option = txProcurar.getSelectedIndex();
                    String text = "%" + txProcurar.getText() + "%";
                    if (option == 0) {
                        pesquisar_PPdc("where empresa ='docatec' and (codigob like ? or ref like ? or nserie like ?)", text, text, text);
                    } else if (option == 1) {
                        pesquisar_PPdc("where descricao like ? and empresa ='docatec'", text);
                    } else if (option == 2) {
                        pesquisar_PPdc("where empresa ='docatec' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
                    } else if (option == 3) {
                        pesquisar_PPdc("where forne like ? and empresa ='docatec'", text);
                    }
                }
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void txProcurarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int getselect = TabPanelProdutos.getSelectedIndex();
            ProdutosPP P = new ProdutosPP();

            if (getselect == 0) {

                int setarNote = TableNotebooksCar.getRowCount();

                if (setarNote == 0) {
                    Object nPreco = TableNotebooksCar.getModel().getValueAt(0, 5);
                    String nPrecoFormated = nPreco.toString().replace(".", "").replace(",", ".");
                    qtd = Integer.parseInt(spnQtd.getValue().toString());
                    preco = Double.parseDouble(nPrecoFormated);
                    txQtdeN.setText(spnQtd.getValue().toString());

                    superTotal = Double.parseDouble(PDV.txTotal.getText());
                    subtotal = qtd * preco;

                    total = superTotal + subtotal;

                    txtSubtotalN.setText(String.valueOf(subtotal));

                    int GetEstoque = Integer.parseInt(TableNotebooksCar.getModel().getValueAt(0, 7).toString());

                    if (GetEstoque <= 0) {
                        JOptionPane.showMessageDialog(null, "Produto sem estoque disponível!!");

                    } else {
                        P.setID(TableNotebooksCar.getModel().getValueAt(0, 0).toString());
                        P.setProduto("Notebook");
                        P.setDescricao(TableNotebooksCar.getModel().getValueAt(0, 1).toString());
                        P.setCodb(TableNotebooksCar.getModel().getValueAt(0, 2).toString());
                        P.setSerial(TableNotebooksCar.getModel().getValueAt(0, 3).toString());
                        P.setMarca(TableNotebooksCar.getModel().getValueAt(0, 4).toString());
                        P.setPreco(TableNotebooksCar.getModel().getValueAt(0, 5).toString());
                        P.setQtde(txQtdeN.getText());
                        P.setSubtotal(txtSubtotalN.getText());

                        modelo = (ProdutosModels) TableCar.getModel();
                        TableCar.setModel(modelo);
                        modelo.addProdutosPP(P);
                        PDV.txTotal.setText(String.valueOf(total));

                        Object obje = new Object();
                        int qtde = Integer.parseInt(spnQtd.getValue().toString());
                        int qtdAtt = GetEstoque - qtd;
                        String getAtualizaqtde = Integer.toString(qtdAtt);
                        obje = getAtualizaqtde;
                        TableNotebooksCar.getModel().setValueAt(obje, 0, 7);
                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!!");

                }
            }

            if (getselect == 3) {

                ProdutosPP O = new ProdutosPP();

                int setarOutros = TableNotebooksCar.getRowCount();

                if (setarOutros == 0) {
                    String id = tableOutros.getModel().getValueAt(setarOutros, 0).toString();
                    Object OPreco = tableOutros.getModel().getValueAt(setarOutros, 5);
                    String nPrecoFormated = OPreco.toString().replace(".", "").replace(",", ".");
                    qtd = (int) spnQtd.getValue();
                    preco = Double.parseDouble(nPrecoFormated);
                    txQtdeO.setText(spnQtd.getValue().toString());

                    superTotal = Double.parseDouble(PDV.txTotal.getText());
                    subtotal = qtd * preco;

                    total = superTotal + subtotal;

                    txSubtotalO.setText(String.valueOf(subtotal));

                    int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarOutros, 7).toString());

                    if (GetEstoque <= 0) {
                        JOptionPane.showMessageDialog(null, "Produto sem estoque disponivel!!");
                    } else {

                        O.setID(tableOutros.getModel().getValueAt(setarOutros, 0).toString());
                        O.setProduto("Outros");
                        O.setDescricao(tableOutros.getModel().getValueAt(setarOutros, 1).toString());
                        O.setCodb(tableOutros.getModel().getValueAt(setarOutros, 2).toString());
                        O.setSerial(tableOutros.getModel().getValueAt(setarOutros, 3).toString());
                        O.setMarca(tableOutros.getModel().getValueAt(setarOutros, 4).toString());
                        O.setPreco(tableOutros.getModel().getValueAt(setarOutros, 5).toString());
                        O.setQtde(txQtdeO.getText());
                        O.setSubtotal(txSubtotalO.getText());

                        modelo = (ProdutosModels) TableCar.getModel();
                        TableCar.setModel(modelo);
                        modelo.addProdutosPP(O);
                        PDV.txTotal.setText(String.valueOf(total));

                        Object obj = new Object();

                        int qtdx = Integer.parseInt(spnQtd.getValue().toString());
                        int qtdAtualizada = GetEstoque - qtdx;
                        String getAtualizaQTDE = Integer.toString(qtdAtualizada);
                        obj = getAtualizaQTDE;
                        tableOutros.getModel().setValueAt(obj, setarOutros, 7);
                        baixaEstoqueCarrinho(id, qtdAtualizada);

                    }

                } else {

                    JOptionPane.showMessageDialog(null, "Nenhum produto encontrado!!");

                }
            }
        }
    }//GEN-LAST:event_txProcurarKeyPressed

    private void buttonAddpromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddpromoActionPerformed
        int getselect = TabPanelProdutos.getSelectedIndex();
        ProdutosPP P = new ProdutosPP();

        if (getselect == 0) {

            int setarNote = TableNotebooksCar.getSelectedRow();

            if (setarNote >= 0) {
                Object nPreco = TableNotebooksCar.getModel().getValueAt(setarNote, 5);
                String nPrecoFormated = nPreco.toString().replace(".", "").replace(",", ".");
                qtd = Integer.parseInt(spnQtd.getValue().toString());
                preco = Double.parseDouble(nPrecoFormated);
                txQtdeN.setText(spnQtd.getValue().toString());

                superTotal = Double.parseDouble(PDV.txTotal.getText());
                subtotal = qtd * preco;

                total = superTotal + subtotal;

                txtSubtotalN.setText(String.valueOf(subtotal));

                int GetEstoque = Integer.parseInt(TableNotebooksCar.getModel().getValueAt(setarNote, 7).toString());

                if (GetEstoque <= 0) {
                    JOptionPane.showMessageDialog(null, "Produto sem estoque disponível!!");

                } else {
                    P.setID(TableNotebooksCar.getModel().getValueAt(setarNote, 0).toString());
                    P.setProduto("Notebook");
                    P.setDescricao(TableNotebooksCar.getModel().getValueAt(setarNote, 1).toString());
                    P.setCodb(TableNotebooksCar.getModel().getValueAt(setarNote, 2).toString());
                    P.setSerial(TableNotebooksCar.getModel().getValueAt(setarNote, 3).toString());
                    P.setMarca(TableNotebooksCar.getModel().getValueAt(setarNote, 4).toString());
                    P.setPreco(TableNotebooksCar.getModel().getValueAt(setarNote, 5).toString());
                    P.setQtde(txQtdeN.getText());
                    P.setSubtotal(txtSubtotalN.getText());

                    modelo = (ProdutosModels) TableCar.getModel();
                    TableCar.setModel(modelo);
                    modelo.addProdutosPP(P);
                    PDV.txTotal.setText(String.valueOf(total));

                    Object obje = new Object();
                    int qtde = Integer.parseInt(spnQtd.getValue().toString());
                    int qtdAtt = GetEstoque - qtd;
                    String getAtualizaqtde = Integer.toString(qtdAtt);
                    obje = getAtualizaqtde;
                    TableNotebooksCar.getModel().setValueAt(obje, setarNote, 7);
                }

            } else {

                JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!!");

            }
        }

        if (getselect == 3) {

            ProdutosPP O = new ProdutosPP();
            int setarOutros = tableOutros.getSelectedRow();

            if (setarOutros >= 0) {
                String id = tableOutros.getModel().getValueAt(setarOutros, 0).toString();
                Object OPreco = tableOutros.getModel().getValueAt(setarOutros, 6);
                String nPrecoFormated = OPreco.toString().replace(".", "").replace(",", ".");
                qtd = (int) spnQtd.getValue();
                preco = Double.parseDouble(nPrecoFormated);
                txQtdeO.setText(spnQtd.getValue().toString());

                superTotal = Double.parseDouble(PDV.txTotal.getText());
                subtotal = qtd * preco;

                total = superTotal + subtotal;

                txSubtotalO.setText(String.valueOf(subtotal));

                int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarOutros, 7).toString());

                if (GetEstoque <= 0) {
                    JOptionPane.showMessageDialog(null, "Produto sem estoque disponivel!!");
                } else {

                    O.setID(tableOutros.getModel().getValueAt(setarOutros, 0).toString());
                    O.setProduto("Outros");
                    O.setDescricao(tableOutros.getModel().getValueAt(setarOutros, 1).toString());
                    O.setCodb(tableOutros.getModel().getValueAt(setarOutros, 2).toString());
                    O.setSerial(tableOutros.getModel().getValueAt(setarOutros, 3).toString());
                    O.setMarca(tableOutros.getModel().getValueAt(setarOutros, 4).toString());
                    O.setPreco(tableOutros.getModel().getValueAt(setarOutros, 5).toString());
                    O.setQtde(txQtdeO.getText());
                    O.setSubtotal(txSubtotalO.getText());

                    modelo = (ProdutosModels) TableCar.getModel();
                    TableCar.setModel(modelo);
                    modelo.addProdutosPP(O);
                    PDV.txTotal.setText(String.valueOf(total));

                    Object obj = new Object();

                    int qtdx = Integer.parseInt(spnQtd.getValue().toString());
                    int qtdAtualizada = GetEstoque - qtdx;
                    String getAtualizaQTDE = Integer.toString(qtdAtualizada);
                    obj = getAtualizaQTDE;
                    tableOutros.getModel().setValueAt(obj, setarOutros, 7);
                    baixaEstoqueCarrinho(id, qtdAtualizada);

                }

            } else {

                JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!!");

            }
        }
    }//GEN-LAST:event_buttonAddpromoActionPerformed

    private void TabPanelProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPanelProdutosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabPanelProdutosMouseClicked

    private void tableOutrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutrosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableOutrosMouseEntered

    private void tableOutrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutrosMouseClicked
        // TODO add your handling code here:
        int setarNote = tableOutros.getSelectedRow();
        int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarNote, 7).toString());
        try {
            if (GetEstoque == 0) {
                spnQtd.setModel(new SpinnerNumberModel(0, 0, 0, 0));
            } else {
                spnQtd.setModel(new SpinnerNumberModel(1, 1, GetEstoque, 1));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tableOutrosMouseClicked

    private void TableFindProduto2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto2MouseEntered

    private void TableFindProduto2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto2MouseClicked

    private void TableFindProduto1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto1MouseEntered

    private void TableFindProduto1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto1MouseClicked

    private void TableNotebooksCarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableNotebooksCarMouseEntered

    }//GEN-LAST:event_TableNotebooksCarMouseEntered

    private void TableNotebooksCarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableNotebooksCarMouseClicked
        int setarNotes = TableNotebooksCar.getSelectedRow();
        int GetEstoque = Integer.parseInt(TableNotebooksCar.getModel().getValueAt(setarNotes, 7).toString());
        try {
            if (GetEstoque == 0) {
                spnQtd.setModel(new SpinnerNumberModel(0, 0, 0, 0));
            } else {
                spnQtd.setModel(new SpinnerNumberModel(1, 1, GetEstoque, 1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_TableNotebooksCarMouseClicked

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
            java.util.logging.Logger.getLogger(SelectProdutosCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectProdutosCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectProdutosCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectProdutosCar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectProdutosCar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabPanelProdutos;
    public static javax.swing.JTable TableFindProduto1;
    public static javax.swing.JTable TableFindProduto2;
    public static javax.swing.JTable TableNotebooksCar;
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonAddpromo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    public static ArchiveTablesVendas.TableScrollButton scrollOutros;
    private javax.swing.JScrollPane scrollOutros1;
    private javax.swing.JSpinner spnQtd;
    public static javax.swing.JTable tableOutros;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    private javax.swing.JTextField txQtdeN;
    private javax.swing.JTextField txQtdeO;
    private javax.swing.JTextField txSubtotalO;
    private javax.swing.JTextField txtSubtotalN;
    // End of variables declaration//GEN-END:variables
}
