/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usage;

import glasspanepopup.GlassPanePopup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import static usage.panel1.emprestarCheck;
import static usage.panel1.utilizarCheck;
import static usage.panel2.glassP;
import static usage.panel2.panelBackEmprestar;
import static usage.panel2.panelBackUtil;

/**
 *
 * @author hugog
 */
public class IndicatorUsage extends javax.swing.JPanel {

    public IndicatorUsage() {
        initComponents();
        setBackground(Color.WHITE);
        setOpaque(false);

        Component[] componentsEmprest = new Component[]{new panel1(1), new panel2(2), new panel3(3), new panel4(4), new panel5(5), new panel6(6)};
        panelSlider.setSliderComponent(componentsEmprest);
        progressIndicator.initSlider(panelSlider);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
        g2.dispose();
        super.paintComponent(grphcs);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        progressIndicator = new SliderAnimationUsage.ProgressIndicator();
        panelSlider = new SliderAnimationUsage.PanelSlider();
        button1 = new SliderAnimationUsage.Button();
        btNext = new SliderAnimationUsage.Button();

        progressIndicator.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 80, 10, 80));
        progressIndicator.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        progressIndicator.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Tipo usage", "Dados principais", "Escolher Itens", "Revisar Itens", "Mais informações", "Finalizar" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        progressIndicator.setProgress(0.0F);
        progressIndicator.setProgressColorGradient(new java.awt.Color(0, 0, 204));

        panelSlider.setOpaque(false);

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/anterior.png"))); // NOI18N
        button1.setText("Voltar");
        button1.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        btNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/proximo.png"))); // NOI18N
        btNext.setText("Proximo");
        btNext.setFont(new java.awt.Font("Century Gothic", 3, 12)); // NOI18N
        btNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressIndicator, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNextActionPerformed
        // TODO add your handling code here:
        if (progressIndicator.getProgressIndex() == 0) {
            if (utilizarCheck.isSelected()) {
                glassP.removeAll();
                glassP.add(panelBackUtil);
                progressIndicator.next();
            } else if (emprestarCheck.isSelected()) {
                glassP.removeAll();
                glassP.add(panelBackEmprestar);
                progressIndicator.next();
            }
        } else if (progressIndicator.getProgressIndex() == 4) {
            btNext.setText("Fechar");
            progressIndicator.next();
        } else if (progressIndicator.getProgressIndex() == 5) {
            GlassPanePopup.closePopupAll();
        } else {
            progressIndicator.next();
        }


    }//GEN-LAST:event_btNextActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        progressIndicator.previous();

    }//GEN-LAST:event_button1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private SliderAnimationUsage.Button btNext;
    private SliderAnimationUsage.Button button1;
    private SliderAnimationUsage.PanelSlider panelSlider;
    private SliderAnimationUsage.ProgressIndicator progressIndicator;
    // End of variables declaration//GEN-END:variables
}
