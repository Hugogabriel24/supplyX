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
import java.awt.Color;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import pages.TelaPrincipal;
import static pages.TelaPrincipal.empresa;
import pagesSearchs.SearchEntregador;

public class TelaCadEntregador extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadEntregador() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        SetDate();
        uper();
        ListarDadosEtiquetas();
        Formatter();
        textPix.setEnabled(false);
        textBanco.setEnabled(false);
        btWPP.setVisible(false);
        btImprimir.setVisible(false);
        showColor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

    }

    public void soundClickRemove() {
        try {

            URL sd = getClass().getResource("/sounds/trash.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sd));
            c.start();

        } catch (Exception e) {

        }
    }

    public void uper() {
        TextNome.setDocument(new UpperCase());
        TextNumber.setDocument(new UpperCase());
        TextOrgao.setDocument(new UpperCase());
        TextFantasia.setDocument(new UpperCase());
        TextRazao.setDocument(new UpperCase());
        TextEmail.setDocument(new UpperCase());
        TextEND.setDocument(new UpperCase());
        TextCity.setDocument(new UpperCase());
        TextBairro.setDocument(new UpperCase());
        TextCOMP.setDocument(new UpperCase());
        txtInfo.setDocument(new UpperCase());
        txtObs.setDocument(new UpperCase());
        marcaviculo.setDocument(new UpperCase());
        modeloVeiculo.setDocument(new UpperCase());
        placaVeiculo.setDocument(new UpperCase());
        textPix.setDocument(new UpperCase());
    }

    public void Formatter() {
        try {
            MaskFormatter form = new MaskFormatter("UUU-####");
            placaVeiculo.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }
        try {
            MaskFormatter form = new MaskFormatter("#.###.###");
            TextRG.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }

        try {
            MaskFormatter form = new MaskFormatter("##/##/####");
            TextNASC.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }
    }

    public void addLogCad() {

        String sql = "insert into logentre(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Add");
            pst.setString(2, "Entregador  " + cliente + " adicionado por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void addLogEdit() {

        String sql = "insert into logentre(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Edit");
            pst.setString(2, "Entregador  " + cliente + " editado por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void addLogRemove() {

        String sql = "insert into logentre(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Remove");
            pst.setString(2, "Entregador  " + cliente + " removido por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (Exception e) {
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

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

    public void adicionar() {

        String sql = "insert into entregadores(pessoa,nome,cpf,razaosoc,fantasia,rg,orgaoexp,sexo,datanasc,tel,email,cep,endereco,cidade,bairro,numero,complemento,uf,inativo,info,obs,datacad,tipochave,pix,banco,etiqueta,tipoveiculo,marcaveiculo,modeloveiculo,anoveiculo,corveiculo,placaveiculo,finalveiculo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//7
        try {
            String cpf = TextCPF.getText();
            CPF pf = new CPF(cpf);
            CNPJ pj = new CNPJ(cpf);
            validarEmail vle = new validarEmail();

            pst = conexao.prepareStatement(sql);

            if (CheckPF.isSelected()) {
                pst.setString(1, "PF");
                pst.setString(2, TextNome.getText());
                pst.setString(3, TextCPF.getText());
                pst.setString(4, TextRazao.getText());
                pst.setString(5, TextFantasia.getText());
                pst.setString(6, TextRG.getText());
                pst.setString(7, TextOrgao.getText());
                pst.setString(8, CBOSexo.getSelectedItem().toString());
                pst.setString(9, TextNASC.getText());
                pst.setString(10, TextFONE.getText());
                pst.setString(11, TextEmail.getText());
                pst.setString(12, TextCEP.getText());
                pst.setString(13, TextEND.getText());
                pst.setString(14, TextCity.getText());
                pst.setString(15, TextBairro.getText());
                pst.setString(16, TextNumber.getText());
                pst.setString(17, TextCOMP.getText());
                pst.setString(18, CBOuf.getSelectedItem().toString());
                pst.setString(19, Inativo.getSelectedItem().toString());
                pst.setString(20, txtInfo.getText());
                pst.setString(21, txtObs.getText());
                pst.setString(22, txtDataC.getText());
                pst.setString(23, tipoChave.getSelectedItem().toString());
                pst.setString(24, textPix.getText());
                pst.setString(25, textBanco.getSelectedItem().toString());
                pst.setString(26, cboEtiquetas.getSelectedItem().toString());
                pst.setString(27, tipoveiculo.getSelectedItem().toString());
                pst.setString(28, marcaviculo.getText());
                pst.setString(29, modeloVeiculo.getText());
                pst.setString(30, anoVeiculo.getSelectedItem().toString());
                pst.setString(31, corVeiculo.getSelectedItem().toString());
                pst.setString(32, placaVeiculo.getText());
                pst.setString(33, finalPlaca.getText());

            } else {
                pst.setString(1, "PJ");
                pst.setString(2, TextNome.getText());
                pst.setString(3, TextCPF.getText());
                pst.setString(4, TextRazao.getText());
                pst.setString(5, TextFantasia.getText());
                pst.setString(6, TextRG.getText());
                pst.setString(7, TextOrgao.getText());
                pst.setString(8, CBOSexo.getSelectedItem().toString());
                pst.setString(9, TextNASC.getText());
                pst.setString(10, TextFONE.getText());
                pst.setString(11, TextEmail.getText());
                pst.setString(12, TextCEP.getText());
                pst.setString(13, TextEND.getText());
                pst.setString(14, TextCity.getText());
                pst.setString(15, TextBairro.getText());
                pst.setString(16, TextNumber.getText());
                pst.setString(17, TextCOMP.getText());
                pst.setString(18, CBOuf.getSelectedItem().toString());
                pst.setString(19, Inativo.getSelectedItem().toString());
                pst.setString(20, txtInfo.getText());
                pst.setString(21, txtObs.getText());
                pst.setString(22, txtDataC.getText());
                pst.setString(23, tipoChave.getSelectedItem().toString());
                pst.setString(24, textPix.getText());
                pst.setString(25, textBanco.getSelectedItem().toString());
                pst.setString(26, cboEtiquetas.getSelectedItem().toString());
                pst.setString(27, tipoveiculo.getSelectedItem().toString());
                pst.setString(28, marcaviculo.getText());
                pst.setString(29, modeloVeiculo.getText());
                pst.setString(30, anoVeiculo.getSelectedItem().toString());
                pst.setString(31, corVeiculo.getSelectedItem().toString());
                pst.setString(32, placaVeiculo.getText());
                pst.setString(33, finalPlaca.getText());
            }

            if (CheckPF.isSelected()) {

                if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    if (vle.ValidarEmail(TextEmail.getText())) {
                        if (pf.isCPF()) {
                            int adicionado = pst.executeUpdate();
                            if (adicionado > 0) {
                                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                                JOptionPane.showMessageDialog(null, "Entregador adicionado com sucesso!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                                addLogCad();
                                refresh();
                            }

                        } else {
                            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                            JOptionPane.showMessageDialog(null, "<html><b>CPF inválido!</html>", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
                        }
                    } else {
                        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                        JOptionPane.showMessageDialog(null, "<html><b>Email inválido!</html>", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
                    }
                }
            }
            if (CheckPJ.isSelected()) {

                if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    if (vle.ValidarEmail(TextEmail.getText())) {
                        if (pj.isCNPJ()) {
                            int adicionado = pst.executeUpdate();
                            if (adicionado > 0) {
                                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                                JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!", "MENSAGEM", JOptionPane.INFORMATION_MESSAGE, icon);
                                addLogCad();
                                refresh();

                            }

                        } else {
                            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                            JOptionPane.showMessageDialog(null, "<html><b>CNPJ inválido!</html>", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
                        }
                    } else {
                        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                        JOptionPane.showMessageDialog(null, "<html><b>Email inválido!</html>", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
                    }
                }
            }

        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Error ao cadastrar o fornecedor.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {
        String sql = "update entregadores set pessoa=?,nome=?,cpf=?,razaosoc=?,fantasia=?,rg=?,orgaoexp=?,sexo=?,datanasc=?,tel=?,email=?,cep=?,endereco=?,cidade=?,bairro=?,numero=?,complemento=?,uf=?,inativo=?,info=?,obs=?,tipochave=?,pix=?,banco=?,etiqueta=?,tipoveiculo=?,marcaveiculo=?,modeloveiculo=?,anoveiculo=?,corveiculo=?,placaveiculo=?,finalveiculo=? where identregadores=?";
        try {
            pst = conexao.prepareStatement(sql);
            if (CheckPF.isSelected()) {
                pst.setString(1, "PF");
                pst.setString(2, TextNome.getText());
                pst.setString(3, TextCPF.getText());
                pst.setString(4, TextRazao.getText());
                pst.setString(5, TextFantasia.getText());
                pst.setString(6, TextRG.getText());
                pst.setString(7, TextOrgao.getText());
                pst.setString(8, CBOSexo.getSelectedItem().toString());
                pst.setString(9, TextNASC.getText());
                pst.setString(10, TextFONE.getText());
                pst.setString(11, TextEmail.getText());
                pst.setString(12, TextCEP.getText());
                pst.setString(13, TextEND.getText());
                pst.setString(14, TextCity.getText());
                pst.setString(15, TextBairro.getText());
                pst.setString(16, TextNumber.getText());
                pst.setString(17, TextCOMP.getText());
                pst.setString(18, CBOuf.getSelectedItem().toString());
                pst.setString(19, Inativo.getSelectedItem().toString());
                pst.setString(20, txtInfo.getText());
                pst.setString(21, txtObs.getText());
                pst.setString(22, tipoChave.getSelectedItem().toString());
                pst.setString(23, textPix.getText());
                pst.setString(24, textBanco.getSelectedItem().toString());
                pst.setString(25, cboEtiquetas.getSelectedItem().toString());
                pst.setString(26, tipoveiculo.getSelectedItem().toString());
                pst.setString(27, marcaviculo.getText());
                pst.setString(28, modeloVeiculo.getText());
                pst.setString(29, anoVeiculo.getSelectedItem().toString());
                pst.setString(30, corVeiculo.getSelectedItem().toString());
                pst.setString(31, placaVeiculo.getText());
                pst.setString(32, finalPlaca.getText());
                pst.setString(33, TextID.getText());

            } else {
                pst.setString(1, "PJ");
                pst.setString(2, TextNome.getText());
                pst.setString(3, TextCPF.getText());
                pst.setString(4, TextRazao.getText());
                pst.setString(5, TextFantasia.getText());
                pst.setString(6, TextRG.getText());
                pst.setString(7, TextOrgao.getText());
                pst.setString(8, CBOSexo.getSelectedItem().toString());
                pst.setString(9, TextNASC.getText());
                pst.setString(10, TextFONE.getText());
                pst.setString(11, TextEmail.getText());
                pst.setString(12, TextCEP.getText());
                pst.setString(13, TextEND.getText());
                pst.setString(14, TextCity.getText());
                pst.setString(15, TextBairro.getText());
                pst.setString(16, TextNumber.getText());
                pst.setString(17, TextCOMP.getText());
                pst.setString(18, CBOuf.getSelectedItem().toString());
                pst.setString(19, Inativo.getSelectedItem().toString());
                pst.setString(20, txtInfo.getText());
                pst.setString(21, txtObs.getText());
                pst.setString(22, tipoChave.getSelectedItem().toString());
                pst.setString(23, textPix.getText());
                pst.setString(24, textBanco.getSelectedItem().toString());
                pst.setString(25, cboEtiquetas.getSelectedItem().toString());
                pst.setString(26, tipoveiculo.getSelectedItem().toString());
                pst.setString(27, marcaviculo.getText());
                pst.setString(28, modeloVeiculo.getText());
                pst.setString(29, anoVeiculo.getSelectedItem().toString());
                pst.setString(30, corVeiculo.getSelectedItem().toString());
                pst.setString(31, placaVeiculo.getText());
                pst.setString(32, finalPlaca.getText());
                pst.setString(33, TextID.getText());

            }

            if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Dados do entregador alterado com sucesso!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                    addLogEdit();
                    refresh();

                }
            }
        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Dados do entregador não alterados.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este entregador?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from entregadores where identregadores=?";
            try {

                if ((TextID.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhum entregador selecionado!!");
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, TextID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Entregador removido com sucesso!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                    addLogRemove();
                    soundClickRemove();
                    refresh();

                }

            } catch (Exception e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Dados do entregador não alterados.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    public void refresh() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar os campos?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            try {

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
                TextOrgao.setText(null);
                TextRG.setText(null);
                txtInfo.setText(null);
                txtObs.setText(null);
                btCad.setEnabled(true);
                textPix.setText(null);
                btWPP.setVisible(false);
                Color colorB = new Color(255, 255, 255);
                btCad.setBackground(colorB);
                btEdit.setEnabled(false);
                btExcluir.setEnabled(false);
                SetDate();

            } catch (Exception e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro na limpeza dos campos.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
            }
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void getCnpj() {
        if (TextCPF.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo do CNPJ vazio!");
        } else {
            try {
                String Getcnpj = TextCPF.getText();
                String CnpjFormated = Getcnpj.replaceAll("[^\\d]", "");
                //44216176000150
                String url = "https://www.receitaws.com.br/v1/cnpj/" + CnpjFormated + "";

                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() != 200) {
                    System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
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

                TextNome.setText(dados.getNome().replaceAll("[0-9]", ""));
                TextFONE.setText(dados.getTelefone());
                TextEmail.setText(dados.getEmail());
                TextFantasia.setText(dados.getFantasia());
                TextCEP.setText(dados.getCep());
                TextRazao.setText(dados.getNome());

            } catch (IOException ex) {

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
        jLabel12 = new javax.swing.JLabel();
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
        jLabel21 = new javax.swing.JLabel();
        TextCPF = new javax.swing.JFormattedTextField();
        TextRG = new javax.swing.JFormattedTextField();
        TextNASC = new javax.swing.JFormattedTextField();
        TextFONE = new javax.swing.JFormattedTextField();
        TextCEP = new javax.swing.JFormattedTextField();
        TextOrgao = new javax.swing.JTextField();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        tipoveiculo = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        marcaviculo = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        modeloVeiculo = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        corVeiculo = new javax.swing.JComboBox<>();
        anoVeiculo = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        finalPlaca = new javax.swing.JTextField();
        placaVeiculo = new javax.swing.JFormattedTextField();
        jLabel37 = new javax.swing.JLabel();
        placaVeiculoMercoSul = new javax.swing.JFormattedTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        textBanco = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        tipoChave = new javax.swing.JComboBox<>();
        textPix = new javax.swing.JFormattedTextField();
        btCad = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
        btSearch1 = new javax.swing.JButton();

        TextID.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TextID.setEnabled(false);

        corHex.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar entregador");
        setResizable(false);
        setSize(new java.awt.Dimension(925, 650));

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
        jLabel2.setText("CEP ");

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

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("RG");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("TELEFONE *");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("DATA NASCIMENTO *");

        CBOSexo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
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
        jLabel20.setText("RAZÃO SOCIAL");

        TextFantasia.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel21.setText("ORGÃO EXPEDITOR");

        TextCPF.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextCPFKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextCPFKeyReleased(evt);
            }
        });

        TextRG.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextRG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextRGKeyReleased(evt);
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

        TextOrgao.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(TextCity, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TextEND, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CBOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btWPP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel10))
                                .addGap(300, 300, 300)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TextNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addComponent(TextCOMP)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel21)
                                .addGap(24, 24, 24)
                                .addComponent(TextOrgao, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextNASC, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(TextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12))
                                        .addGap(61, 61, 61)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(TextCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(searchCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(TextRazao, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(TextRG, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(60, 60, 60)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel19)
                                                    .addComponent(jLabel18)
                                                    .addComponent(jLabel16))
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addGap(22, 22, 22)
                                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(CBOSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(TextFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(TextFONE, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(122, 122, 122)))))))))
                        .addGap(0, 137, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(CheckPF)
                        .addGap(17, 17, 17)
                        .addComponent(CheckPJ)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CheckPF)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CheckPJ)
                        .addComponent(jLabel23)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel1))
                    .addComponent(TextNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TextRazao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(TextRG, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextOrgao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBOSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextNASC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextFONE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextEND, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextCity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCOMP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(TextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(CBOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btWPP, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dados cadastrais", jPanel4);

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("OUTRAS INFORMAÇÕES");

        txtInfo.setColumns(20);
        txtInfo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtInfo.setRows(5);
        jScrollPane1.setViewportView(txtInfo);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("OBSERVAÇÕES DO CLIENTE");

        txtObs.setColumns(20);
        txtObs.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtObs.setRows(5);
        jScrollPane2.setViewportView(txtObs);

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("DATA DE CADASTRO");

        txtDataC.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDataC.setEnabled(false);

        Inativo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboEtiquetas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(showColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(Inativo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Mais informações", jPanel5);

        jLabel30.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel30.setText("TIPO DE VEICULO:");

        tipoveiculo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tipoveiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bicicleta", "Ciclomotor", "Motoneta", "Motocicleta", "Triciclo", "Quadriciclo", "Automóvel", "Microônibus", "Ônibus", "Bonde", "Reboque ou semi-reboque", "Charrete" }));

        jLabel31.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel31.setText("MARCA:");

        marcaviculo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        marcaviculo.setAlignmentY(1.5F);
        marcaviculo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                marcaviculoKeyReleased(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel32.setText("MODELO:");

        modeloVeiculo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        modeloVeiculo.setAlignmentY(1.5F);
        modeloVeiculo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                modeloVeiculoKeyReleased(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel33.setText("ANO:");

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel34.setText("COR:");

        corVeiculo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        corVeiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Abóbora", "Açafrão", "Amarelo", "Âmbar", "Ameixa", "Amêndoa", "Ametista", "Anil", "Azul", "Bege", "Bordô", "Branco", "Bronze", "Cáqui", "Caramelo", "Carmesim", "Carmim", "Castanho", "Cereja", "Chocolate", "Ciano ", "Cinza", "Cinzento", "Cobre", "Coral", "Creme", "Damasco", "Dourado", "Escarlate", "Esmeralda", "Ferrugem", "Fúcsia", "Gelo", "Grená", "Gris", "Índigo", "Jade", "Jambo", "Laranja", "Lavanda", "Lilás ", "Limão", "Loiro", "Magenta", "Malva", "Marfim", "Marrom", "Mostarda", "Negro", "Ocre", "Oliva", "Ouro", "Pêssego", "Prata", "Preto", "Púrpura", "Rosa", "Roxo", "Rubro", "Salmão", "Sépia", "Terracota", "Tijolo", "Turquesa", "Uva", "Verde", "Vermelho", "Vinho", "Violeta" }));

        anoVeiculo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        anoVeiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" }));

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel35.setText("PLACA:");

        jLabel36.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel36.setText("FINAL DA PLACA:");

        finalPlaca.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        finalPlaca.setAlignmentY(1.5F);
        finalPlaca.setEnabled(false);
        finalPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                finalPlacaKeyReleased(evt);
            }
        });

        placaVeiculo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                placaVeiculoKeyReleased(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel37.setText("PLACA MERCOSUL:");

        placaVeiculoMercoSul.setEnabled(false);
        placaVeiculoMercoSul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                placaVeiculoMercoSulKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tipoveiculo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(marcaviculo)
                    .addComponent(modeloVeiculo)
                    .addComponent(corVeiculo, 0, 323, Short.MAX_VALUE)
                    .addComponent(anoVeiculo, 0, 323, Short.MAX_VALUE)
                    .addComponent(finalPlaca)
                    .addComponent(placaVeiculo)
                    .addComponent(placaVeiculoMercoSul))
                .addContainerGap(435, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(tipoveiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(marcaviculo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modeloVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(anoVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placaVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(placaVeiculoMercoSul, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(finalPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(184, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados do veiculo", jPanel2);

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tipoChave, 0, 417, Short.MAX_VALUE)
                            .addComponent(textPix)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(textBanco, 0, 1, Short.MAX_VALUE)))
                .addGap(383, 383, 383))
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
                .addContainerGap(385, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados de pagamentos", jPanel6);

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btSearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExcluir)
                        .addGap(15, 15, 15)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCad)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        SearchEntregador shc = new SearchEntregador();
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
            TextRG.setText(JMascara.GetJmascaraLimpar(TextRG.getText()));
            TextRG.setText(null);
            TextOrgao.setText(null);
            TextRG.setEnabled(false);
            TextOrgao.setEnabled(false);
            searchCNPJ.setEnabled(true);
            TextCPF.setDocument(new LimitaNroCaracteres(25));

        } else {
            CheckPF.setEnabled(true);
            TextRG.setEnabled(true);
            TextOrgao.setEnabled(true);
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

    private void TextRGKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextRGKeyReleased
        // TODO add your handling code here:
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
    }//GEN-LAST:event_TextRGKeyReleased

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
        if ((TextCPF.getText().length() > 1) && (TextEmail.getText().length() > 1) && (TextNome.getText().length() > 1) && (TextFONE.getText().length() > 1) && (TextEND.getText().length() > 1) && (TextCity.getText().length() > 1) && (CheckPF.isSelected() || (CheckPJ.isSelected()))) {
            Color color = new Color(147, 167, 39);
            btCad.setBackground(color);
        } else {
            Color colorB = new Color(255, 255, 255);
            btCad.setBackground(colorB);
        }
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

    private void marcaviculoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_marcaviculoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_marcaviculoKeyReleased

    private void modeloVeiculoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_modeloVeiculoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_modeloVeiculoKeyReleased

    private void finalPlacaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_finalPlacaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_finalPlacaKeyReleased

    private void placaVeiculoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaVeiculoKeyReleased
        // TODO add your handling code here:
        String getFinal = placaVeiculo.getText().substring(4, 8);
        finalPlaca.setText(getFinal);
        String placa = placaVeiculo.getText(); // Obtém o valor do campo de texto
        char[] placaArray = placa.toCharArray(); // Converte a placa em um array de caracteres

        if (placaArray.length >= 6) {
            char sextoCaractere = placaArray[5]; // Obtém o sexto caractere (índice 5)

            // Troca o sexto caractere por uma letra específica
            switch (sextoCaractere) {
                case '0':
                    placaArray[5] = 'A';
                    break;
                case '1':
                    placaArray[5] = 'B';
                    break;
                case '2':
                    placaArray[5] = 'C';
                    break;
                case '3':
                    placaArray[5] = 'D';
                    break;
                case '4':
                    placaArray[5] = 'E';
                    break;
                case '5':
                    placaArray[5] = 'F';
                    break;
                case '6':
                    placaArray[5] = 'G';
                    break;
                case '7':
                    placaArray[5] = 'H';
                    break;
                case '8':
                    placaArray[5] = 'I';
                    break;
                case '9':
                    placaArray[5] = 'J';
                    break;
                // Adicione outros casos conforme necessário
                default:
                    // Mantém o sexto caractere inalterado se não for uma letra
                    break;
            }
        }

        String placaModificada = new String(placaArray); // Converte o array de caracteres de volta para uma string
        placaVeiculoMercoSul.setText(placaModificada); // Define o valor modificado de volta no campo de texto
    }//GEN-LAST:event_placaVeiculoKeyReleased

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
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

    private void placaVeiculoMercoSulKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_placaVeiculoMercoSulKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_placaVeiculoMercoSulKeyReleased

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
            java.util.logging.Logger.getLogger(TelaCadEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadEntregador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadEntregador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> CBOSexo;
    public static javax.swing.JComboBox<String> CBOuf;
    public static javax.swing.JCheckBox CheckPF;
    public static javax.swing.JCheckBox CheckPJ;
    public static javax.swing.JComboBox<String> Inativo;
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
    public static javax.swing.JTextField TextOrgao;
    public static javax.swing.JFormattedTextField TextRG;
    public static javax.swing.JTextField TextRazao;
    public static javax.swing.JComboBox<String> anoVeiculo;
    public static javax.swing.JButton btCad;
    public static javax.swing.JButton btEdit;
    public static javax.swing.JButton btExcluir;
    public static javax.swing.JButton btImprimir;
    public static javax.swing.JButton btSearch;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JButton btWPP;
    public static javax.swing.JComboBox<String> cboEtiquetas;
    public static javax.swing.JTextField corHex;
    public static javax.swing.JComboBox<String> corVeiculo;
    public static javax.swing.JTextField finalPlaca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTextField marcaviculo;
    public static javax.swing.JTextField modeloVeiculo;
    public static javax.swing.JFormattedTextField placaVeiculo;
    public static javax.swing.JFormattedTextField placaVeiculoMercoSul;
    public static javax.swing.JButton searchCNPJ;
    private javax.swing.JPanel showColor;
    public static javax.swing.JComboBox<String> textBanco;
    public static javax.swing.JFormattedTextField textPix;
    public static javax.swing.JComboBox<String> tipoChave;
    public static javax.swing.JComboBox<String> tipoveiculo;
    public static javax.swing.JTextField txtDataC;
    public static javax.swing.JTextArea txtInfo;
    public static javax.swing.JTextArea txtObs;
    // End of variables declaration//GEN-END:variables
}
