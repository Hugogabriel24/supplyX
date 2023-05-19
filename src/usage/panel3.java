package usage;

import ModelTableCores.CorNaLinhaUsagePP;
import TableModels.ProdutosModelsToUsage;
import TablesGetSetters.ProdutosToUsage;
import br.com.tanamao.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;
import static usage.panel4.reviewItens;

/**
 *
 * @author hugog
 */
public class panel3 extends javax.swing.JPanel {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ProdutosModelsToUsage modelo;

    public panel3(int step) {
        initComponents();
        conexao = ModuloConexao.conector();
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });

        tableOutros.setDefaultRenderer(Object.class, new CorNaLinhaUsagePP());
        tableOutros.getTableHeader().setResizingAllowed(false);
        tableOutros.getTableHeader().setReorderingAllowed(false);

        TableFindProduto.getTableHeader().setResizingAllowed(false);
        TableFindProduto.getTableHeader().setReorderingAllowed(false);
        colocarDadosOutros();
        spnQtd.setValue(1);
        txProcurar.addOption(new SearchOption("Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Descrição", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.addOption(new SearchOption("Marca ou modelo", new ImageIcon(getClass().getResource("/src/marcas.png"))));
        txProcurar.addOption(new SearchOption("Fornecedor", new ImageIcon(getClass().getResource("/src/forne.png"))));
        txProcurar.setSelectedIndex(0);
    }

    public void colocarDadosOutros() {
        String sql = "select idpartepecas as ID,descricao as Descricao,codigob as CODB,nserie as SerialN,marca as Marca,qtde as Estoque from partepecas order by descricao";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tableOutros.setModel(DbUtils.resultSetToTableModel(rs));
            tableOutros.getColumnModel().getColumn(0).setMaxWidth(60);
            tableOutros.getColumnModel().getColumn(5).setMaxWidth(60);

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

    public void addtocar() {

        int getselect = TabPanelProdutosToUsage.getSelectedIndex();

        if (getselect == 3) {

            ProdutosToUsage O = new ProdutosToUsage();
            int setarOutros = tableOutros.getSelectedRow();

            if (setarOutros >= 0) {

                int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarOutros, 5).toString());

                if (GetEstoque <= 0) {
                    JOptionPane.showMessageDialog(null, "Produto sem estoque disponivel!!");
                } else {

                    O.setID(tableOutros.getModel().getValueAt(setarOutros, 0).toString());
                    O.setProduto("Outros");
                    O.setDescricao(tableOutros.getModel().getValueAt(setarOutros, 1).toString());
                    O.setCodb(tableOutros.getModel().getValueAt(setarOutros, 2).toString());
                    O.setSerial(tableOutros.getModel().getValueAt(setarOutros, 3).toString());
                    O.setMarca(tableOutros.getModel().getValueAt(setarOutros, 4).toString());
                    O.setQtde(spnQtd.getValue().toString());

                    modelo = (ProdutosModelsToUsage) reviewItens.getModel();
                    reviewItens.setModel(modelo);
                    modelo.addProdutosPP(O);


                    Object obj = new Object();

                    int qtdx = Integer.parseInt(spnQtd.getValue().toString());
                    int qtdAtualizada = GetEstoque - qtdx;
                    String getAtualizaQTDE = Integer.toString(qtdAtualizada);
                    obj = getAtualizaQTDE;
                    tableOutros.getModel().setValueAt(obj, setarOutros, 5);
//                    baixaEstoqueCarrinho(id, qtdAtualizada);

                }

            } else {

                JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!!");

            }
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

        TabPanelProdutosToUsage = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableFindProduto = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableFindProduto1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableFindProduto2 = new javax.swing.JTable();
        scrollOutros = new ArchiveTablesVendas.TableScrollButton();
        scrollOutros1 = new javax.swing.JScrollPane();
        tableOutros = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        buttonAdd = new javax.swing.JButton();
        spnQtd = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        TabPanelProdutosToUsage.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        TabPanelProdutosToUsage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPanelProdutosToUsageMouseClicked(evt);
            }
        });

        TableFindProduto = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex,int colIndex){
                return false;
            }
        };
        TableFindProduto.setModel(new javax.swing.table.DefaultTableModel(
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
        TableFindProduto.setEditingRow(0);
        TableFindProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableFindProdutoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TableFindProdutoMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(TableFindProduto);

        TabPanelProdutosToUsage.addTab("Notebooks", jScrollPane2);

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

        TabPanelProdutosToUsage.addTab("Desktop", jScrollPane3);

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

        TabPanelProdutosToUsage.addTab("Monitores", jScrollPane5);

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

        TabPanelProdutosToUsage.addTab("Outros", scrollOutros);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 667, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        TabPanelProdutosToUsage.addTab("Patrimonios", jPanel1);

        txProcurar.setBackground(new java.awt.Color(246, 246, 246));
        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txProcurarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        buttonAdd.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        buttonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/CARRINHO_OFF.png"))); // NOI18N
        buttonAdd.setText("Selecionar item");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        spnQtd.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("QUANTIDADE");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/botao-de-rebobinar.png"))); // NOI18N
        jLabel3.setText("Itens sem estoque disponível!");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        jLabel4.setText("Escolha os itens que serão utilizados");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TabPanelProdutosToUsage)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(spnQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonAdd)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(8, 8, 8)
                .addComponent(TabPanelProdutosToUsage, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(buttonAdd)
                    .addComponent(jLabel1)
                    .addComponent(spnQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TableFindProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProdutoMouseClicked
//        int setarNotes = TableFindProduto.getSelectedRow();
//        int GetEstoque = Integer.parseInt(TableFindProduto.getModel().getValueAt(setarNotes, 7).toString());
//        try {
//            if (GetEstoque == 0) {
//                spnQtd.setModel(new SpinnerNumberModel(0, 0, 0, 0));
//            } else {
//                spnQtd.setModel(new SpinnerNumberModel(1, 1, GetEstoque, 1));
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }//GEN-LAST:event_TableFindProdutoMouseClicked

    private void TableFindProdutoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProdutoMouseEntered

    }//GEN-LAST:event_TableFindProdutoMouseEntered

    private void TableFindProduto1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto1MouseClicked

    private void TableFindProduto1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto1MouseEntered

    private void TableFindProduto2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto2MouseClicked

    private void TableFindProduto2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFindProduto2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TableFindProduto2MouseEntered

    private void tableOutrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutrosMouseClicked
        // TODO add your handling code here:
//        int setarNote = tableOutros.getSelectedRow();
//        int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarNote, 7).toString());
//        try {
//            if (GetEstoque == 0) {
//                spnQtd.setModel(new SpinnerNumberModel(0, 0, 0, 0));
//            } else {
//                spnQtd.setModel(new SpinnerNumberModel(1, 1, GetEstoque, 1));
//            }
//        } catch (Exception e) {
//        }
    }//GEN-LAST:event_tableOutrosMouseClicked

    private void tableOutrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOutrosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tableOutrosMouseEntered

    private void TabPanelProdutosToUsageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPanelProdutosToUsageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabPanelProdutosToUsageMouseClicked

    private void txProcurarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyPressed
        // TODO add your handling code here:
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            int getselect = TabPanelProdutos.getSelectedIndex();
//            ProdutosPP P = new ProdutosPP();
//
//            if (getselect == 0) {
//
//                int setarNote = TableFindProduto.getRowCount();
//
//                if (setarNote == 0) {
//                    Object nPreco = TableFindProduto.getModel().getValueAt(0, 5);
//                    String nPrecoFormated = nPreco.toString().replace(".", "").replace(",", ".");
//                    qtd = Integer.parseInt(spnQtd.getValue().toString());
//                    preco = Double.parseDouble(nPrecoFormated);
//                    txQtdeN.setText(spnQtd.getValue().toString());
//
//                    superTotal = Double.parseDouble(PDV.txTotal.getText());
//                    subtotal = qtd * preco;
//
//                    total = superTotal + subtotal;
//
//                    txtSubtotalN.setText(String.valueOf(subtotal));
//
//                    int GetEstoque = Integer.parseInt(TableFindProduto.getModel().getValueAt(0, 7).toString());
//
//                    if (GetEstoque <= 0) {
//                        JOptionPane.showMessageDialog(null, "Produto sem estoque disponível!!");
//
//                    } else {
//                        P.setID(TableFindProduto.getModel().getValueAt(0, 0).toString());
//                        P.setProduto("Notebook");
//                        P.setDescricao(TableFindProduto.getModel().getValueAt(0, 1).toString());
//                        P.setCodb(TableFindProduto.getModel().getValueAt(0, 2).toString());
//                        P.setSerial(TableFindProduto.getModel().getValueAt(0, 3).toString());
//                        P.setMarca(TableFindProduto.getModel().getValueAt(0, 4).toString());
//                        P.setPreco(TableFindProduto.getModel().getValueAt(0, 5).toString());
//                        P.setQtde(txQtdeN.getText());
//                        P.setSubtotal(txtSubtotalN.getText());
//
//                        modelo = (ProdutosModels) TableCar.getModel();
//                        TableCar.setModel(modelo);
//                        modelo.addProdutosPP(P);
//                        PDV.txTotal.setText(String.valueOf(total));
//
//                        Object obje = new Object();
//                        int qtde = Integer.parseInt(spnQtd.getValue().toString());
//                        int qtdAtt = GetEstoque - qtd;
//                        String getAtualizaqtde = Integer.toString(qtdAtt);
//                        obje = getAtualizaqtde;
//                        TableFindProduto.getModel().setValueAt(obje, 0, 7);
//                    }
//
//                } else {
//
//                    JOptionPane.showMessageDialog(null, "Nenhuma linha selecionada!!");
//
//                }
//            }
//
//            if (getselect == 3) {
//
//                ProdutosPP O = new ProdutosPP();
//
//                int setarOutros = TableFindProduto.getRowCount();
//
//                if (setarOutros == 0) {
//                    String id = tableOutros.getModel().getValueAt(setarOutros, 0).toString();
//                    Object OPreco = tableOutros.getModel().getValueAt(setarOutros, 5);
//                    String nPrecoFormated = OPreco.toString().replace(".", "").replace(",", ".");
//                    qtd = (int) spnQtd.getValue();
//                    preco = Double.parseDouble(nPrecoFormated);
//                    txQtdeO.setText(spnQtd.getValue().toString());
//
//                    superTotal = Double.parseDouble(PDV.txTotal.getText());
//                    subtotal = qtd * preco;
//
//                    total = superTotal + subtotal;
//
//                    txSubtotalO.setText(String.valueOf(subtotal));
//
//                    int GetEstoque = Integer.parseInt(tableOutros.getModel().getValueAt(setarOutros, 7).toString());
//
//                    if (GetEstoque <= 0) {
//                        JOptionPane.showMessageDialog(null, "Produto sem estoque disponivel!!");
//                    } else {
//
//                        O.setID(tableOutros.getModel().getValueAt(setarOutros, 0).toString());
//                        O.setProduto("Outros");
//                        O.setDescricao(tableOutros.getModel().getValueAt(setarOutros, 1).toString());
//                        O.setCodb(tableOutros.getModel().getValueAt(setarOutros, 2).toString());
//                        O.setSerial(tableOutros.getModel().getValueAt(setarOutros, 3).toString());
//                        O.setMarca(tableOutros.getModel().getValueAt(setarOutros, 4).toString());
//                        O.setPreco(tableOutros.getModel().getValueAt(setarOutros, 5).toString());
//                        O.setQtde(txQtdeO.getText());
//                        O.setSubtotal(txSubtotalO.getText());
//
//                        modelo = (ProdutosModels) TableCar.getModel();
//                        TableCar.setModel(modelo);
//                        modelo.addProdutosPP(O);
//                        PDV.txTotal.setText(String.valueOf(total));
//
//                        Object obj = new Object();
//
//                        int qtdx = Integer.parseInt(spnQtd.getValue().toString());
//                        int qtdAtualizada = GetEstoque - qtdx;
//                        String getAtualizaQTDE = Integer.toString(qtdAtualizada);
//                        obj = getAtualizaQTDE;
//                        tableOutros.getModel().setValueAt(obj, setarOutros, 7);
//                        baixaEstoqueCarrinho(id, qtdAtualizada);
//
//                    }
//
//                } else {
//
//                    JOptionPane.showMessageDialog(null, "Nenhum produto encontrado!!");
//
//                }
//            }
//        }
    }//GEN-LAST:event_txProcurarKeyPressed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
//        int getselect = TabPanelProdutos.getSelectedIndex();
//        if (getselect == 3) {
//            if (empresa.getText().equals("tanamao")) {
//                if (txProcurar.isSelected()) {
//                    int option = txProcurar.getSelectedIndex();
//                    String text = "%" + txProcurar.getText() + "%";
//                    if (option == 0) {
//                        pesquisar_PPtnm("where empresa ='tanamao' and (codigob like ? or ref like ? or nserie like ?)", text, text, text);
//                    } else if (option == 1) {
//                        pesquisar_PPtnm("where descricao like ? and empresa ='tanamao'", text);
//                    } else if (option == 2) {
//                        pesquisar_PPtnm("where empresa ='tanamao' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
//                    } else if (option == 3) {
//                        pesquisar_PPtnm("where forne like ? and empresa ='tanamao'", text);
//                    }
//                }
//            }
//            if (empresa.getText().equals("docatec")) {
//                if (txProcurar.isSelected()) {
//                    int option = txProcurar.getSelectedIndex();
//                    String text = "%" + txProcurar.getText() + "%";
//                    if (option == 0) {
//                        pesquisar_PPdc("where empresa ='docatec' and (codigob like ? or ref like ? or nserie like ?)", text, text, text);
//                    } else if (option == 1) {
//                        pesquisar_PPdc("where descricao like ? and empresa ='docatec'", text);
//                    } else if (option == 2) {
//                        pesquisar_PPdc("where empresa ='docatec' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
//                    } else if (option == 3) {
//                        pesquisar_PPdc("where forne like ? and empresa ='docatec'", text);
//                    }
//                }
//            }
//        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        addtocar();
//        NewUsage ng = new NewUsage();
//        Notification noti = new Notification(ng, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "backup completo!!");
//        noti.showNotification();
    }//GEN-LAST:event_buttonAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTabbedPane TabPanelProdutosToUsage;
    public static javax.swing.JTable TableFindProduto;
    public static javax.swing.JTable TableFindProduto1;
    public static javax.swing.JTable TableFindProduto2;
    private javax.swing.JButton buttonAdd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    public static ArchiveTablesVendas.TableScrollButton scrollOutros;
    private javax.swing.JScrollPane scrollOutros1;
    private javax.swing.JSpinner spnQtd;
    public static javax.swing.JTable tableOutros;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
