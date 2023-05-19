/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lotes;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hugogabriel
 */
public class CorLinhasLote extends DefaultTableCellRenderer {

    public void CorLinhasLote() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color r = Color.red;
        Color w = Color.WHITE;
        Color b = Color.BLACK;

        try {

            if (row % 2 == 0) {
                label.setBackground(Color.WHITE);
                b = Color.BLACK;
                label.setForeground(b);
            } else {
                label.setBackground(new Color(242, 242, 242));
                b = Color.BLACK;
                label.setForeground(b);
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
