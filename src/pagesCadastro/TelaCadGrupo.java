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

public final class TelaCadGrupo extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadGrupo() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        ListarDadosGrupo();
        btRemoveGrupo.setEnabled(false);
        btRemoveSubGrupo.setEnabled(false);
        btEditSubGrupo.setEnabled(false);
        btEditGrupo.setEnabled(false);
        thisGrupo.setVisible(false);
        txSubgrupo.setEnabled(false);
        tableGp.getTableHeader().setResizingAllowed(false);
        tableGp.getTableHeader().setReorderingAllowed(false);
        tableSubG.getTableHeader().setResizingAllowed(false);
        tableSubG.getTableHeader().setReorderingAllowed(false);
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        txtGrupo.setDocument(new UpperCase());
        txtDescricaoGrupo.setDocument(new UpperCase());
        txSubgrupo.setDocument(new UpperCase());

    }

    public void adicionarGrupo() {

        String sql = "insert into grupo(nomeg,descri) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtGrupo.getText());
            pst.setString(2, txtDescricaoGrupo.getText());

            if ((txtGrupo.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosGrupo();
                    txtGrupo.setText("");
                    txtDescricaoGrupo.setText("");

                }
            }

        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar o grupo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterarGrupo() {
        String sql = "update grupo set nomeg=?,descri=? where idgrupo=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtGrupo.getText());
            pst.setString(2, txtDescricaoGrupo.getText());
            pst.setString(3, idgruposelected.getText());

            if ((idgruposelected.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosGrupo();
                    btAdcGrupo.setEnabled(true);
                    txtGrupo.setText("");
                    txtDescricaoGrupo.setText("");
                    idgruposelected.setText("");

                }
            }
        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar o grupo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void removerGrupo() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o grupo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from grupo where idgrupo=?";
            try {

                if ((idgruposelected.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhum grupo selecionado!!");
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idgruposelected.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    ListarDadosGrupo();

                }

            } catch (HeadlessException | SQLException e) {

                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover o grupo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void adicionarSubGrupo() {

        String sql = "insert into subgrupo(psubgrupo,pgrupo) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txSubgrupo.getText());
            pst.setString(2, thisGrupo.getText());

            if ((txSubgrupo.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosSubgrupo();
                    txSubgrupo.setText("");

                }
            }

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar!</b></html>\n\n"
                    + "Provavel duplicação de Subgrupo!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarSubGrupo() {
        String sql = "update subgrupo set psubgrupo=?, pgrupo=? where idsubgrupo=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txSubgrupo.getText());
            pst.setString(2, thisGrupo.getText());
            pst.setString(3, idsubgruposelected.getText());

            if ((idsubgruposelected.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosSubgrupo();
                    btAdcSubGrupo.setEnabled(true);
                    txSubgrupo.setText("");
                    thisGrupo.setText("");
                    idsubgruposelected.setText("");
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar!!");
        }
    }

    private void removerSubGrupo() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o SubGrupo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from subgrupo where idsubgrupo=?";
            try {

                if ((idsubgruposelected.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhum subgrupo selecionada!!");
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idsubgruposelected.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    ListarDadosSubgrupo();
                    btAdcSubGrupo.setEnabled(true);
                    txSubgrupo.setText("");

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    public void ListarDadosGrupo() {
        String sql = "select idgrupo as ID,nomeg as Grupo,descri as Descri from grupo order by nomeg";
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            tableGp.setModel(DbUtils.resultSetToTableModel(rs));
            tableGp.getColumnModel().getColumn(0).setMaxWidth(40);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ListarDadosSubgrupo() {
        String sql = "select idsubgrupo as ID, psubgrupo as SUBGRUPO, pgrupo as GRUPO from subgrupo where pgrupo = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, thisGrupo.getText());
            rs = pst.executeQuery();

            tableSubG.setModel(DbUtils.resultSetToTableModel(rs));
            tableSubG.getColumnModel().getColumn(0).setMaxWidth(70);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setarCamposGrupo() {
        try {
            int setar = tableGp.getSelectedRow();
            btAdcGrupo.setEnabled(false);
            btRemoveGrupo.setEnabled(true);
            btEditGrupo.setEnabled(true);

            String id = (tableGp.getModel().getValueAt(setar, 0).toString());
            String grupo = (tableGp.getModel().getValueAt(setar, 1).toString());
            String descric = (tableGp.getModel().getValueAt(setar, 2).toString());

            txtGrupo.setText(grupo);
            txtDescricaoGrupo.setText(descric);
            idgruposelected.setText(id);
            thisGrupo.setVisible(true);
            thisGrupo.setText(grupo);
            txSubgrupo.setEnabled(true);

        } catch (Exception e) {
        }

    }

    public void setarCamposSubGrupo() {
        try {
            int setar = tableSubG.getSelectedRow();
            btAdcSubGrupo.setEnabled(false);
            btRemoveSubGrupo.setEnabled(true);
            btEditSubGrupo.setEnabled(true);

            String id = (tableSubG.getModel().getValueAt(setar, 0).toString());
            String subg = (tableSubG.getModel().getValueAt(setar, 1).toString());
            String gp = (tableSubG.getModel().getValueAt(setar, 2).toString());

            txSubgrupo.setText(subg);
            thisGrupo.setText(gp);
            idsubgruposelected.setText(id);
            thisGrupo.setVisible(true);
            txSubgrupo.setEnabled(true);
            tableGp.setEnabled(false);

        } catch (Exception e) {
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idgruposelected = new javax.swing.JTextField();
        idsubgruposelected = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btAdcGrupo = new javax.swing.JButton();
        btRemoveGrupo = new javax.swing.JButton();
        btEditGrupo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtGrupo = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txSubgrupo = new javax.swing.JTextField();
        btAdcSubGrupo = new javax.swing.JButton();
        btRemoveSubGrupo = new javax.swing.JButton();
        btEditSubGrupo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        thisGrupo = new javax.swing.JLabel();
        clearGp = new javax.swing.JButton();
        clearSubG = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSubG = new javax.swing.JTable();
        searchSubG = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        searchGp = new javax.swing.JTextField();
        tableScrollButton2 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableGp = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtDescricaoGrupo = new javax.swing.JTextField();

        idgruposelected.setText("jTextField1");

        idsubgruposelected.setText("jTextField2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar grupos");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        btAdcGrupo.setBackground(new java.awt.Color(2, 119, 222));
        btAdcGrupo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdcGrupo.setForeground(new java.awt.Color(255, 255, 255));
        btAdcGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdcGrupo.setText("Adicionar");
        btAdcGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcGrupoActionPerformed(evt);
            }
        });

        btRemoveGrupo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemoveGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveGrupo.setText("Excluir");
        btRemoveGrupo.setToolTipText("");
        btRemoveGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveGrupoActionPerformed(evt);
            }
        });

        btEditGrupo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEditGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEditGrupo.setText("Editar");
        btEditGrupo.setToolTipText("");
        btEditGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditGrupoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("GRUPO");

        txtGrupo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("SUBGRUPO");

        txSubgrupo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        btAdcSubGrupo.setBackground(new java.awt.Color(2, 119, 222));
        btAdcSubGrupo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdcSubGrupo.setForeground(new java.awt.Color(255, 255, 255));
        btAdcSubGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdcSubGrupo.setText("Adicionar");
        btAdcSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcSubGrupoActionPerformed(evt);
            }
        });

        btRemoveSubGrupo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemoveSubGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveSubGrupo.setText("Excluir");
        btRemoveSubGrupo.setToolTipText("");
        btRemoveSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveSubGrupoActionPerformed(evt);
            }
        });

        btEditSubGrupo.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEditSubGrupo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEditSubGrupo.setText("Editar");
        btEditSubGrupo.setToolTipText("");
        btEditSubGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditSubGrupoActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        thisGrupo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        thisGrupo.setForeground(new java.awt.Color(102, 102, 102));
        thisGrupo.setText("SUBGRUPO");
        jPanel2.add(thisGrupo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 360, 30));

        clearGp.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        clearGp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        clearGp.setText("Limpar dados");
        clearGp.setToolTipText("Limpar campos");
        clearGp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearGpActionPerformed(evt);
            }
        });

        clearSubG.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        clearSubG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        clearSubG.setText("Limpar dados");
        clearSubG.setToolTipText("Limpar campos");
        clearSubG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSubGActionPerformed(evt);
            }
        });

        tableScrollButton1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SubGrupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        tableSubG.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableSubG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSubGMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableSubG);

        tableScrollButton1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        searchSubG.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

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
                        .addComponent(searchSubG, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchSubG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(15, 15, 15)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

        searchGp.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        tableScrollButton2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Grupos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        tableGp.setModel(new javax.swing.table.DefaultTableModel(
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
        tableGp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableGpMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableGp);

        tableScrollButton2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchGp, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(searchGp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(tableScrollButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("DESCRIÇÃO");

        txtDescricaoGrupo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(txtDescricaoGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btEditGrupo)
                                    .addGap(15, 15, 15)
                                    .addComponent(btRemoveGrupo)
                                    .addGap(15, 15, 15)
                                    .addComponent(btAdcGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(clearGp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txSubgrupo)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btEditSubGrupo)
                                        .addGap(15, 15, 15)
                                        .addComponent(btRemoveSubGrupo)
                                        .addGap(15, 15, 15)
                                        .addComponent(btAdcSubGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(clearSubG, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(15, 15, 15)
                        .addComponent(txtGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel7)
                        .addGap(15, 15, 15)
                        .addComponent(txtDescricaoGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAdcGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btRemoveGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btEditGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(clearGp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(15, 15, 15)
                                .addComponent(txSubgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btAdcSubGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btRemoveSubGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btEditSubGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(clearSubG)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowClosed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_formKeyPressed

    private void btAdcGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcGrupoActionPerformed
        // TODO add your handling code here:
        adicionarGrupo();
    }//GEN-LAST:event_btAdcGrupoActionPerformed

    private void btRemoveGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveGrupoActionPerformed
        // TODO add your handling code here:
        removerGrupo();
    }//GEN-LAST:event_btRemoveGrupoActionPerformed

    private void btEditGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditGrupoActionPerformed
        // TODO add your handling code here:
        alterarGrupo();
    }//GEN-LAST:event_btEditGrupoActionPerformed

    private void btAdcSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcSubGrupoActionPerformed
        adicionarSubGrupo();
    }//GEN-LAST:event_btAdcSubGrupoActionPerformed

    private void btRemoveSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveSubGrupoActionPerformed
        removerSubGrupo();
    }//GEN-LAST:event_btRemoveSubGrupoActionPerformed

    private void btEditSubGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditSubGrupoActionPerformed
        alterarSubGrupo();
    }//GEN-LAST:event_btEditSubGrupoActionPerformed

    private void clearGpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearGpActionPerformed
        txtGrupo.setText("");
        idgruposelected.setText("");
        txtDescricaoGrupo.setText("");
        btAdcGrupo.setEnabled(true);
        btRemoveGrupo.setEnabled(false);
        btEditGrupo.setEnabled(false);
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
    }//GEN-LAST:event_clearGpActionPerformed

    private void clearSubGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSubGActionPerformed
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
        txSubgrupo.setText("");
        idsubgruposelected.setText(null);
        btAdcSubGrupo.setEnabled(true);
        btRemoveSubGrupo.setEnabled(false);
        btEditSubGrupo.setEnabled(false);
        tableGp.setEnabled(true);
        thisGrupo.setText("");
        thisGrupo.setVisible(false);
        idsubgruposelected.setText("");
    }//GEN-LAST:event_clearSubGActionPerformed

    private void tableSubGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSubGMouseClicked
        // TODO add your handling code here:
        setarCamposSubGrupo();
    }//GEN-LAST:event_tableSubGMouseClicked

    private void tableGpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableGpMouseClicked
        // TODO add your handling code here:
        setarCamposGrupo();
        ListarDadosSubgrupo();
    }//GEN-LAST:event_tableGpMouseClicked

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
            java.util.logging.Logger.getLogger(TelaCadGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadGrupo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadGrupo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdcGrupo;
    private javax.swing.JButton btAdcSubGrupo;
    private javax.swing.JButton btEditGrupo;
    private javax.swing.JButton btEditSubGrupo;
    private javax.swing.JButton btRemoveGrupo;
    private javax.swing.JButton btRemoveSubGrupo;
    private javax.swing.JButton clearGp;
    private javax.swing.JButton clearSubG;
    private javax.swing.JTextField idgruposelected;
    private javax.swing.JTextField idsubgruposelected;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField searchGp;
    private javax.swing.JTextField searchSubG;
    private javax.swing.JTable tableGp;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton2;
    private javax.swing.JTable tableSubG;
    private javax.swing.JLabel thisGrupo;
    private javax.swing.JTextField txSubgrupo;
    private javax.swing.JTextField txtDescricaoGrupo;
    private javax.swing.JTextField txtGrupo;
    // End of variables declaration//GEN-END:variables
}
