/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DetailParteEPecas;

import javax.swing.Icon;

/**
 *
 * @author Hugo G
 */
public class ItemPP {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
    public ItemPP(String text,Icon icon){
        this.text = text;
        this.icon = icon;
    }
    
    public ItemPP(){
        
    }

    private String text;
    private Icon icon;

}
