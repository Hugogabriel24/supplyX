/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelTableCores;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import pagesEstoque.TelaEstoquePP;

/**
 *
 * @author hugogabriel
 */
public class CorNaLinhaEstoquePP extends DefaultTableCellRenderer {

    public void CorNaLinhaEstoquePP() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color r = Color.red;
        Color w = Color.WHITE;
        Color b = Color.BLACK;

        Object qtd = TelaEstoquePP.tablePP.getValueAt(row, 9);
        Object promo = TelaEstoquePP.tablePP.getValueAt(row, 8);

        try {

            int qtde = Integer.parseInt(qtd.toString());

            if (!promo.equals("0,0") && qtde == 0) {
                r = Color.red;
                w = Color.WHITE;
                label.setForeground(w);
                label.setBackground(new Color(218, 75, 75));

            } else if (!promo.equals("0,0")) {
                r = Color.blue;
                w = Color.WHITE;
                label.setForeground(w);
                label.setBackground(new Color(37, 150, 190));
            } else if (qtde <= 0) {
                r = Color.red;
                w = Color.WHITE;
                label.setForeground(w);
                label.setBackground(new Color(218, 75, 75));
            } else {
                if (row % 2 == 0) {
                    label.setBackground(Color.WHITE);
                    b = Color.BLACK;
                    label.setForeground(b);
                } else {
                    label.setBackground(new Color(242, 242, 242));
                    b = Color.BLACK;
                    label.setForeground(b);
                }
            }

        } catch (Exception e) {
        }

        if (isSelected) {
            Color color = new Color(147, 167, 39);
            b = Color.BLACK;
            label.setForeground(b);
            label.setBackground(color);

        }

        return label;

    }

}
