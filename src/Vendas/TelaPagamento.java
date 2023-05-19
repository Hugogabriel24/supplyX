
package Vendas;

import TableModels.ProdutosModels;
import br.com.tanamao.dal.Email;
import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.itemVendaDAO;
import br.com.tanamao.model.itemVenda;
import ds.desktop.notify.DesktopNotify;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import pages.LoadingPage;
import static Vendas.PDV.ObsFrete;
import static Vendas.PDV.ObsVenda;
import static Vendas.PDV.campoLimite;
import static Vendas.PDV.cbNomeCli;
import static Vendas.PDV.codabarvenda;
import static Vendas.PDV.inputTelefoneVenda;
import static Vendas.PDV.txEmail;
import static pages.TelaPrincipal.empresa;

/**
 *
 * @author Hugo TT
 */
public class TelaPagamento extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    ProdutosModels carrinho;

    public TelaPagamento() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        formatarpreco();
        InputTotalPag.setText(PDV.txTotal.getText());
        JOptionPane.setRootFrame(this);

    }
    
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void load() {
        int sair = JOptionPane.showConfirmDialog(null, "Confirmar a venda?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            LoadingPage load = new LoadingPage();
            load.setVisible(true);
            new Thread() {
                int i = 0;

                public void run() {
                    while (i < 100) {
                        i++;
                        LoadingPage.txtLoad.setText(String.valueOf(i) + "%");
                        try {
                            sleep(50);
                            if (i == 0) {
                                LoadingPage.txtChange.setText("Carregando os itens...");
                            }
                            if (i == 10) {
                                LoadingPage.txtChange.setText("Carregando os modúlos...     ");
                            }
                            if (i == 30) {
                                LoadingPage.txtChange.setText("Salvando os itens no banco...  ");
                                ValidarVenda();

                            }
                            if (i == 50) {
                                LoadingPage.txtChange.setText("Salvando dados do clientes...   ");
                            }
                            if (i == 70) {
                                LoadingPage.txtChange.setText("Procurando mais itens...   ");
                            }
                            if (i == 80) {
                                LoadingPage.txtChange.setText("Selecionando o item a tabela ...       ");
                                try {
                                    if (emailcheck.isSelected()) {
                                        if (txEmail.getText().equals("")) {
                                            DesktopNotify.showDesktopMessage("Erro ao enviar email", "Cliente sem email cadastrado!", DesktopNotify.ERROR, 7000L);

                                        } else {
                                            LoadingPage.txtChange.setText("Enviando Email de confirmação...   ");
                                            String PegarEmail = txEmail.getText();
                                            //String PegarNome = rs.getString("nomecli");
                                            Email email = new Email(
                                                    "Confirmação de agendamento de tatuagem",
                                                    "Teste",
                                                    PegarEmail);
                                            email.enviar();
                                            DesktopNotify.showDesktopMessage("Confirmação de e-mail", "Um e-mail foi enviado para " + PegarEmail + " confirmando o agendamento!!", DesktopNotify.SUCCESS, 7000L);

                                        }
                                    }

                                } catch (Exception e) {
                                    DesktopNotify.showDesktopMessage("Erro", "Erro no envio do email!!", DesktopNotify.ERROR, 7000L);
                                }
                            }
                            if (i == 85) {
                                if (checkSms.isSelected()) {

                                    try {
                                        LoadingPage.txtChange.setText("Enviando SMS de confirmação...   ");

                                        String PegarNumero = inputTelefoneVenda.getText().replace("+", "").replace("(", "").replace(")", "").replace("-", "");
                                        String PegarNumeroS = inputTelefoneVenda.getText();
                                        String NomeCliente = cbNomeCli.getSelectedItem().toString();
                                        String data = "";

                                        data += "username=" + URLEncoder.encode("hgsystem", "ISO-8859-1");
                                        data += "&password=" + URLEncoder.encode("87520213Hugo", "ISO-8859-1");
                                        data += "&message=" + URLEncoder.encode("Ola, " + NomeCliente + " teste");
                                        data += "&want_report=1";
                                        data += "&msisdn=" + PegarNumero + "";
                                        // Send data
                                        // Please see the FAQ regarding HTTPS (port 443) and HTTP (port 80/5567)
                                        URL url = new URL("https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

                                        URLConnection conn = url.openConnection();
                                        conn.setDoOutput(true);
                                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                                        wr.write(data);
                                        wr.flush();
                                        // Get the response
                                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                        String line;

                                        while ((line = rd.readLine()) != null) {
                                            System.out.println(line);
                                            DesktopNotify.showDesktopMessage("Confirmação de SMS", "Um SMS foi enviado para " + PegarNumeroS + " confirmando a venda!!", DesktopNotify.SUCCESS, 7000L);

                                        }
                                        wr.close();
                                        rd.close();

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }
                            }
                            if (i == 90) {
                                LoadingPage.txtChange.setText("Venda gerada com sucesso!!");
                                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
                                JOptionPane.showMessageDialog(null, "Venda feita com sucesso!", "PDV", JOptionPane.INFORMATION_MESSAGE, icon);

                            }
                            if (i == 99) {
                                carrinho.limpar();
                                load.dispose();

                            }

                        } catch (HeadlessException | InterruptedException e) {
                        }

                    }

                }

            }
                    .start();
            this.dispose();
        } else {

        }
    }

    private void ValidarVenda() {

        String sql = "insert into vendas(idcliente,nomecli,cpfcli,telcli,datacompra,garantia,vendedor,canalvenda,formareceb,obsfrete,endentrega,valorfrete,dataentrega,pagador,formapagamento,nparcelas,valordinheiro,valorcartao,descontoaplicado,valorcdesconto,valorrecebido,total,status,empresa,pagamento,obsvenda,entregador,ean13) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String pegaid = PDV.txtid.getText();
        String peganome = PDV.cbNomeCli.getSelectedItem().toString();
        String pegacpf = PDV.InputCPFVenda.getText();
        String pegatel = PDV.inputTelefoneVenda.getText();
        String pegavendedor = PDV.cbVendedor.getSelectedItem().toString();
        String pegacanal = PDV.cbCanal.getSelectedItem().toString();
        String pegarecebimento = PDV.cbFormaRecebi.getSelectedItem().toString();
        String pegarend = PDV.endEntrega.getText();
        String pegarfrete = PDV.valorFrete.getText();
        String pegarpagador = PDV.cbPagador.getSelectedItem().toString();
        String pegarDataEntrega = PDV.inputData.getText();
        String pegarEntregador = PDV.cbentregador.getSelectedItem().toString();


        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        String dateCompra = formato.format(PDV.inputDataC.getDate());
        String dateGarantia = formato.format(PDV.inputGarant.getDate());

        if (checkPagamento.isSelected()) {

            try {

                pst = conexao.prepareStatement(sql);
                pst.setString(1, pegaid);
                pst.setString(2, peganome);
                pst.setString(3, pegacpf);
                pst.setString(4, pegatel);
                pst.setString(5, dateCompra);
                pst.setString(6, dateGarantia);
                pst.setString(7, pegavendedor);
                pst.setString(8, pegacanal);
                pst.setString(9, pegarecebimento);
                pst.setString(10, ObsFrete.getText());
                pst.setString(11, pegarend);
                pst.setString(12, pegarfrete);
                pst.setString(13, pegarDataEntrega);
                pst.setString(14, pegarpagador);
                pst.setString(15, CBOFormapgm.getSelectedItem().toString());
                pst.setString(16, CBOParcelas.getSelectedItem().toString());
                pst.setString(17, inputValorDinheiro.getText());
                pst.setString(18, inputValorCartao.getText());
                pst.setString(19, txdesconto.getText());
                pst.setString(20, valorcdescontoTotal.getText());
                pst.setString(21, "0");
                pst.setString(22, InputTotalPag.getText());
                pst.setString(23, "concluida");
                pst.setString(24, empresa.getText());
                pst.setString(25, "pendente");
                pst.setString(26, ObsVenda.getText());
                pst.setString(27, pegarEntregador);
                pst.setString(28, codabarvenda.getText());

                if ((InputTotalPag.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    int adicionado = pst.executeUpdate();

                    if (adicionado > 0) {
                        for (int nt = 0; nt < carrinho.getRowCount(); nt++) {

                            int qtdAtual, qtdComprada, qtdAtualizada;
                            itemVendaDAO daoitem = new itemVendaDAO();

                            itemVenda item = new itemVenda();

                            item.setIdproduto(carrinho.getValueAt(nt, 0).toString());
                            item.setProduto(carrinho.getValueAt(nt, 1).toString());
                            item.setDescricao(carrinho.getValueAt(nt, 2).toString());
                            item.setCodigob(carrinho.getValueAt(nt, 3).toString());
                            item.setSerial(carrinho.getValueAt(nt, 4).toString());
                            item.setMarca(carrinho.getValueAt(nt, 5).toString());
                            item.setPreco(carrinho.getValueAt(nt, 6).toString());
                            item.setQtd(carrinho.getValueAt(nt, 7).toString());
                            item.setSubtotal(carrinho.getValueAt(nt, 8).toString());

                            //Baixa de estoque OUTROS
                            if (item.getProduto().equals("Outros")) {
                                qtdAtual = daoitem.retornaEstoqueAtualOutros(item.getIdproduto());
                                qtdComprada = Integer.parseInt(carrinho.getValueAt(nt, 7).toString());
                                qtdAtualizada = qtdAtual - qtdComprada;
                                String DescricaoOutros = (carrinho.getValueAt(nt, 2).toString());
                                daoitem.baixaEstoqueOutros(item.getIdproduto(), qtdAtualizada, DescricaoOutros);
                            }
                            if (item.getProduto().equals("Notebook")) {
                                qtdAtual = daoitem.retornaEstoqueAtualNotebook(item.getIdproduto());
                                qtdComprada = Integer.parseInt(carrinho.getValueAt(nt, 7).toString());
                                qtdAtualizada = qtdAtual - qtdComprada;
                                String DescricaoNote = (carrinho.getValueAt(nt, 2).toString());
                                daoitem.baixaEstoqueNotebook(item.getIdproduto(), qtdAtualizada, DescricaoNote);
                            }

                            daoitem.CadastrarItem(item);
                        }

                    }
                }

            } catch (HeadlessException | NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.print(e);

            }

        } else {

            try {

                pst = conexao.prepareStatement(sql);
                pst.setString(1, pegaid);
                pst.setString(2, peganome);
                pst.setString(3, pegacpf);
                pst.setString(4, pegatel);
                pst.setString(5, dateCompra);
                pst.setString(6, dateGarantia);
                pst.setString(7, pegavendedor);
                pst.setString(8, pegacanal);
                pst.setString(9, pegarecebimento);
                pst.setString(10, ObsFrete.getText());
                pst.setString(11, pegarend);
                pst.setString(12, pegarfrete);
                pst.setString(13, pegarDataEntrega);
                pst.setString(14, pegarpagador);
                pst.setString(15, CBOFormapgm.getSelectedItem().toString());
                pst.setString(16, CBOParcelas.getSelectedItem().toString());
                pst.setString(17, inputValorDinheiro.getText());
                pst.setString(18, inputValorCartao.getText());
                pst.setString(19, txdesconto.getText());
                pst.setString(20, valorcdescontoTotal.getText());
                pst.setString(21, "0");
                pst.setString(22, InputTotalPag.getText());
                pst.setString(23, "concluida");
                pst.setString(24, empresa.getText());
                pst.setString(25, "pago");
                pst.setString(26, ObsVenda.getText());
                pst.setString(27, pegarEntregador);
                pst.setString(28, codabarvenda.getText());
                

                if ((InputTotalPag.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

                } else {
                    int adicionado = pst.executeUpdate();

                    if (adicionado > 0) {
                        for (int nt = 0; nt < carrinho.getRowCount(); nt++) {

                            int qtdAtual, qtdComprada, qtdAtualizada;
                            itemVendaDAO daoitem = new itemVendaDAO();

                            itemVenda item = new itemVenda();

                            item.setIdproduto(carrinho.getValueAt(nt, 0).toString());
                            item.setProduto(carrinho.getValueAt(nt, 1).toString());
                            item.setDescricao(carrinho.getValueAt(nt, 2).toString());
                            item.setCodigob(carrinho.getValueAt(nt, 3).toString());
                            item.setSerial(carrinho.getValueAt(nt, 4).toString());
                            item.setMarca(carrinho.getValueAt(nt, 5).toString());
                            item.setPreco(carrinho.getValueAt(nt, 6).toString());
                            item.setQtd(carrinho.getValueAt(nt, 7).toString());
                            item.setSubtotal(carrinho.getValueAt(nt, 8).toString());

                            //Baixa de estoque OUTROS
                            if (item.getProduto().equals("Outros")) {
                                qtdAtual = daoitem.retornaEstoqueAtualOutros(item.getIdproduto());
                                qtdComprada = Integer.parseInt(carrinho.getValueAt(nt, 7).toString());
                                qtdAtualizada = qtdAtual - qtdComprada;
                                String DescricaoOutros = (carrinho.getValueAt(nt, 2).toString());
                                daoitem.baixaEstoqueOutros(item.getIdproduto(), qtdAtualizada, DescricaoOutros);
                            }
                            if (item.getProduto().equals("Notebook")) {
                                qtdAtual = daoitem.retornaEstoqueAtualNotebook(item.getIdproduto());
                                qtdComprada = Integer.parseInt(carrinho.getValueAt(nt, 7).toString());
                                qtdAtualizada = qtdAtual - qtdComprada;
                                String DescricaoNote = (carrinho.getValueAt(nt, 2).toString());
                                daoitem.baixaEstoqueNotebook(item.getIdproduto(), qtdAtualizada, DescricaoNote);
                            }

                            daoitem.CadastrarItem(item);
                        }

                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                System.out.print(e);

            }

        }

    }

    public void soundClick() {
        try {

            URL sd = getClass().getResource("/sounds/btclick2.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sd));
            c.start();

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {

        }
    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        inputValorCartao.setFormatterFactory(dfFactory);
        inputValorDinheiro.setFormatterFactory(dfFactory);
        valorcdescontoTotal.setFormatterFactory(dfFactory);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CreditoCliente = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        showLimite = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        CBOFormapgm = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        CBOParcelas = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        BTNFinalizar = new javax.swing.JButton();
        BTNCancel = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        InputTotalPag = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txdesconto = new javax.swing.JFormattedTextField();
        emailcheck = new javax.swing.JCheckBox();
        checkSms = new javax.swing.JCheckBox();
        inputValorCartao = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        valorcdescontoTotal = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        inputValorDinheiro = new javax.swing.JFormattedTextField();
        checkPagamento = new javax.swing.JCheckBox();

        CreditoCliente.setTitle("CRÉDITO");
        CreditoCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CreditoCliente.setUndecorated(true);
        CreditoCliente.setResizable(false);
        CreditoCliente.setSize(new java.awt.Dimension(340, 70));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel19.setText("Limite disponível do cliente:");

        showLimite.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        showLimite.setText("0,0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addComponent(showLimite)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showLimite)
                    .addComponent(jLabel19))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout CreditoClienteLayout = new javax.swing.GroupLayout(CreditoCliente.getContentPane());
        CreditoCliente.getContentPane().setLayout(CreditoClienteLayout);
        CreditoClienteLayout.setHorizontalGroup(
            CreditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        CreditoClienteLayout.setVerticalGroup(
            CreditoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TELA DE PAGAMENTO");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel14.setText("Forma de pagamento");

        CBOFormapgm.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CBOFormapgm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Cartão de crédito", "Cartão de débito", "PIX", "TED", "DOC", "Boleto", "Dinheiro + cartão", "PIX + dinheiro", "PIX + cartão", "Limite de crédito" }));
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

        jPanel2.setBackground(new java.awt.Color(147, 167, 39));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pay.png"))); // NOI18N
        jLabel15.setText("   PAGAMENTO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel15)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel16.setText("Número de parcelas");

        CBOParcelas.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        CBOParcelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "1x", "2x", "3x", "4x", "5x", "6x", "7x", "8x", "9x", "10x", "11x", "12x", "14x", "16x", "24x" }));
        CBOParcelas.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel17.setText("Valor pago em dinhero");

        BTNFinalizar.setBackground(new java.awt.Color(147, 167, 39));
        BTNFinalizar.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BTNFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        BTNFinalizar.setText("Finalizar venda");
        BTNFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNFinalizarActionPerformed(evt);
            }
        });

        BTNCancel.setBackground(new java.awt.Color(200, 22, 78));
        BTNCancel.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        BTNCancel.setForeground(new java.awt.Color(255, 255, 255));
        BTNCancel.setText("Cancelar pagamento");
        BTNCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNCancelActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel18.setText("TOTAL");

        InputTotalPag.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        InputTotalPag.setEnabled(false);

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("Valor pago em cartão");

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel21.setText("Desconto");

        txdesconto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txdesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txdescontoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txdescontoKeyReleased(evt);
            }
        });

        emailcheck.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        emailcheck.setText("Enviar e-mail de confirmação para o cliente");

        checkSms.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        checkSms.setText("Enviar SMS de confirmação para o cliente");

        inputValorCartao.setEnabled(false);
        inputValorCartao.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel22.setText("Valor total c/ desconto");

        valorcdescontoTotal.setEnabled(false);
        valorcdescontoTotal.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century", 0, 14)); // NOI18N
        jLabel1.setText("%");

        inputValorDinheiro.setEnabled(false);
        inputValorDinheiro.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        checkPagamento.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        checkPagamento.setText("Colocar pagamento como pendente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBOFormapgm, 0, 189, Short.MAX_VALUE)
                            .addComponent(CBOParcelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addComponent(jLabel22))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txdesconto)
                            .addComponent(valorcdescontoTotal)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputValorCartao)
                            .addComponent(inputValorDinheiro))))
                .addGap(33, 33, 33))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(InputTotalPag, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jLabel18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(emailcheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(checkSms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BTNCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(BTNFinalizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(checkPagamento)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(CBOFormapgm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(CBOParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(inputValorDinheiro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(inputValorCartao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txdesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(valorcdescontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(emailcheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkSms)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(InputTotalPag, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(checkPagamento)
                .addGap(18, 18, 18)
                .addComponent(BTNFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BTNCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
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

    private void BTNCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNCancelActionPerformed
        // TODO add your handling code here:
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja cancelar o pagamento?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_BTNCancelActionPerformed

    private void BTNFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNFinalizarActionPerformed
        load();

    }//GEN-LAST:event_BTNFinalizarActionPerformed

    private void CBOFormapgmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBOFormapgmActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_CBOFormapgmActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        CreditoCliente.setVisible(false);

    }//GEN-LAST:event_formWindowClosed

    private void txdescontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txdescontoKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txdescontoKeyReleased

    private void CBOFormapgmPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_CBOFormapgmPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (CBOFormapgm.getSelectedItem().equals("PIX")) {
            inputValorCartao.setEnabled(false);
            inputValorCartao.setText(null);
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("Dinheiro")) {
            inputValorCartao.setEnabled(false);
            inputValorDinheiro.setEnabled(true);
            inputValorCartao.setText(null);
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("Cartão de crédito")) {
            inputValorCartao.setEnabled(true);
            inputValorDinheiro.setEnabled(false);
            inputValorDinheiro.setText(null);
            CBOParcelas.setEnabled(true);

        }
        if (CBOFormapgm.getSelectedItem().equals("Cartão de débito")) {
            inputValorCartao.setEnabled(true);
            inputValorDinheiro.setEnabled(false);
            CBOParcelas.setEnabled(false);

        }
        if (CBOFormapgm.getSelectedItem().equals("TED")) {
            inputValorCartao.setEnabled(false);
            inputValorCartao.setText(null);
            inputValorCartao.setText(null);
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("Dinheiro + cartão")) {
            inputValorCartao.setEnabled(true);
            inputValorDinheiro.setEnabled(true);
            inputValorCartao.setText(null);
            inputValorDinheiro.setText(null);
            CBOParcelas.setEnabled(true);

        }
        if (CBOFormapgm.getSelectedItem().equals("PIX + dinheiro")) {
            inputValorCartao.setEnabled(false);
            inputValorCartao.setText(null);
            CBOParcelas.setEnabled(false);
            CBOParcelas.setSelectedItem("--");

        }
        if (CBOFormapgm.getSelectedItem().equals("PIX + cartão")) {
            inputValorCartao.setEnabled(false);
            inputValorCartao.setText(null);
            inputValorDinheiro.setText(null);
            inputValorDinheiro.setEnabled(false);
            CBOParcelas.setEnabled(true);

        }
        if (CBOFormapgm.getSelectedItem().equals("Limite de crédito")) {
            CreditoCliente.setVisible(true);
            showLimite.setText(campoLimite.getText());
        } else {
            CreditoCliente.setVisible(false);
        }
    }//GEN-LAST:event_CBOFormapgmPopupMenuWillBecomeInvisible

    private void txdescontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txdescontoKeyPressed
        // TODO add your handling code here:

        try {
            valorcdescontoTotal.setText(null);
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                String number = InputTotalPag.getText().replaceAll(",", ".");
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
            java.util.logging.Logger.getLogger(TelaPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPagamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPagamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTNCancel;
    private javax.swing.JButton BTNFinalizar;
    private javax.swing.JComboBox<String> CBOFormapgm;
    private javax.swing.JComboBox<String> CBOParcelas;
    private javax.swing.JDialog CreditoCliente;
    public static javax.swing.JTextField InputTotalPag;
    private javax.swing.JCheckBox checkPagamento;
    private javax.swing.JCheckBox checkSms;
    private javax.swing.JCheckBox emailcheck;
    private javax.swing.JFormattedTextField inputValorCartao;
    private javax.swing.JFormattedTextField inputValorDinheiro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel showLimite;
    private javax.swing.JFormattedTextField txdesconto;
    private javax.swing.JFormattedTextField valorcdescontoTotal;
    // End of variables declaration//GEN-END:variables

}
