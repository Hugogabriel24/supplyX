package CadastroProdutos;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import jnafilechooser.api.JnaFileChooser;
import notification.Notification;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import static pages.TelaPrincipal.empresa;
import static pages.TelaPrincipal.perfil;
import static pages.TelaPrincipal.selectProduct;
import pagesSearchs.SearchNotebooks;

public final class TelaCadNotebook extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public static File imagemA;
    public static File imagemB;
    public static File imagemC;

    private long lastPressTime = 0;

    public TelaCadNotebook() {
        initComponents();
        uper();
        setIcon();
        setColorFromEmpresa();
        enablesandvisibles();
        conexao = ModuloConexao.conector();
        putUImanager();
        this.scrollNote.getVerticalScrollBar().setUnitIncrement(20);
        this.scroImg.getVerticalScrollBar().setUnitIncrement(20);
        setcodabar();
        ListarRefGeral();
        formatarpreco();
        ListarMarcasNotebook();
        ListarLocais();
        ListarGrupos();
        ListarForne();
        GetModelsNotebook();
        GetSubGrupos();
        SetDate();
        verifUser();
        GetRefClass();
        GetPrats();
        getDescriGeral();

        spQtde.setModel(new SpinnerNumberModel(1, 0, 99999, 1));
        spQtdEmb.setModel(new SpinnerNumberModel(1, 0, 99999, 1));
    }

    public void putUImanager() {
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        UIManager.put("ProgressBar.arc", 0);
        UIManager.put("TextComponent.arc", 0);
        UIManager.put("Component.arrowType", "chevron");
    }

    public void enablesandvisibles() {
        descricaoncm.setVisible(false);
        barra1.setVisible(false);
        barra2.setVisible(false);
        panel2arm.setVisible(false);
        cbArmazena1.setVisible(false);
        cbCapArmazena1.setVisible(false);
        cbArmazena1.setSelectedItem("--");
        cbCapArmazena1.setSelectedItem("--");
        AlterImage1.setVisible(false);
        AlterImage2.setVisible(false);
        AlterImage3.setVisible(false);
        habilitar1PP.setVisible(false);
        habilitar2PP.setVisible(false);
        habilitar3PP.setVisible(false);
        cbArmazena1.setSelectedItem("--");
        cbCapArmazena1.setSelectedItem("--");
        barra1.setVisible(false);
        cbCapArmazena1.setVisible(false);
        panel2arm.setVisible(false);
        cbArmazena1.setVisible(false);
        barra2.setVisible(false);
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
        FormFiscais.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setcodabar() {
        String codigo;
        try {
            do {
                codigo = gerarCodigoEAN13();
            } while (verificarCodigoExistente(codigo));
            codeBarNotebook.setText(codigo);
        } catch (Exception e) {
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

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel2.setBackground(new Color(15, 102, 122));
        } else {
            jPanel2.setBackground(new Color(147, 167, 39));
        }
    }

    public void SetDate() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String date = formatador.format(data);
        txtDataC.setText(date);
    }

    public void uper() {
        descricaoNotebook.setDocument(new UpperCase());
        codAnuncioOlx.setDocument(new UpperCase());
        codAnuncioOlx1.setDocument(new UpperCase());
        codAnuncioML.setDocument(new UpperCase());
        codAnuncioOlx2.setDocument(new UpperCase());
        codAnuncioFaceInterno.setDocument(new UpperCase());
        codAnuncioFace.setDocument(new UpperCase());
        codAnuncioEcoInterno.setDocument(new UpperCase());
        codAnuncioEco.setDocument(new UpperCase());
        skuForne.setDocument(new UpperCase());
        txModeloVideoOff.setDocument(new UpperCase());
        txtSerial.setDocument(new UpperCase());
        partnumber.setDocument(new UpperCase());
        txtrefUnica.setDocument(new UpperCase());
        txModeloProcess.setDocument(new UpperCase());
        txModeloVideoOnboard.setDocument(new UpperCase());
        serialMemoria.setDocument(new UpperCase());
        serialhd.setDocument(new UpperCase());
        nEntradas.setDocument(new UpperCase());
        nObs.setDocument(new UpperCase());
    }

    public void verifUser() {
        if (perfil.getText().equals("user")) {
            txtPrecoCusto.setVisible(false);
            txcusto.setVisible(false);
            txtPrecoMin.setEnabled(false);
            btSearch.setEnabled(false);
        }
    }

    //MARCAS E MODELOS
    public void ListarMarcasNotebook() {
        String sql = "select marcanome from marca order by marcanome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbMarca.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void GetModelsNotebook() {
        String sql = "select model from modelos where marcan like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbMarca.getSelectedItem().toString());

            rs = pst.executeQuery();

            while (rs.next()) {
                String grupo = rs.getString(1);
                cbModelo.addItem(grupo);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    //GRUPOS E SUBGRUPOS
    public void ListarGrupos() {
        String sql = "select nomeg from grupo order by nomeg";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                grupoNotebook.addItem(nome);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void GetSubGrupos() {
        String sql = "select psubgrupo from subgrupo where pgrupo like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, grupoNotebook.getSelectedItem().toString());

            rs = pst.executeQuery();

            while (rs.next()) {
                String subgrupo = rs.getString(1);
                subgrupoNotebook.addItem(subgrupo);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    //LOCAIS E PRATELEIRAS
    public void ListarLocais() {
        String sql = "select local from localestoque order by local";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                nLocal.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void GetPrats() {
        String sql = "select prateleira from prateleiras where local like ?";
        String getLocal = nLocal.getSelectedItem().toString();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getLocal);

            rs = pst.executeQuery();

            while (rs.next()) {
                String prateleira = rs.getString(1);
                nPrateleira.addItem(prateleira);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    //REFS E DESCRIS
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

    public void ListarRefGeral() {
        String sql = "select ref from ref order by ref";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbRefGeral.addItem(nome);

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

    public void setRefunica() {
        String sql = "select max(idpartepecas)idpartepecas from partepecas";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String idMax = rs.getString(1);
                String getclass = cbRefClass.getSelectedItem().toString();
                txtrefUnica.setText(getclass + "-0" + idMax + "");
                getDescriClass();

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
                descri1.setHorizontalAlignment(SwingConstants.CENTER);
                descri1.setVerticalAlignment(SwingConstants.CENTER);
                descri1.setText("<html>" + descri + "<html>");

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
                descri2.setHorizontalAlignment(SwingConstants.CENTER);
                descri2.setVerticalAlignment(SwingConstants.CENTER);
                descri2.setText("<html>" + descri + "<html>");
                if (descri2.getText().length() > 50) {

                }

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    //LISTAGEM FORNECEDOR
    public void ListarForne() {
        String sql = "select nome from fornecedores order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                nForne.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    // LISTAR PROCESSADOR
    public void ListarProcess() {
        String sql = "select process from processador order by process";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbProcess.addItem(nome);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        txtPrecoCusto.setFormatterFactory(dfFactory);
        txtPrecoMin.setFormatterFactory(dfFactory);
        txtPrecoVenda.setFormatterFactory(dfFactory);
    }

    //TRATAMENTOS DE IMAGENS
    private void abrirImagem(String imagePath, int index) {

        switch (index) {
            case 1:
                ImageIcon imagemIcon1 = new ImageIcon(imagePath);
                imagemIcon1.setImage(imagemIcon1.getImage().getScaledInstance(Painel1ImagePP.getWidth(), Painel1ImagePP.getHeight(), 100));
                viewImage1PP.setIcon(imagemIcon1);
                break;
            case 2:
                ImageIcon imagemIcon2 = new ImageIcon(imagePath);
                imagemIcon2.setImage(imagemIcon2.getImage().getScaledInstance(Painel2ImagePP.getWidth(), Painel2ImagePP.getHeight(), 100));
                viewImage2PP.setIcon(imagemIcon2);
                break;
            case 3:
                ImageIcon imagemIcon3 = new ImageIcon(imagePath);
                imagemIcon3.setImage(imagemIcon3.getImage().getScaledInstance(Painel3ImagePP.getWidth(), Painel3ImagePP.getHeight(), 100));
                viewImage3PP.setIcon(imagemIcon3);
                break;
            default:
                // Faça algo caso o índice seja inválido
                break;
        }
    }

    private void abrirImagemWebp(BufferedImage imagePath, int index) {

        switch (index) {
            case 1:
                ImageIcon imagemIcon1 = new ImageIcon(imagePath);
                imagemIcon1.setImage(imagemIcon1.getImage().getScaledInstance(Painel1ImagePP.getWidth(), Painel1ImagePP.getHeight(), 100));
                viewImage1PP.setIcon(imagemIcon1);
                break;
            case 2:
                ImageIcon imagemIcon2 = new ImageIcon(imagePath);
                imagemIcon2.setImage(imagemIcon2.getImage().getScaledInstance(Painel2ImagePP.getWidth(), Painel2ImagePP.getHeight(), 100));
                viewImage2PP.setIcon(imagemIcon2);
                break;
            case 3:
                ImageIcon imagemIcon3 = new ImageIcon(imagePath);
                imagemIcon3.setImage(imagemIcon3.getImage().getScaledInstance(Painel3ImagePP.getWidth(), Painel3ImagePP.getHeight(), 100));
                viewImage3PP.setIcon(imagemIcon3);
                break;
            default:
                // Faça algo caso o índice seja inválido
                break;
        }
    }

    private byte[] getImagem1() {
        boolean isPng = false;

        if (imagemA != null) {
            isPng = imagemA.getName().endsWith("png");

            try {

                BufferedImage image = ImageIO.read(imagemA);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int type = BufferedImage.TYPE_INT_RGB;

                if (isPng) {
                    type = BufferedImage.BITMASK;
                }

                BufferedImage novaImagem = new BufferedImage(Painel1ImagePP.getWidth() + 100, Painel1ImagePP.getHeight() + 100, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel1ImagePP.getWidth() + 100, Painel1ImagePP.getHeight() + 100, null);

                if (isPng) {
                    ImageIO.write(novaImagem, "png", out);
                } else {
                    ImageIO.write(novaImagem, "jpg", out);
                }

                out.flush();
                byte[] byteArray = out.toByteArray();
                out.close();

                return byteArray;

            } catch (IOException e) {

            }

        }
        return null;
    }

    private byte[] getImagem2() {
        boolean isPng = false;

        if (imagemB != null) {
            isPng = imagemB.getName().endsWith("png");

            try {

                BufferedImage image = ImageIO.read(imagemB);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int type = BufferedImage.TYPE_INT_RGB;

                if (isPng) {
                    type = BufferedImage.BITMASK;
                }

                BufferedImage novaImagem = new BufferedImage(Painel2ImagePP.getWidth() + 100, Painel2ImagePP.getHeight() + 100, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel2ImagePP.getWidth() + 100, Painel2ImagePP.getHeight() + 100, null);

                if (isPng) {
                    ImageIO.write(novaImagem, "png", out);
                } else {
                    ImageIO.write(novaImagem, "jpg", out);
                }

                out.flush();
                byte[] byteArray = out.toByteArray();
                out.close();

                return byteArray;

            } catch (IOException e) {

            }

        }
        return null;
    }

    private byte[] getImagem3() {
        boolean isPng = false;

        if (imagemC != null) {
            isPng = imagemC.getName().endsWith("png");

            try {

                BufferedImage image = ImageIO.read(imagemC);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int type = BufferedImage.TYPE_INT_RGB;

                if (isPng) {
                    type = BufferedImage.BITMASK;
                }

                BufferedImage novaImagem = new BufferedImage(Painel3ImagePP.getWidth() + 100, Painel3ImagePP.getHeight() + 100, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel3ImagePP.getWidth() + 100, Painel3ImagePP.getHeight() + 100, null);

                if (isPng) {
                    ImageIO.write(novaImagem, "png", out);
                } else {
                    ImageIO.write(novaImagem, "jpg", out);
                }

                out.flush();
                byte[] byteArray = out.toByteArray();
                out.close();

                return byteArray;

            } catch (IOException e) {

            }

        }
        return null;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        } else {
            return "";
        }
    }

    //CRUD
    public void adicionar() {

        String sql = "insert into notebooks(codebar,descricao,grupo,subgrupo,pn,marca,modelo,nserial,ref,refclass,refunica,memoria,tecm,velocidadem,maxm,slots,seriememoria,processador,modelop,nucleosp,velocidadep,placaonboard,modeloonboard,tipomemoriaonboard,memoriaonboard,placaoffboard,modelooffboard,tipomemoriaoffboard,memoriaoffboard,duploarmazenamento,armazenamento1,capacidade1,armazenamento2,capacidade2,seriesarmazenamento,resolucaotela,taxaatualizacao,tamanhotela,tiporesolucao,tipotela,carregador,potencia,bateria,horas,cornotebook,sistemaop,leitor,bluet,tecladort,garantiafb,validadegarantiafb,usb2,usb3,entradas,precocusto,precomin,precovenda,precopromocao,qtde,undm,qtdeembalagem,localestoque,prateleira,status,fornecedor,skufornecedor,obs,datacadastro,empresa,olx,internoolx,externoolx,ml,internoml,externoml,fb,internofb,externofb,ec,internoec,externoec,img1,img2,img3,ncm,csticms,icms,cstpis,pis,cstcofins,cofins,cstipi,ipi,origem,motivodes,substituicaotb,reducaotb) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //98

        try {

            pst = conexao.prepareStatement(sql);

            //DADOS GERAIS
            pst.setString(1, codeBarNotebook.getText());
            pst.setString(2, descricaoNotebook.getText());
            pst.setString(3, grupoNotebook.getSelectedItem().toString());
            pst.setString(4, subgrupoNotebook.getSelectedItem().toString());
            pst.setString(5, partnumber.getText());
            pst.setString(6, cbMarca.getSelectedItem().toString());
            pst.setString(7, cbModelo.getSelectedItem().toString());
            pst.setString(8, txtSerial.getText());
            pst.setString(9, cbRefGeral.getSelectedItem().toString());
            pst.setString(10, cbRefClass.getSelectedItem().toString());
            pst.setString(11, txtrefUnica.getText());
            //MEMORIA
            pst.setString(12, nMemoria.getSelectedItem().toString());
            pst.setString(13, nTecM.getSelectedItem().toString());
            pst.setString(14, nVeloM.getSelectedItem().toString());
            pst.setString(15, capmemoriaNote.getSelectedItem().toString());
            pst.setInt(16, (int) slotsmemoria.getValue());
            pst.setString(17, serialMemoria.getText());
            //PROCESSADOR
            pst.setString(18, cbProcess.getSelectedItem().toString());
            pst.setString(19, txModeloProcess.getText());
            pst.setString(20, nucleosProcess.getSelectedItem().toString());
            pst.setString(21, VelocidadeProcess.getSelectedItem().toString());
            //PLACA DE VIDEO ONBOARD
            pst.setString(22, cbPlacaVideoOnboard.getSelectedItem().toString());
            pst.setString(23, txModeloVideoOnboard.getText());
            pst.setString(24, cbTipoRamVideoDesktop.getSelectedItem().toString());
            pst.setString(25, cbRamVideoDesktop.getSelectedItem().toString());
            //PLACA DE VIDEO OFFBOARD
            pst.setString(26, cbPlacaVideoOff.getSelectedItem().toString());
            pst.setString(27, txModeloVideoOff.getText());
            pst.setString(28, cbTipoRamVideoOff.getSelectedItem().toString());
            pst.setString(29, cbRamVideoOff.getSelectedItem().toString());
            //ARMAZENAMENTO
            pst.setString(30, duploArma.getSelectedItem().toString());
            pst.setString(31, cbArmazena.getSelectedItem().toString());
            pst.setString(32, cbCapArmazena.getSelectedItem().toString());
            pst.setString(33, cbArmazena1.getSelectedItem().toString());
            pst.setString(34, cbCapArmazena1.getSelectedItem().toString());
            pst.setString(35, serialhd.getText());
            //TELA
            pst.setString(36, cbReso.getSelectedItem().toString());
            pst.setString(37, cbTaxa.getSelectedItem().toString());
            pst.setString(38, cbTamanho.getSelectedItem().toString());
            pst.setString(39, cbTamanho2.getSelectedItem().toString());
            pst.setString(40, cbTamanho3.getSelectedItem().toString());
            //BATERIA / CARREGADOR
            pst.setString(41, cbReso1.getSelectedItem().toString());
            pst.setString(42, cbReso4.getSelectedItem().toString());
            pst.setString(43, cbReso5.getSelectedItem().toString());
            pst.setString(44, cbReso6.getSelectedItem().toString());
            //OUTROS
            pst.setString(45, CorNotebook.getSelectedItem().toString());
            pst.setString(46, cbLeitor2.getSelectedItem().toString());
            pst.setString(47, cbLeitor.getSelectedItem().toString());
            pst.setString(48, cbBlue.getSelectedItem().toString());
            pst.setString(49, cbTeclado.getSelectedItem().toString());
            pst.setString(50, cbGarantia.getSelectedItem().toString());
            if (cbGarantia.getSelectedItem().equals("NAO")) {
                pst.setString(51, null);
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                String dateValidade = formato.format(DataGarantiaFabrica.getDate());
                pst.setString(51, dateValidade);
            }
            pst.setInt(52, (int) spUsb2.getValue());
            pst.setInt(53, (int) spUsb3.getValue());
            pst.setString(54, nEntradas.getText());
            //VALORES
            pst.setString(55, txtPrecoCusto.getText());
            pst.setString(56, txtPrecoMin.getText());
            pst.setString(57, txtPrecoVenda.getText());
            pst.setString(58, precoPromo.getText());
            //ESTOQUE
            pst.setInt(59, (int) spQtde.getValue());
            pst.setString(60, nMedida.getSelectedItem().toString());
            pst.setInt(61, (int) spQtdEmb.getValue());
            pst.setString(62, nLocal.getSelectedItem().toString());
            pst.setString(63, nPrateleira.getSelectedItem().toString());
            //INFORS
            pst.setString(64, nStatus.getSelectedItem().toString());
            pst.setString(65, nForne.getSelectedItem().toString());
            pst.setString(66, skuForne.getText());
            pst.setString(67, nObs.getText());
            pst.setString(68, txtDataC.getText());
            pst.setString(69, empresa.getText());
            //ANUNCIOS
            pst.setString(70, anuncioOlx.getSelectedItem().toString());
            pst.setString(71, codAnuncioOlx.getText());
            pst.setString(72, codAnuncioOlx1.getText());
            pst.setString(73, anuncioOlx1.getSelectedItem().toString());
            pst.setString(74, codAnuncioML.getText());
            pst.setString(75, codAnuncioOlx2.getText());
            pst.setString(76, anuncioFace.getSelectedItem().toString());
            pst.setString(77, codAnuncioFaceInterno.getText());
            pst.setString(78, codAnuncioFace.getText());
            pst.setString(79, anuncioEco.getSelectedItem().toString());
            pst.setString(80, codAnuncioEcoInterno.getText());
            pst.setString(81, codAnuncioEco.getText());
            //IMAGENS
            pst.setBytes(82, getImagem1());
            pst.setBytes(83, getImagem2());
            pst.setBytes(84, getImagem3());
            //DADOS FISCAIS
            pst.setString(85, txtNcm.getSelectedItem().toString());
            pst.setString(86, cbIcms.getSelectedItem().toString());
            pst.setString(87, txIcms.getText());
            pst.setString(88, cbCstPis.getSelectedItem().toString());
            pst.setString(89, Pis.getText());
            pst.setString(90, CstConfis.getSelectedItem().toString());
            pst.setString(91, txCofis.getText());
            pst.setString(92, cbCstIpi.getSelectedItem().toString());
            pst.setString(93, txIpi.getText());
            pst.setString(94, origem.getSelectedItem().toString());
            pst.setString(95, txMotivoD.getSelectedItem().toString());
            pst.setString(96, txSubst.getText());
            pst.setString(97, txReducao.getText());

            if ((descricaoNotebook.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {

                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Notebook cadastrado com sucesso!");
                    noti.showNotification();
                    setcodabar();
                    setRefunica();
                    //addLogCad();
                    txtSerial.setText(null);
                    txtSerial.requestFocus();
                }

            }

        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao cadastrar o Notebook.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    private void alterar() {
        String sql = "update notebooks set descricao=?, grupo=?, subgrupo=?, pn=?, marca=?, modelo=?, nserial=?, ref=?, refclass=?, refunica=?, memoria=?, tecm=?, velocidadem=?, maxm=?, slots=?, seriememoria=?, processador=?, modelop=?, nucleosp=?, velocidadep=?, placaonboard=?, modeloonboard=?, tipomemoriaonboard=?, memoriaonboard=?, placaoffboard=?, modelooffboard=?, tipomemoriaoffboard=?, memoriaoffboard=?, duploarmazenamento=?, armazenamento1=?, capacidade1=?, armazenamento2=?, capacidade2=?, seriesarmazenamento=?, resolucaotela=?, taxaatualizacao=?, tamanhotela=?, tiporesolucao=?, tipotela=?, carregador=?, potencia=?, bateria=?, horas=?, cornotebook=?, sistemaop=?, leitor=?, bluet=?, tecladort=?, garantiafb=?, validadegarantiafb=?, usb2=?, usb3=?, entradas=?, precocusto=?, precomin=?, precovenda=?, precopromocao=?, qtde=?, undm=?, qtdeembalagem=?, localestoque=?, prateleira=?, status=?, fornecedor=?, skufornecedor=?, obs=?, olx=?, internoolx=?, externoolx=?, ml=?, internoml=?, externoml=?, fb=?, internofb=?, externofb=?, ec=?, internoec=?, externoec=?, ncm=?, csticms=?, icms=?, cstpis=?, pis=?, cstcofins=?, cofins=?, cstipi=?, ipi=?, origem=?, motivodes=?, substituicaotb=?, reducaotb=? where idnotebooks=?";
        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, descricaoNotebook.getText());
            pst.setString(2, grupoNotebook.getSelectedItem().toString());
            pst.setString(3, subgrupoNotebook.getSelectedItem().toString());
            pst.setString(4, partnumber.getText());
            pst.setString(5, cbMarca.getSelectedItem().toString());
            pst.setString(6, cbModelo.getSelectedItem().toString());
            pst.setString(7, txtSerial.getText());
            pst.setString(8, cbRefGeral.getSelectedItem().toString());
            pst.setString(9, cbRefClass.getSelectedItem().toString());
            pst.setString(10, txtrefUnica.getText());
            //MEMORIA
            pst.setString(11, nMemoria.getSelectedItem().toString());
            pst.setString(12, nTecM.getSelectedItem().toString());
            pst.setString(13, nVeloM.getSelectedItem().toString());
            pst.setString(14, capmemoriaNote.getSelectedItem().toString());
            pst.setInt(15, (int) slotsmemoria.getValue());
            pst.setString(16, serialMemoria.getText());
            //PROCESSADOR
            pst.setString(17, cbProcess.getSelectedItem().toString());
            pst.setString(18, txModeloProcess.getText());
            pst.setString(19, nucleosProcess.getSelectedItem().toString());
            pst.setString(20, VelocidadeProcess.getSelectedItem().toString());
            //PLACA DE VIDEO ONBOARD
            pst.setString(21, cbPlacaVideoOnboard.getSelectedItem().toString());
            pst.setString(22, txModeloVideoOnboard.getText());
            pst.setString(23, cbTipoRamVideoDesktop.getSelectedItem().toString());
            pst.setString(24, cbRamVideoDesktop.getSelectedItem().toString());
            //PLACA DE VIDEO OFFBOARD
            pst.setString(25, cbPlacaVideoOff.getSelectedItem().toString());
            pst.setString(26, txModeloVideoOff.getText());
            pst.setString(27, cbTipoRamVideoOff.getSelectedItem().toString());
            pst.setString(28, cbRamVideoOff.getSelectedItem().toString());
            //ARMAZENAMENTO
            pst.setString(29, duploArma.getSelectedItem().toString());
            pst.setString(30, cbArmazena.getSelectedItem().toString());
            pst.setString(31, cbCapArmazena.getSelectedItem().toString());
            pst.setString(32, cbArmazena1.getSelectedItem().toString());
            pst.setString(33, cbCapArmazena1.getSelectedItem().toString());
            pst.setString(34, serialhd.getText());
            //TELA
            pst.setString(35, cbReso.getSelectedItem().toString());
            pst.setString(36, cbTaxa.getSelectedItem().toString());
            pst.setString(37, cbTamanho.getSelectedItem().toString());
            pst.setString(38, cbTamanho2.getSelectedItem().toString());
            pst.setString(39, cbTamanho3.getSelectedItem().toString());
            //BATERIA / CARREGADOR
            pst.setString(40, cbReso1.getSelectedItem().toString());
            pst.setString(41, cbReso4.getSelectedItem().toString());
            pst.setString(42, cbReso5.getSelectedItem().toString());
            pst.setString(43, cbReso6.getSelectedItem().toString());
            //OUTROS
            pst.setString(44, CorNotebook.getSelectedItem().toString());
            pst.setString(45, cbLeitor2.getSelectedItem().toString());
            pst.setString(46, cbLeitor.getSelectedItem().toString());
            pst.setString(47, cbBlue.getSelectedItem().toString());
            pst.setString(48, cbTeclado.getSelectedItem().toString());
            pst.setString(49, cbGarantia.getSelectedItem().toString());
            if (cbGarantia.getSelectedItem().equals("NAO")) {
                pst.setString(50, null);
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                String dateValidade = formato.format(DataGarantiaFabrica.getDate());
                pst.setString(50, dateValidade);
            }
            pst.setInt(51, (int) spUsb2.getValue());
            pst.setInt(52, (int) spUsb3.getValue());
            pst.setString(53, nEntradas.getText());
            //VALORES
            pst.setString(54, txtPrecoCusto.getText());
            pst.setString(55, txtPrecoMin.getText());
            pst.setString(56, txtPrecoVenda.getText());
            pst.setString(57, precoPromo.getText());
            //ESTOQUE
            pst.setInt(58, (int) spQtde.getValue());
            pst.setString(59, nMedida.getSelectedItem().toString());
            pst.setInt(60, (int) spQtdEmb.getValue());
            pst.setString(61, nLocal.getSelectedItem().toString());
            pst.setString(62, nPrateleira.getSelectedItem().toString());
            //INFORS
            pst.setString(63, nStatus.getSelectedItem().toString());
            pst.setString(64, nForne.getSelectedItem().toString());
            pst.setString(65, skuForne.getText());
            pst.setString(66, nObs.getText());
            //ANUNCIOS
            pst.setString(67, anuncioOlx.getSelectedItem().toString());
            pst.setString(68, codAnuncioOlx.getText());
            pst.setString(69, codAnuncioOlx1.getText());
            pst.setString(70, anuncioOlx1.getSelectedItem().toString());
            pst.setString(71, codAnuncioML.getText());
            pst.setString(72, codAnuncioOlx2.getText());
            pst.setString(73, anuncioFace.getSelectedItem().toString());
            pst.setString(74, codAnuncioFaceInterno.getText());
            pst.setString(75, codAnuncioFace.getText());
            pst.setString(76, anuncioEco.getSelectedItem().toString());
            pst.setString(77, codAnuncioEcoInterno.getText());
            pst.setString(78, codAnuncioEco.getText());
            //DADOS FISCAIS
            pst.setString(79, txtNcm.getSelectedItem().toString());
            pst.setString(80, cbIcms.getSelectedItem().toString());
            pst.setString(81, txIcms.getText());
            pst.setString(82, cbCstPis.getSelectedItem().toString());
            pst.setString(83, Pis.getText());
            pst.setString(84, CstConfis.getSelectedItem().toString());
            pst.setString(85, txCofis.getText());
            pst.setString(86, cbCstIpi.getSelectedItem().toString());
            pst.setString(87, txIpi.getText());
            pst.setString(88, origem.getSelectedItem().toString());
            pst.setString(89, txMotivoD.getSelectedItem().toString());
            pst.setString(90, txSubst.getText());
            pst.setString(91, txReducao.getText());
            pst.setString(92, nID.getText());

            if ((nID.getText().isEmpty()) || (descricaoNotebook.getText().isEmpty()) || (codeBarNotebook.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados do notebook alterados com sucesso!");
                    noti.showNotification();
                    //addLogEdit();

                }
            }
        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar os dados do notebook.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este Notebook?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from notebooks where idnotebooks=?";
            try {

                if ((nID.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum notebook selecionado!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, nID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Notebook removido com sucesso!");
                    noti.showNotification();
                    //addLogRemove();

                }

            } catch (HeadlessException | SQLException e) {

                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover o notebook.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }

    }
//

    public void refresh() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar os campos?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            try {

                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!");
                noti.showNotification();

                Color colorB = new Color(255, 255, 255);
                btCad.setBackground(colorB);
                btEdit.setEnabled(false);
                btExcluir.setEnabled(false);
                btSearch.setEnabled(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nID = new javax.swing.JTextField();
        FormFiscais = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel94 = new javax.swing.JLabel();
        cbIcms = new javax.swing.JComboBox<>();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        cbCstPis = new javax.swing.JComboBox<>();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        cbCstIpi = new javax.swing.JComboBox<>();
        jLabel101 = new javax.swing.JLabel();
        CstConfis = new javax.swing.JComboBox<>();
        txtNcm = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        txIcms = new javax.swing.JFormattedTextField();
        Pis = new javax.swing.JFormattedTextField();
        txCofis = new javax.swing.JFormattedTextField();
        txIpi = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel102 = new javax.swing.JLabel();
        txReducao = new javax.swing.JTextField();
        txMotivoD = new javax.swing.JComboBox<>();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        txSubst = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        origem = new javax.swing.JComboBox<>();
        descricaoncm = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        Background = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        scrollNote = new javax.swing.JScrollPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        descricaoNotebook = new javax.swing.JTextField();
        grupoNotebook = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        subgrupoNotebook = new javax.swing.JComboBox<>();
        cbMarca = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbModelo = new javax.swing.JComboBox<>();
        txtSerial = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        partnumber = new javax.swing.JTextField();
        cbRefGeral = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        cbRefClass = new javax.swing.JComboBox<>();
        descri2 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txtrefUnica = new javax.swing.JTextField();
        descri1 = new javax.swing.JLabel();
        codeBarNotebook = new javax.swing.JFormattedTextField();
        jButton6 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel108 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        nMemoria = new javax.swing.JComboBox<>();
        nTecM = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        nVeloM = new javax.swing.JComboBox<>();
        capmemoriaNote = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        slotsmemoria = new javax.swing.JSpinner();
        serialMemoria = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cbProcess = new javax.swing.JComboBox<>();
        txModeloProcess = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nucleosProcess = new javax.swing.JComboBox<>();
        VelocidadeProcess = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        cbRamVideoDesktop = new javax.swing.JComboBox<>();
        txVideo = new javax.swing.JLabel();
        txTipo = new javax.swing.JLabel();
        cbTipoRamVideoDesktop = new javax.swing.JComboBox<>();
        txModeloVideoOnboard = new javax.swing.JTextField();
        txModelo = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cbPlacaVideoOnboard = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        cbRamVideoOff = new javax.swing.JComboBox<>();
        txVideo1 = new javax.swing.JLabel();
        txTipo1 = new javax.swing.JLabel();
        cbTipoRamVideoOff = new javax.swing.JComboBox<>();
        txModeloVideoOff = new javax.swing.JTextField();
        txModelo1 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        cbPlacaVideoOff = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        duploArma = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        barra2 = new javax.swing.JLabel();
        barra1 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        serialhd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbArmazena = new javax.swing.JComboBox<>();
        cbCapArmazena = new javax.swing.JComboBox<>();
        panel2arm = new javax.swing.JPanel();
        cbCapArmazena1 = new javax.swing.JComboBox<>();
        cbArmazena1 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        cbReso = new javax.swing.JComboBox<>();
        cbTaxa = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        cbTamanho = new javax.swing.JComboBox<>();
        cbTamanho2 = new javax.swing.JComboBox<>();
        jLabel87 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        cbTamanho3 = new javax.swing.JComboBox<>();
        jLabel106 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        cbReso1 = new javax.swing.JComboBox<>();
        jLabel91 = new javax.swing.JLabel();
        cbReso4 = new javax.swing.JComboBox<>();
        jLabel92 = new javax.swing.JLabel();
        cbReso5 = new javax.swing.JComboBox<>();
        jLabel93 = new javax.swing.JLabel();
        cbReso6 = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel107 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        nEntradas = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        spUsb3 = new javax.swing.JSpinner();
        spUsb2 = new javax.swing.JSpinner();
        cbGarantia = new javax.swing.JComboBox<>();
        cbTeclado = new javax.swing.JComboBox<>();
        cbBlue = new javax.swing.JComboBox<>();
        cbLeitor = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        CorNotebook = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        cbLeitor2 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        DataGarantiaFabrica = new com.toedter.calendar.JDateChooser();
        PainelComplementos = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txcusto = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtPrecoCusto = new javax.swing.JFormattedTextField();
        txtPrecoMin = new javax.swing.JFormattedTextField();
        txtPrecoVenda = new javax.swing.JFormattedTextField();
        radioPromo = new javax.swing.JRadioButton();
        jLabel75 = new javax.swing.JLabel();
        precoPromo = new javax.swing.JFormattedTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        spQtde = new javax.swing.JSpinner();
        nMedida = new javax.swing.JComboBox<>();
        spQtdEmb = new javax.swing.JSpinner();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        nLocal = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        nPrateleira = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        nObs = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        nStatus = new javax.swing.JComboBox<>();
        nForne = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        txtDataC = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        skuForne = new javax.swing.JTextField();
        openFiscais = new javax.swing.JToggleButton();
        PainelDadosAnuncioNotebook = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        anuncioOlx = new javax.swing.JComboBox<>();
        jLabel69 = new javax.swing.JLabel();
        codAnuncioOlx = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel70 = new javax.swing.JLabel();
        codAnuncioOlx1 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        codAnuncioOlx2 = new javax.swing.JTextField();
        codAnuncioML = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        anuncioOlx1 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        anuncioFace = new javax.swing.JComboBox<>();
        codAnuncioFaceInterno = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        codAnuncioFace = new javax.swing.JTextField();
        gerarCodFace = new javax.swing.JButton();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        anuncioEco = new javax.swing.JComboBox<>();
        codAnuncioEcoInterno = new javax.swing.JTextField();
        codAnuncioEco = new javax.swing.JTextField();
        gerarCodEco = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        scroImg = new javax.swing.JScrollPane();
        panelImgNotebook = new javax.swing.JPanel();
        uploadimg1 = new javax.swing.JButton();
        Cancelimg1 = new javax.swing.JButton();
        Painel1ImagePP = new javax.swing.JPanel();
        viewImage1PP = new javax.swing.JLabel();
        Painel2ImagePP = new javax.swing.JPanel();
        viewImage2PP = new javax.swing.JLabel();
        Cancelimg2 = new javax.swing.JButton();
        Cancelimg3 = new javax.swing.JButton();
        Painel3ImagePP = new javax.swing.JPanel();
        viewImage3PP = new javax.swing.JLabel();
        habilitar1PP = new javax.swing.JCheckBox();
        AlterImage1 = new javax.swing.JButton();
        AlterImage2 = new javax.swing.JButton();
        habilitar2PP = new javax.swing.JCheckBox();
        habilitar3PP = new javax.swing.JCheckBox();
        AlterImage3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btCad = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSearch1 = new javax.swing.JButton();

        nID.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        nID.setEnabled(false);

        FormFiscais.setTitle("Inserir dados fiscais do produto");
        FormFiscais.setResizable(false);
        FormFiscais.setSize(new java.awt.Dimension(915, 560));
        FormFiscais.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                FormFiscaisWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                FormFiscaisWindowClosing(evt);
            }
        });

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(915, 520));

        jLabel74.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel74.setText("NCM*");

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton3.setText("Fechar e manter dados");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel94.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel94.setText("CST ICMS");

        cbIcms.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbIcms.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "0 – Nacional, exceto as indicadas nos códigos 3, 4, 5 e 8;", "1 – Estrangeira – Importação direta, exceto a indicada no código 6;", "2 – Estrangeira – Adquirida no mercado interno, exceto a indicada no código 7;", "3 – Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40% (quarenta por cento) e igual ou inferior a 70% (setenta por cento) ;", "4 – Nacional, cuja produção tenha sido feita em conformidade com os processos produtivos básicos de que tratam o Decreto-Lei nº 288/1967 , e as Leis nºs 8.248/1991, 8.387/1991, 10.176/2001 e 11.484/2007;", "5 – Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40%;", "6 – Estrangeira – Importação direta, sem similar nacional, constante em lista de Resolução Camex e gás natural;", "7 – Estrangeira – Adquirida no mercado interno, sem similar nacional, constante em lista de Resolução Camex e gás natural.", "8 – Nacional – Mercadoria ou bem com Conteúdo de Importação superior a 70% (setenta por cento)." }));

        jLabel95.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel95.setText("ICMS");

        jLabel96.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel96.setText("CST PIS");

        cbCstPis.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbCstPis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01- Operação Tributável com Alíquota Básica", "02- Operação Tributável com Alíquota Diferenciada", "03- Operação Tributável com Alíquota por Unidade de Medida de Produto", "04- Operação Tributável Monofásica – Revenda a Alíquota Zero", "05- Operação Tributável por Substituição Tributária", "06- Operação Tributável a Alíquota Zero", "07- Operação Isenta da Contribuição", "08- Operação sem Incidência da Contribuição", "09- Operação com Suspensão da Contribuição", "49- Outras Operações de Saída", "50- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "51- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno", "52- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita de Exportação", "53- Operação com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "54- Operação com Direito a Crédito – Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "55- Operação com Direito a Crédito – Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "56- peração com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "60- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "61- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno", "62- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação", "63- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "64- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "65- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "66- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "67- Crédito Presumido – Outras Operações", "70- Operação de Aquisição sem Direito a Crédito", "71- Operação de Aquisição com Isenção", "72- Operação de Aquisição com Suspensão", "73- Operação de Aquisição a Alíquota Zero", "74- Operação de Aquisição sem Incidência da Contribuição", "75- Operação de Aquisição por Substituição Tributária", "98- Outras Operações de Entrada", "99- Outras Operações" }));

        jLabel97.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel97.setText("PIS");

        jLabel98.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel98.setText("CST COFINS");

        jLabel99.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel99.setText("COFINS");

        jLabel100.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel100.setText("CST IPI");

        cbCstIpi.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbCstIpi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "0- Entrada com Recuperação de Crédito", "1- Entrada Tributável com Alíquota Zero", "2- Entrada Isenta", "3- Entrada Não-Tributada", "4- Entrada Imune", "5- Entrada com Suspensão", "49- Outras Entradas", "50- Saída Tributada", "51- Saída Tributável com Alíquota Zero", "52- Saída Isenta", "53- Saída Não-Tributada", "54- Saída Imune", "55- Saída com Suspensão", "99- Outras Saídas" }));

        jLabel101.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel101.setText("IPI");

        CstConfis.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CstConfis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01- Operação Tributável com Alíquota Básica", "02- Operação Tributável com Alíquota Diferenciada", "03- Operação Tributável com Alíquota por Unidade de Medida de Produto", "04- Operação Tributável Monofásica – Revenda a Alíquota Zero", "05- Operação Tributável por Substituição Tributária", "06- Operação Tributável a Alíquota Zero", "07- Operação Isenta da Contribuição", "08- Operação sem Incidência da Contribuição", "09- Operação com Suspensão da Contribuição", "49- Outras Operações de Saída", "50- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "51- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno", "52- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita de Exportação", "53- Operação com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "54- Operação com Direito a Crédito – Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "55- Operação com Direito a Crédito – Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "56- peração com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "60- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "61- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno", "62- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação", "63- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "64- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "65- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "66- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "67- Crédito Presumido – Outras Operações", "70- Operação de Aquisição sem Direito a Crédito", "71- Operação de Aquisição com Isenção", "72- Operação de Aquisição com Suspensão", "73- Operação de Aquisição a Alíquota Zero", "74- Operação de Aquisição sem Incidência da Contribuição", "75- Operação de Aquisição por Substituição Tributária", "98- Outras Operações de Entrada", "99- Outras Operações" }));

        txtNcm.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtNcm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--" }));
        txtNcm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                txtNcmPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/registro.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        jLabel102.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel102.setText("MOTIVO DA DESONERAÇÃO");

        txReducao.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txMotivoD.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txMotivoD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "Táxi | Produto agropecuário/Uso na agropecuária", "Frotista/Locadora", "Diplomático/Consular", "Utilitários e Motocicletas da Amazônia Ocidental e Áreas de Livre Comércio", "SUFRAMA", "Venda a Órgão Público", "Outros", "Deficiente Condutor", "Deficiente Não Condutor", "Órgão de fomento e desenvolvimento agropecuário" }));

        jLabel103.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel103.setText("REDUÇÃO");

        jLabel104.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel104.setText("SUBSTITUIÇÃO TRIBUTÁRIA");

        txSubst.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel105.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel105.setText("ORIGEM DO PRODUTO");

        origem.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        origem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "0 - Nacional", "1 - Importado", "2 - Fabricação Propria" }));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabel105)
                        .addGap(53, 53, 53)
                        .addComponent(origem, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel102)
                            .addComponent(jLabel104)
                            .addComponent(jLabel103))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txReducao, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txSubst, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txMotivoD, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(origem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel105))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txMotivoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel102))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel104)
                    .addComponent(txSubst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel103)
                    .addComponent(txReducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        descricaoncm.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        descricaoncm.setForeground(new java.awt.Color(153, 153, 153));
        descricaoncm.setText("$descricao");

        jButton5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton5.setText("Fechar e apagar dados");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel12Layout.createSequentialGroup()
                                            .addComponent(jLabel94)
                                            .addGap(24, 24, 24))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                            .addComponent(jLabel74)
                                            .addGap(45, 45, 45)))
                                    .addComponent(jLabel96))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCstPis, 0, 1, Short.MAX_VALUE)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel12Layout.createSequentialGroup()
                                                .addComponent(txtNcm, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(descricaoncm))
                                            .addComponent(cbIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel100)
                                    .addComponent(jLabel98))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CstConfis, 0, 1, Short.MAX_VALUE)
                                    .addComponent(cbCstIpi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel97)
                                    .addComponent(jLabel95))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txIcms, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(Pis)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel99)
                                    .addComponent(jLabel101))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txCofis, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(txIpi)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel74)
                            .addComponent(txtNcm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(descricaoncm)))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(cbIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95)
                    .addComponent(txIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel96)
                    .addComponent(cbCstPis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97)
                    .addComponent(Pis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(jLabel99)
                    .addComponent(CstConfis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txCofis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel100)
                    .addComponent(cbCstIpi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel101)
                    .addComponent(txIpi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout FormFiscaisLayout = new javax.swing.GroupLayout(FormFiscais.getContentPane());
        FormFiscais.getContentPane().setLayout(FormFiscaisLayout);
        FormFiscaisLayout.setHorizontalGroup(
            FormFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FormFiscaisLayout.setVerticalGroup(
            FormFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        Background.setBackground(new java.awt.Color(204, 204, 204));

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        scrollNote.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollNote.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jTabbedPane2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("COD. BARRAS");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("DESCRIÇÃO");

        descricaoNotebook.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        grupoNotebook.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        grupoNotebook.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                grupoNotebookPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        AutoCompleteDecorator.decorate(grupoNotebook);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("GRUPO");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("SUBGRUPO");

        subgrupoNotebook.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(subgrupoNotebook);

        cbMarca.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbMarca.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbMarcaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        AutoCompleteDecorator.decorate(cbMarca);

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("MARCA");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("MODELO");

        cbModelo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(cbModelo);

        txtSerial.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("SERIAL NUMBER");

        jLabel53.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel53.setText("P/N");

        partnumber.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        cbRefGeral.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbRefGeral.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbRefGeralPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("REF. GERAL");

        jLabel51.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel51.setText("REF. CLASSIFICATIVA");

        cbRefClass.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(cbRefClass);
        cbRefClass.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbRefClassPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        descri2.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        descri2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        descri2.setText(" ");
        descri2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        descri2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        descri2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        descri2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel52.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel52.setText("REF. ÚNICA");

        txtrefUnica.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtrefUnica.setEnabled(false);

        descri1.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        descri1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        descri1.setText(" ");
        descri1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        descri1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        descri1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        descri1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        codeBarNotebook.setEnabled(false);
        codeBarNotebook.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/atualizada.png"))); // NOI18N
        jButton6.setToolTipText("Atualizar dados");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel108.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/notebook-2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(jLabel17)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(jLabel53)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codeBarNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(grupoNotebook, 0, 223, Short.MAX_VALUE)
                            .addComponent(subgrupoNotebook, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbModelo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSerial)
                            .addComponent(partnumber))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addGap(18, 18, 18)
                                .addComponent(cbRefClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(descri1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(descri2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel108)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel52)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtrefUnica, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(74, 74, 74)
                                            .addComponent(cbRefGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(descricaoNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(246, 246, 246))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(codeBarNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(descricaoNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(grupoNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(subgrupoNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(cbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(txtSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel53)
                                .addComponent(partnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jSeparator3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cbRefGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(descri1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(cbRefClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(descri2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(txtrefUnica, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(111, 111, 111)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel108)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jButton6)))
                .addContainerGap(328, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Dados gerais e refêrencias", jPanel3);

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel21.setText("MEMÓRIA RAM");

        nMemoria.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nMemoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1GB", "2GB", "3GB", "4GB", "5GB", "6GB", "7GB", "8GB", "9GB", "10GB", "12GB", "14GB", "16GB", "18GB", "20GB", "22GB", "24GB", "26GB", "28GB", "32GB", "64GB" }));

        nTecM.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nTecM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DDR1", "DDR2", "DDR3", "DDR4", "DDR5", "DDR6" }));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("TECNOLOGIA RAM");

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel34.setText("VELOCIDADE ");

        nVeloM.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nVeloM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1333Mhz", "1600Mhz", "1866Mhz", "2133Mhz", "2400Mhz", "2600Mhz", "2666Mhz", "2800Mhz", "2933Mhz", "3000Mhz", "3200Mhz", "3333Mhz", "3400Mhz", "3466Mhz", "3600Mhz", "3733MHz", "3866MHz", "4000Mhz", "4133Mhz", "4266MHz", "4500MHz", "4600MHz", "4800MHz", "5066MHz", "5200MHz", "5333MHz", "5600MHz", "6000MHz", "6400MHz", "7200MHz" }));

        capmemoriaNote.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        capmemoriaNote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8GB", "12GB", "24GB", "32GB", "64GB", "128GB" }));

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel35.setText("CAPACIDADE MAXIMA SUPORTADA");

        jLabel57.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel57.setText("SLOTS DISPONÍVEIS");

        slotsmemoria.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        serialMemoria.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel56.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel56.setText("NÚMEROS DE SÉRIE");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/memoria-ram.png"))); // NOI18N

        jLabel90.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        jLabel90.setText("EXEMPLO: (NUMERODESERIE1,NUMERODESERIE2)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20))
                        .addGap(164, 164, 164)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(nTecM, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(nMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(244, 244, 244))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35))
                                .addGap(57, 57, 57)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(capmemoriaNote, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nVeloM, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel57))
                                .addGap(158, 158, 158)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(serialMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(slotsmemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel90))))
                        .addGap(0, 442, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel21)
                        .addComponent(nMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(nTecM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nVeloM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capmemoriaNote, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slotsmemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serialMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel90)
                .addContainerGap(548, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Memoria", jPanel5);

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel18.setText("PROCESSADOR");

        cbProcess.setEditable(true);
        AutoCompleteDecorator.decorate(cbProcess);
        cbProcess.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbProcess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Intel Core Duo L2300", "Intel Core Duo L2400", "Intel Core Duo L2500", "Intel Core Duo T2050", "Intel Core Duo T2250", "Intel Core Duo T2300", "Intel Core Duo T2300E", "Intel Core Duo T2400", "Intel Core Duo T2500", "Intel Core Duo T2600", "Intel Core Duo T2700", "Intel Core Duo U2400", "Intel Core Duo U2500", "Intel Core Solo T1300", "Intel Core Solo T1350", "Intel Core Solo T1400", "Intel Core Solo U1300", "Intel Core Solo U1400", "Intel Core2 Duo E6300", "Intel Core2 Duo E6400", "Intel Core2 Duo E6600", "Intel Core2 Duo E6700", "Intel Core2 Duo T5200", "Intel Core2 Duo T5500", "Intel Core2 Duo T5600", "Intel Core2 Duo T7200", "Intel Core2 Duo T7400", "Intel Core2 Duo T7600", "Intel Core2 Extreme X6800", "Intel Core Solo U1500", "Intel Core2 Duo E4300", "Intel Core2 Duo L7200", "Intel Core2 Duo L7400", "Intel Core2 Extreme QX6700", "Intel Core Duo T2350", "Intel Core Duo T2450", "Intel Core2 Duo E4400", "Intel Core2 Duo E6320", "Intel Core2 Duo E6420", "Intel Core2 Duo L7300", "Intel Core2 Duo L7500", "Intel Core2 Duo T5300", "Intel Core2 Duo T7100", "Intel Core2 Duo T7300", "Intel Core2 Duo T7500", "Intel Core2 Duo T7700", "Intel Core2 Duo U7500", "Intel Core2 Duo U7600", "Intel Core2 Quádruplo Q6600", "Intel Core2 Extreme QX6800", "Intel Core2 Duo E4500", "Intel Core2 Duo E6540", "Intel Core2 Duo E6550", "Intel Core2 Duo E6750", "Intel Core2 Duo E6850", "Intel Core2 Duo T5250", "Intel Core2 Duo T5450", "Intel Core2 Extreme X7800\tD", "Intel Core2 Extreme QX6850", "Intel Core2 Quádruplo Q6700", "Intel Core2 Duo T7250", "Intel Core2 Duo T7800", "Intel Core2 Extreme X7900\tD", "Intel Core2 Duo T5470", "Intel Core2 Solo U2100", "Intel Core2 Solo U2200", "Intel Core2 Duo E4600", "Intel Core2 Duo L7700", "Intel Core2 Duo T5550", "Intel Core2 Duo T5270", "Intel Core2 Duo T8300", "Intel Core2 Duo E8200", "Intel Core2 Duo E8400", "Intel Core2 Duo E8500", "Intel Core2 Duo T5750", "Intel Core2 Duo T8100", "Intel Core2 Duo T9300", "Intel Core2 Duo T9500", "Intel Core2 Duo U7700", "Intel Core2 Extreme QX9650", "Intel Core2 Quádruplo Q9300", "Intel Core2 Quádruplo Q9450", "Intel Core2 Quádruplo Q9550", "Intel Core2 Duo E4700", "Intel Core2 Duo E8190", "Intel Core2 Extreme X9000", "Intel Core2 Extreme QX9770", "Intel Core2 Extreme QX9775", "Intel Core2 Duo E6405", "Intel Core2 Duo E8300", "Intel Core2 Duo T5670", "Intel Core2 Duo E6305", "Intel Core2 Duo E7200", "Intel Core2 Quádruplo Q9400", "Intel Core2 Quádruplo Q9650", "Intel Core2 Extreme X9100\tD", "Intel Core2 Duo T9400", "Intel Core2 Duo T9600", "Intel Core2 Duo P9500", "Intel Core2 Duo P8600", "Intel Core2 Duo P8400", "Intel Core2 Duo T5800", "Intel Core2 Duo E8600", "Intel Core2 Duo E7300", "Intel Core2 Duo E7400", "Intel Core2 Duo E7500", "Intel Core2 Quádruplo Q8200", "Intel Core2 Duo SL9300", "Intel Core2 Duo SL9400", "Intel Core2 Duo SP9300", "Intel Core2 Duo SP9400", "Intel Core2 Duo SU9300", "Intel Core2 Duo SU9400", "Intel Core2 Extreme QX9300", "Intel Core2 Solo ULV SU3300", "Intel Core2 Duo P7450", "Intel Core2 Duo P7350", "Intel Core2 Duo U7500", "Intel Core2 Duo U7600", "Intel Core2 Duo T9800", "Intel Core2 Duo P8700", "Intel Core2 Quádruplo Q9100", "Intel Core2 Duo T5870", "Intel Core2 Duo P7370", "Intel Core2 Duo T9550", "Intel Core2 Solo SU3500", "Intel Core i7-920", "Intel Core i7-940", "Intel Core i7-965 Extreme E", "Intel Core i7-950", "Intel Core i7-960", "Intel Core i7-975 Extreme", "Intel Core2 Quádruplo Q9500", "Intel Core2 Duo SL9380", "Intel Core Solo T1250", "Intel Core2 Duo T6600", "Intel Core2 Duo SP9600", "Intel Core2 Duo SL9600", "Intel Core2 Duo SU9600", "Intel Core2 Duo P9600", "Intel Core2 Quádruplo Q8400", "Intel Core2 Quádruplo Q8300", "Intel Core2 Duo T6500", "Intel Core2 Duo T9900", "Intel Core2 Duo P8800", "Intel Core2 Duo T6400", "Intel Core2 Quádruplo Q9000", "Intel Core2 Quádruplo Q9400", "Intel Core2 Quádruplo Q9550", "Intel Core2 Quádruplo Q8200", "Intel Core i7-870", "Intel Core i7-860", "Intel Core i7-860S", "Intel Core i7-930", "Intel Core2 Duo E7600", "Intel Core2 Solo SU3500", "Intel Core2 Duo P7550", "Intel Core2 Duo T6670", "Intel Core2 Quádruplo Q8400", "Intel Core2 Solo SU3500", "Intel Core2 Duo P7570", "Intel Core2 Duo P9700", "Intel Core2 Duo SU7300", "Intel Core2 Duo T6570", "Intel Core i5-750", "Intel Core i5-750S", "Intel Core2 Quádruplo Q9505", "Intel Core2 Quádruplo Q9505", "Intel Core i7-720QM", "Intel Core i7-820QM", "Intel Core i7-840QM", "Intel Core i7-920XM Extreme", "Intel Core i7-940XM Extreme", "Intel Core i3-350M", "Intel Core i5-430M", "Intel Core i5-540M", "Intel Core i5-650", "Intel Core i5-660", "Intel Core i5-661", "Intel Core i5-670", "Intel Core i7-620LM", "Intel Core i7-620M", "Intel Core i7-620UM", "Intel Core i7-640LM", "Intel Core i3-530", "Intel Core i3-540", "Intel Core i5-520M", "Intel Core i5-520UM", "Intel Core i3-330M", "Intel Core i7-640UM", "Intel Core i7-980X Extreme", "Intel Core i7-970", "Intel Core i7-620LE", "Intel Core i7-620UE", "Intel Core i5-520E", "Intel Core i7-610E", "Intel Core i3-330E", "Intel Core i5-760", "Intel Core i7-870S", "Intel Core i7-875K", "Intel Core i7-880", "Intel Core i5-680", "Intel Core i3-550", "Intel Core i5-655K", "Intel Core i3-370M", "Intel Core i3-330UM", "Intel Core i5-450M", "Intel Core i5-430UM", "Intel Core i7-740QM", "Intel Core i7-660UM", "Intel Core i5-540UM", "Intel Core i5-580M", "Intel Core i5-560M", "Intel Core i7-660LM", "Intel Core i7-680UM", "Intel Core i5-560UM", "Intel Core i7-640M", "Intel Core i7-660UE", "Intel Core i5-470UM", "Intel Core i3-380UM", "Intel Core i7-2720QM", "Intel Core i5-2540M", "Intel Core i3-560", "Intel Core i3-380M", "Intel Core i5-460M", "Intel Core i5-2300", "Intel Core i5-2400", "Intel Core i5-2400S", "Intel Core i5-2500", "Intel Core i5-2500K", "Intel Core i5-2500S", "Intel Core i5-2500T", "Intel Core i7-2600", "Intel Core i7-2600K", "Intel Core i7-2600S", "Intel Core i7-2630QM", "Intel Core i3-2310M", "Intel Core i5-2410M", "Intel Core i7-2820QM", "Intel Core i5-2520M", "Intel Core i7-2620M", "Intel Core i7-2920XM Extreme", "Intel Core i7-990X Extreme", "Intel Core i5-480M", "Intel Core i3-390M", "Intel Core i3-2100", "Intel Core i3-2100T", "Intel Core i3-2102", "Intel Core i3-2120", "Intel Core i3-2120T", "Intel Core i3-2130", "Intel Core i3-2312M", "Intel Core i3-2330E", "Intel Core i3-2330M", "Intel Core i3-2350M", "Intel Core i3-2370M", "Intel Core i5-2310", "Intel Core i5-2320", "Intel Core i5-2390T", "Intel Core i5-2415M", "Intel Core i5-2430M", "Intel Core i5-2450M", "Intel Core i5-2510E", "Intel Core i7-2635QM", "Intel Core i7-2640M", "Intel Core i7-2670QM", "Intel Core i7-2675QM", "Intel Core i7-2710QE", "Intel Core i7-2760QM", "Intel Core i7-2860QM", "Intel Core i7-2960XM Extreme", "Intel Core i7-2629M", "Intel Core i7-2649M", "Intel Core i7-2657M", "Intel Core i7-2617M", "Intel Core i7-2677M", "Intel Core i7-2637M", "Intel Core i5-2537M", "Intel Core i5-2557M", "Intel Core i3-2357M", "Intel Core i7-2655LE", "Intel Core i3-2310E", "Intel Core i7-2715QE", "Intel Core i7-2610UE", "Intel Core i3-2340UE", "Intel Core i5-2515E", "Intel Core i3-2377M", "Intel Core i5-2405S", "Intel Core i3-2105", "Intel Core i5-2467M", "Intel Core i7-980", "Intel Core i3-2125", "Intel Core i3-2367M", "Intel Core i5-2435M", "Intel Core i7-2700K", "Intel Core i5-2450P", "Intel Core i5-2380P", "Intel Core i7-3820QM", "Intel Core i7-3720QM", "Intel Core i7-3520M", "Intel Core i5-3360M", "Intel Core i5-3320M", "Intel Core i7-3667U", "Intel Core i7-3610QM", "Intel Core i7-3615QM", "Intel Core i7-3612QM", "Intel Core i5-3427U", "Intel Core i5-3330", "Intel Core i5-3330S", "Intel Core i5-3450", "Intel Core i5-3450S", "Intel Core i5-3475S", "Intel Core i5-3550", "Intel Core i5-3550S", "Intel Core i5-3570K", "Intel Core i5-3570T", "Intel Core i7-3770K", "Intel Core i7-3770S", "Intel Core i7-3770T", "Intel Core i5-2550K", "Intel Core i3-3240", "Intel Core i3-3225", "Intel Core i3-3220", "Intel Core i3-3220T", "Intel Core i3-3217UE", "Intel Core i3-3217U", "Intel Core i3-3120ME", "Intel Core i3-3110M", "Intel Core i5-3570S", "Intel Core i5-3570", "Intel Core i5-3470T", "Intel Core i5-3610ME", "Intel Core i5-3317U", "Intel Core i5-3210M", "Intel Core i7-3615QE", "Intel Core i7-3612QE", "Intel Core i7-3610QE", "Intel Core i7-3555LE", "Intel Core i7-3517UE", "Intel Core i7-3517U", "Intel Core i7-3770", "Intel Core i3-3240T", "Intel Core i5-3210M", "Intel Core i7-3612QM", "Intel Core i5-3470S", "Intel Core i5-3470", "Intel Core i3-2115C", "Intel Core i5-3350P", "Intel Core i3-2365M", "Intel Core i7-3840QM", "Intel Core i7-3740QM", "Intel Core i3-2328M", "Intel Core i3-3210", "Intel Core i7-3540M", "Intel Core i5-3380M", "Intel Core i5-3340M", "Intel Core i7-3687U", "Intel Core i5-3437U", "Intel Core i7-3632QM", "Intel Core i7-3630QM", "Intel Core i7-3635QM", "Intel Core i3-3120M", "Intel Core i7-3632QM", "Intel Core i3-3229Y", "Intel Core i5-3339Y", "Intel Core i5-3439Y", "Intel Core i7-3689Y", "Intel Core i7-3537U", "Intel Core i5-3337U", "Intel Core i5-3230M", "Intel Core i3-3227U", "Intel Core i3-3130M", "Intel Core i5-3230M", "Intel Core i3-2375M", "Intel Core i3-2348M", "Intel Core i3-3250", "Intel Core i3-3250T", "Intel Core i3-3245", "Intel Core i5-3340", "Intel Core i5-3340S", "Intel Core i3-3115C", "Intel Core i3-4000M", "Intel Core i3-4005U", "Intel Core i3-4010U", "Intel Core i3-4100U", "Intel Core i3-4010Y", "Intel Core i3-4158U", "Intel Core i3-4100E", "Intel Core i3-4102E", "Intel Core i3-4100M", "Intel Core i3-4012Y", "Intel Core i3-4020Y", "Intel Core i3-4130", "Intel Core i3-4130T", "Intel Core i3-4110M", "Intel Core i3-4150", "Intel Core i3-4150T", "Intel Core i3-4160", "Intel Core i3-4160T", "Intel Core i3-4170", "Intel Core i3-4350", "Intel Core i3-4350T", "Intel Core i3-4360", "Intel Core i3-4360T", "Intel Core i3-4370", "Intel Core i3-4330", "Intel Core i3-4330T", "Intel Core i3-4340", "Intel Core i3-4330TE", "Intel Core i3-4110E", "Intel Core i3-4112E", "Intel Core i3-4340TE", "Intel Core i3-4120U", "Intel Core i3-4030U", "Intel Core i3-4025U", "Intel Core i3-4030Y", "Intel Core i3-4370T", "Intel Core i3-4170T", "Intel Core i5-4200H", "Intel Core i5-4250U", "Intel Core i5-4260U", "Intel Core i5-4350U", "Intel Core i5-4360U", "Intel Core i5-4430", "Intel Core i5-4430S", "Intel Core i5-4440", "Intel Core i5-4440S", "Intel Core i5-4570", "Intel Core i5-4570S", "Intel Core i5-4570T", "Intel Core i5-4670", "Intel Core i5-4670K", "Intel Core i5-4670S", "Intel Core i5-4670T", "Intel Core i5-4200U", "Intel Core i5-4570TE", "Intel Core i5-4402EC", "Intel Core i5-4200Y", "Intel Core i5-4258U", "Intel Core i5-4288U", "Intel Core i5-4400E", "Intel Core i5-4402E", "Intel Core i5-4300U", "Intel Core i5-4300M", "Intel Core i5-4200M", "Intel Core i5-4202Y", "Intel Core i5-4210Y", "Intel Core i5-4300Y", "Intel Core i5-4302Y", "Intel Core i5-4570R", "Intel Core i5-4670R", "Intel Core i5-4330M", "Intel Core i5-4460T", "Intel Core i5-4590T", "Intel Core i5-4210H", "Intel Core i5-4410E", "Intel Core i5-4422E", "Intel Core i5-4310U", "Intel Core i5-4340M", "Intel Core i5-4310M", "Intel Core i5-4690", "Intel Core i5-4690K", "Intel Core i5-4690S", "Intel Core i5-4690T", "Intel Core i5-4590", "Intel Core i5-4590S", "Intel Core i5-4460", "Intel Core i5-4460S", "Intel Core i5-4210M", "Intel Core i5-4210U", "Intel Core i5-4220Y", "Intel Core i5-4308U", "Intel Core i5-4278U", "Intel Core i7-4550U", "Intel Core i7-4650U", "Intel Core i7-4700HQ", "Intel Core i7-4700MQ", "Intel Core i7-4702HQ", "Intel Core i7-4702MQ", "Intel Core i7-4765T", "Intel Core i7-4770", "Intel Core i7-4770K", "Intel Core i7-4770S", "Intel Core i7-4770T", "Intel Core i7-4800MQ", "Intel Core i7-4900MQ", "Intel Core i7-4500U", "Intel Core i7-4700EQ", "Intel Core i7-4700EC", "Intel Core i7-4702EC", "Intel Core i7-4770TE", "Intel Core i7-4558U", "Intel Core i7-4950HQ", "Intel Core i7-4850HQ", "Intel Core i7-4750HQ", "Intel Core i7-4960HQ", "Intel Core i7-4860HQ", "Intel Core i7-4760HQ", "Intel Core i7-4701EQ", "Intel Core i7-4860EQ", "Intel Core i7-4850EQ", "Intel Core i7-4600M", "Intel Core i7-4600U", "Intel Core i7-4610Y", "Intel Core i7-4770R", "Intel Core i7-4771", "Intel Core i7-4710HQ", "Intel Core i7-4710MQ", "Intel Core i7-4712HQ", "Intel Core i7-4712MQ", "Intel Core i7-4720HQ", "Intel Core i7-4722HQ", "Intel Core i7-4810MQ", "Intel Core i7-4910MQ", "Intel Core i7-4610M", "Intel Core i7-4790", "Intel Core i7-4790K", "Intel Core i7-4790S", "Intel Core i7-4790T", "Intel Core i7-4785T", "Intel Core i7-4510U", "Intel Core i7-4980HQ", "Intel Core i7-4870HQ", "Intel Core i7-4770HQ", "Intel Core i7-4578U", "Intel Core i3-5005U", "Intel Core i3-5010U", "Intel Core i3-5015U", "Intel Core i3-5020U", "Intel Core i3-5157U", "Intel Core i5-5250U", "Intel Core i5-5257U", "Intel Core i5-5287U", "Intel Core i5-5350U", "Intel Core i5-5200U", "Intel Core i5-5300U", "Intel Core i5-5350H", "Intel Core i5-5575R", "Intel Core i5-5675R", "Intel Core i5-5675C", "Intel Core i7-5550U", "Intel Core i7-5557U", "Intel Core i7-5650U", "Intel Core i7-5500U", "Intel Core i7-5600U", "Intel Core i7-5700HQ", "Intel Core i7-5750HQ", "Intel Core i7-5775R", "Intel Core i7-5850HQ", "Intel Core i7-5950HQ", "Intel Core i7-5775C", "Intel Core i7-5700EQ", "Intel Core i7-5850EQ", "Intel Core M-5Y10", "Intel Core M-5Y10a", "Intel Core M-5Y70", "Intel Core M-5Y31", "Intel Core M-5Y51", "Intel Core M-5Y71", "Intel Core M-5Y10c", "Intel Core i3-6100U", "Intel Core i3-6100TE", "Intel Core i3-6100H", "Intel Core i3-6100E", "Intel Core i3-6102E", "Intel Core i3-6300T", "Intel Core i3-6100", "Intel Core i3-6300", "Intel Core i3-6320", "Intel Core i3-6100T", "Intel Core i3-6167U", "Intel Core i3-6006U", "Intel Core i3-6098P", "Intel Core i3-6157U", "Intel Core i5-6500T", "Intel Core i5-6500", "Intel Core i5-6400", "Intel Core i5-6500TE", "Intel Core i5-6400T", "Intel Core i5-6600", "Intel Core i5-6600T", "Intel Core i5-6300U", "Intel Core i5-6600K", "Intel Core i5-6200U", "Intel Core i5-6300HQ", "Intel Core i5-6440HQ", "Intel Core i5-6440EQ", "Intel Core i5-6442EQ", "Intel Core i5-6360U", "Intel Core i5-6260U", "Intel Core i5-6287U", "Intel Core i5-6267U", "Intel Core i5-6402P", "Intel Core i5-6350HQ", "Intel Core i5-6585R", "Intel Core i5-6685R", "Intel Core i5-6198DU", "Intel Core i7-6600U", "Intel Core i7-6500U", "Intel Core i7-6700K", "Intel Core i7-6700", "Intel Core i7-6700T", "Intel Core i7-6700TE", "Intel Core i7-6700HQ", "Intel Core i7-6820HK", "Intel Core i7-6820HQ", "Intel Core i7-6920HQ", "Intel Core i7-6820EQ", "Intel Core i7-6822EQ", "Intel Core i7-6560U", "Intel Core i7-6567U", "Intel Core i7-6660U", "Intel Core i7-6650U", "Intel Core i7-6970HQ", "Intel Core i7-6785R", "Intel Core i7-6870HQ", "Intel Core i7-6770HQ", "Intel Core i7-6498DU", "Intel Core i3-7020U", "Intel Core i3-7130U", "Intel Core i3-7100U", "Intel Core i3-7102E", "Intel Core i3-7101TE", "Intel Core i3-7100H", "Intel Core i3-7101E", "Intel Core i3-7100E", "Intel Core i3-7100", "Intel Core i3-7300T", "Intel Core i3-7300", "Intel Core i3-7320", "Intel Core i3-7100T", "Intel Core i3-7350K", "Intel Core i3-7167U", "Intel Core i5-7200U", "Intel Core i5-7Y54", "Intel Core i5-7500T", "Intel Core i5-7500", "Intel Core i5-7600K", "Intel Core i5-7400", "Intel Core i5-7600", "Intel Core i5-7600T", "Intel Core i5-7400T", "Intel Core i5-7300HQ", "Intel Core i5-7440HQ", "Intel Core i5-7Y57", "Intel Core i5-7300U", "Intel Core i5-7440EQ ", "Intel Core i5-7267U", "Intel Core i5-7287U", "Intel Core i5-7442EQ ", "Intel Core i5-7360U", "Intel Core i5-7260U", "Intel Core i7-7Y75", "Intel Core i7-7500U", "Intel Core i7-7700T", "Intel Core i7-7820EQ ", "Intel Core i7-7700", "Intel Core i7-7700K", "Intel Core i7-7700HQ", "Intel Core i7-7920HQ", "Intel Core i7-7820HK", "Intel Core i7-7600U", "Intel Core i7-7820HQ", "Intel Core i7-7660U", "Intel Core i7-7560U", "Intel Core i7-7567U", "Intel Core m3-7Y30", "Intel Core m3-7Y32", "Intel Core i3-8100", "Intel Core i3-8350K", "Intel Core i3-8300", "Intel Core i3-8300T", "Intel Core i3-8100T", "Intel Core i3-8109U", "Intel Core i3-8121U", "Intel Core i3-8130U", "Intel Core i3-8145U", "Intel Core i3-8100H", "Intel Core i3-8100B", "Intel Core i3-8145UE", "Intel Core i3-8140U", "Intel Core i5-8250U", "Intel Core i5-8350U", "Intel Core i5-8600K", "Intel Core i5-8400", "Intel Core i5-8600", "Intel Core i5-8600T", "Intel Core i5-8500", "Intel Core i5-8400T", "Intel Core i5-8500T", "Intel Core i5-8305G  with Radeon RX Vega M GL graphics", "Intel Core i5-8300H", "Intel Core i5-8400H", "Intel Core i5-8400B", "Intel Core i5-8500B", "Intel Core i5-8259U", "Intel Core i5-8269U", "Intel Core i5+8500", "Intel Core i5+8400", "Intel Core i5-8305G  with Radeon Pro WX Vega M GL graphics", "Intel Core i5-8265U", "Intel Core i5-8200Y", "Intel Core i5-8210Y", "Intel Core i5-8257U", "Intel Core i5-8279U", "Intel Core i5-8365U", "Intel Core i5-8365UE", "Intel Core i5-8310Y", "Intel Core i5-8260U", "Intel Core i7-8550U", "Intel Core i7-8650U", "Intel Core i7-8700K", "Intel Core i7-8700", "Intel Core i7-8700T", "Intel Core i7-8706G com placa de vídeo Radeon RX Vega M GL", "Intel Core i7-8709G com placa de vídeo Radeon RX Vega M GH", "Intel Core i7-8809G com placa de vídeo Radeon RX Vega M GH", "Intel Core i7-8705G com placa de vídeo Radeon RX Vega M GL", "Intel Core i7-8850H", "Intel Core i7-8700B", "Intel Core i7-8750H", "Intel Core i7-8559U", "Intel Core i7+8700", "Intel Core i7-8086K", "Intel Core i7-8706G  with Radeon Pro WX Vega M GL graphics", "Intel Core i7-8565U", "Intel Core i7-8500Y", "Intel Core i7-8569U", "Intel Core i7-8557U", "Intel Core i7-8665UE", "Intel Core i7-8665U", "Intel Core i9-8950HK", "Intel Core m3-8100Y", "Intel Core i3-9100", "Intel Core i3-9100T", "Intel Core i3-9300T", "Intel Core i3-9300", "Intel Core i3-9350K", "Intel Core i3-9100F", "Intel Core i3-9350KF", "Intel Core i3-9320", "Intel Core i3-9100E", "Intel Core i3-9100HL", "Intel Core i3-9100TE", "Intel Core i5-9400T", "Intel Core i5-9500", "Intel Core i5-9600K", "Intel Core i5-9400", "Intel Core i5-9600", "Intel Core i5-9400F", "Intel Core i5-9600KF", "Intel Core i5-9500F", "Intel Core i5-9600T", "Intel Core i5-9500T", "Intel Core i5-9300H", "Intel Core i5-9400H", "Intel Core i5-9300HF", "Intel Core i5-9500E", "Intel Core i5-9500TE", "Intel Core i7-9700K", "Intel Core i7-9700KF", "Intel Core i7-9750H", "Intel Core i7-9750HF", "Intel Core i7-9850H", "Intel Core i7-9700T", "Intel Core i7-9700", "Intel Core i7-9700F", "Intel Core i7-9700E", "Intel Core i7-9850HE", "Intel Core i7-9700TE", "Intel Core i7-9850HL", "Intel Core i9-9900K", "Intel Core i9-9900KF", "Intel Core i9-9900T", "Intel Core i9-9900", "Intel Core i9-9900KS", "Intel Core i9-9880H", "Intel Core i9-9980HK", "Intel Core i3-10110U", "Intel Core i3-10110Y", "Intel Core i3-1000NG4", "Intel Core i3-1005G1", "Intel Core i3-1000G1", "Intel Core i3-1000G4", "Intel Core i3-10320", "Intel Core i3-10300", "Intel Core i3-10300T", "Intel Core i3-10100", "Intel Core i3-10100T", "Intel Core i3-10325", "Intel Core i3-10105T", "Intel Core i3-10305", "Intel Core i3-10105", "Intel Core i3-10305T", "Intel Core i3-10100F", "Intel Core i3-10105F", "Intel Core i3-10100E ", "Intel Core i3-10100TE ", "Intel Core i3-10100Y ", "Intel Core i5-10210U", "Intel Core i5-10310Y", "Intel Core i5-10210Y", "Intel Core i5-1030NG7", "Intel Core i5-1035G4", "Intel Core i5-1035G7", "Intel Core i5-1038NG7", "Intel Core i5-1035G1", "Intel Core i5-1030G7", "Intel Core i5-1030G4", "Intel Core i5-10400", "Intel Core i5-10600", "Intel Core i5-10500T", "Intel Core i5-10400T", "Intel Core i5-10500", "Intel Core i5-10400F", "Intel Core i5-10600T", "Intel Core i5-10600K", "Intel Core i5-10600KF", "Intel Core i5-10300H", "Intel Core i5-10505", "Intel Core i5-10310U", "Intel Core i5-10400H", "Intel Core i5-10500H", "Intel Core i5-10500TE ", "Intel Core i5-10500E ", "Intel Core i5-10200H", "Intel Core i7-10710U", "Intel Core i7-10510U", "Intel Core i7-10510Y", "Intel Core i7-1068NG7", "Intel Core i7-1060NG7", "Intel Core i7-1065G7", "Intel Core i7-1060G7", "Intel Core i7-10700T", "Intel Core i7-10700", "Intel Core i7-10700F", "Intel Core i7-10700KF", "Intel Core i7-10700K", "Intel Core i7-10750H", "Intel Core i7-10810U", "Intel Core i7-10610U", "Intel Core i7-10850H", "Intel Core i7-10875H", "Intel Core i7-10700TE ", "Intel Core i7-10700E ", "Intel Core i7-10870H ", "Intel Core i9-10900T", "Intel Core i9-10900", "Intel Core i9-10900F", "Intel Core i9-10900KF", "Intel Core i9-10900K", "Intel Core i9-10980HK", "Intel Core i9-10885H", "Intel Core i9-10900TE ", "Intel Core i9-10900E ", "Intel Core i9-10910 ", "Intel Core i9-10850K", "Intel Core i3-1115GRE", "Intel Core i3-1115G4E", "Intel Core i3-1110G4", "Intel Core i3-1115G4", "Intel Core i3-1125G4", "Intel Core i3-1115G4", "Intel Core i3-1125G4", "Intel Core i3-1120G4", "Intel Core i3-11100B ", "Intel Core i5-11500B ", "Intel Core i5-11300H ", "Intel Core i5-1145GRE", "Intel Core i5-1145G7E", "Intel Core i5-1130G7", "Intel Core i5-1135G7", "Intel Core i5-1140G7", "Intel Core i5-1145G7", "Intel Core i5-1135G7", "Intel Core i5-11400 ", "Intel Core i5-11400F ", "Intel Core i5-11500T ", "Intel Core i5-11400T ", "Intel Core i5-11600 ", "Intel Core i5-11600K ", "Intel Core i5-11600KF ", "Intel Core i5-11500 ", "Intel Core i5-11600T ", "Intel Core i5-11500H ", "Intel Core i5-11400H ", "Intel Core i5-11260H ", "Intel Core i5-11320H ", "Intel Core i5-1155G7 ", "Intel Core i5-1155G7 ", "Intel Core i7-11700B ", "Intel Core i7-11370H ", "Intel Core i7-11375H ", "Intel Core i7-1185G7E", "Intel Core i7-1185GRE", "Intel Core i7-1160G7", "Intel Core i7-1165G7", "Intel Core i7-1180G7", "Intel Core i7-1185G7", "Intel Core i7-1165G7", "Intel Core i7-11700K ", "Intel Core i7-11700KF ", "Intel Core i7-11700T ", "Intel Core i7-11700 ", "Intel Core i7-11700F", "Intel Core i7-11850H", "Intel Core i7-11800H", "Intel Core i7-1195G7", "Intel Core i7-11390H", "Intel Core i7-1195G7", "Intel Core i9-11900", "Intel Core i9-11900F", "Intel Core i9-11900T", "Intel Core i9-11900KF", "Intel Core i9-11900K", "Intel Core i9-11950H", "Intel Core i9-11980HK", "Intel Core i9-11900H", "Intel Core i9-11900KB" }));

        txModeloProcess.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel36.setText("MODELO DO PROCESSADOR");

        jLabel39.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel39.setText("QUANTIDADE DE NÚCLEOS");

        jLabel40.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel40.setText("VELOCIDADE DO PROCESSADOR");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/processadores.png"))); // NOI18N

        nucleosProcess.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nucleosProcess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40" }));

        VelocidadeProcess.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        VelocidadeProcess.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1.0 GHz", "1.2 GHz", "1.3 GHz", "1.4 GHz", "1.5 GHz", "1.6 GHz", "1.7 GHz", "1.8 GHz", "1.9 GHz", "2.0 GHz", "2.1 GHz", "2.2 GHz", "2.3 GHz", "2.4 GHz", "2.5 GHz", "2.6 GHz", "2.7 GHz", "2.8 GHz", "2.9 GHz", "3.0 GHz", "3.1 GHz", "3.2 GHz", "3.3 GHz", "3.4 GHz", "3.5 GHz", "3.6 GHz", "3.7 GHz", "3.8 GHz", "3.9 GHz", "4.0 GHz", "4.1 GHz", "4.2 GHz", "4.3 GHz", "4.4 GHz", "4.5 GHz", "4.6 GHz", "4.7 GHz", "4.8 GHz", "4.9 GHz", "5.0 GHz", "5.1 GHz", "5.2 GHz", "5.3 GHz", "5.4 GHz" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel39)
                    .addComponent(jLabel36)
                    .addComponent(jLabel40))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txModeloProcess))
                        .addGap(222, 222, 222)
                        .addComponent(jLabel1))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nucleosProcess, 0, 154, Short.MAX_VALUE)
                        .addComponent(VelocidadeProcess, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(247, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addComponent(jLabel1))
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txModeloProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(nucleosProcess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(VelocidadeProcess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(659, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Processador", jPanel6);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/placa-de-video.png"))); // NOI18N

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ONBOARD - Integrada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12))); // NOI18N

        cbRamVideoDesktop.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbRamVideoDesktop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "20MB", "40MB", "60MB", "128MB", "256MB", "384MB", "512MB", "1GB", "2GB", "3GB", "4GB", "5GB", "6GB", "7GB", "8GB", "9GB", "10GB", "12GB", "14GB", "16GB", "18GB", "20GB", "22GB", "24GB", "26GB", "28GB", "32GB", "64GB", "--" }));

        txVideo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txVideo.setText("MEMORIA DE VÍDEO");

        txTipo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txTipo.setText("TIPO DE MEMORIA DE VIDEO");

        cbTipoRamVideoDesktop.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTipoRamVideoDesktop.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GDDR", "GDDR1", "GDDR2", "DDR3", "GDDR3", "GDDR4", "GDDR5", "GDDR5X", "GDDR6", "GDDR6X", "--" }));

        txModeloVideoOnboard.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txModelo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txModelo.setText("MODELO DA PLACA DE VIDEO");

        jLabel49.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel49.setText("CONTÉM PLACA ONBOARD");

        cbPlacaVideoOnboard.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbPlacaVideoOnboard.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SIM", "NAO" }));
        cbPlacaVideoOnboard.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbPlacaVideoOnboardPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbPlacaVideoOnboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPlacaVideoOnboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txVideo)
                    .addComponent(txTipo)
                    .addComponent(txModelo)
                    .addComponent(jLabel49))
                .addGap(31, 31, 31)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPlacaVideoOnboard, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cbTipoRamVideoDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbRamVideoDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txModeloVideoOnboard, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 23, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPlacaVideoOnboard, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addGap(15, 15, 15)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txModeloVideoOnboard, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txModelo))
                .addGap(15, 15, 15)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTipoRamVideoDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTipo))
                .addGap(15, 15, 15)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRamVideoDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txVideo))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OFFBOARD - Dedicada", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12))); // NOI18N

        cbRamVideoOff.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbRamVideoOff.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "20MB", "40MB", "60MB", "128MB", "256MB", "384MB", "512MB", "1GB", "2GB", "3GB", "4GB", "5GB", "6GB", "7GB", "8GB", "9GB", "10GB", "12GB", "14GB", "16GB", "18GB", "20GB", "22GB", "24GB", "26GB", "28GB", "32GB", "64GB", "--" }));

        txVideo1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txVideo1.setText("MEMORIA DE VÍDEO");

        txTipo1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txTipo1.setText("TIPO DE MEMORIA DE VIDEO");

        cbTipoRamVideoOff.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTipoRamVideoOff.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GDDR", "GDDR1", "GDDR2", "DDR3", "GDDR3", "GDDR4", "GDDR5", "GDDR5X", "GDDR6", "GDDR6X", "--" }));

        txModeloVideoOff.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txModelo1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txModelo1.setText("MODELO DA PLACA DE VIDEO");

        jLabel89.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel89.setText("CONTÉM PLACA OFFBOARD");

        cbPlacaVideoOff.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbPlacaVideoOff.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SIM", "NAO" }));
        cbPlacaVideoOff.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbPlacaVideoOffPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbPlacaVideoOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPlacaVideoOffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txVideo1)
                    .addComponent(txTipo1)
                    .addComponent(txModelo1)
                    .addComponent(jLabel89))
                .addGap(37, 37, 37)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPlacaVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cbTipoRamVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbRamVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txModeloVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPlacaVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89))
                .addGap(15, 15, 15)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txModeloVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txModelo1))
                .addGap(15, 15, 15)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTipoRamVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTipo1))
                .addGap(15, 15, 15)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRamVideoOff, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txVideo1))
                .addGap(0, 15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addComponent(jLabel5)
                .addContainerGap(243, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(406, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Placa de video", jPanel7);

        jLabel66.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel66.setText("DUPLO ARMAZENAMENTO");

        duploArma.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        duploArma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        duploArma.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                duploArmaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel41.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel41.setText("TIPO DE ARMAZENAMENTO");

        barra2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        barra2.setText("/");

        barra1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        barra1.setText("/");

        jLabel42.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel42.setText("CAPACIDADE DE ARMAZENAMENTO");

        jLabel54.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel54.setText("NÚMERO(S) DE SÉRIE");

        serialhd.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/armazenamento-em-disco.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Armazenamento 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        cbArmazena.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbArmazena.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HD SLIM", "HD NORMAL", "SSD SATA", "SSD M.2", "SSD M.2 NVME", "SSD MSATA", "SEM DISCO DE ARMAZENAMENTO", "--" }));
        cbArmazena.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbArmazenaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        cbCapArmazena.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbCapArmazena.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8GB", "32GB", "64GB", "120GB", "128GB", "240GB", "256GB", "500GB", "512GB", "600GB", "750GB", "1TB", "2TB", "4TB", "--" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCapArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cbCapArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panel2arm.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Armazenamento 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        cbCapArmazena1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbCapArmazena1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8GB", "32GB", "64GB", "120GB", "128GB", "240GB", "256GB", "500GB", "512GB", "600GB", "750GB", "1TB", "2TB", "4TB", "--" }));

        cbArmazena1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbArmazena1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HD SLIM", "HD NORMAL", "SSD SATA", "SSD M.2", "SSD M.2 NVME", "SSD MSATA", "--" }));
        cbArmazena1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbArmazena1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout panel2armLayout = new javax.swing.GroupLayout(panel2arm);
        panel2arm.setLayout(panel2armLayout);
        panel2armLayout.setHorizontalGroup(
            panel2armLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2armLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2armLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCapArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panel2armLayout.setVerticalGroup(
            panel2armLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2armLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cbCapArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel41)
                        .addComponent(jLabel54)
                        .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(serialhd, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(barra1)
                                    .addComponent(barra2))
                                .addGap(19, 19, 19)
                                .addComponent(panel2arm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(duploArma, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 467, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(246, 246, 246))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel66)
                        .addComponent(duploArma, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(barra2)
                        .addGap(18, 18, 18)
                        .addComponent(barra1)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel42)
                        .addGap(27, 27, 27))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel2arm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serialhd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addContainerGap(607, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Armazenamento", jPanel8);

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel26.setText("RESOLUÇÃO DA TELA");

        cbReso.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbReso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "320 x 240", "640 x 360", "640 x 480", "720 x 480", "800 x 600", "1024 x 768", "1280 x 720", "1366 x 768", "1440 x 900", "1600 x 900", "1680 x 1050", "1920 x 1080", "2560 x 1080", "2560 x 1440", "3840 x 2160", "7680 x 4320", "10240×4320", "10328×7760" }));

        cbTaxa.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTaxa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30Hz", "40Hz", "50Hz", "60Hz", "75Hz", "110Hz", "120Hz", "144Hz", "240Hz", "360Hz" }));

        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel27.setText("TAXA DE ATUALIZAÇÃO");

        jLabel43.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel43.setText("TAMANHO DA TELA");

        cbTamanho.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTamanho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7\"", "8.9\"", "10\"", "10.1\"", "11\"", "12\"", "12.1\"", "13.1\"", "13.3\"", "14\"", "15\"", "15.6\"", "17\"", "17.3\"", "18\"", "19\"", "20\"", "21\"", "22\"", "24\"", "32\"", "40\"", "44\"" }));

        cbTamanho2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTamanho2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2K: 2048 x 1080 pixels", "DCI 2K: 2048 x 858 ou 1998 x 1080 pixels", "4K (UHDTV ou QFHD): 3840 x 2160 pixels", "4K (Ultra Wide HDTV): 5120 x 2160 pixels", "DCI 4K: 4096 x 2160, 4096 x 1716", "HD (720p): 1280 x 720 pixels", "nHD: 640 x 360 pixels", "qHD: 960 x 540 pixels", "full HD (FHD ou 1080p): 1920 x 1080 pixels", "QHD (WQHD): 2560 x 1440 pixels", "VGA: 640 x 480 pixels", "WVGA: 800 x 480 pixels", "FWVGA: 854 x 480 pixels", "QVGA: 320 x 240 pixels", "QQVGA: 160 x 120 pixels", "HQVGA: 240 x 160 pixels", "WQVGA: 400 x 240 pixels", "HVGA: 480 x 320 pixels", "WVGA: 800 x 480 pixels", "SVGA / Super VGA: 800 x 600 pixels", "DVGA: 960 x 640 pixels", "WSVGA: 1024 x 600 pixels", "XGA: 1024 x 768 pixels", "WXGA: de 1152 x 768 a 1366 x 768 pixels", "XGA+: 1152 x 900 e 1152 x 864 pixels", "WXGA+: 1440 x 900 pixels", "SXGA: 1280 x 1024 pixels", "SXGA+: 1400 x 1050 pixels", "WSWGA+: 1680 x 1050 pixels", "UXGA: 1600 x 1200 pixels", "WUXGA: 1920 x 1200 pixels", "QWXGA: 2048 x 1152 pixels", "QXGA: 2048 x 1536 pixels", "WQXGA: 2560 x 1600 pixels", "QSXGA: 2560 x 2048 pixels", "WQSXGA: 3200 x 2048 pixels", "QUXGA: 3200 x 2400 pixels", "WQUXGA: 3840 x 2400 pixels", "HXGA: 4096 x 3072 pixels", "WHXGA: 5120 x 3200 pixels", "HSXGA:  5120 x 4096 pixels", "WHSXGA: 6400 x 4096 pixels", "HUXGA: 6400 x 4800 pixels", "WHUXGA: 7680 x 4800 pixels" }));

        jLabel87.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel87.setText("TIPO DE RESOLUÇÃO");

        jLabel88.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel88.setText("TIPO DE TELA");

        cbTamanho3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTamanho3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LCD", "OLED", "LED", "In-Plane Switching (IPS)", "Vertical Alignment (VA)", "Twisted Nematic (TN)" }));

        jLabel106.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/tela-de-televisao.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel87)
                    .addComponent(jLabel88)
                    .addComponent(jLabel43)
                    .addComponent(jLabel27)
                    .addComponent(jLabel26))
                .addGap(44, 44, 44)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbTamanho3, 0, 472, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cbReso, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTaxa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTamanho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbTamanho2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 195, Short.MAX_VALUE)
                .addComponent(jLabel106)
                .addGap(247, 247, 247))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbReso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26))
                    .addComponent(jLabel106))
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTamanho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTamanho2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel87))
                .addGap(15, 15, 15)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTamanho3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel88))
                .addContainerGap(617, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Tela", jPanel9);

        jLabel86.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel86.setText("CONTÉM CARREGADOR");

        cbReso1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbReso1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SIM", "NAO" }));

        jLabel91.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel91.setText("POTÊNCIA");

        cbReso4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbReso4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30W", "20W", "40W", "45W", "50W", "65W", "75W", "90W", "100W", "120W", "135W" }));

        jLabel92.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel92.setText("CONTÉM BATERIA");

        cbReso5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbReso5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SIM", "NAO" }));

        jLabel93.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel93.setText("DURAÇÃO MÉDIA (h)");

        cbReso6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbReso6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Menos de 30min", "30min ~ 1h", "1h ~ 1h30", "1h30 ~ 2h", "2h30 ~ 3h", "3h30 ~ 4h", "4h30 ~ 5h", "5h30 ~ 6h", "6h30 ~ 7h", " " }));

        jLabel107.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/carregador-de-telefone.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel86)
                            .addComponent(jLabel91))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(cbReso4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(764, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(cbReso1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel107)
                                .addGap(247, 247, 247))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel92)
                                    .addComponent(jLabel93))
                                .addGap(75, 75, 75)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbReso6, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbReso5, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbReso1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel86))
                    .addComponent(jLabel107))
                .addGap(8, 8, 8)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel91)
                    .addComponent(cbReso4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbReso5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel92))
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(cbReso6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(643, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Bateria e carregador", jPanel10);

        jLabel45.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel45.setText("LEITOR DIGITAL");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/variante-de-contorno-de-impressao-digital.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/sinal-de-bluetooth.png"))); // NOI18N

        jLabel46.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel46.setText("BLUETOOTH");

        jLabel47.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel47.setText("TECLADO RETROILUMINADO");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/computador-portatil.png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/certificado-de-garantia.png"))); // NOI18N

        jLabel48.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel48.setText("GARANTIA DO FABRICANTE");

        jLabel58.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel58.setText("USBS 2.0 DISPONIVEIS");

        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/porta-usb.png"))); // NOI18N

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/porta-usb.png"))); // NOI18N

        jLabel60.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel60.setText("USBS 3.0 DISPONIVEIS");

        jLabel59.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel59.setText("OUTRAS ENTRADAS");

        jLabel84.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/download-da-caixa-de-entrada.png"))); // NOI18N

        nEntradas.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel85.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        jLabel85.setText("EXEMPLO: ( HDMI - ETHERNET - VGA - DVI )");

        spUsb3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        spUsb2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        cbGarantia.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbGarantia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        cbGarantia.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbGarantiaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        cbTeclado.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbTeclado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));

        cbBlue.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbBlue.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));

        cbLeitor.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbLeitor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/elipse.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Validade até:");

        jLabel50.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel50.setText("COR DO NOTEBOOK");

        CorNotebook.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        CorNotebook.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BRANCO", "PRETO", "CINZA", "AZUL", "ROSA", "VIOLETA", "BRANCO GELO", "CINZA ESCURO", "CINZA CLARO", "CINZA E PRETO", "MARRON", "CORAL", "CREME", "COBRE", "DOURADO" }));

        jLabel62.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel62.setText("SISTEMA OP.");

        cbLeitor2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbLeitor2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Windows 11", "Windows 10", "Windows 8.1", "Windows 8", "Windows 7", "Windows Vista", "Windows XP", "Windows 2000", "Windows NT", "Windows 98", "Windows 95", "macOS Monterey", "macOS Big Sur", "macOS Catalina", "macOS Mojave", "macOS High Sierra", "macOS Sierra", "OS X El Capitan", "OS X Yosemite", "OS X Mavericks", "OS X Mountain Lion", "OS X Lion", "OS X Snow Leopard", "Mac OS X Leopard", "Mac OS X Tiger", "Mac OS X Panther", "Mac OS X Jaguar", "Mac OS X Puma", "Mac OS X Cheetah", "Ubuntu", "Debian", "Fedora", "Red Hat Enterprise Linux", "CentOS", "openSUSE", "Arch Linux", "Gentoo", "Slackware", "Linux Mint", "Elementary OS", "Zorin OS", "Kali Linux", "Tails", "iOS 15", "iOS 14", "iOS 13", "iOS 12", "iOS 11", "iOS 10", "iOS 9", "iOS 8", "iOS 7", "iOS 6", "iOS 5", "Android 12", "Android 11", "Android 10", "Android 9", "Android 8", "Android 7", "Android 6", "Android 5", "Android 4", "Android 3", "Android 2", "Chrome OS 91", "Chrome OS 90", "Chrome OS 89", "Chrome OS 88", "Chrome OS 87", "Chrome OS 86", "Chrome OS 85", "Chrome OS 84", "Chrome OS 83" }));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/paleta-de-cores (2).png"))); // NOI18N

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/sistema.png"))); // NOI18N

        DataGarantiaFabrica.setEnabled(false);
        DataGarantiaFabrica.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)))
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel61)
                                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGap(5, 5, 5)
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel58)
                                                    .addComponent(jLabel60)
                                                    .addComponent(jLabel48))
                                                .addGap(90, 90, 90))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel59)
                                                .addGap(148, 148, 148)))
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spUsb2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spUsb3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel85)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addComponent(cbGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(DataGarantiaFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel46)
                                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                                        .addGap(7, 7, 7)
                                                        .addComponent(jLabel45)))
                                                .addGap(163, 163, 163)
                                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbLeitor, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cbBlue, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cbLeitor2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                                        .addComponent(CorNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel25))))
                                            .addComponent(jLabel47)))))
                            .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(251, 251, 251))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel62)
                                    .addComponent(jLabel50))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel50)
                                .addComponent(CorNotebook, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel62)
                            .addComponent(cbLeitor2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbLeitor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45))
                    .addComponent(jLabel11))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbBlue, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel46)
                        .addComponent(jLabel15)))
                .addGap(2, 2, 2)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(cbTeclado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(DataGarantiaFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel48)
                                    .addComponent(cbGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))))))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel58)
                        .addComponent(spUsb2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel61))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(spUsb3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel85))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(430, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Outros", jPanel11);

        scrollNote.setViewportView(jTabbedPane2);

        jTabbedPane1.addTab("Informações do notebook", scrollNote);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Valores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        txcusto.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txcusto.setText("PREÇO DE CUSTO");

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("PREÇO DE VENDA");

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("PREÇO MIN. DE VENDA");

        txtPrecoCusto.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtPrecoMin.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtPrecoVenda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        radioPromo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioPromo.setText("Colocar em promoção");
        radioPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPromoActionPerformed(evt);
            }
        });

        jLabel75.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel75.setText("PREÇO DE PROMOÇÃO");

        precoPromo.setText("0,0");
        precoPromo.setEnabled(false);
        precoPromo.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        precoPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precoPromoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel23)
                    .addComponent(txcusto)
                    .addComponent(jLabel75))
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(precoPromo, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(txtPrecoMin)
                    .addComponent(txtPrecoCusto)
                    .addComponent(txtPrecoVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap(63, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(radioPromo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txcusto)
                    .addComponent(txtPrecoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtPrecoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(radioPromo)
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel75)
                    .addComponent(precoPromo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estoque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel28.setText("QTDE. EM ESTOQUE");

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel29.setText("UNID. DE MEDIDA");

        spQtde.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        nMedida.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        nMedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UND", "FRD", "PCT", "CX" }));

        spQtdEmb.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel30.setText("QTDE. NA EMBALAGEM");

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel31.setText("LOCAL EM ESTOQUE");

        nLocal.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        nLocal.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                nLocalPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel64.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel64.setText("PRATELEIRA");

        nPrateleira.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel64))
                .addGap(25, 25, 25)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spQtdEmb, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nMedida, javax.swing.GroupLayout.Alignment.TRAILING, 0, 173, Short.MAX_VALUE)
                    .addComponent(nLocal, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spQtde, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nPrateleira, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(spQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(nMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(spQtdEmb, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(nLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(nPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Outras informações", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel33.setText("OBSERVAÇÃO DO NOTEBOOK");

        nObs.setColumns(20);
        nObs.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        nObs.setRows(5);
        jScrollPane1.setViewportView(nObs);

        jLabel37.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel37.setText("STATUS DO PRODUTO");

        nStatus.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Novo (ainda não utilizado)", "Seminovo (usado, mas em excelente condição)", "Recondicionado (foi consertado e restaurado à condição de novo)", "Usado (em bom estado, mas pode apresentar sinais de uso)", "Carcaça (Notebook usado para retirada de peças)", "Danificado (tem algum tipo de dano ou defeito)", "Vencido (expirou a data de validade ou prazo de garantia)", "Quebrado (não funciona e precisa de reparos)", "Inoperante (não funciona e não pode ser reparado)", "Refurbished (recondicionado com peças originais de fábrica)", "Com defeito (tem um problema específico que pode ou não ser reparado)" }));

        nForne.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel55.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel55.setText("FORNECEDOR");

        jLabel63.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel63.setText("DATA DE CADASTRO");

        txtDataC.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDataC.setEnabled(false);

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("SKU FORNECEDOR");

        skuForne.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        openFiscais.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        openFiscais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/impostos.png"))); // NOI18N
        openFiscais.setText("Dados fiscais");
        openFiscais.setToolTipText("Dados fiscais");
        openFiscais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFiscaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)
                            .addComponent(txtDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(skuForne, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nForne, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(103, 103, 103))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(nStatus, 0, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(openFiscais)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(openFiscais)))
                .addGap(15, 15, 15)
                .addComponent(jLabel55)
                .addGap(15, 15, 15)
                .addComponent(nForne, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(skuForne, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout PainelComplementosLayout = new javax.swing.GroupLayout(PainelComplementos);
        PainelComplementos.setLayout(PainelComplementosLayout);
        PainelComplementosLayout.setHorizontalGroup(
            PainelComplementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelComplementosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelComplementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PainelComplementosLayout.setVerticalGroup(
            PainelComplementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelComplementosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelComplementosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PainelComplementosLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Informações complementares", PainelComplementos);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/olx32x.png"))); // NOI18N

        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/ml32x.png"))); // NOI18N

        jLabel68.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel68.setText("ANUNCIADO NA OLX:");

        anuncioOlx.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anuncioOlx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));

        jLabel69.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel69.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        codAnuncioOlx.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioOlx.setEnabled(false);

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        jButton2.setText(" Gerar cod.");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel70.setText("CÓDIGO OLX ANÚNCIO:");

        codAnuncioOlx1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioOlx1.setEnabled(false);

        jLabel71.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel71.setText("CÓDIGO ML ANÚNCIO:");

        codAnuncioOlx2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioOlx2.setEnabled(false);

        codAnuncioML.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioML.setEnabled(false);

        jLabel72.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel72.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        jLabel73.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel73.setText("ANUNCIADO NO ML:");

        anuncioOlx1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anuncioOlx1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));

        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        jButton4.setText(" Gerar cod.");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/facebook.png"))); // NOI18N

        jLabel77.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel77.setText("ANUNCIADO NO FACEBOOK:");

        anuncioFace.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anuncioFace.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        anuncioFace.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                anuncioFacePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        codAnuncioFaceInterno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioFaceInterno.setEnabled(false);

        jLabel78.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel78.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        jLabel79.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel79.setText("CÓDIGO ML ANÚNCIO:");

        codAnuncioFace.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioFace.setEnabled(false);

        gerarCodFace.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        gerarCodFace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        gerarCodFace.setText(" Gerar cod.");
        gerarCodFace.setEnabled(false);
        gerarCodFace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarCodFaceActionPerformed(evt);
            }
        });

        jLabel80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/comercio-eletronico.png"))); // NOI18N

        jLabel81.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel81.setText("ANUNCIADO NO E-COMMERCE:");

        jLabel82.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel82.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        jLabel83.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel83.setText("CÓDIGO ML ANÚNCIO:");

        anuncioEco.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anuncioEco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        anuncioEco.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                anuncioEcoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        codAnuncioEcoInterno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioEcoInterno.setEnabled(false);

        codAnuncioEco.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioEco.setEnabled(false);

        gerarCodEco.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        gerarCodEco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        gerarCodEco.setText(" Gerar cod.");
        gerarCodEco.setEnabled(false);
        gerarCodEco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarCodEcoActionPerformed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout PainelDadosAnuncioNotebookLayout = new javax.swing.GroupLayout(PainelDadosAnuncioNotebook);
        PainelDadosAnuncioNotebook.setLayout(PainelDadosAnuncioNotebookLayout);
        PainelDadosAnuncioNotebookLayout.setHorizontalGroup(
            PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel69)
                                    .addComponent(jLabel68)
                                    .addComponent(jLabel70))
                                .addGap(18, 18, 18)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(codAnuncioOlx1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(anuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                        .addComponent(codAnuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2))))
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addComponent(jLabel67)
                                .addGap(18, 18, 18)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                        .addComponent(jLabel71)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(codAnuncioOlx2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel72)
                                            .addComponent(jLabel73))
                                        .addGap(18, 18, 18)
                                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(anuncioOlx1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(codAnuncioML, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)))
                        .addGap(43, 43, 43)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel81)
                            .addComponent(jLabel80)
                            .addComponent(anuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(41, Short.MAX_VALUE))
                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                        .addComponent(jLabel76)
                        .addGap(18, 18, 18)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addComponent(jLabel79)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(codAnuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel78)
                                    .addComponent(jLabel77))
                                .addGap(18, 18, 18)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(anuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codAnuncioFaceInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(gerarCodFace)
                        .addGap(12, 12, 12)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gerarCodEco)
                            .addComponent(jLabel82)
                            .addComponent(codAnuncioEcoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83)
                            .addComponent(codAnuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        PainelDadosAnuncioNotebookLayout.setVerticalGroup(
            PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel68)
                                .addComponent(anuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel69)
                            .addComponent(codAnuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel70)
                            .addComponent(codAnuncioOlx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel67)
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel73)
                                            .addComponent(anuncioOlx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(codAnuncioEcoInterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel72)
                                            .addComponent(codAnuncioML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton4))
                                        .addGap(18, 18, 18)
                                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel71)
                                            .addComponent(codAnuncioOlx2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                        .addComponent(jLabel83)
                                        .addGap(18, 18, 18)
                                        .addComponent(codAnuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel76)
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel77)
                                    .addComponent(anuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel78)
                                    .addComponent(codAnuncioFaceInterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gerarCodFace))
                                .addGap(18, 18, 18)
                                .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel79)
                                    .addComponent(codAnuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(PainelDadosAnuncioNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PainelDadosAnuncioNotebookLayout.createSequentialGroup()
                                .addComponent(jLabel80)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel81)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(anuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel82)
                                .addGap(132, 132, 132)
                                .addComponent(gerarCodEco))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados sobre anuncios", PainelDadosAnuncioNotebook);

        scroImg.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        uploadimg1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        uploadimg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/upload-de-arquivo.png"))); // NOI18N
        uploadimg1.setText("Upload");
        uploadimg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadimg1ActionPerformed(evt);
            }
        });

        Cancelimg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        Cancelimg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancelimg1ActionPerformed(evt);
            }
        });

        Painel1ImagePP.setBackground(new java.awt.Color(255, 255, 255));

        viewImage1PP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewImage1PPMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                viewImage1PPMousePressed(evt);
            }
        });

        javax.swing.GroupLayout Painel1ImagePPLayout = new javax.swing.GroupLayout(Painel1ImagePP);
        Painel1ImagePP.setLayout(Painel1ImagePPLayout);
        Painel1ImagePPLayout.setHorizontalGroup(
            Painel1ImagePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1PP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Painel1ImagePPLayout.setVerticalGroup(
            Painel1ImagePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1PP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        Painel2ImagePP.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel2ImagePPLayout = new javax.swing.GroupLayout(Painel2ImagePP);
        Painel2ImagePP.setLayout(Painel2ImagePPLayout);
        Painel2ImagePPLayout.setHorizontalGroup(
            Painel2ImagePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2PP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Painel2ImagePPLayout.setVerticalGroup(
            Painel2ImagePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2PP, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        Cancelimg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        Cancelimg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancelimg2ActionPerformed(evt);
            }
        });

        Cancelimg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        Cancelimg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancelimg3ActionPerformed(evt);
            }
        });

        Painel3ImagePP.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel3ImagePPLayout = new javax.swing.GroupLayout(Painel3ImagePP);
        Painel3ImagePP.setLayout(Painel3ImagePPLayout);
        Painel3ImagePPLayout.setHorizontalGroup(
            Painel3ImagePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3PP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Painel3ImagePPLayout.setVerticalGroup(
            Painel3ImagePPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3PP, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        habilitar1PP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        habilitar1PP.setText("Habilitar edição do anexo 1");
        habilitar1PP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                habilitar1PPActionPerformed(evt);
            }
        });

        AlterImage1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        AlterImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        AlterImage1.setText("Salvar edição do anexo 1");
        AlterImage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterImage1ActionPerformed(evt);
            }
        });

        AlterImage2.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        AlterImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        AlterImage2.setText("Salvar edição do anexo 2");
        AlterImage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterImage2ActionPerformed(evt);
            }
        });

        habilitar2PP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        habilitar2PP.setText("Habilitar edição do anexo 2");
        habilitar2PP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                habilitar2PPActionPerformed(evt);
            }
        });

        habilitar3PP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        habilitar3PP.setText("Habilitar edição do anexo 3");
        habilitar3PP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                habilitar3PPActionPerformed(evt);
            }
        });

        AlterImage3.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        AlterImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        AlterImage3.setText("Salvar edição do anexo 3");
        AlterImage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterImage3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelImgNotebookLayout = new javax.swing.GroupLayout(panelImgNotebook);
        panelImgNotebook.setLayout(panelImgNotebookLayout);
        panelImgNotebookLayout.setHorizontalGroup(
            panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImgNotebookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Painel1ImagePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Painel2ImagePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImgNotebookLayout.createSequentialGroup()
                        .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(habilitar1PP)
                            .addComponent(AlterImage1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 607, Short.MAX_VALUE)
                        .addComponent(Cancelimg1))
                    .addComponent(Painel3ImagePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelImgNotebookLayout.createSequentialGroup()
                        .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(habilitar2PP)
                            .addComponent(AlterImage2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelimg2))
                    .addGroup(panelImgNotebookLayout.createSequentialGroup()
                        .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(habilitar3PP)
                            .addComponent(AlterImage3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelimg3))
                    .addComponent(uploadimg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelImgNotebookLayout.setVerticalGroup(
            panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImgNotebookLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uploadimg1)
                .addGap(18, 18, 18)
                .addComponent(Painel1ImagePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cancelimg1)
                    .addGroup(panelImgNotebookLayout.createSequentialGroup()
                        .addComponent(habilitar1PP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AlterImage1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Painel2ImagePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImgNotebookLayout.createSequentialGroup()
                        .addComponent(habilitar2PP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AlterImage2))
                    .addComponent(Cancelimg2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Painel3ImagePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelImgNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelImgNotebookLayout.createSequentialGroup()
                        .addComponent(habilitar3PP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AlterImage3))
                    .addComponent(Cancelimg3))
                .addContainerGap())
        );

        scroImg.setViewportView(panelImgNotebook);

        jTabbedPane1.addTab("Imagens do produto", scroImg);

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("CADASTRAR NOTEBOOK");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/de-volta.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(860, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(2, 2, 2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                    .addGap(2, 2, 2)))
        );

        btCad.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionarUser.png"))); // NOI18N
        btCad.setText("Cadastrar");
        btCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadActionPerformed(evt);
            }
        });

        btSearch.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pesquisarUser.png"))); // NOI18N
        btSearch.setText("Procurar");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText("Editar");
        btEdit.setEnabled(false);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        btExcluir.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        btExcluir.setText("Excluir");
        btExcluir.setEnabled(false);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btSearch1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        btSearch1.setText(" Limpar campos");
        btSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BackgroundLayout = new javax.swing.GroupLayout(Background);
        Background.setLayout(BackgroundLayout);
        BackgroundLayout.setHorizontalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(BackgroundLayout.createSequentialGroup()
                        .addComponent(btSearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSearch)
                        .addGap(278, 278, 278)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCad, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BackgroundLayout.setVerticalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btEdit)
                    .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btSearch)
                        .addComponent(btExcluir)
                        .addComponent(btSearch1))
                    .addComponent(btCad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(Background, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        selectProduct.setVisible(true);
        selectProduct.setAlwaysOnTop(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadActionPerformed
        adicionar();
    }//GEN-LAST:event_btCadActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:

        SearchNotebooks sn = new SearchNotebooks();
        sn.setVisible(true);
    }//GEN-LAST:event_btSearchActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSearch1ActionPerformed

    private void nLocalPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_nLocalPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        nPrateleira.removeAllItems();
        GetPrats();
    }//GEN-LAST:event_nLocalPopupMenuWillBecomeInvisible

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        //setCodOLX();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // setCodML();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void radioPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPromoActionPerformed
        // TODO add your handling code here:
        if (radioPromo.isSelected()) {
            DecimalFormat decimal = new DecimalFormat("#,###,###.00");
            NumberFormatter numFormatter = new NumberFormatter(decimal);
            numFormatter.setFormat(decimal);
            numFormatter.setAllowsInvalid(false);
            DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

            precoPromo.setEnabled(true);
            precoPromo.setFormatterFactory(dfFactory);
        } else {
            precoPromo.setEnabled(false);
            precoPromo.setFormatterFactory(null);
            precoPromo.setText("0,0");
        }
    }//GEN-LAST:event_radioPromoActionPerformed

    private void precoPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precoPromoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precoPromoActionPerformed

    private void anuncioFacePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_anuncioFacePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
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
    }//GEN-LAST:event_anuncioFacePopupMenuWillBecomeInvisible

    private void gerarCodFaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarCodFaceActionPerformed
        // TODO add your handling code here:
        //  setCodFace();
    }//GEN-LAST:event_gerarCodFaceActionPerformed

    private void anuncioEcoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_anuncioEcoPopupMenuWillBecomeInvisible
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
    }//GEN-LAST:event_anuncioEcoPopupMenuWillBecomeInvisible

    private void gerarCodEcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarCodEcoActionPerformed
        // TODO add your handling code here:
        // setCodEco();
    }//GEN-LAST:event_gerarCodEcoActionPerformed

    private void grupoNotebookPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_grupoNotebookPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        subgrupoNotebook.removeAllItems();
        GetSubGrupos();
    }//GEN-LAST:event_grupoNotebookPopupMenuWillBecomeInvisible

    private void cbMarcaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbMarcaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbModelo.removeAllItems();
        GetModelsNotebook();
    }//GEN-LAST:event_cbMarcaPopupMenuWillBecomeInvisible

    private void cbRefGeralPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbRefGeralPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbRefClass.removeAllItems();
        GetRefClass();
        getDescriGeral();
    }//GEN-LAST:event_cbRefGeralPopupMenuWillBecomeInvisible

    private void cbRefClassPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbRefClassPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        String sql = "select max(idpartepecas)idpartepecas from partepecas";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String idMax = rs.getString(1);
                String getclass = cbRefClass.getSelectedItem().toString();
                txtrefUnica.setText(getclass + "-00" + idMax + "");
                getDescriClass();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }//GEN-LAST:event_cbRefClassPopupMenuWillBecomeInvisible

    private void cbPlacaVideoOnboardPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbPlacaVideoOnboardPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (cbPlacaVideoOnboard.getSelectedItem().equals("NAO")) {
            cbRamVideoDesktop.setSelectedItem("--");
            cbTipoRamVideoDesktop.setSelectedItem("--");
            txModeloVideoOnboard.setText("--");
            cbRamVideoDesktop.setEnabled(false);
            cbTipoRamVideoDesktop.setEnabled(false);
            txModeloVideoOnboard.setEnabled(false);
        } else {
            txModeloVideoOnboard.setText(null);
            cbRamVideoDesktop.setSelectedItem("1GB");
            cbTipoRamVideoDesktop.setSelectedItem("GDDR");
            cbRamVideoDesktop.setEnabled(true);
            cbTipoRamVideoDesktop.setEnabled(true);
            txModeloVideoOnboard.setEnabled(true);

        }
    }//GEN-LAST:event_cbPlacaVideoOnboardPopupMenuWillBecomeInvisible

    private void cbPlacaVideoOnboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPlacaVideoOnboardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPlacaVideoOnboardActionPerformed

    private void duploArmaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_duploArmaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (duploArma.getSelectedItem().equals("SIM")) {
            barra1.setVisible(true);
            barra2.setVisible(true);
            cbArmazena1.setVisible(true);
            panel2arm.setVisible(true);
            cbCapArmazena1.setVisible(true);
            cbArmazena1.setSelectedItem("HD SLIM");
            cbCapArmazena1.setSelectedItem("8GB");
        } else {
            barra1.setVisible(false);
            barra2.setVisible(false);
            panel2arm.setVisible(false);
            cbArmazena1.setVisible(false);
            cbCapArmazena1.setVisible(false);
            cbArmazena1.setSelectedItem("--");
            cbCapArmazena1.setSelectedItem("--");

        }
    }//GEN-LAST:event_duploArmaPopupMenuWillBecomeInvisible

    private void cbArmazenaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbArmazenaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (cbArmazena.getSelectedItem().equals("SEM DISCO DE ARMAZENAMENTO")) {
            cbCapArmazena.setSelectedItem("--");
        }
    }//GEN-LAST:event_cbArmazenaPopupMenuWillBecomeInvisible

    private void cbArmazena1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbArmazena1PopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cbArmazena1PopupMenuWillBecomeInvisible

    private void cbGarantiaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbGarantiaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (cbGarantia.getSelectedItem().equals("NAO")) {
            DataGarantiaFabrica.setEnabled(false);
        } else {
            DataGarantiaFabrica.setEnabled(true);

        }

    }//GEN-LAST:event_cbGarantiaPopupMenuWillBecomeInvisible

    private void openFiscaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFiscaisActionPerformed
        // TODO add your handling code here:
        if (openFiscais.isSelected()) {
            FormFiscais.setVisible(true);
            FormFiscais.setLocationRelativeTo(null);
            FormFiscais.setAlwaysOnTop(true);
        } else {
            FormFiscais.setVisible(false);
        }
    }//GEN-LAST:event_openFiscaisActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        FormFiscais.setVisible(false);
        openFiscais.setSelected(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtNcmPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_txtNcmPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
//        if (txtNcm.getSelectedItem().equals("--")) {
//            descricaoncm.setVisible(false);
//        } else {
//            descricaoncm.setVisible(true);
//            chaangedescri();
//
//        }
    }//GEN-LAST:event_txtNcmPopupMenuWillBecomeInvisible

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
//        cadCMS cadc = new cadCMS();
//        cadc.setVisible(true);
//        cadc.setAlwaysOnTop(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void FormFiscaisWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_FormFiscaisWindowClosed
        // TODO add your handling code here:
        openFiscais.setSelected(false);
    }//GEN-LAST:event_FormFiscaisWindowClosed

    private void FormFiscaisWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_FormFiscaisWindowClosing
        // TODO add your handling code here:
        openFiscais.setSelected(false);
    }//GEN-LAST:event_FormFiscaisWindowClosing

    private void cbPlacaVideoOffPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbPlacaVideoOffPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (cbPlacaVideoOff.getSelectedItem().equals("NAO")) {
            cbRamVideoOff.setSelectedItem("--");
            cbTipoRamVideoOff.setSelectedItem("--");
            txModeloVideoOff.setText("--");
            cbRamVideoOff.setEnabled(false);
            cbTipoRamVideoOff.setEnabled(false);
            txModeloVideoOff.setEnabled(false);
        } else {
            cbRamVideoOff.setSelectedItem("1GB");
            cbTipoRamVideoOff.setSelectedItem("GDDR");
            cbRamVideoOff.setEnabled(true);
            cbTipoRamVideoOff.setEnabled(true);
            txModeloVideoOff.setEnabled(true);

        }
    }//GEN-LAST:event_cbPlacaVideoOffPopupMenuWillBecomeInvisible

    private void cbPlacaVideoOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPlacaVideoOffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPlacaVideoOffActionPerformed

    private void uploadimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadimg1ActionPerformed
        // TODO add your handling code here:
        try {                  
            JnaFileChooser filec = new JnaFileChooser();
            filec.addFilter("Escolha até 3 imagens em JPEG e PNG ou 3 imagens em WEBP", "jpg", "png", "jpeg", "webp");
            filec.setMode(JnaFileChooser.Mode.Files);
            filec.setMultiSelectionEnabled(true);
            filec.setCurrentDirectory("/");

            boolean result = filec.showOpenDialog(this);
            if (result) {
                File[] selectedFiles = filec.getSelectedFiles();
                if (selectedFiles.length <= 3) {
                    for (int i = 0; i < selectedFiles.length; i++) {

                        String imagePath = selectedFiles[i].getAbsolutePath();

                        File file = selectedFiles[i];
                        String fileExtension = getFileExtension(file);

                        BufferedImage imagem = ImageIO.read(selectedFiles[i]);
                        if (selectedFiles.length == 1) {
                            if (fileExtension.equalsIgnoreCase("webp")) {
                                imagemA = selectedFiles[0];
                                abrirImagemWebp(imagem, i + 1);
                            } else {
                                imagemA = selectedFiles[0];
                                abrirImagem(imagePath, i + 1);
                            }
                        } else if (selectedFiles.length == 2) {
                            if (fileExtension.equalsIgnoreCase("webp")) {
                                imagemA = selectedFiles[0];
                                imagemB = selectedFiles[1];
                                abrirImagemWebp(imagem, i + 1);
                            } else {
                                imagemA = selectedFiles[0];
                                imagemB = selectedFiles[1];
                                abrirImagem(imagePath, i + 1);

                            }
                        } else if (selectedFiles.length == 3) {
                            if (fileExtension.equalsIgnoreCase("webp")) {
                                imagemA = selectedFiles[0];
                                imagemB = selectedFiles[1];
                                imagemC = selectedFiles[2];
                                abrirImagemWebp(imagem, i + 1);
                            } else {
                                imagemA = selectedFiles[0];
                                imagemB = selectedFiles[1];
                                imagemC = selectedFiles[2];
                                abrirImagem(imagePath, i + 1);
                            }
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Máximo de 3 imagens podem ser escolhidas!!");
                }
            }
        } catch (IOException e) {

        }
    }//GEN-LAST:event_uploadimg1ActionPerformed

    private void Cancelimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancelimg1ActionPerformed
//        // TODO add your handling code here:
//        if (viewImage1PP.getIcon() != null) {
//            viewImage1PP.setIcon(null);
//            imagemA = null;
//        } else {
//            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");
//        }
    }//GEN-LAST:event_Cancelimg1ActionPerformed

    private void viewImage1PPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage1PPMouseClicked

    }//GEN-LAST:event_viewImage1PPMouseClicked

    private void viewImage1PPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage1PPMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage1PPMousePressed

    private void Cancelimg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancelimg2ActionPerformed
        // TODO add your handling code here:
//        if (viewImage2PP.getIcon() != null) {
//            viewImage2PP.setIcon(null);
//            imagemB = null;
//        } else {
//            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");
//        }
    }//GEN-LAST:event_Cancelimg2ActionPerformed

    private void Cancelimg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancelimg3ActionPerformed
        // TODO add your handling code here:
//        if (viewImage3PP.getIcon() != null) {
//            viewImage3PP.setIcon(null);
//            imagemC = null;
//        } else {
//            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");
//        }
    }//GEN-LAST:event_Cancelimg3ActionPerformed

    private void habilitar1PPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_habilitar1PPActionPerformed
        // TODO add your handling code here:
        if (habilitar1PP.isSelected()) {
            AlterImage1.setVisible(true);
            uploadimg1.setEnabled(true);
            Cancelimg1.setEnabled(true);
        } else {
            AlterImage1.setVisible(false);
            uploadimg1.setEnabled(false);
            Cancelimg1.setEnabled(false);

        }
    }//GEN-LAST:event_habilitar1PPActionPerformed

    private void AlterImage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage1ActionPerformed
        // TODO add your handling code here:
////        String sql = "update partepecas set imagem1=? where idpartepecas=?";
////        try {
////            pst = conexao.prepareStatement(sql);
////
////            pst.setBytes(1, getImagem1());
////            pst.setString(2, txtID.getText());
////
////            int adicionado = pst.executeUpdate();
////
////            if (adicionado > 0) {
////                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
////                JOptionPane.showMessageDialog(null, "Anexo 1 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
////                AlterImage1.setVisible(false);
////                habilitar1PP.setSelected(false);
////                uploadimg1.setEnabled(false);
////                Cancelimg1.setEnabled(false);
////            }
////
////        } catch (Exception e) {
////            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 1!!");
////        }
    }//GEN-LAST:event_AlterImage1ActionPerformed

    private void AlterImage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage2ActionPerformed
        // TODO add your handling code here:
//        String sql = "update partepecas set imagem1=? where idpartepecas=?";
//        try {
//            pst = conexao.prepareStatement(sql);
//
//            pst.setBytes(1, getImagem2());
//            pst.setString(2, txtID.getText());
//
//            int adicionado = pst.executeUpdate();
//
//            if (adicionado > 0) {
//                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
//                JOptionPane.showMessageDialog(null, "Anexo 2 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
//                AlterImage2.setVisible(false);
//                habilitar2PP.setSelected(false);
//                Cancelimg2.setEnabled(false);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 2!!");
//        }
    }//GEN-LAST:event_AlterImage2ActionPerformed

    private void habilitar2PPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_habilitar2PPActionPerformed
        // TODO add your handling code here:
        if (habilitar2PP.isSelected()) {
            AlterImage2.setVisible(true);
            Cancelimg2.setEnabled(true);
        } else {
            AlterImage2.setVisible(false);
            Cancelimg2.setEnabled(false);

        }
    }//GEN-LAST:event_habilitar2PPActionPerformed

    private void habilitar3PPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_habilitar3PPActionPerformed
        // TODO add your handling code here:
        if (habilitar3PP.isSelected()) {
            AlterImage3.setVisible(true);
            Cancelimg3.setEnabled(true);
        } else {
            AlterImage3.setVisible(false);
            Cancelimg3.setEnabled(false);

        }
    }//GEN-LAST:event_habilitar3PPActionPerformed

    private void AlterImage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage3ActionPerformed
        // TODO add your handling code here:
//        String sql = "update partepecas set imagem3=? where idpartepecas=?";
//        try {
//            pst = conexao.prepareStatement(sql);
//
//            pst.setBytes(1, getImagem3());
//            pst.setString(2, txtID.getText());
//
//            int adicionado = pst.executeUpdate();
//
//            if (adicionado > 0) {
//                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
//                JOptionPane.showMessageDialog(null, "Anexo 3 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
//                AlterImage3.setVisible(false);
//                habilitar3PP.setSelected(false);
//                Cancelimg3.setEnabled(false);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 3!!");
//        }
    }//GEN-LAST:event_AlterImage3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

//        cbGrupo.removeAllItems();
//        cbMarca.removeAllItems();
//        cbFornecedores.removeAllItems();
//        cbLocal.removeAllItems();
//        cbSubgrupo.removeAllItems();
//        cbModelo.removeAllItems();
//        txtNcm.removeAllItems();
//        cbRefGeral.removeAllItems();
//        cbRefClass.removeAllItems();
//        ListarLocais();
//        ListarGrupos();
//        ListarMarcas();
//        ListarForne();
//        Getncm();
//        ListarRefGeral();
//        GetRefClass();
//        if (cbRefClass.getSelectedItem() == null) {
//            cbRefClass.setEnabled(false);
//        } else {
//            cbRefClass.setEnabled(true);
//        }
    }//GEN-LAST:event_jButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadNotebook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadNotebook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadNotebook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadNotebook.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadNotebook().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton AlterImage1;
    public static javax.swing.JButton AlterImage2;
    public static javax.swing.JButton AlterImage3;
    private javax.swing.JPanel Background;
    public static javax.swing.JButton Cancelimg1;
    public static javax.swing.JButton Cancelimg2;
    public static javax.swing.JButton Cancelimg3;
    public static javax.swing.JComboBox<String> CorNotebook;
    public static javax.swing.JComboBox<String> CstConfis;
    public static com.toedter.calendar.JDateChooser DataGarantiaFabrica;
    private javax.swing.JDialog FormFiscais;
    public static javax.swing.JPanel Painel1ImagePP;
    public static javax.swing.JPanel Painel2ImagePP;
    public static javax.swing.JPanel Painel3ImagePP;
    private javax.swing.JPanel PainelComplementos;
    private javax.swing.JPanel PainelDadosAnuncioNotebook;
    public static javax.swing.JFormattedTextField Pis;
    private javax.swing.JComboBox<String> VelocidadeProcess;
    private javax.swing.JComboBox<String> anuncioEco;
    private javax.swing.JComboBox<String> anuncioFace;
    private javax.swing.JComboBox<String> anuncioOlx;
    private javax.swing.JComboBox<String> anuncioOlx1;
    public static javax.swing.JLabel barra1;
    public static javax.swing.JLabel barra2;
    public static javax.swing.JButton btCad;
    public static javax.swing.JButton btEdit;
    public static javax.swing.JButton btExcluir;
    public static javax.swing.JButton btSearch;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JComboBox<String> capmemoriaNote;
    public static javax.swing.JComboBox<String> cbArmazena;
    public static javax.swing.JComboBox<String> cbArmazena1;
    public static javax.swing.JComboBox<String> cbBlue;
    public static javax.swing.JComboBox<String> cbCapArmazena;
    public static javax.swing.JComboBox<String> cbCapArmazena1;
    public static javax.swing.JComboBox<String> cbCstIpi;
    public static javax.swing.JComboBox<String> cbCstPis;
    public static javax.swing.JComboBox<String> cbGarantia;
    public static javax.swing.JComboBox<String> cbIcms;
    public static javax.swing.JComboBox<String> cbLeitor;
    public static javax.swing.JComboBox<String> cbLeitor2;
    public static javax.swing.JComboBox<String> cbMarca;
    public static javax.swing.JComboBox<String> cbModelo;
    public static javax.swing.JComboBox<String> cbPlacaVideoOff;
    public static javax.swing.JComboBox<String> cbPlacaVideoOnboard;
    private javax.swing.JComboBox<String> cbProcess;
    public static javax.swing.JComboBox<String> cbRamVideoDesktop;
    public static javax.swing.JComboBox<String> cbRamVideoOff;
    public static javax.swing.JComboBox<String> cbRefClass;
    public static javax.swing.JComboBox<String> cbRefGeral;
    public static javax.swing.JComboBox<String> cbReso;
    public static javax.swing.JComboBox<String> cbReso1;
    public static javax.swing.JComboBox<String> cbReso4;
    public static javax.swing.JComboBox<String> cbReso5;
    public static javax.swing.JComboBox<String> cbReso6;
    public static javax.swing.JComboBox<String> cbTamanho;
    public static javax.swing.JComboBox<String> cbTamanho2;
    public static javax.swing.JComboBox<String> cbTamanho3;
    public static javax.swing.JComboBox<String> cbTaxa;
    public static javax.swing.JComboBox<String> cbTeclado;
    public static javax.swing.JComboBox<String> cbTipoRamVideoDesktop;
    public static javax.swing.JComboBox<String> cbTipoRamVideoOff;
    private javax.swing.JTextField codAnuncioEco;
    private javax.swing.JTextField codAnuncioEcoInterno;
    private javax.swing.JTextField codAnuncioFace;
    private javax.swing.JTextField codAnuncioFaceInterno;
    private javax.swing.JTextField codAnuncioML;
    private javax.swing.JTextField codAnuncioOlx;
    private javax.swing.JTextField codAnuncioOlx1;
    private javax.swing.JTextField codAnuncioOlx2;
    public static javax.swing.JFormattedTextField codeBarNotebook;
    public static javax.swing.JLabel descri1;
    public static javax.swing.JLabel descri2;
    public static javax.swing.JTextField descricaoNotebook;
    public static javax.swing.JLabel descricaoncm;
    public static javax.swing.JComboBox<String> duploArma;
    private javax.swing.JButton gerarCodEco;
    private javax.swing.JButton gerarCodFace;
    public static javax.swing.JComboBox<String> grupoNotebook;
    public static javax.swing.JCheckBox habilitar1PP;
    public static javax.swing.JCheckBox habilitar2PP;
    public static javax.swing.JCheckBox habilitar3PP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    public static javax.swing.JTextField nEntradas;
    public static javax.swing.JComboBox<String> nForne;
    public static javax.swing.JTextField nID;
    public static javax.swing.JComboBox<String> nLocal;
    public static javax.swing.JComboBox<String> nMedida;
    public static javax.swing.JComboBox<String> nMemoria;
    public static javax.swing.JTextArea nObs;
    public static javax.swing.JComboBox<String> nPrateleira;
    public static javax.swing.JComboBox<String> nStatus;
    public static javax.swing.JComboBox<String> nTecM;
    public static javax.swing.JComboBox<String> nVeloM;
    private javax.swing.JComboBox<String> nucleosProcess;
    private javax.swing.JToggleButton openFiscais;
    public static javax.swing.JComboBox<String> origem;
    private javax.swing.JPanel panel2arm;
    private javax.swing.JPanel panelImgNotebook;
    public static javax.swing.JTextField partnumber;
    public static javax.swing.JFormattedTextField precoPromo;
    public static javax.swing.JRadioButton radioPromo;
    private javax.swing.JScrollPane scroImg;
    private javax.swing.JScrollPane scrollNote;
    public static javax.swing.JTextField serialMemoria;
    public static javax.swing.JTextField serialhd;
    public static javax.swing.JTextField skuForne;
    public static javax.swing.JSpinner slotsmemoria;
    public static javax.swing.JSpinner spQtdEmb;
    public static javax.swing.JSpinner spQtde;
    public static javax.swing.JSpinner spUsb2;
    public static javax.swing.JSpinner spUsb3;
    public static javax.swing.JComboBox<String> subgrupoNotebook;
    public static javax.swing.JFormattedTextField txCofis;
    public static javax.swing.JFormattedTextField txIcms;
    public static javax.swing.JFormattedTextField txIpi;
    private javax.swing.JLabel txModelo;
    private javax.swing.JLabel txModelo1;
    private javax.swing.JTextField txModeloProcess;
    public static javax.swing.JTextField txModeloVideoOff;
    public static javax.swing.JTextField txModeloVideoOnboard;
    public static javax.swing.JComboBox<String> txMotivoD;
    public static javax.swing.JTextField txReducao;
    public static javax.swing.JTextField txSubst;
    private javax.swing.JLabel txTipo;
    private javax.swing.JLabel txTipo1;
    private javax.swing.JLabel txVideo;
    private javax.swing.JLabel txVideo1;
    private javax.swing.JLabel txcusto;
    public static javax.swing.JTextField txtDataC;
    public static javax.swing.JComboBox<String> txtNcm;
    public static javax.swing.JFormattedTextField txtPrecoCusto;
    public static javax.swing.JFormattedTextField txtPrecoMin;
    public static javax.swing.JFormattedTextField txtPrecoVenda;
    public static javax.swing.JTextField txtSerial;
    public static javax.swing.JTextField txtrefUnica;
    public static javax.swing.JButton uploadimg1;
    public static javax.swing.JLabel viewImage1PP;
    public static javax.swing.JLabel viewImage2PP;
    public static javax.swing.JLabel viewImage3PP;
    // End of variables declaration//GEN-END:variables
}
