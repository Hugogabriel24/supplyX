/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DetailParteEPecas;

import br.com.tanamao.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Hugo G
 */
public class PainelPP2 extends javax.swing.JPanel {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public PainelPP2() {
        initComponents();
        conexao = ModuloConexao.conector();
        //getDadosGerais();

    }

//    public void getDadosGerais() {
//
//        String getID = textidNotebook.getText();
//
//        try {
//            String sql = "select * from notebooks where idnotebooks like ?";
//            pst = conexao.prepareStatement(sql);
//            pst.setString(1, getID);
//
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                descricaoPainel.setText(rs.getString(2));
//
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        precofinal2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        precocusto2 = new javax.swing.JPasswordField();
        showprice = new javax.swing.JToggleButton();
        precomin2 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txpromoc = new javax.swing.JTextField();

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Preço de custo");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("Preço mínimo de venda");

        precofinal2.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("Preço final de venda");

        precocusto2.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        showprice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/viewPass.png"))); // NOI18N
        showprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showpriceActionPerformed(evt);
            }
        });

        precomin2.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("PROMOÇÃO");

        txpromoc.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(precomin2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(precofinal2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(precocusto2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                        .addComponent(showprice, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txpromoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(showprice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precocusto2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addGap(14, 14, 14)
                .addComponent(precomin2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(precofinal2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txpromoc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(286, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void showpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showpriceActionPerformed
        // TODO add your handling code here:
        if (showprice.isSelected()) {
            precocusto2.setEchoChar('\u0000');
            precomin2.setEchoChar('\u0000');
        } else {
            precomin2.setEchoChar('*');
            precocusto2.setEchoChar('*');
        }
    }//GEN-LAST:event_showpriceActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public static javax.swing.JPasswordField precocusto2;
    public static javax.swing.JTextField precofinal2;
    public static javax.swing.JPasswordField precomin2;
    private javax.swing.JToggleButton showprice;
    public static javax.swing.JTextField txpromoc;
    // End of variables declaration//GEN-END:variables
}
