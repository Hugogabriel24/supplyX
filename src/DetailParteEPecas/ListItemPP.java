package DetailParteEPecas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ListItemPP extends javax.swing.JPanel {

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected;

    public ListItemPP() {
        initComponents();
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        if (label != null) {
            label.setForeground(color);
        }
    }

    public void setItem(Object obj) {
        if (obj instanceof ItemPP) {
            ItemPP item = (ItemPP) obj;
            label.setIcon(item.getIcon());
            label.setText(item.getText());
        } else {
            label.setText(obj + "");
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (selected) {
            g2.fillRect(0, 0, 3, getHeight());
            int x[] = {getWidth() - 15, getWidth(), getWidth()};
            int y[] = {getHeight() / 2, 0, getHeight()};
            g2.fillPolygon(x, y, x.length);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();

        label.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        label.setText("Nome do item");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label;
    // End of variables declaration//GEN-END:variables
}
