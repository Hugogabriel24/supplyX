package pages;

import br.com.tanamao.dal.Config;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import notification.Notification;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public final class NewTelaLogin extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private Animator animatorLogin;

    public NewTelaLogin() {
        FlatIntelliJLaf.setup();   
        conexao = ModuloConexao.conector();
        Locale.setDefault(new Locale("pt", "BR"));
        initComponents();
        UIManager.put("Button.arc", 20);
        btLogin.setRequestFocusEnabled(true);
        InputUser.requestFocus();
        JOptionPane.setRootFrame(this);
        setIcon();
        getContentPane().setBackground(new Color(245, 245, 245));
        getLembrarYesorNo();
        TimingTarget targetLogin = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                classeBack1.setAnimate(fraction);
            }

        };
        animatorLogin = new Animator(500, targetLogin);
        animatorLogin.setResolution(0);

        if (conexao != null) {
            lblstatus.setText("Conectado");
        } else {
            lblstatus.setText("Não conectado");
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void getLembrarYesorNo() {
        String arqConf = "C://convtec/Supply-X/config.con";
        String conteudo = Config.Read(arqConf);
        String c1 = conteudo.split(";")[0];

        if (c1.equals("yes")) {
            checkLembrar.setSelected(true);
            String c2 = conteudo.split(";")[1];
            String c3 = conteudo.split(";")[2];
            InputUser.setText(c2);
            InputPass.setText(c3);

        } else {
            InputUser.setText("");
            InputPass.setText("");
        }

    }

    public void lembrarPass() {
        String arqConf = "C://convtec/Supply-X/config.con";
        String sql = "update usuarios set lembrar=? where login=?";
        String lembrar = "yes";
        String nolembrar = "no";

        try {
            pst = conexao.prepareStatement(sql);
            if (checkLembrar.isSelected()) {               
                String login = InputUser.getText();
                String senha = InputPass.getText();
                String print = lembrar + ";" + login + ";" + senha;
                Config.Write(arqConf, print);
                pst.setString(1, lembrar);
                pst.setString(2, InputUser.getText());

            } else {
                String login = "";
                String senha = "";
                String print = nolembrar + ";" + login + ";" + senha + "";
                Config.Write(arqConf, print);
                pst.setString(1, nolembrar);
                pst.setString(2, InputUser.getText());
            }

            pst.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public void setarOnline() {
        String sql = "update usuarios set status=? where login=?";
        String logado = "on";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, logado);
            pst.setString(2, InputUser.getText());
            pst.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public void logar() {

        try {

            String sql = "select * from usuarios where login=? and senha=?";

            pst = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, InputUser.getText());
            pst.setString(2, InputPass.getText());
            rs = pst.executeQuery();

            if (rs.first()) {
                String perfil = rs.getString(6);
                String avatar = rs.getString(8);

                ImageIcon avatar1 = new ImageIcon(getClass().getResource("/src/avatar1.png"));
                ImageIcon avatar2 = new ImageIcon(getClass().getResource("/src/avatar2.png"));
                ImageIcon avatar3 = new ImageIcon(getClass().getResource("/src/avatar3.png"));
                ImageIcon avatar4 = new ImageIcon(getClass().getResource("/src/avatar4.png"));
                ImageIcon avatar5 = new ImageIcon(getClass().getResource("/src/avatar5.png"));
                ImageIcon avatar6 = new ImageIcon(getClass().getResource("/src/avatar6.png"));
                ImageIcon avatar7 = new ImageIcon(getClass().getResource("/src/avatar7.png"));
                    if (perfil.equals("admin")) {

                        if (avatar.equals("avatar1")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));
                            TelaPrincipal.avatar.setIcon(avatar1);

                            this.dispose();
                        }
                        if (avatar.equals("avatar2")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar2);

                            this.dispose();
                        }
                        if (avatar.equals("avatar3")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar3);

                            this.dispose();
                        }
                        if (avatar.equals("avatar4")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar4);

                            this.dispose();
                        }
                        if (avatar.equals("avatar5")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar5);

                            this.dispose();

                        }
                        if (avatar.equals("avatar6")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.avatar.setIcon(avatar6);
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            this.dispose();
                        }
                        if (avatar.equals("avatar7")) {
                            animatorLogin.start();
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            lembrarPass();
                            setarOnline();
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar7);

                            this.dispose();
                        }

                    } else {

                        if (avatar.equals("avatar1")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar1);
                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }
                        if (avatar.equals("avatar2")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.avatar.setIcon(avatar2);
                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }
                        if (avatar.equals("avatar3")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar3);
                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }
                        if (avatar.equals("avatar4")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar4);
                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }
                        if (avatar.equals("avatar5")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.avatar.setIcon(avatar5);
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }
                        if (avatar.equals("avatar6")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar6);
                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }
                        if (avatar.equals("avatar7")) {
                            TelaPrincipal principal = new TelaPrincipal();
                            principal.setVisible(true);
                            setarOnline();
                            TelaPrincipal.MenuFunc.setEnabled(false);
                            TelaPrincipal.lblUsuario.setText(rs.getString(2));
                            TelaPrincipal.username.setText(rs.getString(4));
                            TelaPrincipal.perfil.setText(rs.getString(6));
                            TelaPrincipal.empresa.setText(rs.getString(9));

                            TelaPrincipal.avatar.setIcon(avatar7);
                            TelaPrincipal.MenuUser.setEnabled(false);
                            TelaPrincipal.MenuForn.setEnabled(false);
                            TelaPrincipal.MenuRel.setEnabled(false);
                            TelaPrincipal.log.setEnabled(false);
                            TelaPrincipal.statusUser.setEnabled(false);
                            TelaPrincipal.menuAdm.setEnabled(false);

                            this.dispose();
                        }

                    }


            } else {
                InputPass.setHelperText("Usuário e/ou senha inválido(s).");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passField1 = new loginAnimation.PassField();
        classeBack1 = new loginAnimation.classeBack();
        painelLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btLogin = new loginAnimation.Button();
        InputPass = new loginAnimation.PassField();
        button2 = new loginAnimation.Button();
        checkLembrar = new javax.swing.JCheckBox();
        ShowHide = new javax.swing.JToggleButton();
        InputUser = new loginAnimation.TextFIeld();
        lblstatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        classeBack1.setLayout(new java.awt.CardLayout());

        painelLogin.setBackground(new java.awt.Color(245, 245, 245));
        painelLogin.setEnabled(false);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/conv150x.png"))); // NOI18N

        btLogin.setBackground(new java.awt.Color(24, 49, 139));
        btLogin.setForeground(new java.awt.Color(255, 255, 255));
        btLogin.setText("Login");
        btLogin.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });

        InputPass.setBackground(new java.awt.Color(245, 245, 245));
        InputPass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        InputPass.setLabelText("Senha");
        InputPass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InputPassMouseClicked(evt);
            }
        });
        InputPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InputPassKeyPressed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(255, 51, 51));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Sair");
        button2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        checkLembrar.setBackground(new java.awt.Color(245, 245, 245));
        checkLembrar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        checkLembrar.setText("Lembrar login");

        ShowHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/viewPass.png"))); // NOI18N
        ShowHide.setContentAreaFilled(false);
        ShowHide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHideActionPerformed(evt);
            }
        });

        InputUser.setBackground(new java.awt.Color(245, 245, 245));
        InputUser.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        InputUser.setLabelText("Usuário");
        InputUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InputUserKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(checkLembrar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137)
                        .addComponent(ShowHide, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(InputPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InputUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(InputUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(ShowHide, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkLembrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );

        lblstatus.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblstatus.setText("conection");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/atualizar-banco-de-dados.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelLoginLayout = new javax.swing.GroupLayout(painelLogin);
        painelLogin.setLayout(painelLoginLayout);
        painelLoginLayout.setHorizontalGroup(
            painelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLoginLayout.createSequentialGroup()
                .addGroup(painelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelLoginLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblstatus))
                    .addGroup(painelLoginLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        painelLoginLayout.setVerticalGroup(
            painelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblstatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        classeBack1.add(painelLogin, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(classeBack1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(classeBack1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_formKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (conexao != null) {
            Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Banco de dados conectado!");
            noti.showNotification();
        } else {
            Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Tentando reconectar ao banco...");
            noti.showNotification();
            conexao = ModuloConexao.conector();
            if (conexao != null) {
                Notification noty = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Conexão ao banco restabelecida.");
                noty.showNotification();
                lblstatus.setText("Conectado");
            } else {
                Notification noty = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Falha na conexão do banco, tente novamente..");
                noty.showNotification();

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void InputUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputUserKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            InputPass.requestFocus();
        }
    }//GEN-LAST:event_InputUserKeyPressed

    private void ShowHideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHideActionPerformed
        if (ShowHide.isSelected()) {
            InputPass.setEchoChar('\u0000');
        } else {
            InputPass.setEchoChar('*');
        }
    }//GEN-LAST:event_ShowHideActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_button2ActionPerformed

    private void InputPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputPassKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            logar();
        }
    }//GEN-LAST:event_InputPassKeyPressed

    private void InputPassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InputPassMouseClicked
        // TODO add your handling code here:
        InputPass.setHelperText(null);
    }//GEN-LAST:event_InputPassMouseClicked

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        // TODO add your handling code here:

        String user = InputUser.getText().trim();
        String pass = String.valueOf(InputPass.getPassword());
        if (user.equals("") || pass.equals("")) {
            InputPass.setHelperText("Campo de usuário ou senha vazio!");
            InputUser.grabFocus();
        } else {
            InputPass.setHelperText(null);
            logar();
        }
    }//GEN-LAST:event_btLoginActionPerformed

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
            java.util.logging.Logger.getLogger(NewTelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewTelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewTelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewTelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewTelaLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private loginAnimation.PassField InputPass;
    private loginAnimation.TextFIeld InputUser;
    private javax.swing.JToggleButton ShowHide;
    private loginAnimation.Button btLogin;
    private loginAnimation.Button button2;
    private javax.swing.JCheckBox checkLembrar;
    private loginAnimation.classeBack classeBack1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblstatus;
    private javax.swing.JPanel painelLogin;
    private loginAnimation.PassField passField1;
    // End of variables declaration//GEN-END:variables
}
