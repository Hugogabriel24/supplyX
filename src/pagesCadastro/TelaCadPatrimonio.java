package pagesCadastro;

import br.com.tanamao.dal.ModuloConexao;
import br.com.tanamao.dal.UpperCase;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import notification.Notification;
import pagesSearchs.SearchPatrimonio;

public final class TelaCadPatrimonio extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private File imagemA;

    public TelaCadPatrimonio() {
        initComponents();
        setIcon();
        conexao = ModuloConexao.conector();
        uper();
        formatarpreco();
        setcodabar();
        ListarLocais();
        GetPrats();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void uper() {
        txItem.setDocument(new UpperCase());
        txDescri.setDocument(new UpperCase());
        txserialPatrimonio.setDocument(new UpperCase());

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

                BufferedImage novaImagem = new BufferedImage(Painel1ImagePT.getWidth() + 100, Painel1ImagePT.getHeight() + 100, type);
                Graphics2D g = novaImagem.createGraphics();
                g.setComposite(AlphaComposite.Src);
                g.drawImage(image, 0, 0, Painel1ImagePT.getWidth() + 100, Painel1ImagePT.getHeight() + 100, null);

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

    private void abrirImagemA(Object source) {
        if (source instanceof File) {
            ImageIcon icon = new ImageIcon(imagemA.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel1ImagePT.getWidth(), Painel1ImagePT.getHeight(), 100));
            viewImage1PT.setIcon(icon);
        } else if (source instanceof byte[]) {
            ImageIcon icon = new ImageIcon(imagemA.getPath());
            icon.setImage(icon.getImage().getScaledInstance(Painel1ImagePT.getWidth(), Painel1ImagePT.getHeight(), 100));
            viewImage1PT.setIcon(icon);
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

        } catch (Exception e) {
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

    public void setcodabar() {
        String codigo;
        try {
            do {
                codigo = gerarCodigoEAN13();
            } while (verificarCodigoExistente(codigo));
            txEan.setText(codigo);
        } catch (SQLException e) {
        }

    }

    public boolean verificarCodigoExistente(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patrimonio WHERE codigo = ?";

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

    public void formatarpreco() {
        DecimalFormat decimal = new DecimalFormat("#,###,###.00");
        NumberFormatter numFormatter = new NumberFormatter(decimal);
        numFormatter.setFormat(decimal);
        numFormatter.setAllowsInvalid(false);
        DefaultFormatterFactory dfFactory = new DefaultFormatterFactory(numFormatter);

        txvalor.setFormatterFactory(dfFactory);

    }

    public void adicionar() {

        String sql = "insert into patrimonio(item,descricao,codigo,data,valor,serial,estado,local,prateleira,img1) values(?,?,?,?,?,?,?,?,?,?)";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        String dateAq = formato.format(dataAquis.getDate());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txItem.getText());
            pst.setString(2, txDescri.getText());
            pst.setString(3, txEan.getText());
            pst.setString(4, dateAq);
            pst.setString(5, txvalor.getText());
            pst.setString(6, txserialPatrimonio.getText());
            pst.setString(7, txEstadoPatrimonio.getSelectedItem().toString());
            pst.setString(8, cbLocal.getSelectedItem().toString());
            pst.setString(9, cbPrateleira.getSelectedItem().toString());
            pst.setBytes(10, getImagem1());

            if ((txItem.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    txID.setText(null);
                    txItem.setText(null);
                    txvalor.setText(null);
                    txDescri.setText(null);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Patrimonio adicionado com sucesso!!!");
                    noti.showNotification();

                }
            }

        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao adicionar o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void alterar() {
        String sql = "update patrimonio set item=?, descricao=?,data=?,valor=?,serial=?,estado=? where idpatrimonio=?";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        String dateAq = formato.format(dataAquis.getDate());
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txItem.getText());
            pst.setString(2, txDescri.getText());
            pst.setString(3, dateAq);
            pst.setString(5, txvalor.getText());
            pst.setString(5, txserialPatrimonio.getText());
            pst.setString(5, txEstadoPatrimonio.getSelectedItem().toString());
            pst.setString(5, txID.getText());

            if ((txItem.getText().isEmpty())) {
                Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Preencha todos os campos obrigatórios!!");
                noti.showNotification();

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    txID.setText(null);
                    txItem.setText(null);
                    txvalor.setText(null);
                    txDescri.setText(null);
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Patrimônio editado com sucesso!!!");
                    noti.showNotification();

                }
            }
        } catch (SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao alterar o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja remover o patrimônio?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from patrimonio where idpatrimonio=?";
            try {

                if ((txItem.getText().isEmpty())) {
                    Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Nenhum patrimônio selecionado!!");
                    noti.showNotification();
                }
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txID.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    txID.setText(null);
                    txItem.setText(null);
                    txvalor.setText(null);
                    txDescri.setText(null);
                    btAdc.setEnabled(true);
                    btRemove.setEnabled(false);
                    btEdit.setEnabled(false);
                    Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Patrimônio removido com sucesso!!!");
                    noti.showNotification();

                }

            } catch (SQLException e) {
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
                JOptionPane.showMessageDialog(null, "<html><b>Erro ao remover o cargo.</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);

            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txID = new javax.swing.JTextField();
        habilitar1PP = new javax.swing.JCheckBox();
        AlterImage1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btAdc = new javax.swing.JButton();
        btRemove = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txDescri = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txEan = new javax.swing.JTextField();
        dataAquis = new com.toedter.calendar.JDateChooser();
        txvalor = new javax.swing.JFormattedTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txserialPatrimonio = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txEstadoPatrimonio = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        cbLocal = new javax.swing.JComboBox<>();
        cbPrateleira = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        uploadimg1 = new javax.swing.JButton();
        Cancelimg1 = new javax.swing.JButton();
        btSearch = new javax.swing.JButton();
        Painel1ImagePT = new javax.swing.JPanel();
        viewImage1PT = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        txID.setText("jTextField1");

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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar Patrimonio");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setText("CODIGO EAN 13");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("ITEM");

        txItem.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setText("DATA AQUISIÇÃO");

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
        btRemove.setText("Excluir cargo");
        btRemove.setToolTipText("");
        btRemove.setEnabled(false);
        btRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveActionPerformed(evt);
            }
        });

        btEdit.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/editarUser.png"))); // NOI18N
        btEdit.setText("Editar cargo");
        btEdit.setToolTipText("");
        btEdit.setEnabled(false);
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

        txDescri.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setText("DESCRIÇÃO");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setText("VALOR ITEM");

        txEan.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txEan.setEnabled(false);

        dataAquis.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        txvalor.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel54.setFont(new java.awt.Font("Century Gothic", 3, 8)); // NOI18N
        jLabel54.setText("(EAN-13) gerado automaticamente");

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel7.setText("SERIAL / IDENTIFICAÇÃO ITEM");

        txserialPatrimonio.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel8.setText("ESTADO DE CONSERVAÇÃO");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        txEstadoPatrimonio.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        txEstadoPatrimonio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Novo (ainda não utilizado)", "Seminovo (usado, mas em excelente condição)", "Recondicionado (foi consertado e restaurado à condição de novo)", "Usado (em bom estado, mas pode apresentar sinais de uso)", "Danificado (tem algum tipo de dano ou defeito)", "Vencido (expirou a data de validade ou prazo de garantia)", "Quebrado (não funciona e precisa de reparos)", "Inoperante (não funciona e não pode ser reparado)", "Refurbished (recondicionado com peças originais de fábrica)", "Com defeito (tem um problema específico que pode ou não ser reparado)" }));

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

        jLabel64.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel64.setText("PRATELEIRA/GAVETA");

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

        btSearch.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/pesquisarUser.png"))); // NOI18N
        btSearch.setText("Procurar");
        btSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Painel1ImagePTLayout = new javax.swing.GroupLayout(Painel1ImagePT);
        Painel1ImagePT.setLayout(Painel1ImagePTLayout);
        Painel1ImagePTLayout.setHorizontalGroup(
            Painel1ImagePTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1PT, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
        );
        Painel1ImagePTLayout.setVerticalGroup(
            Painel1ImagePTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImage1PT, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel5.setText("IMAGEM DO PATRIMÔNIO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txDescri)
                            .addComponent(txItem)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel54)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(txEan, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)
                                    .addComponent(txvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txserialPatrimonio)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(0, 5, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbLocal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbPrateleira, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel64))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(dataAquis, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txEstadoPatrimonio, 0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btAdc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(Cancelimg1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(uploadimg1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Painel1ImagePT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txDescri, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txEan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txserialPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txEstadoPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataAquis, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(15, 15, 15)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6))
                        .addGap(15, 15, 15)
                        .addComponent(jLabel64)
                        .addGap(15, 15, 15)
                        .addComponent(cbPrateleira, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Painel1ImagePT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(uploadimg1)
                            .addComponent(Cancelimg1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRemove)
                    .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
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
        txID.setText(null);
        txItem.setText(null);
        txserialPatrimonio.setText(null);
        txvalor.setText(null);
        txDescri.setText(null);
        txEan.setText(null);
        btRemove.setEnabled(false);
        btEdit.setEnabled(false);
        btAdc.setEnabled(true);
        setcodabar();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Limpeza dos dados feita com sucesso!!!");
        noti.showNotification();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        //btCargo.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

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
//        String sql = "update partepecas set imagem1=? where idpartepecas=?";
//        try {
//            pst = conexao.prepareStatement(sql);
//
//            pst.setBytes(1, getImagem1());
//            pst.setString(2, txtID.getText());
//
//            int adicionado = pst.executeUpdate();
//
//            if (adicionado > 0) {
//                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/checklist.png")));
//                JOptionPane.showMessageDialog(null, "Anexo 1 alterado com sucesso!", "Alteração de anexo", JOptionPane.INFORMATION_MESSAGE, icon);
//                AlterImage1.setVisible(false);
//                habilitar1PP.setSelected(false);
//                uploadimg1.setEnabled(false);
//                Cancelimg1.setEnabled(false);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erro ao editar o anexo 1!!");
//        }
    }//GEN-LAST:event_AlterImage1ActionPerformed

    private void Cancelimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancelimg1ActionPerformed
        // TODO add your handling code here:
        if (viewImage1PT.getIcon() != null) {
            viewImage1PT.setIcon(null);

        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma imagem encontrada!");

        }
    }//GEN-LAST:event_Cancelimg1ActionPerformed

    private void uploadimg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadimg1ActionPerformed
        // TODO add your handling code here:
        imagemA = selecionarImagemA();
        abrirImagemA(imagemA);
    }//GEN-LAST:event_uploadimg1ActionPerformed

    private void cbLocalPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbLocalPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbPrateleira.removeAllItems();
        GetPrats();
        if (cbPrateleira.getSelectedItem() == null) {
            Notification noti = new Notification(this, Notification.Type.WARNING, Notification.Location.TOP_CENTER, "Cadastre uma PRATELEIRA para o LOCAL escolhido!!");
            noti.showNotification();
        }
    }//GEN-LAST:event_cbLocalPopupMenuWillBecomeInvisible

    private void cbLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLocalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbLocalActionPerformed

    private void cbPrateleiraPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbPrateleiraPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        //        GetBox();
    }//GEN-LAST:event_cbPrateleiraPopupMenuWillBecomeInvisible

    private void btSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchActionPerformed
        // TODO add your handling code here:
        SearchPatrimonio spp = new SearchPatrimonio();
        spp.setVisible(true);
    }//GEN-LAST:event_btSearchActionPerformed

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
            java.util.logging.Logger.getLogger(TelaCadPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCadPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCadPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCadPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCadPatrimonio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton AlterImage1;
    public static javax.swing.JButton Cancelimg1;
    public static javax.swing.JPanel Painel1ImagePT;
    public static javax.swing.JButton btAdc;
    public static javax.swing.JButton btEdit;
    public static javax.swing.JButton btRemove;
    public static javax.swing.JButton btSearch;
    public static javax.swing.JComboBox<String> cbLocal;
    public static javax.swing.JComboBox<String> cbPrateleira;
    public static com.toedter.calendar.JDateChooser dataAquis;
    public static javax.swing.JCheckBox habilitar1PP;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTextField txDescri;
    public static javax.swing.JTextField txEan;
    public static javax.swing.JComboBox<String> txEstadoPatrimonio;
    public static javax.swing.JTextField txID;
    public static javax.swing.JTextField txItem;
    public static javax.swing.JTextField txserialPatrimonio;
    public static javax.swing.JFormattedTextField txvalor;
    public static javax.swing.JButton uploadimg1;
    public static javax.swing.JLabel viewImage1PT;
    // End of variables declaration//GEN-END:variables
}
