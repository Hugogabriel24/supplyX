/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import notification.Notification;
import static pages.TelaPrincipal.empresa;
import pagesSearchs.SearchUsuario;

public final class TelaCadUser extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadUser() {
        initComponents();
        setIcon();
        uper();
        conexao = ModuloConexao.conector();
        setColorFromEmpresa();

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel2.setBackground(new Color(15, 102, 122));
        } else {
            jPanel2.setBackground(new Color(147, 167, 39));
        }
    }

    public void uper() {
        txNome.setDocument(new UpperCase());
        txMail.setDocument(new UpperCase());

    }

    public void adicionar() {

        String sql = "insert into usuarios(nome,email,login,senha,perfil,status,avatar,empresa) values(?,?,?,?,?,?,?,?)";
        String status = "off";
        String Avatar1 = "avatar1";
        String Avatar2 = "avatar2";
        String Avatar3 = "avatar3";
        String Avatar4 = "avatar4";
        String Avatar5 = "avatar5";
        String Avatar6 = "avatar6";
        String Avatar7 = "avatar7";
        try {
            pst = conexao.prepareStatement(sql);
            //DADOS GERAIS------------------------------------//
            if (avatar1.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar1);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }
            if (avatar2.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar2);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }
            if (avatar3.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar3);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }
            if (avatar4.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar4);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }
            if (avatar5.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar5);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }
            if (avatar6.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar6);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }
            if (avatar7.isSelected()) {
                pst.setString(1, txNome.getText());
                pst.setString(2, txMail.getText());
                pst.setString(3, txLogin.getText());
                pst.setString(4, txPass.getText());
                pst.setString(5, cbPerfil.getSelectedItem().toString());
                pst.setString(6, status);
                pst.setString(7, Avatar7);
                pst.setString(8, cbEmpresa.getSelectedItem().toString());
            }

            if ((txLogin.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Usuário cadastrado com sucesso!");
                    noti.showNotification();
                    refresh();
                }
            }

            } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao cadastrar o usuário.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {

        try {
            String sql = "update usuarios set nome=?,email=?,login=?,senha=?,perfil=?,status=?,avatar=?,empresa=? where idusuarios=?";
            pst = conexao.prepareStatement(sql);
            if (txPass.getText().equals(txRePass.getText())) {

                if (avatar1.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar1");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }

                if (avatar2.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar2");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }
                if (avatar3.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar3");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }
                if (avatar4.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar4");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }
                if (avatar5.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar5");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }
                if (avatar6.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar6");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }
                if (avatar7.isSelected()) {
                    pst.setString(1, txNome.getText());
                    pst.setString(2, txMail.getText());
                    pst.setString(3, txLogin.getText());
                    pst.setString(4, txPass.getText());
                    pst.setString(5, cbPerfil.getSelectedItem().toString());
                    pst.setString(6, "off");
                    pst.setString(7, "avatar7");
                    pst.setString(8, cbEmpresa.getSelectedItem().toString());
                    pst.setString(9, iduserx.getText());
                }

                if ((txLogin.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    int adicionado = pst.executeUpdate();

                    if (adicionado > 0) {
                        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Usuário editado com sucesso!");
                        noti.showNotification();
                        refresh();
                    }
                }
            } else {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Senhas não conhecidem!");
                noti.showNotification();

            }
        } catch (HeadlessException | SQLException e) {

            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar os dados do usuário.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuarios where login=?";
            try {

                if ((txLogin.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhum usuário selecionado!!");
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txLogin.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Usuário removido com sucesso!");
                    noti.showNotification();
                    refresh();

                }

            } catch (HeadlessException | SQLException e) {

            }
        }
    }

    private void refresh() {

        try {
            Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados limpos com sucesso!");
            noti.showNotification();
            txNome.setText("");
            txMail.setText("");
            txLogin.setText("");
            txPass.setText("");
            txRePass.setText("");
            avatar1.setSelected(false);
            avatar2.setSelected(false);
            avatar3.setSelected(false);
            avatar4.setSelected(false);
            avatar5.setSelected(false);
            avatar6.setSelected(false);
            avatar7.setSelected(false);
            btCad1.setEnabled(true);
            btEdit1.setEnabled(false);
            btExcluir1.setEnabled(false);
        } catch (Exception e) {

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

        grupob = new javax.swing.ButtonGroup();
        iduserx = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        avatar1 = new javax.swing.JToggleButton();
        avatar2 = new javax.swing.JToggleButton();
        avatar3 = new javax.swing.JToggleButton();
        avatar4 = new javax.swing.JToggleButton();
        avatar5 = new javax.swing.JToggleButton();
        avatar6 = new javax.swing.JToggleButton();
        avatar7 = new javax.swing.JToggleButton();
        txNome = new loginAnimation.TextFIeld();
        txPass = new loginAnimation.PassField();
        ShowHide = new javax.swing.JToggleButton();
        txMail = new loginAnimation.TextFIeld();
        txLogin = new loginAnimation.TextFIeld();
        txRePass = new loginAnimation.PassField();
        btCad1 = new javax.swing.JButton();
        btSearch2 = new javax.swing.JButton();
        btEdit1 = new javax.swing.JButton();
        btSearch3 = new javax.swing.JButton();
        cbPerfil = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbEmpresa = new javax.swing.JComboBox<>();
        btExcluir1 = new javax.swing.JButton();

        iduserx.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CADASTRAR USUÁRIO");

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
                .addGap(16, 16, 16)
                .addComponent(jLabel15)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SELECIONE O AVATAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        grupob.add(avatar1);
        avatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar1.png"))); // NOI18N
        avatar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar1ActionPerformed(evt);
            }
        });

        grupob.add(avatar2);
        avatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar2.png"))); // NOI18N
        avatar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar2ActionPerformed(evt);
            }
        });

        grupob.add(avatar3);
        avatar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar3.png"))); // NOI18N
        avatar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar3ActionPerformed(evt);
            }
        });

        grupob.add(avatar4);
        avatar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar4.png"))); // NOI18N
        avatar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar4ActionPerformed(evt);
            }
        });

        grupob.add(avatar5);
        avatar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar5.png"))); // NOI18N
        avatar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar5ActionPerformed(evt);
            }
        });

        grupob.add(avatar6);
        avatar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar6.png"))); // NOI18N
        avatar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar6ActionPerformed(evt);
            }
        });

        grupob.add(avatar7);
        avatar7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar7.png"))); // NOI18N
        avatar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(avatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(avatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(avatar3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(avatar4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(avatar5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(avatar6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(avatar7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar7)
                    .addComponent(avatar6)
                    .addComponent(avatar5)
                    .addComponent(avatar4)
                    .addComponent(avatar3)
                    .addComponent(avatar2)
                    .addComponent(avatar1))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        txNome.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txNome.setLabelText("Nome completo");

        txPass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txPass.setLabelText("Senha");

        ShowHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/viewPass.png"))); // NOI18N
        ShowHide.setPreferredSize(new java.awt.Dimension(30, 30));
        ShowHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHideActionPerformed(evt);
            }
        });

        txMail.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txMail.setLabelText("E-mail");

        txLogin.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txLogin.setLabelText("Login");

        txRePass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txRePass.setLabelText("Confirme a senha");

        btCad1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionarUser.png"))); // NOI18N
        btCad1.setText("Cadastrar");
        btCad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCad1ActionPerformed(evt);
            }
        });

        btSearch2.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pesquisarUser.png"))); // NOI18N
        btSearch2.setText("Procurar");
        btSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch2ActionPerformed(evt);
            }
        });

        btEdit1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit1.setText("Editar");
        btEdit1.setEnabled(false);
        btEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEdit1ActionPerformed(evt);
            }
        });

        btSearch3.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        btSearch3.setText("Limpar");
        btSearch3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch3ActionPerformed(evt);
            }
        });

        cbPerfil.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));
        cbPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("PERFIL");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("EMRPESA :");

        cbEmpresa.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbEmpresa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tanamao", "docatec" }));

        btExcluir1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btExcluir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        btExcluir1.setText("Excluir");
        btExcluir1.setEnabled(false);
        btExcluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluir1ActionPerformed(evt);
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbEmpresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btSearch3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btSearch2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btExcluir1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btEdit1))
                            .addComponent(txMail, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbPerfil, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txRePass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txLogin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                                    .addComponent(txPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(ShowHide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCad1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(txMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(ShowHide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addComponent(txRePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(cbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btSearch3)
                        .addComponent(btSearch2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btCad1)
                        .addComponent(btEdit1)
                        .addComponent(btExcluir1)))
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

    private void avatar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_avatar1ActionPerformed

    private void avatar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_avatar2ActionPerformed

    private void avatar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_avatar3ActionPerformed

    private void avatar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar4ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_avatar4ActionPerformed

    private void avatar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar5ActionPerformed

    }//GEN-LAST:event_avatar5ActionPerformed

    private void avatar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar6ActionPerformed

    }//GEN-LAST:event_avatar6ActionPerformed

    private void avatar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar7ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_avatar7ActionPerformed

    private void ShowHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHideActionPerformed
        // TODO add your handling code here:
        if (ShowHide.isSelected()) {
            txPass.setEchoChar('\u0000');
            txRePass.setEchoChar('\u0000');
        } else {
            txPass.setEchoChar('*');
            txRePass.setEchoChar('*');

        }
    }//GEN-LAST:event_ShowHideActionPerformed

    private void btCad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCad1ActionPerformed
        String Senha = txPass.getText();
        String ReSenha = txRePass.getText();
        if (Senha == null ? ReSenha == null : Senha.equals(ReSenha)) {
            adicionar();
        } else {
            JOptionPane.showMessageDialog(null, "As senhas nao se conhecidem");
        }
    }//GEN-LAST:event_btCad1ActionPerformed

    private void btSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch2ActionPerformed
        // TODO add your handling code here:
        SearchUsuario su = new SearchUsuario();
        su.setVisible(true);
        btSearch2.setEnabled(false);
    }//GEN-LAST:event_btSearch2ActionPerformed

    private void btEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEdit1ActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEdit1ActionPerformed

    private void btExcluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluir1ActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btExcluir1ActionPerformed

    private void btSearch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch3ActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_btSearch3ActionPerformed

    private void cbPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfilActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton ShowHide;
    public static javax.swing.JToggleButton avatar1;
    public static javax.swing.JToggleButton avatar2;
    public static javax.swing.JToggleButton avatar3;
    public static javax.swing.JToggleButton avatar4;
    public static javax.swing.JToggleButton avatar5;
    public static javax.swing.JToggleButton avatar6;
    public static javax.swing.JToggleButton avatar7;
    public static javax.swing.JButton btCad1;
    public static javax.swing.JButton btEdit1;
    public static javax.swing.JButton btExcluir1;
    public static javax.swing.JButton btSearch2;
    public static javax.swing.JButton btSearch3;
    public static javax.swing.JComboBox<String> cbEmpresa;
    public static javax.swing.JComboBox<String> cbPerfil;
    private javax.swing.ButtonGroup grupob;
    public static javax.swing.JTextField iduserx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    public static loginAnimation.TextFIeld txLogin;
    public static loginAnimation.TextFIeld txMail;
    public static loginAnimation.TextFIeld txNome;
    public static loginAnimation.PassField txPass;
    public static loginAnimation.PassField txRePass;
    // End of variables declaration//GEN-END:variables
}
