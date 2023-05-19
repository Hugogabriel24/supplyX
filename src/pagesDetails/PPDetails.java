package pagesDetails;

import DetailParteEPecas.PainelPP1;
import DetailParteEPecas.ItemPP;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import DetailParteEPecas.EventMenuSelectedPP;
import static DetailParteEPecas.PainelPP1.codebar1;
import static DetailParteEPecas.PainelPP1.descricao1;
import static DetailParteEPecas.PainelPP1.forne1;
import static DetailParteEPecas.PainelPP1.grupo1;
import static DetailParteEPecas.PainelPP1.marca1;
import static DetailParteEPecas.PainelPP1.modelo1;
import static DetailParteEPecas.PainelPP1.ref1;
import static DetailParteEPecas.PainelPP1.subgrupo1;
import DetailParteEPecas.PainelPP11;
import DetailParteEPecas.PainelPP2;
import static DetailParteEPecas.PainelPP2.precocusto2;
import static DetailParteEPecas.PainelPP2.precofinal2;
import static DetailParteEPecas.PainelPP2.precomin2;
import DetailParteEPecas.PainelPP3;
import static DetailParteEPecas.PainelPP3.local3;
import static DetailParteEPecas.PainelPP3.prate3;
import static DetailParteEPecas.PainelPP3.qtd3;
import static DetailParteEPecas.PainelPP3.qtdemb3;
import static DetailParteEPecas.PainelPP3.und3;
import DetailParteEPecas.PainelPP4;
import static DetailParteEPecas.PainelPP4.data4;
import static DetailParteEPecas.PainelPP4.status4;
import static DetailParteEPecas.PainelPP4.obs4;
import DetailParteEPecas.PainelPP5;
import DetailParteEPecas.PainelPP6;
import DetailParteEPecas.PainelPP7;
import DetailParteEPecas.PainelPP8;
import DetailParteEPecas.PainelPP9;
import static DetailParteEPecas.PainelPP9.img3pp;
import static DetailParteEPecas.PainelPP9.painelimg3;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import static pages.TelaPrincipal.empresa;
import static pages.TelaPrincipal.perfil;
import static DetailParteEPecas.PainelPP11.codebarREFS;
import static DetailParteEPecas.PainelPP11.serialREFS;
import static DetailParteEPecas.PainelPP11.skuforneREFS;
import static DetailParteEPecas.PainelPP11.PartNumberREFS;
import static DetailParteEPecas.PainelPP11.RefsFromREFS;
import static DetailParteEPecas.PainelPP11.ClassREFS;
import static DetailParteEPecas.PainelPP11.UniqREFS;
import static DetailParteEPecas.PainelPP2.txpromoc;
import static DetailParteEPecas.PainelPP5.ec1;
import static DetailParteEPecas.PainelPP5.ec2;
import static DetailParteEPecas.PainelPP5.ec3;
import static DetailParteEPecas.PainelPP5.fb1;
import static DetailParteEPecas.PainelPP5.fb2;
import static DetailParteEPecas.PainelPP5.fb3;
import static DetailParteEPecas.PainelPP5.ml1;
import static DetailParteEPecas.PainelPP5.ml2;
import static DetailParteEPecas.PainelPP5.ml3;
import static DetailParteEPecas.PainelPP5.olx1;
import static DetailParteEPecas.PainelPP5.olx2;
import static DetailParteEPecas.PainelPP5.olx3;
import static DetailParteEPecas.PainelPP6.cofinspp;
import static DetailParteEPecas.PainelPP6.cstcofinspp;
import static DetailParteEPecas.PainelPP6.csticmspp;
import static DetailParteEPecas.PainelPP6.cstipipp;
import static DetailParteEPecas.PainelPP6.cstpispp;
import static DetailParteEPecas.PainelPP6.icmspp;
import static DetailParteEPecas.PainelPP6.ipipp;
import static DetailParteEPecas.PainelPP6.motivopp;
import static DetailParteEPecas.PainelPP6.ncmpp;
import static DetailParteEPecas.PainelPP6.pispp;
import static DetailParteEPecas.PainelPP6.reducaopp;
import static DetailParteEPecas.PainelPP6.subspp;
import static DetailParteEPecas.PainelPP7.Painel1PP;
import static DetailParteEPecas.PainelPP7.viewIcon1;
import static DetailParteEPecas.PainelPP8.Painel2PP;
import static DetailParteEPecas.PainelPP8.viewIcon2;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import pagesEstoque.TelaEstoquePP;

public final class PPDetails extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private EventMenuSelectedPP event;

    public void AddEventMenuSelected(EventMenuSelectedPP event) {
        this.event = event;
        myListPP.AddEventMenuSelected(event);
    }

    public PPDetails() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        setColorFromEmpresa();
        setFocusable(true);

        painelSlidePP.init(new PainelPP1(), new PainelPP2(), new PainelPP3(), new PainelPP4(), new PainelPP5(), new PainelPP6(), new PainelPP7(), new PainelPP8(), new PainelPP9(), new PainelPP11());
        painelSlidePP.setAnimate(8);

        myListPP.addItem(new ItemPP("Dados gerais", new ImageIcon(getClass().getResource("/src/DadosNotebook.png"))));
        myListPP.addItem(new ItemPP("Refêrencias e códigos", new ImageIcon(getClass().getResource("/src/referencia.png"))));
        myListPP.addItem(new ItemPP("Valores", new ImageIcon(getClass().getResource("/src/dolares.png"))));
        myListPP.addItem(new ItemPP("Estoque", new ImageIcon(getClass().getResource("/src/estoque-pronto.png"))));
        myListPP.addItem(new ItemPP("Mais informações", new ImageIcon(getClass().getResource("/src/em-formacao.png"))));
        myListPP.addItem(new ItemPP("Anuncios", new ImageIcon(getClass().getResource("/src/anuncio32x.png"))));
        myListPP.addItem(new ItemPP("Tributações", new ImageIcon(getClass().getResource("/src/imposto32x.png"))));
        myListPP.addItem(new ItemPP("Anexo 1", new ImageIcon(getClass().getResource("/src/imagem32x.png"))));
        myListPP.addItem(new ItemPP("Anexo 2", new ImageIcon(getClass().getResource("/src/imagem32x.png"))));
        myListPP.addItem(new ItemPP("Anexo 3", new ImageIcon(getClass().getResource("/src/imagem32x.png"))));

        myListPP.AddEventMenuSelected(new EventMenuSelectedPP() {
            @Override
            public void selected(int index) {
                switch (index) {
                    case 0:
                        painelSlidePP.show(0);
                        getDadosGerais();
                        break;
                    case 1:
                        painelSlidePP.show(9);
                        getRefs();
                        break;
                    case 2:
                        painelSlidePP.show(1);
                        getValores();
                        verifUser();
                        break;
                    case 3:
                        painelSlidePP.show(2);
                        getEstoque();
                        break;
                    case 4:
                        painelSlidePP.show(3);
                        getInfo();
                        break;
                    case 5:
                        painelSlidePP.show(4);
                        getAnuncios();
                        break;
                    case 6:
                        painelSlidePP.show(5);
                        getTributos();
                        break;
                    case 7:
                        painelSlidePP.show(6);
                        getAnexo1();
                        break;
                    case 8:
                        painelSlidePP.show(7);
                        getAnexo2();
                        break;
                    case 9:
                        painelSlidePP.show(8);
                        getAnexo3();
                        break;
                    case 10:
                        painelSlidePP.show(9);
                        break;
                    default:
                        break;
                }
            }
        });

        myListPP.setSelectedIndex(0);

    }
    
    

    public void setColorFromEmpresa() {

        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            myListPP.setBackground(new Color(15, 102, 122));
            myListPP.setForeground(new Color(242, 242, 242));
            myListPP.setSelectedColor(new Color(15, 102, 100));
        } else {
            myListPP.setBackground(new Color(147, 167, 39));
            myListPP.setForeground(new Color(242, 242, 242));
            myListPP.setSelectedColor(new Color(147, 167, 50));
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void verifUser() {
        if (perfil.getText().equals("admin")) {
            precocusto2.setEchoChar('*');
            precomin2.setEchoChar('*');
        } else {
            precocusto2.setVisible(false);
            precomin2.setEchoChar('*');
        }
    }

    public void getDadosGerais() {

        String getID = idPP0001.getText();

        try {
            String sql = "select * from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                descricao1.setText(rs.getString(3));
                codebar1.setText(rs.getString(2));
                ref1.setText(rs.getString(4));
                grupo1.setText(rs.getString(5));
                subgrupo1.setText(rs.getString(6));
                marca1.setText(rs.getString(7));
                modelo1.setText(rs.getString(8));
                forne1.setText(rs.getString(10));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getRefs() {
        String getID = idPP0001.getText();

        try {
            String sql = "select codigob,ref,nserie,skufornecedor,pn,refclass,refunica from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                codebarREFS.setText(rs.getString(1));
                serialREFS.setText(rs.getString(3));
                skuforneREFS.setText(rs.getString(4));
                PartNumberREFS.setText(rs.getString(5));
                RefsFromREFS.setText(rs.getString(2));
                ClassREFS.setText(rs.getString(6));
                UniqREFS.setText(rs.getString(7));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getValores() {

        String getID = idPP0001.getText();

        try {
            String sql = "select * from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {

                precocusto2.setText(rs.getString(11));
                precomin2.setText(rs.getString(12));
                precofinal2.setText(rs.getString(13));
                txpromoc.setText(rs.getString(14));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getEstoque() {
        String getID = idPP0001.getText();
        try {
            String sql = "select * from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {

                qtd3.setText(rs.getString(15));
                und3.setText(rs.getString(16));
                qtdemb3.setText(rs.getString(17));
                local3.setText(rs.getString(18));
                prate3.setText(rs.getString(19));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getInfo() {

        String getID = idPP0001.getText();

        try {
            String sql = "select * from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                status4.setText(rs.getString(21));
                obs4.setText(rs.getString(22));
                data4.setText(rs.getString(23));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getAnuncios() {

        String getID = idPP0001.getText();

        try {
            String sql = "select * from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {

                olx1.setText(rs.getString(24));
                olx2.setText(rs.getString(25));
                olx3.setText(rs.getString(26));
                ml1.setText(rs.getString(27));
                ml2.setText(rs.getString(28));
                ml3.setText(rs.getString(29));
                fb1.setText(rs.getString(30));
                fb2.setText(rs.getString(31));
                fb3.setText(rs.getString(32));
                ec1.setText(rs.getString(33));
                ec2.setText(rs.getString(34));
                ec3.setText(rs.getString(35));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getTributos() {

        String getID = idPP0001.getText();

        try {
            String sql = "select * from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {

                ncmpp.setText(rs.getString(41));
                csticmspp.setText(rs.getString(42));
                icmspp.setText(rs.getString(43));
                cstpispp.setText(rs.getString(44));
                pispp.setText(rs.getString(45));
                cstcofinspp.setText(rs.getString(46));
                cofinspp.setText(rs.getString(47));
                cstipipp.setText(rs.getString(48));
                ipipp.setText(rs.getString(49));
                motivopp.setText(rs.getString(50));
                reducaopp.setText(rs.getString(51));
                subspp.setText(rs.getString(52));

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getAnexo1() {
        String getID = idPP0001.getText();
        try {
            String sql = "select imagem1 from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);
            rs = pst.executeQuery();
            while (rs.next()) {
                byte[] bytesDoBancoDeDados = rs.getBytes(1);
                if (bytesDoBancoDeDados != null && bytesDoBancoDeDados.length > 0) {
                    ImageIcon icone1 = new ImageIcon(rs.getBytes(1));
                    icone1.setImage(icone1.getImage().getScaledInstance(Painel1PP.getWidth() - 1, Painel1PP.getWidth() - 1, 100));
                    viewIcon1.setIcon(icone1);
                }

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getAnexo2() {

        String getID = idPP0001.getText();

        try {
            String sql = "select imagem2 from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                byte[] bytesDoBancoDeDados = rs.getBytes(1);
                if (bytesDoBancoDeDados != null && bytesDoBancoDeDados.length > 0) {
                    ImageIcon icone1 = new ImageIcon(rs.getBytes(1));
                    icone1.setImage(icone1.getImage().getScaledInstance(Painel2PP.getWidth() - 1, Painel2PP.getWidth() - 1, 100));
                    viewIcon2.setIcon(icone1);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getAnexo3() {

        String getID = idPP0001.getText();

        try {
            String sql = "select imagem3 from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                byte[] bytesDoBancoDeDados = rs.getBytes(1);
                if (bytesDoBancoDeDados != null && bytesDoBancoDeDados.length > 0) {
                    ImageIcon icone1 = new ImageIcon(rs.getBytes(1));
                    icone1.setImage(icone1.getImage().getScaledInstance(painelimg3.getWidth() - 1, painelimg3.getWidth() - 1, 100));
                    img3pp.setIcon(icone1);
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

        itemPP1 = new DetailParteEPecas.ItemPP();
        jPanel1 = new javax.swing.JPanel();
        idPP0001 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        myListPP = new DetailParteEPecas.MyListPP<>();
        painelSlidePP = new DetailParteEPecas.PainelSlidePP();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        idPP0001.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        idPP0001.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("ID :");

        myListPP.setBackground(new java.awt.Color(147, 167, 39));
        jScrollPane2.setViewportView(myListPP);

        javax.swing.GroupLayout painelSlidePPLayout = new javax.swing.GroupLayout(painelSlidePP);
        painelSlidePP.setLayout(painelSlidePPLayout);
        painelSlidePPLayout.setHorizontalGroup(
            painelSlidePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );
        painelSlidePPLayout.setVerticalGroup(
            painelSlidePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(idPP0001, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelSlidePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(painelSlidePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idPP0001, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        painelSlidePP.show(0);
        getDadosGerais();
        String getIDTitle = idPP0001.getText();
        this.setTitle("PRODUTO - " + getIDTitle + "");
    }//GEN-LAST:event_formWindowActivated

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            TelaEstoquePP.txProcurar.requestFocus();
            dispose();

        }
    }//GEN-LAST:event_formKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String getID = idPP0001.getText();
        String Descricao = "";
        String pn = "";
        String ref = "";
        String precofinal = "";
        String precoPromo = "";
        try {
            String sql = "select descricao,codigob,pn,refclass,precovenda,precopromocao from partepecas where idpartepecas like ?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getID);

            rs = pst.executeQuery();

            while (rs.next()) {
                Descricao = rs.getString(1);
                pn = rs.getString(3);
                ref = rs.getString(4);
                precofinal = rs.getString(5);
                precoPromo = rs.getString(6);
            }

        } catch (Exception e) {
            // Trate a exceção adequadamente
            e.printStackTrace();
        }
        if (precoPromo.equals("0,0")) {
            String myCopy = "Produto: " + Descricao + "\n"
                    + "PartNumber: " + pn + "\n"
                    + "REF: " + ref + "\n"
                    + "Preço à vista: R$ " + precofinal + "\n"
                    + "Preço Parcelado: R$";

            StringSelection stringSelection = new StringSelection(myCopy);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } else {
            String myCopy = "Produto: " + Descricao + "\n"
                    + "PartNumber: " + pn + "\n"
                    + "REF: " + ref + "\n"
                    + "Preço à vista: R$ " + precofinal + "\n"
                    + "Preço Promocional: R$ " + precoPromo + "\n"
                    + "Preço Parcelado: R$";

            StringSelection stringSelection = new StringSelection(myCopy);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }


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
            java.util.logging.Logger.getLogger(PPDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PPDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PPDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PPDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PPDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTextField idPP0001;
    private DetailParteEPecas.ItemPP itemPP1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private DetailParteEPecas.MyListPP<String> myListPP;
    private DetailParteEPecas.PainelSlidePP painelSlidePP;
    // End of variables declaration//GEN-END:variables
}
