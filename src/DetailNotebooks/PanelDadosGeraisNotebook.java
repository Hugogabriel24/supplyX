/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package DetailNotebooks;

import br.com.tanamao.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;

/**
 *
 * @author Hugo G
 */
public class PanelDadosGeraisNotebook extends javax.swing.JPanel {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public PanelDadosGeraisNotebook() {
        initComponents();
        conexao = ModuloConexao.conector();
        btnMarca.hide();

    }

    public void VerifiMarca() {
        if (MarcaDetail.getText().equals("LENOVO")) {
            btnMarca.show();
            ImageIcon icon = new ImageIcon(getClass().getResource("/src/lenovo100x.png"));
            btnMarca.setIcon(icon);
        }
        if (MarcaDetail.getText().equals("DELL")) {
            btnMarca.show();
            ImageIcon icon = new ImageIcon(getClass().getResource("/src/dell100x.png"));
            btnMarca.setIcon(icon);
        }
        if (MarcaDetail.getText().equals("ACER")) {
            btnMarca.show();
            ImageIcon icon = new ImageIcon(getClass().getResource("/src/dell100x.png"));
            btnMarca.setIcon(icon);
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

        jLabel1 = new javax.swing.JLabel();
        descricaoPainel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        GrupoPainel = new javax.swing.JTextField();
        CodeBarDetail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ModeloDetail = new javax.swing.JTextField();
        ServiceTagDetail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        PartNumberDetail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        MarcaDetail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnMarca = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        SubGrupoPainel = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        RefGeralDetail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        RefClassDetail = new javax.swing.JTextField();
        RefunicaDetail = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Descrição");

        descricaoPainel.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Grupo");

        GrupoPainel.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        CodeBarDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Código de barras");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Modelo");

        ModeloDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        ServiceTagDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Service Tag / SerialNumber");

        PartNumberDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("PartNumber (PN)");

        MarcaDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Marca");

        btnMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/lenovo100x.png"))); // NOI18N
        btnMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("SubGrupo");

        SubGrupoPainel.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("REF");

        RefGeralDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Ref Classificativa");

        RefClassDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        RefunicaDetail.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Ref Única");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(GrupoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(CodeBarDetail)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(SubGrupoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(ServiceTagDetail)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(MarcaDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(PartNumberDetail)
                                    .addComponent(jLabel8)
                                    .addComponent(RefGeralDetail)
                                    .addComponent(jLabel11)
                                    .addComponent(RefunicaDetail)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 444, Short.MAX_VALUE))
                            .addComponent(descricaoPainel))
                        .addGap(208, 208, 208))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(RefClassDetail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ModeloDetail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CodeBarDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(descricaoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GrupoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ServiceTagDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SubGrupoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PartNumberDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MarcaDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ModeloDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RefGeralDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RefClassDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RefunicaDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(btnMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcaActionPerformed
        // TODO add your handling code here:
        if (MarcaDetail.getText().equals("DELL")) {
            String getSerial = ServiceTagDetail.getText();
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.dell.com/support/home/pt-br/product-support/servicetag/" + getSerial + "/overview"));
            } catch (Exception erro) {
                System.out.println(erro);
            }
        }
        if (MarcaDetail.getText().equals("LENOVO")) {
            String getSerial = ServiceTagDetail.getText();
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://pcsupport.lenovo.com/br/pt/products/" + getSerial + ""));
            } catch (Exception erro) {
                System.out.println(erro);
            }
        }
        if (MarcaDetail.getText().equals("ACER")) {
            String getSerial = ServiceTagDetail.getText();
            String model = ModeloDetail.getText();
            try {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("https://www.acer.com/br-pt/support?search=" + getSerial + ";" + model + "&filter=global_download"));
            } catch (Exception erro) {
                System.out.println(erro);
            }
        }
        //
    }//GEN-LAST:event_btnMarcaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField CodeBarDetail;
    public static javax.swing.JTextField GrupoPainel;
    public static javax.swing.JTextField MarcaDetail;
    public static javax.swing.JTextField ModeloDetail;
    public static javax.swing.JTextField PartNumberDetail;
    public static javax.swing.JTextField RefClassDetail;
    public static javax.swing.JTextField RefGeralDetail;
    public static javax.swing.JTextField RefunicaDetail;
    public static javax.swing.JTextField ServiceTagDetail;
    public static javax.swing.JTextField SubGrupoPainel;
    public static javax.swing.JButton btnMarca;
    public static javax.swing.JTextField descricaoPainel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
