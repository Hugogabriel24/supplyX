/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CadastroProdutos;

import java.awt.Toolkit;

/**
 *
 * @author Hugo Gabriel
 */
public class TelaAlteracaoComp extends javax.swing.JFrame {

    /**
     * Creates new form TelaAlteracaoComp
     */
    public TelaAlteracaoComp() {
        initComponents();
        enablesandvisibles();
        setIcon();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));;
    }

    public void enablesandvisibles() {
        barra1.setVisible(false);
        barra2.setVisible(false);
        panel2arm.setVisible(false);
        cbArmazena1.setVisible(false);
        cbCapArmazena1.setVisible(false);
        cbArmazena1.setSelectedItem("--");
        cbCapArmazena1.setSelectedItem("--");
        cbArmazena1.setSelectedItem("--");
        cbCapArmazena1.setSelectedItem("--");
        barra1.setVisible(false);
        cbCapArmazena1.setVisible(false);
        panel2arm.setVisible(false);
        cbArmazena1.setVisible(false);
        barra2.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        painelMemoria = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        nMemoria = new javax.swing.JComboBox<>();
        nTecM = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        nVeloM = new javax.swing.JComboBox<>();
        capmemoriaNote = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        serialMemoria = new javax.swing.JTextField();
        slotsmemoria = new javax.swing.JSpinner();
        jLabel90 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        duploArma = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        cbArmazena = new javax.swing.JComboBox<>();
        cbCapArmazena = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        serialhd = new javax.swing.JTextField();
        barra1 = new javax.swing.JLabel();
        barra2 = new javax.swing.JLabel();
        panel2arm = new javax.swing.JPanel();
        cbCapArmazena1 = new javax.swing.JComboBox<>();
        cbArmazena1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        btAddqtde = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Troca rapida de componente");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        painelMemoria.setBorder(javax.swing.BorderFactory.createTitledBorder("Memória"));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel21.setText("MEMÓRIA RAM");

        nMemoria.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nMemoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1GB", "2GB", "3GB", "4GB", "5GB", "6GB", "7GB", "8GB", "9GB", "10GB", "12GB", "14GB", "16GB", "18GB", "20GB", "22GB", "24GB", "26GB", "28GB", "32GB", "64GB" }));

        nTecM.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nTecM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DDR1", "DDR2", "DDR3", "DDR4", "DDR5", "DDR6" }));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("TECNOLOGIA RAM");

        jLabel34.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel34.setText("VELOCIDADE ");

        nVeloM.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        nVeloM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1333Mhz", "1600Mhz", "1866Mhz", "2133Mhz", "2400Mhz", "2600Mhz", "2666Mhz", "2800Mhz", "2933Mhz", "3000Mhz", "3200Mhz", "3333Mhz", "3400Mhz", "3466Mhz", "3600Mhz", "3733MHz", "3866MHz", "4000Mhz", "4133Mhz", "4266MHz", "4500MHz", "4600MHz", "4800MHz", "5066MHz", "5200MHz", "5333MHz", "5600MHz", "6000MHz", "6400MHz", "7200MHz" }));

        capmemoriaNote.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        capmemoriaNote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8GB", "12GB", "24GB", "32GB", "64GB", "128GB" }));

        jLabel35.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel35.setText("CAPACIDADE MAXIMA SUPORTADA");

        jLabel57.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel57.setText("SLOTS DISPONÍVEIS");

        jLabel56.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel56.setText("NÚMEROS DE SÉRIE");

        serialMemoria.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        slotsmemoria.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        jLabel90.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        jLabel90.setText("EXEMPLO: (NUMERODESERIE1,NUMERODESERIE2)");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/memoria-ram.png"))); // NOI18N

        javax.swing.GroupLayout painelMemoriaLayout = new javax.swing.GroupLayout(painelMemoria);
        painelMemoria.setLayout(painelMemoriaLayout);
        painelMemoriaLayout.setHorizontalGroup(
            painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMemoriaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelMemoriaLayout.createSequentialGroup()
                        .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20))
                        .addGap(164, 164, 164)
                        .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelMemoriaLayout.createSequentialGroup()
                                .addComponent(nTecM, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(painelMemoriaLayout.createSequentialGroup()
                                .addComponent(nMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)))
                        .addContainerGap())
                    .addGroup(painelMemoriaLayout.createSequentialGroup()
                        .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelMemoriaLayout.createSequentialGroup()
                                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel35))
                                .addGap(57, 57, 57)
                                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(capmemoriaNote, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nVeloM, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(painelMemoriaLayout.createSequentialGroup()
                                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel56)
                                    .addComponent(jLabel57))
                                .addGap(158, 158, 158)
                                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(serialMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(slotsmemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel90))))
                        .addGap(0, 86, Short.MAX_VALUE))))
        );
        painelMemoriaLayout.setVerticalGroup(
            painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMemoriaLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(nMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(15, 15, 15)
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(nTecM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nVeloM, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addGap(15, 15, 15)
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(capmemoriaNote, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(15, 15, 15)
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slotsmemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addGap(15, 15, 15)
                .addGroup(painelMemoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serialMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addGap(15, 15, 15)
                .addComponent(jLabel90)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 796, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(painelMemoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(painelMemoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("Memoria", jPanel3);

        jLabel66.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel66.setText("DUPLO ARMAZENAMENTO");

        duploArma.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        duploArma.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        duploArma.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                duploArmaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Armazenamento 1", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        cbArmazena.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbArmazena.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HD SLIM", "HD NORMAL", "SSD SATA", "SSD M.2", "SSD M.2 NVME", "SSD MSATA", "SEM DISCO DE ARMAZENAMENTO", "--" }));
        cbArmazena.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbArmazenaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        cbCapArmazena.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbCapArmazena.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8GB", "32GB", "64GB", "120GB", "128GB", "240GB", "256GB", "500GB", "512GB", "600GB", "750GB", "1TB", "2TB", "4TB", "--" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCapArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cbCapArmazena, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel41.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel41.setText("TIPO DE ARMAZENAMENTO");

        jLabel42.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel42.setText("CAPACIDADE DE ARMAZENAMENTO");

        jLabel54.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        jLabel54.setText("NÚMERO(S) DE SÉRIE");

        serialhd.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N

        barra1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        barra1.setText("/");

        barra2.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        barra2.setText("/");

        panel2arm.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Armazenamento 2", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 3, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        cbCapArmazena1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbCapArmazena1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8GB", "32GB", "64GB", "120GB", "128GB", "240GB", "256GB", "500GB", "512GB", "600GB", "750GB", "1TB", "2TB", "4TB", "--" }));

        cbArmazena1.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        cbArmazena1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "HD SLIM", "HD NORMAL", "SSD SATA", "SSD M.2", "SSD M.2 NVME", "SSD MSATA", "--" }));
        cbArmazena1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbArmazena1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout panel2armLayout = new javax.swing.GroupLayout(panel2arm);
        panel2arm.setLayout(panel2armLayout);
        panel2armLayout.setHorizontalGroup(
            panel2armLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2armLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2armLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCapArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panel2armLayout.setVerticalGroup(
            panel2armLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2armLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cbCapArmazena1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/armazenamento-em-disco.png"))); // NOI18N

        jLabel91.setFont(new java.awt.Font("Century Gothic", 3, 10)); // NOI18N
        jLabel91.setText("EXEMPLO: (NUMERODESERIE1,NUMERODESERIE2)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel66)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel41)
                        .addComponent(jLabel54)
                        .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(duploArma, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel91)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(serialhd, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(barra1)
                                        .addComponent(barra2))
                                    .addGap(19, 19, 19)
                                    .addComponent(panel2arm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 146, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel66)
                        .addComponent(duploArma, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(barra2)
                        .addGap(18, 18, 18)
                        .addComponent(barra1)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel41)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel42)
                        .addGap(27, 27, 27))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel2arm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serialhd, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel91)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Armazenamento", jPanel4);

        btAddqtde.setFont(new java.awt.Font("Century Gothic", 3, 14)); // NOI18N
        btAddqtde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/checklist.png"))); // NOI18N
        btAddqtde.setText("Salvar alterações");
        btAddqtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddqtdeActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btAddqtde)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btAddqtde)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddqtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddqtdeActionPerformed
        // TODO add your handling code here:
        //        int getselect = tablePP.getSelectedRow();
        //        if (getselect >= 0) {
        //            int total;
        //            String qtdAtual = txtQtdAtual.getText();
        //            int qtda = Integer.parseInt(qtdAtual);
        //            int qtdnew = Integer.parseInt(spinnerNew.getValue().toString());
        //            total = qtda + qtdnew;
        //
        //            String sql = "update partepecas set qtde=? where idpartepecas =?";
        //            conexao = ModuloConexao.conector();
        //
        //            try {
        //
        //                String descri = descriselected.getText();
        //
        //                pst = conexao.prepareStatement(sql);
        //                pst.setInt(1, total);
        //                pst.setString(2, idselected.getText());
        //
        //                int adicionado = pst.executeUpdate();
        //
        //                if (adicionado > 0) {
        //                    DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + descri + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);
        //                    idselected.setText(null);
        //                    descriselected.setText(null);
        //                    txtQtdAtual.setText(null);
        //                    colocarDados();
        //                }
        //
        //            } catch (Exception e) {
        //                System.out.println(e);
        //
        //            }
        //        } else {
        //            JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
        //        }
    }//GEN-LAST:event_btAddqtdeActionPerformed

    private void duploArmaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_duploArmaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (duploArma.getSelectedItem().equals("SIM")) {
            barra1.setVisible(true);
            barra2.setVisible(true);
            cbArmazena1.setVisible(true);
            panel2arm.setVisible(true);
            cbCapArmazena1.setVisible(true);
            cbArmazena1.setSelectedItem("HD SLIM");
            cbCapArmazena1.setSelectedItem("8GB");
        } else {
            barra1.setVisible(false);
            barra2.setVisible(false);
            panel2arm.setVisible(false);
            cbArmazena1.setVisible(false);
            cbCapArmazena1.setVisible(false);
            cbArmazena1.setSelectedItem("--");
            cbCapArmazena1.setSelectedItem("--");

        }
    }//GEN-LAST:event_duploArmaPopupMenuWillBecomeInvisible

    private void cbArmazenaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbArmazenaPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        if (cbArmazena.getSelectedItem().equals("SEM DISCO DE ARMAZENAMENTO")) {
            cbCapArmazena.setSelectedItem("--");
        }
    }//GEN-LAST:event_cbArmazenaPopupMenuWillBecomeInvisible

    private void cbArmazena1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbArmazena1PopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cbArmazena1PopupMenuWillBecomeInvisible

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
            java.util.logging.Logger.getLogger(TelaAlteracaoComp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAlteracaoComp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAlteracaoComp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAlteracaoComp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAlteracaoComp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel barra1;
    public static javax.swing.JLabel barra2;
    private javax.swing.JButton btAddqtde;
    public static javax.swing.JComboBox<String> capmemoriaNote;
    public static javax.swing.JComboBox<String> cbArmazena;
    public static javax.swing.JComboBox<String> cbArmazena1;
    public static javax.swing.JComboBox<String> cbCapArmazena;
    public static javax.swing.JComboBox<String> cbCapArmazena1;
    public static javax.swing.JComboBox<String> duploArma;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JComboBox<String> nMemoria;
    public static javax.swing.JComboBox<String> nTecM;
    public static javax.swing.JComboBox<String> nVeloM;
    private javax.swing.JPanel painelMemoria;
    private javax.swing.JPanel panel2arm;
    public static javax.swing.JTextField serialMemoria;
    public static javax.swing.JTextField serialhd;
    public static javax.swing.JSpinner slotsmemoria;
    // End of variables declaration//GEN-END:variables
}
