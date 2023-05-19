/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import notification.Notification;

/**
 *
 * @author hugogabriel
 */
public final class TelaCadBox extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private DefaultListModel lista = new DefaultListModel();

    public TelaCadBox() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        ListarDados();
        ListarPrateleira();
        formatarpreco();
        setcodabar();
        setNomeCaixaauto();
    }

    public void setNomeCaixaauto() {
        String sql = "select max(idbox)idbox from box";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                Date data = new Date();
                DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
                String date = formatador.format(data);
                String newdate = date.replaceAll("/", "");
                String idMax = rs.getString(1);
                String newName = "BOX-0" + idMax + "/" + newdate + "";
                NameCaixa.setText(newName);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void setcodabar() {
        String codigo;
        try {
            do {
                codigo = gerarCodigoEAN13();
            } while (verificarCodigoExistente(codigo));
            cxCod.setText(codigo);
        } catch (SQLException e) {
        }

    }

    public boolean verificarCodigoExistente(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM box WHERE codigocaixa = ?";

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

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        cxDescricao.setDocument(new UpperCase());

    }

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        comprimento.setFormatterFactory(dfFactory);
        largura.setFormatterFactory(dfFactory);
        altura.setFormatterFactory(dfFactory);

    }

    public void ListarDados() {
        String sql = "select box from box order by box";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                lista.addElement(nome);
                listCaixa.setModel(lista);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao listar a caixa.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    public void ListarPrateleira() {
        String sql = "select prateleira from prateleiras order by prateleira";

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String nome = rs.getString(1);
                cxPrateleira.addItem(nome);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao listar a prateleira.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }

    public void adicionar() {

        String sql = "insert into box(prateleira,box,descricao,tipocaixa,codigocaixa,cp,lg,al) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, cxPrateleira.getSelectedItem().toString());
            pst.setString(2, NameCaixa.getText());
            pst.setString(3, cxDescricao.getText());
            pst.setString(4, cxTipoCaixa.getSelectedItem().toString());
            pst.setString(5, cxCod.getText());
            pst.setString(6, comprimento.getText());
            pst.setString(7, largura.getText());
            pst.setString(8, altura.getText());

            if ((cxDescricao.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    lista.clear();
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Caixa adicionada com sucesso!!!");
                    noti.showNotification();
                    cxDescricao.setText(null);
                    NameCaixa.setText(null);
                    largura.setText(null);
                    altura.setText(null);
                    comprimento.setText(null);
                    setNomeCaixaauto();
                }
            }

        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar a caixa.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {
        String sql = "update box set descricao=?, box=?, prateleira=?,tipocaixa=?,codigocaixa=?,cp=?,lg=?,al=? where box=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, cxDescricao.getText());
            pst.setString(2, NameCaixa.getText());
            pst.setString(3, cxPrateleira.getSelectedItem().toString());
            pst.setString(4, cxTipoCaixa.getSelectedItem().toString());
            pst.setString(5, cxCod.getText());
            pst.setString(6, comprimento.getText());
            pst.setString(7, largura.getText());
            pst.setString(8, altura.getText());
            pst.setString(9, NameCaixa.getText());

            if ((cxDescricao.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    lista.clear();
                    ListarDados();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Caixa alterada com sucesso!!!");
                    noti.showNotification();
                    btAdc.setEnabled(true);
                    cxDescricao.setText(null);
                    NameCaixa.setText(null);
                    largura.setText(null);
                    altura.setText(null);
                    comprimento.setText(null);
                    setNomeCaixaauto();
                }
            }
        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar a caixa.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover a caixa?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from box where box=?";
            try {

                if ((cxDescricao.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Nenhuma caixa selecionado!!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, cxDescricao.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    lista.clear();
                    ListarDados();
                    btAdc.setEnabled(true);
                    cxDescricao.setText(null);
                    NameCaixa.setText(null);
                    largura.setText(null);
                    altura.setText(null);
                    comprimento.setText(null);
                    setNomeCaixaauto();
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Caixa removida com sucesso!!!");
                    noti.showNotification();
                }

            } catch (SQLException e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover a caixa.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cxPrateleira = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCaixa = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cxCod = new javax.swing.JTextField();
        btAdc = new javax.swing.JButton();
        btRemove = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        comprimento = new javax.swing.JFormattedTextField();
        largura = new javax.swing.JFormattedTextField();
        altura = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        cxTipoCaixa = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        cxDescricao = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        NameCaixa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Criar caixas");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("PRATELEIRA / GAVETA");

        cxPrateleira.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("DESCRIÇÃO DA CAIXA");

        listCaixa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Caixas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        listCaixa.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        listCaixa.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listCaixaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listCaixa);

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("MEDIDA DA CAIXA:");

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel5.setText("C :");
        jLabel5.setAlignmentX(0.9F);
        jLabel5.setAlignmentY(0.9F);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel6.setText("L :");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel7.setText("A :");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("CÓDIGO DA CAIXA");

        cxCod.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cxCod.setEnabled(false);

        btAdc.setBackground(new java.awt.Color(2, 119, 222));
        btAdc.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btAdc.setForeground(new java.awt.Color(255, 255, 255));
        btAdc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/plus.png"))); // NOI18N
        btAdc.setText("Adicionar");
        btAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcActionPerformed(evt);
            }
        });

        btRemove.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/REMOVE_LINHA.png"))); // NOI18N
        btRemove.setText("Excluir caixa");
        btRemove.setToolTipText("");
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText("Editar caixa");
        btEdit.setToolTipText("");
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/cleaning.png"))); // NOI18N
        jButton4.setText("Limpar dados");
        jButton4.setToolTipText("Limpar campos");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 9)); // NOI18N
        jLabel9.setText("( gerado automaticamente )");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel10.setText("cm");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel11.setText("cm");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel12.setText("cm");

        comprimento.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        largura.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N

        altura.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        altura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                alturaKeyPressed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/dimensions cópia.png"))); // NOI18N

        cxTipoCaixa.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cxTipoCaixa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CAIXA DE PAPELAO", "ORGANIZADOR", "CAIXA DE PLASTICO", "CAIXA DE MADEIRA", "EMBALAGEM DE PAPELAO OU PAPEL", "EMBALAGEM DE VIDRO", "EMBALAGEM DE ALUMINIO", "EMBALAGEM DE PLASTICO", "EMBALAGEM DE ISOPOR" }));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel14.setText("TIPO DA CAIXA");

        cxDescricao.setColumns(20);
        cxDescricao.setRows(5);
        jScrollPane2.setViewportView(cxDescricao);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("NOME CAIXA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(179, 179, 179))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cxPrateleira, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cxTipoCaixa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel4)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jLabel6)
                                                                    .addComponent(jLabel5))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(largura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(comprimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jLabel7)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(altura, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(11, 11, 11)))
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel10)
                                                            .addComponent(jLabel11)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addGap(114, 114, 114)
                                                        .addComponent(jLabel12)))
                                                .addGap(33, 33, 33)
                                                .addComponent(jLabel13))
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(NameCaixa))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btAdc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btRemove, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(cxCod, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cxPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel14)
                        .addGap(15, 15, 15)
                        .addComponent(cxTipoCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NameCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(cxCod, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10)
                                    .addComponent(comprimento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel11)
                                    .addComponent(largura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(altura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel12))
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(25, 25, 25))))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void listCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listCaixaMouseClicked
        // TODO add your handling code here:
        String SetMarca = listCaixa.getSelectedValue();

        String sql = "select prateleira,box,descricao,tipocaixa,codigocaixa,cp,lg,al from box where box = ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, SetMarca);
            rs = pst.executeQuery();

            while (rs.next()) {
                NameCaixa.setText(rs.getString(2));
                cxDescricao.setText(rs.getString(3));
                cxTipoCaixa.setSelectedItem(rs.getString(4));
                cxPrateleira.setSelectedItem(rs.getString(1));
                cxCod.setText(rs.getString(5));
                comprimento.setText(rs.getString(6));
                largura.setText(rs.getString(7));
                altura.setText(rs.getString(8));
                btAdc.setEnabled(false);

            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao listar a caixa.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

        }
    }//GEN-LAST:event_listCaixaMouseClicked

    private void btAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btAdcActionPerformed

    private void btRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btRemoveActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btEditActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        cxDescricao.setText(null);
        cxCod.setText(null);
        comprimento.setText(null);
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
        largura.setText(null);
        altura.setText(null);
        btAdc.setEnabled(true);
        setcodabar();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void alturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alturaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            adicionar();
        }
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            // remover();
        }
    }//GEN-LAST:event_alturaKeyPressed

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
            java.util.logging.Logger.getLogger(TelaCadBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadBox().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NameCaixa;
    private javax.swing.JFormattedTextField altura;
    private javax.swing.JButton btAdc;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btRemove;
    private javax.swing.JFormattedTextField comprimento;
    private javax.swing.JTextField cxCod;
    private javax.swing.JTextArea cxDescricao;
    private javax.swing.JComboBox<String> cxPrateleira;
    private javax.swing.JComboBox<String> cxTipoCaixa;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFormattedTextField largura;
    private javax.swing.JList<String> listCaixa;
    // End of variables declaration//GEN-END:variables
}
