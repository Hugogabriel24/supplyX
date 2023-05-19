/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package textFieldSearch;

import javax.swing.Icon;

public class SearchOption {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public SearchOption(String name, Icon icon) {
        this.name = name;
        this.icon = icon;
    }

    public SearchOption() {
    }

    private String name;
    private Icon icon;
}