package pagesOpcoes;

import br.com.tanamao.dal.ModuloConexao;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static pages.TelaPrincipal.username;


public final class myPerfil extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public myPerfil() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        colocarDados();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void colocarDados() {
        String sql = "select nome,email,login,avatar from usuarios where login like ?";
        try {
            pst = conexao.prepareStatement(sql);
            String getUser = username.getText();
            pst.setString(1, getUser);
            rs = pst.executeQuery();

            while (rs.next()) {
                String Nome = rs.getString(1);
                String Email = rs.getString(2);
                String Login = rs.getString(3);
                String Avatar = rs.getString(4);
                NomeUsuario.setText(Nome);

                if (Avatar.equals("avatar1")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar1.setSelected(true);
                    avatar1.setEnabled(true);
                    avatar2.setEnabled(false);
                    avatar3.setEnabled(false);
                    avatar4.setEnabled(false);
                    avatar5.setEnabled(false);
                    avatar6.setEnabled(false);
                    avatar7.setEnabled(false);
                    avatar2.setSelected(false);
                    avatar3.setSelected(false);
                    avatar4.setSelected(false);
                    avatar5.setSelected(false);
                    avatar6.setSelected(false);
                    avatar7.setSelected(false);

                }
                if (Avatar.equals("avatar2")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar2.setEnabled(true);
                    avatar2.setSelected(true);
                    avatar1.setEnabled(false);
                    avatar3.setEnabled(false);
                    avatar4.setEnabled(false);
                    avatar5.setEnabled(false);
                    avatar6.setEnabled(false);
                    avatar7.setEnabled(false);
                    avatar1.setSelected(false);
                    avatar3.setSelected(false);
                    avatar4.setSelected(false);
                    avatar5.setSelected(false);
                    avatar6.setSelected(false);
                    avatar7.setSelected(false);
                }
                if (Avatar.equals("avatar3")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar3.setEnabled(true);
                    avatar3.setSelected(true);
                    avatar1.setEnabled(false);
                    avatar2.setEnabled(false);
                    avatar4.setEnabled(false);
                    avatar5.setEnabled(false);
                    avatar6.setEnabled(false);
                    avatar7.setEnabled(false);
                    avatar1.setSelected(false);
                    avatar2.setSelected(false);
                    avatar4.setSelected(false);
                    avatar5.setSelected(false);
                    avatar6.setSelected(false);
                    avatar7.setSelected(false);
                }
                if (Avatar.equals("avatar4")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar4.setEnabled(true);
                    avatar4.setSelected(true);
                    avatar1.setEnabled(false);
                    avatar2.setEnabled(false);
                    avatar3.setEnabled(false);
                    avatar5.setEnabled(false);
                    avatar6.setEnabled(false);
                    avatar7.setEnabled(false);
                    avatar1.setSelected(false);
                    avatar2.setSelected(false);
                    avatar3.setSelected(false);
                    avatar5.setSelected(false);
                    avatar6.setSelected(false);
                    avatar7.setSelected(false);
                }
                if (Avatar.equals("avatar5")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar5.setEnabled(true);
                    avatar5.setSelected(true);
                    avatar1.setEnabled(false);
                    avatar2.setEnabled(false);
                    avatar3.setEnabled(false);
                    avatar4.setEnabled(false);
                    avatar6.setEnabled(false);
                    avatar7.setEnabled(false);
                    avatar1.setSelected(false);
                    avatar2.setSelected(false);
                    avatar3.setSelected(false);
                    avatar4.setSelected(false);
                    avatar6.setSelected(false);
                    avatar7.setSelected(false);
                }
                if (Avatar.equals("avatar6")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar6.setEnabled(true);
                    avatar6.setSelected(true);
                    avatar1.setEnabled(false);
                    avatar2.setEnabled(false);
                    avatar3.setEnabled(false);
                    avatar4.setEnabled(false);
                    avatar5.setEnabled(false);
                    avatar7.setEnabled(false);
                    avatar1.setSelected(false);
                    avatar2.setSelected(false);
                    avatar3.setSelected(false);
                    avatar4.setSelected(false);
                    avatar5.setSelected(false);
                    avatar7.setSelected(false);
                }
                if (Avatar.equals("avatar7")) {
                    txNome.setText(Nome);
                    txMail.setText(Email);
                    txLogin.setText(Login);
                    avatar7.setEnabled(true);
                    avatar7.setSelected(true);
                    avatar1.setEnabled(false);
                    avatar2.setEnabled(false);
                    avatar3.setEnabled(false);
                    avatar4.setEnabled(false);
                    avatar5.setEnabled(false);
                    avatar6.setEnabled(false);
                    avatar1.setSelected(false);
                    avatar2.setSelected(false);
                    avatar3.setSelected(false);
                    avatar4.setSelected(false);
                    avatar5.setSelected(false);
                    avatar6.setSelected(false);
                }
            }

        } catch (SQLException e) {
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        NomeUsuario = new javax.swing.JLabel();
        btAlter = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        txNome = new javax.swing.JTextField();
        txMail = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txLogin = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        avatar1 = new javax.swing.JToggleButton();
        avatar2 = new javax.swing.JToggleButton();
        avatar3 = new javax.swing.JToggleButton();
        avatar4 = new javax.swing.JToggleButton();
        avatar5 = new javax.swing.JToggleButton();
        avatar6 = new javax.swing.JToggleButton();
        avatar7 = new javax.swing.JToggleButton();
        btSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar1 64x.png"))); // NOI18N

        NomeUsuario.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        NomeUsuario.setForeground(new java.awt.Color(255, 255, 255));
        NomeUsuario.setText("$user");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(NomeUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(NomeUsuario)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btAlter.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAlter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/senha.png"))); // NOI18N
        btAlter.setText("Alterar senha");
        btAlter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("NOME COMPLETO");

        txNome.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txMail.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("E-MAIL");

        txLogin.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("LOGIN");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SELECIONE O AVATAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        avatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar1.png"))); // NOI18N
        avatar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar1ActionPerformed(evt);
            }
        });

        avatar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar2.png"))); // NOI18N
        avatar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar2ActionPerformed(evt);
            }
        });

        avatar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar3.png"))); // NOI18N
        avatar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar3ActionPerformed(evt);
            }
        });

        avatar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar4.png"))); // NOI18N
        avatar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar4ActionPerformed(evt);
            }
        });

        avatar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar5.png"))); // NOI18N
        avatar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar5ActionPerformed(evt);
            }
        });

        avatar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar6.png"))); // NOI18N
        avatar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatar6ActionPerformed(evt);
            }
        });

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(avatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(avatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(avatar3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(avatar4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(avatar7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(avatar6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(avatar5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar5)
                    .addComponent(avatar4)
                    .addComponent(avatar3)
                    .addComponent(avatar2)
                    .addComponent(avatar1)
                    .addComponent(avatar7)
                    .addComponent(avatar6))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btSalvar.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/salvar-instagram.png"))); // NOI18N
        btSalvar.setText("Salvar alterações");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 188, Short.MAX_VALUE))
                            .addComponent(txMail)
                            .addComponent(txNome)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(btAlter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSalvar))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(txNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAlter)
                    .addComponent(btSalvar))
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
        if (avatar1.isSelected()) {
            avatar2.setEnabled(false);
            avatar3.setEnabled(false);
            avatar4.setEnabled(false);
            avatar5.setEnabled(false);
            avatar6.setEnabled(false);
            avatar7.setEnabled(false);
            avatar2.setSelected(false);
            avatar3.setSelected(false);
            avatar4.setSelected(false);
            avatar5.setSelected(false);
            avatar6.setSelected(false);
            avatar7.setSelected(false);
        } else {
            avatar2.setEnabled(true);
            avatar3.setEnabled(true);
            avatar4.setEnabled(true);
            avatar5.setEnabled(true);
            avatar6.setEnabled(true);
            avatar7.setEnabled(true);

        }
    }//GEN-LAST:event_avatar1ActionPerformed

    private void avatar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar2ActionPerformed
        // TODO add your handling code here:
        if (avatar2.isSelected()) {
            avatar1.setEnabled(false);
            avatar3.setEnabled(false);
            avatar4.setEnabled(false);
            avatar5.setEnabled(false);
            avatar6.setEnabled(false);
            avatar7.setEnabled(false);
            avatar1.setSelected(false);
            avatar3.setSelected(false);
            avatar4.setSelected(false);
            avatar5.setSelected(false);
            avatar6.setSelected(false);
            avatar7.setSelected(false);
        } else {
            avatar1.setEnabled(true);
            avatar3.setEnabled(true);
            avatar4.setEnabled(true);
            avatar5.setEnabled(true);
            avatar6.setEnabled(true);
            avatar7.setEnabled(true);
        }
    }//GEN-LAST:event_avatar2ActionPerformed

    private void avatar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar3ActionPerformed
        // TODO add your handling code here:
        if (avatar3.isSelected()) {
            avatar1.setEnabled(false);
            avatar2.setEnabled(false);
            avatar4.setEnabled(false);
            avatar5.setEnabled(false);
            avatar6.setEnabled(false);
            avatar7.setEnabled(false);
            avatar1.setSelected(false);
            avatar2.setSelected(false);
            avatar4.setSelected(false);
            avatar5.setSelected(false);
            avatar6.setSelected(false);
            avatar7.setSelected(false);
        } else {
            avatar1.setEnabled(true);
            avatar2.setEnabled(true);
            avatar4.setEnabled(true);
            avatar5.setEnabled(true);
            avatar6.setEnabled(true);
            avatar7.setEnabled(true);
        }
    }//GEN-LAST:event_avatar3ActionPerformed

    private void avatar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar4ActionPerformed
        // TODO add your handling code here:
        if (avatar4.isSelected()) {
            avatar1.setEnabled(false);
            avatar2.setEnabled(false);
            avatar3.setEnabled(false);
            avatar5.setEnabled(false);
            avatar6.setEnabled(false);
            avatar7.setEnabled(false);
            avatar1.setSelected(false);
            avatar2.setSelected(false);
            avatar3.setSelected(false);
            avatar5.setSelected(false);
            avatar6.setSelected(false);
            avatar7.setSelected(false);
        } else {
            avatar1.setEnabled(true);
            avatar2.setEnabled(true);
            avatar3.setEnabled(true);
            avatar5.setEnabled(true);
            avatar6.setEnabled(true);
            avatar7.setEnabled(true);
        }
    }//GEN-LAST:event_avatar4ActionPerformed

    private void avatar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar5ActionPerformed
        if (avatar5.isSelected()) {
            avatar1.setEnabled(false);
            avatar2.setEnabled(false);
            avatar3.setEnabled(false);
            avatar4.setEnabled(false);
            avatar6.setEnabled(false);
            avatar7.setEnabled(false);
            avatar1.setSelected(false);
            avatar2.setSelected(false);
            avatar3.setSelected(false);
            avatar4.setSelected(false);
            avatar6.setSelected(false);
            avatar7.setSelected(false);
        } else {
            avatar1.setEnabled(true);
            avatar2.setEnabled(true);
            avatar3.setEnabled(true);
            avatar4.setEnabled(true);
            avatar6.setEnabled(true);
            avatar7.setEnabled(true);
        }
    }//GEN-LAST:event_avatar5ActionPerformed

    private void avatar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar6ActionPerformed
        if (avatar6.isSelected()) {
            avatar1.setEnabled(false);
            avatar2.setEnabled(false);
            avatar3.setEnabled(false);
            avatar5.setEnabled(false);
            avatar4.setEnabled(false);
            avatar7.setEnabled(false);
            avatar1.setSelected(false);
            avatar2.setSelected(false);
            avatar3.setSelected(false);
            avatar5.setSelected(false);
            avatar4.setSelected(false);
            avatar7.setSelected(false);
        } else {
            avatar1.setEnabled(true);
            avatar2.setEnabled(true);
            avatar3.setEnabled(true);
            avatar5.setEnabled(true);
            avatar4.setEnabled(true);
            avatar7.setEnabled(true);
        }
    }//GEN-LAST:event_avatar6ActionPerformed

    private void avatar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avatar7ActionPerformed
        // TODO add your handling code here:
        if (avatar7.isSelected()) {
            avatar1.setEnabled(false);
            avatar2.setEnabled(false);
            avatar3.setEnabled(false);
            avatar5.setEnabled(false);
            avatar4.setEnabled(false);
            avatar6.setEnabled(false);
            avatar1.setSelected(false);
            avatar2.setSelected(false);
            avatar3.setSelected(false);
            avatar5.setSelected(false);
            avatar4.setSelected(false);
            avatar6.setSelected(false);
        } else {
            avatar1.setEnabled(true);
            avatar2.setEnabled(true);
            avatar3.setEnabled(true);
            avatar5.setEnabled(true);
            avatar4.setEnabled(true);
            avatar6.setEnabled(true);
        }
    }//GEN-LAST:event_avatar7ActionPerformed

    private void btAlterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterActionPerformed
        // TODO add your handling code here:
        AlterPass altp = new AlterPass();
        altp.setVisible(true);
        btAlter.setEnabled(false);
    }//GEN-LAST:event_btAlterActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(myPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(myPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(myPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(myPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new myPerfil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NomeUsuario;
    public static javax.swing.JToggleButton avatar1;
    public static javax.swing.JToggleButton avatar2;
    public static javax.swing.JToggleButton avatar3;
    public static javax.swing.JToggleButton avatar4;
    public static javax.swing.JToggleButton avatar5;
    public static javax.swing.JToggleButton avatar6;
    public static javax.swing.JToggleButton avatar7;
    public static javax.swing.JButton btAlter;
    private javax.swing.JButton btSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JTextField txLogin;
    public static javax.swing.JTextField txMail;
    public static javax.swing.JTextField txNome;
    // End of variables declaration//GEN-END:variables
}
