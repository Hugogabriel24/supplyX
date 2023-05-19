/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesCadastro;

import TableModels.ProdutosModelsOrcamento;
import TablesGetSetters.ProdutosPPOrc;
import br.com.tanamao.dal.ModuloConexao;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import Orcamento.SelectProdutosOrcamento;
import static pages.TelaPrincipal.empresa;

/**
 *
 * @author Hugo Gabriel
 */
public final class NewOrcamento extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    double total, preco, subtotal, superTotal;
    int qtd;

    String AGetID, AGetCpf, AGetFone, AGetNome;
    String Name;
    ProdutosModelsOrcamento modelo;

    public NewOrcamento() {
        initComponents();
        conexao = ModuloConexao.conector();
        modelo = new ProdutosModelsOrcamento();
        setColorFromEmpresa();
        formatarpreco();
        tableCarOrcamento.setModel(modelo);
        endEntrega.setEnabled(false);
        cbentregador.setEnabled(false);
        valorFrete.setEnabled(false);
        inputData.setEnabled(false);
        cbPagador.setEnabled(false);
        cbAddFrete.setEnabled(false);
        setIcon();
        setDate();
        SetarVendedor();
        SetarCliente();
        SetarEntregador();
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

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setDate() {
        Date now = new Date();
        SimpleDateFormat datayes = new SimpleDateFormat("dd/MM/yyyy");
        String DataFormated = datayes.format(now);
        inputDataC.setDate(now);
    }

    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            jPanel2.setBackground(new Color(15, 102, 122));
        } else {
            jPanel2.setBackground(new Color(147, 167, 39));
        }
    }

    public void SetarVendedor() {
        String sql = "select * from funcionarios order by nome";

        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(2);
                vendedorOrcamento.addItem(nome);

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
                cbNomeOrc.addItem(Name);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void colocarDadosForCPF() {
        String sqv = "select idclientes,nome,cpf,tel from clientes where cpf like ?";
        String getCpf = InputCPFOrc.getText();
        try {
            pst = conexao.prepareStatement(sqv);

            pst.setString(1, getCpf);
            rs = pst.executeQuery();

            while (rs.next()) {
                AGetID = rs.getString(1);
                AGetNome = rs.getString(2);
                AGetCpf = rs.getString(3);
                AGetFone = rs.getString(4);

                cbNomeOrc.setSelectedItem(AGetNome);
                inputTelefoneOrcamento.setText(AGetFone);

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

    public static String calcular(String destino) {
        URL url;
        try {
            url = new URL(
                    "https://maps.googleapis.com/maps/api/directions/xml?origin=Rua Francisco Berenguer, 737, Recife-PE&destination=" + destino + "&key=AIzaSyAOME7FCQDBd-yzqsZWAhWxh6-95llIJTA");

            Document document = getDocumento(url);

            return analisaXml(document);
        } catch (MalformedURLException | DocumentException e) {
            e.printStackTrace();
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

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        valorcdescontoTotal.setFormatterFactory(dfFactory);
        valorFrete.setFormatterFactory(dfFactory);

    }

    public void saveorc() {

        String[] botoes = {"Salvar orçamento", "Salvar orçamento e gerar PDF", "Salvar orçamento xxx"};
        int option = JOptionPane.showOptionDialog(null, "Escolha a opção para gerar o recibo de venda!", "<html><b>GERAR RECIBO</html></b>", 0, JOptionPane.QUESTION_MESSAGE, null, botoes, "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtid = new javax.swing.JTextField();
        txEmail = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cbNomeOrc = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        InputCPFOrc = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        inputTelefoneOrcamento = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        inputDataC = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        vendedorOrcamento = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        inputDataC1 = new com.toedter.calendar.JDateChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        btAddProdutoOrc = new javax.swing.JButton();
        btRemoveProduto = new javax.swing.JButton();
        btLimparCar = new javax.swing.JButton();
        tableScrollButton1 = new ArchiveTablesVendas.TableScrollButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCarOrcamento = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        endEntrega = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        valorFrete = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        inputData = new javax.swing.JFormattedTextField();
        cbPagador = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        cbAddFrete = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        cbFormaRecebi = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ObsFrete = new javax.swing.JTextArea();
        cbentregador = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        CBOFormapgm = new javax.swing.JComboBox<>();
        CBOParcelas = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txdesconto = new javax.swing.JFormattedTextField();
        valorcdescontoTotal = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ObsVenda2 = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ObsVenda = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txTotalOrcamento = new javax.swing.JFormattedTextField();
        btAddProduto1 = new javax.swing.JButton();

        txtid.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/orcamento_1.png"))); // NOI18N
        jLabel15.setText(" ORÇAMENTO DE VENDA");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("REF. ORÇAMENTO:");

        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jTextField1.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Dados do orçamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(204, 204, 204));

        cbNomeOrc.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbNomeOrc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNomeOrcItemStateChanged(evt);
            }
        });
        cbNomeOrc.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbNomeOrcPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cbNomeOrc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbNomeOrcMouseClicked(evt);
            }
        });
        cbNomeOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNomeOrcActionPerformed(evt);
            }
        });
        AutoCompleteDecorator.decorate(cbNomeOrc);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("NOME :");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel4.setText("CPF / CNPJ :");

        InputCPFOrc.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        InputCPFOrc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                InputCPFOrcKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                InputCPFOrcKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel10.setText("TELEFONE :");

        inputTelefoneOrcamento.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        inputTelefoneOrcamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputTelefoneOrcamentoKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("DATA DO ORÇAM. :");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel9.setText("VENDEDOR :");

        vendedorOrcamento.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel8.setText("VALIDADE :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputDataC1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InputCPFOrc, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vendedorOrcamento, 0, 206, Short.MAX_VALUE)
                            .addComponent(inputTelefoneOrcamento)))
                    .addComponent(cbNomeOrc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbNomeOrc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InputCPFOrc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputDataC, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(inputTelefoneOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(vendedorOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputDataC1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        btAddProdutoOrc.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btAddProdutoOrc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/descricao-do-produto.png"))); // NOI18N
        btAddProdutoOrc.setText("Adicionar produtos");
        btAddProdutoOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProdutoOrcActionPerformed(evt);
            }
        });

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

        tableCarOrcamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "DESCRICAO", "QTDE", "PRECO", "SUBTOTAL"
            }
        ));
        jScrollPane1.setViewportView(tableCarOrcamento);

        tableScrollButton1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 388, Short.MAX_VALUE)
                        .addComponent(btLimparCar)
                        .addGap(18, 18, 18)
                        .addComponent(btRemoveProduto)
                        .addGap(18, 18, 18)
                        .addComponent(btAddProdutoOrc, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAddProdutoOrc)
                    .addComponent(btRemoveProduto)
                    .addComponent(btLimparCar))
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("Carrinho de produtos ", jPanel3);

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel17.setText("ENDEREÇO DE ENTREGA");

        endEntrega.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        endEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                endEntregaMouseClicked(evt);
            }
        });
        endEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                endEntregaKeyPressed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel18.setText("VALOR DO FRETE");

        valorFrete.setText("--");
        valorFrete.setToolTipText("");
        valorFrete.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel19.setText("DATA DE ENTREGA");

        inputData.setText("00/00/0000");
        inputData.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        inputData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputDataKeyReleased(evt);
            }
        });

        cbPagador.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        cbPagador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "CLIENTE", "ESTABELECIMENTO" }));
        cbPagador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPagadorActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("PAGADOR DA ENTREGA");

        cbAddFrete.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        cbAddFrete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/entrega-rapida-2.png"))); // NOI18N
        cbAddFrete.setText("Adicionar frete");
        cbAddFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAddFreteActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel23.setText("FORMA DE RECEBIMENTO :");

        cbFormaRecebi.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
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

        jLabel24.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel24.setText("OBSERVAÇÕES DO FRETE / RETIRADA:");

        ObsFrete.setColumns(20);
        ObsFrete.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        ObsFrete.setRows(5);
        jScrollPane2.setViewportView(ObsFrete);

        cbentregador.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        cbentregador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--" }));
        cbentregador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbentregadorActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel25.setText("ENTREGADOR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel17)
                        .addComponent(endEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cbFormaRecebi, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbPagador, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel25)
                                .addComponent(cbentregador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(cbAddFrete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(valorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(inputData, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel24)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(cbFormaRecebi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(valorFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbentregador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPagador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(cbAddFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jTabbedPane1.addTab("Dados da entrega", jPanel5);

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Forma de pagamento");

        CBOFormapgm.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
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

        CBOParcelas.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CBOParcelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "1x", "2x", "3x", "4x", "5x", "6x", "7x", "8x", "9x", "10x", "11x", "12x", "14x", "16x", "24x" }));
        CBOParcelas.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel16.setText("Número de parcelas");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel21.setText("Desconto");

        jLabel1.setFont(new java.awt.Font("Century", 0, 14)); // NOI18N
        jLabel1.setText("%");

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

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel22.setText("Valor total c/ desconto");

        jLabel28.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel28.setText("CONDIÇÕES DE PAGAMENTO :");

        ObsVenda2.setColumns(20);
        ObsVenda2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        ObsVenda2.setRows(5);
        jScrollPane5.setViewportView(ObsVenda2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBOFormapgm, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CBOParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addComponent(jLabel22))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txdesconto, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(valorcdescontoTotal)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(0, 562, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(CBOFormapgm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(CBOParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txdesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(valorcdescontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados do pagamento", jPanel6);

        ObsVenda.setColumns(20);
        ObsVenda.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        ObsVenda.setRows(5);
        jScrollPane3.setViewportView(ObsVenda);

        jLabel26.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel26.setText("OBSERVAÇÕES DO ORÇAMENTO :");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(624, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(209, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Mais informações", jPanel7);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel2.setText("TOTAL:");

        txTotalOrcamento.setText("0.0");
        txTotalOrcamento.setEnabled(false);
        txTotalOrcamento.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N

        btAddProduto1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btAddProduto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/salvar-instagram.png"))); // NOI18N
        btAddProduto1.setText("Salvar orçamento");
        btAddProduto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProduto1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txTotalOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btAddProduto1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAddProduto1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txTotalOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void cbNomeOrcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNomeOrcItemStateChanged

    }//GEN-LAST:event_cbNomeOrcItemStateChanged

    private void cbNomeOrcPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbNomeOrcPopupMenuWillBecomeInvisible

        String sql = "select idclientes,cpf,tel,email from clientes where nome  = ?";
        String getNome = cbNomeOrc.getSelectedItem().toString();
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
                InputCPFOrc.setText(CpfCliente);
                inputTelefoneOrcamento.setText(FoneCliente);
                txEmail.setText(EmailCliente);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cbNomeOrcPopupMenuWillBecomeInvisible

    private void cbNomeOrcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbNomeOrcMouseClicked

    }//GEN-LAST:event_cbNomeOrcMouseClicked

    private void cbNomeOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNomeOrcActionPerformed

    }//GEN-LAST:event_cbNomeOrcActionPerformed

    private void InputCPFOrcKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputCPFOrcKeyPressed

    }//GEN-LAST:event_InputCPFOrcKeyPressed

    private void InputCPFOrcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputCPFOrcKeyReleased

//        InputCPFVenda.setText(JMascara.GetJmascaraCpfCnpj(InputCPFVenda.getText()));
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            colocarDadosForCPF();
//        }
    }//GEN-LAST:event_InputCPFOrcKeyReleased

    private void inputTelefoneOrcamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputTelefoneOrcamentoKeyReleased
        // TODO add your handling code here:
//        inputTelefoneVenda.setText(JMascara.GetJmascaraFone(inputTelefoneVenda.getText()));
    }//GEN-LAST:event_inputTelefoneOrcamentoKeyReleased

    private void btAddProdutoOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProdutoOrcActionPerformed

        SelectProdutosOrcamento slpo = new SelectProdutosOrcamento();
        slpo.setVisible(true);
        btAddProdutoOrc.setEnabled(false);
    }//GEN-LAST:event_btAddProdutoOrcActionPerformed

    private void btRemoveProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveProdutoActionPerformed
        // TODO add your handling code here:
        modelo = (ProdutosModelsOrcamento) tableCarOrcamento.getModel();
        int setar = tableCarOrcamento.getSelectedRow();
        if (tableCarOrcamento.getSelectedRow() >= 0) {
            String getQ = (tableCarOrcamento.getModel().getValueAt(setar, 8).toString());
            Double getQtde = Double.parseDouble(getQ);
            String GetTotal = txTotalOrcamento.getText();
            Double TotalAll = Double.parseDouble(GetTotal);
            double result = TotalAll - getQtde;
            total = TotalAll - getQtde;
            String resultadoF = Double.toString(result);
            txTotalOrcamento.setText(resultadoF);
            modelo.remove(tableCarOrcamento.getSelectedRow());
            tableCarOrcamento.setModel(modelo);
            soundClickRemove();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto!");
        }
    }//GEN-LAST:event_btRemoveProdutoActionPerformed

    private void btLimparCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparCarActionPerformed
        // TODO add your handling code here:
        modelo = (ProdutosModelsOrcamento) tableCarOrcamento.getModel();
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja limpar o carrinho?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            modelo.limpar();
            tableCarOrcamento.setModel(modelo);
            txTotalOrcamento.setText("0.0");

        } else {

        }
    }//GEN-LAST:event_btLimparCarActionPerformed

    private void btAddProduto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProduto1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btAddProduto1ActionPerformed

    private void endEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_endEntregaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calcularKm();
        }
    }//GEN-LAST:event_endEntregaKeyPressed

    private void inputDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputDataKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_inputDataKeyReleased

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
        // TODO add your handling code here:
        if (endEntrega.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nenhum endereço selecionado!!");
        } else {
            try {
                int quantidade = 1;
                ProdutosPPOrc P = new ProdutosPPOrc();
                qtd = (int) 1;
                preco = Double.parseDouble(valorFrete.getText());

                superTotal = Double.parseDouble(txTotalOrcamento.getText());
                subtotal = qtd * preco;

                total = superTotal + subtotal;

                Date now = new Date();
                SimpleDateFormat datayes = new SimpleDateFormat("dd/MM/yyyy");
                String DataFormated = datayes.format(now);
                String End = endEntrega.getText();
                P.setID("00001");
                P.setProduto("Frete");
                P.setDescricao("Frete p/ " + End + "");
                P.setCodb("teste");
                P.setSerial("FTCL");
                P.setMarca("N/A");
                P.setPreco(valorFrete.getText());
                P.setQtde("1");
                P.setSubtotal((String.valueOf(subtotal)));
                modelo = (ProdutosModelsOrcamento) tableCarOrcamento.getModel();
                tableCarOrcamento.setModel(modelo);
                modelo.addProdutosPP(P);
                txTotalOrcamento.setText(String.valueOf(total));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Algo deu errado, revise os dados da entrega!");
            }

        }

    }//GEN-LAST:event_cbAddFreteActionPerformed

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
                String number = txTotalOrcamento.getText().replaceAll(",", ".");
                String number1 = txdesconto.getText().replaceAll(",", ".");

                double num = Double.parseDouble(number);

                double num1 = Double.parseDouble(number1);
                if (num1 <= 100 && num1 >= 0) {
                    double subtotal, total;

                    subtotal = num * num1 / 100;
                    total = num - subtotal;

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

    private void endEntregaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_endEntregaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_endEntregaMouseClicked

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

    private void cbentregadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbentregadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbentregadorActionPerformed

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
            java.util.logging.Logger.getLogger(NewOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewOrcamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewOrcamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBOFormapgm;
    private javax.swing.JComboBox<String> CBOParcelas;
    public static javax.swing.JFormattedTextField InputCPFOrc;
    public static javax.swing.JTextArea ObsFrete;
    public static javax.swing.JTextArea ObsVenda;
    public static javax.swing.JTextArea ObsVenda2;
    public static javax.swing.JButton btAddProduto1;
    public static javax.swing.JButton btAddProdutoOrc;
    private javax.swing.JButton btLimparCar;
    private javax.swing.JButton btRemoveProduto;
    public static javax.swing.JButton cbAddFrete;
    public static javax.swing.JComboBox<String> cbFormaRecebi;
    public static javax.swing.JComboBox<String> cbNomeOrc;
    public static javax.swing.JComboBox<String> cbPagador;
    public static javax.swing.JComboBox<String> cbentregador;
    public static javax.swing.JTextField endEntrega;
    public static javax.swing.JFormattedTextField inputData;
    public static com.toedter.calendar.JDateChooser inputDataC;
    public static com.toedter.calendar.JDateChooser inputDataC1;
    public static javax.swing.JFormattedTextField inputTelefoneOrcamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    public static javax.swing.JTable tableCarOrcamento;
    private ArchiveTablesVendas.TableScrollButton tableScrollButton1;
    public static javax.swing.JTextField txEmail;
    public static javax.swing.JFormattedTextField txTotalOrcamento;
    private javax.swing.JFormattedTextField txdesconto;
    public static javax.swing.JTextField txtid;
    public static javax.swing.JFormattedTextField valorFrete;
    private javax.swing.JFormattedTextField valorcdescontoTotal;
    private javax.swing.JComboBox<String> vendedorOrcamento;
    // End of variables declaration//GEN-END:variables
}
