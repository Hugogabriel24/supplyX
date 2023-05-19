package pagesCadastro;

import br.com.tanamao.dal.LimitaNroCaracteres;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import net.proteanit.sql.DbUtils;
import notification.Notification;

public class CadREF extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private long lastPressTime = 0;

    public CadREF() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        uper();
        ListarDadosGeral();
        ListarDadosClass();
        GetGeral();
        txGeral.setDocument(new LimitaNroCaracteres(3));
        btRemove1.setEnabled(false);
        btRemove.setEnabled(false);
        btEdit1.setEnabled(false);
        btEdit.setEnabled(false);
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        txDescriGeral.setDocument(new UpperCase());
        txDescriClass.setDocument(new UpperCase());
        txGeral.setDocument(new UpperCase());
        txClass.setDocument(new UpperCase());

    }

    public void GetGeral() {
        String sql = "select ref from ref";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String grupo = rs.getString(1);
                cbGeral.addItem(grupo);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void adicionarGeral() {

        String sql = "insert into ref(ref,descri) values(?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txGeral.getText());
            pst.setString(2, txDescriGeral.getText());

            if ((txDescriGeral.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosGeral();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados adicionados com sucesso!!!");
                    noti.showNotification();
                    cbGeral.removeAllItems();
                    GetGeral();
                    btAdcGeral.setEnabled(true);
                    btRemove1.setEnabled(false);
                    btEdit1.setEnabled(false);
                    txGeral.setText(null);
                    txDescriGeral.setText(null);
                    idrefGeral.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar!</b></html>\n\n"
                    + "Provavel duplicação de refêrencia!\n" + e + "", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarGeral() {
        String sql = "update ref set ref=?, descri=? where idref=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txGeral.getText());
            pst.setString(2, txDescriGeral.getText());
            pst.setString(3, idrefGeral.getText());

            if ((txGeral.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosGeral();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados alterados com sucesso!!!");
                    noti.showNotification();
                    btAdcGeral.setEnabled(true);
                    btRemove1.setEnabled(false);
                    btEdit1.setEnabled(false);
                    txGeral.setText(null);
                    txDescriGeral.setText(null);
                    idrefGeral.setText(null);
                }
            }
        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar a referência.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void removerGeral() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover a referência?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from ref where idref=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idrefGeral.getText());
                if ((txGeral.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhuma referência selecionado!!");
                } else {

                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        ListarDadosGeral();
                        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados removidos com sucesso!!!");
                        noti.showNotification();
                        btAdcGeral.setEnabled(true);
                        btRemove1.setEnabled(false);
                        btEdit1.setEnabled(false);
                        txGeral.setText(null);
                        txDescriGeral.setText(null);
                        idrefGeral.setText(null);

                    }
                }

            } catch (Exception e) {

                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover a referência.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }
//

    public void adicionarClass() {

        String sql = "insert into refclass(refgeral,refclass,descri) values(?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, cbGeral.getSelectedItem().toString());
            pst.setString(2, txClass.getText());
            pst.setString(3, txDescriClass.getText());

            if ((txClass.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosClass();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados cadastrados com sucesso!!!");
                    noti.showNotification();
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    txClass.setText(null);
                    txDescriClass.setText(null);
                    idrefClass.setText(null);

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar!</b></html>\n\n"
                    + "Provavel duplicação de refêrencia!\n" + e + "", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarClass() {
        String sql = "update refclass set refgeral=?,refclass=?,descri=? where idrefclass=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, cbGeral.getSelectedItem().toString());
            pst.setString(2, txClass.getText());
            pst.setString(3, txDescriClass.getText());
            pst.setString(4, idrefClass.getText());

            if ((txClass.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ListarDadosClass();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados alterados com sucesso!!!");
                    noti.showNotification();
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    txClass.setText(null);
                    txDescriClass.setText(null);
                    idrefClass.setText(null);
                }
            }
        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar a referência.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void removerClass() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover a referência?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from refclass where idrefclass=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, idrefClass.getText());

                if ((idrefClass.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhuma referência selecionado!!");
                } else {

                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        ListarDadosClass();
                        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados removidos com sucesso!!!");
                        noti.showNotification();
                        btAdc.setEnabled(true);
                        btRemove.setEnabled(false);
                        btEdit.setEnabled(false);
                        txClass.setText(null);
                        txDescriClass.setText(null);
                        idrefClass.setText(null);

                    }

                }

            } catch (Exception e) {

                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover a referência.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void clickforGeral() {
        String sql = "select max(idrefclass)idrefclass from refclass";
        String ref = cbGeral.getSelectedItem().toString();

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String idref = (rs.getString(1));
                txClass.setText(ref + "-0" + idref + "/");

            }
        } catch (SQLException E) {

        }

    }

    public void ListarDadosGeral() {
        String sql = "select idref as ID,ref as REF, descri as Descricao from ref";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            TableGeral.setModel(DbUtils.resultSetToTableModel(rs));
            TableGeral.getColumnModel().getColumn(0).setMaxWidth(60);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ListarDadosClass() {
        String sql = "select idrefclass as ID,refgeral as REFGERAL,refclass as CLASS, descri as Descricao from refclass";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();
            TableClass.setModel(DbUtils.resultSetToTableModel(rs));
            TableClass.getColumnModel().getColumn(0).setMaxWidth(60);
            TableClass.getColumnModel().getColumn(1).setMaxWidth(100);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idrefGeral = new javax.swing.JTextField();
        idrefClass = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txDescriGeral = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableGeral = new javax.swing.JTable();
        btAdcGeral = new javax.swing.JButton();
        btEdit1 = new javax.swing.JButton();
        btRemove1 = new javax.swing.JButton();
        txGeral = new javax.swing.JFormattedTextField();
        btCleanGeral = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txProcurarMarca1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txDescriClass = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableClass = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        cbGeral = new javax.swing.JComboBox<>();
        btAdc = new javax.swing.JButton();
        btRemove = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        txClass = new javax.swing.JFormattedTextField();
        btSearch2 = new javax.swing.JButton();
        txProcurarMarca2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        idrefGeral.setText("jTextField1");

        idrefClass.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Referência");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        txDescriGeral.setColumns(20);
        txDescriGeral.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txDescriGeral.setRows(5);
        txDescriGeral.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txDescriGeralKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txDescriGeral);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Descrição da referência");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("Referência GERAL");

        TableGeral.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TableGeral.setModel(new javax.swing.table.DefaultTableModel(
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
        TableGeral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableGeralMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableGeral);

        btAdcGeral.setBackground(new java.awt.Color(2, 119, 222));
        btAdcGeral.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdcGeral.setForeground(new java.awt.Color(255, 255, 255));
        btAdcGeral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdcGeral.setText(" Adicionar");
        btAdcGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcGeralActionPerformed(evt);
            }
        });

        btEdit1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit1.setText(" Editar");
        btEdit1.setToolTipText("");
        btEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEdit1ActionPerformed(evt);
            }
        });

        btRemove1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemove1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemove1.setText(" Excluir");
        btRemove1.setToolTipText("");
        btRemove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemove1ActionPerformed(evt);
            }
        });

        txGeral.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txGeralActionPerformed(evt);
            }
        });

        btCleanGeral.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btCleanGeral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        btCleanGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCleanGeralActionPerformed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

        txProcurarMarca1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txGeral))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(btCleanGeral)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btEdit1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemove1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAdcGeral))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txProcurarMarca1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addComponent(txGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btAdcGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btRemove1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btCleanGeral, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txProcurarMarca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txDescriClass.setColumns(20);
        txDescriClass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txDescriClass.setRows(5);
        txDescriClass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txDescriClassKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(txDescriClass);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Descrição da referência");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Referência CLASSIFICATIVA");

        TableClass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TableClass.setModel(new javax.swing.table.DefaultTableModel(
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
        TableClass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClassMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableClass);

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Referência GERAL");

        cbGeral.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbGeral.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbGeralPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        btAdc.setBackground(new java.awt.Color(2, 119, 222));
        btAdc.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdc.setForeground(new java.awt.Color(255, 255, 255));
        btAdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdc.setText(" Adicionar");
        btAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcActionPerformed(evt);
            }
        });

        btRemove.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemove.setText(" Excluir");
        btRemove.setToolTipText("");
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText(" Editar");
        btEdit.setToolTipText("");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        txClass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        btSearch2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btSearch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        btSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch2ActionPerformed(evt);
            }
        });

        txProcurarMarca2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/iconSearch.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btSearch2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAdc))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(txClass)
                            .addComponent(cbGeral, 0, 226, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txProcurarMarca2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(15, 15, 15)
                .addComponent(txClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btSearch2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txProcurarMarca2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/simbolo-de-setas-duplas-para-a-direita-para-avancar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jLabel6)
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

    private void btAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcActionPerformed
        // TODO add your handling code here:
        adicionarClass();
    }//GEN-LAST:event_btAdcActionPerformed

    private void btRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveActionPerformed
        // TODO add your handling code here:
        removerClass();
    }//GEN-LAST:event_btRemoveActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterarClass();
    }//GEN-LAST:event_btEditActionPerformed

    private void btAdcGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcGeralActionPerformed
        // TODO add your handling code here:
        adicionarGeral();
    }//GEN-LAST:event_btAdcGeralActionPerformed

    private void btRemove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemove1ActionPerformed
        // TODO add your handling code here:
        removerGeral();
    }//GEN-LAST:event_btRemove1ActionPerformed

    private void btEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEdit1ActionPerformed
        // TODO add your handling code here:
        alterarGeral();
    }//GEN-LAST:event_btEdit1ActionPerformed

    private void cbGeralPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbGeralPopupMenuWillBecomeInvisible
//         TODO add your handling code here:
        clickforGeral();
    }//GEN-LAST:event_cbGeralPopupMenuWillBecomeInvisible

    private void TableGeralMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableGeralMouseClicked
        // TODO add your handling code here:
        int setar = TableGeral.getSelectedRow();
        String sql = "select * from ref where idref like ?";
        String id = (TableGeral.getModel().getValueAt(setar, 0).toString());

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                idrefGeral.setText(rs.getString(1));
                txGeral.setText(rs.getString(3));
                txDescriGeral.setText(rs.getString(4));
                btAdcGeral.setEnabled(false);
                btRemove1.setEnabled(true);
                btEdit1.setEnabled(true);
            }
        } catch (Exception E) {

        }
    }//GEN-LAST:event_TableGeralMouseClicked

    private void btCleanGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCleanGeralActionPerformed
        // TODO add your handling code here:
        Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
        btAdcGeral.setEnabled(true);
        btRemove1.setEnabled(false);
        btEdit1.setEnabled(false);
        txGeral.setText(null);
        txDescriGeral.setText(null);
        idrefGeral.setText(null);

    }//GEN-LAST:event_btCleanGeralActionPerformed

    private void btSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch2ActionPerformed
        // TODO add your handling code here:
        Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
        btAdc.setEnabled(true);
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
        txClass.setText(null);
        txDescriClass.setText(null);
        idrefClass.setText(null);
    }//GEN-LAST:event_btSearch2ActionPerformed

    private void TableClassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClassMouseClicked
        // TODO add your handling code here:
        int setar = TableClass.getSelectedRow();
        String sql = "select * from refclass where idrefclass like ?";
        String id = (TableClass.getModel().getValueAt(setar, 0).toString());

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                idrefClass.setText(rs.getString(1));
                cbGeral.setSelectedItem(rs.getString(2));
                txClass.setText(rs.getString(3));
                txDescriClass.setText(rs.getString(4));
                btAdc.setEnabled(false);
                btRemove.setEnabled(true);
                btEdit.setEnabled(true);
            }
        } catch (Exception E) {

        }
    }//GEN-LAST:event_TableClassMouseClicked

    private void txGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txGeralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txGeralActionPerformed

    private void txDescriGeralKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDescriGeralKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPressTime <= 300) { // Verifica o tempo entre os cliques
                adicionarGeral();
            }
            lastPressTime = currentTime;
        }
    }//GEN-LAST:event_txDescriGeralKeyPressed

    private void txDescriClassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txDescriClassKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPressTime <= 300) { // Verifica o tempo entre os cliques
                adicionarClass();
            }
            lastPressTime = currentTime;
        }
    }//GEN-LAST:event_txDescriClassKeyPressed

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
            java.util.logging.Logger.getLogger(CadREF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadREF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadREF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadREF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadREF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableClass;
    private javax.swing.JTable TableGeral;
    private javax.swing.JButton btAdc;
    private javax.swing.JButton btAdcGeral;
    public static javax.swing.JButton btCleanGeral;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btEdit1;
    private javax.swing.JButton btRemove;
    private javax.swing.JButton btRemove1;
    public static javax.swing.JButton btSearch2;
    private javax.swing.JComboBox<String> cbGeral;
    private javax.swing.JTextField idrefClass;
    private javax.swing.JTextField idrefGeral;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JFormattedTextField txClass;
    private javax.swing.JTextArea txDescriClass;
    private javax.swing.JTextArea txDescriGeral;
    private javax.swing.JFormattedTextField txGeral;
    private javax.swing.JTextField txProcurarMarca1;
    private javax.swing.JTextField txProcurarMarca2;
    // End of variables declaration//GEN-END:variables
}
