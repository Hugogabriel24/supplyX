/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TelaRelatorios;

import br.com.tanamao.dal.ModuloConexao;
import com.raven.datechooser.DateChooser;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
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

/**
 *
 * @author user
 */
public class TelaRelatorioVenda extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private DateChooser chDate = new DateChooser();

    public TelaRelatorioVenda() {
        initComponents();
        setIcon();
        chDate.setTextField(SearchDate);
        chDate.setDateSelectionMode(DateChooser.DateSelectionMode.BETWEEN_DATE_SELECTED);
        chDate.setLabelCurrentDayVisible(false);
        chDate.setDateFormat(new SimpleDateFormat("dd-MMM-yyyy"));
        conexao = ModuloConexao.conector();
        SetarVendedor();
    }

    public void SetarVendedor() {
        String sql = "select * from funcionarios order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(2);
                cbVendedor.addItem(nome);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

//    public void setarRelatorioAgenda() {
//
//        String getVendedor = ComboVendedor.getSelectedItem().toString();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        String getDataInicio = simpleDateFormat.format(InputDatainicial.getDate());
//        String getDataFinal = simpleDateFormat.format(InputDataFinal.getDate());
//
//        int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a impressão deste relatório?", "Atenção", JOptionPane.YES_NO_OPTION);
//        if (confirma == JOptionPane.YES_OPTION) {
//            try {
//                String sql;
//                sql = "select vendas.idvenda,vendas.idcliente,vendas.cpfcli,vendas.nomecli,vendas.telcli,vendas.datacompra,vendas.garantia,vendas.vendedor,vendas.formapgm,vendas.numeroparcela,vendas.valordinheiro,vendas.valorcartao,vendas.total,sum(total) as TOTALNW from vendas where vendedor = '" + getVendedor + "' AND datacompra BETWEEN '" + getDataInicio + "' AND '" + getDataFinal + "' group by vendas.idvenda,vendas.idcliente,vendas.cpfcli,vendas.nomecli,vendas.telcli,vendas.datacompra,vendas.garantia,vendas.vendedor,vendas.formapgm,vendas.numeroparcela,vendas.valordinheiro,vendas.valorcartao,vendas.total";
//                pst = conexao.prepareStatement(sql);
//                rs = pst.executeQuery();
//                JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
//                String os = System.getProperty("os.name");
//                 if (os.startsWith("Windoe")) {
//                     
//                 }
//                JasperPrint jasperPrint = JasperFillManager.fillReport("C:/Users/user/Desktop/BACKUP HUGO/teste/RelatorioCompra.jasper", new HashMap(), relatResul);
//                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/user/Desktop/BACKUP HUGO/teste/Backrelagenda.pdf");
//                File file = new File("C:/Users/user/Desktop/BACKUP HUGO/teste/Backrelagenda.pdf");
//                try {
//                    Desktop.getDesktop().open(file);
//
//                } catch (Exception e) {
//                    JOptionPane.showConfirmDialog(null, e);
//                }
//                //file.deleteOnExit();
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, e);
//            }
//
//        }
//    }
    public void setIcon() {
        //Setar a troca de icone do aplicativo
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/icone.png")));
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
        jLabel15 = new javax.swing.JLabel();
        BTNgerar = new javax.swing.JButton();
        BTNCancel = new javax.swing.JButton();
        cbVendedor = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        SearchDate = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RELATÓRIO");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("RELATÓRIO DE VENDAS");

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

        BTNgerar.setBackground(new java.awt.Color(147, 167, 39));
        BTNgerar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        BTNgerar.setForeground(new java.awt.Color(255, 255, 255));
        BTNgerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAV_RECIBO.png"))); // NOI18N
        BTNgerar.setText("Gerar relatório em PDF");
        BTNgerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNgerarActionPerformed(evt);
            }
        });

        BTNCancel.setBackground(new java.awt.Color(147, 167, 39));
        BTNCancel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        BTNCancel.setForeground(new java.awt.Color(255, 255, 255));
        BTNCancel.setText("Cancelar");
        BTNCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNCancelActionPerformed(evt);
            }
        });

        cbVendedor.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 0));
        jLabel25.setText("VENDEDOR :");

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 0));
        jLabel26.setText("ESCOLHER PERIODO :");

        jCheckBox1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jCheckBox1.setText("habilitar filtro das vendas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 285, Short.MAX_VALUE)
                        .addComponent(BTNCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTNgerar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SearchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jCheckBox1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(SearchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTNgerar)
                    .addComponent(BTNCancel))
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
        // TODO add your handling code here:
        //setarRelatorioAgenda();
    }//GEN-LAST:event_BTNgerarActionPerformed

    private void BTNCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNCancelActionPerformed
        // TODO add your handling code here:          
        this.dispose();
    }//GEN-LAST:event_BTNCancelActionPerformed

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
            java.util.logging.Logger.getLogger(TelaRelatorioVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorioVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorioVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaRelatorioVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaRelatorioVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNCancel;
    private javax.swing.JButton BTNgerar;
    private javax.swing.JTextField SearchDate;
    public static javax.swing.JComboBox<String> cbVendedor;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
