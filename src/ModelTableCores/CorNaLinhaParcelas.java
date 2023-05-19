package ModelTableCores;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CorNaLinhaParcelas extends DefaultTableCellRenderer {

    public void CorNaLinhaParcelas() {
        
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
