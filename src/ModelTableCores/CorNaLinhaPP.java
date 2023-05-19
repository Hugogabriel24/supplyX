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
import static pagesSearchs.SearchPartePecas.tbParteP;


/**
 *
 * @author hugogabriel
 */
public class CorNaLinhaPP extends DefaultTableCellRenderer {

    public void CorNaLinhaPP() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color r = new Color(216, 81, 81);
        Color w = Color.WHITE;
        Color b = Color.BLACK;

        Object qtd = tbParteP.getValueAt(row, 8);

        try {
            int qtde = Integer.parseInt(qtd.toString());

            if (qtde <= 0) {
                w = Color.WHITE;
                label.setForeground(w);
                label.setBackground(r);
            } else {
                w = Color.WHITE;
                b = Color.BLACK;
                label.setForeground(b);
                label.setBackground(w);
            }

        } catch (Exception e) {
        }

        if (isSelected) {
            Color color = new Color(147, 167, 39);
            b = Color.WHITE;
            label.setForeground(b);
            label.setBackground(color);

        }

        return label;

    }

}
