/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Verificadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author hugogabriel
 */
public class validarEmail {
   
//    public static void main(String[] args){
//        validarEmail vle = new validarEmail();
//        if(vle.ValidarEmail("HUGOGABRIEL24@LIV_E.COM")){
//            JOptionPane.showMessageDialog(null, "Certo");
//        }else{
//             JOptionPane.showMessageDialog(null, "errado");
//        }
//    }
    
    public boolean ValidarEmail(String email){
        Pattern pat = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,})$");
        Matcher mat = pat.matcher(email);
                return mat.find();
    }
    
}
