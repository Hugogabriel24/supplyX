package pages;

import OS.TelaOS;
import OS.HistoricoOS;
import Orcamento.changeorcamento;
import TelaRelatorios.TelaRelatorioClientes;
import TelaRelatorios.TelaRelatorioVenda;
import Vendas.Historicovenda;
import Vendas.TelaCancelVenda;
import Vendas.PDV;
import PagesAdmin.cadCMS;
import PagesAdmin.comissoes;
import PagesAdmin.visuAcessos;
import pagesOpcoes.TelaFrete;
import pagesOpcoes.myPerfil;
import pagesOpcoes.UsersStatus;
import pagesCadastro.TelaCadFuncionario;
import pagesCadastro.TelaCadFornecedor;
import pagesCadastro.TelaCadEntregador;
import pagesCadastro.TelaCadUser;
import pagesCadastro.TelaCadBox;
import br.com.tanamao.dal.ModuloConexao;
import buttonTelaPrincipal.EventFloatingActionButton;
import com.formdev.flatlaf.FlatIntelliJLaf;
import glasspanepopup.GlassPanePopup;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import net.proteanit.sql.DbUtils;
import notification.Notification;
import static pages.ChamadoDetail.chamador;
import static pages.ChamadoDetail.descri;
import static pages.ChamadoDetail.idtext;
import static pages.ChamadoDetail.modulo;
import static pages.ChamadoDetail.problema;
import static pages.ChamadoDetail.statuschamada;
import static pages.ChamadoDetail.titulo;
import pagesCadastro.CadREF;
import pagesCadastro.TelaCadCargo;
import pagesCadastro.TelaCadCli;
import CadastroProdutos.TelaCadDesktop;
import pagesCadastro.TelaCadEtiqueta;
import pagesCadastro.TelaCadGrupo;
import pagesCadastro.TelaCadLocalestoque;
import pagesCadastro.TelaCadMarca;
import CadastroProdutos.TelaCadMonitor;
import CadastroProdutos.TelaCadNotebook;
import CadastroProdutos.TelaCadPartePecas;
import Lotes.VisualizerLotes;
import compras.telaPrincipalCompras;
import pagesCadastro.TelaCadPatrimonio;
import pagesCadastro.TelaCadPrateleira;
import pagesCadastro.TelaCadProcessador;
import pagesCadastro.TelaCadServicos;
import pagesCadastro.leads;
import pagesEstoque.TelaControleCaixa;
import pagesEstoque.TelaEstoqueDesktops;
import pagesEstoque.TelaEstoqueMonitores;
import pagesEstoque.TelaEstoqueNotebook;
import pagesEstoque.TelaEstoquePP;
import pagesEstoque.TelaEstoquePatrimonios;
import pagesOpcoes.Parcelas;
import pagesOpcoes.TelaLogClientes;
import pagesOpcoes.TelaLogEntregadores;
import pagesOpcoes.TelaLogFornecedor;
import pagesOpcoes.TelaLogFunc;
import pagesOpcoes.TelaLogProdutos;
import usage.NewUsage;

public final class TelaPrincipal extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public TelaPrincipal() {
        FlatIntelliJLaf.setup();    
        UIManager.put("TabbedPane.showTabSeparators", true);
        initComponents();
        GlassPanePopup.install(this);
        conexao = ModuloConexao.conector();        
        floatingActionButton1.addItem(new ImageIcon(getClass().getResource("/src/lead.png")), Color.WHITE);
        floatingActionButton1.addItem(new ImageIcon(getClass().getResource("/src/ml32x.png")), Color.WHITE);
        floatingActionButton1.addItem(new ImageIcon(getClass().getResource("/src/getwhatslogo.png")), Color.WHITE);
        setIcon();
        System.out.print(conexao);
        this.setExtendedState(MAXIMIZED_BOTH);
        ListarChamados();
        setColorFromEmpresa();
        selectProduct.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
        selectProduct.setLocationRelativeTo(null);
        floatingActionButton1.addEvent(new EventFloatingActionButton() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    leads ld = new leads();
                    ld.setVisible(true);
                    
                } else if (index == 1) {
                    buscaML bml = new buscaML();
                    bml.setVisible(true);
                    
                } else if (index == 2) {
                    
                    getwhats wtd = new getwhats();
                    wtd.setVisible(true);
                }
            }
            
        });
        
    }
    
    public void setColorFromEmpresa() {
        String empresaSelected = empresa.getText();
        if (empresaSelected.equals("docatec")) {
            PainelBack.setBackground(new Color(15, 102, 122));
        } else {
            PainelBack.setBackground(new Color(147, 167, 39));
        }
    }
    
    public void WelcomeMessage() {
        String Username = lblUsuario.getText();
        Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.BOTTOM_RIGHT, "Bem vindo(a) novamente, " + Username + " !!");
        noti.showNotification();
    }
    
    public void setLogo() {
        String tanamao = "tanamao";
        String Empresa = empresa.getText();
        if (Empresa == null ? tanamao != null : !Empresa.equals(tanamao)) {
            // JOptionPane.showMessageDialog(null, Empresa);

            ImageIcon icon = new ImageIcon(getClass().getResource("/src/docatec150x.png"));
            logo.setIcon(icon);
            this.setTitle("Docatec!");
        } else {
            //  JOptionPane.showMessageDialog(null, Empresa);

            ImageIcon icon1 = new ImageIcon(getClass().getResource("/src/logovetor150px.png"));
            logo.setIcon(icon1);
            this.setTitle("Tá na mão!");
        }
    }
    
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }
    
    public void ListarChamados() {
        String sql = "select idchamados as ID,titulo as Titulo, autor as Autor from chamados where status = 'aberto' ";
        try {
            pst = conexao.prepareStatement(sql);
            
            rs = pst.executeQuery();
            tableServChama.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void setarOff() {
        String sql = "update usuarios set status=? where login=?";
        String logado = "off";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, logado);
            pst.setString(2, username.getText());
            pst.executeUpdate();
            
        } catch (SQLException e) {
        }
    }
    
    public void setarCamposTabela() {
        int setar = tableServChama.getSelectedRow();
        String sql = "select idchamados,modulo,problema,prioridade,titulo,descricao,status,autor from chamados where idchamados like ?";
        
        String id = (tableServChama.getModel().getValueAt(setar, 0).toString());
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, id);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                String getat = (rs.getString(8));
                modulo.setSelectedItem(rs.getString(2));
                problema.setText(rs.getString(3));
                idtext.setText(rs.getString(1));
                descri.setText(rs.getString(6));
                titulo.setText(rs.getString(5));
                statuschamada.setText(rs.getString(7));
                chamador.setText(rs.getString(8));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void restricoesEmpresas() {
        
        if (empresa.getText().equals("tanamao")) {
            
        } else {
            
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        username = new javax.swing.JLabel();
        perfil = new javax.swing.JLabel();
        empresa = new javax.swing.JTextField();
        nomex = new javax.swing.JLabel();
        selectProduct = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        btNotebook = new javax.swing.JButton();
        btDesktop = new javax.swing.JButton();
        btOutros = new javax.swing.JButton();
        btMonitor = new javax.swing.JButton();
        PainelBack = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        DesktopView = new javax.swing.JDesktopPane();
        logoConv = new javax.swing.JLabel();
        floatingActionButton1 = new buttonTelaPrincipal.FloatingActionButton();
        jPanel2 = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        avatar = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lbldate = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableServChama = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        version = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuClientes = new javax.swing.JMenuItem();
        MenuForn = new javax.swing.JMenuItem();
        MenuFunc = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        menuProdutos = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        MenuUser = new javax.swing.JMenuItem();
        MenuRel = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        EstoqueNtb = new javax.swing.JMenuItem();
        EstoqueDesk = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        menuUsage = new javax.swing.JMenu();
        jMenuItem48 = new javax.swing.JMenuItem();
        menuAdm = new javax.swing.JMenu();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem42 = new javax.swing.JMenuItem();
        menuOpcoes = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        MenuFrete = new javax.swing.JMenuItem();
        MenuPerfil = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        statusUser = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        log = new javax.swing.JMenu();
        logcli = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem47 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        username.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        username.setText("user");

        perfil.setText("jLabel1");

        empresa.setText("jTextField1");

        nomex.setText("jLabel1");

        selectProduct.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        selectProduct.setTitle("Selecionar produtos");
        selectProduct.setMinimumSize(new java.awt.Dimension(460, 160));
        selectProduct.setModal(true);
        selectProduct.setResizable(false);
        selectProduct.setSize(new java.awt.Dimension(550, 150));
        selectProduct.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                selectProductWindowOpened(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jPanel4.setMaximumSize(new java.awt.Dimension(550, 150));
        jPanel4.setMinimumSize(new java.awt.Dimension(550, 150));
        jPanel4.setPreferredSize(new java.awt.Dimension(550, 150));

        btNotebook.setBackground(new java.awt.Color(204, 204, 204));
        btNotebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/notebook-2.png"))); // NOI18N
        btNotebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNotebookActionPerformed(evt);
            }
        });

        btDesktop.setBackground(new java.awt.Color(204, 204, 204));
        btDesktop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/area-de-trabalho-do-computador.png"))); // NOI18N
        btDesktop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDesktopActionPerformed(evt);
            }
        });

        btOutros.setBackground(new java.awt.Color(204, 204, 204));
        btOutros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/mais.png"))); // NOI18N
        btOutros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOutrosActionPerformed(evt);
            }
        });

        btMonitor.setBackground(new java.awt.Color(204, 204, 204));
        btMonitor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/monitor.png"))); // NOI18N
        btMonitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMonitorActionPerformed(evt);
            }
        });

        PainelBack.setBackground(new java.awt.Color(147, 167, 39));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Notebook");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Desktop");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Monitor");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Outros");

        javax.swing.GroupLayout PainelBackLayout = new javax.swing.GroupLayout(PainelBack);
        PainelBack.setLayout(PainelBackLayout);
        PainelBackLayout.setHorizontalGroup(
            PainelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelBackLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(92, 92, 92)
                .addComponent(jLabel3)
                .addGap(98, 98, 98)
                .addComponent(jLabel4)
                .addGap(98, 98, 98)
                .addComponent(jLabel6)
                .addContainerGap())
        );
        PainelBackLayout.setVerticalGroup(
            PainelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelBackLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelBackLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btNotebook)
                .addGap(70, 70, 70)
                .addComponent(btDesktop)
                .addGap(70, 70, 70)
                .addComponent(btMonitor)
                .addGap(70, 70, 70)
                .addComponent(btOutros)
                .addGap(15, 15, 15))
            .addComponent(PainelBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btNotebook)
                    .addComponent(btDesktop)
                    .addComponent(btMonitor)
                    .addComponent(btOutros))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout selectProductLayout = new javax.swing.GroupLayout(selectProduct.getContentPane());
        selectProduct.getContentPane().setLayout(selectProductLayout);
        selectProductLayout.setHorizontalGroup(
            selectProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        selectProductLayout.setVerticalGroup(
            selectProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TA NA MÃO");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        DesktopView.setBackground(new java.awt.Color(204, 204, 204));
        DesktopView.setDragMode(javax.swing.JDesktopPane.OUTLINE_DRAG_MODE);

        logoConv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/powered.png"))); // NOI18N
        logoConv.setToolTipText("");

        DesktopView.setLayer(logoConv, javax.swing.JLayeredPane.DEFAULT_LAYER);
        DesktopView.setLayer(floatingActionButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout DesktopViewLayout = new javax.swing.GroupLayout(DesktopView);
        DesktopView.setLayout(DesktopViewLayout);
        DesktopViewLayout.setHorizontalGroup(
            DesktopViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesktopViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(floatingActionButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 620, Short.MAX_VALUE)
                .addComponent(logoConv, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        DesktopViewLayout.setVerticalGroup(
            DesktopViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DesktopViewLayout.createSequentialGroup()
                .addContainerGap(461, Short.MAX_VALUE)
                .addGroup(DesktopViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(floatingActionButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoConv, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/logovetor150px.png"))); // NOI18N

        avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/avatar3.png"))); // NOI18N

        lblUsuario.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        lblUsuario.setText("Usuário");

        lbldate.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lbldate.setText("Data");

        tableServChama.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        tableServChama.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Titulo"
            }
        ));
        tableServChama.getTableHeader().setResizingAllowed(false);
        tableServChama.getTableHeader().setReorderingAllowed(false);
        tableServChama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableServChamaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableServChama);

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Serviços / chamados em aberto");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 35, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/conversando (1).png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        version.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        version.setText("v 1.0.0.3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsuario)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(logo)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(version)
                        .addContainerGap())))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbldate)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsuario)
                    .addComponent(avatar))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(version)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbldate)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(DesktopView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DesktopView)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAP_CADASTRO.png"))); // NOI18N
        jMenu1.setText("Cadastro");
        jMenu1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        menuClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuClientes.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuClientes.setText("Clientes");
        menuClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClientesActionPerformed(evt);
            }
        });
        jMenu1.add(menuClientes);

        MenuForn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
        MenuForn.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        MenuForn.setText("Fornecedores");
        MenuForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFornActionPerformed(evt);
            }
        });
        jMenu1.add(MenuForn);

        MenuFunc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_DOWN_MASK));
        MenuFunc.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        MenuFunc.setText("Funcionários");
        MenuFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFuncActionPerformed(evt);
            }
        });
        jMenu1.add(MenuFunc);

        jMenuItem40.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem40.setText("Entregador");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem40);

        menuProdutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuProdutos.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        menuProdutos.setText("Produtos");
        menuProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProdutosActionPerformed(evt);
            }
        });
        jMenu1.add(menuProdutos);

        jMenuItem16.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem16.setText("Serviços");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuItem44.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem44.setText("REF");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem44);

        jMenuItem23.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem23.setText("Local de estoque");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem23);

        jMenuItem29.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem29.setText("Patrimonio");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem29);

        jMenuItem24.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem24.setText("Prateleira");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem24);

        jMenuItem28.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem28.setText("Grupo / Subgrupo");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem28);

        jMenuItem38.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem38.setText("Cargo");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem38);

        jMenuItem30.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem30.setText("Marca / Modelo");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem30);

        jMenuItem32.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem32.setText("Processador");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem32);

        jMenuItem33.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem33.setText("Etiqueta");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem33);

        jMenuItem18.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem18.setText("Contas");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem18);

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem17.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem17.setText("Caixas");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem17);

        MenuUser.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_DOWN_MASK));
        MenuUser.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        MenuUser.setText("Usuários");
        MenuUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuUserActionPerformed(evt);
            }
        });
        jMenu1.add(MenuUser);

        jMenuBar1.add(jMenu1);

        MenuRel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAP_RELATORIO.png"))); // NOI18N
        MenuRel.setText("Relatórios");
        MenuRel.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem8.setText("Relatório de vendas");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        MenuRel.add(jMenuItem8);

        jMenuItem9.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem9.setText("Relatório de clientes");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        MenuRel.add(jMenuItem9);

        jMenuItem13.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem13.setText("Relatório de OS");
        MenuRel.add(jMenuItem13);

        jMenuItem39.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem39.setText("Lista de produtos");
        MenuRel.add(jMenuItem39);

        jMenuBar1.add(MenuRel);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAP_VENDAS.png"))); // NOI18N
        jMenu3.setText("Vendas");
        jMenu3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem4.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem4.setText("PDV");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem5.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem5.setText("Historico de vendas");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem2.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem2.setText("Orçamentos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem35.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem35.setText("Cancelamento de venda");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem35);

        jMenuItem36.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem36.setText("Devolução de venda / produtos");
        jMenu3.add(jMenuItem36);

        jMenuBar1.add(jMenu3);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAP_ESTOQUE.png"))); // NOI18N
        jMenu6.setText("Estoque");
        jMenu6.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        EstoqueNtb.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        EstoqueNtb.setText("Notebook");
        EstoqueNtb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstoqueNtbActionPerformed(evt);
            }
        });
        jMenu6.add(EstoqueNtb);

        EstoqueDesk.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        EstoqueDesk.setText("Desktop");
        EstoqueDesk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstoqueDeskActionPerformed(evt);
            }
        });
        jMenu6.add(EstoqueDesk);

        jMenuItem20.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem20.setText("Monitor");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenuItem22.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem22.setText("Partes & Peças");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem22);

        jMenuItem25.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem25.setText("Caixa");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem25);

        jMenuItem21.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem21.setText("Patrimônio ");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem21);

        jMenuBar1.add(jMenu6);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/chave-de-fenda.png"))); // NOI18N
        jMenu5.setText("Setor técnico");
        jMenu5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem11.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem11.setText("Cadastrar uma OS");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem11);

        jMenuItem12.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem12.setText("Historico OS");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem12);

        jMenuItem6.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem6.setText("Visita tecnica");
        jMenu5.add(jMenuItem6);

        jMenuItem26.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem26.setText("Erros e soluções");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem26);

        jMenuItem37.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem37.setText("Bateria de testes");
        jMenu5.add(jMenuItem37);

        jMenuItem31.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem31.setText("Lotes");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem31);

        jMenuBar1.add(jMenu5);

        menuUsage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/ordem.png"))); // NOI18N
        menuUsage.setText("Usage");

        jMenuItem48.setText("Visualizador");
        jMenuItem48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem48ActionPerformed(evt);
            }
        });
        menuUsage.add(jMenuItem48);

        jMenuBar1.add(menuUsage);

        menuAdm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/multa-administrativa.png"))); // NOI18N
        menuAdm.setText("Administrativo");
        menuAdm.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem34.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem34.setText("Visualizador de acessos");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        menuAdm.add(jMenuItem34);

        jMenuItem43.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem43.setText("Setor de compra");
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        menuAdm.add(jMenuItem43);

        jMenuItem41.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem41.setText("Comissão");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        menuAdm.add(jMenuItem41);

        jMenu9.setText("Tributação");
        jMenu9.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jMenuItem42.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem42.setText("Cadastrar NCM");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem42);

        menuAdm.add(jMenu9);

        jMenuBar1.add(menuAdm);

        menuOpcoes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/TELAP_OPCOES.png"))); // NOI18N
        menuOpcoes.setText("Opções");
        menuOpcoes.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem7.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem7.setText("Simulador de parcelas");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        menuOpcoes.add(jMenuItem7);

        MenuFrete.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        MenuFrete.setText("Calculador de frete");
        MenuFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuFreteActionPerformed(evt);
            }
        });
        menuOpcoes.add(MenuFrete);

        MenuPerfil.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        MenuPerfil.setText("Meu perfil");
        MenuPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuPerfilActionPerformed(evt);
            }
        });
        menuOpcoes.add(MenuPerfil);

        jMenuItem10.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem10.setText("Trocar usuário");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        menuOpcoes.add(jMenuItem10);

        jMenu8.setText("Transferências entre empresa");
        jMenu8.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem19.setText("Transferir clientes");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem19);

        menuOpcoes.add(jMenu8);

        statusUser.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        statusUser.setText("Status do usuário");
        statusUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusUserActionPerformed(evt);
            }
        });
        menuOpcoes.add(statusUser);

        jMenuItem27.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem27.setText("Configurações");
        menuOpcoes.add(jMenuItem27);

        log.setText("Log");
        log.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        logcli.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        logcli.setText("Clientes");
        logcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logcliActionPerformed(evt);
            }
        });
        log.add(logcli);

        jMenuItem14.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem14.setText("Fornecedores");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        log.add(jMenuItem14);

        jMenuItem45.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem45.setText("Entregadores");
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        log.add(jMenuItem45);

        jMenuItem46.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem46.setText("Funcionários");
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        log.add(jMenuItem46);

        jMenuItem47.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jMenuItem47.setText("Produtos");
        jMenuItem47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem47ActionPerformed(evt);
            }
        });
        log.add(jMenuItem47);

        menuOpcoes.add(log);

        jMenuItem15.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem15.setText("Backup do banco");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        menuOpcoes.add(jMenuItem15);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        jMenuItem3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem3.setText("Sair");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menuOpcoes.add(jMenuItem3);

        jMenuBar1.add(menuOpcoes);

        menuAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/ajuda.png"))); // NOI18N
        menuAjuda.setText("Ajuda");
        menuAjuda.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jMenuItem1.setText("Abrir chamado");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuAjuda.add(jMenuItem1);

        jMenuBar1.add(menuAjuda);

        setJMenuBar(jMenuBar1);

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

    private void menuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClientesActionPerformed
        
        TelaCadCli tld = new TelaCadCli();
        tld.setVisible(true);
    }//GEN-LAST:event_menuClientesActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.LONG);
        lbldate.setText(formatador.format(data));
        setLogo();
        restricoesEmpresas();
        WelcomeMessage();
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        PDV pdv = new PDV();
        pdv.setExtendedState(pdv.MAXIMIZED_BOTH);
        pdv.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            
            setarOff();
            System.exit(0);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void MenuFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFuncActionPerformed
        
        TelaCadFuncionario tlf = new TelaCadFuncionario();
        tlf.setVisible(true);
    }//GEN-LAST:event_MenuFuncActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        
        Historicovenda hv = new Historicovenda();
        hv.setExtendedState(hv.MAXIMIZED_BOTH);
        hv.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        setarOff();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        setarOff();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:            
        TelaOS tlos = new TelaOS();
        tlos.setExtendedState(tlos.MAXIMIZED_BOTH);
        tlos.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        TelaRelatorioVenda tlr = new TelaRelatorioVenda();
        tlr.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        TelaCadBox tlb = new TelaCadBox();
        tlb.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void MenuUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuUserActionPerformed
        // TODO add your handling code here:
        TelaCadUser tlu = new TelaCadUser();
        tlu.setVisible(true);
    }//GEN-LAST:event_MenuUserActionPerformed

    private void menuProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProdutosActionPerformed
        // TODO add your handling code here:
        selectProduct.setVisible(true);
        selectProduct.setAlwaysOnTop(true);

    }//GEN-LAST:event_menuProdutosActionPerformed

    private void MenuFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFornActionPerformed
        // TODO add your handling code here:
        TelaCadFornecedor tlf = new TelaCadFornecedor();
        tlf.setVisible(true);
    }//GEN-LAST:event_MenuFornActionPerformed

    private void MenuPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuPerfilActionPerformed
        // TODO add your handling code here:
        myPerfil mpf = new myPerfil();
        mpf.setVisible(true);

    }//GEN-LAST:event_MenuPerfilActionPerformed

    private void statusUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusUserActionPerformed
        // TODO add your handling code here:
        UsersStatus ust = new UsersStatus();
        ust.setVisible(true);
    }//GEN-LAST:event_statusUserActionPerformed

    private void MenuFreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuFreteActionPerformed
        // TODO add your handling code here:
        TelaFrete tlf = new TelaFrete();
        tlf.setVisible(true);
    }//GEN-LAST:event_MenuFreteActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        TelaCadServicos tls = new TelaCadServicos();
        tls.setVisible(true);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        TelaEstoqueMonitores tln = new TelaEstoqueMonitores();
        tln.setExtendedState(tln.MAXIMIZED_BOTH);
        tln.setVisible(true);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        TelaCadLocalestoque tlce = new TelaCadLocalestoque();
        tlce.setVisible(true);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        TelaCadPrateleira tlp = new TelaCadPrateleira();
        tlp.setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void logcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logcliActionPerformed
        // TODO add your handling code here:
        TelaLogClientes tlc = new TelaLogClientes();
        tlc.setVisible(true);
    }//GEN-LAST:event_logcliActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        TelaLogFornecedor tlf = new TelaLogFornecedor();
        tlf.setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void EstoqueNtbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstoqueNtbActionPerformed
        // TODO add your handling code here:
        TelaEstoqueNotebook tln = new TelaEstoqueNotebook();
        tln.setExtendedState(tln.MAXIMIZED_BOTH);
        tln.setVisible(true);
    }//GEN-LAST:event_EstoqueNtbActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        TelaControleCaixa tlc = new TelaControleCaixa();
        tlc.setVisible(true);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        TelaError tle = new TelaError();
        tle.setVisible(true);
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        TelaEstoquePP tepp = new TelaEstoquePP();
        tepp.setExtendedState(tepp.MAXIMIZED_BOTH);
        tepp.setVisible(true);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        PageChamado pc = new PageChamado();
        pc.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        // TODO add your handling code here:
        TelaCadProcessador tlcp = new TelaCadProcessador();
        tlcp.setVisible(true);
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        changeorcamento cho = new changeorcamento();
        cho.setVisible(true);

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void tableServChamaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableServChamaMouseClicked
        // TODO add your handling code here:
        ChamadoDetail cmdD = new ChamadoDetail();
        cmdD.setVisible(true);
        setarCamposTabela();
    }//GEN-LAST:event_tableServChamaMouseClicked

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        TelaCadGrupo tlg = new TelaCadGrupo();
        tlg.setVisible(true);
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
        TelaCadMarca tlm = new TelaCadMarca();
        tlm.setVisible(true);
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:
        TelaCadEtiqueta tlce = new TelaCadEtiqueta();
        tlce.setVisible(true);
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        visuAcessos visu = new visuAcessos();
        visu.setVisible(true);

    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        contas cnt = new contas();
        cnt.setVisible(true);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        // TODO add your handling code here:
        TelaCancelVenda clcv = new TelaCancelVenda();
        clcv.setVisible(true);
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        transferClientes transfc = new transferClientes();
        transfc.setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        BackupBanco bkp = new BackupBanco();
        bkp.setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:]
        TelaCadCargo clcg = new TelaCadCargo();
        clcg.setVisible(true);
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
        TelaCadEntregador tlcentre = new TelaCadEntregador();
        tlcentre.setVisible(true);
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja trocar de usúario?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            
            setarOff();
            this.dispose();
            NewTelaLogin nlg = new NewTelaLogin();
            nlg.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void btNotebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNotebookActionPerformed
        // TODO add your handling code here:
        selectProduct.setVisible(false);
        TelaCadNotebook tln = new TelaCadNotebook();
        tln.setVisible(true);
        

    }//GEN-LAST:event_btNotebookActionPerformed

    private void btDesktopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDesktopActionPerformed
        // TODO add your handling code here:
        selectProduct.setVisible(false);
        TelaCadDesktop tld = new TelaCadDesktop();
        tld.setVisible(true);
        

    }//GEN-LAST:event_btDesktopActionPerformed

    private void btOutrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOutrosActionPerformed
        // TODO add your handling code here:
        selectProduct.setVisible(false);
        TelaCadPartePecas tlpp;
        try {
            tlpp = new TelaCadPartePecas();
            if (tlpp.isVisible()) {
                tlpp.setVisible(false);
            } else {
                tlpp.setVisible(true);
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }//GEN-LAST:event_btOutrosActionPerformed

    private void btMonitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMonitorActionPerformed
        // TODO add your handling code here:
        selectProduct.setVisible(false);
        TelaCadMonitor tlm = new TelaCadMonitor();
        tlm.setVisible(true);
        

    }//GEN-LAST:event_btMonitorActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
        comissoes cms = new comissoes();
        cms.setVisible(true);
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        // TODO add your handling code here:
        cadCMS cms = new cadCMS();
        cms.setVisible(true);
    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void selectProductWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_selectProductWindowOpened
        // TODO add your handling code here:
        setColorFromEmpresa();
    }//GEN-LAST:event_selectProductWindowOpened

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        // TODO add your handling code here:
        CadREF cadr = new CadREF();
        cadr.setVisible(true);
    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        // TODO add your handling code here:
        TelaLogFunc tlf = new TelaLogFunc();
        tlf.setVisible(true);
    }//GEN-LAST:event_jMenuItem46ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        // TODO add your handling code here:
        TelaLogEntregadores tlge = new TelaLogEntregadores();
        tlge.setVisible(true);
    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        TelaRelatorioClientes tlc = new TelaRelatorioClientes();
        tlc.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        HistoricoOS hos = new HistoricoOS();
        hos.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
        TelaCadPatrimonio tlpmonio = new TelaCadPatrimonio();
        tlpmonio.setVisible(true);
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        TelaEstoquePatrimonios tlp = new TelaEstoquePatrimonios();
        tlp.setVisible(true);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem47ActionPerformed
        // TODO add your handling code here:
        TelaLogProdutos tlp = new TelaLogProdutos();
        tlp.setVisible(true);
    }//GEN-LAST:event_jMenuItem47ActionPerformed

    private void jMenuItem48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem48ActionPerformed
        // TODO add your handling code here:
        NewUsage nus = new NewUsage();
        nus.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem48ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:
        VisualizerLotes vzl = new VisualizerLotes();
        vzl.setExtendedState(vzl.MAXIMIZED_BOTH);
        vzl.setVisible(true);
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        Parcelas pcl = new Parcelas();
        pcl.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void EstoqueDeskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstoqueDeskActionPerformed
        // TODO add your handling code here:
        TelaEstoqueDesktops vzl = new TelaEstoqueDesktops();
        vzl.setExtendedState(vzl.MAXIMIZED_BOTH);
        vzl.setVisible(true);
    }//GEN-LAST:event_EstoqueDeskActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        // TODO add your handling code here:
        telaPrincipalCompras tlpc = new telaPrincipalCompras();
        tlpc.setExtendedState(tlpc.MAXIMIZED_BOTH);
        tlpc.setVisible(true);
    }//GEN-LAST:event_jMenuItem43ActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane DesktopView;
    public static javax.swing.JMenuItem EstoqueDesk;
    public static javax.swing.JMenuItem EstoqueNtb;
    public static javax.swing.JMenuItem MenuForn;
    private javax.swing.JMenuItem MenuFrete;
    public static javax.swing.JMenuItem MenuFunc;
    private javax.swing.JMenuItem MenuPerfil;
    public static javax.swing.JMenu MenuRel;
    public static javax.swing.JMenuItem MenuUser;
    private javax.swing.JPanel PainelBack;
    public static javax.swing.JLabel avatar;
    private javax.swing.JButton btDesktop;
    private javax.swing.JButton btMonitor;
    private javax.swing.JButton btNotebook;
    private javax.swing.JButton btOutros;
    public static javax.swing.JTextField empresa;
    private buttonTelaPrincipal.FloatingActionButton floatingActionButton1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    public static javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem47;
    private javax.swing.JMenuItem jMenuItem48;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lbldate;
    public static javax.swing.JMenu log;
    private javax.swing.JMenuItem logcli;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel logoConv;
    public static javax.swing.JMenu menuAdm;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenuItem menuClientes;
    private javax.swing.JMenu menuOpcoes;
    public static javax.swing.JMenuItem menuProdutos;
    private javax.swing.JMenu menuUsage;
    private javax.swing.JLabel nomex;
    public static javax.swing.JLabel perfil;
    public static javax.swing.JDialog selectProduct;
    public static javax.swing.JMenuItem statusUser;
    public static javax.swing.JTable tableServChama;
    public static javax.swing.JLabel username;
    private javax.swing.JLabel version;
    // End of variables declaration//GEN-END:variables
}
