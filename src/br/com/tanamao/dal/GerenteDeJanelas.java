/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tanamao.dal;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Hugo TT
 */
public class GerenteDeJanelas {
    
    private static JDesktopPane jDesktopPane;
    
    public GerenteDeJanelas(JDesktopPane JDesktopPane ) {
        GerenteDeJanelas.jDesktopPane = JDesktopPane;
        
    }
    
    public void abrirJanelas(JInternalFrame jInternalFrame){
        if (jInternalFrame.isVisible()){
            jInternalFrame.toFront();
            jInternalFrame.requestFocus();
        }else{
            jDesktopPane.add(jInternalFrame);
            jInternalFrame.setVisible(true);
            
        }
    }
    
}
