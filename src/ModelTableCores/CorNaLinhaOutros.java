package ModelTableCores;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import Vendas.SelectProdutosCar;

public class CorNaLinhaOutros extends DefaultTableCellRenderer {

    public void CorNaLinhaOutros() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color r = Color.red;
        Color w = Color.WHITE;
        Color b = Color.BLACK;
        Object qtd = SelectProdutosCar.tableOutros.getValueAt(row, 7);
        Object promo = SelectProdutosCar.tableOutros.getValueAt(row, 6);

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
