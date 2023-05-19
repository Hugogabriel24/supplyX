package br.com.tanamao.dal;

import ds.desktop.notify.DesktopNotify;
import ds.desktop.notify.NotifyTheme;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static OS.TelaOS.TotalOS;

public class moduloOS {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public String GetIDMaxItemOS() {
        String idvenda = null;
        String sql = "select max(idos)idos from os";
        conexao = ModuloConexao.conector();
        try {

            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                String idUV = rs.getString(1);
                idvenda = idUV;

            }
            return idvenda;

        } catch (SQLException e) {

        }
        return idvenda;

    }

    public void CadastrarProdutoOS(produtoOS obj) {

        String sql = "insert into itemos(idos,idproduto,produto,descricaoprod,codigob,serialprod,marcaprod,precoprod,qtdprod,subtotal) values(?,?,?,?,?,?,?,?,?,?)";
        conexao = ModuloConexao.conector();

        try {

            String getiduv = GetIDMaxItemOS();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getiduv);
            pst.setString(2, obj.getIdproduto());
            pst.setString(3, obj.getProduto());
            pst.setString(4, obj.getDescricao());
            pst.setString(5, obj.getCodigob());
            pst.setString(6, obj.getSerial());
            pst.setString(7, obj.getMarca());
            pst.setString(8, obj.getPreco());
            pst.setString(9, obj.getQtd());
            pst.setString(10, obj.getSubtotal());

            if ((TotalOS.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                }
            }

        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o item no sistema!!</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }

    public void CadastrarServicoOS(servicoOS obj) {

        String sql = "insert into servicoos(idos,idservico,servico,detalhes,qtde,preco,subtotal) values(?,?,?,?,?,?,?)";
        conexao = ModuloConexao.conector();

        try {

            String getiduv = GetIDMaxItemOS();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, getiduv);
            pst.setString(2, obj.getIdservico());
            pst.setString(3, obj.getServico());
            pst.setString(4, obj.getDetalhes());
            pst.setString(5, obj.getQtde());
            pst.setString(6, obj.getPreco());
            pst.setString(7, obj.getSubtotal());


            if ((TotalOS.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!!");

            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {

                }
            }

        } catch (HeadlessException | SQLException e) {
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/cancelar.png")));
            JOptionPane.showMessageDialog(null, "<html><b>Erro ao registrar o item no sistema!!</b></html>\n" + e + "", "<html><b>ERROR Service!</b></html>", JOptionPane.ERROR_MESSAGE, icon);
        }

    }
    
        public int retornaEstoqueAtualOutrosOS(String id) {
        String sql = "select qtde from partepecas where idpartepecas=?";
        conexao = ModuloConexao.conector();

        try {

            int qtdE = 0;
            pst = conexao.prepareStatement(sql);
            pst.setString(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                qtdE = (rs.getInt(1));
            }
            return qtdE;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void mudarEstoqueOutrosOS(String id, int qtdNovo, String DescricaoOutros) {

        String sql = "update partepecas set qtde=? where idpartepecas =?";
        conexao = ModuloConexao.conector();

        try {

            pst = conexao.prepareStatement(sql);
            pst.setInt(1, qtdNovo);
            pst.setString(2, id);

            int adicionado = pst.executeUpdate();

            if (adicionado > 0) {
                DesktopNotify.setDefaultTheme(NotifyTheme.Light);
                DesktopNotify.showDesktopMessage("Mudança de estoque", "O item: " + DescricaoOutros + " tem um novo valor de estoque.", DesktopNotify.SUCCESS, 7000L);

            }

        } catch (Exception e) {

        }

    }
}
