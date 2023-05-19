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

public class ModuloConexao {

    public Statement stm;
    public ResultSet rs;
    public Connection conn;

    public static Connection conector() {
//String url = "jdbc:mysql://192.185.177.26:3306/docate36_supplyX";
        java.sql.Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        //String url = "jdbc:mysql://127.0.0.1:3306/docate36_supplyX";
        String url = "jdbc:mysql://DESKTOP-0TPE7OL:3306/docate36_supplyX";
        String user = "admin";
        String password = "87520213@Server";

        // estabelecendo conexao com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);

            return conexao;
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }

    }

}
