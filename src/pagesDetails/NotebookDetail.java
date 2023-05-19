/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesDetails;

import DetailNotebooks.EventMenuSelected;
import DetailNotebooks.Item;
import DetailNotebooks.PanelArmazenamentoNotebook;
import static DetailNotebooks.PanelArmazenamentoNotebook.cap1Detail;
import static DetailNotebooks.PanelArmazenamentoNotebook.cap2Detail;
import static DetailNotebooks.PanelArmazenamentoNotebook.duploDetail;
import static DetailNotebooks.PanelArmazenamentoNotebook.nserieDetail;
import static DetailNotebooks.PanelArmazenamentoNotebook.tipo1Detail;
import static DetailNotebooks.PanelArmazenamentoNotebook.tipo2Detail;
import DetailNotebooks.PanelDadosGeraisNotebook;
import static DetailNotebooks.PanelDadosGeraisNotebook.btnMarca;
import static DetailNotebooks.PanelDadosGeraisNotebook.descricaoPainel;
import static DetailNotebooks.PanelDadosGeraisNotebook.RefGeralDetail;
import DetailNotebooks.PanelMemoriaNotebook;
import DetailNotebooks.PanelProcessNotebook;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static DetailNotebooks.PanelDadosGeraisNotebook.GrupoPainel;
import static DetailNotebooks.PanelDadosGeraisNotebook.MarcaDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.ModeloDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.CodeBarDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.ServiceTagDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.PartNumberDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.RefunicaDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.RefClassDetail;
import static DetailNotebooks.PanelDadosGeraisNotebook.SubGrupoPainel;
import static DetailNotebooks.PanelMemoriaNotebook.CapacidadeMaxDetail;
import static DetailNotebooks.PanelMemoriaNotebook.MemoriaRamDetail;
import static DetailNotebooks.PanelMemoriaNotebook.SlotsDetail;
import static DetailNotebooks.PanelMemoriaNotebook.TecnologiaRamDetail;
import static DetailNotebooks.PanelMemoriaNotebook.VelocidadeRamDetail;
import static DetailNotebooks.PanelMemoriaNotebook.seriaisRamDetail;
import DetailNotebooks.PanelPlacaVNotebook;
import static DetailNotebooks.PanelPlacaVNotebook.modeloOnboardNotebook;
import static DetailNotebooks.PanelPlacaVNotebook.tipoMemoriaOnboard;
import static DetailNotebooks.PanelPlacaVNotebook.memoriaOnboard;
import static DetailNotebooks.PanelPlacaVNotebook.OffboardNotebook;
import static DetailNotebooks.PanelPlacaVNotebook.modeloOffboardNotebook;
import static DetailNotebooks.PanelPlacaVNotebook.TipomemoriaOffboardNotebook;
import static DetailNotebooks.PanelPlacaVNotebook.memoriaOffboardNotebook;
import static DetailNotebooks.PanelPlacaVNotebook.placaOnboardNotebook;
import static DetailNotebooks.PanelProcessNotebook.ModeloProcessadorDetail;
import static DetailNotebooks.PanelProcessNotebook.NucleosDetail;
import static DetailNotebooks.PanelProcessNotebook.VelocidadeProcessadorDetail;
import static DetailNotebooks.PanelProcessNotebook.ProcessadorDetail;
import DetailNotebooks.PanelTelaNotebook;

import java.sql.SQLException;
import static pages.TelaPrincipal.empresa;

/**
 *
 * @author Hugo G
 */
public class NotebookDetail extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private EventMenuSelected event;

    public void AddEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        myList.AddEventMenuSelected(event);

    }

    public NotebookDetail() {
        initComponents();
        conexao = ModuloConexao.conector();
        setColorFromEmpresa();
        setIcon();

        painelSlide.init(new PanelDadosGeraisNotebook(), new PanelMemoriaNotebook(), new PanelProcessNotebook(), new PanelPlacaVNotebook(), new PanelArmazenamentoNotebook(), new PanelTelaNotebook());
        painelSlide.setAnimate(8);
        myList.setForeground(new Color(242, 242, 242));
        myList.addItem(new Item("Dados gerais", new ImageIcon(getClass().getResource("/src/DadosNotebook.png"))));
        myList.addItem(new Item("Memória", new ImageIcon(getClass().getResource("/src/ram.png"))));
        myList.addItem(new Item("Processador", new ImageIcon(getClass().getResource("/src/processador.png"))));
        myList.addItem(new Item("Placa de vídeo", new ImageIcon(getClass().getResource("/src/placadevideo.png"))));
        myList.addItem(new Item("Armazenamento", new ImageIcon(getClass().getResource("/src/armazenamento2.png"))));
        myList.addItem(new Item("Tela", new ImageIcon(getClass().getResource("/src/computador.png"))));
        myList.addItem(new Item("Bateria / Carregador", new ImageIcon(getClass().getResource("/src/nivel-de-bateria.png"))));
        myList.addItem(new Item("Outros", new ImageIcon(getClass().getResource("/src/mais_1.png"))));
        myList.addItem(new Item("Valores", new ImageIcon(getClass().getResource("/src/dolares.png"))));
        myList.addItem(new Item("Estoque", new ImageIcon(getClass().getResource("/src/estoque-pronto.png"))));
        myList.addItem(new Item("Mais informações", new ImageIcon(getClass().getResource("/src/em-formacao.png"))));
        myList.addItem(new Item("Anuncios", new ImageIcon(getClass().getResource("/src/anuncio32x.png"))));
        myList.addItem(new Item("Tributações", new ImageIcon(getClass().getResource("/src/imposto32x.png"))));
        myList.addItem(new Item("Anexo 1", new ImageIcon(getClass().getResource("/src/imagem32x.png"))));
        myList.addItem(new Item("Anexo 2", new ImageIcon(getClass().getResource("/src/imagem32x.png"))));
        myList.addItem(new Item("Anexo 3", new ImageIcon(getClass().getResource("/src/imagem32x.png"))));

        myList.AddEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    painelSlide.show(0);
                    getDadosGerais();
                } else if (index == 1) {
                    painelSlide.show(1);
                    getMemorias();
                } else if (index == 2) {
                    painelSlide.show(2);
                    getProcessador();
                } else if (index == 3) {
                    painelSlide.show(3);
                    getPlacaV();
                } else if (index == 4) {
                    painelSlide.show(4);
                    getArmazenamento();
                }
            }
        });

        myList.setSelectedIndex(0);
    }

    public void setColorFromEmpresa() {

        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            myList.setBackground(new Color(15, 102, 122));
            myList.setForeground(new Color(242, 242, 242));
            myList.setSelectedColor(new Color(15, 102, 100));
        } else {
            myList.setBackground(new Color(147, 167, 39));
            myList.setForeground(new Color(242, 242, 242));
            myList.setSelectedColor(new Color(147, 167, 50));
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

//    public void verifUser() {
//        if (perfil.getText().equals("admin")) {
//            precocusto2.setEchoChar('*');
//            precomin2.setEchoChar('*');
//        } else {
//            precocusto2.setVisible(false);
//            precomin2.setEchoChar('*');
//        }
//    }
    public void getDadosGerais() {

        String getID = idNotebooks.getText();

        try {
            String sql = "select * from notebooks where idnotebooks like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {

                CodeBarDetail.setText(rs.getString(2));
                descricaoPainel.setText(rs.getString(3));
                GrupoPainel.setText(rs.getString(4));
                SubGrupoPainel.setText(rs.getString(5));
                PartNumberDetail.setText(rs.getString(6));
                MarcaDetail.setText(rs.getString(7));
                ModeloDetail.setText(rs.getString(8));
                ServiceTagDetail.setText(rs.getString(9));
                RefGeralDetail.setText(rs.getString(10));
                RefClassDetail.setText(rs.getString(11));
                RefunicaDetail.setText(rs.getString(12));

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getMemorias() {
        String getID = idNotebooks.getText();

        try {
            String sql = "select * from notebooks where idnotebooks like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                CapacidadeMaxDetail.setText(rs.getString(16));
                MemoriaRamDetail.setText(rs.getString(13));
                SlotsDetail.setText(rs.getString(17));
                TecnologiaRamDetail.setText(rs.getString(14));
                VelocidadeRamDetail.setText(rs.getString(15));
                seriaisRamDetail.setText(rs.getString(18));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getProcessador() {
        String getID = idNotebooks.getText();

        try {
            String sql = "select * from notebooks where idnotebooks like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                ProcessadorDetail.setText(rs.getString(19));
                ModeloProcessadorDetail.setText(rs.getString(20));
                NucleosDetail.setText(rs.getString(21));
                VelocidadeProcessadorDetail.setText(rs.getString(22));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getPlacaV() {
        String getID = idNotebooks.getText();

        try {
            String sql = "select * from notebooks where idnotebooks like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                placaOnboardNotebook.setText(rs.getString(23));
                modeloOnboardNotebook.setText(rs.getString(24));
                tipoMemoriaOnboard.setText(rs.getString(25));
                memoriaOnboard.setText(rs.getString(26));
                OffboardNotebook.setText(rs.getString(27));
                modeloOffboardNotebook.setText(rs.getString(28));
                TipomemoriaOffboardNotebook.setText(rs.getString(29));
                memoriaOffboardNotebook.setText(rs.getString(30));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getArmazenamento() {
        String getID = idNotebooks.getText();

        try {
            String sql = "select * from notebooks where idnotebooks like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                duploDetail.setText(rs.getString(31));
                tipo1Detail.setText(rs.getString(32));
                cap1Detail.setText(rs.getString(33));
                tipo2Detail.setText(rs.getString(34));
                cap2Detail.setText(rs.getString(35));
                nserieDetail.setText(rs.getString(36));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        myList = new DetailNotebooks.MyList<>();
        painelSlide = new DetailNotebooks.PainelSlide();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        idNotebooks = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(242, 242, 242));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));

        myList.setBackground(new java.awt.Color(147, 167, 39));
        myList.setForeground(new java.awt.Color(242, 242, 242));
        myList.setSelectionBackground(new java.awt.Color(242, 242, 242));
        myList.setSelectionForeground(new java.awt.Color(242, 242, 242));
        jScrollPane1.setViewportView(myList);

        javax.swing.GroupLayout painelSlideLayout = new javax.swing.GroupLayout(painelSlide);
        painelSlide.setLayout(painelSlideLayout);
        painelSlideLayout.setHorizontalGroup(
            painelSlideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        painelSlideLayout.setVerticalGroup(
            painelSlideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/copy.png"))); // NOI18N
        jButton1.setText("Copiar dados para envio em mensagem!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("ID :");

        idNotebooks.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        idNotebooks.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 129, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(idNotebooks, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(painelSlide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idNotebooks, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
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

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        painelSlide.show(0);
        getDadosGerais();
        VerifiMarca();

    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        String getID = idNotebooks.getText();
//        String Descricao = "";
//        String pn = "";
//        String ref = "";
//        String precofinal = "";
//        String precoPromo = "";
//        try {
//            String sql = "select descricao,codigob,pn,refclass,precovenda,precopromocao from partepecas where idpartepecas like ?";
//            pst = conexao.prepareStatement(sql);
//            pst.setString(1, getID);
//
//            rs = pst.executeQuery();
//
//            while (rs.next()) {
//                Descricao = rs.getString(1);
//                pn = rs.getString(3);
//                ref = rs.getString(4);
//                precofinal = rs.getString(5);
//                precoPromo = rs.getString(6);
//            }
//
//        } catch (Exception e) {
//            // Trate a exceção adequadamente
//            e.printStackTrace();
//        }
//        if (precoPromo.equals("0,0")) {
//            String myCopy = "Produto: " + Descricao + "\n"
//            + "PartNumber: " + pn + "\n"
//            + "REF: " + ref + "\n"
//            + "Preço à vista: R$ " + precofinal + "\n"
//            + "Preço Parcelado: R$";
//
//            StringSelection stringSelection = new StringSelection(myCopy);
//            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            clipboard.setContents(stringSelection, null);
//        } else {
//            String myCopy = "Produto: " + Descricao + "\n"
//            + "PartNumber: " + pn + "\n"
//            + "REF: " + ref + "\n"
//            + "Preço à vista: R$ " + precofinal + "\n"
//            + "Preço Promocional: R$ " + precoPromo + "\n"
//            + "Preço Parcelado: R$";
//
//            StringSelection stringSelection = new StringSelection(myCopy);
//            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            clipboard.setContents(stringSelection, null);
//        }

    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(NotebookDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotebookDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotebookDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotebookDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotebookDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField idNotebooks;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private DetailNotebooks.MyList<String> myList;
    private DetailNotebooks.PainelSlide painelSlide;
    // End of variables declaration//GEN-END:variables
}
