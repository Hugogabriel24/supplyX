/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tanamao.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Classe responsável por enviar E-mail.
 * @author Denner Dias
 */
public class Email {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public String PegarEmail;
    public String PegarSenha;
    //static final String REMETENTE_NOME = "hgsystem.send@gmail.com";
    //static final String REMETENTE_SENHA = "87520213h";
    private String assunto ;
    private String mensagem;
    private String destinatario;
    
    /**
     * 
     * @param assunto  Assunto para envio do E-mail.
     * @param mensagem  Mensagem qual vai ser enviado ao destinatário.
     * @param destinatario  E-mail do destinatário.
     */
    public Email(String assunto, String mensagem, String destinatario) {
        conexao = ModuloConexao.conector();  
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.destinatario = destinatario;
    }

    
    
    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
   
    /**
     * Função para enviar o e-mail ao destinatário.
     */
    public void enviar (){
        
        String sql = "select * from email";
        try {
            pst = conexao.prepareStatement(sql);

            rs = pst.executeQuery();

            while(rs.next()){
                PegarEmail = rs.getString("email");
                PegarSenha = rs.getString("senha");
  }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } 
    
        SimpleEmail email = new SimpleEmail();
            email.setSSLOnConnect(true);
            email.setHostName("smtp.gmail.com");
            email.setSslSmtpPort("465");
            email.setStartTLSRequired(true);
            email.setStartTLSEnabled(true);
            //email.setSSLOnConnect(true);  
     
       email.setAuthenticator(new DefaultAuthenticator(PegarEmail, PegarSenha));
       try {           
           email.setFrom(PegarEmail);
           email.setDebug(true);
           email.setSubject(this.assunto);
           email.setMsg(this.mensagem);
           email.addTo(this.destinatario);//por favor trocar antes de testar!!!!

           email.send();
           System.out.println("enviado");

       } catch (EmailException e) {
            System.out.println("error:"+e);
       }
    }
}
