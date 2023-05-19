package pagesEstoque;

import ArchiveTablesVendas.TableCustom;
import ModelTableCores.CorNaLinhaEstoquePP;
import TablesGetSetters.ListProdutosShowPP;
import TableModels.ModelTableToPrint;
import TablesGetSetters.ProdutosToPrint;
import TableModels.TableModelShowPP;
import br.com.tanamao.dal.ModuloConexao;
import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotifyTheme;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import static pages.TelaPrincipal.empresa;
import CadastroProdutos.TelaCadPartePecas;
import static CadastroProdutos.TelaCadPartePecas.CstConfis;
import static CadastroProdutos.TelaCadPartePecas.Pis;
import static CadastroProdutos.TelaCadPartePecas.anuncioEco;
import static CadastroProdutos.TelaCadPartePecas.anuncioFace;
import static CadastroProdutos.TelaCadPartePecas.anuncioML;
import static CadastroProdutos.TelaCadPartePecas.anuncioOlx;
import static CadastroProdutos.TelaCadPartePecas.btCad;
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
import static CadastroProdutos.TelaCadPartePecas.txtNcm;
import static CadastroProdutos.TelaCadPartePecas.txtObs;
import static CadastroProdutos.TelaCadPartePecas.txtPrecoCusto;
import static CadastroProdutos.TelaCadPartePecas.txtPrecoMin;
import static CadastroProdutos.TelaCadPartePecas.txtPrecoVenda;
import javax.swing.SpinnerNumberModel;
import static pages.TelaPrincipal.perfil;
import pagesDetails.PPDetails;
import static pagesDetails.PPDetails.idPP0001;
import textFieldSearch.SearchOptinEvent;
import textFieldSearch.SearchOption;

public final class TelaEstoquePP extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ModelTableToPrint modelo;
    TableModelShowPP model = new TableModelShowPP() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public TelaEstoquePP() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        setFocusable(true);
        modelo = new ModelTableToPrint();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
        setColorFromEmpresa();
        verifUser();
        tablePrint.setModel(modelo);
        tablePP.setModel(model);
        tablePrint.getColumnModel().getColumn(0).setMaxWidth(60);
        
        txProcurar.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {

                txProcurar.setHint("Procurar por " + option.getName() + "....");
                txProcurar.setText(null);
            }
        });
        tablePP.setDefaultRenderer(Object.class, new CorNaLinhaEstoquePP());
        tablePP.getTableHeader().setResizingAllowed(false);
        tablePP.getTableHeader().setReorderingAllowed(false);
        spinnerNew.setModel(new SpinnerNumberModel(1, 0, 99999, 1));
        txProcurar.addOption(new SearchOption("Barcode, referência ou SerialNumber", new ImageIcon(getClass().getResource("/src/codabar.png"))));
        txProcurar.addOption(new SearchOption("Descrição / Observações / Modelos compatíveis", new ImageIcon(getClass().getResource("/src/descri.png"))));
        txProcurar.addOption(new SearchOption("Grupo, marca ou modelo", new ImageIcon(getClass().getResource("/src/marcas.png"))));
        txProcurar.addOption(new SearchOption("Fornecedor", new ImageIcon(getClass().getResource("/src/forne.png"))));
        txProcurar.addOption(new SearchOption("Local em estoque, prateleira, gavetas", new ImageIcon(getClass().getResource("/src/prateleira.png"))));
        txProcurar.setSelectedIndex(0);
        txProcurar.requestFocus();
        pesquisar_EstoquePP("");
        colocarDados();
    }

    public void verifUser() {
        if (perfil.getText().equals("user")) {
            btRemoveQtde.setVisible(false);
            txtQtdAtual.setVisible(false);
            labelQtd.setVisible(false);
            spinnerNew.setVisible(false);
            btAddqtde.setVisible(false);
            btRemoveQtde.setEnabled(false);
            txtQtdAtual.setEnabled(false);
            btAddqtde.setEnabled(false);
        } else {
            btRemoveQtde.setVisible(true);
            txtQtdAtual.setVisible(true);
            labelQtd.setVisible(true);
            spinnerNew.setVisible(true);
            btAddqtde.setVisible(true);
            btRemoveQtde.setEnabled(true);
            txtQtdAtual.setEnabled(true);
            btAddqtde.setEnabled(true);
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setColorFromEmpresa() {

        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel9.setBackground(new Color(15, 102, 122));
        } else {
            jPanel9.setBackground(new Color(147, 167, 39));
        }
    }

    public void setcodabar() {
        String codigo;
        try {
            do {
                codigo = gerarCodigoEAN13();
            } while (verificarCodigoExistente(codigo));
            txtCodeBar.setText(codigo);
        } catch (SQLException e) {
        }

    }

    public boolean verificarCodigoExistente(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM partepecas WHERE codigob = ?";

        pst = conexao.prepareStatement(sql);
        pst.setString(1, codigo);
        rs = pst.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return count > 0;

    }

    private static String gerarCodigoEAN13() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("0");
        for (int i = 0; i < 11; i++) {
            sb.append(random.nextInt(10));
        }
        int digitoVerificador = calcularDigitoVerificador(sb.toString());
        sb.append(digitoVerificador);
        return sb.toString();
    }

    private static int calcularDigitoVerificador(String codigo) {
        int soma1 = 0;
        int soma2 = 0;
        for (int i = 0; i < codigo.length(); i++) {
            int digito = Integer.parseInt(codigo.substring(i, i + 1));
            if (i % 2 == 0) {
                soma1 += digito;
            } else {
                soma2 += digito;
            }
        }
        int somaTotal = soma1 + soma2 * 3;
        int resto = somaTotal % 10;
        return (10 - resto) % 10;
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

    public void colocarDados() {
        String emp = empresa.getText();
        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? order by descricao";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, emp);
            rs = pst.executeQuery();
            JTableHeader header = tablePP.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Preço");
            header.repaint();
            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_EstoquePP(String where, Object... search) {

        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as Promo,qtde as QTDE from partepecas " + where + "";

        try {

            pst = conexao.prepareStatement(sql);

            for (int i = 0; i < search.length; i++) {
                pst.setObject(i + 1, search[i]);
            }
            rs = pst.executeQuery();
            JTableHeader header = tablePP.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Preço");
            header.repaint();
            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void setarCamposTabela() {

        int setar = tablePP.getSelectedRow();
        String sql = "select * from partepecas where idpartepecas like ?";

        String id = (tablePP.getModel().getValueAt(setar, 0).toString());
        String codabar = (tablePP.getModel().getValueAt(setar, 1).toString());

        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                txtQtdAtual.setText(rs.getString(15));
                idselected.setText(rs.getString(1));
                descriselected.setText(rs.getString(3));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void addToPrint() {
        ProdutosToPrint PPT = new ProdutosToPrint();
        int setar = tablePP.getSelectedRow();

        if (setar >= 0) {
            if (tablePrint.getRowCount() >= 3) {
                JOptionPane.showMessageDialog(null, "Número máximo de 3 produtos pra impressão atingido!");
            } else {
                String id = (tablePP.getModel().getValueAt(setar, 0).toString());
                String descri = (tablePP.getModel().getValueAt(setar, 2).toString());

                PPT.setID(id);
                PPT.setDescricao(descri);

                modelo = (ModelTableToPrint) tablePrint.getModel();
                tablePrint.setModel(modelo);
                modelo.addProdutosPPT(PPT);
                geraretq.setEnabled(true);
                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "" + descri + " adicionado a fila de impressão de etiqueta");
                noti.showNotification();
            }

        } else {

        }

    }

    public void addToPrint1Line() {
        ProdutosToPrint PPT = new ProdutosToPrint();
        int setar = tablePP.getRowCount();

        if (setar == 1) {
            if (tablePrint.getRowCount() >= 3) {
                JOptionPane.showMessageDialog(null, "Número máximo de 3 produtos pra impressão atingido!");
            } else {
                String id = (tablePP.getModel().getValueAt(0, 0).toString());
                String descri = (tablePP.getModel().getValueAt(0, 2).toString());

                PPT.setID(id);
                PPT.setDescricao(descri);

                modelo = (ModelTableToPrint) tablePrint.getModel();
                tablePrint.setModel(modelo);
                modelo.addProdutosPPT(PPT);
                geraretq.setEnabled(true);
                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "" + descri + " adicionado a fila de impressão de etiqueta");
                noti.showNotification();
            }

        } else {

        }

    }

    public void setNewCadastro() {
        int setar = tablePP.getSelectedRow();
        btCad.setEnabled(true);
        String sql = "select * from partepecas where idpartepecas like ?";
        String id = (tablePP.getModel().getValueAt(setar, 0).toString());

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {

                txtDescri.setText(rs.getString(3));
                cbRefGeral.setSelectedItem(rs.getString(4));
                cbGrupo.setSelectedItem(rs.getString(5));
                cbSubgrupo.setSelectedItem(rs.getString(6));
                cbMarca.setSelectedItem(rs.getString(7));
                cbModelo.setSelectedItem(rs.getString(8));
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
                setcodabar();
            }
        } catch (Exception e) {

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idselected = new javax.swing.JTextField();
        descriselected = new javax.swing.JTextField();
        dialogPrint = new javax.swing.JDialog();
        itensPrint = new javax.swing.JScrollPane();
        tablePrint = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        labelQtd = new javax.swing.JLabel();
        txtQtdAtual = new javax.swing.JTextField();
        spinnerNew = new javax.swing.JSpinner();
        btAddqtde = new javax.swing.JButton();
        btRemoveQtde = new javax.swing.JButton();
        txProcurar = new textFieldSearch.TextFieldSearchOption();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        btDetalhesVenda = new javax.swing.JButton();
        geraretq = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePP = new javax.swing.JTable();
        ProdutosTotais = new javax.swing.JRadioButton();
        ProdutosDisponiveis = new javax.swing.JRadioButton();
        ProdutosOff = new javax.swing.JRadioButton();
        btDetalhesVenda1 = new javax.swing.JButton();

        idselected.setText("jTextField1");

        descriselected.setText("jTextField1");

        dialogPrint.setUndecorated(true);
        dialogPrint.setSize(new java.awt.Dimension(373, 258));

        tablePrint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        itensPrint.setViewportView(tablePrint);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setText("Lista de impressão");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton5.setText("Fechar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton4.setText("Limpar lista de impressão");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton6.setText("Excluir item");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogPrintLayout = new javax.swing.GroupLayout(dialogPrint.getContentPane());
        dialogPrint.getContentPane().setLayout(dialogPrintLayout);
        dialogPrintLayout.setHorizontalGroup(
            dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(itensPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(dialogPrintLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        dialogPrintLayout.setVerticalGroup(
            dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(itensPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dialogPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(67, 118, 218));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 3, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("ESTOQUE DE PARTES & PEÇAS");

        labelQtd.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        labelQtd.setForeground(new java.awt.Color(255, 255, 255));
        labelQtd.setText("QUANTIDADE ATUAL :");

        txtQtdAtual.setEnabled(false);

        btAddqtde.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btAddqtde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionar-acrescentar.png"))); // NOI18N
        btAddqtde.setText("Adicionar");
        btAddqtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddqtdeActionPerformed(evt);
            }
        });

        btRemoveQtde.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btRemoveQtde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveQtde.setText("Remover");
        btRemoveQtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveQtdeActionPerformed(evt);
            }
        });

        txProcurar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txProcurarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txProcurarKeyReleased(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/addlista.png"))); // NOI18N
        jButton1.setText(" Adicionar item a lista de impressão");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton7.setText("Limpar lista de impressão");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(labelQtd)
                        .addGap(18, 18, 18)
                        .addComponent(txtQtdAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(spinnerNew, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(btAddqtde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemoveQtde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelQtd)
                    .addComponent(txtQtdAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinnerNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAddqtde)
                    .addComponent(btRemoveQtde)
                    .addComponent(jButton1)
                    .addComponent(jButton7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btDetalhesVenda.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btDetalhesVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/detalhes.png"))); // NOI18N
        btDetalhesVenda.setText("Detalhes do produto");
        btDetalhesVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDetalhesVendaActionPerformed(evt);
            }
        });

        geraretq.setBackground(new java.awt.Color(102, 102, 102));
        geraretq.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        geraretq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/etiqueta-de-preco_1.png"))); // NOI18N
        geraretq.setText("Gerar etiqueta");
        geraretq.setEnabled(false);
        geraretq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geraretqActionPerformed(evt);
            }
        });

        tablePP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablePP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePPMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablePPMousePressed(evt);
            }
        });
        tablePP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablePPKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablePP);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        buttonGroup1.add(ProdutosTotais);
        ProdutosTotais.setSelected(true);
        ProdutosTotais.setText("Produtos totais");
        ProdutosTotais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutosTotaisActionPerformed(evt);
            }
        });

        buttonGroup1.add(ProdutosDisponiveis);
        ProdutosDisponiveis.setText("Produtos disponíveis");
        ProdutosDisponiveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutosDisponiveisActionPerformed(evt);
            }
        });

        buttonGroup1.add(ProdutosOff);
        ProdutosOff.setText("Produtos sem estoque");
        ProdutosOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutosOffActionPerformed(evt);
            }
        });

        btDetalhesVenda1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btDetalhesVenda1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionar-ficheiro_1.png"))); // NOI18N
        btDetalhesVenda1.setText("Cadastrar um produto igual");
        btDetalhesVenda1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDetalhesVenda1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(geraretq)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btDetalhesVenda1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btDetalhesVenda))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(ProdutosTotais)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProdutosDisponiveis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProdutosOff)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProdutosDisponiveis)
                    .addComponent(ProdutosOff)
                    .addComponent(ProdutosTotais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btDetalhesVenda)
                    .addComponent(geraretq)
                    .addComponent(btDetalhesVenda1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btDetalhesVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDetalhesVendaActionPerformed
//        // TODO add your handling code here:
        if (tablePP.getSelectedRow() >= 0) {
            PPDetails ppd = new PPDetails();
            ppd.setVisible(true);
            int setarNotes = tablePP.getSelectedRow();
            String getID = tablePP.getModel().getValueAt(setarNotes, 0).toString();
            idPP0001.setText(getID);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto!");
        }
    }//GEN-LAST:event_btDetalhesVendaActionPerformed

    private void geraretqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geraretqActionPerformed
        // TODO add your handling code here:
        printpp print = new printpp();
        print.setVisible(true);


    }//GEN-LAST:event_geraretqActionPerformed

    private void btAddqtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddqtdeActionPerformed
        // TODO add your handling code here:
        int getselect = tablePP.getSelectedRow();
        if (getselect >= 0) {
            int total;
            String qtdAtual = txtQtdAtual.getText();
            int qtda = Integer.parseInt(qtdAtual);
            int qtdnew = Integer.parseInt(spinnerNew.getValue().toString());
            total = qtda + qtdnew;

            String sql = "update partepecas set qtde=? where idpartepecas =?";
            conexao = ModuloConexao.conector();

            try {

                String descri = descriselected.getText();

                pst = conexao.prepareStatement(sql);
                pst.setInt(1, total);
                pst.setString(2, idselected.getText());

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    DesktopNotify.setDefaultTheme(NotifyTheme.Light);
                    DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + descri + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);
                    idselected.setText(null);
                    descriselected.setText(null);
                    txtQtdAtual.setText(null);
                    colocarDados();
                }

            } catch (Exception e) {
                System.out.println(e);

            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }


    }//GEN-LAST:event_btAddqtdeActionPerformed

    private void btRemoveQtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveQtdeActionPerformed
        // TODO add your handling code here:
        int getselect = tablePP.getSelectedRow();

        if (getselect >= 0) {

            int total;
            String qtdAtual = txtQtdAtual.getText();
            int qtda = Integer.parseInt(qtdAtual);
            int qtdnew = Integer.parseInt(spinnerNew.getValue().toString());
            total = qtda - qtdnew;

            String descri = descriselected.getText();

            if (qtdnew <= qtda) {

                String sql = "update partepecas set qtde=? where idpartepecas =?";
                conexao = ModuloConexao.conector();

                try {

                    pst = conexao.prepareStatement(sql);
                    pst.setInt(1, total);
                    pst.setString(2, idselected.getText());

                    int adicionado = pst.executeUpdate();

                    if (adicionado > 0) {
                        DesktopNotify.setDefaultTheme(NotifyTheme.Light);
                        DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + descri + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);
                        idselected.setText(null);
                        descriselected.setText(null);
                        txtQtdAtual.setText(null);
                        colocarDados();
                    }

                } catch (Exception e) {
                    System.out.println(e);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Quantidade que deseja retirar é maior que a existente!");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        }
    }//GEN-LAST:event_btRemoveQtdeActionPerformed

    private void txProcurarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyReleased
        // TODO add your handling code here:
        if (empresa.getText().equals("tanamao")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                switch (option) {
                    case 0:
                        pesquisar_EstoquePP("where empresa like 'tanamao' and (codigob like ? or ref like ? or nserie like ?)", text, text, text);
                        break;
                    case 1:
                        pesquisar_EstoquePP("where empresa like 'tanamao' and (descricao like ? or obs like ?)", text, text);
                        break;
                    case 2:
                        pesquisar_EstoquePP("where empresa like 'tanamao' and (grupo like ? or marca like ? or modelo like ?)", text, text, text);
                        break;
                    case 3:
                        pesquisar_EstoquePP("where empresa like 'tanamao' and (forne like ?)", text);
                        break;
                    default:
                        break;
                }
            }
        }
        if (empresa.getText().equals("docatec")) {
            if (txProcurar.isSelected()) {
                int option = txProcurar.getSelectedIndex();
                String text = "%" + txProcurar.getText() + "%";
                switch (option) {
                    case 0:
                        pesquisar_EstoquePP("where empresa like 'docatec' and (codigob like ? or ref like ? or nserie like ? or refclass like ? or refunica like ? or pn like ?)", text, text, text, text, text, text);
                        break;
                    case 1:
                        pesquisar_EstoquePP("where empresa like 'docatec' and (descricao like ? or obs like ?)", text, text);
                        break;
                    case 2:
                        pesquisar_EstoquePP("where empresa like 'docatec' and (grupo like ? or marca like ? or modelo like ? or subgrupo like ?)", text, text, text, text);
                        break;
                    case 3:
                        pesquisar_EstoquePP("where empresa like 'docatec' and (forne like ?)", text);
                        break;
                    default:
                        break;
                }
            }
        }
    }//GEN-LAST:event_txProcurarKeyReleased

    private void tablePPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePPMouseClicked
        // TODO add your handling code here:
        setarCamposTabela();
        if (evt.getButton() != MouseEvent.BUTTON1 || evt.getClickCount() != 2) {
        } else {
            btDetalhesVenda.doClick();
        }

    }//GEN-LAST:event_tablePPMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        dialogPrint.setSize(373, 274);
        dialogPrint.setLocation(this.getLocationOnScreen().x + this.getWidth(), this.getLocationOnScreen().y);
        dialogPrint.setVisible(true);
        addToPrint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (tablePrint.getRowCount() == 0) {
            geraretq.setEnabled(false);
        }
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dialogPrint.setVisible(false);
    }//GEN-LAST:event_formWindowClosing

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        if (tablePrint.getSelectedRow() >= 0) {

            modelo.remove(tablePrint.getSelectedRow());
            tablePrint.setModel(modelo);
            if (tablePrint.getRowCount() == 0) {
                geraretq.setEnabled(false);
            }
        } else {

        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        modelo.limpar();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Fila de impressão limpa com sucesso!");
        noti.showNotification();
        tablePrint.setModel(modelo);
        geraretq.setEnabled(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txProcurarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txProcurarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            dialogPrint.setSize(373, 274);
            dialogPrint.setLocation(this.getLocationOnScreen().x + this.getWidth(), this.getLocationOnScreen().y);
            dialogPrint.setVisible(true);
            addToPrint1Line();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            txProcurar.setSelectedIndex(1);
        }

        if (evt.getKeyCode() == KeyEvent.VK_F8) {
            modelo.limpar();
            Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Fila de impressão limpa com sucesso!");
            noti.showNotification();
            tablePrint.setModel(modelo);
            geraretq.setEnabled(false);
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tablePP.getRowCount() == 1) {
                PPDetails ppd = new PPDetails();
                ppd.setVisible(true);
                String getID = tablePP.getModel().getValueAt(0, 0).toString();
                idPP0001.setText(getID);
            } else {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum produto encontrado na base de dados!! :/");
                noti.showNotification();
            }
        }
    }//GEN-LAST:event_txProcurarKeyPressed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        modelo.limpar();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Fila de impressão limpa com sucesso!");
        noti.showNotification();
        tablePrint.setModel(modelo);
        geraretq.setEnabled(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void ProdutosTotaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutosTotaisActionPerformed
        // TODO add your handling code here:
        String emp = empresa.getText();
        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? order by descricao";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, emp);
            rs = pst.executeQuery();
            JTableHeader header = tablePP.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Preço");
            header.repaint();
            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ProdutosTotaisActionPerformed

    private void ProdutosDisponiveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutosDisponiveisActionPerformed
        // TODO add your handling code here:
        String emp = empresa.getText();
        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? AND qtde > '0' order by descricao";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, emp);
            rs = pst.executeQuery();
            JTableHeader header = tablePP.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Preço");
            header.repaint();
            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ProdutosDisponiveisActionPerformed

    private void ProdutosOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutosOffActionPerformed
        // TODO add your handling code here:

        String emp = empresa.getText();
        String sql = "select idpartepecas as ID,codigob as CodeBar,descricao as Descricao,refunica as REF,subgrupo as SubGrupo,marca as Marca,nserie as Serial,precovenda as Preco,precopromocao as promo,qtde as QTDE from partepecas where empresa = ? AND qtde = '0' order by descricao";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, emp);
            rs = pst.executeQuery();
            JTableHeader header = tablePP.getTableHeader();
            TableColumnModel columnModel = header.getColumnModel();
            tablePP.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn descricaoColumn = columnModel.getColumn(2);
            TableColumn codabarColumn = columnModel.getColumn(1);
            TableColumn precoCol = columnModel.getColumn(7);
            descricaoColumn.setHeaderValue("Descrição");
            codabarColumn.setHeaderValue("Código de barras");
            precoCol.setHeaderValue("Preço");
            header.repaint();
            tablePP.getColumnModel().getColumn(0).setMaxWidth(40);
            tablePP.getColumnModel().getColumn(1).setPreferredWidth(50);
            tablePP.getColumnModel().getColumn(7).setMaxWidth(80);
            tablePP.getColumnModel().getColumn(8).setMaxWidth(60);
            tablePP.getColumnModel().getColumn(9).setMaxWidth(60);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_ProdutosOffActionPerformed

    private void btDetalhesVenda1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDetalhesVenda1ActionPerformed
        // TODO add your handling code here:
        TelaCadPartePecas tlpp;
        try {
            tlpp = new TelaCadPartePecas();
            if (tlpp.isVisible()) {
                tlpp.setVisible(false);
            } else {
                tlpp.setVisible(true);
                setNewCadastro();
            }
        } catch (BadLocationException ex) {

        }
    }//GEN-LAST:event_btDetalhesVenda1ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    }//GEN-LAST:event_formKeyPressed

    private void tablePPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePPKeyPressed
    }//GEN-LAST:event_tablePPKeyPressed

    private void tablePPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePPMousePressed
        // TODO add your handling code here:
        if (evt.getButton() != MouseEvent.BUTTON1 || evt.getClickCount() != 2) {
        } else {
            btDetalhesVenda.doClick();
        }
    }//GEN-LAST:event_tablePPMousePressed

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
            java.util.logging.Logger.getLogger(TelaEstoquePP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEstoquePP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEstoquePP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEstoquePP.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaEstoquePP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ProdutosDisponiveis;
    private javax.swing.JRadioButton ProdutosOff;
    private javax.swing.JRadioButton ProdutosTotais;
    private javax.swing.JButton btAddqtde;
    private javax.swing.JButton btDetalhesVenda;
    private javax.swing.JButton btDetalhesVenda1;
    private javax.swing.JButton btRemoveQtde;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField descriselected;
    public static javax.swing.JDialog dialogPrint;
    private javax.swing.JButton geraretq;
    private javax.swing.JTextField idselected;
    private javax.swing.JScrollPane itensPrint;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelQtd;
    private javax.swing.JSpinner spinnerNew;
    public static javax.swing.JTable tablePP;
    public static javax.swing.JTable tablePrint;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    public static textFieldSearch.TextFieldSearchOption txProcurar;
    public static javax.swing.JTextField txtQtdAtual;
    // End of variables declaration//GEN-END:variables
}
