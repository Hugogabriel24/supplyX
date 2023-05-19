/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelaRelatorios;

import br.com.tanamao.dal.ModuloConexao;
import com.raven.datechooser.DateBetween;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.listener.DateChooserAction;
import com.raven.datechooser.listener.DateChooserAdapter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import static pages.TelaPrincipal.empresa;

/**
 *
 * @author user
 */
public class TelaRelatorioClientes extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private DateChooser chDate = new DateChooser();

    public TelaRelatorioClientes() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        setColorFromEmpresa();
        chDate.setTextField(SearchDateCli);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setDateFormat(new SimpleDateFormat("dd-MMM-yyyy"));
        chDate.addActionDateChooserListener(new DateChooserAdapter() {
            @Override
            public void dateBetweenChanged(DateBetween date, DateChooserAction action) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String DateFrom = df.format(date.getFromDate());
                String toDate = df.format(date.getToDate());
                loadDate("select * from clientes where datacad BETWEEN '" + DateFrom + "' and '" + toDate + "' AND pessoa like ?");
            }

        });

    }

    public void loadDate(String sql) {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração deste recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                if (CheckPF.isSelected()) {
//                    String sql;
//                    sql = "select * from clientes where pessoa=?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, "PF");
                    rs = pst.executeQuery();
                    InputStream report = getClass().getResourceAsStream("/recibos/Rel_Clientes_docatec.jasper");

                    String os = System.getProperty("os.name");
                    if (os.startsWith("Windows")) {

                        JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");
                        File file = new File("C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");

                        try {
                            Desktop.getDesktop().open(file);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }

                } else {
//                    String sql;
//                    sql = "select * from clientes where pessoa=?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, "PJ");
                    rs = pst.executeQuery();
                    InputStream report = getClass().getResourceAsStream("/recibos/Rel_Clientes_docatec.jasper");

                    String os = System.getProperty("os.name");
                    if (os.startsWith("Windows")) {

                        JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");
                        File file = new File("C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");

                        try {
                            Desktop.getDesktop().open(file);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        BTNgerar = new javax.swing.JButton();
        BTNCancel = new javax.swing.JButton();
        checkHabilit = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        SearchDateCli = new javax.swing.JTextField();
        BTNgerar1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        CheckPF = new javax.swing.JCheckBox();
        CheckPJ = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RELATÓRIO");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("RELATÓRIO DE CLIENTES");

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
                .addComponent(jLabel15)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        BTNgerar.setBackground(new java.awt.Color(255, 51, 51));
        BTNgerar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        BTNgerar.setForeground(new java.awt.Color(255, 255, 255));
        BTNgerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAV_RECIBO.png"))); // NOI18N
        BTNgerar.setText("Gerar relatório em PDF");
        BTNgerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNgerarActionPerformed(evt);
            }
        });

        BTNCancel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        BTNCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        BTNCancel.setText("  Fechar");
        BTNCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNCancelActionPerformed(evt);
            }
        });

        checkHabilit.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        checkHabilit.setText("habilitar filtro por data");
        checkHabilit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkHabilitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setText("Filtro por data de cadastro:");

        SearchDateCli.setBackground(new java.awt.Color(153, 153, 153));
        SearchDateCli.setEnabled(false);

        BTNgerar1.setBackground(new java.awt.Color(147, 167, 39));
        BTNgerar1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        BTNgerar1.setForeground(new java.awt.Color(255, 255, 255));
        BTNgerar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAV_RECIBO.png"))); // NOI18N
        BTNgerar1.setText("Gerar relatório EXCEL");
        BTNgerar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNgerar1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel2.setText("Filtro por tipo de pessoa :");

        grupo1.add(CheckPF);
        CheckPF.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CheckPF.setText("PESSOA FÍSICA");
        CheckPF.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CheckPFStateChanged(evt);
            }
        });
        CheckPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckPFActionPerformed(evt);
            }
        });

        grupo1.add(CheckPJ);
        CheckPJ.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CheckPJ.setText("PESSOA JURÍDICA");
        CheckPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckPJActionPerformed(evt);
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
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(CheckPF)
                                .addGap(17, 17, 17)
                                .addComponent(CheckPJ))
                            .addComponent(SearchDateCli))
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkHabilit)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(BTNCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BTNgerar1)))
                        .addGap(12, 12, 12)
                        .addComponent(BTNgerar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkHabilit)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(CheckPF)
                    .addComponent(CheckPJ))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchDateCli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTNgerar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTNCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTNgerar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void BTNgerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNgerarActionPerformed
        if (checkHabilit.isSelected()) {

        }else{
            int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração deste recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            try {
                if (CheckPF.isSelected()) {
                    String sql;
                    sql = "select * from clientes where pessoa=?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, "PF");
                    rs = pst.executeQuery();
                    InputStream report = getClass().getResourceAsStream("/recibos/Rel_Clientes_docatec.jasper");

                    String os = System.getProperty("os.name");
                    if (os.startsWith("Windows")) {

                        JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");
                        File file = new File("C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");

                        try {
                            Desktop.getDesktop().open(file);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }

                } else {
                    String sql;
                    sql = "select * from clientes where pessoa=?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, "PJ");
                    rs = pst.executeQuery();
                    InputStream report = getClass().getResourceAsStream("/recibos/Rel_Clientes_docatec.jasper");

                    String os = System.getProperty("os.name");
                    if (os.startsWith("Windows")) {

                        JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");
                        File file = new File("C://convtec/Supply-x/trash/relatoriocliente#CLTrel.pdf");

                        try {
                            Desktop.getDesktop().open(file);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        }

    }//GEN-LAST:event_BTNgerarActionPerformed

    private void BTNCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNCancelActionPerformed
        // TODO add your handling code here:          
        this.dispose();
    }//GEN-LAST:event_BTNCancelActionPerformed

    private void checkHabilitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkHabilitActionPerformed
        // TODO add your handling code here:
        if (checkHabilit.isSelected()) {
            SearchDateCli.setEnabled(true);
            SearchDateCli.setForeground(Color.GRAY);
        } else {
            SearchDateCli.setEnabled(false);
        }
    }//GEN-LAST:event_checkHabilitActionPerformed

    private void BTNgerar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNgerar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BTNgerar1ActionPerformed

    private void CheckPFStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CheckPFStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckPFStateChanged

    private void CheckPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckPFActionPerformed


    }//GEN-LAST:event_CheckPFActionPerformed

    private void CheckPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckPJActionPerformed

    }//GEN-LAST:event_CheckPJActionPerformed

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
            java.util.logging.Logger.getLogger(TelaRelatorioClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorioClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorioClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorioClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaRelatorioClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNCancel;
    private javax.swing.JButton BTNgerar;
    private javax.swing.JButton BTNgerar1;
    public static javax.swing.JCheckBox CheckPF;
    public static javax.swing.JCheckBox CheckPJ;
    private javax.swing.JTextField SearchDateCli;
    private javax.swing.JCheckBox checkHabilit;
    private javax.swing.ButtonGroup grupo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
