/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesEstoque;

import ModelTableCores.CorNaLinhaEstoqueNotebook;
import br.com.tanamao.dal.ModuloConexao;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import net.proteanit.sql.DbUtils;
import CadastroProdutos.TelaAlteracaoComp;
import static pages.TelaPrincipal.empresa;
import pagesDetails.NotebookDetail;
import static pagesDetails.NotebookDetail.idNotebooks;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

/**
 *
 * @author hugogabriel
 */
public class TelaEstoqueDesktops extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaEstoqueDesktops() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        setColorFromEmpresa();
        colocarDadosNotebook();
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        spinnerNew.setModel(new SpinnerNumberModel(1, 0, 99999, 1));
        tableNotebooks.setDefaultRenderer(Object.class, new CorNaLinhaEstoqueNotebook());
        tableNotebooks.getTableHeader().setResizingAllowed(false);
        tableNotebooks.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption(" Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption(" Descrição", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.addOption(new SearchOption(" Marca ou modelo", new ImageIcon(getClass().getResource("/src/marcas.png"))));
        txProcurar.addOption(new SearchOption(" Fornecedor", new ImageIcon(getClass().getResource("/src/forne.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_notebook("");
    }

    public void setColorFromEmpresa() {

        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel9.setBackground(new Color(15, 102, 122));
        } else {
            jPanel9.setBackground(new Color(147, 167, 39));
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void colocarDadosNotebook() {
        String emp = empresa.getText();
        String sql = "select idnotebooks as ID,codebar as Cod,descricao as Descricao, nserial as ServiceTag,marca as Marca,status as Status,precovenda as Preco,qtde as Estoque from notebooks where empresa = ? order by descricao";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, emp);
            rs = pst.executeQuery();
            JTableHeader header = tableNotebooks.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tableNotebooks.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn Service = columnModel.getColumn(3);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Quantidade");
            Service.setHeaderValue("Service Tag");
            header.repaint();
            tableNotebooks.getColumnModel().getColumn(0).setMaxWidth(60);
            tableNotebooks.getColumnModel().getColumn(6).setMaxWidth(80);
            tableNotebooks.getColumnModel().getColumn(7).setMaxWidth(80);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCamposTabela() {

        int setar = tableNotebooks.getSelectedRow();
        String sql = "select * from notebooks where idnotebooks like ?";

        String id = (tableNotebooks.getModel().getValueAt(setar, 0).toString());

        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                int qtde = rs.getInt(48);
                txtQtdAtual.setText(Integer.toString(qtde));
                idselected.setText(rs.getString(1));
                descriselected.setText(rs.getString(2));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_notebook(String where, Object... search) {
        String sql = "select idnotebooks as ID,codebar as COd,descricao as Descricao, nserial as ServiceTag,marca as Marca,status as Status,precovenda as Preco,qtde as Estoque from notebooks " + where + "";

        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            JTableHeader header = tableNotebooks.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tableNotebooks.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn Service = columnModel.getColumn(3);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Quantidade");
            Service.setHeaderValue("Service Tag");
            header.repaint();
            tableNotebooks.getColumnModel().getColumn(0).setMaxWidth(60);
            tableNotebooks.getColumnModel().getColumn(6).setMaxWidth(80);
            tableNotebooks.getColumnModel().getColumn(7).setMaxWidth(80);

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

        idselected = new javax.swing.JTextField();
        descriselected = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        btRemoveQtde = new javax.swing.JButton();
        btAddqtde = new javax.swing.JButton();
        spinnerNew = new javax.swing.JSpinner();
        txtQtdAtual = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        jSeparator1 = new javax.swing.JSeparator();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        DetalhesNotebook = new javax.swing.JButton();
        BTEtiqueta = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableNotebooks = new javax.swing.JTable();
        btDetalhesVenda1 = new javax.swing.JButton();
        ProdutosTotais = new javax.swing.JRadioButton();
        ProdutosDisponiveis = new javax.swing.JRadioButton();
        ProdutosOff = new javax.swing.JRadioButton();

        idselected.setText("jTextField1");

        descriselected.setText("jTextField1");

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/mudar.png"))); // NOI18N
        jButton1.setText("Alteração rapida de componente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(67, 118, 218));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 3, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("ESTOQUE DE DESKTOPS");

        btRemoveQtde.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btRemoveQtde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveQtde.setText("Remover");
        btRemoveQtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveQtdeActionPerformed(evt);
            }
        });

        btAddqtde.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btAddqtde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionar-acrescentar.png"))); // NOI18N
        btAddqtde.setText("Adicionar");
        btAddqtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddqtdeActionPerformed(evt);
            }
        });

        txtQtdAtual.setEnabled(false);

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("QUANTIDADE ATUAL");

        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jButton7.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton7.setText("Limpar lista de impressão");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/addlista.png"))); // NOI18N
        jButton3.setText(" Adicionar item a lista de impressão");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtQtdAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spinnerNew, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btAddqtde)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btRemoveQtde))))
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addComponent(txProcurar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton7))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQtdAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinnerNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btAddqtde)
                        .addComponent(btRemoveQtde)))
                .addContainerGap())
        );

        DetalhesNotebook.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        DetalhesNotebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/detalhes.png"))); // NOI18N
        DetalhesNotebook.setText("Detalhes do Desktop");
        DetalhesNotebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DetalhesNotebookActionPerformed(evt);
            }
        });

        BTEtiqueta.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        BTEtiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/etiqueta-de-preco_1.png"))); // NOI18N
        BTEtiqueta.setText("Gerar etiqueta");
        BTEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTEtiquetaActionPerformed(evt);
            }
        });

        tableNotebooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableNotebooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableNotebooksMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableNotebooks);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btDetalhesVenda1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btDetalhesVenda1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionar-ficheiro_1.png"))); // NOI18N
        btDetalhesVenda1.setText("Cadastrar um Desktop igual");

        ProdutosTotais.setSelected(true);
        ProdutosTotais.setText("Produtos totais");
        ProdutosTotais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutosTotaisActionPerformed(evt);
            }
        });

        ProdutosDisponiveis.setText("Produtos disponíveis");
        ProdutosDisponiveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutosDisponiveisActionPerformed(evt);
            }
        });

        ProdutosOff.setText("Produtos sem estoque");
        ProdutosOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutosOffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(BTEtiqueta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btDetalhesVenda1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DetalhesNotebook))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(ProdutosTotais)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProdutosDisponiveis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProdutosOff)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProdutosDisponiveis)
                    .addComponent(ProdutosOff)
                    .addComponent(ProdutosTotais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DetalhesNotebook)
                    .addComponent(BTEtiqueta)
                    .addComponent(btDetalhesVenda1))
                .addGap(12, 12, 12))
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

    private void DetalhesNotebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DetalhesNotebookActionPerformed
        // TODO add your handling code here:
        if (tableNotebooks.getSelectedRow() >= 0) {
            NotebookDetail ntb = new NotebookDetail();
            ntb.setVisible(true);
            int setarNotes = tableNotebooks.getSelectedRow();
            String getID = tableNotebooks.getModel().getValueAt(setarNotes, 0).toString();
            idNotebooks.setText(getID);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um notebook!");
        }


    }//GEN-LAST:event_DetalhesNotebookActionPerformed

    private void BTEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTEtiquetaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_BTEtiquetaActionPerformed

    private void btAddqtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddqtdeActionPerformed
        int getselect = tableNotebooks.getSelectedRow();
        if (getselect >= 0) {
            int total;
            String qtdAtual = txtQtdAtual.getText();
            int qtda = Integer.parseInt(qtdAtual);
            int qtdnew = Integer.parseInt(spinnerNew.getValue().toString());
            total = qtda + qtdnew;

            String sql = "update notebooks set qtde=? where idnotebooks =?";
            conexao = ModuloConexao.conector();

            try {

                String descri = descriselected.getText();

                pst = conexao.prepareStatement(sql);
                pst.setInt(1, total);
                pst.setString(2, idselected.getText());

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + descri + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);
                    idselected.setText(null);
                    descriselected.setText(null);
                    txtQtdAtual.setText(null);
                    colocarDadosNotebook();
                }

            } catch (Exception e) {
                System.out.println(e);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }

    }//GEN-LAST:event_btAddqtdeActionPerformed

    private void btRemoveQtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveQtdeActionPerformed
//         TODO add your handling code here:
        int getselect = tableNotebooks.getSelectedRow();

        if (getselect >= 0) {

            int total;
            String qtdAtual = txtQtdAtual.getText();
            int qtda = Integer.parseInt(qtdAtual);
            int qtdnew = Integer.parseInt(spinnerNew.getValue().toString());
            total = qtda - qtdnew;

            String descri = descriselected.getText();

            if (qtdnew <= qtda) {

                String sql = "update notebooks set qtde=? where idnotebooks =?";
                conexao = ModuloConexao.conector();

                try {

                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, total);
                    pst.setString(2, idselected.getText());

                    int adicionado = pst.executeUpdate();

                    if (adicionado > 0) {
                        DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + descri + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);
                        idselected.setText(null);
                        descriselected.setText(null);
                        txtQtdAtual.setText(null);
                        colocarDadosNotebook();
                    }

                } catch (Exception e) {
                    System.out.println(e);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Quantidade que deseja retirar é maior que a existente!");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }
    }//GEN-LAST:event_btRemoveQtdeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TelaAlteracaoComp tlac = new TelaAlteracaoComp();
        tlac.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
//        modelo.limpar();
//        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Fila de impressão limpa com sucesso!");
//        noti.showNotification();
//        tablePrint.setModel(modelo);
//        geraretq.setEnabled(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

//        dialogPrint.setSize(373, 274);
//        dialogPrint.setLocation(this.getLocationOnScreen().x + this.getWidth(), this.getLocationOnScreen().y);
//        dialogPrint.setVisible(true);
//        addToPrint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ProdutosTotaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutosTotaisActionPerformed
        // TODO add your handling code here:
//        String emp = empresa.getText();
//        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? order by descricao";
//        try {
//            pst = conexao.prepareStatement(sql);
//            pst.setString(1, emp);
//            rs = pst.executeQuery();
//            JTableHeader header = tablePP.getTableHeader();
//            TableColumnModel columnModel = header.getColumnModel();
//            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
//            TableColumn descricaoColumn = columnModel.getColumn(2);
//            TableColumn codabarColumn = columnModel.getColumn(1);
//            TableColumn precoCol = columnModel.getColumn(7);
//            descricaoColumn.setHeaderValue("Descrição");
//            codabarColumn.setHeaderValue("Código de barras");
//            precoCol.setHeaderValue("Preço");
//            header.repaint();
//            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
//            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
//            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
//            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
//            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
    }//GEN-LAST:event_ProdutosTotaisActionPerformed

    private void ProdutosDisponiveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutosDisponiveisActionPerformed
        // TODO add your handling code here:
//        String emp = empresa.getText();
//        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? AND qtde > '0' order by descricao";
//        try {
//            pst = conexao.prepareStatement(sql);
//            pst.setString(1, emp);
//            rs = pst.executeQuery();
//            JTableHeader header = tablePP.getTableHeader();
//            TableColumnModel columnModel = header.getColumnModel();
//            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
//            TableColumn descricaoColumn = columnModel.getColumn(2);
//            TableColumn codabarColumn = columnModel.getColumn(1);
//            TableColumn precoCol = columnModel.getColumn(7);
//            descricaoColumn.setHeaderValue("Descrição");
//            codabarColumn.setHeaderValue("Código de barras");
//            precoCol.setHeaderValue("Preço");
//            header.repaint();
//            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
//            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
//            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
//            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
//            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
    }//GEN-LAST:event_ProdutosDisponiveisActionPerformed

    private void ProdutosOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutosOffActionPerformed
        // TODO add your handling code here:

//        String emp = empresa.getText();
//        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? AND qtde = '0' order by descricao";
//        try {
//            pst = conexao.prepareStatement(sql);
//            pst.setString(1, emp);
//            rs = pst.executeQuery();
//            JTableHeader header = tablePP.getTableHeader();
//            TableColumnModel columnModel = header.getColumnModel();
//            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
//            TableColumn descricaoColumn = columnModel.getColumn(2);
//            TableColumn codabarColumn = columnModel.getColumn(1);
//            TableColumn precoCol = columnModel.getColumn(7);
//            descricaoColumn.setHeaderValue("Descrição");
//            codabarColumn.setHeaderValue("Código de barras");
//            precoCol.setHeaderValue("Preço");
//            header.repaint();
//            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
//            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
//            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
//            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
//            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
//        }
    }//GEN-LAST:event_ProdutosOffActionPerformed

    private void tableNotebooksMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNotebooksMousePressed
        // TODO add your handling code here:
        if (evt.getButton() != MouseEvent.BUTTON1 || evt.getClickCount() != 2) {
        } else {
            DetalhesNotebook.doClick();
        }
    }//GEN-LAST:event_tableNotebooksMousePressed

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
            java.util.logging.Logger.getLogger(TelaEstoqueDesktops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEstoqueDesktops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEstoqueDesktops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEstoqueDesktops.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEstoqueDesktops().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTEtiqueta;
    private javax.swing.JButton DetalhesNotebook;
    private javax.swing.JRadioButton ProdutosDisponiveis;
    private javax.swing.JRadioButton ProdutosOff;
    private javax.swing.JRadioButton ProdutosTotais;
    private javax.swing.JButton btAddqtde;
    private javax.swing.JButton btDetalhesVenda1;
    private javax.swing.JButton btRemoveQtde;
    private javax.swing.JTextField descriselected;
    private javax.swing.JTextField idselected;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner spinnerNew;
    public static javax.swing.JTable tableNotebooks;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    public static javax.swing.JTextField txtQtdAtual;
    // End of variables declaration//GEN-END:variables
}
