/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesCadastro;

import Jm.JMascara;

import Verificadores.CPF;
import br.com.tanamao.dal.LimitaNroCaracteres;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import br.com.tanamao.dal.WebServiceCep;
import Verificadores.validarEmail;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import pages.TelaPrincipal;
import static pages.TelaPrincipal.empresa;
import pagesSearchs.SearchFuncionario;

/**
 *
 * @author hugogabriel
 */
public class TelaCadFuncionario extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCadFuncionario() {
        initComponents();
        conexao = ModuloConexao.conector();
        setIcon();
        SetDate();
        uper();
        ListarCargos();
        SetMatricula();
        formatarpreco();
        TextCPF.setDocument(new LimitaNroCaracteres(14));
        btImprimir.setVisible(false);

        try {
            MaskFormatter form = new MaskFormatter("##/##/####");
            txDataN.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }
        try {
            MaskFormatter form = new MaskFormatter("#.###.###");
            TextRG.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }
        
    }

    public void SetMatricula() {
        String mat = null;
        String sql = "select max(idfunc)idfunc from funcionarios";
        conexao = ModuloConexao.conector();
        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String idUV = rs.getString(1);
                int primarykey = Integer.parseInt(idUV) + 5;
                Date data = new Date();
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
                String date = formatador.format(data);
                mat = "#MAT" + primarykey + "-" + date.replaceAll("0", "").replaceAll("/", "") + "";
                txMat.setText(mat);
            }

        } catch (Exception e) {

        }

    }
    
    public void addLogCad() {

        String sql = "insert into logfunc(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Add");
            pst.setString(2, "Funcionario " + cliente + " adicionado por  " + user + "");
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

        String sql = "insert into logfunc(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Edit");
            pst.setString(2, "Funcionario  " + cliente + " editado por  " + user + "");
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

        String sql = "insert into logfunc(tipolog,message,datalog) values(?,?,?)";
        String cliente = TextNome.getText();
        String user = TelaPrincipal.lblUsuario.getText();
        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, "Remove");
            pst.setString(2, "Funcionario  " + cliente + " removido por  " + user + "");
            pst.setString(3, time);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {

            }

        } catch (Exception e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o log.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void SetDate() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        String date = formatador.format(data);
        txtDatac.setText(null);
        txtDatac.setText(date);
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

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        txremunera.setFormatterFactory(dfFactory);
    }

    public void uper() {
        TextNome.setDocument(new UpperCase());
        TextEmail.setDocument(new UpperCase());
        TextEND.setDocument(new UpperCase());
        TextCity.setDocument(new UpperCase());
        TextBairro.setDocument(new UpperCase());
        TextCOMP.setDocument(new UpperCase());
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
            JOptionPane.showMessageDialog(null, "CEP inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);

        }
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

        String sql = "insert into funcionarios(nome,cpf,rg,sexo,telefone,datan,email,cep,endereco,cidade,bairro,numero,complemento,uf,cargo,datacontrato,datapagamento,remunera,comissao,dataadc,mat) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            String cpf = TextCPF.getText();
            CPF pf = new CPF(cpf);
            validarEmail vle = new validarEmail();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            String dateContrat = formato.format(DateContrato.getDate());
            String datePag = formato.format(DatePgm.getDate());

            pst = conexao.prepareStatement(sql);

            pst.setString(1, TextNome.getText());
            pst.setString(2, TextCPF.getText());
            pst.setString(3, TextRG.getText());
            pst.setString(4, CBOSexo.getSelectedItem().toString());
            pst.setString(5, TextFONE.getText());
            pst.setString(6, txDataN.getText());
            pst.setString(7, TextEmail.getText());
            pst.setString(8, TextCEP.getText());
            pst.setString(9, TextEND.getText());
            pst.setString(10, TextCity.getText());
            pst.setString(11, TextBairro.getText());
            pst.setString(12, TextNumber.getText());
            pst.setString(13, TextCOMP.getText());
            pst.setString(14, CBOuf.getSelectedItem().toString());
            pst.setString(15, cbCargo.getSelectedItem().toString());
            pst.setString(16, dateContrat);
            pst.setString(17, datePag);
            pst.setString(18, txremunera.getText());
            pst.setString(19, txcomissao.getText());
            pst.setString(20, txtDatac.getText());
            pst.setString(21, txMat.getText());

            if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                if (vle.ValidarEmail(TextEmail.getText())) {
                    if (pf.isCPF()) {
                        int adicionado = pst.executeUpdate();
                        if (adicionado > 0) {
                            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                            JOptionPane.showMessageDialog(null, "Funcionário adicionado com sucesso!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                            refresh();
                            addLogCad();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "CPF inválido!", "<html><b>Error Service!</b></html>", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Email inválido!", "<html><b>Error Service!</b></html>", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar!", "<html><b>Error Service!</b></html>", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {

        String sql = "update funcionarios set nome=?,cpf=?,rg=?,sexo=?,telefone=?,datan=?,email=?,cep=?,endereco=?,cidade=?,bairro=?,numero=?,complemento=?,uf=?,cargo=?,datacontrato=?,datapagamento=?,remunera=?,comissao=? where idfunc=?";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        String dateContrat = formato.format(DateContrato.getDate());
        String datePag = formato.format(DatePgm.getDate());
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, TextNome.getText());
            pst.setString(2, TextCPF.getText());
            pst.setString(3, TextRG.getText());
            pst.setString(4, CBOSexo.getSelectedItem().toString());
            pst.setString(5, TextFONE.getText());
            pst.setString(6, txDataN.getText());
            pst.setString(7, TextEmail.getText());
            pst.setString(8, TextCEP.getText());
            pst.setString(9, TextEND.getText());
            pst.setString(10, TextCity.getText());
            pst.setString(11, TextBairro.getText());
            pst.setString(12, TextNumber.getText());
            pst.setString(13, TextCOMP.getText());
            pst.setString(14, CBOuf.getSelectedItem().toString());
            pst.setString(15, cbCargo.getSelectedItem().toString());
            pst.setString(16, dateContrat);
            pst.setString(17, datePag);
            pst.setString(18, txremunera.getText());
            pst.setString(19, txcomissao.getText());
            pst.setString(20, TextID.getText());

            if ((TextNome.getText().isEmpty()) || (TextEmail.getText().isEmpty()) || (TextFONE.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Dados do funcionário alterados com sucesso!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                    refresh();
                    addLogEdit();

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar os campos!!" + e + "");
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este funcionário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from funcionarios where idfunc=?";
            try {

                if ((TextID.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Nenhum funcionário selecionado!!");
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, TextID.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                    JOptionPane.showMessageDialog(null, "Funcionário removido com sucesso!", "<html><b>Message Service!</b></html>", JOptionPane.INFORMATION_MESSAGE, icon);
                    soundClickRemove();
                    refresh();
                    addLogRemove();

                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            }
        }
    }

    public void refresh() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar os campos?", "<html><b>Message Service!</b></html>", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            try {

                TextBairro.setText(null);
                TextCEP.setText(null);
                TextCOMP.setText(null);
                TextCPF.setText(null);
                TextCity.setText(null);
                TextEND.setText(null);
                txDataN.setText(null);
                TextEmail.setText(null);
                TextFONE.setText(null);
                TextNome.setText(null);
                TextNumber.setText(null);
                TextRG.setText(null);
                txremunera.setText(null);
                txcomissao.setText(null);
                Color colorB = new Color(255, 255, 255);
                btCad.setBackground(colorB);
                btEdit.setEnabled(false);
                btExcluir.setEnabled(false);
                btSearchFunc.setEnabled(true);
                txMat.setText(null);
                DecimalFormat decimal = new DecimalFormat("#,###,###.00");
                NumberFormatter numFormatter = new NumberFormatter(decimal);
                numFormatter.setFormat(decimal);
                numFormatter.setAllowsInvalid(false);
                DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);
                txremunera.setFormatterFactory(null);
                txremunera.setText(null);
                txcomissao.setText(null);
                txremunera.setFormatterFactory(dfFactory);
                btCad.setEnabled(true);
                SetMatricula();
                SetDate();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void ListarCargos() {
        String sql = "select cargo from cargo order by cargo";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cbCargo.addItem(nome);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        TextID = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TextNome = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TextCPF = new javax.swing.JFormattedTextField();
        TextRG = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        TextFONE = new javax.swing.JFormattedTextField();
        TextEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TextCEP = new javax.swing.JFormattedTextField();
        TextEND = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextCity = new javax.swing.JTextField();
        TextBairro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        CBOuf = new javax.swing.JComboBox<>();
        TextNumber = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TextCOMP = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        CBOSexo = new javax.swing.JComboBox<>();
        txDataN = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        btImprimir = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        DateContrato = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        DatePgm = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        cbCargo = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txcomissao = new javax.swing.JTextField();
        txremunera = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        txtDatac = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txMat = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        btCad = new javax.swing.JButton();
        btSearchFunc = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSearch1 = new javax.swing.JButton();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        TextID.setEnabled(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de funcionários");
        setResizable(false);
        setSize(new java.awt.Dimension(925, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("NOME COMPLETO ");

        TextNome.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextNome.setAlignmentY(1.5F);

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel11.setText("CPF");

        TextCPF.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextCPFKeyReleased(evt);
            }
        });

        TextRG.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel12.setText("RG");

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("TELEFONE");

        TextFONE.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextFONE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextFONEKeyReleased(evt);
            }
        });

        TextEmail.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel9.setText("E-MAIL");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("CEP ");

        TextCEP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextCEPKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextCEPKeyReleased(evt);
            }
        });

        TextEND.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("ENDEREÇO");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("CIDADE");

        TextCity.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        TextBairro.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBairroActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("BAIRRO");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("UF");

        CBOuf.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        CBOuf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        TextNumber.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextNumberActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("NÚMERO");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("COMPLEMENTO");

        TextCOMP.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        TextCOMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextCOMPActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel20.setText("SEXO");

        CBOSexo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        CBOSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MASCULINO", "FEMININO", "OUTROS" }));

        txDataN.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("DATA DE NASCIMENTO");

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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(434, 434, 434)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TextNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(TextCOMP)))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TextEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                                    .addComponent(TextCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextEND, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                                    .addComponent(CBOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TextNome)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(TextFONE, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextRG, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextCPF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                                        .addGap(41, 41, 41)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel20))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(CBOSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txDataN)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TextCity, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(33, 33, 33)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7)))))
                            .addComponent(jLabel17)
                            .addComponent(jLabel9))
                        .addGap(0, 211, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TextNome))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CBOSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextRG, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txDataN, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextFONE, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(TextCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TextEND, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextCity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextCOMP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dados cadastrais", jPanel4);

        jLabel13.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel13.setText("DATA DE ADMISSÃO");

        DateContrato.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("REMUNERAÇÃO (R$)");

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("CARGO");

        DatePgm.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("DATA DE PAGAMENTO");

        cbCargo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbCargo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbCargoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("COMISSÃO VENDA (%)");

        txcomissao.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        txremunera.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel21.setText("DATA DE CADASTRO");

        txtDatac.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txtDatac.setEnabled(false);

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("MATRICULA");

        txMat.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txMat.setEnabled(false);

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("(gerado automaticamente)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(DateContrato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbCargo, 0, 180, Short.MAX_VALUE))
                            .addComponent(DatePgm, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txMat, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(txtDatac, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(txremunera)
                            .addComponent(txcomissao, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)))
                .addContainerGap(329, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DateContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(DatePgm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txremunera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txcomissao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtDatac, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txMat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addContainerGap(138, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mais informações", jPanel5);

        btCad.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/adicionarUser.png"))); // NOI18N
        btCad.setText("Salvar cadastro");
        btCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadActionPerformed(evt);
            }
        });

        btSearchFunc.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearchFunc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pesquisarUser.png"))); // NOI18N
        btSearchFunc.setText("Procurar");
        btSearchFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchFuncActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btSearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSearchFunc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btExcluir)
                        .addGap(18, 18, 18)
                        .addComponent(btEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btCad)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCad)
                    .addComponent(btSearchFunc)
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


    private void TextCPFKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCPFKeyReleased
        // TODO add your handling code here:
        TextCPF.setText(JMascara.GetJmascaraCpfCnpj(TextCPF.getText()));
    }//GEN-LAST:event_TextCPFKeyReleased

    private void TextCEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCEPKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            puxarcep();
        }
    }//GEN-LAST:event_TextCEPKeyPressed

    private void TextBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBairroActionPerformed

    private void TextNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextNumberActionPerformed

    private void TextCOMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextCOMPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextCOMPActionPerformed

    private void btCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadActionPerformed
        adicionar();
    }//GEN-LAST:event_btCadActionPerformed

    private void btSearchFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchFuncActionPerformed

        SearchFuncionario scf = new SearchFuncionario();
        scf.setVisible(true);
        btSearchFunc.setEnabled(false);

    }//GEN-LAST:event_btSearchFuncActionPerformed

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
        refresh();
    }//GEN-LAST:event_btSearch1ActionPerformed

    private void cbCargoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbCargoPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        String sql = "select remunera,comissao from cargo where cargo like ?";

        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, cbCargo.getSelectedItem().toString());
            rs = pst.executeQuery();

            while (rs.next()) {
                String cargo = rs.getString(1);
                String funcao = rs.getString(2);
                txremunera.setText(cargo);
                txcomissao.setText(funcao);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }//GEN-LAST:event_cbCargoPopupMenuWillBecomeInvisible

    private void TextCEPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextCEPKeyReleased
        TextCEP.setText(JMascara.GetJmascaraCep(TextCEP.getText()));
        if (TextCEP.getText().length() > 9) {
            puxarcepNoError();
        }
    }//GEN-LAST:event_TextCEPKeyReleased

    private void TextFONEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFONEKeyReleased
        // TODO add your handling code here:
        TextFONE.setText(JMascara.GetJmascaraFone(TextFONE.getText()));
    }//GEN-LAST:event_TextFONEKeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formWindowClosed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
//        if (TextID.getText() == null) {
//
//        } else {
//            int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a geração dos dados do cliente detalhado em pdf?", "Atenção", JOptionPane.YES_NO_OPTION);
//            if (confirma == JOptionPane.YES_OPTION) {
//                try {
//                    String id = TextID.getText();
//                    String sql;
//                    sql = "select * from clientes where idclientes = ?";
//                    pst = conexao.prepareStatement(sql);
//                    pst.setString(1, TextID.getText());
//                    rs = pst.executeQuery();
//                    InputStream report = getClass().getResourceAsStream("/recibos/Dados_clientes.jasper");
//
//                    String os = System.getProperty("os.name");
//                    if (os.startsWith("Windows")) {
//
//                        JRResultSetDataSource relatResul = new JRResultSetDataSource(rs);
//                        JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap(), relatResul);
//                        JasperExportManager.exportReportToPdfFile(jasperPrint, "C://convtec/Supply-x/trash/Recibo#CLT" + id + ".pdf");
//                        File file = new File("C://convtec/Supply-x/trash/Recibo#CLT" + id + ".pdf");
//
//                        try {
//                            Desktop.getDesktop().open(file);
//                        } catch (Exception e) {
//                            JOptionPane.showMessageDialog(null, e);
//                        }
//                    }
//
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e);
//                }
//            }
//        }
    }//GEN-LAST:event_btImprimirActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadFuncionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox<String> CBOSexo;
    public static javax.swing.JComboBox<String> CBOuf;
    public static com.toedter.calendar.JDateChooser DateContrato;
    public static com.toedter.calendar.JDateChooser DatePgm;
    public static javax.swing.JTextField TextBairro;
    public static javax.swing.JFormattedTextField TextCEP;
    public static javax.swing.JTextField TextCOMP;
    public static javax.swing.JFormattedTextField TextCPF;
    public static javax.swing.JTextField TextCity;
    public static javax.swing.JTextField TextEND;
    public static javax.swing.JTextField TextEmail;
    public static javax.swing.JFormattedTextField TextFONE;
    public static javax.swing.JTextField TextID;
    public static javax.swing.JTextField TextNome;
    public static javax.swing.JTextField TextNumber;
    public static javax.swing.JFormattedTextField TextRG;
    public static javax.swing.JButton btCad;
    public static javax.swing.JButton btEdit;
    public static javax.swing.JButton btExcluir;
    public static javax.swing.JButton btImprimir;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JButton btSearchFunc;
    public static javax.swing.JComboBox<String> cbCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JFormattedTextField txDataN;
    public static javax.swing.JTextField txMat;
    public static javax.swing.JTextField txcomissao;
    public static javax.swing.JFormattedTextField txremunera;
    public static javax.swing.JTextField txtDatac;
    // End of variables declaration//GEN-END:variables
}
