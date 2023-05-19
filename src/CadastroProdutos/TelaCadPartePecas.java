package CadastroProdutos;

import PagesAdmin.cadCMS;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import notification.Notification;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import pages.TelaPrincipal;
import static pages.TelaPrincipal.empresa;
import static pages.TelaPrincipal.menuProdutos;
import static pages.TelaPrincipal.perfil;
import static pages.TelaPrincipal.selectProduct;
import pagesSearchs.SearchPartePecas;
import jnafilechooser.api.JnaFileChooser;

public final class TelaCadPartePecas extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public static File imagemA;
    public static File imagemB;
    public static File imagemC;

    private long lastPressTime = 0;

    public TelaCadPartePecas() throws BadLocationException {
        initComponents();
        conexao = ModuloConexao.conector();
        Locale.setDefault(new Locale("pt", "BR"));
        setIcon();
        setColorFromEmpresa();
        uper();
        ListarRefGeral();
        verifUser();
        formatarCamposDecimais();
        ListarLocais();
        ListarGrupos();
        ListarMarcas();
        ListarForne();
        SetDate();
        GetRefClass();
        GetPrats();
        Getncm();
        getDescriGeral();

        GetSubGrupos();
        GetModels();
        setcodabar();

        if (cbRefClass.getSelectedItem() == null) {
            cbRefClass.setEnabled(false);
        } else {
            cbRefClass.setEnabled(true);
        }

        descricaoncm.setVisible(false);
        FormFiscais.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
        AlterImage1.setVisible(false);
        AlterImage2.setVisible(false);
        AlterImage3.setVisible(false);
        habilitar1PP.setVisible(false);
        habilitar2PP.setVisible(false);
        habilitar3PP.setVisible(false);

        this.ScrollImage.getVerticalScrollBar().setUnitIncrement(20);

        spQTDE.setModel(new SpinnerNumberModel(1, 0, 99999, 1));
        spQtEmb.setModel(new SpinnerNumberModel(1, 0, 99999, 1));

        viewImage1PP.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    // Obtém a transferência de dados e a imagem transferida
                    Transferable transferable = evt.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                        File file = (File) transferable.getTransferData(DataFlavor.imageFlavor);
                        String imagePath = file.getAbsolutePath();
                        abrirImagemforarraste(imagePath, 1);
                    } else {
                        File file = (File) transferable.getTransferData(DataFlavor.imageFlavor);
                        String imagePath = file.getAbsolutePath();
                        abrirImagemforarraste(imagePath, 1);
 
                    }

                    // Chama o método abrirImagemWebp para exibir a imagem no JLabel de destino
                    // substitua o índice pelo que você precisar
                } catch (HeadlessException | UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public void setcodabar() {
        String codigo;
        try {
            do {
                codigo = gerarCodigoEAN13();
            } while (verificarCodigoExistente(codigo));
            txtCodeBar.setText(codigo);
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

    private void abrirImagemforarraste(String imagePath, int index) {

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

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setCodOLX() {
        if (txtSerial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo 'Serial' não esta preenchido!");
        } else {
            String Serial = txtSerial.getText();
            Date data = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            String date = formatador.format(data);
            codAnuncioOlxInterno.setText("#" + date.replaceAll("/", "") + "5OLX" + Serial);
        }

    }

    public void setCodML() {
        if (txtSerial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo 'Serial' não esta preenchido!");
        } else {
            String Serial = txtSerial.getText();
            Date data = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            String date = formatador.format(data);
            codAnuncioMLInterno.setText("#" + date.replaceAll("/", "") + "5ML" + Serial);
        }

    }

    public void setCodFace() {
        if (txtSerial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo 'Serial' não esta preenchido!");
        } else {
            String Serial = txtSerial.getText();
            Date data = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            String date = formatador.format(data);
            codAnuncioFaceInterno.setText("#" + date.replaceAll("/", "") + "5FB" + Serial);
        }

    }

    public void setCodEco() {
        if (txtSerial.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo 'Serial' não esta preenchido!");
        } else {
            String Serial = txtSerial.getText();
            Date data = new Date();
            DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
            String date = formatador.format(data);
            codAnuncioEcoInterno.setText("#" + date.replaceAll("/", "") + "5Ecommerce" + Serial);
        }

    }

    public void verifUser() {

        if (perfil.getText().equals("user")) {
            txtPrecoCusto.setVisible(false);
            txcusto.setVisible(false);
            txtPrecoMin.setEnabled(false);
        }

    }

    public void uper() {

        txtCodeBar.setDocument(new UpperCase());
        txtDescri.setDocument(new UpperCase());
        txtSerial.setDocument(new UpperCase());
        txtObs.setDocument(new UpperCase());
        partnumber.setDocument(new UpperCase());
        skuForne.setDocument(new UpperCase());

    }

    public void formatarCamposDecimais() {

        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        txtPrecoCusto.setFormatterFactory(dfFactory);
        txtPrecoMin.setFormatterFactory(dfFactory);
        txtPrecoVenda.setFormatterFactory(dfFactory);
        txIcms.setFormatterFactory(dfFactory);
        Pis.setFormatterFactory(dfFactory);
        txCofis.setFormatterFactory(dfFactory);
        txIpi.setFormatterFactory(dfFactory);
    }

    public void ListarGrupos() {
        String sql = "select nomeg from grupo order by nomeg";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbGrupo.addItem(nome);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void ListarLocais() {
        String sql = "select local from localestoque order by local";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbLocal.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ListarMarcas() {
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

    public void ListarForne() {
        String sql = "select nome from fornecedores order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbFornecedores.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void GetSubGrupos() {
        String sql = "select psubgrupo from subgrupo where pgrupo like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbGrupo.getSelectedItem().toString());

            rs = pst.executeQuery();

            while (rs.next()) {
                String subgrupo = rs.getString(1);
                cbSubgrupo.addItem(subgrupo);

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

    public void GetPrats() {
        String sql = "select prateleira from prateleiras where local like ?";
        String getLocal = cbLocal.getSelectedItem().toString();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getLocal);

            rs = pst.executeQuery();

            while (rs.next()) {
                String prateleira = rs.getString(1);
                cbPrateleira.addItem(prateleira);

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

    public void SetDate() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String date = formatador.format(data);
        txtDataC.setText(date);
    }

    public void GetModels() {
        String sql = "select model from modelos where marcan like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbMarca.getSelectedItem().toString());

            rs = pst.executeQuery();

            while (rs.next()) {
                String modelo = rs.getString(1);
                cbModelo.addItem(modelo);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void Getncm() {
        String sql = "select ncm from ncm";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String ncm = rs.getString(1);
                txtNcm.addItem(ncm);

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

    public void addLogCad() {

        String sql = "insert into logpartepecas(tipolog,message,datalog) values(?,?,?)";
        String produto = txtDescri.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Add");
            pst.setString(2, "Produto " + produto + " adicionado por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (SQLException e) {

            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void addLogEdit() {

        String sql = "insert into logpartepecas(tipolog,message,datalog) values(?,?,?)";
        String produto = txtDescri.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Edit");
            pst.setString(2, "Produto  " + produto + " editado por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void addLogRemove() {

        String sql = "insert into logpartepecas(tipolog,message,datalog) values(?,?,?)";
        String produto = txtDescri.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Remove");
            pst.setString(2, "Produto  " + produto + " removido por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void adicionar() {

        String sql = "insert into partepecas(codigob,descricao,ref,grupo,subgrupo,marca,modelo,nserie,forne,precocusto,precomin,precovenda,precopromocao,qtde,undm,qtdeemb,local,prateleira,status,obs,datac,olx,codigoiolx,codigoolx,ml,codigoiml,codigoml,face,codigoiface,codigoface,eco,codigoieco,codigoeco,skufornecedor,imagem1,imagem2,imagem3,empresa,ncm,csticms,icms,cstpis,pis,cstcofins,cofins,cstipi,ipi,desoneracao,reducao,subtrib,origem,refclass,refunica,pn) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            String precoCad = txtPrecoCusto.getText();
            String precoMin = txtPrecoMin.getText();
            String precoVenda = txtPrecoVenda.getText();

            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCodeBar.getText());
            pst.setString(2, txtDescri.getText());
            pst.setString(3, cbRefGeral.getSelectedItem().toString());
            pst.setString(4, cbGrupo.getSelectedItem().toString());
            pst.setString(5, cbSubgrupo.getSelectedItem().toString());
            pst.setString(6, cbMarca.getSelectedItem().toString());
            pst.setString(7, cbModelo.getSelectedItem().toString());
            pst.setString(8, txtSerial.getText());
            pst.setString(9, cbFornecedores.getSelectedItem().toString());
            pst.setString(10, precoCad);
            pst.setString(11, precoMin);
            pst.setString(12, precoVenda);
            pst.setString(13, precoPromo.getText());
            pst.setInt(14, Integer.parseInt(spQTDE.getValue().toString()));
            pst.setString(15, cbMedida.getSelectedItem().toString());
            pst.setInt(16, Integer.parseInt(spQtEmb.getValue().toString()));
            pst.setString(17, cbLocal.getSelectedItem().toString());
            pst.setString(18, cbPrateleira.getSelectedItem().toString());
            pst.setString(19, cbStatus.getSelectedItem().toString());
            pst.setString(20, txtObs.getText());
            pst.setString(21, txtDataC.getText());
            pst.setString(22, anuncioOlx.getSelectedItem().toString());
            pst.setString(23, codAnuncioOlxInterno.getText());
            pst.setString(24, codAnuncioOlx.getText());
            pst.setString(25, anuncioML.getSelectedItem().toString());
            pst.setString(26, codAnuncioMLInterno.getText());
            pst.setString(27, codAnuncioML.getText());
            pst.setString(28, anuncioFace.getSelectedItem().toString());
            pst.setString(29, codAnuncioFaceInterno.getText());
            pst.setString(30, codAnuncioFace.getText());
            pst.setString(31, anuncioEco.getSelectedItem().toString());
            pst.setString(32, codAnuncioEcoInterno.getText());
            pst.setString(33, codAnuncioEco.getText());
            pst.setString(34, skuForne.getText());
            pst.setBytes(35, getImagem1());
            pst.setBytes(36, getImagem2());
            pst.setBytes(37, getImagem3());
            pst.setString(38, empresa.getText());
            pst.setString(39, txtNcm.getSelectedItem().toString());
            pst.setString(40, cbIcms.getSelectedItem().toString());
            pst.setString(41, txIcms.getText());
            pst.setString(42, cbCstPis.getSelectedItem().toString());
            pst.setString(43, Pis.getText());
            pst.setString(44, CstConfis.getSelectedItem().toString());
            pst.setString(45, txCofis.getText());
            pst.setString(46, cbCstIpi.getSelectedItem().toString());
            pst.setString(47, txIpi.getText());
            pst.setString(48, txMotivoD.getSelectedItem().toString());
            pst.setString(49, txReducao.getText());
            pst.setString(50, txSubst.getText());
            pst.setString(51, origem.getSelectedItem().toString());
            pst.setString(52, cbRefClass.getSelectedItem().toString());
            pst.setString(53, txtrefUnica.getText());
            pst.setString(54, partnumber.getText());

            if ((txtDescri.getText().isEmpty()) || (txtSerial.getText().isEmpty()) || (txtPrecoVenda.getText().isEmpty()) || txtrefUnica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Produto cadastrado com sucesso!");
                    noti.showNotification();
                    setcodabar();
                    setRefunica();
                    addLogCad();
                    txtSerial.setText(null);
                    txtSerial.requestFocus();
                }

            }
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao cadastrar o produto.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {

        String sql = "update partepecas set descricao=?,ref=?,grupo=?,subgrupo=?,marca=?,modelo=?,nserie=?,forne=?,precocusto=?,precomin=?,precovenda=?,precopromocao=?,qtde=?,undm=?,qtdeemb=?,local=?,prateleira=?,status=?,obs=?,olx=?,codigoiolx=?,codigoolx=?,ml=?,codigoiml=?,codigoml=?,face=?,codigoiface=?,codigoface=?,eco=?,codigoieco=?,codigoeco=?,skufornecedor=?,ncm=?,csticms=?,icms=?,cstpis=?,pis=?,cstcofins=?,cofins=?,cstipi=?,ipi=?,desoneracao=?,reducao=?,subtrib=?,origem=?,refclass=?,refunica=?,pn=? where idpartepecas=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtDescri.getText());
            pst.setString(2, cbRefGeral.getSelectedItem().toString());
            pst.setString(3, cbGrupo.getSelectedItem().toString());
            pst.setString(4, cbSubgrupo.getSelectedItem().toString());
            pst.setString(5, cbMarca.getSelectedItem().toString());
            pst.setString(6, cbModelo.getSelectedItem().toString());
            pst.setString(7, txtSerial.getText());
            pst.setString(8, cbFornecedores.getSelectedItem().toString());
            pst.setString(9, txtPrecoCusto.getText());
            pst.setString(10, txtPrecoMin.getText());
            pst.setString(11, txtPrecoVenda.getText());
            pst.setString(12, precoPromo.getText());
            pst.setInt(13, Integer.parseInt(spQTDE.getValue().toString()));
            pst.setString(14, cbMedida.getSelectedItem().toString());
            pst.setInt(15, Integer.parseInt(spQtEmb.getValue().toString()));
            pst.setString(16, cbLocal.getSelectedItem().toString());
            pst.setString(17, cbPrateleira.getSelectedItem().toString());
            pst.setString(18, cbStatus.getSelectedItem().toString());
            pst.setString(19, txtObs.getText());
            pst.setString(20, anuncioOlx.getSelectedItem().toString());
            pst.setString(21, codAnuncioOlxInterno.getText());
            pst.setString(22, codAnuncioOlx.getText());
            pst.setString(23, anuncioML.getSelectedItem().toString());
            pst.setString(24, codAnuncioMLInterno.getText());
            pst.setString(25, codAnuncioML.getText());
            pst.setString(26, anuncioFace.getSelectedItem().toString());
            pst.setString(27, codAnuncioFaceInterno.getText());
            pst.setString(28, codAnuncioFace.getText());
            pst.setString(29, anuncioEco.getSelectedItem().toString());
            pst.setString(30, codAnuncioEcoInterno.getText());
            pst.setString(31, codAnuncioEco.getText());
            pst.setString(32, skuForne.getText());
            pst.setString(33, txtNcm.getSelectedItem().toString());
            pst.setString(34, cbIcms.getSelectedItem().toString());
            pst.setString(35, txIcms.getText());
            pst.setString(36, cbCstPis.getSelectedItem().toString());
            pst.setString(37, Pis.getText());
            pst.setString(38, CstConfis.getSelectedItem().toString());
            pst.setString(39, txCofis.getText());
            pst.setString(40, cbCstIpi.getSelectedItem().toString());
            pst.setString(41, txIpi.getText());
            pst.setString(42, txMotivoD.getSelectedItem().toString());
            pst.setString(43, txReducao.getText());
            pst.setString(44, txSubst.getText());
            pst.setString(45, origem.getSelectedItem().toString());
            pst.setString(46, cbRefClass.getSelectedItem().toString());
            pst.setString(47, txtrefUnica.getText());
            pst.setString(48, partnumber.getText());

            pst.setString(49, txtID.getText());

            if ((txtDescri.getText().isEmpty()) || (txtSerial.getText().isEmpty()) || (txtPrecoVenda.getText().isEmpty()) || txtrefUnica.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados do produto alterados com sucesso!");
                    noti.showNotification();
                    addLogEdit();

                }
            }
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar os dados do produto.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este produto?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from partepecas where idpartepecas=?";
            try {

                if ((txtID.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum produto selecionado!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados do produto removidos com sucesso!");
                    noti.showNotification();
                    addLogRemove();

                }

            } catch (HeadlessException | SQLException e) {

                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover o produto.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void refresh() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar os campos?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            try {
                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!");
                noti.showNotification();
                txtID.setText(null);
                txtCodeBar.setText(null);
                txtDescri.setText(null);
                txtSerial.setText(null);
                txtObs.setText(null);
                txtDescri.setText(null);
                txtSerial.setText(null);
                precoPromo.setText(null);
                txtObs.setText(null);
                txtDataC.setText(null);
                codAnuncioOlxInterno.setText(null);
                codAnuncioOlx.setText(null);
                codAnuncioMLInterno.setText(null);
                codAnuncioML.setText(null);
                codAnuncioFaceInterno.setText(null);
                codAnuncioFace.setText(null);
                codAnuncioEcoInterno.setText(null);
                codAnuncioEco.setText(null);
                skuForne.setText(null);
                txIcms.setText(null);
                Pis.setText(null);
                txCofis.setText(null);
                txIpi.setText(null);
                codAnuncioOlxInterno.setEnabled(false);
                gerarCodOLX.setEnabled(false);
                codAnuncioOlx.setEnabled(false);
                codAnuncioOlx.setText("");
                codAnuncioOlxInterno.setText("");
                codAnuncioMLInterno.setEnabled(false);
                gerarCodML.setEnabled(false);
                codAnuncioML.setEnabled(false);
                codAnuncioML.setText("");
                codAnuncioMLInterno.setText("");
                codAnuncioFaceInterno.setEnabled(false);
                gerarCodFace.setEnabled(false);
                codAnuncioFace.setEnabled(false);
                codAnuncioFaceInterno.setText("");
                codAnuncioFace.setText("");
                codAnuncioEcoInterno.setEnabled(false);
                gerarCodEco.setEnabled(false);
                codAnuncioEco.setEnabled(false);
                codAnuncioEcoInterno.setText("");
                codAnuncioEco.setText("");
                anuncioOlx.setSelectedItem("NAO");
                anuncioML.setSelectedItem("NAO");
                anuncioFace.setSelectedItem("NAO");
                anuncioEco.setSelectedItem("NAO");
                txtPrecoCusto.setText("");
                txtPrecoMin.setText("");
                txtPrecoVenda.setText("");
                radioPromo.setSelected(false);
                precoPromo.setEnabled(false);
                precoPromo.setFormatterFactory(null);
                precoPromo.setText("0,0");
                viewImage1PP.setIcon(null);
                viewImage2PP.setIcon(null);
                viewImage3PP.setIcon(null);
                uploadimg1.setEnabled(true);
//                uploadimg2.setEnabled(true);
//                uploadimg3.setEnabled(true);
                Cancelimg1.setEnabled(true);
                Cancelimg2.setEnabled(true);
                Cancelimg3.setEnabled(true);
                habilitar1PP.setSelected(false);
                habilitar2PP.setSelected(false);
                habilitar3PP.setSelected(false);
                habilitar1PP.setVisible(false);
                habilitar2PP.setVisible(false);
                habilitar3PP.setVisible(false);
                AlterImage1.setVisible(false);
                AlterImage2.setVisible(false);
                AlterImage3.setVisible(false);
                SetDate();
                txReducao.setText(null);
                txSubst.setText(null);
                btCad.setEnabled(true);
                btEdit.setEnabled(false);
                btExcluir.setEnabled(false);
                btSearch.setEnabled(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtID = new javax.swing.JTextField();
        Painel2 = new javax.swing.JPanel();
        viewImage2 = new javax.swing.JLabel();
        Painel4 = new javax.swing.JPanel();
        viewImage4 = new javax.swing.JLabel();
        Painel3 = new javax.swing.JPanel();
        viewImage3 = new javax.swing.JLabel();
        FormFiscais = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        cbIcms = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cbCstPis = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        cbCstIpi = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        CstConfis = new javax.swing.JComboBox<>();
        txtNcm = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        txIcms = new javax.swing.JFormattedTextField();
        Pis = new javax.swing.JFormattedTextField();
        txCofis = new javax.swing.JFormattedTextField();
        txIpi = new javax.swing.JFormattedTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        txReducao = new javax.swing.JTextField();
        txMotivoD = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txSubst = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        origem = new javax.swing.JComboBox<>();
        descricaoncm = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btSearch1 = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btCad = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtDescri = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbGrupo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbFornecedores = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        txcusto = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtPrecoCusto = new javax.swing.JFormattedTextField();
        txtPrecoMin = new javax.swing.JFormattedTextField();
        txtPrecoVenda = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        precoPromo = new javax.swing.JFormattedTextField();
        radioPromo = new javax.swing.JRadioButton();
        cbMarca = new javax.swing.JComboBox<>();
        cbSubgrupo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtSerial = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cbModelo = new javax.swing.JComboBox<>();
        txtCodeBar = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        skuForne = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel51 = new javax.swing.JLabel();
        cbRefGeral = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        txtrefUnica = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        partnumber = new javax.swing.JTextField();
        cbRefClass = new javax.swing.JComboBox<>();
        descri1 = new javax.swing.JLabel();
        descri2 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        spQTDE = new javax.swing.JSpinner();
        cbMedida = new javax.swing.JComboBox<>();
        spQtEmb = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cbLocal = new javax.swing.JComboBox<>();
        cbPrateleira = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        txtDataC = new javax.swing.JTextField();
        openFiscais = new javax.swing.JToggleButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        anuncioOlx = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        codAnuncioOlxInterno = new javax.swing.JTextField();
        gerarCodOLX = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        codAnuncioOlx = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        codAnuncioML = new javax.swing.JTextField();
        codAnuncioMLInterno = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        anuncioML = new javax.swing.JComboBox<>();
        gerarCodML = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        codAnuncioFace = new javax.swing.JTextField();
        codAnuncioFaceInterno = new javax.swing.JTextField();
        anuncioFace = new javax.swing.JComboBox<>();
        gerarCodFace = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        anuncioEco = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        codAnuncioEcoInterno = new javax.swing.JTextField();
        gerarCodEco = new javax.swing.JButton();
        codAnuncioEco = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        ScrollImage = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
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

        txtID.setText("jTextField2");

        Painel2.setBackground(new java.awt.Color(255, 255, 255));
        Painel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 167, 39), 4));
        Painel2.setPreferredSize(new java.awt.Dimension(300, 0));

        viewImage2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewImage2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewImage2MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout Painel2Layout = new javax.swing.GroupLayout(Painel2);
        Painel2.setLayout(Painel2Layout);
        Painel2Layout.setHorizontalGroup(
            Painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Painel2Layout.setVerticalGroup(
            Painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );

        Painel4.setBackground(new java.awt.Color(255, 255, 255));
        Painel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 167, 39), 4));
        Painel4.setPreferredSize(new java.awt.Dimension(300, 0));

        viewImage4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewImage4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewImage4MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout Painel4Layout = new javax.swing.GroupLayout(Painel4);
        Painel4.setLayout(Painel4Layout);
        Painel4Layout.setHorizontalGroup(
            Painel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Painel4Layout.setVerticalGroup(
            Painel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage4, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        );

        Painel3.setBackground(new java.awt.Color(255, 255, 255));
        Painel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(147, 167, 39), 4));
        Painel3.setPreferredSize(new java.awt.Dimension(300, 0));

        viewImage3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewImage3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewImage3MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout Painel3Layout = new javax.swing.GroupLayout(Painel3);
        Painel3.setLayout(Painel3Layout);
        Painel3Layout.setHorizontalGroup(
            Painel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        );
        Painel3Layout.setVerticalGroup(
            Painel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

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

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(915, 520));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel38.setText("NCM*");

        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton3.setText("Fechar e manter dados");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel39.setText("CST ICMS");

        cbIcms.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbIcms.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "0 – Nacional, exceto as indicadas nos códigos 3, 4, 5 e 8;", "1 – Estrangeira – Importação direta, exceto a indicada no código 6;", "2 – Estrangeira – Adquirida no mercado interno, exceto a indicada no código 7;", "3 – Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40% (quarenta por cento) e igual ou inferior a 70% (setenta por cento) ;", "4 – Nacional, cuja produção tenha sido feita em conformidade com os processos produtivos básicos de que tratam o Decreto-Lei nº 288/1967 , e as Leis nºs 8.248/1991, 8.387/1991, 10.176/2001 e 11.484/2007;", "5 – Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40%;", "6 – Estrangeira – Importação direta, sem similar nacional, constante em lista de Resolução Camex e gás natural;", "7 – Estrangeira – Adquirida no mercado interno, sem similar nacional, constante em lista de Resolução Camex e gás natural.", "8 – Nacional – Mercadoria ou bem com Conteúdo de Importação superior a 70% (setenta por cento)." }));

        jLabel40.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel40.setText("ICMS");

        jLabel41.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel41.setText("CST PIS");

        cbCstPis.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbCstPis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01- Operação Tributável com Alíquota Básica", "02- Operação Tributável com Alíquota Diferenciada", "03- Operação Tributável com Alíquota por Unidade de Medida de Produto", "04- Operação Tributável Monofásica – Revenda a Alíquota Zero", "05- Operação Tributável por Substituição Tributária", "06- Operação Tributável a Alíquota Zero", "07- Operação Isenta da Contribuição", "08- Operação sem Incidência da Contribuição", "09- Operação com Suspensão da Contribuição", "49- Outras Operações de Saída", "50- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "51- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno", "52- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita de Exportação", "53- Operação com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "54- Operação com Direito a Crédito – Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "55- Operação com Direito a Crédito – Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "56- peração com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "60- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "61- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno", "62- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação", "63- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "64- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "65- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "66- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "67- Crédito Presumido – Outras Operações", "70- Operação de Aquisição sem Direito a Crédito", "71- Operação de Aquisição com Isenção", "72- Operação de Aquisição com Suspensão", "73- Operação de Aquisição a Alíquota Zero", "74- Operação de Aquisição sem Incidência da Contribuição", "75- Operação de Aquisição por Substituição Tributária", "98- Outras Operações de Entrada", "99- Outras Operações" }));

        jLabel42.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel42.setText("PIS");

        jLabel43.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel43.setText("CST COFINS");

        jLabel44.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel44.setText("COFINS");

        jLabel45.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel45.setText("CST IPI");

        cbCstIpi.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbCstIpi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "0- Entrada com Recuperação de Crédito", "1- Entrada Tributável com Alíquota Zero", "2- Entrada Isenta", "3- Entrada Não-Tributada", "4- Entrada Imune", "5- Entrada com Suspensão", "49- Outras Entradas", "50- Saída Tributada", "51- Saída Tributável com Alíquota Zero", "52- Saída Isenta", "53- Saída Não-Tributada", "54- Saída Imune", "55- Saída com Suspensão", "99- Outras Saídas" }));

        jLabel46.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel46.setText("IPI");

        CstConfis.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CstConfis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "01- Operação Tributável com Alíquota Básica", "02- Operação Tributável com Alíquota Diferenciada", "03- Operação Tributável com Alíquota por Unidade de Medida de Produto", "04- Operação Tributável Monofásica – Revenda a Alíquota Zero", "05- Operação Tributável por Substituição Tributária", "06- Operação Tributável a Alíquota Zero", "07- Operação Isenta da Contribuição", "08- Operação sem Incidência da Contribuição", "09- Operação com Suspensão da Contribuição", "49- Outras Operações de Saída", "50- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "51- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita Não Tributada no Mercado Interno", "52- Operação com Direito a Crédito – Vinculada Exclusivamente a Receita de Exportação", "53- Operação com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "54- Operação com Direito a Crédito – Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "55- Operação com Direito a Crédito – Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "56- peração com Direito a Crédito – Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "60- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Tributada no Mercado Interno", "61- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita Não-Tributada no Mercado Interno", "62- Crédito Presumido – Operação de Aquisição Vinculada Exclusivamente a Receita de Exportação", "63- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno", "64- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas no Mercado Interno e de Exportação", "65- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Não-Tributadas no Mercado Interno e de Exportação", "66- Crédito Presumido – Operação de Aquisição Vinculada a Receitas Tributadas e Não-Tributadas no Mercado Interno, e de Exportação", "67- Crédito Presumido – Outras Operações", "70- Operação de Aquisição sem Direito a Crédito", "71- Operação de Aquisição com Isenção", "72- Operação de Aquisição com Suspensão", "73- Operação de Aquisição a Alíquota Zero", "74- Operação de Aquisição sem Incidência da Contribuição", "75- Operação de Aquisição por Substituição Tributária", "98- Outras Operações de Entrada", "99- Outras Operações" }));

        txtNcm.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        AutoCompleteDecorator.decorate(txtNcm);
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

        jPanel11.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        jLabel47.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel47.setText("MOTIVO DA DESONERAÇÃO");

        txReducao.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txMotivoD.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txMotivoD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "Táxi | Produto agropecuário/Uso na agropecuária", "Frotista/Locadora", "Diplomático/Consular", "Utilitários e Motocicletas da Amazônia Ocidental e Áreas de Livre Comércio", "SUFRAMA", "Venda a Órgão Público", "Outros", "Deficiente Condutor", "Deficiente Não Condutor", "Órgão de fomento e desenvolvimento agropecuário" }));

        jLabel48.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel48.setText("REDUÇÃO");

        jLabel49.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel49.setText("SUBSTITUIÇÃO TRIBUTÁRIA");

        txSubst.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel50.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel50.setText("ORIGEM DO PRODUTO");

        origem.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        origem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "0 - Nacional", "1 - Importado", "2 - Fabricação Propria" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel50)
                        .addGap(53, 53, 53)
                        .addComponent(origem, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel49)
                            .addComponent(jLabel48))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txReducao, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txSubst, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txMotivoD, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(origem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txMotivoD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(txSubst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(txReducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        descricaoncm.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        descricaoncm.setForeground(new java.awt.Color(153, 153, 153));
        descricaoncm.setText("$descricao");

        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        jButton4.setText("Fechar e apagar dados");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel9Layout.createSequentialGroup()
                                            .addComponent(jLabel39)
                                            .addGap(24, 24, 24))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                            .addComponent(jLabel38)
                                            .addGap(45, 45, 45)))
                                    .addComponent(jLabel41))
                                .addGap(14, 14, 14)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbCstPis, 0, 1, Short.MAX_VALUE)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addComponent(txtNcm, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(descricaoncm))
                                            .addComponent(cbIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel43))
                                .addGap(22, 22, 22)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CstConfis, 0, 1, Short.MAX_VALUE)
                                    .addComponent(cbCstIpi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txIcms, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(Pis)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel44)
                                    .addComponent(jLabel46))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txCofis, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addComponent(txIpi)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txtNcm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(descricaoncm)))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(cbIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(txIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(cbCstPis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(Pis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel44)
                    .addComponent(CstConfis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txCofis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(cbCstIpi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)
                    .addComponent(txIpi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addGap(84, 84, 84))
        );

        javax.swing.GroupLayout FormFiscaisLayout = new javax.swing.GroupLayout(FormFiscais.getContentPane());
        FormFiscais.getContentPane().setLayout(FormFiscaisLayout);
        FormFiscaisLayout.setHorizontalGroup(
            FormFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FormFiscaisLayout.setVerticalGroup(
            FormFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowDeiconified(java.awt.event.WindowEvent evt) {
                formWindowDeiconified(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CADASTRAR PRODUTOS");

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
                .addGap(5, 5, 5)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btSearch1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        btSearch1.setText(" Limpar campos");
        btSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch1ActionPerformed(evt);
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

        btExcluir.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        btExcluir.setText("Excluir");
        btExcluir.setEnabled(false);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
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

        btCad.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionarUser.png"))); // NOI18N
        btCad.setText("Cadastrar");
        btCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadActionPerformed(evt);
            }
        });

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("DESCRIÇÃO");

        txtDescri.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("COD. BARRAS");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("GRUPO");

        cbGrupo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbGrupo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbGrupoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGrupoActionPerformed(evt);
            }
        });
        AutoCompleteDecorator.decorate(cbGrupo);

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("MARCA");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("MODELO");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel11.setText("FORNECEDOR");

        cbFornecedores.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        AutoCompleteDecorator.decorate(cbFornecedores);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Valores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        txcusto.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txcusto.setText("PREÇO DE CUSTO");

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("PREÇO DE VENDA");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("PREÇO MIN. DE VENDA");

        txtPrecoCusto.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtPrecoMin.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txtPrecoVenda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtPrecoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoVendaActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("PREÇO DE PROMOÇÃO");

        precoPromo.setText("0,0");
        precoPromo.setEnabled(false);
        precoPromo.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        precoPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precoPromoActionPerformed(evt);
            }
        });

        radioPromo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        radioPromo.setText("Colocar em promoção");
        radioPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPromoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(txcusto)
                    .addComponent(jLabel16))
                .addGap(45, 45, 45)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecoMin)
                    .addComponent(txtPrecoCusto)
                    .addComponent(txtPrecoVenda))
                .addGap(183, 183, 183)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioPromo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(precoPromo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txcusto))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtPrecoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(radioPromo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(precoPromo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        cbSubgrupo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(cbSubgrupo);

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("SUBGRUPO");

        txtSerial.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtSerial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSerialKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("SERIAL NUMBER");

        cbModelo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(cbModelo);

        txtCodeBar.setEnabled(false);
        txtCodeBar.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("REF. GERAL");

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("SKU FORNECEDOR");

        skuForne.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/atualizada.png"))); // NOI18N
        jButton2.setToolTipText("Atualizar dados");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel51.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel51.setText("REF. CLASSIFICATIVA");

        cbRefGeral.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(cbRefGeral);
        cbRefGeral.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbRefGeralPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel52.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel52.setText("REF. ÚNICA");

        txtrefUnica.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtrefUnica.setEnabled(false);

        jLabel53.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel53.setText("P/N");

        partnumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

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

        descri1.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        descri1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        descri1.setText(" ");
        descri1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        descri1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        descri1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        descri1.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        descri2.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        descri2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        descri2.setText(" ");
        descri2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        descri2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        descri2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        descri2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel54.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jLabel54.setText("(EAN-13) gerado automaticamente");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel11))
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtCodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel54))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbSubgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(skuForne, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel51)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                        .addComponent(cbRefClass, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(descri1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(descri2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel52)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtrefUnica, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel53)
                                            .addComponent(jLabel12))
                                        .addGap(84, 84, 84)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbRefGeral, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(partnumber)))))
                            .addComponent(txtDescri))
                        .addGap(31, 31, 31))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCodeBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDescri, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53)
                            .addComponent(partnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cbRefGeral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(descri1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(cbRefClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(descri2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(txtrefUnica, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(cbSubgrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(cbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cbFornecedores, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(skuForne, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jTabbedPane1.addTab("Dados cadastrais", jPanel4);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Estoque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel20.setText("QTDE. EM ESTOQUE");

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("UNID. DE MEDIDA");

        spQTDE.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        cbMedida.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbMedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UND", "FRD", "PCT", "CX" }));

        spQtEmb.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("QTDE. NA EMBALAGEM");

        jLabel64.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel64.setText("PRATELEIRA/GAVETA");

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel31.setText("LOCAL EM ESTOQUE");

        cbLocal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbLocal.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbLocalPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLocalActionPerformed(evt);
            }
        });

        cbPrateleira.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbPrateleira.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbPrateleiraPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20)
                    .addComponent(jLabel23)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(39, 39, 39)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbMedida, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(spQtEmb)
                        .addComponent(spQTDE, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(spQTDE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(cbMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(spQtEmb, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel64))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Outras informações", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel21.setText("OBSERVAÇÃO DO PRODUTO");

        txtObs.setColumns(20);
        txtObs.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtObs.setRows(5);
        jScrollPane1.setViewportView(txtObs);

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel25.setText("STATUS DO PRODUTO");

        cbStatus.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Novo (ainda não utilizado)", "Seminovo (usado, mas em excelente condição)", "Recondicionado (foi consertado e restaurado à condição de novo)", "Usado (em bom estado, mas pode apresentar sinais de uso)", "Danificado (tem algum tipo de dano ou defeito)", "Vencido (expirou a data de validade ou prazo de garantia)", "Quebrado (não funciona e precisa de reparos)", "Inoperante (não funciona e não pode ser reparado)", "Refurbished (recondicionado com peças originais de fábrica)", "Com defeito (tem um problema específico que pode ou não ser reparado)" }));

        jLabel63.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel63.setText("DATA DE CADASTRO");

        txtDataC.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDataC.setEnabled(false);

        openFiscais.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        openFiscais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/impostos.png"))); // NOI18N
        openFiscais.setText("Dados fiscais");
        openFiscais.setToolTipText("Dados fiscais");
        openFiscais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFiscaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openFiscais))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)
                            .addComponent(txtDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 280, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(openFiscais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel21)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel63)
                .addGap(15, 15, 15)
                .addComponent(txtDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mais informações", jPanel5);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/olx32x.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/ml32x.png"))); // NOI18N

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("ANUNCIADO NA OLX:");

        anuncioOlx.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anuncioOlx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        anuncioOlx.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                anuncioOlxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel26.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        codAnuncioOlxInterno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioOlxInterno.setEnabled(false);

        gerarCodOLX.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        gerarCodOLX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        gerarCodOLX.setText(" Gerar cod.");
        gerarCodOLX.setEnabled(false);
        gerarCodOLX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarCodOLXActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel27.setText("CÓDIGO OLX ANÚNCIO:");

        codAnuncioOlx.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioOlx.setEnabled(false);

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel28.setText("CÓDIGO ML ANÚNCIO:");

        codAnuncioML.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioML.setEnabled(false);

        codAnuncioMLInterno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioMLInterno.setEnabled(false);

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel29.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel30.setText("ANUNCIADO NO ML:");

        anuncioML.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anuncioML.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        anuncioML.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                anuncioMLPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        gerarCodML.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        gerarCodML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        gerarCodML.setText(" Gerar cod.");
        gerarCodML.setEnabled(false);
        gerarCodML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarCodMLActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/facebook.png"))); // NOI18N

        jLabel32.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel32.setText("ANUNCIADO NO FACEBOOK:");

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel33.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel34.setText("CÓDIGO FB ANÚNCIO:");

        codAnuncioFace.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioFace.setEnabled(false);

        codAnuncioFaceInterno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioFaceInterno.setEnabled(false);

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

        gerarCodFace.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        gerarCodFace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        gerarCodFace.setText(" Gerar cod.");
        gerarCodFace.setEnabled(false);
        gerarCodFace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarCodFaceActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/comercio-eletronico.png"))); // NOI18N

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel35.setText("ANUNCIADO NO E-COMMERCE:");

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

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel36.setText("CÓDIGO INTERNO DO ANÚNCIO:");

        codAnuncioEcoInterno.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioEcoInterno.setEnabled(false);

        gerarCodEco.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        gerarCodEco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/leitura-de-codigo-de-barras.png"))); // NOI18N
        gerarCodEco.setText(" Gerar cod.");
        gerarCodEco.setEnabled(false);
        gerarCodEco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarCodEcoActionPerformed(evt);
            }
        });

        codAnuncioEco.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        codAnuncioEco.setEnabled(false);

        jLabel37.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel37.setText("CÓDIGO EC ANÚNCIO:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(codAnuncioML, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(anuncioML, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codAnuncioMLInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(gerarCodML))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(jLabel24)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(codAnuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(anuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(codAnuncioOlxInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gerarCodOLX))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(codAnuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel32))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(anuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codAnuncioFaceInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(gerarCodFace))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(codAnuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel35))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(anuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codAnuncioEcoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(gerarCodEco)))
                .addContainerGap(221, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(anuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(codAnuncioOlxInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gerarCodOLX))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(codAnuncioOlx, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(anuncioML, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(codAnuncioMLInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gerarCodML))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(codAnuncioML, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(anuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(codAnuncioFaceInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gerarCodFace))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(codAnuncioFace, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(anuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(codAnuncioEcoInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gerarCodEco))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(codAnuncioEco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados sobre anuncios", jPanel6);

        ScrollImage.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Painel1ImagePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Painel2ImagePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(habilitar1PP)
                            .addComponent(AlterImage1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 528, Short.MAX_VALUE)
                        .addComponent(Cancelimg1))
                    .addComponent(Painel3ImagePP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(habilitar2PP)
                            .addComponent(AlterImage2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelimg2))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(habilitar3PP)
                            .addComponent(AlterImage3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelimg3))
                    .addComponent(uploadimg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uploadimg1)
                .addGap(18, 18, 18)
                .addComponent(Painel1ImagePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cancelimg1)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(habilitar1PP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AlterImage1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Painel2ImagePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(habilitar2PP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AlterImage2))
                    .addComponent(Cancelimg2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Painel3ImagePP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(habilitar3PP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(AlterImage3))
                    .addComponent(Cancelimg3))
                .addContainerGap())
        );

        ScrollImage.setViewportView(jPanel10);

        jTabbedPane1.addTab("Imagens do produto", ScrollImage);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btSearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExcluir)
                        .addGap(15, 15, 15)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCad, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCad)
                    .addComponent(btSearch)
                    .addComponent(btEdit)
                    .addComponent(btExcluir)
                    .addComponent(btSearch1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch1ActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_btSearch1ActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        SearchPartePecas spp = new SearchPartePecas();
        spp.setVisible(true);
    }//GEN-LAST:event_btSearchActionPerformed

    private void btCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadActionPerformed
        adicionar();
    }//GEN-LAST:event_btCadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        selectProduct.setVisible(true);
        selectProduct.setAlwaysOnTop(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btExcluirActionPerformed

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
        // TODO add your handling code here:
        if (viewImage1PP.getIcon() != null) {
            viewImage1PP.setIcon(null);
            imagemA = null;
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");
        }

    }//GEN-LAST:event_Cancelimg1ActionPerformed

    private void viewImage2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage2MouseClicked

    private void viewImage2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage2MouseEntered

    private void viewImage3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage3MouseClicked

    private void viewImage3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage3MouseEntered

    private void viewImage4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage4MouseClicked

    private void viewImage4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage4MouseEntered

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        menuProdutos.setEnabled(true);
        FormFiscais.setVisible(false);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        menuProdutos.setEnabled(false);

    }//GEN-LAST:event_formWindowOpened

    private void gerarCodEcoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarCodEcoActionPerformed
        // TODO add your handling code here:
        setCodEco();
    }//GEN-LAST:event_gerarCodEcoActionPerformed

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

    private void gerarCodFaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarCodFaceActionPerformed
        // TODO add your handling code here:
        setCodFace();
    }//GEN-LAST:event_gerarCodFaceActionPerformed

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

    private void gerarCodMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarCodMLActionPerformed
        // TODO add your handling code here:
        setCodML();
    }//GEN-LAST:event_gerarCodMLActionPerformed

    private void anuncioMLPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_anuncioMLPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
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
    }//GEN-LAST:event_anuncioMLPopupMenuWillBecomeInvisible

    private void gerarCodOLXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarCodOLXActionPerformed
        // TODO add your handling code here:
        setCodOLX();
    }//GEN-LAST:event_gerarCodOLXActionPerformed

    private void anuncioOlxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_anuncioOlxPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
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
    }//GEN-LAST:event_anuncioOlxPopupMenuWillBecomeInvisible

    private void cbPrateleiraPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbPrateleiraPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
//        GetBox();
    }//GEN-LAST:event_cbPrateleiraPopupMenuWillBecomeInvisible

    private void cbLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLocalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLocalActionPerformed

    private void cbLocalPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLocalPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbPrateleira.removeAllItems();
        GetPrats();
        if (cbPrateleira.getSelectedItem() == null) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cadastre uma PRATELEIRA para o LOCAL escolhido!!");
            noti.showNotification();
        }
    }//GEN-LAST:event_cbLocalPopupMenuWillBecomeInvisible

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        cbGrupo.removeAllItems();
        cbMarca.removeAllItems();
        cbFornecedores.removeAllItems();
        cbLocal.removeAllItems();
        cbSubgrupo.removeAllItems();
        cbModelo.removeAllItems();
        txtNcm.removeAllItems();
        cbRefGeral.removeAllItems();
        cbRefClass.removeAllItems();
        ListarLocais();
        ListarGrupos();
        ListarMarcas();
        ListarForne();
        Getncm();
        ListarRefGeral();
        GetRefClass();
        if (cbRefClass.getSelectedItem() == null) {
            cbRefClass.setEnabled(false);
        } else {
            cbRefClass.setEnabled(true);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbMarcaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbMarcaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbModelo.removeAllItems();
        GetModels();
        if (cbModelo.getSelectedItem() == null) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cadastre um MODELO para a MARCA escolhida!!");
            noti.showNotification();
        } else {
        }
    }//GEN-LAST:event_cbMarcaPopupMenuWillBecomeInvisible

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

    private void txtPrecoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaActionPerformed

    private void cbGrupoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbGrupoPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbSubgrupo.removeAllItems();
        GetSubGrupos();
        if (cbSubgrupo.getSelectedItem() == null) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cadastre um SUBGRUPO para o GRUPO escolhido!!");
            noti.showNotification();
        } else {
        }
    }//GEN-LAST:event_cbGrupoPopupMenuWillBecomeInvisible

    private void Cancelimg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancelimg2ActionPerformed
        // TODO add your handling code here:
        if (viewImage2PP.getIcon() != null) {
            viewImage2PP.setIcon(null);
            imagemB = null;
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");
        }
    }//GEN-LAST:event_Cancelimg2ActionPerformed

    private void Cancelimg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancelimg3ActionPerformed
        // TODO add your handling code here:
        if (viewImage3PP.getIcon() != null) {
            viewImage3PP.setIcon(null);
            imagemC = null;
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");
        }
    }//GEN-LAST:event_Cancelimg3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        FormFiscais.setVisible(false);
        openFiscais.setSelected(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowDeiconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeiconified
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowDeiconified

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
        FormFiscais.setVisible(false);
        openFiscais.setSelected(false);
    }//GEN-LAST:event_formWindowIconified

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
        String sql = "update partepecas set imagem1=? where idpartepecas=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setBytes(1, getImagem1());
            pst.setString(2, txtID.getText());

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                JOptionPane.showMessageDialog(null, "Anexo 1 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
                AlterImage1.setVisible(false);
                habilitar1PP.setSelected(false);
                uploadimg1.setEnabled(false);
                Cancelimg1.setEnabled(false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 1!!");
        }
    }//GEN-LAST:event_AlterImage1ActionPerformed

    private void AlterImage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage2ActionPerformed
        // TODO add your handling code here:
        String sql = "update partepecas set imagem1=? where idpartepecas=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setBytes(1, getImagem2());
            pst.setString(2, txtID.getText());

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                JOptionPane.showMessageDialog(null, "Anexo 2 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
                AlterImage2.setVisible(false);
                habilitar2PP.setSelected(false);
                Cancelimg2.setEnabled(false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 2!!");
        }
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
        String sql = "update partepecas set imagem3=? where idpartepecas=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setBytes(1, getImagem3());
            pst.setString(2, txtID.getText());

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                JOptionPane.showMessageDialog(null, "Anexo 3 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
                AlterImage3.setVisible(false);
                habilitar3PP.setSelected(false);
                Cancelimg3.setEnabled(false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 3!!");
        }
    }//GEN-LAST:event_AlterImage3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        cadCMS cadc = new cadCMS();
        cadc.setVisible(true);
        cadc.setAlwaysOnTop(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void FormFiscaisWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_FormFiscaisWindowClosed
        // TODO add your handling code here:
        openFiscais.setSelected(false);
    }//GEN-LAST:event_FormFiscaisWindowClosed

    private void FormFiscaisWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_FormFiscaisWindowClosing
        // TODO add your handling code here:
        openFiscais.setSelected(false);
    }//GEN-LAST:event_FormFiscaisWindowClosing

    private void txtNcmPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_txtNcmPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (txtNcm.getSelectedItem().equals("--")) {
            descricaoncm.setVisible(false);
        } else {
            descricaoncm.setVisible(true);
            chaangedescri();

        }
    }//GEN-LAST:event_txtNcmPopupMenuWillBecomeInvisible

    private void cbRefGeralPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbRefGeralPopupMenuWillBecomeInvisible

        cbRefClass.removeAllItems();
        GetRefClass();
        getDescriGeral();
        if (cbRefClass.getSelectedItem() == null) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cadastre uma Referência classificativa!!");
            noti.showNotification();
            cbRefClass.setEnabled(false);
            txtrefUnica.setText(null);
        } else {
            cbRefClass.setEnabled(true);
        }

    }//GEN-LAST:event_cbRefGeralPopupMenuWillBecomeInvisible

    private void cbRefClassPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbRefClassPopupMenuWillBecomeInvisible
        setRefunica();

    }//GEN-LAST:event_cbRefClassPopupMenuWillBecomeInvisible

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cbGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbGrupoActionPerformed

    private void openFiscaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFiscaisActionPerformed
        // TODO add your handling code here:
        if (openFiscais.isSelected()) {
            FormFiscais.setVisible(true);
            FormFiscais.setLocationRelativeTo(null);
        } else {
            FormFiscais.setVisible(false);
        }
    }//GEN-LAST:event_openFiscaisActionPerformed

    private void txtSerialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerialKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPressTime <= 300) { // Verifica o tempo entre os cliques
                adicionar();
            }
            lastPressTime = currentTime;
        }
    }//GEN-LAST:event_txtSerialKeyPressed

    private void viewImage1PPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage1PPMouseClicked

    }//GEN-LAST:event_viewImage1PPMouseClicked

    private void viewImage1PPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewImage1PPMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewImage1PPMousePressed

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
            java.util.logging.Logger.getLogger(TelaCadPartePecas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadPartePecas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadPartePecas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadPartePecas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaCadPartePecas().setVisible(true);

                } catch (BadLocationException ex) {
                    Logger.getLogger(TelaCadPartePecas.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton AlterImage1;
    public static javax.swing.JButton AlterImage2;
    public static javax.swing.JButton AlterImage3;
    public static javax.swing.JButton Cancelimg1;
    public static javax.swing.JButton Cancelimg2;
    public static javax.swing.JButton Cancelimg3;
    public static javax.swing.JComboBox<String> CstConfis;
    private javax.swing.JDialog FormFiscais;
    public static javax.swing.JPanel Painel1ImagePP;
    private javax.swing.JPanel Painel2;
    public static javax.swing.JPanel Painel2ImagePP;
    private javax.swing.JPanel Painel3;
    public static javax.swing.JPanel Painel3ImagePP;
    private javax.swing.JPanel Painel4;
    public static javax.swing.JFormattedTextField Pis;
    private javax.swing.JScrollPane ScrollImage;
    public static javax.swing.JComboBox<String> anuncioEco;
    public static javax.swing.JComboBox<String> anuncioFace;
    public static javax.swing.JComboBox<String> anuncioML;
    public static javax.swing.JComboBox<String> anuncioOlx;
    public static javax.swing.JButton btCad;
    public static javax.swing.JButton btEdit;
    public static javax.swing.JButton btExcluir;
    public static javax.swing.JButton btSearch;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JComboBox<String> cbCstIpi;
    public static javax.swing.JComboBox<String> cbCstPis;
    public static javax.swing.JComboBox<String> cbFornecedores;
    public static javax.swing.JComboBox<String> cbGrupo;
    public static javax.swing.JComboBox<String> cbIcms;
    public static javax.swing.JComboBox<String> cbLocal;
    public static javax.swing.JComboBox<String> cbMarca;
    public static javax.swing.JComboBox<String> cbMedida;
    public static javax.swing.JComboBox<String> cbModelo;
    public static javax.swing.JComboBox<String> cbPrateleira;
    public static javax.swing.JComboBox<String> cbRefClass;
    public static javax.swing.JComboBox<String> cbRefGeral;
    public static javax.swing.JComboBox<String> cbStatus;
    public static javax.swing.JComboBox<String> cbSubgrupo;
    public static javax.swing.JTextField codAnuncioEco;
    public static javax.swing.JTextField codAnuncioEcoInterno;
    public static javax.swing.JTextField codAnuncioFace;
    public static javax.swing.JTextField codAnuncioFaceInterno;
    public static javax.swing.JTextField codAnuncioML;
    public static javax.swing.JTextField codAnuncioMLInterno;
    public static javax.swing.JTextField codAnuncioOlx;
    public static javax.swing.JTextField codAnuncioOlxInterno;
    public static javax.swing.JLabel descri1;
    public static javax.swing.JLabel descri2;
    public static javax.swing.JLabel descricaoncm;
    public static javax.swing.JButton gerarCodEco;
    public static javax.swing.JButton gerarCodFace;
    public static javax.swing.JButton gerarCodML;
    public static javax.swing.JButton gerarCodOLX;
    public static javax.swing.JCheckBox habilitar1PP;
    public static javax.swing.JCheckBox habilitar2PP;
    public static javax.swing.JCheckBox habilitar3PP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToggleButton openFiscais;
    public static javax.swing.JComboBox<String> origem;
    public static javax.swing.JTextField partnumber;
    public static javax.swing.JFormattedTextField precoPromo;
    public static javax.swing.JRadioButton radioPromo;
    public static javax.swing.JTextField skuForne;
    public static javax.swing.JSpinner spQTDE;
    public static javax.swing.JSpinner spQtEmb;
    public static javax.swing.JFormattedTextField txCofis;
    public static javax.swing.JFormattedTextField txIcms;
    public static javax.swing.JFormattedTextField txIpi;
    public static javax.swing.JComboBox<String> txMotivoD;
    public static javax.swing.JTextField txReducao;
    public static javax.swing.JTextField txSubst;
    private javax.swing.JLabel txcusto;
    public static javax.swing.JFormattedTextField txtCodeBar;
    public static javax.swing.JTextField txtDataC;
    public static javax.swing.JTextField txtDescri;
    public static javax.swing.JTextField txtID;
    public static javax.swing.JComboBox<String> txtNcm;
    public static javax.swing.JTextArea txtObs;
    public static javax.swing.JFormattedTextField txtPrecoCusto;
    public static javax.swing.JFormattedTextField txtPrecoMin;
    public static javax.swing.JFormattedTextField txtPrecoVenda;
    public static javax.swing.JTextField txtSerial;
    public static javax.swing.JTextField txtrefUnica;
    public static javax.swing.JButton uploadimg1;
    public static javax.swing.JLabel viewImage1PP;
    public static javax.swing.JLabel viewImage2;
    public static javax.swing.JLabel viewImage2PP;
    public static javax.swing.JLabel viewImage3;
    public static javax.swing.JLabel viewImage3PP;
    public static javax.swing.JLabel viewImage4;
    // End of variables declaration//GEN-END:variables
}
