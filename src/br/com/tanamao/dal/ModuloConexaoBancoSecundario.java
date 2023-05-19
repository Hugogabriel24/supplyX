/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tanamao.dal;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author HUGO TT
 */
public class ModuloConexaoBancoSecundario {

    public Statement statment;
    public ResultSet results;
    public Connection connex;

    public static Connection conectorSec() {

        java.sql.Connection conexaoSec = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:8080/tanamao";
        String user = "admin";
        String password = "87520213@Server";

        // estabelecendo conexao com o banco
        try {
            Class.forName(driver);
            conexaoSec = DriverManager.getConnection(url, user, password);

            return conexaoSec;
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }

    }

}
