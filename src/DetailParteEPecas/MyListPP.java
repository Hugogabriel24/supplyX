/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DetailParteEPecas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

/**
 *
 * @author Hugo G
 */
public class MyListPP<E extends Object> extends JList<E> {

    private final DefaultListModel model;
    private Color selectedColor;
    private int hoverIndex = -1;

    private EventMenuSelectedPP event;

    public void AddEventMenuSelected(EventMenuSelectedPP event) {
        this.event = event;

    }

    public MyListPP() {
        model = new DefaultListModel();
        selectedColor = new Color(160, 190, 39);
        setModel(model);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    int index = locationToIndex(e.getPoint());
                    Object o = model.getElementAt(index);
                    if(event !=null){
                        event.selected(index);
                    }
                }
            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverIndex = -1;
                repaint();
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                checkMouse(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                checkMouse(e);
            }

        });

    }

    private void checkMouse(MouseEvent e) {
        Point p = e.getPoint();
        int index = locationToIndex(p);
        if (index != hoverIndex) {
            hoverIndex = index;
            repaint();
        }
    }

    @Override
    public ListCellRenderer getCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jlist, Object o, int index, boolean selected, boolean focus) {
                ListItemPP item = new ListItemPP();
                item.setItem(o);
                item.setBackground(MyListPP.this.getBackground());
                item.setForeground(MyListPP.this.getForeground());
                item.setSelected(selected);
                if (index == hoverIndex || selected) {
                    item.setBackground(selectedColor);
                }
                return item;
            }

        };
    }

    public void addItem(ItemPP item) {
        model.addElement(item);
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
}
