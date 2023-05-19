/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tanamao.dal;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Hugo TT
 */
public class LimiteDigitos extends PlainDocument{
    
    private int tamanhoMax = 10;
         
    public LimiteDigitos(int tamanhoMax){
        this.tamanhoMax = tamanhoMax;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
 
            if (str == null) return;  
                    
             String stringAntiga = getText (0, getLength() );  
             int tamanhoNovo = stringAntiga.length() + str.length(); 
                        
             if (tamanhoNovo <= tamanhoMax) {  
                 super.insertString(offset, str , attr);  
             } else {    
                 super.insertString(offset, "", attr); 
             }  
    }
}
    

