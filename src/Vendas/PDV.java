package Vendas;

import Jm.JMascara;
import TableModels.ProdutosModels;
import TablesGetSetters.ProdutosPP;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
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
import java.util.List;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import notification.Notification;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import static pages.TelaPrincipal.empresa;

public final class PDV extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    double total, preco, subtotal, superTotal;
    int qtd;

    String AGetID, AGetCpf, AGetFone, AGetNome;
    String Name;
    ProdutosModels modelo;

    public PDV() {

        initComponents();
        this.setAutoRequestFocus(true);
        setIcon();
        conexao = ModuloConexao.conector();
        modelo = new ProdutosModels();
        setDate();
        SetarVendedor();
        setColorFromEmpresa();
        SetarCliente();
        uper();
        setcodabar();
        SetarEntregador();
        endEntrega.setEnabled(false);
        cbentregador.setEnabled(false);
        valorFrete.setEnabled(false);
        inputData.setEnabled(false);
        cbPagador.setEnabled(false);
        cbAddFrete.setEnabled(false);
        TableCar.setModel(modelo);
        TableCar.getTableHeader().setResizingAllowed(false);
        TableCar.getTableHeader().setReorderingAllowed(false);
        TableCar.getColumnModel().getColumn(0).setMaxWidth(70);
        TableCar.getColumnModel().getColumn(5).setMaxWidth(70);
        TableCar.getColumnModel().getColumn(6).setMaxWidth(60);
        TableCar.getColumnModel().getColumn(7).setMaxWidth(70);

        try {
            MaskFormatter form = new MaskFormatter("##/##/####");
            inputData.setFormatterFactory(new DefaultFormatterFactory(form));

        } catch (ParseException ex) {

        }

    }

    public void setcodabar() {
        String codigo;
        try {
            do {
                codigo = gerarCodigoEAN13();
            } while (verificarCodigoExistente(codigo));
            codabarvenda.setText(codigo);
        } catch (SQLException e) {
        }

    }

    public boolean verificarCodigoExistente(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vendas WHERE ean13 = ?";

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

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        valorFrete.setFormatterFactory(dfFactory);

    }

    public void uper() {
        endEntrega.setDocument(new UpperCase());
        ObsVenda.setDocument(new UpperCase());
        ObsFrete.setDocument(new UpperCase());

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
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

    @SuppressWarnings("rawtypes")
    public static String analisaXml(Document document) {
        List list = document
                .selectNodes("//DirectionsResponse/route/leg/distance/text");

        Element element = (Element) list.get(list.size() - 1);

        return element.getText();
    }

    public static Document getDocumento(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public void calcularKm() {
        if (endEntrega.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo de origem ou destino vazia!!");
        } else {
            String rua2 = endEntrega.getText();
            String calc = calcular(rua2);
            String kmConv = calc.replaceAll("[\\D]", "");
            int kmFrete = Integer.parseInt(kmConv);
            Double preco, total;
            preco = 0.13;
            total = kmFrete * preco + 5;
            String valorF = total.toString();
            valorFrete.setText(valorF);

        }
    }

    public void setDate() {
        Date now = new Date();
        SimpleDateFormat datayes = new SimpleDateFormat("dd/MM/yyyy");
        inputDataC.setDate(now);
    }

    public void SetarVendedor() {
        String sql = "select * from funcionarios order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(2);
                cbVendedor.addItem(nome);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
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

    public void SetarCliente() {
        String sql = "select * from clientes order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                Name = rs.getString(3);
                cbNomeCli.addItem(Name);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void colocarDadosForCPF() {
        String sqv = "select idclientes,nome,cpf,tel from clientes where cpf like ?";
        String getCpf = InputCPFVenda.getText();
        try {
            pst = conexao.prepareStatement(sqv);

            pst.setString(1, getCpf);
            rs = pst.executeQuery();

            while (rs.next()) {
                AGetID = rs.getString(1);
                AGetNome = rs.getString(2);
                AGetCpf = rs.getString(3);
                AGetFone = rs.getString(4);

                cbNomeCli.setSelectedItem(AGetNome);
                inputTelefoneVenda.setText(AGetFone);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
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

    public void DevolverEstoqueCarrinho(String id, int qtdNovo) {

        String sql = "update partepecas set qtde=? where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
            }

        } catch (SQLException e) {

        }

    }

    public void DevolverEstoqueNotebook(String id, int qtdNovo) {

        String sql = "update notebooks set qtde=? where idnotebooks =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
            }

        } catch (SQLException e) {

        }

    }

    public void upEstoqueCar(String ide, int qtdNovoe) {

        String sql = "update partepecas set qtde = qtde+ " + qtdNovoe + " where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setString(1, ide);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
            }

        } catch (Exception e) {

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        txtid = new javax.swing.JTextField();
        txEmail = new javax.swing.JTextField();
        campoLimite = new javax.swing.JTextField();
        Background = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        cbNomeCli = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        InputCPFVenda = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        inputTelefoneVenda = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        inputDataC = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        inputGarant = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        cbVendedor = new javax.swing.JComboBox<>();
        add3m = new javax.swing.JButton();
        add6m = new javax.swing.JButton();
        add9m = new javax.swing.JButton();
        add12m = new javax.swing.JButton();
        cbCanal = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbFormaRecebi = new javax.swing.JComboBox<>();
        pEntrega = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        endEntrega = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbPagador = new javax.swing.JComboBox<>();
        cbAddFrete = new javax.swing.JButton();
        valorFrete = new javax.swing.JFormattedTextField();
        inputData = new javax.swing.JFormattedTextField();
        jLabel23 = new javax.swing.JLabel();
        cbentregador = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ObsFrete = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ObsVenda = new javax.swing.JTextArea();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        obsgarantia = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        codabarvenda = new javax.swing.JLabel();
        btSearch1 = new javax.swing.JButton();
        btCad = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btAddProduto = new javax.swing.JButton();
        txTotal = new javax.swing.JFormattedTextField();
        btRemoveProduto = new javax.swing.JButton();
        btLimparCar = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCar = new javax.swing.JTable();

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

        txtid.setText("jTextField1");

        campoLimite.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PDV");
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

        Background.setBackground(new java.awt.Color(204, 204, 204));

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N

        cbNomeCli.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbNomeCli.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNomeCliItemStateChanged(evt);
            }
        });
        cbNomeCli.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbNomeCliPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbNomeCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbNomeCliMouseClicked(evt);
            }
        });
        cbNomeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNomeCliActionPerformed(evt);
            }
        });
        AutoCompleteDecorator.decorate(cbNomeCli);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("NOME :");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("CPF / CNPJ :");

        InputCPFVenda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        InputCPFVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InputCPFVendaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                InputCPFVendaKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel10.setText("TELEFONE :");

        inputTelefoneVenda.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        inputTelefoneVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTelefoneVendaKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("DATA DE COMPRA :");

        inputDataC.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("GARANTIA :");

        inputGarant.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel25.setText("VENDEDOR");

        cbVendedor.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        add3m.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        add3m.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/number-3.png"))); // NOI18N
        add3m.setToolTipText("Adicionar garantia de 3 meses");
        add3m.setContentAreaFilled(false);
        add3m.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add3m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add3mActionPerformed(evt);
            }
        });

        add6m.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        add6m.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/six (1).png"))); // NOI18N
        add6m.setToolTipText("Adicionar garantia de 6 meses");
        add6m.setContentAreaFilled(false);
        add6m.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add6m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add6mActionPerformed(evt);
            }
        });

        add9m.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        add9m.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/number-9 (1).png"))); // NOI18N
        add9m.setToolTipText("Adicionar garantia de 9 meses");
        add9m.setContentAreaFilled(false);
        add9m.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add9m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add9mActionPerformed(evt);
            }
        });

        add12m.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        add12m.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/number-12.png"))); // NOI18N
        add12m.setToolTipText("Adicionar garantia de 12 meses");
        add12m.setContentAreaFilled(false);
        add12m.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add12m.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add12mActionPerformed(evt);
            }
        });

        cbCanal.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbCanal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "OLX", "Facebook", "Instagram", "WhatsApp", "Outros meios virtuais", "Outros meios pessoais" }));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("CANAL DE VENDA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(inputDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputGarant, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(InputCPFVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(22, 22, 22)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(inputTelefoneVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cbNomeCli, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addGap(59, 59, 59)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cbVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(add3m, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add6m, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add9m, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(add12m, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cbVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(InputCPFVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(cbCanal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(inputTelefoneVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(7, 7, 7))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(inputGarant, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(1, 1, 1)))
                    .addComponent(inputDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add3m, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add6m, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add9m, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add12m, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Dados Gerais", jPanel4);

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel16.setText("FORMA DE RECEBIMENTO :");

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

        pEntrega.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da entrega", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 13))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel17.setText("ENDEREÇO DE ENTREGA");

        endEntrega.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        endEntrega.setText("--");
        endEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                endEntregaKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel18.setText("VALOR DO FRETE");

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel19.setText("DATA DE ENTREGA");

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel20.setText("PAGADOR DA ENTREGA");

        cbPagador.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        cbPagador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "CLIENTE", "ESTABELECIMENTO" }));
        cbPagador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPagadorActionPerformed(evt);
            }
        });

        cbAddFrete.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        cbAddFrete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/entrega-rapida-2.png"))); // NOI18N
        cbAddFrete.setText("Adicionar frete");
        cbAddFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAddFreteActionPerformed(evt);
            }
        });

        valorFrete.setText("--");
        valorFrete.setToolTipText("");
        valorFrete.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        inputData.setText("00/00/0000");
        inputData.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        inputData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputDataKeyReleased(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel23.setText("ENTREGADOR");

        cbentregador.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbentregador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--" }));
        cbentregador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbentregadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pEntregaLayout = new javax.swing.GroupLayout(pEntrega);
        pEntrega.setLayout(pEntregaLayout);
        pEntregaLayout.setHorizontalGroup(
            pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEntregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEntregaLayout.createSequentialGroup()
                        .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pEntregaLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel18))
                            .addComponent(valorFrete))
                        .addGap(29, 29, 29)
                        .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(inputData, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbPagador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel17)
                    .addComponent(endEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbAddFrete, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addComponent(cbentregador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel23))
                .addContainerGap())
        );
        pEntregaLayout.setVerticalGroup(
            pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEntregaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPagador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbAddFrete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel21.setText("OBSERVAÇÕES DO FRETE / RETIRADA:");

        ObsFrete.setColumns(20);
        ObsFrete.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        ObsFrete.setRows(5);
        jScrollPane1.setViewportView(ObsFrete);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbFormaRecebi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane1))
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
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(cbFormaRecebi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Informações de entrega/retirada", jPanel5);

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel22.setText("OBSERVAÇÕES DA VENDA :");

        ObsVenda.setColumns(20);
        ObsVenda.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        ObsVenda.setRows(5);
        jScrollPane3.setViewportView(ObsVenda);

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel24.setText("OBSERVAÇÕES DA GARANTIA :");

        obsgarantia.setColumns(20);
        obsgarantia.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        obsgarantia.setRows(5);
        jScrollPane4.setViewportView(obsgarantia);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addContainerGap(288, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(73, 73, 73))
        );

        jTabbedPane1.addTab("Mais informações", jPanel3);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1081, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Dados de produtos como entrada", jPanel7);

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel38.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/ponto-de-venda.png"))); // NOI18N
        jLabel38.setText(" PONTO DE VENDA");

        codabarvenda.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        codabarvenda.setForeground(new java.awt.Color(255, 255, 255));
        codabarvenda.setText("1542541565895");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(codabarvenda)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codabarvenda)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btSearch1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/clear.png"))); // NOI18N
        btSearch1.setText("Limpar campos");
        btSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearch1ActionPerformed(evt);
            }
        });

        btCad.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btCad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cobrar-2.png"))); // NOI18N
        btCad.setText("Efetuar pagamento");
        btCad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel2.setText("TOTAL:");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/carrinho-de-compras.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Century Gothic", 3, 24)); // NOI18N
        jLabel9.setText("CARRINHO");

        btAddProduto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btAddProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/descricao-do-produto.png"))); // NOI18N
        btAddProduto.setText("Adicionar produtos");
        btAddProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProdutoActionPerformed(evt);
            }
        });

        txTotal.setText("0.0");
        txTotal.setEnabled(false);
        txTotal.setFont(new java.awt.Font("Century Gothic", 3, 18)); // NOI18N

        btRemoveProduto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btRemoveProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemoveProduto.setText("Remover produto");
        btRemoveProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveProdutoActionPerformed(evt);
            }
        });

        btLimparCar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btLimparCar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/CARRINHO_OFF.png"))); // NOI18N
        btLimparCar.setText("Limpar carrinho");
        btLimparCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparCarActionPerformed(evt);
            }
        });

        jScrollPane2.setPreferredSize(new java.awt.Dimension(64, 64));

        TableCar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TableCar);

        tableScrollButton1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btLimparCar)
                        .addGap(18, 18, 18)
                        .addComponent(btRemoveProduto)
                        .addGap(18, 18, 18)
                        .addComponent(btAddProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAddProduto)
                    .addComponent(txTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRemoveProduto)
                    .addComponent(btLimparCar))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout BackgroundLayout = new javax.swing.GroupLayout(Background);
        Background.setLayout(BackgroundLayout);
        BackgroundLayout.setHorizontalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BackgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btSearch1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCad)))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BackgroundLayout.setVerticalGroup(
            BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(BackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCad)
                    .addComponent(btSearch1))
                .addGap(10, 10, 10))
        );

        getContentPane().add(Background, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbNomeCliItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNomeCliItemStateChanged

    }//GEN-LAST:event_cbNomeCliItemStateChanged

    private void cbNomeCliPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbNomeCliPopupMenuWillBecomeInvisible

        String sql = "select idclientes,cpf,tel,email,limitecredito from clientes where nome  = ?";
        String getNome = cbNomeCli.getSelectedItem().toString();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getNome);
            rs = pst.executeQuery();

            if (rs.next()) {
                String idCliente = rs.getString(1);
                String CpfCliente = rs.getString(2);
                String FoneCliente = rs.getString(3);
                String EmailCliente = rs.getString(4);
                String limite = rs.getString(5);

                txtid.setText(idCliente);
                InputCPFVenda.setText(CpfCliente);
                inputTelefoneVenda.setText(FoneCliente);
                txEmail.setText(EmailCliente);
                campoLimite.setText(limite);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cbNomeCliPopupMenuWillBecomeInvisible

    private void cbNomeCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbNomeCliMouseClicked

    }//GEN-LAST:event_cbNomeCliMouseClicked

    private void cbNomeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNomeCliActionPerformed

    }//GEN-LAST:event_cbNomeCliActionPerformed

    private void InputCPFVendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputCPFVendaKeyPressed


    }//GEN-LAST:event_InputCPFVendaKeyPressed

    private void InputCPFVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputCPFVendaKeyReleased

        InputCPFVenda.setText(JMascara.GetJmascaraCpfCnpj(InputCPFVenda.getText()));
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            colocarDadosForCPF();
        }
    }//GEN-LAST:event_InputCPFVendaKeyReleased

    private void btSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearch1ActionPerformed

    }//GEN-LAST:event_btSearch1ActionPerformed

    private void btCadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadActionPerformed
        if ((InputCPFVenda.getText().isEmpty()) || (inputTelefoneVenda.getText().isEmpty())) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios");

        } else {
            if (txTotal.getText().equals("0.0")) {
                JOptionPane.showMessageDialog(null, "Nenhum item selecionado no carrinho!");
            } else {
                TelaPagamento tlp = new TelaPagamento();
                tlp.setVisible(true);
                tlp.carrinho = modelo;
            }

        }

    }//GEN-LAST:event_btCadActionPerformed

    private void add3mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add3mActionPerformed

        Calendar c = inputDataC.getCalendar();
        c.add(Calendar.MONTH, 3);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String next = df.format(c.getTime());
        try {
            Date dataform = df.parse(next);
            inputGarant.setDate(dataform);

        } catch (ParseException ex) {

        }
    }//GEN-LAST:event_add3mActionPerformed

    private void add6mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add6mActionPerformed
        Calendar c = inputDataC.getCalendar();
        c.add(Calendar.MONTH, 6);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String next = df.format(c.getTime());
        try {
            Date dataform = df.parse(next);
            inputGarant.setDate(dataform);

        } catch (ParseException ex) {

        }
    }//GEN-LAST:event_add6mActionPerformed

    private void btAddProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProdutoActionPerformed

        SelectProdutosCar slc = new SelectProdutosCar();
        slc.setVisible(true);
        btAddProduto.setEnabled(false);
    }//GEN-LAST:event_btAddProdutoActionPerformed

    private void cbFormaRecebiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormaRecebiActionPerformed


    }//GEN-LAST:event_cbFormaRecebiActionPerformed

    private void endEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_endEntregaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calcularKm();
        }
    }//GEN-LAST:event_endEntregaKeyPressed

    private void cbAddFreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAddFreteActionPerformed
        // TODO add your handling code here:
        if (endEntrega.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum endereço selecionado!!");
        } else {
            try {
                int quantidade = 1;
                ProdutosPP P = new ProdutosPP();
                qtd = (int) 1;
                preco = Double.parseDouble(valorFrete.getText());

                superTotal = Double.parseDouble(PDV.txTotal.getText());
                subtotal = qtd * preco;

                total = superTotal + subtotal;

                Date now = new Date();
                String entregador = cbentregador.getSelectedItem().toString();
                SimpleDateFormat datayes = new SimpleDateFormat("dd/MM/yyyy");
                String DataFormated = datayes.format(now);
                String End = endEntrega.getText();
                P.setID("00001");
                P.setProduto("Frete");
                P.setDescricao("Frete p/ " + End + "");
                P.setCodb("Entrega por: " + entregador + "");
                P.setSerial("FTCL000X");
                P.setMarca("N/A");
                P.setPreco(valorFrete.getText());
                P.setQtde("1");
                P.setSubtotal((String.valueOf(subtotal)));
                modelo = (ProdutosModels) TableCar.getModel();
                TableCar.setModel(modelo);
                modelo.addProdutosPP(P);
                PDV.txTotal.setText(String.valueOf(total));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Algo deu errado, revise os dados da entrega!");
            }

        }


    }//GEN-LAST:event_cbAddFreteActionPerformed

    private void cbPagadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPagadorActionPerformed
        // TODO add your handling code here:
        if (cbPagador.getSelectedItem().equals("ESTABELECIMENTO")) {
            cbAddFrete.setVisible(false);
        } else {
            cbAddFrete.setVisible(true);
            cbAddFrete.setEnabled(true);
        }
    }//GEN-LAST:event_cbPagadorActionPerformed

    private void btRemoveProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveProdutoActionPerformed
        // TODO add your handling code here:
        modelo = (ProdutosModels) TableCar.getModel();
        int setar = TableCar.getSelectedRow();
        String TipoProduto = (TableCar.getModel().getValueAt(setar, 1).toString());

        if (TableCar.getSelectedRow() >= 0) {
            if (TipoProduto.equals("Outros")) {
                
                String precoTableCar = (TableCar.getModel().getValueAt(setar, 8).toString());
                String qt = (TableCar.getModel().getValueAt(setar, 7).toString());
                String id = (TableCar.getModel().getValueAt(setar, 0).toString());
                int qtdPrecoCar = Integer.parseInt(qt);
                Double getQtde = Double.valueOf(precoTableCar);
                String GetTotal = txTotal.getText();
                Double TotalAll = Double.valueOf(GetTotal);
                double result = TotalAll - getQtde;
                total = TotalAll - getQtde;
                String resultadoF = Double.toString(result);
                txTotal.setText(resultadoF);
                modelo.remove(TableCar.getSelectedRow());
                TableCar.setModel(modelo);
                soundClickRemove();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Produto removido com sucesso!");
                noti.showNotification();
                try {
                    String sql = "select qtde from  partepecas where idpartepecas =?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, id);
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        int totalnew = 0;
                        int qtdeestoque = rs.getInt(1);

                        totalnew = qtdeestoque + qtdPrecoCar;
                        DevolverEstoqueCarrinho(id, totalnew);
                    }

                } catch (HeadlessException | NumberFormatException | SQLException e) {

                }

            } else if (TipoProduto.equals("Notebook")) {
                String precoTableCar = (TableCar.getModel().getValueAt(setar, 8).toString());
                String qt = (TableCar.getModel().getValueAt(setar, 7).toString());
                String id = (TableCar.getModel().getValueAt(setar, 0).toString());
                int qtdPrecoCar = Integer.parseInt(qt);
                Double getQtde = Double.valueOf(precoTableCar);
                String GetTotal = txTotal.getText();
                Double TotalAll = Double.valueOf(GetTotal);
                double result = TotalAll - getQtde;
                total = TotalAll - getQtde;
                String resultadoF = Double.toString(result);
                txTotal.setText(resultadoF);
                modelo.remove(TableCar.getSelectedRow());
                TableCar.setModel(modelo);
                soundClickRemove();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Notebook removido com sucesso!");
                noti.showNotification();
                try {
                    String sql = "select qtde from  notebooks where idnotebooks =?";
                    pst = conexao.prepareStatement(sql);
                    pst.setString(1, id);
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        int totalnew = 0;
                        int qtdeestoque = rs.getInt(1);
                        totalnew = qtdeestoque + qtdPrecoCar;
                        DevolverEstoqueNotebook(id, totalnew);
                    }

                } catch (HeadlessException | NumberFormatException | SQLException e) {

                }
            } else if (TipoProduto.equals("Desktop")) {

            } else if (TipoProduto.equals("Monitor")) {

            }
        } else {

            Notification noti = new Notification(this, Notification.Type.INFO, Notification.Location.TOP_CENTER, "Nenhum produto selecionado!!");
            noti.showNotification();

        }


    }//GEN-LAST:event_btRemoveProdutoActionPerformed

    private void btLimparCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparCarActionPerformed
        // TODO add your handling code here:
        modelo = (ProdutosModels) TableCar.getModel();
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar o carrinho de compras?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            int rowcount = TableCar.getRowCount();
            for (int i = 0; i < rowcount; i++) {
                String qt = (TableCar.getModel().getValueAt(i, 7).toString());
                String id = (TableCar.getModel().getValueAt(i, 0).toString());
                int qtnew = Integer.parseInt(qt);
                upEstoqueCar(id, qtnew);
            }
            modelo.limpar();
            Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Carrinho limpo com sucesso!");
            noti.showNotification();
            TableCar.setModel(modelo);
            txTotal.setText("0.0");

        } else {

        }
    }//GEN-LAST:event_btLimparCarActionPerformed

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

    private void inputTelefoneVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputTelefoneVendaKeyReleased
        // TODO add your handling code here:
        inputTelefoneVenda.setText(JMascara.GetJmascaraFone(inputTelefoneVenda.getText()));
    }//GEN-LAST:event_inputTelefoneVendaKeyReleased

    private void inputDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputDataKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_inputDataKeyReleased

    private void add9mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add9mActionPerformed
        // TODO add your handling code here:
        Calendar c = inputDataC.getCalendar();
        c.add(Calendar.MONTH, 9);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String next = df.format(c.getTime());
        try {
            Date dataform = df.parse(next);
            inputGarant.setDate(dataform);

        } catch (ParseException ex) {

        }
    }//GEN-LAST:event_add9mActionPerformed

    private void add12mActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add12mActionPerformed
        // TODO add your handling code here:
        Calendar c = inputDataC.getCalendar();
        c.add(Calendar.MONTH, 12);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String next = df.format(c.getTime());
        try {
            Date dataform = df.parse(next);
            inputGarant.setDate(dataform);

        } catch (ParseException ex) {

        }
    }//GEN-LAST:event_add12mActionPerformed

    private void cbentregadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbentregadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbentregadorActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_F1) {
            btCad.doClick();
        }
    }//GEN-LAST:event_formKeyPressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        int rowcount = TableCar.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            String qt = (TableCar.getModel().getValueAt(i, 7).toString());
            String id = (TableCar.getModel().getValueAt(i, 0).toString());
            int qtnew = Integer.parseInt(qt);
            upEstoqueCar(id, qtnew);
        }
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:

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
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PDV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    public static javax.swing.JFormattedTextField InputCPFVenda;
    public static javax.swing.JTextArea ObsFrete;
    public static javax.swing.JTextArea ObsVenda;
    public static javax.swing.JTable TableCar;
    private javax.swing.JButton add12m;
    private javax.swing.JButton add3m;
    private javax.swing.JButton add6m;
    private javax.swing.JButton add9m;
    public static javax.swing.JButton btAddProduto;
    public static javax.swing.JButton btCad;
    private javax.swing.JButton btLimparCar;
    private javax.swing.JButton btRemoveProduto;
    public static javax.swing.JButton btSearch1;
    public static javax.swing.JTextField campoLimite;
    public static javax.swing.JButton cbAddFrete;
    public static javax.swing.JComboBox<String> cbCanal;
    public static javax.swing.JComboBox<String> cbFormaRecebi;
    public static javax.swing.JComboBox<String> cbNomeCli;
    public static javax.swing.JComboBox<String> cbPagador;
    public static javax.swing.JComboBox<String> cbVendedor;
    public static javax.swing.JComboBox<String> cbentregador;
    public static javax.swing.JLabel codabarvenda;
    public static javax.swing.JTextField endEntrega;
    public static javax.swing.JFormattedTextField inputData;
    public static com.toedter.calendar.JDateChooser inputDataC;
    public static com.toedter.calendar.JDateChooser inputGarant;
    public static javax.swing.JFormattedTextField inputTelefoneVenda;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTextArea obsgarantia;
    private javax.swing.JPanel pEntrega;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    public static javax.swing.JTextField txEmail;
    public static javax.swing.JFormattedTextField txTotal;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JFormattedTextField valorFrete;
    // End of variables declaration//GEN-END:variables
}
