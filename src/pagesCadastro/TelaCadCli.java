package pagesCadastro;

import Jm.JMascara;
import Verificadores.CNPJ;
import Verificadores.CPF;
import br.com.tanamao.dal.LimitaNroCaracteres;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import br.com.tanamao.dal.WebServiceCep;
import Verificadores.validarEmail;
import br.com.tanamao.model.ConfigWhats;
import Verificadores.DadosCNPJ;
import com.google.gson.Gson;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import jnafilechooser.api.JnaFileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import notification.Notification;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import pagesSearchs.SearchCliente;
import pages.TelaPrincipal;
import static pages.TelaPrincipal.empresa;
import static pages.TelaPrincipal.perfil;

public final class TelaCadCli extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public byte image1;
    public byte image2;
    public byte image3;

    private File imagemA;
    private File imagemB;
    private File imagemC;

    public TelaCadCli() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        SetDate();
        verifUser();
        uper();
        ListarDadosEtiquetas();
        Formatter();
        formatarpreco();
        setVisibleandEnableds();
        this.jScrollPane3.getVerticalScrollBar().setUnitIncrement(20);
        showColor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

    }

    public void setVisibleandEnableds() {
        AlterImage1.setVisible(false);
        AlterImage2.setVisible(false);
        AlterImage3.setVisible(false);
        habilitar1.setVisible(false);
        habilitar2.setVisible(false);
        habilitar3.setVisible(false);
        btImprimir.setVisible(false);
        textPix.setEnabled(false);
        textBanco.setEnabled(false);
        btWPP.setVisible(false);
    }


    public void verifUser() {
        if (perfil.getText().equals("user")) {
            LimiteCredito.setEnabled(false);
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

                BufferedImage novaImagem = new BufferedImage(Painel1ImageCliente.getWidth() + 200, Painel1ImageCliente.getHeight() + 200, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel1ImageCliente.getWidth() + 200, Painel1ImageCliente.getHeight() + 200, null);

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

                BufferedImage novaImagem = new BufferedImage(Painel2ImageCliente.getWidth() + 200, Painel2ImageCliente.getHeight() + 200, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel2ImageCliente.getWidth() + 200, Painel2ImageCliente.getHeight() + 200, null);

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

                BufferedImage novaImagem = new BufferedImage(Painel3ImageCliente.getWidth() + 300, Painel3ImageCliente.getHeight() + 300, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel3ImageCliente.getWidth() + 300, Painel3ImageCliente.getHeight() + 300, null);

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

    public void soundClickRemove() {
        try {

            URL sd = getClass().getResource("/sounds/trash.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sd));
            c.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {

        }
    }

    public void uper() {
        TextNome.setDocument(new UpperCase());
        TextNumber.setDocument(new UpperCase());
        TextFantasia.setDocument(new UpperCase());
        TextRazao.setDocument(new UpperCase());
        TextEmail.setDocument(new UpperCase());
        TextEND.setDocument(new UpperCase());
        TextCity.setDocument(new UpperCase());
        TextBairro.setDocument(new UpperCase());
        TextCOMP.setDocument(new UpperCase());
        txtInfo.setDocument(new UpperCase());
        txtObs.setDocument(new UpperCase());
    }

    public void Formatter() {

        try {
            MaskFormatter form = new MaskFormatter("##/##/####");
            TextNASC.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }
    }

    public void addLogCad() {

        String sql = "insert into logclientes(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Add");
            pst.setString(2, "Cliente " + cliente + " adicionado por  " + user + "");
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

        String sql = "insert into logclientes(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Edit");
            pst.setString(2, "Cliente  " + cliente + " editado por  " + user + "");
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

        String sql = "insert into logclientes(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Remove");
            pst.setString(2, "Cliente  " + cliente + " removido por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void ListarDadosEtiquetas() {
        String sql = "select etiqueta from etiquetas order by etiqueta";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cboEtiquetas.addItem(nome);

            }

        } catch (SQLException e) {

        }
    }

    public void puxarcep() {

        String cep = TextCEP.getText();
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);

        if (webServiceCep.wasSuccessful()) {
            TextEND.setText(webServiceCep.getLogradouroFull());
            TextCity.setText(webServiceCep.getCidade());
            TextBairro.setText(webServiceCep.getBairro());
            CBOuf.setSelectedItem(webServiceCep.getUf());
            TextNumber.requestFocus();

        } else {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>CEP inválido!</html>", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    public void SetDate() {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy/MM/dd");
        String date = formatador.format(data);
        txtDataC.setText(date);
    }

    public void puxarcepNoError() {

        String cep = TextCEP.getText();
        WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);

        if (webServiceCep.wasSuccessful()) {
            TextEND.setText(webServiceCep.getLogradouroFull());
            TextCity.setText(webServiceCep.getCidade());
            TextBairro.setText(webServiceCep.getBairro());
            CBOuf.setSelectedItem(webServiceCep.getUf());
            TextNumber.requestFocus();

        } else {

        }
    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        LimiteCredito.setFormatterFactory(dfFactory);

    }

    private void abrirImagem(String imagePath, int index) {

        switch (index) {
            case 1:
                ImageIcon imagemIcon1 = new ImageIcon(imagePath);
                imagemIcon1.setImage(imagemIcon1.getImage().getScaledInstance(Painel1ImageCliente.getWidth(), Painel1ImageCliente.getHeight(), 100));
                viewImage1Cliente.setIcon(imagemIcon1);
                break;
            case 2:
                ImageIcon imagemIcon2 = new ImageIcon(imagePath);
                imagemIcon2.setImage(imagemIcon2.getImage().getScaledInstance(Painel2ImageCliente.getWidth(), Painel2ImageCliente.getHeight(), 100));
                viewImage2Cliente.setIcon(imagemIcon2);
                break;
            case 3:
                ImageIcon imagemIcon3 = new ImageIcon(imagePath);
                imagemIcon3.setImage(imagemIcon3.getImage().getScaledInstance(Painel3ImageCliente.getWidth(), Painel3ImageCliente.getHeight(), 100));
                viewImage3Cliente.setIcon(imagemIcon3);
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
                imagemIcon1.setImage(imagemIcon1.getImage().getScaledInstance(Painel1ImageCliente.getWidth(), Painel1ImageCliente.getHeight(), 100));
                viewImage1Cliente.setIcon(imagemIcon1);
                break;
            case 2:
                ImageIcon imagemIcon2 = new ImageIcon(imagePath);
                imagemIcon2.setImage(imagemIcon2.getImage().getScaledInstance(Painel2ImageCliente.getWidth(), Painel2ImageCliente.getHeight(), 100));
                viewImage2Cliente.setIcon(imagemIcon2);
                break;
            case 3:
                ImageIcon imagemIcon3 = new ImageIcon(imagePath);
                imagemIcon3.setImage(imagemIcon3.getImage().getScaledInstance(Painel3ImageCliente.getWidth(), Painel3ImageCliente.getHeight(), 100));
                viewImage3Cliente.setIcon(imagemIcon3);
                break;
            default:
                // Faça algo caso o índice seja inválido
                break;
        }
    }

    public void adicionar() {

        String sql = "insert into clientes(pessoa,nome,cpf,razaosoc,fantasia,sexo,datanasc,tel,email,cep,endereco,cidade,bairro,numero,complemento,uf,inativo,info,obs,datacad,empresa,tipochave,pix,banco,etiqueta,anexo1,anexo2,anexo3,limitecredito,descontopadrao) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            String cpf = TextCPF.getText();
            CPF pf = new CPF(cpf);
            CNPJ pj = new CNPJ(cpf);
            validarEmail vle = new validarEmail();

            pst = conexao.prepareStatement(sql);

            if (CheckPF.isSelected() || CheckPJ.isSelected()) {

                if (CheckPF.isSelected()) {
                    pst.setString(1, "PF");
                    pst.setString(2, TextNome.getText());
                    pst.setString(3, TextCPF.getText());
                    pst.setString(4, TextRazao.getText());
                    pst.setString(5, TextFantasia.getText());
                    pst.setString(6, CBOSexo.getSelectedItem().toString());
                    pst.setString(7, TextNASC.getText());
                    pst.setString(8, TextFONE.getText());
                    pst.setString(9, TextEmail.getText());
                    pst.setString(10, TextCEP.getText());
                    pst.setString(11, TextEND.getText());
                    pst.setString(12, TextCity.getText());
                    pst.setString(13, TextBairro.getText());
                    pst.setString(14, TextNumber.getText());
                    pst.setString(15, TextCOMP.getText());
                    pst.setString(16, CBOuf.getSelectedItem().toString());
                    pst.setString(17, Inativo.getSelectedItem().toString());
                    pst.setString(18, txtInfo.getText());
                    pst.setString(19, txtObs.getText());
                    pst.setString(20, txtDataC.getText());
                    pst.setString(21, empresa.getText());
                    pst.setString(22, tipoChave.getSelectedItem().toString());
                    pst.setString(23, textPix.getText());
                    pst.setString(24, textBanco.getSelectedItem().toString());
                    pst.setString(25, cboEtiquetas.getSelectedItem().toString());
                    pst.setBytes(26, getImagem1());
                    pst.setBytes(27, getImagem2());
                    pst.setBytes(28, getImagem3());
                    pst.setString(29, LimiteCredito.getText());
                    pst.setString(30, DescontoPadrao.getText());

                } else {

                    pst.setString(1, "PJ");
                    pst.setString(2, TextNome.getText());
                    pst.setString(3, TextCPF.getText());
                    pst.setString(4, TextRazao.getText());
                    pst.setString(5, TextFantasia.getText());
                    pst.setString(6, CBOSexo.getSelectedItem().toString());
                    pst.setString(7, TextNASC.getText());
                    pst.setString(8, TextFONE.getText());
                    pst.setString(9, TextEmail.getText());
                    pst.setString(10, TextCEP.getText());
                    pst.setString(11, TextEND.getText());
                    pst.setString(12, TextCity.getText());
                    pst.setString(13, TextBairro.getText());
                    pst.setString(14, TextNumber.getText());
                    pst.setString(15, TextCOMP.getText());
                    pst.setString(16, CBOuf.getSelectedItem().toString());
                    pst.setString(17, Inativo.getSelectedItem().toString());
                    pst.setString(18, txtInfo.getText());
                    pst.setString(19, txtObs.getText());
                    pst.setString(20, txtDataC.getText());
                    pst.setString(21, empresa.getText());
                    pst.setString(22, tipoChave.getSelectedItem().toString());
                    pst.setString(23, textPix.getText());
                    pst.setString(24, textBanco.getSelectedItem().toString());
                    pst.setString(25, cboEtiquetas.getSelectedItem().toString());
                    pst.setBytes(26, getImagem1());
                    pst.setBytes(27, getImagem2());
                    pst.setBytes(28, getImagem3());
                    pst.setString(29, LimiteCredito.getText());
                    pst.setString(30, DescontoPadrao.getText());
                }
            } else {
                Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Selecione um tipo de pessoa!");
                noti.showNotification();
            }

            if (CheckPF.isSelected()) {

                if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty()) || (TextCPF.getText().isEmpty()) || (TextNASC.getText().isEmpty()) || (TextCEP.getText().isEmpty()) || (TextEND.getText().isEmpty()) || (TextCity.getText().isEmpty()) || (TextBairro.getText().isEmpty()) || (TextNumber.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    if (vle.ValidarEmail(TextEmail.getText())) {
                        if (pf.isCPF()) {
                            int adicionado = pst.executeUpdate();
                            if (adicionado > 0) {
                                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente cadastrado com sucesso!");
                                noti.showNotification();
                                addLogCad();
                                refresh();
                            }

                        } else {
                            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "CPF inválido!!");
                            noti.showNotification();
                        }
                    } else {
                        Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "E-mail inválido!!");
                        noti.showNotification();
                    }
                }
            }
            if (CheckPJ.isSelected()) {

                if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty()) || (TextCPF.getText().isEmpty()) || (TextRazao.getText().isEmpty()) || (TextCEP.getText().isEmpty()) || (TextEND.getText().isEmpty()) || (TextCity.getText().isEmpty()) || (TextBairro.getText().isEmpty()) || (TextNumber.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    if (vle.ValidarEmail(TextEmail.getText())) {
                        if (pj.isCNPJ()) {
                            int adicionado = pst.executeUpdate();
                            if (adicionado > 0) {
                                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente cadastrado com sucesso!");
                                noti.showNotification();
                                addLogCad();
                                refresh();

                            }

                        } else {
                            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "CNPJ inválido!!");
                            noti.showNotification();

                        }
                    } else {
                        Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "E-mail inválido!!");
                        noti.showNotification();
                    }
                }
            }

        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao cadastrar o cliente.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {
        String sql = "update clientes set pessoa=?,nome=?,cpf=?,razaosoc=?,fantasia=?,sexo=?,datanasc=?,tel=?,email=?,cep=?,endereco=?,cidade=?,bairro=?,numero=?,complemento=?,uf=?,inativo=?,info=?,obs=?,tipochave=?,pix=?,banco=?,etiqueta=?,limitecredito=?,descontopadrao=? where idclientes=?";
        try {
            pst = conexao.prepareStatement(sql);
            if (CheckPF.isSelected()) {
                pst.setString(1, "PF");
                pst.setString(2, TextNome.getText());
                pst.setString(3, TextCPF.getText());
                pst.setString(4, TextRazao.getText());
                pst.setString(5, TextFantasia.getText());
                pst.setString(6, CBOSexo.getSelectedItem().toString());
                pst.setString(7, TextNASC.getText());
                pst.setString(8, TextFONE.getText());
                pst.setString(9, TextEmail.getText());
                pst.setString(10, TextCEP.getText());
                pst.setString(11, TextEND.getText());
                pst.setString(12, TextCity.getText());
                pst.setString(13, TextBairro.getText());
                pst.setString(14, TextNumber.getText());
                pst.setString(15, TextCOMP.getText());
                pst.setString(16, CBOuf.getSelectedItem().toString());
                pst.setString(17, Inativo.getSelectedItem().toString());
                pst.setString(18, txtInfo.getText());
                pst.setString(19, txtObs.getText());
                pst.setString(20, tipoChave.getSelectedItem().toString());
                pst.setString(21, textPix.getText());
                pst.setString(22, textBanco.getSelectedItem().toString());
                pst.setString(23, cboEtiquetas.getSelectedItem().toString());
                pst.setString(24, LimiteCredito.getText());
                pst.setString(25, DescontoPadrao.getText());
                pst.setString(26, TextID.getText());

            } else {
                pst.setString(1, "PJ");
                pst.setString(2, TextNome.getText());
                pst.setString(3, TextCPF.getText());
                pst.setString(4, TextRazao.getText());
                pst.setString(5, TextFantasia.getText());
                pst.setString(6, CBOSexo.getSelectedItem().toString());
                pst.setString(7, TextNASC.getText());
                pst.setString(8, TextFONE.getText());
                pst.setString(9, TextEmail.getText());
                pst.setString(10, TextCEP.getText());
                pst.setString(11, TextEND.getText());
                pst.setString(12, TextCity.getText());
                pst.setString(13, TextBairro.getText());
                pst.setString(14, TextNumber.getText());
                pst.setString(15, TextCOMP.getText());
                pst.setString(16, CBOuf.getSelectedItem().toString());
                pst.setString(17, Inativo.getSelectedItem().toString());
                pst.setString(18, txtInfo.getText());
                pst.setString(19, txtObs.getText());
                pst.setString(20, tipoChave.getSelectedItem().toString());
                pst.setString(21, textPix.getText());
                pst.setString(22, textBanco.getSelectedItem().toString());
                pst.setString(23, cboEtiquetas.getSelectedItem().toString());
                pst.setString(24, LimiteCredito.getText());
                pst.setString(25, DescontoPadrao.getText());
                pst.setString(26, TextID.getText());

            }

            if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente editado com sucesso!");
                    noti.showNotification();
                    addLogEdit();
                    refresh();

                }
            }
        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar os dados do cliente.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from clientes where idclientes=?";
            try {

                if ((TextID.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado!!");
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, TextID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Cliente removido com sucesso!");
                    noti.showNotification();
                    addLogRemove();
                    soundClickRemove();
                    refresh();

                }

            } catch (Exception e) {

            }
        }
    }

    public void refresh() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar os campos?", "<html><b>Message Service!</b></html>", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            try {

                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Dados limpos com sucesso!");
                noti.showNotification();
                TextBairro.setText(null);
                TextCEP.setText(null);
                TextCOMP.setText(null);
                TextCPF.setText(null);
                TextCity.setText(null);
                TextRazao.setText(null);
                TextFantasia.setText(null);
                TextEND.setText(null);
                TextEmail.setText(null);
                TextFONE.setText(null);
                TextID.setText(null);
                TextNASC.setText(null);
                TextNome.setText(null);
                TextNumber.setText(null);
                LimiteCredito.setFormatterFactory(new DefaultFormatterFactory(null));
                LimiteCredito.setText("");
                DescontoPadrao.setText(null);
                textPix.setFormatterFactory(new DefaultFormatterFactory(null));
                textPix.setText("");
                textBanco.setSelectedItem("--");
                tipoChave.setSelectedItem("--");
                viewImage1Cliente.setIcon(null);
                viewImage2Cliente.setIcon(null);
                viewImage3Cliente.setIcon(null);
                habilitar1.setVisible(false);
                habilitar2.setVisible(false);
                habilitar3.setVisible(false);
                AlterImage1.setVisible(false);
                AlterImage2.setVisible(false);
                AlterImage3.setVisible(false);
                uploadimgCliente1.setEnabled(true);
                CancelimgCliente1.setEnabled(true);
                CancelimgCliente2.setEnabled(true);
                CancelimgCliente3.setEnabled(true);
                textPix.setEnabled(false);
                textBanco.setEnabled(false);
                Inativo.setSelectedItem("Ativo");
                txtInfo.setText(null);
                txtObs.setText(null);
                btCad.setEnabled(true);
                textPix.setText(null);
                btWPP.setVisible(false);
                btImprimir.setVisible(false);
                Color colorB = new Color(255, 255, 255);
                btCad.setBackground(colorB);
                btEdit.setEnabled(false);
                btExcluir.setEnabled(false);
                CheckPF.setSelected(false);
                CheckPJ.setSelected(false);
                CheckPJ.setEnabled(true);
                CheckPF.setEnabled(true);
                searchCNPJ.setEnabled(false);
                TextRazao.setEnabled(true);
                TextFantasia.setEnabled(true);
                SetDate();

            } catch (Exception e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao limpar os campos.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
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

    public void getCnpj() {
        if (TextCPF.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo do CNPJ vazio!");
        } else {
            try {
                String Getcnpj = TextCPF.getText();
                String CnpjFormated = Getcnpj.replaceAll("[^\\d]", "");
                String url = "https://www.receitaws.com.br/v1/cnpj/" + CnpjFormated + "";

                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                    JOptionPane.showMessageDialog(null, "<html><b>Erro ao consultar o CNPJ.</b></html>\n" + conn.getResponseCode() + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String output = "";
                String line;
                while ((line = br.readLine()) != null) {
                    output += line;
                }

                conn.disconnect();

                Gson gson = new Gson();
                DadosCNPJ dados = gson.fromJson(new String(output.getBytes()), DadosCNPJ.class);

                TextNome.setText(dados.getNome());
                TextFONE.setText(dados.getTelefone());
                TextEmail.setText(dados.getEmail());
                TextFantasia.setText(dados.getFantasia());
                TextCEP.setText(dados.getCep());
                TextRazao.setText(dados.getNome());

            } catch (IOException ex) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao consultar o CNPJ.</b></html>\n" + ex + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TextID = new javax.swing.JTextField();
        corHex = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TextNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextCity = new javax.swing.JTextField();
        TextBairro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        CBOuf = new javax.swing.JComboBox<>();
        TextEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        CBOSexo = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TextCOMP = new javax.swing.JTextField();
        TextNumber = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TextEND = new javax.swing.JTextField();
        CheckPF = new javax.swing.JCheckBox();
        CheckPJ = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        TextRazao = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        TextFantasia = new javax.swing.JTextField();
        TextCPF = new javax.swing.JFormattedTextField();
        TextNASC = new javax.swing.JFormattedTextField();
        TextFONE = new javax.swing.JFormattedTextField();
        TextCEP = new javax.swing.JFormattedTextField();
        searchCNPJ = new javax.swing.JButton();
        btWPP = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        btImprimir = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtInfo = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        txtDataC = new javax.swing.JTextField();
        Inativo = new javax.swing.JComboBox<>();
        cboEtiquetas = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        showColor = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        textBanco = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        tipoChave = new javax.swing.JComboBox<>();
        textPix = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        DescontoPadrao = new javax.swing.JTextField();
        LimiteCredito = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        Painel1ImageCliente = new javax.swing.JPanel();
        viewImage1Cliente = new javax.swing.JLabel();
        CancelimgCliente1 = new javax.swing.JButton();
        uploadimgCliente1 = new javax.swing.JButton();
        CancelimgCliente2 = new javax.swing.JButton();
        Painel2ImageCliente = new javax.swing.JPanel();
        viewImage2Cliente = new javax.swing.JLabel();
        CancelimgCliente3 = new javax.swing.JButton();
        Painel3ImageCliente = new javax.swing.JPanel();
        viewImage3Cliente = new javax.swing.JLabel();
        AlterImage1 = new javax.swing.JButton();
        AlterImage2 = new javax.swing.JButton();
        AlterImage3 = new javax.swing.JButton();
        habilitar1 = new javax.swing.JCheckBox();
        habilitar2 = new javax.swing.JCheckBox();
        habilitar3 = new javax.swing.JCheckBox();
        btCad = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
        btSearch1 = new javax.swing.JButton();

        TextID.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TextID.setEnabled(false);

        corHex.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Clientes");
        setResizable(false);
        setSize(new java.awt.Dimension(925, 650));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("NOME COMPLETO * ");

        TextNome.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextNome.setAlignmentY(1.5F);
        TextNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextNomeKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("CEP *");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("CIDADE *");

        TextCity.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        TextBairro.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBairroActionPerformed(evt);
            }
        });
        TextBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBairroKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("BAIRRO *");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("UF *");

        CBOuf.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        CBOuf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        TextEmail.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextEmailKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextEmailKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("E-MAIL *");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel11.setText("CPF / CNPJ *");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("TELEFONE *");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("DATA NASCIMENTO *");

        CBOSexo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CBOSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MASCULINO", "FEMININO", "OUTROS" }));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("SEXO");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("COMPLEMENTO");

        TextCOMP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCOMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextCOMPActionPerformed(evt);
            }
        });
        TextCOMP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextCOMPKeyReleased(evt);
            }
        });

        TextNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNumberActionPerformed(evt);
            }
        });
        TextNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextNumberKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("NÚMERO *");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("ENDEREÇO *");

        TextEND.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextEND.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TextENDFocusGained(evt);
            }
        });
        TextEND.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TextENDMouseClicked(evt);
            }
        });
        TextEND.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextENDKeyReleased(evt);
            }
        });

        CheckPF.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        CheckPF.setText("PESSOA FÍSICA *");
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

        CheckPJ.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        CheckPJ.setText("PESSOA JURÍDICA *");
        CheckPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckPJActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("NOME FANTASIA");

        TextRazao.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel20.setText("RAZÃO SOCIAL *");

        TextFantasia.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        TextCPF.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextCPFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextCPFKeyReleased(evt);
            }
        });

        TextNASC.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        TextFONE.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextFONE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextFONEKeyReleased(evt);
            }
        });

        TextCEP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextCEPKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextCEPKeyReleased(evt);
            }
        });

        searchCNPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/search.png"))); // NOI18N
        searchCNPJ.setEnabled(false);
        searchCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchCNPJActionPerformed(evt);
            }
        });

        btWPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/whatsapp-2.png"))); // NOI18N
        btWPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWPPActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("* Campos obrigatórios");

        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/imprimir.png"))); // NOI18N
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(67, 67, 67)
                                        .addComponent(TextCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel16))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextFONE, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextNASC, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel18))
                                        .addGap(22, 22, 22))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(searchCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel20)
                                        .addGap(20, 20, 20)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextRazao, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CBOSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(CheckPF)
                                .addGap(17, 17, 17)
                                .addComponent(CheckPJ)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(70, 70, 70)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(TextBairro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(TextCity, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(338, 338, 338)))
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(TextEND, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(TextNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(TextCOMP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(CBOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel10))
                                        .addGap(300, 300, 300)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btWPP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btWPP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CheckPF)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CheckPJ)
                                .addComponent(jLabel23)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(TextRazao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(TextCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(TextFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CBOSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextNASC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextFONE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEND, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TextCity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TextNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TextCOMP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(TextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(CBOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados cadastrais", jPanel4);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("OUTRAS INFORMAÇÕES");

        txtInfo.setColumns(20);
        txtInfo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtInfo.setRows(5);
        jScrollPane1.setViewportView(txtInfo);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("OBSERVAÇÕES DO CLIENTE");

        txtObs.setColumns(20);
        txtObs.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtObs.setRows(5);
        jScrollPane2.setViewportView(txtObs);

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("DATA DE CADASTRO");

        txtDataC.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDataC.setEnabled(false);

        Inativo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        Inativo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativo", "Inativo" }));

        cboEtiquetas.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cboEtiquetas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S/E" }));
        cboEtiquetas.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cboEtiquetasPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("ETIQUETA:");

        javax.swing.GroupLayout showColorLayout = new javax.swing.GroupLayout(showColor);
        showColor.setLayout(showColorLayout);
        showColorLayout.setHorizontalGroup(
            showColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        showColorLayout.setVerticalGroup(
            showColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addComponent(jLabel22)
                            .addComponent(txtDataC))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(showColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cboEtiquetas, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Inativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(15, 15, 15)
                        .addComponent(cboEtiquetas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(showColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel22)
                .addGap(15, 15, 15)
                .addComponent(txtDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(Inativo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Mais informações", jPanel5);

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel25.setText("CHAVE PIX ");

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel26.setText("BANCO PRINC.");

        textBanco.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        textBanco.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "Banco ABC Brasil S.A", "Banco Cooperativo Sicredi S.A", "Advanced Cc Ltda", "Banco Agibank S.A", "Albatross Ccv S.A", "Ativa Investimentos S.A", "Avista S.A. Crédito, Financiamento e Investimento", "B&T Cc Ltda", "Banco A.J.Renner S.A", "Banco ABC Brasil S.A", "Banco ABN AMRO S.A", "Banco Agibank S.A", "Banco Alfa S.A", "Banco Alvorada S.A", "Banco Andbank (Brasil) S.A", "Banco Arbi S.A", "Banco B3 S.A", "Banco BANDEPE S.A", "Banco BMG S.A", "Banco BNP Paribas Brasil S.A", "Banco BOCOM BBM S.A", "Banco Bradescard S.A", "Banco Bradesco BBI S.A", "Banco Bradesco BERJ S.A", "Banco Bradesco Cartões S.A", "Banco Bradesco Financiamentos S.A", "Banco Bradesco S.A", "Banco BS2 S.A", "Banco BTG Pactual S.A", "Banco C6 S.A – C6 Bank", "Banco Caixa Geral – Brasil S.A", "Banco Capital S.A", "Banco Cargill S.A", "Banco Carrefour", "Banco Cédula S.A", "Banco Cetelem S.A", "Banco Cifra S.A", "Banco Citibank S.A", "Banco Clássico S.A", "Banco Cooperativo do Brasil S.A. – BANCOOB", "Banco Cooperativo Sicredi S.A", "Banco Credit Agricole Brasil S.A", "Banco Credit Suisse (Brasil) S.A", "Banco Crefisa S.A", "Banco da Amazônia S.A", "Banco da China Brasil S.A", "Banco Daycoval S.A", "Banco de Desenvolvimento do Espírito Santo S.A", "Banco de La Nacion Argentina", "Banco de La Provincia de Buenos Aires", "Banco de La Republica Oriental del Uruguay", "Banco Digio S.A", "Banco do Brasil S.A", "Banco do Estado de Sergipe S.A", "Banco do Estado do Pará S.A", "Banco do Estado do Rio Grande do Sul S.A", "Banco do Nordeste do Brasil S.A", "Banco Fair Corretora de Câmbio S.A", "Banco Fator S.A", "Banco Fibra S.A", "Banco Ficsa S.A", "Banco Finaxis S.A", "Banco Guanabara S.A", "Banco Inbursa S.A", "Banco Industrial do Brasil S.A", "Banco Indusval S.A", "Banco Inter S.A", "Banco Investcred Unibanco S.A", "Banco Itaú BBA S.A", "Banco Itaú Consignado S.A\t", "Banco ItauBank S.A", "Banco J. P. Morgan S.A", "Banco J. Safra S.A", "Banco John Deere S.A", "Banco KDB S.A", "Banco KEB HANA do Brasil S.A", "Banco Luso Brasileiro S.A", "Banco Máxima S.A", "Banco Maxinvest S.A", "Banco Mercantil de Investimentos S.A", "Banco Mercantil do Brasil S.A", "Banco Mizuho do Brasil S.A", "Banco Modal S.A", "Banco Morgan Stanley S.A", "Banco MUFG Brasil S.A", "Banco Nacional de Desenvolvimento Econômico e Social – BNDES", "Banco Olé Bonsucesso Consignado S.A", "Banco Oliveira Trust Dtvm S.A", "Banco Original do Agronegócio S.A", "Banco Original S.A", "Banco PAN S.A", "Banco Paulista S.A", "Banco Pine S.A", "Banco Porto Real de Investimentos S.A", "Banco Rabobank International Brasil S.A", "Banco Rendimento S.A", "Banco Ribeirão Preto S.A", "Banco Rodobens S.A", "Banco Safra S.A", "Banco Santander (Brasil) S.A", "Banco Semear S.A.", "Banco Sistema S.A", "Banco Smartbank S.A", "Banco Société Générale Brasil S.A", "Banco Sofisa S.A", "Banco Sumitomo Mitsui Brasileiro S.A", "Banco Topázio S.A", "Banco Triângulo S.A", "Banco Tricury S.A", "Banco Votorantim S.A", "Banco VR S.A", "Banco Western Union do Brasil S.A", "Banco Woori Bank do Brasil S.A", "Banco Xp S/A", "BancoSeguro S.A", "BANESTES S.A. Banco do Estado do Espírito Santo", "Bank of America Merrill Lynch Banco Múltiplo S.A", "Barigui Companhia Hipotecária", "BCV – Banco de Crédito e Varejo S.A", "BEXS Banco de Câmbio S.A", "Bexs Corretora de Câmbio S/A", "Bgc Liquidez Dtvm Ltda", "BNY Mellon Banco S.A", "Bpp Instituição De Pagamentos S.A", "BR Partners Banco de Investimento S.A", "BRB – Banco de Brasília S.A", "Brickell S.A. Crédito, Financiamento e Investimento", "BRL Trust Distribuidora de Títulos e Valores Mobiliários S.A", "Broker Brasil Cc Ltda", "BS2 Distribuidora de Títulos e Valores Mobiliários S.A", "C.Suisse Hedging-Griffo Cv S.A (Credit Suisse)", "Caixa Econômica Federal", "Carol Distribuidora de Títulos e Valor Mobiliários Ltda", "Caruana Scfi", "Casa Credito S.A", "Ccm Desp Trâns Sc E Rs", "Ccr Reg Mogiana", "Central Cooperativa De Crédito No Estado Do Espírito Santo", "Central das Cooperativas de Economia e Crédito Mútuo doEstado do Espírito Santo Ltda", "China Construction Bank (Brasil) Banco Múltiplo S.A", "Citibank N.A", "Cm Capital Markets Cctvm Ltda", "Codepe Cvc S.A", "Commerzbank Brasil S.A. – Banco Múltiplo", "Confidence Cc S.A", "Coop Central Ailos", "Cooperativa Central de Crédito Noroeste Brasileiro Ltda", "Cooperativa Central de Crédito Urbano-CECRED", "Cooperativa Central de Economia e Crédito Mutuo – SICOOB UNIMAIS", "Cooperativa Central de Economia e Crédito Mútuo das Unicredsde Santa Catarina e Paraná", " Cooperativa de Crédito Rural da Região da Mogiana", "Cooperativa de Crédito Rural De Ouro", "Cooperativa de Crédito Rural de Primavera Do Leste", "Cooperativa de Crédito Rural de São Miguel do Oeste – Sulcredi/São Miguel", "Credialiança Ccr", "CREDIALIANÇA COOPERATIVA DE CRÉDITO RURAL", "Credicoamo", "Cresol Confederação", "Dacasa Financeira S/A", "Banco Daycoval S.A", "Deutsche Bank S.A. – Banco Alemão", "Easynvest – Título Cv S.A", "Facta S.A. Cfi", "Frente Corretora de Câmbio Ltda", "Genial Investimentos Corretora de Valores Mobiliários S.A", "Get Money Cc Ltda", "Goldman Sachs do Brasil Banco Múltiplo S.A", "Guide Investimentos S.A. Corretora de Valores", "Guitta Corretora de Câmbio Ltda", "Haitong Banco de Investimento do Brasil S.A", "Hipercard Banco Múltiplo S.A", "HS Financeira S/A Crédito, Financiamento e Investimentos", "HSBC Brasil S.A. – Banco de Investimento", "IB Corretora de Câmbio, Títulos e Valores Mobiliários S.A", "Icap Do Brasil Ctvm Ltda", "ICBC do Brasil Banco Múltiplo S.A", "ING Bank N.V", "Intesa Sanpaolo Brasil S.A. – Banco Múltiplo", "Itaú Unibanco Holding S.A", "Itaú Unibanco S.A", "JPMorgan Chase Bank, National Association", "Kirton Bank S.A. – Banco Múltiplo", "Lastro RDV Distribuidora de Títulos e Valores Mobiliários Ltda", "Lecca Crédito, Financiamento e Investimento S/A", "Levycam Ccv Ltda", "Magliano S.A", "Mercado Pago – Conta Do Mercado Livre", "MS Bank S.A. Banco de Câmbio", "Multimoney Cc Ltda", "Natixis Brasil S.A. Banco Múltiplo", "Nova Futura Corretora de Títulos e Valores Mobiliários Ltda", "Novo Banco Continental S.A. – Banco Múltiplo", "Nu Pagamentos S.A (Nubank)", "Omni Banco S.A", "Omni Banco S.A", "Pagseguro Internet S.A", "Paraná Banco S.A", "Parati – Crédito Financiamento e Investimento S.A", "Parmetal Distribuidora de Títulos e Valores Mobiliários Ltda", "Pernambucanas Financ S.A", "Planner Corretora De Valores S.A", "Plural S.A. – Banco Múltiplo", "Pólocred Scmepp Ltda", "Portocred S.A", "Rb Capital Investimentos Dtvm Ltda", "Renascenca Dtvm Ltda", "Sagitur Corretora de Câmbio Ltda", "Scotiabank Brasil S.A. Banco Múltiplo", "Senff S.A. – Crédito, Financiamento e Investimento", "Senso Ccvm S.A", "Servicoop", "Socred S.A", "Sorocred Crédito, Financiamento e Investimento S.A", "Standard Chartered Bank (Brasil) S/A–Bco Invest", "Stone Pagamentos S.A", "Super Pagamentos e Administração de Meios Eletrônicos S.A", "Travelex Banco de Câmbio S.A", "Treviso Corretora de Câmbio S.A", "Tullett Prebon Brasil Cvc Ltda", "UBS Brasil Banco de Investimento S.A", "Unicred Central do Rio Grande do Sul", "Unicred Central Rs", "Unicred Cooperativa", "UNIPRIME Central – Central Interestadual de Cooperativas de Crédito Ltda", "Uniprime Norte do Paraná – Coop de Economia eCrédito Mútuo dos Médicos, Profissionais das Ciências", "Vips Cc Ltda", "Vortx Distribuidora de Títulos e Valores Mobiliários Ltda", "Xp Investimentos S.A" }));
        AutoCompleteDecorator.decorate(textBanco);

        jLabel27.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel27.setText("TIPO DE CHAVE");

        tipoChave.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tipoChave.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "CPF/CNPJ", "E-MAIL", "TELEFONE", "CHAVE ALEATORIA" }));
        tipoChave.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                tipoChavePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        textPix.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        textPix.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textPixKeyReleased(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados de créditos e taxas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel28.setText("LIMITE DE CRÉDITO R$");

        jLabel29.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel29.setText("DESCONTO PADRÃO %");

        DescontoPadrao.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        LimiteCredito.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28)
                    .addComponent(DescontoPadrao, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LimiteCredito))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addGap(15, 15, 15)
                .addComponent(LimiteCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel29)
                .addGap(15, 15, 15)
                .addComponent(DescontoPadrao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 541, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textBanco, 0, 1, Short.MAX_VALUE)
                            .addComponent(tipoChave, javax.swing.GroupLayout.Alignment.LEADING, 0, 289, Short.MAX_VALUE)
                            .addComponent(textPix, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(383, 383, 383))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoChave, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(textPix, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(textBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dados de pagamentos", jPanel6);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Painel1ImageCliente.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel1ImageClienteLayout = new javax.swing.GroupLayout(Painel1ImageCliente);
        Painel1ImageCliente.setLayout(Painel1ImageClienteLayout);
        Painel1ImageClienteLayout.setHorizontalGroup(
            Painel1ImageClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        Painel1ImageClienteLayout.setVerticalGroup(
            Painel1ImageClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );

        CancelimgCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        CancelimgCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelimgCliente1ActionPerformed(evt);
            }
        });

        uploadimgCliente1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        uploadimgCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/upload-de-arquivo.png"))); // NOI18N
        uploadimgCliente1.setText("Upload");
        uploadimgCliente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadimgCliente1ActionPerformed(evt);
            }
        });

        CancelimgCliente2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        CancelimgCliente2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelimgCliente2ActionPerformed(evt);
            }
        });

        Painel2ImageCliente.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel2ImageClienteLayout = new javax.swing.GroupLayout(Painel2ImageCliente);
        Painel2ImageCliente.setLayout(Painel2ImageClienteLayout);
        Painel2ImageClienteLayout.setHorizontalGroup(
            Painel2ImageClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        Painel2ImageClienteLayout.setVerticalGroup(
            Painel2ImageClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage2Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );

        CancelimgCliente3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/excluirUser.png"))); // NOI18N
        CancelimgCliente3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelimgCliente3ActionPerformed(evt);
            }
        });

        Painel3ImageCliente.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout Painel3ImageClienteLayout = new javax.swing.GroupLayout(Painel3ImageCliente);
        Painel3ImageCliente.setLayout(Painel3ImageClienteLayout);
        Painel3ImageClienteLayout.setHorizontalGroup(
            Painel3ImageClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        Painel3ImageClienteLayout.setVerticalGroup(
            Painel3ImageClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage3Cliente, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        );

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

        AlterImage3.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        AlterImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        AlterImage3.setText("Salvar edição do anexo 3");
        AlterImage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlterImage3ActionPerformed(evt);
            }
        });

        habilitar1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        habilitar1.setText("Habilitar edição do anexo 1");
        habilitar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                habilitar1ActionPerformed(evt);
            }
        });

        habilitar2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        habilitar2.setText("Habilitar edição do anexo 2");
        habilitar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                habilitar2ActionPerformed(evt);
            }
        });

        habilitar3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        habilitar3.setText("Habilitar edição do anexo 3");
        habilitar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                habilitar3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AlterImage3)
                            .addComponent(habilitar3))
                        .addGap(356, 356, 356)
                        .addComponent(CancelimgCliente3))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Painel3ImageCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AlterImage1)
                                    .addComponent(habilitar1))
                                .addGap(356, 356, 356)
                                .addComponent(CancelimgCliente1))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(uploadimgCliente1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Painel1ImageCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AlterImage2)
                                    .addComponent(habilitar2))
                                .addGap(356, 356, 356)
                                .addComponent(CancelimgCliente2))
                            .addComponent(Painel2ImageCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(298, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(uploadimgCliente1)
                .addGap(18, 18, 18)
                .addComponent(Painel1ImageCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(habilitar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AlterImage1))
                    .addComponent(CancelimgCliente1))
                .addGap(18, 18, 18)
                .addComponent(Painel2ImageCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(habilitar2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AlterImage2))
                    .addComponent(CancelimgCliente2))
                .addGap(18, 18, 18)
                .addComponent(Painel3ImageCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(habilitar3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AlterImage3))
                    .addComponent(CancelimgCliente3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel2);

        jTabbedPane1.addTab("Anexar fotos de documentos", jScrollPane3);

        btCad.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionarUser.png"))); // NOI18N
        btCad.setText("Salvar cadastro");
        btCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadActionPerformed(evt);
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

        btSearch.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pesquisarUser.png"))); // NOI18N
        btSearch.setText("Procurar");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btSearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExcluir)
                        .addGap(15, 15, 15)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCad))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 785, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btSearch)
                        .addComponent(btSearch1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btCad)
                        .addComponent(btEdit)
                        .addComponent(btExcluir)))
                .addContainerGap(14, Short.MAX_VALUE))
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

    private void btCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadActionPerformed
        adicionar();
    }//GEN-LAST:event_btCadActionPerformed

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        SearchCliente shc = new SearchCliente();
        shc.setVisible(true);
        btSearch.setEnabled(false);
    }//GEN-LAST:event_btSearchActionPerformed

    private void btSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch1ActionPerformed
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_btSearch1ActionPerformed

    private void TextBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBairroActionPerformed

    private void TextEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextEmailKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextEmailKeyPressed

    private void TextEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextEmailKeyReleased
        // TODO add your handling code here:
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }

    }//GEN-LAST:event_TextEmailKeyReleased

    private void TextCOMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCOMPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextCOMPActionPerformed

    private void TextNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNumberActionPerformed

    private void CheckPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckPFActionPerformed

        if (CheckPF.isSelected()) {

            CheckPJ.setEnabled(false);
            TextRazao.setText(null);
            TextFantasia.setText(null);
            TextRazao.setEnabled(false);
            TextFantasia.setEnabled(false);
            TextCPF.setDocument(new LimitaNroCaracteres(14));

        } else {
            CheckPJ.setEnabled(true);
            TextRazao.setEnabled(true);
            TextFantasia.setEnabled(true);
            TextCPF.setDocument(new LimitaNroCaracteres(25));
        }

    }//GEN-LAST:event_CheckPFActionPerformed

    private void CheckPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckPJActionPerformed

        if (CheckPJ.isSelected()) {
            CheckPF.setEnabled(false);
            searchCNPJ.setEnabled(true);
            TextCPF.setDocument(new LimitaNroCaracteres(25));

        } else {
            CheckPF.setEnabled(true);
            searchCNPJ.setEnabled(false);
            TextCPF.setDocument(new LimitaNroCaracteres(25));
        }

    }//GEN-LAST:event_CheckPJActionPerformed

    private void TextCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCPFKeyPressed
        // TODO add your handling code here:
        if (CheckPJ.isSelected()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                getCnpj();
            }
        }
    }//GEN-LAST:event_TextCPFKeyPressed

    private void TextCPFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCPFKeyReleased
        // TODO add your handling code here:
        TextCPF.setText(JMascara.GetJmascaraCpfCnpj(TextCPF.getText()));
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextCPFKeyReleased

    private void TextFONEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFONEKeyReleased
        // TODO add your handling code here:
        TextFONE.setText(JMascara.GetJmascaraFone(TextFONE.getText()));
    }//GEN-LAST:event_TextFONEKeyReleased

    private void TextCEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCEPKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            puxarcep();
        }
    }//GEN-LAST:event_TextCEPKeyPressed

    private void TextCEPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCEPKeyReleased
        // TODO add your handling code here:
        TextCEP.setText(JMascara.GetJmascaraCep(TextCEP.getText()));
        if (TextCEP.getText().length() > 9) {
            puxarcepNoError();
        }
    }//GEN-LAST:event_TextCEPKeyReleased

    private void searchCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchCNPJActionPerformed
        // TODO add your handling code here:
        getCnpj();
    }//GEN-LAST:event_searchCNPJActionPerformed

    private void btWPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWPPActionPerformed
        // TODO add your handling code here:
        String getNumber = TextFONE.getText().replaceAll(" ", "").replace("(", "").replace(")", "").replaceAll("-", "");
        String arqConf = "C://convtec/confwhats.con";
        String conteudo = ConfigWhats.Read(arqConf);
        String c1 = conteudo.split(";")[0];
        String c2 = conteudo.split(";")[1];
        try {
            String artLast = "C://convtec/lastNumber.con";
            ConfigWhats.Write(artLast, TextFONE.getText());
            java.awt.Desktop.getDesktop().browse(new java.net.URI("https://" + c2 + ".whatsapp.com/send?phone=+55" + getNumber + "&text=" + c1.replaceAll(" ", "%20") + ""));
        } catch (Exception erro) {
            System.out.println(erro);
        }
    }//GEN-LAST:event_btWPPActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btExcluirActionPerformed

    private void TextENDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TextENDMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_TextENDMouseClicked

    private void TextENDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextENDKeyReleased
        // TODO add your handling code here:
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextENDKeyReleased

    private void TextNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNomeKeyReleased
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextNomeKeyReleased

    private void TextBairroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBairroKeyReleased
        // TODO add your handling code here:
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextBairroKeyReleased

    private void TextCOMPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCOMPKeyReleased
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextCOMPKeyReleased

    private void TextNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextNumberKeyReleased
        // TODO add your handling code here:
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextNumberKeyReleased

    private void CheckPFStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CheckPFStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_CheckPFStateChanged

    private void TextENDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TextENDFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_TextENDFocusGained

    private void cboEtiquetasPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboEtiquetasPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        String sql = "select etiqueta,corhex from etiquetas where etiqueta = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cboEtiquetas.getSelectedItem().toString());
            rs = pst.executeQuery();

            if (cboEtiquetas.getSelectedItem().equals("S/E")) {
                showColor.setBackground(Color.GRAY);
            } else {

                while (rs.next()) {
                    String cor = rs.getString(2);
                    corHex.setText(cor);
                    showColor.setBackground(Color.decode(cor));

                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }//GEN-LAST:event_cboEtiquetasPopupMenuWillBecomeInvisible

    private void CancelimgCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelimgCliente1ActionPerformed
        // TODO add your handling code here:
        if (viewImage1Cliente.getIcon() != null) {
            viewImage1Cliente.setIcon(null);
            imagemA = null;
        } else {

            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");

        }
    }//GEN-LAST:event_CancelimgCliente1ActionPerformed

    private void uploadimgCliente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadimgCliente1ActionPerformed
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
    }//GEN-LAST:event_uploadimgCliente1ActionPerformed

    private void CancelimgCliente2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelimgCliente2ActionPerformed
        // TODO add your handling code here:
        if (viewImage2Cliente.getIcon() != null) {
            viewImage2Cliente.setIcon(null);
            imagemB = null;
        } else {

            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");

        }
    }//GEN-LAST:event_CancelimgCliente2ActionPerformed

    private void CancelimgCliente3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelimgCliente3ActionPerformed
        // TODO add your handling code here:
        if (viewImage3Cliente.getIcon() != null) {
            viewImage3Cliente.setIcon(null);
            imagemC = null;
        } else {

            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");

        }
    }//GEN-LAST:event_CancelimgCliente3ActionPerformed

    private void tipoChavePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_tipoChavePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (tipoChave.getSelectedItem().equals("CPF/CNPJ")) {
            textPix.setEnabled(true);
            textBanco.setEnabled(true);
            textPix.setFormatterFactory(new DefaultFormatterFactory(null));
            textPix.setText("");
        }
        if (tipoChave.getSelectedItem().equals("TELEFONE")) {
            textPix.setEnabled(true);
            textBanco.setEnabled(true);
            textPix.setFormatterFactory(new DefaultFormatterFactory(null));
            textPix.setText("");

        }
        if (tipoChave.getSelectedItem().equals("--")) {
            textPix.setFormatterFactory(new DefaultFormatterFactory(null));
            textPix.setText("");
            textPix.setEnabled(false);
            textBanco.setSelectedItem("--");
            textBanco.setEnabled(false);

        } else {
            textPix.setEnabled(true);
            textBanco.setEnabled(true);
            textPix.setFormatterFactory(new DefaultFormatterFactory(null));
            textPix.setText("");
            textPix.setText(JMascara.GetJmascaraLimpar(textPix.getText()));
        }

    }//GEN-LAST:event_tipoChavePopupMenuWillBecomeInvisible

    private void textPixKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textPixKeyReleased
        // TODO add your handling code here:
        if (tipoChave.getSelectedItem().equals("CPF/CNPJ")) {
            textPix.setText(JMascara.GetJmascaraCpfCnpj(textPix.getText()));
        }
        if (tipoChave.getSelectedItem().equals("TELEFONE")) {
            textPix.setText(JMascara.GetJmascaraFone(textPix.getText()));
        }
    }//GEN-LAST:event_textPixKeyReleased

    private void AlterImage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage1ActionPerformed
        // TODO add your handling code here:
        if (TextID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado!");
        } else {
            String sql = "update clientes set anexo1=? where idclientes=?";
            try {
                pst = conexao.prepareStatement(sql);

                pst.setBytes(1, getImagem1());
                pst.setString(2, TextID.getText());

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Anexo 1 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
                    AlterImage1.setVisible(false);
                    habilitar1.setSelected(false);
                    uploadimgCliente1.setEnabled(false);
                    CancelimgCliente1.setEnabled(false);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 1!!");
            }
        }

    }//GEN-LAST:event_AlterImage1ActionPerformed

    private void AlterImage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage2ActionPerformed
        // TODO add your handling code here:
        if (TextID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado!");
        } else {
            String sql = "update clientes set anexo2=? where idclientes=?";
            try {
                pst = conexao.prepareStatement(sql);

                pst.setBytes(1, getImagem2());
                pst.setString(2, TextID.getText());

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Anexo 2 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
                    AlterImage2.setVisible(false);
                    habilitar2.setSelected(false);
                    CancelimgCliente2.setEnabled(false);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 2!!");
            }
        }
    }//GEN-LAST:event_AlterImage2ActionPerformed

    private void AlterImage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlterImage3ActionPerformed
        // TODO add your handling code here:
        if (TextID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado!");
        } else {
            String sql = "update clientes set anexo3=? where idclientes=?";
            try {
                pst = conexao.prepareStatement(sql);

                pst.setBytes(1, getImagem3());
                pst.setString(2, TextID.getText());

                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Anexo 3 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
                    AlterImage3.setVisible(false);
                    habilitar3.setSelected(false);
                    CancelimgCliente3.setEnabled(false);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 3!!");
            }
        }
    }//GEN-LAST:event_AlterImage3ActionPerformed

    private void habilitar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_habilitar1ActionPerformed
        // TODO add your handling code here:
        if (habilitar1.isSelected()) {
            AlterImage1.setVisible(true);
            uploadimgCliente1.setEnabled(true);
            CancelimgCliente1.setEnabled(true);
        } else {
            AlterImage1.setVisible(false);
            uploadimgCliente1.setEnabled(false);
            CancelimgCliente1.setEnabled(false);
        }
    }//GEN-LAST:event_habilitar1ActionPerformed

    private void habilitar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_habilitar2ActionPerformed
        // TODO add your handling code here:
        if (habilitar2.isSelected()) {
            AlterImage2.setVisible(true);
            CancelimgCliente2.setEnabled(true);
        } else {
            AlterImage2.setVisible(false);
            CancelimgCliente2.setEnabled(false);
        }
    }//GEN-LAST:event_habilitar2ActionPerformed

    private void habilitar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_habilitar3ActionPerformed
        if (habilitar3.isSelected()) {
            AlterImage3.setVisible(true);
            CancelimgCliente3.setEnabled(true);
        } else {
            AlterImage3.setVisible(false);
            CancelimgCliente3.setEnabled(false);
        }
    }//GEN-LAST:event_habilitar3ActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        if (TextID.getText() == null) {

        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração dos dados do cliente detalhado em pdf?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {
                    String id = TextID.getText();
                    String sql;
                    sql = "select * from clientes where idclientes = ?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, TextID.getText());
                    rs = pst.executeQuery();
                    InputStream report = getClass().getResourceAsStream("/recibos/Dados_clientes.jasper");

                    String os = System.getProperty("os.name");
                    if (os.startsWith("Windows")) {

                        JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/Recibo#CLT" + id + ".pdf");
                        File file = new File("C://convtec/Supply-x/trash/Recibo#CLT" + id + ".pdf");

                        try {
                            Desktop.getDesktop().open(file);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                    }

                } catch (HeadlessException | SQLException | JRException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }//GEN-LAST:event_btImprimirActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        SearchCliente shc = new SearchCliente();
        shc.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        // TODO add your handling code here:

    }//GEN-LAST:event_formWindowLostFocus

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
            java.util.logging.Logger.getLogger(TelaCadCli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadCli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadCli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadCli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadCli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton AlterImage1;
    public static javax.swing.JButton AlterImage2;
    public static javax.swing.JButton AlterImage3;
    public static javax.swing.JComboBox<String> CBOSexo;
    public static javax.swing.JComboBox<String> CBOuf;
    public static javax.swing.JButton CancelimgCliente1;
    public static javax.swing.JButton CancelimgCliente2;
    public static javax.swing.JButton CancelimgCliente3;
    public static javax.swing.JCheckBox CheckPF;
    public static javax.swing.JCheckBox CheckPJ;
    public static javax.swing.JTextField DescontoPadrao;
    public static javax.swing.JComboBox<String> Inativo;
    public static javax.swing.JFormattedTextField LimiteCredito;
    public static javax.swing.JPanel Painel1ImageCliente;
    public static javax.swing.JPanel Painel2ImageCliente;
    public static javax.swing.JPanel Painel3ImageCliente;
    public static javax.swing.JTextField TextBairro;
    public static javax.swing.JFormattedTextField TextCEP;
    public static javax.swing.JTextField TextCOMP;
    public static javax.swing.JFormattedTextField TextCPF;
    public static javax.swing.JTextField TextCity;
    public static javax.swing.JTextField TextEND;
    public static javax.swing.JTextField TextEmail;
    public static javax.swing.JFormattedTextField TextFONE;
    public static javax.swing.JTextField TextFantasia;
    public static javax.swing.JTextField TextID;
    public static javax.swing.JFormattedTextField TextNASC;
    public static javax.swing.JTextField TextNome;
    public static javax.swing.JTextField TextNumber;
    public static javax.swing.JTextField TextRazao;
    public static javax.swing.JButton btCad;
    public static javax.swing.JButton btEdit;
    public static javax.swing.JButton btExcluir;
    public static javax.swing.JButton btImprimir;
    public static javax.swing.JButton btSearch;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JButton btWPP;
    public static javax.swing.JComboBox<String> cboEtiquetas;
    public static javax.swing.JTextField corHex;
    public static javax.swing.JCheckBox habilitar1;
    public static javax.swing.JCheckBox habilitar2;
    public static javax.swing.JCheckBox habilitar3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JButton searchCNPJ;
    public static javax.swing.JPanel showColor;
    public static javax.swing.JComboBox<String> textBanco;
    public static javax.swing.JFormattedTextField textPix;
    public static javax.swing.JComboBox<String> tipoChave;
    public static javax.swing.JTextField txtDataC;
    public static javax.swing.JTextArea txtInfo;
    public static javax.swing.JTextArea txtObs;
    public static javax.swing.JButton uploadimgCliente1;
    public static javax.swing.JLabel viewImage1Cliente;
    public static javax.swing.JLabel viewImage2Cliente;
    public static javax.swing.JLabel viewImage3Cliente;
    // End of variables declaration//GEN-END:variables
}
