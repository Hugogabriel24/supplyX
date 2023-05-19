/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.tanamao.dal;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author hugogabriel
 */
public class UpperCase extends PlainDocument {
    @Override
    public void insertString( int offset, String str, javax.swing.text.AttributeSet attr)
            throws BadLocationException{
        super.insertString(offset, str.toUpperCase(), attr);
    }
    
    public void replace(int offset,String str, javax.swing.text.AttributeSet attr)
            throws BadLocationException{
        super.insertString(offset, str.toUpperCase(), attr);
    }
    
}
