/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArchiveTablesVendas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class F1KeyListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            // Evento a ser executado quando a tecla F1 é pressionada
            System.out.println("Tecla F1 pressionada!");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Não é necessário implementar nada aqui
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não é necessário implementar nada aqui
    }

}