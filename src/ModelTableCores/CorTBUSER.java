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
import pagesOpcoes.UsersStatus;

/**
 *
 * @author hugogabriel
 */
public class CorTBUSER extends DefaultTableCellRenderer {

    public void CorNaLinha() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color w = Color.WHITE;
        Object qtd = UsersStatus.tbUsers.getValueAt(row, 3);
        try {
            String status = qtd.toString();
            if (status.equals("on")) {
                Color color = new Color(147, 167, 39);
                w = Color.WHITE;
                label.setForeground(w);
                label.setBackground(color);
            } else {
                label.setBackground(new Color(218, 75, 75));
            }

        } catch (Exception e) {
        }

        return label;

    }

}
