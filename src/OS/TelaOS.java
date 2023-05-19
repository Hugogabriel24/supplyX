/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package OS;

import Jm.JMascara;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import br.com.tanamao.dal.moduloOS;
import br.com.tanamao.dal.produtoOS;
import br.com.tanamao.dal.servicoOS;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import notification.Notification;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import static pages.TelaPrincipal.empresa;

/**
 *
 * @author hugogabriel
 */
public final class TelaOS extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String AGetFone, AGetNome;
    String Name;
    double preco, subtotal, totalServico, precoServico, subtotalServico, alltotal, totalProduto;
    int qtd;
    private File imagemA;
    private File imagemB;
    private File imagemC;

    DefaultTableModel carService;
    DefaultTableModel carProd;

    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
        setOSnumebr();
        setIcon();
        setDates();
        setColorFromEmpresa();
        SetarCliente();
        SetarServicos();
        SetarProdutos();
        SetarVendedor();
        SetarEntregador();
        setValidade();
        getServ();
        uper();
        selectProdutos();
        endEntrega.setEnabled(false);
        cbentregador.setEnabled(false);
        valorFrete.setEnabled(false);
        inputData.setEnabled(false);
        cbPagador.setEnabled(false);
        cbAddFrete.setEnabled(false);
        this.scrollImage.getVerticalScrollBar().setUnitIncrement(20);
        this.scrollEquipamentos.getVerticalScrollBar().setUnitIncrement(20);
        SpQTDE.setValue(1);
        SpQTDEp.setValue(1);
        totalServico = 0;
        alltotal = 0;
    }

    public void SetarEntregador() {
        String sql = "select * from entregadores order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(3);
                cbentregador.addItem(nome);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void setValidade() {
        Calendar c = validadeOS.getCalendar();
        c.add(Calendar.DAY_OF_WEEK, 10);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String next = df.format(c.getTime());
        try {
            Date dataform = df.parse(next);
            validadeOS.setDate(dataform);

        } catch (ParseException ex) {

        }
    }

    public void setDates() {
        Date now = new Date();
        inputData.setDate(now);
        entrada.setDate(now);
        validadeOS.setDate(now);
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

    public void SetarServicos() {
        String sql = "select * from servicos order by servico";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String Service = rs.getString(2);
                cbServicos.addItem(Service);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void SetarProdutos() {
        String sql = "select * from partepecas order by descricao";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String Prod = rs.getString(3);
                cbProdutos.addItem(Prod);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void SetarCliente() {
        String sql = "select * from clientes order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                Name = rs.getString(3);
                clienteOS.addItem(Name);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void colocarDadosForCPF() {
        String sql = "select idcliente,nome,cpf,tel from clientes where cpf like ?";
        String getCpf = cpfOS.getText();
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, getCpf);
            rs = pst.executeQuery();

            while (rs.next()) {
                rs.getString(1);
                AGetNome = rs.getString(2);
                rs.getString(3);
                AGetFone = rs.getString(4);

                clienteOS.setSelectedItem(AGetNome);
                telefoneOS.setText(AGetFone);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public File selecionarImagemA() {
        JFileChooser filec = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens em JPEG e PNG", "jpg", "png", "jpeg");
        filec.addChoosableFileFilter(filtro);
        filec.setAcceptAllFileFilterUsed(false);
        filec.setDialogType(JFileChooser.OPEN_DIALOG);
        filec.setCurrentDirectory(new File("/"));
        filec.showOpenDialog(this);

        return filec.getSelectedFile();
    }

    private void abrirImagemA(Object source) {
        if (source instanceof File) {
            ImageIcon icon = new ImageIcon(imagemA.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel1ImageOS.getWidth(), Painel1ImageOS.getHeight(), 100));
            viewImage1OS.setIcon(icon);
        } else if (source instanceof byte[]) {
            ImageIcon icon = new ImageIcon(imagemA.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel1ImageOS.getWidth(), Painel1ImageOS.getHeight(), 100));
            viewImage1OS.setIcon(icon);
        }

    }

    public File selecionarImagemB() {
        JFileChooser filec = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens em JPEG e PNG", "jpg", "png", "jpeg");
        filec.addChoosableFileFilter(filtro);
        filec.setAcceptAllFileFilterUsed(false);
        filec.setDialogType(JFileChooser.OPEN_DIALOG);
        filec.setCurrentDirectory(new File("/"));
        filec.showOpenDialog(this);

        return filec.getSelectedFile();
    }

    private void abrirImagemB(Object source) {
        if (source instanceof File) {
            ImageIcon icon = new ImageIcon(imagemB.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel2ImageOS.getWidth(), Painel2ImageOS.getHeight(), 100));
            viewImage2OS.setIcon(icon);
        } else if (source instanceof byte[]) {
            ImageIcon icon = new ImageIcon(imagemB.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel2ImageOS.getWidth(), Painel2ImageOS.getHeight(), 100));
            viewImage2OS.setIcon(icon);
        }

    }

    public File selecionarImagemC() {
        JFileChooser filec = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imagens em JPEG e PNG", "jpg", "png", "jpeg", "Heic");
        filec.addChoosableFileFilter(filtro);
        filec.setAcceptAllFileFilterUsed(false);
        filec.setDialogType(JFileChooser.OPEN_DIALOG);
        filec.setCurrentDirectory(new File("/"));
        filec.showOpenDialog(this);

        return filec.getSelectedFile();
    }

    private void abrirImagemC(Object source) {
        if (source instanceof File) {
            ImageIcon icon = new ImageIcon(imagemC.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel3ImageOS.getWidth(), Painel3ImageOS.getHeight(), 100));
            viewImage3OS.setIcon(icon);
        } else if (source instanceof byte[]) {
            ImageIcon icon = new ImageIcon(imagemC.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel3ImageOS.getWidth(), Painel3ImageOS.getHeight(), 100));
            viewImage3OS.setIcon(icon);
        }

    }

    public static Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    @SuppressWarnings("rawtypes")
    public static String analisaXml(Document document) {
        List list = document
                .selectNodes("//DirectionsResponse/route/leg/distance/text");

        Element element = (Element) list.get(list.size() - 1);

        return element.getText();
    }

    public static String calcular(String destino) {
        URL url;
        try {
            url = new URL(
                    "https://maps.googleapis.com/maps/api/directions/xml?origin=Rua Francisco Berenguer, 737, Recife-PE&destination=" + destino + "&key=AIzaSyAOME7FCQDBd-yzqsZWAhWxh6-95llIJTA");

            Document document = getDocumento(url);

            return analisaXml(document);
        } catch (MalformedURLException | DocumentException e) {
        }
        return "";
    }

    public void calcularKm() {
        if (endEntrega.getText().equals("")) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Campo do endereço vazio!!");
            noti.showNotification();
        } else {
            String rua2 = endEntrega.getText();
            String calc = calcular(rua2);
            String kmConv = calc.replaceAll("[\\D]", "");
            int kmFrete = Integer.parseInt(kmConv);
            Double precox, totalx;
            precox = 0.13;
            totalx = kmFrete * precox + 5;
            String valorF = totalx.toString();
            valorFrete.setText(valorF);

        }
    }

    public void SetarVendedor() {
        String sql = "select * from funcionarios order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(2);
                vendedorOS.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void setOSnumebr() {
        String mat = null;
        String sql = "select max(idos)idos from os";
        conexao = ModuloConexao.conector();
        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String idUV = rs.getString(1);
                int primarykey = Integer.parseInt(idUV);
                int pos = primarykey + 1;
                Date data = new Date();
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
                String date = formatador.format(data);
                mat = "#OS00" + pos + "-" + date.replaceAll("/", "") + "";
                nos.setText(mat);
            }

        } catch (NumberFormatException | SQLException e) {

        }
    }

    public void uper() {
        endEntrega.setDocument(new UpperCase());
        obsOS.setDocument(new UpperCase());
        ObsFrete.setDocument(new UpperCase());
        marca.setDocument(new UpperCase());
        modelo.setDocument(new UpperCase());
        serial.setDocument(new UpperCase());
        config.setDocument(new UpperCase());
        itens.setDocument(new UpperCase());
        RelatoOS.setDocument(new UpperCase());
        LaudoOS.setDocument(new UpperCase());
        StatusEquipamentoOS.setDocument(new UpperCase());
        CausasOS.setDocument(new UpperCase());
        MelhoriasOS.setDocument(new UpperCase());

    }

    public void selectProdutos() {
        String sql = "select * from partepecas where descricao = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbProdutos.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                int qtde = rs.getInt(15);
                if (qtde <= 0) {
                    String Qtde = Integer.toString(qtde);
                    QtdeProdutosSelected.setText(Qtde);
                    PrecoProdutosSelected.setText(rs.getString(13));
                    indicarEstoque.setText("Produto sem estoque disponivel :/");
                    indicarEstoque.setForeground(Color.red);

                } else {
                    String Qtde = Integer.toString(qtde);
                    QtdeProdutosSelected.setText(Qtde);
                    PrecoProdutosSelected.setText(rs.getString(13));
                    SpQTDEp.setModel(new SpinnerNumberModel(1, 1, qtde, 1));
                    indicarEstoque.setText("");

                }
                if (qtde == 0) {
                    SpQTDEp.setModel(new SpinnerNumberModel(0, 0, 0, 0));
                } else {
                    SpQTDEp.setModel(new SpinnerNumberModel(1, 1, qtde, 1));
                }

            }
        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao exibir os produtos.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    public void addprod() {
        String sql = "select * from partepecas where descricao  = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbProdutos.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                String id = rs.getString(1);
                String descri = rs.getString(3);
                String grup = rs.getString("refunica");
                String serials = rs.getString(9);
                String marc = rs.getString(7);
                String prec = rs.getString(13);
                int qtde = rs.getInt(15);
                txIdProdutoOS.setText(id);
                txDescri.setText(descri);
                txCodigob.setText(grup);
                txserialOS.setText(serials);
                txMarca.setText(marc);
                txPreco.setText(prec);

                if ((cbProdutos.getSelectedItem().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    if (qtde <= 0) {
                        Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Produto sem estoque disponivel!!");
                        noti.showNotification();
                    } else {
                        qtd = Integer.parseInt(SpQTDEp.getValue().toString());
                        preco = Double.parseDouble(txPreco.getText().replace(",", "."));

                        subtotal = qtd * preco;
                        totalProduto += subtotal;

                        alltotal = totalProduto + totalServico;

                        lbTotalProd.setText("R$ " + totalProduto + "");
                        TotalOS.setText("" + alltotal);

                        carProd = (DefaultTableModel) TableProducts.getModel();

                        carProd.addRow(new Object[]{
                            txIdProdutoOS.getText(),
                            "Outros",
                            txDescri.getText(),
                            txCodigob.getText(),
                            txserialOS.getText(),
                            txMarca.getText(),
                            txPreco.getText().replaceAll(",", "."),
                            SpQTDEp.getValue().toString(),
                            subtotal

                        });
                        selectProdutos();
                    }

                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar produto.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    public void getServ() {
        String sql = "select * from servicos where servico = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbServicos.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                PrecoServicoSelected.setText(rs.getString(4));

            }
        } catch (SQLException e) {

        }
    }

    private void SaveOS() {

        String sql = "insert into os(idcliente,nome,cpf,tel,entrada,vendedor,chegada,obs,validade,status,recebimento,obsfrete,endereco,entregador,precofrete,dataentrega,pagador,formapg,parcelas,desconto,valordesconto,equipamento,marca,modelo,serial,config,itens,relato,laudo,aparencia,causas,sugestao,total,img1,img2,img3,empresa,numeroos) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        if (entrada.getDate() == null || validadeOS.getDate() == null || inputData.getDate() == null) {
            Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
            noti.showNotification();
        } else {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            String entrace = formato.format(entrada.getDate());
            String validate = formato.format(validadeOS.getDate());
            String dateEntrega = formato.format(inputData.getDate());
            try {

                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtid.getText());
                pst.setString(2, clienteOS.getSelectedItem().toString());
                pst.setString(3, cpfOS.getText());
                pst.setString(4, telefoneOS.getText());
                pst.setString(5, entrace);
                pst.setString(6, vendedorOS.getSelectedItem().toString());
                pst.setString(7, canalOS.getSelectedItem().toString());
                pst.setString(8, obsOS.getText());
                pst.setString(9, validate);
                pst.setString(10, status.getSelectedItem().toString());
                pst.setString(11, cbFormaRecebi.getSelectedItem().toString());
                pst.setString(12, ObsFrete.getText());
                pst.setString(13, endEntrega.getText());
                pst.setString(14, cbentregador.getSelectedItem().toString());
                pst.setString(15, valorFrete.getText());
                pst.setString(16, dateEntrega);
                pst.setString(17, cbPagador.getSelectedItem().toString());
                pst.setString(18, CBOFormapgm.getSelectedItem().toString());
                pst.setString(19, CBOParcelas.getSelectedItem().toString());
                pst.setString(20, txdesconto.getText());
                pst.setString(21, valorcdescontoTotal.getText());
                pst.setString(22, equipamento.getSelectedItem().toString());
                pst.setString(23, marca.getText());
                pst.setString(24, modelo.getText());
                pst.setString(25, serial.getText());
                pst.setString(26, config.getText());
                pst.setString(27, itens.getText());
                pst.setString(28, RelatoOS.getText());
                pst.setString(29, LaudoOS.getText());
                pst.setString(30, StatusEquipamentoOS.getText());
                pst.setString(31, CausasOS.getText());
                pst.setString(32, MelhoriasOS.getText());
                pst.setString(33, TotalOS.getText());
                pst.setBytes(34, getImagem1());
                pst.setBytes(35, getImagem2());
                pst.setBytes(36, getImagem3());
                pst.setString(37, empresa.getText());
                pst.setString(38, nos.getText());

                if ((TotalOS.getText().isEmpty()) || cpfOS.getText().isEmpty() || validadeOS.getDate() == null) {
                    Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                    noti.showNotification();

                } else {
                    int adicionado = pst.executeUpdate();

                    if (adicionado > 0) {
                        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "OS salva com sucesso!!");
                        noti.showNotification();
                        setOSnumebr();
                        for (int nt = 0; nt < carProd.getRowCount(); nt++) {

                            int qtdAtual, qtdComprada, qtdAtualizada;
                            moduloOS osmodel = new moduloOS();

                            produtoOS item = new produtoOS();

                            item.setIdproduto(carProd.getValueAt(nt, 0).toString());
                            item.setProduto(carProd.getValueAt(nt, 1).toString());
                            item.setDescricao(carProd.getValueAt(nt, 2).toString());
                            item.setCodigob(carProd.getValueAt(nt, 3).toString());
                            item.setSerial(carProd.getValueAt(nt, 4).toString());
                            item.setMarca(carProd.getValueAt(nt, 5).toString());
                            item.setPreco(carProd.getValueAt(nt, 6).toString());
                            item.setQtd(carProd.getValueAt(nt, 7).toString());
                            item.setSubtotal(carProd.getValueAt(nt, 8).toString());
                            osmodel.CadastrarProdutoOS(item);
                            //Baixa de estoque OUTROS
                            qtdAtual = osmodel.retornaEstoqueAtualOutrosOS(item.getIdproduto());
                            qtdComprada = Integer.parseInt(carProd.getValueAt(nt, 7).toString());
                            qtdAtualizada = qtdAtual - qtdComprada;
                            String DescricaoOutros = (carProd.getValueAt(nt, 2).toString());
                            osmodel.mudarEstoqueOutrosOS(item.getIdproduto(), qtdAtualizada, DescricaoOutros);

                        }

                        for (int nt = 0; nt < carService.getRowCount(); nt++) {

                            moduloOS osmodel = new moduloOS();

                            servicoOS serv = new servicoOS();

                            serv.setIdservico(carService.getValueAt(nt, 0).toString());
                            serv.setServico(carService.getValueAt(nt, 1).toString());
                            serv.setDetalhes(carService.getValueAt(nt, 2).toString());
                            serv.setQtde(carService.getValueAt(nt, 3).toString());
                            serv.setPreco(carService.getValueAt(nt, 4).toString());
                            serv.setSubtotal(carService.getValueAt(nt, 5).toString());
                            osmodel.CadastrarServicoOS(serv);
                        }

                    }
                }

            } catch (NumberFormatException | SQLException e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao salvar a OS</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
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

                BufferedImage novaImagem = new BufferedImage(Painel1ImageOS.getWidth() + 200, Painel1ImageOS.getHeight() + 200, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel1ImageOS.getWidth() + 200, Painel1ImageOS.getHeight() + 200, null);

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

                BufferedImage novaImagem = new BufferedImage(Painel2ImageOS.getWidth() + 200, Painel2ImageOS.getHeight() + 200, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel2ImageOS.getWidth() + 200, Painel2ImageOS.getHeight() + 200, null);

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

                BufferedImage novaImagem = new BufferedImage(Painel3ImageOS.getWidth() + 200, Painel3ImageOS.getHeight() + 200, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel3ImageOS.getWidth() + 200, Painel3ImageOS.getHeight() + 200, null);

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txServico = new javax.swing.JTextField();
        txDetail = new javax.swing.JTextField();
        txValor = new javax.swing.JTextField();
        txDescri = new javax.swing.JTextField();
        txCodigob = new javax.swing.JTextField();
        txMarca = new javax.swing.JTextField();
        txPreco = new javax.swing.JTextField();
        txtid = new javax.swing.JTextField();
        txEmail = new javax.swing.JTextField();
        txIdProdutoOS = new javax.swing.JTextField();
        txserialOS = new javax.swing.JTextField();
        datacadastroOS = new javax.swing.JTextField();
        idservice = new javax.swing.JTextField();
        TabPanel = new javax.swing.JTabbedPane();
        PanelEquipamento = new javax.swing.JPanel();
        scrollEquipamentos = new javax.swing.JScrollPane();
        painelEquipamentos = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        marca = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        modelo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        serial = new javax.swing.JTextField();
        config = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        itens = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        RelatoOS = new javax.swing.JTextArea();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LaudoOS = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        StatusEquipamentoOS = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        CausasOS = new javax.swing.JTextArea();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        MelhoriasOS = new javax.swing.JTextArea();
        equipamento = new javax.swing.JComboBox<>();
        PanelServico = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbServicos = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableServices = new javax.swing.JTable();
        btAddService = new javax.swing.JButton();
        SpQTDE = new javax.swing.JSpinner();
        jLabel25 = new javax.swing.JLabel();
        clearServicos = new javax.swing.JButton();
        deleteServicos = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lbTotalService = new javax.swing.JLabel();
        PrecoServicoSelected = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        PanelPecas = new javax.swing.JPanel();
        cbProdutos = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btAddProducts = new javax.swing.JButton();
        SpQTDEp = new javax.swing.JSpinner();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProducts = new javax.swing.JTable();
        lbTotalProd = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        clearProdutos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        QtdeProdutosSelected = new javax.swing.JTextField();
        PrecoProdutosSelected = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        indicarEstoque = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        scrollImage = new javax.swing.JScrollPane();
        PanelImagens = new javax.swing.JPanel();
        CancelimgCliente1 = new javax.swing.JButton();
        uploadimg1OS = new javax.swing.JButton();
        Painel1ImageOS = new javax.swing.JPanel();
        viewImage1OS = new javax.swing.JLabel();
        CancelimgCliente2 = new javax.swing.JButton();
        uploadimg2OS = new javax.swing.JButton();
        Painel2ImageOS = new javax.swing.JPanel();
        viewImage2OS = new javax.swing.JLabel();
        uploadimg3OS = new javax.swing.JButton();
        Painel3ImageOS = new javax.swing.JPanel();
        viewImage3OS = new javax.swing.JLabel();
        CancelimgCliente3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        clienteOS = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cpfOS = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        telefoneOS = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        entrada = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        vendedorOS = new javax.swing.JComboBox<>();
        canalOS = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        status = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        cbFormaRecebi = new javax.swing.JComboBox<>();
        pEntrega = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        endEntrega = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cbPagador = new javax.swing.JComboBox<>();
        cbAddFrete = new javax.swing.JButton();
        valorFrete = new javax.swing.JFormattedTextField();
        cbentregador = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        inputData = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        ObsFrete = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        obsOS = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        validadeOS = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        CBOParcelas = new javax.swing.JComboBox<>();
        CBOFormapgm = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txdesconto = new javax.swing.JFormattedTextField();
        valorcdescontoTotal = new javax.swing.JFormattedTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        TotalOS = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        nos = new javax.swing.JLabel();
        btCad = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        btSearch1 = new javax.swing.JButton();

        txServico.setText("jTextField12");

        txDetail.setText("jTextField13");

        txValor.setText("jTextField14");

        txDescri.setText("jTextField12");

        txCodigob.setText("jTextField12");

        txMarca.setText("jTextField12");

        txPreco.setText("jTextField12");

        txtid.setText("jTextField1");

        txIdProdutoOS.setText("jTextField1");

        txserialOS.setText("jTextField1");

        datacadastroOS.setText("jTextField1");

        idservice.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        TabPanel.setBackground(new java.awt.Color(255, 255, 255));
        TabPanel.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        TabPanel.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        scrollEquipamentos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel15.setText("EQUIPAMENTO");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("MARCA");

        marca.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("MODELO");

        modelo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("RELATO DO CLIENTE :");

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel35.setText("SERIAL DO PRODUTO");

        serial.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        config.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel36.setText("CONFIGURAÇÕES");

        itens.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel37.setText("ITENS ACOMPANHADOS");

        RelatoOS.setColumns(20);
        RelatoOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        RelatoOS.setRows(5);
        jScrollPane2.setViewportView(RelatoOS);

        jLabel39.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel39.setText("LAUDO TECNICO :");

        LaudoOS.setColumns(20);
        LaudoOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        LaudoOS.setRows(5);
        jScrollPane4.setViewportView(LaudoOS);

        jLabel40.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel40.setText("STATUS APARENTE DO EQUIPAMENTO :");

        StatusEquipamentoOS.setColumns(20);
        StatusEquipamentoOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        StatusEquipamentoOS.setRows(5);
        jScrollPane5.setViewportView(StatusEquipamentoOS);

        jLabel41.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel41.setText("POSSIVEIS CAUSAS DO PROBLEMA :");

        CausasOS.setColumns(20);
        CausasOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        CausasOS.setRows(5);
        jScrollPane6.setViewportView(CausasOS);

        jLabel42.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel42.setText("SUGESTÃO DE MELHORIA PARA O EQUIPAMENTO :");

        MelhoriasOS.setColumns(20);
        MelhoriasOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        MelhoriasOS.setRows(5);
        jScrollPane9.setViewportView(MelhoriasOS);

        equipamento.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        equipamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Notebook", "Desktop", "Monitor", "Servidor", "Celular", "Outros" }));

        javax.swing.GroupLayout painelEquipamentosLayout = new javax.swing.GroupLayout(painelEquipamentos);
        painelEquipamentos.setLayout(painelEquipamentosLayout);
        painelEquipamentosLayout.setHorizontalGroup(
            painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEquipamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelEquipamentosLayout.createSequentialGroup()
                                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel35)
                                            .addComponent(serial, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(painelEquipamentosLayout.createSequentialGroup()
                                                .addComponent(jLabel36)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(config)))
                                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel15)
                                            .addComponent(equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel16)
                                            .addComponent(marca, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel17)
                                    .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(itens, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                        .addComponent(modelo, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(painelEquipamentosLayout.createSequentialGroup()
                                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel41)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(67, Short.MAX_VALUE))))
            .addGroup(painelEquipamentosLayout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addGap(69, 69, 69))
        );
        painelEquipamentosLayout.setVerticalGroup(
            painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEquipamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(equipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(serial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(itens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(config, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(painelEquipamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelEquipamentosLayout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scrollEquipamentos.setViewportView(painelEquipamentos);

        javax.swing.GroupLayout PanelEquipamentoLayout = new javax.swing.GroupLayout(PanelEquipamento);
        PanelEquipamento.setLayout(PanelEquipamentoLayout);
        PanelEquipamentoLayout.setHorizontalGroup(
            PanelEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEquipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollEquipamentos, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanelEquipamentoLayout.setVerticalGroup(
            PanelEquipamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEquipamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollEquipamentos, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
        );

        TabPanel.addTab("Equipamento", PanelEquipamento);

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setText("SERVICOS / MÃO DE OBRA");

        AutoCompleteDecorator.decorate(cbServicos);
        cbServicos.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbServicosPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        TableServices.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TableServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Serviço", "Detalhes", "Quantidade", "Valor", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableServices);
        if (TableServices.getColumnModel().getColumnCount() > 0) {
            TableServices.getColumnModel().getColumn(0).setResizable(false);
            TableServices.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableServices.getColumnModel().getColumn(1).setResizable(false);
            TableServices.getColumnModel().getColumn(2).setResizable(false);
            TableServices.getColumnModel().getColumn(3).setResizable(false);
            TableServices.getColumnModel().getColumn(4).setResizable(false);
            TableServices.getColumnModel().getColumn(5).setResizable(false);
        }

        btAddService.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        btAddService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/servico.png"))); // NOI18N
        btAddService.setText("  Adicionar serviço");
        btAddService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddServiceActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel25.setText("QUANTIDADE");

        clearServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8-esvaziar-o-carrinho-de-compras-22.png"))); // NOI18N
        clearServicos.setToolTipText("Esvaziar carrinho");
        clearServicos.setBorderPainted(false);
        clearServicos.setContentAreaFilled(false);
        clearServicos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearServicos.setFocusPainted(false);
        clearServicos.setRequestFocusEnabled(false);
        clearServicos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/src/CARRINHO_OFF.png"))); // NOI18N
        clearServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearServicosActionPerformed(evt);
            }
        });

        deleteServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        deleteServicos.setContentAreaFilled(false);
        deleteServicos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        deleteServicos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA_CLICK.png"))); // NOI18N
        deleteServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteServicosActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setText("TOTAL:");

        lbTotalService.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lbTotalService.setText("R$ 0,0");

        PrecoServicoSelected.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        PrecoServicoSelected.setEnabled(false);

        jLabel46.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel46.setText("PREÇO");

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        javax.swing.GroupLayout PanelServicoLayout = new javax.swing.GroupLayout(PanelServico);
        PanelServico.setLayout(PanelServicoLayout);
        PanelServicoLayout.setHorizontalGroup(
            PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelServicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
                    .addGroup(PanelServicoLayout.createSequentialGroup()
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(cbServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PrecoServicoSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SpQTDE, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelServicoLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(btAddService)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelServicoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelServicoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTotalService)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelServicoLayout.setVerticalGroup(
            PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelServicoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelServicoLayout.createSequentialGroup()
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel25))
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btAddService, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(SpQTDE)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelServicoLayout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PrecoServicoSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbServicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelServicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lbTotalService)))
                .addContainerGap())
        );

        TabPanel.addTab("Serviços prestados", PanelServico);

        cbProdutos.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        AutoCompleteDecorator.decorate(cbProdutos);
        cbProdutos.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbProdutosPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbProdutos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbProdutosKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel12.setText("PRODUTOS");

        btAddProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/descricao-do-produto.png"))); // NOI18N
        btAddProducts.setText("  Adicionar produto");
        btAddProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProductsActionPerformed(evt);
            }
        });

        TableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Produto", "Descricao", "REF", "Serial", "Marca", "Preco", "Quantidade", "Subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(TableProducts);
        if (TableProducts.getColumnModel().getColumnCount() > 0) {
            TableProducts.getColumnModel().getColumn(0).setResizable(false);
            TableProducts.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableProducts.getColumnModel().getColumn(1).setResizable(false);
            TableProducts.getColumnModel().getColumn(2).setResizable(false);
            TableProducts.getColumnModel().getColumn(3).setResizable(false);
            TableProducts.getColumnModel().getColumn(4).setResizable(false);
            TableProducts.getColumnModel().getColumn(5).setResizable(false);
            TableProducts.getColumnModel().getColumn(6).setResizable(false);
            TableProducts.getColumnModel().getColumn(7).setResizable(false);
            TableProducts.getColumnModel().getColumn(8).setResizable(false);
        }

        lbTotalProd.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lbTotalProd.setText("R$ 0,0");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA_CLICK.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        clearProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/icons8-esvaziar-o-carrinho-de-compras-22.png"))); // NOI18N
        clearProdutos.setToolTipText("Esvaziar carrinho");
        clearProdutos.setBorderPainted(false);
        clearProdutos.setContentAreaFilled(false);
        clearProdutos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearProdutos.setFocusPainted(false);
        clearProdutos.setRequestFocusEnabled(false);
        clearProdutos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/src/CARRINHO_OFF.png"))); // NOI18N
        clearProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearProdutosActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel2.setText("TOTAL:");

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel26.setText("QUANTIDADE");

        jLabel44.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel44.setText("QTD. ESTOQUE");

        jLabel45.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel45.setText("PREÇO");

        QtdeProdutosSelected.setEnabled(false);

        PrecoProdutosSelected.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        PrecoProdutosSelected.setEnabled(false);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        indicarEstoque.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        javax.swing.GroupLayout PanelPecasLayout = new javax.swing.GroupLayout(PanelPecas);
        PanelPecas.setLayout(PanelPecasLayout);
        PanelPecasLayout.setHorizontalGroup(
            PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPecasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPecasLayout.createSequentialGroup()
                        .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPecasLayout.createSequentialGroup()
                                .addComponent(cbProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(QtdeProdutosSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelPecasLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(217, 217, 217)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(indicarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PrecoProdutosSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(SpQTDEp, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPecasLayout.createSequentialGroup()
                                .addComponent(btAddProducts)
                                .addGap(0, 254, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPecasLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPecasLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTotalProd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelPecasLayout.setVerticalGroup(
            PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPecasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel26)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelPecasLayout.createSequentialGroup()
                        .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45))
                        .addGap(19, 19, 19)
                        .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(QtdeProdutosSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PrecoProdutosSelected, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SpQTDEp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAddProducts, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(indicarEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelPecasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(lbTotalProd)))
                .addContainerGap())
        );

        TabPanel.addTab("Peças ultilizadas", PanelPecas);

        scrollImage.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        CancelimgCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        CancelimgCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelimgCliente1ActionPerformed(evt);
            }
        });

        uploadimg1OS.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        uploadimg1OS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/upload-de-arquivo.png"))); // NOI18N
        uploadimg1OS.setText("Upload");
        uploadimg1OS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadimg1OSActionPerformed(evt);
            }
        });

        Painel1ImageOS.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel1ImageOSLayout = new javax.swing.GroupLayout(Painel1ImageOS);
        Painel1ImageOS.setLayout(Painel1ImageOSLayout);
        Painel1ImageOSLayout.setHorizontalGroup(
            Painel1ImageOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1OS, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );
        Painel1ImageOSLayout.setVerticalGroup(
            Painel1ImageOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1OS, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        CancelimgCliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        CancelimgCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelimgCliente2ActionPerformed(evt);
            }
        });

        uploadimg2OS.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        uploadimg2OS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/upload-de-arquivo.png"))); // NOI18N
        uploadimg2OS.setText("Upload");
        uploadimg2OS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadimg2OSActionPerformed(evt);
            }
        });

        Painel2ImageOS.setBackground(new java.awt.Color(255, 255, 255));

        viewImage2OS.setPreferredSize(new java.awt.Dimension(730, 530));

        javax.swing.GroupLayout Painel2ImageOSLayout = new javax.swing.GroupLayout(Painel2ImageOS);
        Painel2ImageOS.setLayout(Painel2ImageOSLayout);
        Painel2ImageOSLayout.setHorizontalGroup(
            Painel2ImageOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2OS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Painel2ImageOSLayout.setVerticalGroup(
            Painel2ImageOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2OS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        uploadimg3OS.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        uploadimg3OS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/upload-de-arquivo.png"))); // NOI18N
        uploadimg3OS.setText("Upload");
        uploadimg3OS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadimg3OSActionPerformed(evt);
            }
        });

        Painel3ImageOS.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel3ImageOSLayout = new javax.swing.GroupLayout(Painel3ImageOS);
        Painel3ImageOS.setLayout(Painel3ImageOSLayout);
        Painel3ImageOSLayout.setHorizontalGroup(
            Painel3ImageOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3OS, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
        );
        Painel3ImageOSLayout.setVerticalGroup(
            Painel3ImageOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3OS, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        CancelimgCliente3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        CancelimgCliente3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelimgCliente3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelImagensLayout = new javax.swing.GroupLayout(PanelImagens);
        PanelImagens.setLayout(PanelImagensLayout);
        PanelImagensLayout.setHorizontalGroup(
            PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelImagensLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelImagensLayout.createSequentialGroup()
                        .addGap(549, 549, 549)
                        .addGroup(PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelImagensLayout.createSequentialGroup()
                                .addComponent(CancelimgCliente1)
                                .addGap(18, 18, 18)
                                .addComponent(uploadimg1OS))
                            .addGroup(PanelImagensLayout.createSequentialGroup()
                                .addComponent(CancelimgCliente2)
                                .addGap(18, 18, 18)
                                .addComponent(uploadimg2OS))))
                    .addComponent(Painel1ImageOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Painel2ImageOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(PanelImagensLayout.createSequentialGroup()
                            .addComponent(CancelimgCliente3)
                            .addGap(18, 18, 18)
                            .addComponent(uploadimg3OS))
                        .addComponent(Painel3ImageOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(340, Short.MAX_VALUE))
        );
        PanelImagensLayout.setVerticalGroup(
            PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelImagensLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Painel1ImageOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CancelimgCliente1)
                    .addComponent(uploadimg1OS))
                .addGap(18, 18, 18)
                .addComponent(Painel2ImageOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uploadimg2OS)
                    .addComponent(CancelimgCliente2))
                .addGap(18, 18, 18)
                .addComponent(Painel3ImageOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelImagensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uploadimg3OS)
                    .addComponent(CancelimgCliente3))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        scrollImage.setViewportView(PanelImagens);

        TabPanel.addTab("Carregar imagens", scrollImage);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        clienteOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        clienteOS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                clienteOSItemStateChanged(evt);
            }
        });
        clienteOS.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                clienteOSPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        clienteOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clienteOSMouseClicked(evt);
            }
        });
        clienteOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteOSActionPerformed(evt);
            }
        });
        AutoCompleteDecorator.decorate(clienteOS);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("NOME :");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("CPF / CNPJ :");

        cpfOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cpfOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cpfOSKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cpfOSKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("TELEFONE :");

        telefoneOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        telefoneOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                telefoneOSKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("DATA DE ENTRADA :");

        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel27.setText("VENDEDOR/ TECNICO");

        vendedorOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        canalOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        canalOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OLX", "Facebook", "Instagram", "WhatsApp", "Outros meios virtuais", "Outros meios pessoais" }));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("CANAL DE CHEGADA");

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel33.setText("STATUS DA OS");

        status.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EM ABERTA", "EM ATENDIMENTO", "CONCLUIDA", "AGUARDANDO LIBERAÇÃO", "CANCELADA", "AGUARDANDO RETIRADA", "PAGAMENTO PENDENTE", "PRONTA /  ENTREGA A DOMICILIO", "PRONTA / ESPERANDO RETIRADA", "REVISÃO", "EM FATURAMENTO", "FATURADA", "ENVIADA", "ENTREGUE" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cpfOS, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(telefoneOS, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(status, 0, 1, Short.MAX_VALUE)))
                    .addComponent(clienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(vendedorOS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(canalOS, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clienteOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(vendedorOS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cpfOS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(telefoneOS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel14)
                        .addComponent(canalOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(entrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados Gerais", jPanel4);

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel21.setText("FORMA DE RECEBIMENTO :");

        cbFormaRecebi.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbFormaRecebi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RETIRADA", "ENTREGA" }));
        cbFormaRecebi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbFormaRecebiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbFormaRecebi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFormaRecebiActionPerformed(evt);
            }
        });

        pEntrega.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da entrega", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("ENDEREÇO DE ENTREGA");

        endEntrega.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        endEntrega.setText("--");
        endEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                endEntregaKeyPressed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel28.setText("VALOR DO FRETE");

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel29.setText("DATA DE ENTREGA");

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel30.setText("PAGADOR DA ENTREGA");

        cbPagador.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbPagador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "CLIENTE", "ESTABELECIMENTO" }));
        cbPagador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPagadorActionPerformed(evt);
            }
        });

        cbAddFrete.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        cbAddFrete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/entrega-rapida-2.png"))); // NOI18N
        cbAddFrete.setText("Adicionar frete no carrinho");
        cbAddFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAddFreteActionPerformed(evt);
            }
        });

        valorFrete.setText("--");
        valorFrete.setToolTipText("");
        valorFrete.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        cbentregador.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbentregador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--" }));
        cbentregador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbentregadorActionPerformed(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel47.setText("ENTREGADOR");

        javax.swing.GroupLayout pEntregaLayout = new javax.swing.GroupLayout(pEntrega);
        pEntrega.setLayout(pEntregaLayout);
        pEntregaLayout.setHorizontalGroup(
            pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEntregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEntregaLayout.createSequentialGroup()
                        .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pEntregaLayout.createSequentialGroup()
                                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pEntregaLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel28))
                                    .addComponent(valorFrete))
                                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pEntregaLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addComponent(jLabel29))
                                    .addGroup(pEntregaLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(inputData, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(27, 27, 27)
                                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbPagador, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(cbAddFrete))
                    .addGroup(pEntregaLayout.createSequentialGroup()
                        .addComponent(endEntrega)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(cbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        pEntregaLayout.setVerticalGroup(
            pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEntregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(endEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pEntregaLayout.createSequentialGroup()
                        .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbPagador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(valorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbAddFrete))
                    .addComponent(inputData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel31.setText("OBSERVAÇÕES DO FRETE / RETIRADA:");

        ObsFrete.setColumns(20);
        ObsFrete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        ObsFrete.setRows(5);
        jScrollPane7.setViewportView(ObsFrete);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbFormaRecebi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel31)
                    .addComponent(jScrollPane7))
                .addGap(23, 23, 23)
                .addComponent(pEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pEntrega, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(cbFormaRecebi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informações de entrega/retirada", jPanel5);

        jLabel32.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel32.setText("OBSERVAÇÕES DA OS :");

        obsOS.setColumns(20);
        obsOS.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        obsOS.setRows(5);
        jScrollPane8.setViewportView(obsOS);

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel34.setText("VALIDADE DA OS :");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel32)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel34))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(validadeOS, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(509, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(validadeOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mais informações", jPanel10);

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("Nº DE PARCELAS");

        CBOParcelas.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CBOParcelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "1x", "2x", "3x", "4x", "5x", "6x", "7x", "8x", "9x", "10x", "11x", "12x", "14x", "16x", "24x" }));
        CBOParcelas.setEnabled(false);

        CBOFormapgm.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        CBOFormapgm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Cartão de crédito", "Cartão de débito", "PIX", "TED", "DOC", "Boleto", "Dinheiro + cartão", "PIX + dinheiro", "PIX + cartão" }));
        CBOFormapgm.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                CBOFormapgmPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        CBOFormapgm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBOFormapgmActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("FORMA DE PAGAMENTO");

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("DESCONTO");

        jLabel3.setFont(new java.awt.Font("Century", 0, 14)); // NOI18N
        jLabel3.setText("%");

        txdesconto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txdesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txdescontoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txdescontoKeyReleased(evt);
            }
        });

        valorcdescontoTotal.setEnabled(false);
        valorcdescontoTotal.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel43.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel43.setText("VALOR TOTAL C/ DESCONTO");

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel20.setText("VALOR TOTAL DA OS :");

        TotalOS.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        TotalOS.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(jLabel43)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txdesconto, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CBOFormapgm, javax.swing.GroupLayout.Alignment.LEADING, 0, 208, Short.MAX_VALUE)
                    .addComponent(CBOParcelas, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(valorcdescontoTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 352, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(TotalOS, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(CBOFormapgm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CBOParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txdesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valorcdescontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TotalOS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dados de pagamento", jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/ordem-de-servico (1).png"))); // NOI18N
        jLabel38.setText("   ORDEM DE SERVIÇO");

        nos.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N
        nos.setForeground(new java.awt.Color(255, 255, 255));
        nos.setText("#0704230001-OSINT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nos)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(nos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btCad.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/salvar-instagram.png"))); // NOI18N
        btCad.setText("Salvar OS");
        btCad.setFocusable(false);
        btCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadActionPerformed(evt);
            }
        });

        jSeparator4.setBackground(new java.awt.Color(255, 255, 255));

        btSearch1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        btSearch1.setText(" Limpar campos");
        btSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
            .addComponent(TabPanel, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btSearch1)
                .addGap(18, 18, 18)
                .addComponent(btCad, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(TabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCad)
                    .addComponent(btSearch1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddServiceActionPerformed
        // TODO add your handling code here:

        String sql = "select * from servicos where servico  = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cbServicos.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                String service = rs.getString(2);
                String detalhe = rs.getString(3);
                String valorGet = rs.getString(4);
                txServico.setText(service);
                txDetail.setText(detalhe);
                txValor.setText(valorGet.replaceAll(",", "."));

                if ((cbServicos.getSelectedItem().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    qtd = (int) SpQTDE.getValue();
                    precoServico = Double.parseDouble(txValor.getText());

                    subtotalServico = qtd * precoServico;
                    totalServico += subtotalServico;
                    alltotal = totalServico + totalProduto;

                    lbTotalService.setText("R$" + totalServico + "");
                    TotalOS.setText("" + alltotal);

                    carService = (DefaultTableModel) TableServices.getModel();

                    carService.addRow(new Object[]{
                        rs.getString(1),
                        txServico.getText(),
                        txDetail.getText(),
                        SpQTDE.getValue(),
                        txValor.getText(),
                        subtotalServico

                    });

                }

            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }//GEN-LAST:event_btAddServiceActionPerformed

    private void clearServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearServicosActionPerformed
        // TODO add your handling code here:
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar o carrinho?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            carService.setNumRows(0);
            String totalpg = TotalOS.getText();
            Double resulttotal = Double.valueOf(totalpg);
            String GetTotal = lbTotalService.getText().replaceAll("R", "");
            StringBuilder str = new StringBuilder(GetTotal);
            StringBuilder afterRemoval = str.deleteCharAt(0);
            Double TotalAll = Double.valueOf(afterRemoval.toString());
            double result = resulttotal - TotalAll;
            TotalOS.setText("" + result);
            lbTotalService.setText("R$ 0,0");
            totalServico = 0;

        }
    }//GEN-LAST:event_clearServicosActionPerformed

    private void deleteServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteServicosActionPerformed
        // TODO add your handling code here:
        DefaultTableModel dtm = (DefaultTableModel) TableServices.getModel();
        int setar = TableServices.getSelectedRow();
        if (TableServices.getSelectedRow() >= 0) {
            String getQ = (TableServices.getModel().getValueAt(setar, 5).toString());
            Double getQtde = Double.parseDouble(getQ);
            String GetTotal = lbTotalService.getText().replaceAll("R", "");
            StringBuilder str = new StringBuilder(GetTotal);
            StringBuilder afterRemoval = str.deleteCharAt(0);
            Double TotalAll = Double.parseDouble(afterRemoval.toString());
            double result = TotalAll - getQtde;
            totalServico = TotalAll - getQtde;
            lbTotalService.setText("R$" + result + "");

            String getTotalAll = TotalOS.getText();
            Double getTotalConverted = Double.parseDouble(getTotalAll);
            alltotal = getTotalConverted - getQtde;
            TotalOS.setText("" + alltotal);
            dtm.removeRow(TableServices.getSelectedRow());
            TableServices.setModel(dtm);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }//GEN-LAST:event_deleteServicosActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int qtdAtual, qtdComprada, qtdAtualizada;
        moduloOS osmodel = new moduloOS();
        DefaultTableModel dtmP = (DefaultTableModel) TableProducts.getModel();
        int setar = TableProducts.getSelectedRow();
        if (TableProducts.getSelectedRow() >= 0) {
            String getQ = (TableProducts.getModel().getValueAt(setar, 8).toString());
            Double getQtde = Double.parseDouble(getQ);
            String GetTotal = lbTotalProd.getText().replaceAll("R", "");
            StringBuilder str = new StringBuilder(GetTotal);
            StringBuilder afterRemoval = str.deleteCharAt(0);
            Double TotalAll = Double.parseDouble(afterRemoval.toString());
            double result = TotalAll - getQtde;
            totalProduto = TotalAll - getQtde;
            lbTotalProd.setText("R$" + result + "");
            String getTotalAll = TotalOS.getText();
            Double getTotalConverted = Double.parseDouble(getTotalAll);
            alltotal = getTotalConverted - getQtde;
            TotalOS.setText("" + alltotal);

            dtmP.removeRow(TableProducts.getSelectedRow());
            TableProducts.setModel(dtmP);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma linha!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void clearProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearProdutosActionPerformed
        // TODO add your handling code here:
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar o carrinho?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            carProd.setNumRows(0);
            String totalpg = TotalOS.getText();
            Double resulttotal = Double.valueOf(totalpg);
            String GetTotal = lbTotalProd.getText().replaceAll("R", "");
            StringBuilder str = new StringBuilder(GetTotal);
            StringBuilder afterRemoval = str.deleteCharAt(0);
            Double TotalAll = Double.valueOf(afterRemoval.toString());
            double result = resulttotal - TotalAll;
            TotalOS.setText("" + result);
            lbTotalProd.setText("R$ 0,0");
            totalProduto = 0;

        }
    }//GEN-LAST:event_clearProdutosActionPerformed

    private void cbProdutosPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbProdutosPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        selectProdutos();
    }//GEN-LAST:event_cbProdutosPopupMenuWillBecomeInvisible

    private void clienteOSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_clienteOSItemStateChanged

    }//GEN-LAST:event_clienteOSItemStateChanged

    private void clienteOSPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_clienteOSPopupMenuWillBecomeInvisible

        String sql = "select idclientes,cpf,tel,email from clientes where nome  = ?";
        String getNome = clienteOS.getSelectedItem().toString();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getNome);
            rs = pst.executeQuery();

            if (rs.next()) {
                String idCliente = rs.getString(1);
                String CpfCliente = rs.getString(2);
                String FoneCliente = rs.getString(3);
                String EmailCliente = rs.getString(4);

                txtid.setText(idCliente);
                cpfOS.setText(CpfCliente);
                telefoneOS.setText(FoneCliente);
                txEmail.setText(EmailCliente);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_clienteOSPopupMenuWillBecomeInvisible

    private void clienteOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clienteOSMouseClicked

    }//GEN-LAST:event_clienteOSMouseClicked

    private void clienteOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteOSActionPerformed

    }//GEN-LAST:event_clienteOSActionPerformed

    private void cpfOSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpfOSKeyPressed

    }//GEN-LAST:event_cpfOSKeyPressed

    private void cpfOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cpfOSKeyReleased

        cpfOS.setText(JMascara.GetJmascaraCpfCnpj(cpfOS.getText()));
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            colocarDadosForCPF();
        }
    }//GEN-LAST:event_cpfOSKeyReleased

    private void telefoneOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telefoneOSKeyReleased
        // TODO add your handling code here:
        telefoneOS.setText(JMascara.GetJmascaraFone(telefoneOS.getText()));
    }//GEN-LAST:event_telefoneOSKeyReleased

    private void cbFormaRecebiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbFormaRecebiPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (cbFormaRecebi.getSelectedItem().equals("ENTREGA")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            simpleDateFormat.format(date);
            // inputData.setText(bull)
            endEntrega.setText(null);
            valorFrete.setText(null);
            cbentregador.setEnabled(true);
            endEntrega.setEnabled(true);
            valorFrete.setEnabled(true);
            inputData.setEnabled(true);
            cbPagador.setEnabled(true);
            cbAddFrete.setVisible(true);
            cbAddFrete.setEnabled(true);
            cbPagador.setSelectedItem("CLIENTE");

        } else {

            endEntrega.setText("--");
            valorFrete.setText("--");
            endEntrega.setEnabled(false);
            cbentregador.setEnabled(false);
            valorFrete.setEnabled(false);
            inputData.setEnabled(false);
            cbPagador.setEnabled(false);
            cbPagador.setSelectedItem("--");
            cbAddFrete.setEnabled(false);

        }
    }//GEN-LAST:event_cbFormaRecebiPopupMenuWillBecomeInvisible

    private void cbFormaRecebiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormaRecebiActionPerformed

    }//GEN-LAST:event_cbFormaRecebiActionPerformed

    private void endEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_endEntregaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calcularKm();
        }
    }//GEN-LAST:event_endEntregaKeyPressed

    private void cbPagadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPagadorActionPerformed
        // TODO add your handling code here:
        if (cbPagador.getSelectedItem().equals("ESTABELECIMENTO")) {
            cbAddFrete.setVisible(false);
        } else {
            cbAddFrete.setVisible(true);
            cbAddFrete.setEnabled(true);
        }
    }//GEN-LAST:event_cbPagadorActionPerformed

    private void cbAddFreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAddFreteActionPerformed

//        if (endEntrega.getText().equals("")) {
//            JOptionPane.showMessageDialog(null, "Nenhum endereço selecionado!!");
//        } else {
//            try {
//                String getDescri = "Frete p/ " + endEntrega.getText() + "No dia " + inputData.getText();
//
//                Double ConvertFrete = Double.parseDouble(valorFrete.getText());
//                totalServico = ConvertFrete + totalServico;
//                alltotal = totalServico + totalProduto;
//                lbTotal.setText("R$" + totalServico + "");
//                TotalOS.setText("" + alltotal);
//
//                carService = (DefaultTableModel) TableServices.getModel();
//                carService.addRow(new Object[]{
//                    "Serviço de entrega",
//                    getDescri,
//                    "1",
//                    valorFrete.getText(),
//                    valorFrete.getText()
//
//                });
//
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Algo deu errado, revise os dados da entrega!");
//            }
//
//        }

    }//GEN-LAST:event_cbAddFreteActionPerformed

    private void btAddProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProductsActionPerformed
        // TODO add your handling code here:
        addprod();
    }//GEN-LAST:event_btAddProductsActionPerformed

    private void CBOFormapgmPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_CBOFormapgmPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (CBOFormapgm.getSelectedItem().equals("PIX")) {
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("Dinheiro")) {
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("Cartão de crédito")) {
            CBOParcelas.setEnabled(true);

        }
        if (CBOFormapgm.getSelectedItem().equals("Cartão de débito")) {
            CBOParcelas.setEnabled(false);

        }
        if (CBOFormapgm.getSelectedItem().equals("TED")) {
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("Dinheiro + cartão")) {
            CBOParcelas.setEnabled(true);

        }
        if (CBOFormapgm.getSelectedItem().equals("PIX + dinheiro")) {

            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("PIX + cartão")) {
            CBOParcelas.setEnabled(true);

        }
    }//GEN-LAST:event_CBOFormapgmPopupMenuWillBecomeInvisible

    private void CBOFormapgmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBOFormapgmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CBOFormapgmActionPerformed

    private void txdescontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txdescontoKeyPressed
        // TODO add your handling code here:

        try {
            valorcdescontoTotal.setText(null);
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                String number = TotalOS.getText().replaceAll(",", ".");
                String number1 = txdesconto.getText().replaceAll(",", ".");

                double num = Double.parseDouble(number);

                double num1 = Double.parseDouble(number1);
                if (num1 <= 100 && num1 >= 0) {
                    double subtotal, total;

                    subtotal = num * num1 / 100;
                    total = num - subtotal;

                    //String tt = Double.toString(total);
                    valorcdescontoTotal.setText(new DecimalFormat("##.##").format(total));
                } else {
                    JOptionPane.showMessageDialog(null, "Valor de desconto só pode ser menor que 100 ou maior que 0!");
                }

            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_txdescontoKeyPressed

    private void txdescontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txdescontoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txdescontoKeyReleased

    private void cbServicosPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbServicosPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        getServ();

    }//GEN-LAST:event_cbServicosPopupMenuWillBecomeInvisible

    private void CancelimgCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelimgCliente1ActionPerformed
        // TODO add your handling code here:
        if (viewImage1OS.getIcon() != null) {
            viewImage1OS.setIcon(null);

        } else {

            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");

        }
    }//GEN-LAST:event_CancelimgCliente1ActionPerformed

    private void uploadimg1OSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadimg1OSActionPerformed
        // TODO add your handling code here:
        imagemA = selecionarImagemA();
        abrirImagemA(imagemA);
    }//GEN-LAST:event_uploadimg1OSActionPerformed

    private void CancelimgCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelimgCliente2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CancelimgCliente2ActionPerformed

    private void uploadimg2OSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadimg2OSActionPerformed
        // TODO add your handling code here:
        imagemB = selecionarImagemB();
        abrirImagemB(imagemB);
    }//GEN-LAST:event_uploadimg2OSActionPerformed

    private void uploadimg3OSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadimg3OSActionPerformed
        // TODO add your handling code here:
        imagemC = selecionarImagemC();
        abrirImagemC(imagemC);
    }//GEN-LAST:event_uploadimg3OSActionPerformed

    private void CancelimgCliente3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelimgCliente3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CancelimgCliente3ActionPerformed

    private void cbentregadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbentregadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbentregadorActionPerformed

    private void btCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadActionPerformed
        // TODO add your handling code here:
        SaveOS();
    }//GEN-LAST:event_btCadActionPerformed

    private void btSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch1ActionPerformed
        // TODO add your handling code here:
        //refresh();
    }//GEN-LAST:event_btSearch1ActionPerformed

    private void cbProdutosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbProdutosKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addprod();
        }
    }//GEN-LAST:event_cbProdutosKeyPressed

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
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBOFormapgm;
    private javax.swing.JComboBox<String> CBOParcelas;
    public static javax.swing.JButton CancelimgCliente1;
    public static javax.swing.JButton CancelimgCliente2;
    public static javax.swing.JButton CancelimgCliente3;
    private javax.swing.JTextArea CausasOS;
    private javax.swing.JTextArea LaudoOS;
    private javax.swing.JTextArea MelhoriasOS;
    public static javax.swing.JTextArea ObsFrete;
    public static javax.swing.JPanel Painel1ImageOS;
    public static javax.swing.JPanel Painel2ImageOS;
    public static javax.swing.JPanel Painel3ImageOS;
    private javax.swing.JPanel PanelEquipamento;
    private javax.swing.JPanel PanelImagens;
    private javax.swing.JPanel PanelPecas;
    private javax.swing.JPanel PanelServico;
    private javax.swing.JTextField PrecoProdutosSelected;
    private javax.swing.JTextField PrecoServicoSelected;
    private javax.swing.JTextField QtdeProdutosSelected;
    private javax.swing.JTextArea RelatoOS;
    private javax.swing.JSpinner SpQTDE;
    private javax.swing.JSpinner SpQTDEp;
    private javax.swing.JTextArea StatusEquipamentoOS;
    private javax.swing.JTabbedPane TabPanel;
    private javax.swing.JTable TableProducts;
    private javax.swing.JTable TableServices;
    public static javax.swing.JTextField TotalOS;
    private javax.swing.JButton btAddProducts;
    private javax.swing.JButton btAddService;
    public static javax.swing.JButton btCad;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JComboBox<String> canalOS;
    public static javax.swing.JButton cbAddFrete;
    public static javax.swing.JComboBox<String> cbFormaRecebi;
    public static javax.swing.JComboBox<String> cbPagador;
    private javax.swing.JComboBox<String> cbProdutos;
    private javax.swing.JComboBox<String> cbServicos;
    public static javax.swing.JComboBox<String> cbentregador;
    private javax.swing.JButton clearProdutos;
    private javax.swing.JButton clearServicos;
    public static javax.swing.JComboBox<String> clienteOS;
    private javax.swing.JTextField config;
    public static javax.swing.JFormattedTextField cpfOS;
    private javax.swing.JTextField datacadastroOS;
    private javax.swing.JButton deleteServicos;
    public static javax.swing.JTextField endEntrega;
    public static com.toedter.calendar.JDateChooser entrada;
    private javax.swing.JComboBox<String> equipamento;
    private javax.swing.JTextField idservice;
    private javax.swing.JLabel indicarEstoque;
    public static com.toedter.calendar.JDateChooser inputData;
    private javax.swing.JTextField itens;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lbTotalProd;
    private javax.swing.JLabel lbTotalService;
    private javax.swing.JTextField marca;
    private javax.swing.JTextField modelo;
    private javax.swing.JLabel nos;
    public static javax.swing.JTextArea obsOS;
    private javax.swing.JPanel pEntrega;
    private javax.swing.JPanel painelEquipamentos;
    private javax.swing.JScrollPane scrollEquipamentos;
    private javax.swing.JScrollPane scrollImage;
    private javax.swing.JTextField serial;
    public static javax.swing.JComboBox<String> status;
    public static javax.swing.JFormattedTextField telefoneOS;
    private javax.swing.JTextField txCodigob;
    private javax.swing.JTextField txDescri;
    private javax.swing.JTextField txDetail;
    public static javax.swing.JTextField txEmail;
    private javax.swing.JTextField txIdProdutoOS;
    private javax.swing.JTextField txMarca;
    private javax.swing.JTextField txPreco;
    private javax.swing.JTextField txServico;
    private javax.swing.JTextField txValor;
    private javax.swing.JFormattedTextField txdesconto;
    private javax.swing.JTextField txserialOS;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JButton uploadimg1OS;
    public static javax.swing.JButton uploadimg2OS;
    public static javax.swing.JButton uploadimg3OS;
    public static com.toedter.calendar.JDateChooser validadeOS;
    public static javax.swing.JFormattedTextField valorFrete;
    private javax.swing.JFormattedTextField valorcdescontoTotal;
    public static javax.swing.JComboBox<String> vendedorOS;
    public static javax.swing.JLabel viewImage1OS;
    public static javax.swing.JLabel viewImage2OS;
    public static javax.swing.JLabel viewImage3OS;
    // End of variables declaration//GEN-END:variables
}
