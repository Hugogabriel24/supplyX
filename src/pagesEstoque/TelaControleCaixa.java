/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesEstoque;

import TableModels.ModelTableToPrintBox;
import TablesGetSetters.ProdutosToPrintBox;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import pages.SelectProdutosBox;
import static pages.TelaPrincipal.empresa;

public final class TelaControleCaixa extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private DefaultListModel lista = new DefaultListModel();
    ModelTableToPrintBox modelo;

    public TelaControleCaixa() {
        initComponents();
        conexao = ModuloConexao.conector();
        ListarDados();
        ListarPrateleira();
        nameBox.setText("");
        setIcon();
        setColorFromEmpresa();
        modelo = new ModelTableToPrintBox();
        tablePrintBox.setModel(modelo);

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

    public void ListarPrateleira() {
        String sql = "select prateleira from prateleiras order by prateleira";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cxPrateleira.addItem(nome);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao listar a prateleira.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    public void ListarDados() {
        String sql = "select codigocaixa as Codigo, box as Caixa from box order by box";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            tableBoxes.setModel(DbUtils.resultSetToTableModel(rs));
            tableBoxes.getColumnModel().getColumn(0).setWidth(40);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ListarItensNaCaixa() {
        String sql = "select idpartepecas as ID,descricao as Descricao,codigob as CODB,nserie as SerialN,marca as Marca,precovenda as Preco,qtde as Estoque from partepecas where caixa = ? order by descricao";
        try {
            pst = conexao.prepareStatement(sql);
            String namebox = nameBox.getText();
            pst.setString(1, namebox);
            rs = pst.executeQuery();
            tableItensBox.setModel(DbUtils.resultSetToTableModel(rs));
            tableItensBox.getColumnModel().getColumn(0).setWidth(40);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCamposTabela() {
        int setar = tableBoxes.getSelectedRow();
        String sql = "select * from box where codigocaixa like ?";
        String codigo = (tableBoxes.getModel().getValueAt(setar, 0).toString());
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigo);
            rs = pst.executeQuery();

            while (rs.next()) {
                nameBox.setText(rs.getString(3));
                cxPrateleira.setSelectedItem(rs.getString(2));
                cxTipoCaixa.setSelectedItem(rs.getString(5));
                cxDescricao.setText(rs.getString(4));
                btAddProduto.setEnabled(true);
                btRemoveProduto.setEnabled(true);
                btLimparCaixa.setEnabled(true);
                ListarItensNaCaixa();
            }
        } catch (SQLException e) {

        }
    }

    public void removeforCaixa(String id, String caixaAtt) {

        String sql = "update partepecas set caixa=? where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, caixaAtt);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Produto retirado da caixa com sucesso!");
                noti.showNotification();
                ListarItensNaCaixa();
            }

        } catch (Exception e) {

        }

    }

    public void soundClickRemove() {
        try {

            URL sd = getClass().getResource("/sounds/trash.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sd));
            c.start();

        } catch (Exception e) {

        }
    }

    public void addToPrint() {
        ProdutosToPrintBox PPT = new ProdutosToPrintBox();
        int setar = tableBoxes.getSelectedRow();

        if (setar >= 0) {
            if (tablePrintBox.getRowCount() >= 3) {
                JOptionPane.showMessageDialog(null, "Número máximo de 3 produtos pra impressão atingido!");
            } else {
                String id = (tableBoxes.getModel().getValueAt(setar, 0).toString());
                String codigo = (tableBoxes.getModel().getValueAt(setar, 1).toString());

                PPT.setID(id);
                PPT.setCaixa(codigo);

                modelo = (ModelTableToPrintBox) tablePrintBox.getModel();
                tablePrintBox.setModel(modelo);
                modelo.addProdutosPPT(PPT);
                btGerarEtiqueta.setEnabled(true);
                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "" + codigo + " adicionado a fila de impressão de etiqueta da caixa!");
                noti.showNotification();
            }

        } else {

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogPrint = new javax.swing.JDialog();
        itensPrintBox = new javax.swing.JScrollPane();
        tablePrintBox = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btGerarEtiqueta = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableItensBox = new javax.swing.JTable();
        btAddProduto = new javax.swing.JButton();
        btRemoveProduto = new javax.swing.JButton();
        btLimparCaixa = new javax.swing.JButton();
        nameBox = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tableScrollButton2 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableBoxes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        cxPrateleira = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cxTipoCaixa = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cxDescricao = new javax.swing.JTextArea();
        txProcurarbox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        dialogPrint.setUndecorated(true);
        dialogPrint.setSize(new java.awt.Dimension(373, 258));

        tablePrintBox.setModel(new javax.swing.table.DefaultTableModel(
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
        itensPrintBox.setViewportView(tablePrintBox);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel5.setText("Lista de impressão");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton5.setText("Fechar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton4.setText("Limpar lista de impressão");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton6.setText("Excluir item");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogPrintLayout = new javax.swing.GroupLayout(dialogPrint.getContentPane());
        dialogPrint.getContentPane().setLayout(dialogPrintLayout);
        dialogPrintLayout.setHorizontalGroup(
            dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(itensPrintBox, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        dialogPrintLayout.setVerticalGroup(
            dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(itensPrintBox, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1066, 567));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/descricao-do-produto.png"))); // NOI18N
        jLabel15.setText("  ITENS DA CAIXA");

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
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        btGerarEtiqueta.setBackground(new java.awt.Color(102, 102, 102));
        btGerarEtiqueta.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btGerarEtiqueta.setForeground(new java.awt.Color(255, 255, 255));
        btGerarEtiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/etiqueta-de-preco.png"))); // NOI18N
        btGerarEtiqueta.setText("Gerar etiqueta da caixa");
        btGerarEtiqueta.setEnabled(false);
        btGerarEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGerarEtiquetaActionPerformed(evt);
            }
        });

        tableItensBox.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tableItensBox);

        tableScrollButton1.add(jScrollPane2, java.awt.BorderLayout.PAGE_START);

        btAddProduto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btAddProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/descri.png"))); // NOI18N
        btAddProduto.setText("  Lista de itens");
        btAddProduto.setEnabled(false);
        btAddProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProdutoActionPerformed(evt);
            }
        });

        btRemoveProduto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btRemoveProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/caixa-aberta (1).png"))); // NOI18N
        btRemoveProduto.setText("Remover item");
        btRemoveProduto.setEnabled(false);
        btRemoveProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveProdutoActionPerformed(evt);
            }
        });

        btLimparCaixa.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btLimparCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cancelar_1.png"))); // NOI18N
        btLimparCaixa.setText("Limpar caixa");
        btLimparCaixa.setEnabled(false);
        btLimparCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparCaixaActionPerformed(evt);
            }
        });

        nameBox.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        nameBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/caixa.png"))); // NOI18N
        nameBox.setText("$box");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jLabel1.setText("Selecione a caixa >>");

        tableBoxes.setModel(new javax.swing.table.DefaultTableModel(
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
        tableBoxes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBoxesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableBoxes);

        tableScrollButton2.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da caixa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        cxPrateleira.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("PRATELEIRA / GAVETA");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("TIPO DA CAIXA");

        cxTipoCaixa.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cxTipoCaixa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CAIXA DE PAPELAO", "ORGANIZADOR", "CAIXA DE PLASTICO", "CAIXA DE MADEIRA", "EMBALAGEM DE PAPELAO OU PAPEL", "EMBALAGEM DE VIDRO", "EMBALAGEM DE ALUMINIO", "EMBALAGEM DE PLASTICO", "EMBALAGEM DE ISOPOR" }));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("DESCRIÇÃO DA CAIXA");

        cxDescricao.setColumns(20);
        cxDescricao.setRows(5);
        jScrollPane4.setViewportView(cxDescricao);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(173, 173, 173))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cxPrateleira, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cxTipoCaixa, 0, 274, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(61, 61, 61))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(15, 15, 15)
                        .addComponent(cxPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel14)
                        .addGap(15, 15, 15)
                        .addComponent(cxTipoCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        txProcurarbox.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/enviar-para-impressora (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btGerarEtiqueta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btLimparCaixa)
                        .addGap(18, 18, 18)
                        .addComponent(btRemoveProduto)
                        .addGap(18, 18, 18)
                        .addComponent(btAddProduto))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jButton1)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(nameBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                            .addComponent(txProcurarbox))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txProcurarbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAddProduto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btLimparCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btRemoveProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btGerarEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
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

    private void btGerarEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerarEtiquetaActionPerformed
        printBox print = new printBox();
        print.setVisible(true);
    }//GEN-LAST:event_btGerarEtiquetaActionPerformed

    private void btAddProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProdutoActionPerformed
        SelectProdutosBox slc = new SelectProdutosBox();
        slc.setVisible(true);
        btAddProduto.setEnabled(false);
    }//GEN-LAST:event_btAddProdutoActionPerformed

    private void btRemoveProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveProdutoActionPerformed
        // TODO add your handling code here:
        int setarNote = tableItensBox.getSelectedRow();
        if (setarNote >= 0) {
            int getrow = tableItensBox.getSelectedRow();
            String getid = (tableItensBox.getModel().getValueAt(getrow, 0).toString());
            removeforCaixa(getid, "");
        } else {
            Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Selecione um item para poder retirar da caixa!");
            noti.showNotification();
        }

    }//GEN-LAST:event_btRemoveProdutoActionPerformed

    private void btLimparCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparCaixaActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar a caixa?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            int rowcount = tableItensBox.getRowCount();
            for (int i = 0; i < rowcount; i++) {
                String id = (tableItensBox.getModel().getValueAt(i, 0).toString());
                removeforCaixa(id, "");
            }
            Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Caixa limpa com sucesso!");
            noti.showNotification();
        }
    }//GEN-LAST:event_btLimparCaixaActionPerformed

    private void tableBoxesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBoxesMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tableBoxesMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (tablePrintBox.getRowCount() == 0) {
            btGerarEtiqueta.setEnabled(false);
        }
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        modelo.limpar();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Fila de impressão limpa com sucesso!");
        noti.showNotification();
        tablePrintBox.setModel(modelo);
        btGerarEtiqueta.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (tablePrintBox.getSelectedRow() >= 0) {
            modelo.remove(tablePrintBox.getSelectedRow());
            tablePrintBox.setModel(modelo);
            if (tablePrintBox.getRowCount() == 0) {
                btGerarEtiqueta.setEnabled(false);
            }
        } else {

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:       
        dialogPrint.setSize(373, 274);
        dialogPrint.setLocation(this.getLocationOnScreen().x + this.getWidth(), this.getLocationOnScreen().y);
        dialogPrint.setVisible(true);
        addToPrint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(TelaControleCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaControleCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaControleCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaControleCaixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaControleCaixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btAddProduto;
    private javax.swing.JButton btGerarEtiqueta;
    private javax.swing.JButton btLimparCaixa;
    private javax.swing.JButton btRemoveProduto;
    private javax.swing.JTextArea cxDescricao;
    private javax.swing.JComboBox<String> cxPrateleira;
    private javax.swing.JComboBox<String> cxTipoCaixa;
    public static javax.swing.JDialog dialogPrint;
    public static javax.swing.JScrollPane itensPrintBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JLabel nameBox;
    private javax.swing.JTable tableBoxes;
    public static javax.swing.JTable tableItensBox;
    public static javax.swing.JTable tablePrintBox;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton2;
    private javax.swing.JTextField txProcurarbox;
    // End of variables declaration//GEN-END:variables
}
