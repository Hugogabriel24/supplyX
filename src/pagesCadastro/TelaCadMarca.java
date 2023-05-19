/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import notification.Notification;

/**
 *
 * @author hugogabriel
 */
public final class TelaCadMarca extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadMarca() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        ListarDados();
        txModelo.setEnabled(false);
        thisMarca.setVisible(false);
        btEditMarca.setEnabled(false);
        btRemoveMarca.setEnabled(false);
        btRemoveModel.setEnabled(false);
        btEditModel.setEnabled(false);
        tableMarcas.getTableHeader().setResizingAllowed(false);
        tableMarcas.getTableHeader().setReorderingAllowed(false);

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        txtMarca.setDocument(new UpperCase());
        txModelo.setDocument(new UpperCase());

    }

    public void adicionarModel() {

        String sql = "insert into modelos(model,marcan) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txModelo.getText());
            pst.setString(2, thisMarca.getText());

            if ((txModelo.getText().isEmpty() || thisMarca.getText() == null)) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Modelo adicionado com sucesso!!!");
                    noti.showNotification();
                    txModelo.setText("");
                    btRemoveModel.setEnabled(false);
                    btEditModel.setEnabled(false);
                    ListarModels();

                }
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar!</b></html>\n\n"
                    + "Provável duplicação de modelo!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void adicionarMarca() {

        String sql = "insert into marca(marcanome) values(?)";
        
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtMarca.getText());
            int adicionado = pst.executeUpdate();

            if ((txtMarca.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {

                if (adicionado > 0) {
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Marca adicionada com sucesso!!!");
                    noti.showNotification();
                    txtMarca.setText(null);

                }
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar!</b></html>\n\n"
                    + "Provavel duplicação de marcas!\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        String sql = "update marca set marcanome=? where idmarca=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtMarca.getText());
            pst.setString(2, idMarca.getText());

            if ((idMarca.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhuma marca selecionada!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Marca editada com sucesso!!!");
                    noti.showNotification();
                    btAdcMarca.setEnabled(true);
                    btRemoveMarca.setEnabled(false);
                    btEditMarca.setEnabled(false);
                    txtMarca.setText("");

                }
            }
        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao editar a marca.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover a marca?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from marca where idmarca=?";
            try {

                if ((idMarca.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhuma marca selecionada!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idMarca.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Marca removida com sucesso!!!");
                    noti.showNotification();
                    btAdcMarca.setEnabled(true);
                    btRemoveMarca.setEnabled(false);
                    btEditMarca.setEnabled(false);
                    txtMarca.setText("");

                }

            } catch (HeadlessException | SQLException e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover a marca.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void ListarDados() {
        String sql = "select idmarca as ID,marcanome as Marca from marca order by marcanome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            tableMarcas.setModel(DbUtils.resultSetToTableModel(rs));
            tableMarcas.getColumnModel().getColumn(0).setMaxWidth(70);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ListarModels() {
        String sql = "select idmodelos as ID,model as Modelo,marcan as Marca from modelos where marcan = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, thisMarca.getText());
            rs = pst.executeQuery();

            tableModelo.setModel(DbUtils.resultSetToTableModel(rs));
            tableModelo.getColumnModel().getColumn(0).setMaxWidth(70);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCamposMarca() {
        try {
            int setar = tableMarcas.getSelectedRow();
            btAdcMarca.setEnabled(false);
            btRemoveMarca.setEnabled(true);
            btEditMarca.setEnabled(true);

            String id = (tableMarcas.getModel().getValueAt(setar, 0).toString());
            String marca = (tableMarcas.getModel().getValueAt(setar, 1).toString());

            txtMarca.setText(marca);
            idMarca.setText(id);
            thisMarca.setVisible(true);
            thisMarca.setText(marca);
            txModelo.setEnabled(true);
        } catch (Exception e) {
        }

    }

    private void alterarModels() {
        String sql = "update modelos set model=?, marcan=? where idmodelos=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txModelo.getText());
            pst.setString(2, thisMarca.getText());
            pst.setString(3, idmodelo.getText());

            if ((idmodelo.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum modelo selecionado!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Modelo removido com sucesso!!!");
                    noti.showNotification();
                    btAdcMarca.setEnabled(false);
                    btRemoveMarca.setEnabled(true);
                    btEditMarca.setEnabled(true);
                    txModelo.setText("");
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar!!");
        }
    }

    private void removerModels() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o modelo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from modelos where idmodelos=?";
            try {

                if ((idmodelo.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhuma marca selecionada!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idmodelo.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Modelo removido com sucesso!!!");
                    noti.showNotification();
                    btAdcMarca.setEnabled(false);
                    btRemoveMarca.setEnabled(true);
                    btEditMarca.setEnabled(true);
                    txModelo.setText("");

                }

            } catch (SQLException e) {

                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    public void setarCamposModels() {
        try {
            int setar = tableModelo.getSelectedRow();
            btAdcModel.setEnabled(false);
            btRemoveModel.setEnabled(true);
            btEditModel.setEnabled(true);
            tableMarcas.setEnabled(false);

            String id = (tableModelo.getModel().getValueAt(setar, 0).toString());
            String modelo = (tableModelo.getModel().getValueAt(setar, 1).toString());
            String marca = (tableModelo.getModel().getValueAt(setar, 2).toString());

            idmodelo.setText(id);
            thisMarca.setText(marca);
            txModelo.setText(modelo);
        } catch (Exception e) {
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idMarca = new javax.swing.JTextField();
        idmodelo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btAdcMarca = new javax.swing.JButton();
        btRemoveMarca = new javax.swing.JButton();
        btEditMarca = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txModelo = new javax.swing.JTextField();
        btAdcModel = new javax.swing.JButton();
        btRemoveModel = new javax.swing.JButton();
        btEditModel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        thisMarca = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableModelo = new javax.swing.JTable();
        txProcurarMarca1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txProcurarMarca = new javax.swing.JTextField();
        tableScrollButton2 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableMarcas = new javax.swing.JTable();

        idMarca.setText("jTextField1");

        idmodelo.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar marca / modelo");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        btAdcMarca.setBackground(new java.awt.Color(2, 119, 222));
        btAdcMarca.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdcMarca.setForeground(new java.awt.Color(255, 255, 255));
        btAdcMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdcMarca.setText("Adicionar");
        btAdcMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcMarcaActionPerformed(evt);
            }
        });

        btRemoveMarca.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemoveMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveMarca.setText("Excluir");
        btRemoveMarca.setToolTipText("");
        btRemoveMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveMarcaActionPerformed(evt);
            }
        });

        btEditMarca.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEditMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEditMarca.setText("Editar");
        btEditMarca.setToolTipText("");
        btEditMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditMarcaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("MARCA");

        txtMarca.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("MODELO");

        txModelo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        btAdcModel.setBackground(new java.awt.Color(2, 119, 222));
        btAdcModel.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdcModel.setForeground(new java.awt.Color(255, 255, 255));
        btAdcModel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdcModel.setText("Adicionar");
        btAdcModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcModelActionPerformed(evt);
            }
        });

        btRemoveModel.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemoveModel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveModel.setText("Excluir");
        btRemoveModel.setToolTipText("");
        btRemoveModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveModelActionPerformed(evt);
            }
        });

        btEditModel.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEditModel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEditModel.setText("Editar");
        btEditModel.setToolTipText("");
        btEditModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditModelActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        thisMarca.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        thisMarca.setForeground(new java.awt.Color(102, 102, 102));
        thisMarca.setText("MODELO");
        jPanel2.add(thisMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 360, 30));

        jButton4.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton4.setText("Limpar dados");
        jButton4.setToolTipText("Limpar campos");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton5.setText("Limpar dados");
        jButton5.setToolTipText("Limpar campos");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tableScrollButton1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Modelos da marca", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        tableModelo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableModeloMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableModelo);

        tableScrollButton1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        txProcurarMarca1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 15, Short.MAX_VALUE)
                        .addComponent(txProcurarMarca1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txProcurarMarca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(15, 15, 15)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

        txProcurarMarca.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        tableScrollButton2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Marcas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        tableMarcas.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMarcas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMarcasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableMarcas);

        tableScrollButton2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txProcurarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txProcurarMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btEditMarca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btRemoveMarca)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btAdcMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txModelo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btEditModel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btRemoveModel)
                                        .addGap(18, 18, 18)
                                        .addComponent(btAdcModel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdcMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btRemoveMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btEditMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(txModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdcModel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btRemoveModel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btEditModel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(jButton5)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void tableMarcasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMarcasMouseClicked
        // TODO add your handling code here:
        setarCamposMarca();
        ListarModels();
    }//GEN-LAST:event_tableMarcasMouseClicked

    private void btEditMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditMarcaActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditMarcaActionPerformed

    private void btRemoveMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveMarcaActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btRemoveMarcaActionPerformed

    private void btAdcMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcMarcaActionPerformed
        // TODO add your handling code here:
        adicionarMarca();
    }//GEN-LAST:event_btAdcMarcaActionPerformed

    private void btAdcModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcModelActionPerformed
        // TODO add your handling code here:
        adicionarModel();
    }//GEN-LAST:event_btAdcModelActionPerformed

    private void btRemoveModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveModelActionPerformed
        // TODO add your handling code here:
        removerModels();
    }//GEN-LAST:event_btRemoveModelActionPerformed

    private void btEditModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditModelActionPerformed
        // TODO add your handling code here:
        alterarModels();
    }//GEN-LAST:event_btEditModelActionPerformed

    private void tableModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableModeloMouseClicked
        // TODO add your handling code here:
        setarCamposModels();
    }//GEN-LAST:event_tableModeloMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
        txtMarca.setText("");
        idMarca.setText(null);
        btAdcMarca.setEnabled(true);
        btRemoveMarca.setEnabled(false);
        btEditMarca.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
        idmodelo.setText(null);
        txModelo.setText("");
        btAdcModel.setEnabled(true);
        btRemoveModel.setEnabled(false);
        btEditModel.setEnabled(false);
        tableMarcas.setEnabled(true);
        thisMarca.setText("");
        thisMarca.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadMarca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdcMarca;
    private javax.swing.JButton btAdcModel;
    private javax.swing.JButton btEditMarca;
    private javax.swing.JButton btEditModel;
    private javax.swing.JButton btRemoveMarca;
    private javax.swing.JButton btRemoveModel;
    private javax.swing.JTextField idMarca;
    private javax.swing.JTextField idmodelo;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tableMarcas;
    private javax.swing.JTable tableModelo;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton2;
    private javax.swing.JLabel thisMarca;
    private javax.swing.JTextField txModelo;
    private javax.swing.JTextField txProcurarMarca;
    private javax.swing.JTextField txProcurarMarca1;
    private javax.swing.JTextField txtMarca;
    // End of variables declaration//GEN-END:variables
}
