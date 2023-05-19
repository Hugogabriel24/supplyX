package pagesSearchs;

import ModelTableCores.CorNaLinhaPP;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;
import static pages.TelaPrincipal.empresa;
import static CadastroProdutos.TelaCadPartePecas.Cancelimg1;
import static CadastroProdutos.TelaCadPartePecas.Cancelimg2;
import static CadastroProdutos.TelaCadPartePecas.Cancelimg3;
import static CadastroProdutos.TelaCadPartePecas.CstConfis;
import static CadastroProdutos.TelaCadPartePecas.Painel1ImagePP;
import static CadastroProdutos.TelaCadPartePecas.Painel2ImagePP;
import static CadastroProdutos.TelaCadPartePecas.Painel3ImagePP;
import static CadastroProdutos.TelaCadPartePecas.Pis;
import static CadastroProdutos.TelaCadPartePecas.anuncioEco;
import static CadastroProdutos.TelaCadPartePecas.anuncioFace;
import static CadastroProdutos.TelaCadPartePecas.anuncioML;
import static CadastroProdutos.TelaCadPartePecas.anuncioOlx;
import static CadastroProdutos.TelaCadPartePecas.btCad;
import static CadastroProdutos.TelaCadPartePecas.btEdit;
import static CadastroProdutos.TelaCadPartePecas.btExcluir;
import static CadastroProdutos.TelaCadPartePecas.btSearch;
import static CadastroProdutos.TelaCadPartePecas.cbCstIpi;
import static CadastroProdutos.TelaCadPartePecas.cbCstPis;
import static CadastroProdutos.TelaCadPartePecas.cbFornecedores;
import static CadastroProdutos.TelaCadPartePecas.cbGrupo;
import static CadastroProdutos.TelaCadPartePecas.cbIcms;
import static CadastroProdutos.TelaCadPartePecas.cbLocal;
import static CadastroProdutos.TelaCadPartePecas.cbMarca;
import static CadastroProdutos.TelaCadPartePecas.cbMedida;
import static CadastroProdutos.TelaCadPartePecas.cbModelo;
import static CadastroProdutos.TelaCadPartePecas.cbPrateleira;
import static CadastroProdutos.TelaCadPartePecas.cbRefClass;
import static CadastroProdutos.TelaCadPartePecas.cbRefGeral;
import static CadastroProdutos.TelaCadPartePecas.cbStatus;
import static CadastroProdutos.TelaCadPartePecas.cbSubgrupo;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioEco;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioEcoInterno;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioFace;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioFaceInterno;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioML;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioMLInterno;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioOlx;
import static CadastroProdutos.TelaCadPartePecas.codAnuncioOlxInterno;
import static CadastroProdutos.TelaCadPartePecas.descri1;
import static CadastroProdutos.TelaCadPartePecas.descri2;
import static CadastroProdutos.TelaCadPartePecas.descricaoncm;
import static CadastroProdutos.TelaCadPartePecas.gerarCodEco;
import static CadastroProdutos.TelaCadPartePecas.gerarCodFace;
import static CadastroProdutos.TelaCadPartePecas.gerarCodML;
import static CadastroProdutos.TelaCadPartePecas.gerarCodOLX;
import static CadastroProdutos.TelaCadPartePecas.habilitar1PP;
import static CadastroProdutos.TelaCadPartePecas.habilitar2PP;
import static CadastroProdutos.TelaCadPartePecas.habilitar3PP;
import static CadastroProdutos.TelaCadPartePecas.origem;
import static CadastroProdutos.TelaCadPartePecas.partnumber;
import static CadastroProdutos.TelaCadPartePecas.precoPromo;
import static CadastroProdutos.TelaCadPartePecas.radioPromo;
import static CadastroProdutos.TelaCadPartePecas.skuForne;
import static CadastroProdutos.TelaCadPartePecas.spQTDE;
import static CadastroProdutos.TelaCadPartePecas.spQtEmb;
import static CadastroProdutos.TelaCadPartePecas.txCofis;
import static CadastroProdutos.TelaCadPartePecas.txIcms;
import static CadastroProdutos.TelaCadPartePecas.txIpi;
import static CadastroProdutos.TelaCadPartePecas.txMotivoD;
import static CadastroProdutos.TelaCadPartePecas.txReducao;
import static CadastroProdutos.TelaCadPartePecas.txSubst;
import static CadastroProdutos.TelaCadPartePecas.txtCodeBar;
import static CadastroProdutos.TelaCadPartePecas.txtDataC;
import static CadastroProdutos.TelaCadPartePecas.txtDescri;
import static CadastroProdutos.TelaCadPartePecas.txtID;
import static CadastroProdutos.TelaCadPartePecas.txtNcm;
import static CadastroProdutos.TelaCadPartePecas.txtObs;
import static CadastroProdutos.TelaCadPartePecas.txtPrecoCusto;
import static CadastroProdutos.TelaCadPartePecas.txtPrecoMin;
import static CadastroProdutos.TelaCadPartePecas.txtPrecoVenda;
import static CadastroProdutos.TelaCadPartePecas.txtSerial;
import static CadastroProdutos.TelaCadPartePecas.txtrefUnica;
import static CadastroProdutos.TelaCadPartePecas.uploadimg1;
import static CadastroProdutos.TelaCadPartePecas.viewImage1PP;
import static CadastroProdutos.TelaCadPartePecas.viewImage2PP;
import static CadastroProdutos.TelaCadPartePecas.viewImage3PP;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public final class SearchPartePecas extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public SearchPartePecas() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        setColorFromEmpresa();
        btSearch.setEnabled(false);

        txProcurar.addEventOptionSelected(new SearchOptinEvent() {

            @Override
            public void optionSelected(SearchOption option, int index) {
                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });

        tbParteP.setDefaultRenderer(Object.class, new CorNaLinhaPP());
        tbParteP.getTableHeader().setResizingAllowed(false);
        tbParteP.getTableHeader().setReorderingAllowed(false);
        txProcurar.addOption(new SearchOption("Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Descrição", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.addOption(new SearchOption("Grupo, marca ou modelo", new ImageIcon(getClass().getResource("/src/marcas.png"))));
        txProcurar.addOption(new SearchOption("Fornecedor", new ImageIcon(getClass().getResource("/src/forne.png"))));
        txProcurar.setSelectedIndex(0);
        pesquisar_PPtnm("");
        pesquisar_PPdc("");
        colocarDados();
    }

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel2.setBackground(new Color(15, 102, 122));
        } else {
            jPanel2.setBackground(new Color(147, 167, 39));
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void colocarDados() {
        if (empresa.getText().equals("tanamao")) {
            String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refclass as REF,grupo as Grupo,marca as Marca,nserie as Serial,precovenda as Preco,qtde as QTDE from partepecas where empresa like 'tanamao'";
            try {
                pst = conexao.prepareStatement(sql);

                rs = pst.executeQuery();
                tbParteP.setModel(DbUtils.resultSetToTableModel(rs));
                tbParteP.getColumnModel().getColumn(0).setMaxWidth(60);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        if (empresa.getText().equals("docatec")) {
            String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refclass as REF,grupo as Grupo,marca as Marca,nserie as Serial,precovenda as Preco,qtde as QTDE from partepecas where empresa like 'docatec'";
            try {
                pst = conexao.prepareStatement(sql);

                rs = pst.executeQuery();
                tbParteP.setModel(DbUtils.resultSetToTableModel(rs));
                tbParteP.getColumnModel().getColumn(0).setMaxWidth(60);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void pesquisar_PPtnm(String wheretnm, Object... searchtnm) {
        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refclass as REF,grupo as Grupo,marca as Marca,nserie as Serial,precovenda as Preco,qtde as QTDE from partepecas " + wheretnm + "";
        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchtnm.length; i++) {
                pst.setObject(i + 1, searchtnm[i]);
            }
            rs = pst.executeQuery();
            tbParteP.setModel(DbUtils.resultSetToTableModel(rs));
            tbParteP.getColumnModel().getColumn(0).setMaxWidth(40);
            tbParteP.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_PPdc(String wheredc, Object... searchdc) {
        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refclass as REF,grupo as Grupo,marca as Marca,nserie as Serial,precovenda as Preco,qtde as QTDE from partepecas " + wheredc + "";
        try {
            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < searchdc.length; i++) {
                pst.setObject(i + 1, searchdc[i]);
            }
            rs = pst.executeQuery();
            tbParteP.setModel(DbUtils.resultSetToTableModel(rs));
            tbParteP.getColumnModel().getColumn(0).setMaxWidth(40);
            tbParteP.getColumnModel().getColumn(2).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void chaangedescri() {
        String sql = "select descri from ncm where ncm=?";
        conexao = ModuloConexao.conector();
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNcm.getSelectedItem().toString());
            rs = pst.executeQuery();
            if (rs.next()) {
                String descri = rs.getString(1);
                descricaoncm.setText(descri);

            }

        } catch (SQLException e) {

        }
    }

    public void getDescriGeral() {
        String sql = "select descri from ref where ref=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbRefGeral.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                String descri = rs.getString(1);
                descri1.setText(descri);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void getDescriClass() {
        String sql = "select descri from refclass where refclass=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbRefClass.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                String descri = rs.getString(1);
                descri2.setText(descri);
                if (descri2.getText().length() > 50) {

                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void GetRefClass() {
        String sql = "select refclass from refclass where refgeral like ?";
        String getRef = cbRefGeral.getSelectedItem().toString();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getRef);

            rs = pst.executeQuery();

            while (rs.next()) {
                String refclass = rs.getString(1);
                cbRefClass.addItem(refclass);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void setarCamposTabela() {
        int setar = tbParteP.getSelectedRow();
        btCad.setEnabled(true);
        String sql = "select * from partepecas where idpartepecas like ?";
        String id = (tbParteP.getModel().getValueAt(setar, 0).toString());

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                txtID.setText(rs.getString(1));
                txtCodeBar.setText(rs.getString(2));
                txtDescri.setText(rs.getString(3));
                cbRefGeral.setSelectedItem(rs.getString(4));
                cbGrupo.setSelectedItem(rs.getString(5));
                cbSubgrupo.setSelectedItem(rs.getString(6));
                cbMarca.setSelectedItem(rs.getString(7));
                cbModelo.setSelectedItem(rs.getString(8));
                txtSerial.setText(rs.getString(9));
                cbFornecedores.setSelectedItem(rs.getString(10));
                txtPrecoCusto.setText(rs.getString(11));
                txtPrecoMin.setText(rs.getString(12));
                txtPrecoVenda.setText(rs.getString(13));
                precoPromo.setText(rs.getString(14));

                if (precoPromo.getText().equals("0,0") || (precoPromo.getText().equals(""))) {
                    radioPromo.setSelected(false);
                    precoPromo.setText("0,0");
                    precoPromo.setEnabled(false);

                } else {
                    radioPromo.setSelected(true);
                    precoPromo.setEnabled(true);
                }

                spQTDE.setValue(rs.getInt(15));
                cbMedida.setSelectedItem(rs.getString(16));
                spQtEmb.setValue(rs.getInt(17));
                cbLocal.setSelectedItem(rs.getString(18));
                cbPrateleira.setSelectedItem(rs.getString(19));
                cbStatus.setSelectedItem(rs.getString(21));
                txtObs.setText(rs.getString(22));
                txtDataC.setText(rs.getString(23));
                anuncioOlx.setSelectedItem(rs.getString(24));
                codAnuncioOlxInterno.setText(rs.getString(25));
                codAnuncioOlx.setText(rs.getString(26));
                anuncioML.setSelectedItem(rs.getString(27));
                codAnuncioMLInterno.setText(rs.getString(28));
                codAnuncioML.setText(rs.getString(29));
                anuncioFace.setSelectedItem(rs.getString(30));
                codAnuncioFaceInterno.setText(rs.getString(31));
                codAnuncioFace.setText(rs.getString(32));
                anuncioEco.setSelectedItem(rs.getString(33));
                codAnuncioEcoInterno.setText(rs.getString(34));
                codAnuncioEco.setText(rs.getString(35));
                skuForne.setText(rs.getString(36));
                if (rs.getBytes(37) == null) {

                } else {
                    ImageIcon icone1 = new ImageIcon(rs.getBytes(37));
                    icone1.setImage(icone1.getImage().getScaledInstance(Painel1ImagePP.getWidth() - 1, Painel1ImagePP.getWidth() - 1, 100));
                    viewImage1PP.setIcon(icone1);
                }
                if (rs.getBytes(38) == null) {

                } else {
                    ImageIcon icone2 = new ImageIcon(rs.getBytes(38));
                    icone2.setImage(icone2.getImage().getScaledInstance(Painel2ImagePP.getWidth() - 1, Painel2ImagePP.getWidth() - 1, 100));
                    viewImage2PP.setIcon(icone2);
                }
                if (rs.getBytes(39) == null) {

                } else {
                    ImageIcon icone3 = new ImageIcon(rs.getBytes(39));
                    icone3.setImage(icone3.getImage().getScaledInstance(Painel3ImagePP.getWidth() - 1, Painel3ImagePP.getWidth() - 1, 100));
                    viewImage3PP.setIcon(icone3);
                }

                txtNcm.setSelectedItem(rs.getString(41));
                cbIcms.setSelectedItem(rs.getString(42));
                txIcms.setText(rs.getString(43));
                cbCstPis.setSelectedItem(rs.getString(44));
                Pis.setText(rs.getString(45));
                CstConfis.setSelectedItem(rs.getString(46));
                txCofis.setText(rs.getString(47));
                cbCstIpi.setSelectedItem(rs.getString(48));
                txIpi.setText(rs.getString(49));
                txMotivoD.setSelectedItem(rs.getString(50));
                txSubst.setText(rs.getString(51));
                txReducao.setText(rs.getString(52));
                origem.setSelectedItem(rs.getString(53));
                cbRefClass.setSelectedItem(rs.getString(54));
                txtrefUnica.setText(rs.getString(55));
                partnumber.setText(rs.getString(56));
                if (anuncioOlx.getSelectedItem().equals("SIM")) {
                    codAnuncioOlxInterno.setEnabled(true);
                    gerarCodOLX.setEnabled(true);
                    codAnuncioOlx.setEnabled(true);
                } else {
                    codAnuncioOlxInterno.setEnabled(false);
                    gerarCodOLX.setEnabled(false);
                    codAnuncioOlx.setEnabled(false);
                    codAnuncioOlx.setText("");
                    codAnuncioOlxInterno.setText("");
                }
                if (anuncioML.getSelectedItem().equals("SIM")) {
                    codAnuncioMLInterno.setEnabled(true);
                    gerarCodML.setEnabled(true);
                    codAnuncioML.setEnabled(true);
                } else {
                    codAnuncioMLInterno.setEnabled(false);
                    gerarCodML.setEnabled(false);
                    codAnuncioML.setEnabled(false);
                    codAnuncioML.setText("");
                    codAnuncioMLInterno.setText("");
                }
                if (anuncioFace.getSelectedItem().equals("SIM")) {
                    codAnuncioFaceInterno.setEnabled(true);
                    gerarCodFace.setEnabled(true);
                    codAnuncioFace.setEnabled(true);
                } else {
                    codAnuncioFaceInterno.setEnabled(false);
                    gerarCodFace.setEnabled(false);
                    codAnuncioFace.setEnabled(false);
                    codAnuncioFaceInterno.setText("");
                    codAnuncioFace.setText("");
                }
                if (anuncioEco.getSelectedItem().equals("SIM")) {
                    codAnuncioEcoInterno.setEnabled(true);
                    gerarCodEco.setEnabled(true);
                    codAnuncioEco.setEnabled(true);
                    
                } else {
                    
                    codAnuncioEcoInterno.setEnabled(false);
                    gerarCodEco.setEnabled(false);
                    codAnuncioEco.setEnabled(false);
                    codAnuncioEcoInterno.setText("");
                    codAnuncioEco.setText("");
                    
                }

                if (txtNcm.getSelectedItem().equals("--")) {
                    
                    descricaoncm.setVisible(false);
                    
                } else {
                    descricaoncm.setVisible(true);
                    chaangedescri();

                }
                
                getDescriGeral();
                getDescriClass();
                cbRefClass.removeAllItems();
                GetRefClass();

                uploadimg1.setEnabled(false);
                Cancelimg1.setEnabled(false);
                Cancelimg2.setEnabled(false);
                Cancelimg3.setEnabled(false);

                btEdit.setEnabled(true);
                btExcluir.setEnabled(true);
                btCad.setEnabled(false);
                habilitar1PP.setVisible(true);
                habilitar2PP.setVisible(true);
                habilitar3PP.setVisible(true);
                cbRefClass.setEnabled(true);

                this.dispose();

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao carregar os dados do produto!</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        PainelBackuground = new javax.swing.JPanel();
        btCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbParteP = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pesquisar partes & peças");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        PainelBackuground.setBackground(new java.awt.Color(204, 204, 204));

        btCancel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        btCancel.setText("Cancelar");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 231, Short.MAX_VALUE)
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbParteP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tbParteP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRICAO", "GRUPO", "MARCA", "SERIAL", "PRECO", "QTDE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbParteP.getTableHeader().setReorderingAllowed(false);
        tbParteP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPartePMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbParteP);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout PainelBackugroundLayout = new javax.swing.GroupLayout(PainelBackuground);
        PainelBackuground.setLayout(PainelBackugroundLayout);
        PainelBackugroundLayout.setHorizontalGroup(
            PainelBackugroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PainelBackugroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelBackugroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelBackugroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btCancel))
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PainelBackugroundLayout.setVerticalGroup(
            PainelBackugroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelBackugroundLayout.createSequentialGroup()
                .addGroup(PainelBackugroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelBackugroundLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(293, 293, 293))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelBackugroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tableScrollButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(btCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelBackuground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelBackuground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbPartePMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPartePMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
    }//GEN-LAST:event_tbPartePMouseClicked

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        this.dispose();
        btEdit.setEnabled(false);
        btExcluir.setEnabled(false);
        btSearch.setEnabled(true);
    }//GEN-LAST:event_btCancelActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased

        if (empresa.getText().equals("tanamao")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                if (option == 0) {
                    pesquisar_PPtnm("where empresa ='tanamao' and (codigob like ? or refclass like ? or nserie like ?)", text, text, text);
                } else if (option == 1) {
                    pesquisar_PPtnm("where descricao like ? and empresa ='tanamao'", text);
                } else if (option == 2) {
                    pesquisar_PPtnm("where empresa ='tanamao' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
                } else if (option == 3) {
                    pesquisar_PPtnm("where forne like ? and empresa ='tanamao'", text);
                }
            }
        }
        if (empresa.getText().equals("docatec")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                if (option == 0) {
                    pesquisar_PPdc("where empresa ='docatec' and (codigob like ? or refclass like ? or nserie like ?)", text, text, text);
                } else if (option == 1) {
                    pesquisar_PPdc("where descricao like ? and empresa ='docatec'", text);
                } else if (option == 2) {
                    pesquisar_PPdc("where empresa ='docatec' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
                } else if (option == 3) {
                    pesquisar_PPdc("where forne like ? and empresa ='docatec'", text);
                }
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        btSearch.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        btSearch.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(SearchPartePecas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchPartePecas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchPartePecas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchPartePecas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchPartePecas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBackuground;
    private javax.swing.JButton btCancel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    public static javax.swing.JTable tbParteP;
    private textFieldSearch.TextFieldSearchOption txProcurar;
    // End of variables declaration//GEN-END:variables
}
